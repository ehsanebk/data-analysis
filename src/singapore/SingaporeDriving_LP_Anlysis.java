package singapore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.examples.NewLinesInCells;

import analysis.Tokenizer;
import analysis.Utilities;
import analysis.Values;


public class SingaporeDriving_LP_Anlysis {

	public static void main(String[] args){

		Driving_vs_PVT_processed();

	}
	

	public static void 	Driving_vs_PVT_processed(){
		Vector<SampleSingaporePVT> PVTsamples = new Vector<SampleSingaporePVT>();
		SampleSingaporePVT_Processed  PVTprocessed= new SampleSingaporePVT_Processed();
		
		File PVTfile = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "/PVT Raw Data/MFPD_PVT_all.txt");
		PVTsamples = (new SingaporePVT(PVTfile)).samples;

		File directoryA = new File ("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data/Protocol A filtered");
		
		File directoryB = new File ("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data/Protocol B filtered");
		
		
		File Test = new File ("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol A driving data (csv)/508ProcessedData.csv");
		
		
		File PVTfileOutPutProcessed = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/test.csv");
		PrintWriter fout = null;
		try {
			fout = new PrintWriter(PVTfileOutPutProcessed);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		 fout.println("test");

		
		// adding driving data
		for (int i = 0; i < 6; i++) {
			Values LanePosition = new Values();
			String protocol = PVTsamples.elementAt(i).getProtocol();
			String ID = PVTsamples.elementAt(i).getId();
			String trial = PVTsamples.elementAt(i).getTrial();
			String trialDate =PVTsamples.elementAt(i).getTrialdate();
			String trialTimeString =PVTsamples.elementAt(i).getTrialtimeString();
			int trialTime = PVTsamples.elementAt(i).getTrialtime();
			int frameCount = 0;

			if (ID.equals(PVTsamples.elementAt(i+1).getId())){  // check to see if it's the last trial of the day or not
				System.out.println("time pvt : " + trialTime);
				if (protocol.equals("A") && Integer.valueOf(trial).intValue()!=5){


					Vector<Double> LanePosAve= new Vector<Double>();

					Tokenizer t = new Tokenizer(Test);
					t.skipLine(); // skiping the first line
					
					int invalidFrameCounter = 0;
					long lastValidFrameTime = 0;
					while (t.hasMoreTokens()){
						String Video = t.nextToken();
						String Time = t.nextToken();
						String LeftRho = t.nextToken();
						String LeftTheta = t.nextToken();
						String RightRho = t.nextToken();
						String RightTheta = t.nextToken();
						String LeftX = t.nextToken();
						String RightX = t.nextToken();
						String LaneWidth = t.nextToken();
						String LaneCenter = t.nextToken();
						double LateralPosition = t.nextDouble();
						int LeftRhoThetaOK = t.nextInt();
						int RightRhoThetaOK = t.nextInt();
						int LeftSigSpikeOK = t.nextInt();
						int RightSigSpikeOK = t.nextInt();
						int LaneWidthOK = t.nextInt();
						int isDriving = t.nextInt();

						int timeDrivingFrame = Integer.valueOf(Time.substring(0, 5).replace(":", "")).intValue(); // getting the time in hhmm format

						// processing the frames and see if the invalid frames between valid frames are more than 1 seconds 
						if (timeDrivingFrame > addTime(trialTime,5) 
								&& timeDrivingFrame <= addTime(PVTsamples.elementAt(i+1).getTrialtime(),5)){
							boolean validFrame = (LeftRhoThetaOK==1 && RightRhoThetaOK==1 && LeftSigSpikeOK==1 && RightSigSpikeOK==1 
									&& LaneWidthOK==1 && isDriving==1);

							frameCount++;
							DateFormat formatter = new SimpleDateFormat("hh:mm:ss:SSS");
							Date date = null;
							try {
								date = formatter.parse(Time);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							if (validFrame && invalidFrameCounter ==0){
								//fout.print(LateralPosition + ",");
								LanePosition.add(LateralPosition);
								lastValidFrameTime = date.getTime();
							}else if (validFrame && invalidFrameCounter>0) {
								if ( date.getTime() - lastValidFrameTime < 10000){
									for (int j = 0; j < invalidFrameCounter; j++) {
										//fout.print(",");
										//fout.flush();
										LanePosition.add(-1);
									}
								}else{
									//fout.print("\n" + LateralPosition + ",");
									//fout.flush();
									System.out.println(LanePosition.size());
									// writng to file
									for (int j = 0; j < LanePosition.size(); j++) {
										if (LanePosition.get(j) >=0)
											fout.print(LanePosition.get(j) + ",");
										else
											fout.print(",");
										fout.flush();
									}
									
									LanePosition = new Values();
								}
								invalidFrameCounter = 0;
								lastValidFrameTime = date.getTime();	
							}else if (!validFrame)
								invalidFrameCounter++;				
						}
//						else if (timeDrivingFrame > PVTsamples.elementAt(i+1).getTrialtime())
//							break; //breaking the while loop
					}
					

				}	
			}
		}
		fout.close();
//			
//			if (ID.equals(PVTsamples.elementAt(i+1).getId())){  // check to see if it's the last trial of the day or not
//				System.out.println("time pvt : " + trialTime);
//				if (protocol.equals("A") && Integer.valueOf(trial).intValue()!=5){
//					for (File file : directoryA.listFiles())
//						if (file.getName().startsWith(ID)){
//							Vector<Double> LanePosAve= new Vector<Double>();
//
//							Tokenizer t = new Tokenizer(file);
//							t.skipLine(); // skiping the first line
//							while (t.hasMoreTokens()){
//								String Video = t.nextToken();
//								String Time = t.nextToken();
//								String LeftRho = t.nextToken();
//								String LeftTheta = t.nextToken();
//								String RightRho = t.nextToken();
//								String RightTheta = t.nextToken();
//								String LeftX = t.nextToken();
//								String RightX = t.nextToken();
//								String LaneWidth = t.nextToken();
//								String LaneCenter = t.nextToken();
//								double LateralPosition = t.nextDouble();
//								int LeftRhoThetaOK = t.nextInt();
//								int RightRhoThetaOK = t.nextInt();
//								int LeftSigSpikeOK = t.nextInt();
//								int RightSigSpikeOK = t.nextInt();
//								int LaneWidthOK = t.nextInt();
//								int isDriving = t.nextInt();
//
//								int timeDrivingFrame = Integer.valueOf(Time.substring(0, 5).replace(":", "")).intValue(); // getting the time in hhmm format
//
//
//								if (timeDrivingFrame > addTime(trialTime,5) && timeDrivingFrame <= addTime(PVTsamples.elementAt(i+1).getTrialtime(),5)){
//									LateralPositions.add(LateralPosition);
//									frameCount++;
//								}
//								else if (timeDrivingFrame > PVTsamples.elementAt(i+1).getTrialtime())
//									break; //breaking the while loop
//							}
//							if (frameCount > 0){
//								PVTprocessed.addDrivingSample(protocol, ID, trialDate, trial, LateralPositions.stddev());
//								 // LateralPositions.average()
//							}
//							break;
//						}
//				}	
//				else if (protocol.equals("B") && Integer.valueOf(trial).intValue() != 2){
//					for (File file : directoryB.listFiles())
//						if (file.getName().startsWith(ID)){
//							Vector<Double> LanePosAve= new Vector<Double>();
//
//							Tokenizer t = new Tokenizer(file);
//							t.skipLine(); // skiping the first line
//							while (t.hasMoreTokens()){
//								String Video = t.nextToken();
//								String Time = t.nextToken();
//								String LeftRho = t.nextToken();
//								String LeftTheta = t.nextToken();
//								String RightRho = t.nextToken();
//								String RightTheta = t.nextToken();
//								String LeftX = t.nextToken();
//								String RightX = t.nextToken();
//								String LaneWidth = t.nextToken();
//								String LaneCenter = t.nextToken();
//								double LateralPosition = t.nextDouble();
//								int LeftRhoThetaOK = t.nextInt();
//								int RightRhoThetaOK = t.nextInt();
//								int LeftSigSpikeOK = t.nextInt();
//								int RightSigSpikeOK = t.nextInt();
//								int LaneWidthOK = t.nextInt();
//								int isDriving = t.nextInt();
//
//								int timeDrivingFrame = Integer.valueOf(Time.substring(0, 5).replace(":", "")).intValue(); // getting the time in hhmm format
//
//
//								if (timeDrivingFrame > addTime(trialTime,5) && timeDrivingFrame <= addTime(PVTsamples.elementAt(i+1).getTrialtime(),5)){
//									LateralPositions.add(LateralPosition);
//									frameCount++;
//								}
//								else if (timeDrivingFrame > PVTsamples.elementAt(i+1).getTrialtime())
//									break; //breaking the while loop
//
//							}
//
//							if (frameCount > 0){
//								PVTprocessed.addDrivingSample(protocol, ID, trialDate, trial, LateralPositions.stddev());
//								 // LateralPositions.average()
//							}
//							break;
//						}
//				}			
//			}
//		}
		
	
	}
	
	
	

	// adding osme minutes to a time in the form of hhmm
	private static int addTime(int time, int add) {
		int m =(time%100) + add;
		if ( m >= 60 ){
			return ((time/100) +(m/60))*100 + (m % 60);
		}
		else
			return time +add;
	}
		
}
