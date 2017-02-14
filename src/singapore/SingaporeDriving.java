package singapore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import analysis.Tokenizer;
import analysis.Utilities;
import analysis.Values;


public class SingaporeDriving {

	public static void main(String[] args){

		//Driving_vs_PVT_pure();
		Driving_vs_PVT_processed();

	}
	

	public static void 	Driving_vs_PVT_processed(){
		Vector<SampleSingaporePVT> PVTsamples = new Vector<SampleSingaporePVT>();
		SampleSingaporePVT_Processed  PVTprocessed= new SampleSingaporePVT_Processed();
		
		File PVTfile = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "MFPD_PVT_all.txt");
		PVTsamples = (new SingaporePVT(PVTfile)).samples;

		File directoryA = new File ("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data/Protocol A filtered");
		
		File directoryB = new File ("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data/Protocol B filtered");
		
		
		File PVTfileOutPutProcessed = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "MFPD_Driving_vs_PVT_processed.csv");
		PrintWriter foutPVT = null;
		try {
			foutPVT = new PrintWriter(PVTfileOutPutProcessed);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		foutPVT.println("protocol,id,trialdate,,lapses 0, lapses 1,lapses 2,lapses 3,lapses 4,lapses 5,lapses 6,lapses 7,"
				+ ",alert ave 0,alert ave 1,alert ave 2,alert ave 3,alert ave 4,alert ave 5,alert ave 6,alert ave 7,"
				+ ",driving 0,driving 1,driving 2,driving 3,driving 4");
		foutPVT.flush();
		

		for (int i = 0; i < PVTsamples.size(); i++) {
			PVTprocessed.addPVTsample(PVTsamples.elementAt(i));
		}
		
		// adding driving data
		for (int i = 0; i < PVTsamples.size()-1; i++) {
			Values LateralPositions = new Values();
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
					for (File file : directoryA.listFiles())
						if (file.getName().startsWith(ID)){
							Vector<Double> LanePosAve= new Vector<Double>();

							Tokenizer t = new Tokenizer(file);
							t.skipLine(); // skiping the first line
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


								if (timeDrivingFrame > addTime(trialTime,5) && timeDrivingFrame <= addTime(PVTsamples.elementAt(i+1).getTrialtime(),5)){
									LateralPositions.add(LateralPosition);
									frameCount++;
								}
								else if (timeDrivingFrame > PVTsamples.elementAt(i+1).getTrialtime())
									break; //breaking the while loop

							}
							if (frameCount > 0){

								PVTprocessed.addDrivingSample(protocol, ID, trialDate, trial, LateralPositions.stddev());
								 // LateralPositions.average()
	
							}
							break;
						}
				}	
				else if (protocol.equals("B") && Integer.valueOf(trial).intValue() != 2){
					for (File file : directoryB.listFiles())
						if (file.getName().startsWith(ID)){
							Vector<Double> LanePosAve= new Vector<Double>();

							Tokenizer t = new Tokenizer(file);
							t.skipLine(); // skiping the first line
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


								if (timeDrivingFrame > addTime(trialTime,5) && timeDrivingFrame <= addTime(PVTsamples.elementAt(i+1).getTrialtime(),5)){
									LateralPositions.add(LateralPosition);
									frameCount++;
								}
								else if (timeDrivingFrame > PVTsamples.elementAt(i+1).getTrialtime())
									break; //breaking the while loop

							}

							if (frameCount > 0){
								PVTprocessed.addDrivingSample(protocol, ID, trialDate, trial, LateralPositions.stddev());
								 // LateralPositions.average()
							}
							break;
						}
				}			
			}
		}
		
		for (int i = 0; i < PVTprocessed.size(); i++) {
			foutPVT.println(PVTprocessed.toString(i));
			foutPVT.flush();
		}
		foutPVT.flush();
		foutPVT.close();
	}
	
	
	public static void 	Driving_vs_PVT_pure(){
		// getting all the pvt data 
		Vector<SampleSingaporePVT> PVTsamples = new Vector<SampleSingaporePVT>();
		
		
		File PVTfile = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "MFPD_PVT_all.txt");
		PVTsamples = (new SingaporePVT(PVTfile)).samples;
	
		
		File directoryA = new File ("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data/Protocol A filtered");
		
		File directoryB = new File ("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data/Protocol B filtered");
		
		
		File PVTfileOutPut = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "MFPD_Driving_vs_PVT.csv");		
		PrintWriter fout = null;
		try {
			fout = new PrintWriter(PVTfileOutPut);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
//		fout.println("pro	id	trial	trialdate	PVTtime(before)	"
//				+ "RTaverage(before)	PVTtime(after)	RTaverage(after)	"
//				+ "LanePosAverage	LanePosSD	FrameCount");
		fout.println("pro,id,trial,trialdate,PVTtime(before),"
				+ "RTaverage(before),PVTtime(after),RTaverage(after),"
				+ "LanePosAverage,LanePosSD,FrameCount");

		fout.flush();

		for (int i = 0; i < PVTsamples.size()-1; i++) {
			Values LateralPositions = new Values();
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
					for (File file : directoryA.listFiles())
						if (file.getName().startsWith(ID)){
							Vector<Double> LanePosAve= new Vector<Double>();

							Tokenizer t = new Tokenizer(file);
							t.skipLine(); // skiping the first line
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


								if (timeDrivingFrame > addTime(trialTime,5) && timeDrivingFrame <= addTime(PVTsamples.elementAt(i+1).getTrialtime(),5)){
									LateralPositions.add(LateralPosition);
									frameCount++;
								}
								else if (timeDrivingFrame > PVTsamples.elementAt(i+1).getTrialtime())
									break; //breaking the while loop

							}
							if (frameCount > 0){
//								fout.println(protocol+ "\t"+ ID +"\t"+ trial + "\t\t" + trialDate +"\t"+ 
//								trialTimeString + "\t\t\t"  + Utilities.df3.format(samples.elementAt(i).RT.average()) + "\t\t\t\t" +
//								samples.elementAt(i+1).getTrialtime() + "\t\t\t"  + 
//								Utilities.df3.format(samples.elementAt(i+1).RT.average()) + "\t\t\t\t" +
//								Utilities.df5.format(LateralPositions.average())+ "\t\t\t"+ 
//								Utilities.df5.format(LateralPositions.stddev())+ "\t\t" + frameCount);
						fout.println(protocol+ ","+ ID +","+ trial + "," + trialDate +","+ 
								trialTimeString + ","  + Utilities.df3.format(PVTsamples.elementAt(i).RT.average()) + "," +
								PVTsamples.elementAt(i+1).getTrialtime() + ","  + 
								Utilities.df3.format(PVTsamples.elementAt(i+1).RT.average()) + "," +
								Utilities.df5.format(LateralPositions.average())+ ","+ 
								Utilities.df5.format(LateralPositions.stddev())+ "," + frameCount);
								fout.flush();
							}
							break;
						}
				}	
				else if (protocol.equals("B") && Integer.valueOf(trial).intValue() != 2){
					for (File file : directoryB.listFiles())
						if (file.getName().startsWith(ID)){
							Vector<Double> LanePosAve= new Vector<Double>();

							Tokenizer t = new Tokenizer(file);
							t.skipLine(); // skiping the first line
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


								if (timeDrivingFrame > addTime(trialTime,5) && timeDrivingFrame <= addTime(PVTsamples.elementAt(i+1).getTrialtime(),5)){
									LateralPositions.add(LateralPosition);
									frameCount++;
								}
								else if (timeDrivingFrame > PVTsamples.elementAt(i+1).getTrialtime())
									break; //breaking the while loop

							}

							if (frameCount > 0){
//								fout.println(protocol+ "\t"+ ID +"\t"+ trial + "\t\t" + trialDate +"\t"+ 
//										trialTimeString + "\t\t\t"  + Utilities.df3.format(samples.elementAt(i).RT.average()) + "\t\t\t\t" +
//										samples.elementAt(i+1).getTrialtime() + "\t\t\t"  + 
//										Utilities.df3.format(samples.elementAt(i+1).RT.average()) + "\t\t\t\t" +
//										Utilities.df5.format(LateralPositions.average())+ "\t\t\t"+ 
//										Utilities.df5.format(LateralPositions.stddev())+ "\t\t" + frameCount);
								fout.println(protocol+ ","+ ID +","+ trial + "," + trialDate +","+ 
										trialTimeString + ","  + Utilities.df3.format(PVTsamples.elementAt(i).RT.average()) + "," +
										PVTsamples.elementAt(i+1).getTrialtime() + ","  + 
										Utilities.df3.format(PVTsamples.elementAt(i+1).RT.average()) + "," +
										Utilities.df5.format(LateralPositions.average())+ ","+ 
										Utilities.df5.format(LateralPositions.stddev())+ "," + frameCount);
								fout.flush();
							}
							break;
						}
				}			
			}
		}
		fout.flush();
		fout.close(); //closing the output file
	}

	private static int addTime(int time, int add) {
		int m =(time%100) + add;
		if ( m >= 60 ){
			return ((time/100) +(m/60))*100 + (m % 60);
		}
		else
			return time +add;
	}
	
	
}
