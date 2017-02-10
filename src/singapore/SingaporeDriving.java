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
		
		
		// getting all the pvt data 
		Vector<SampleSingaporePVT> samples = new Vector<SampleSingaporePVT>();
		File PVTfile = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "MFPD_PVT_all.txt");
		samples = (new SingaporePVT(PVTfile)).samples;
		
		
		
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

		for (int i = 0; i < samples.size()-1; i++) {
			Values LateralPositions = new Values();
			String protocol = samples.elementAt(i).getProtocol();
			String ID = samples.elementAt(i).getId();
			String trial = samples.elementAt(i).getTrial();
			String trialDate =samples.elementAt(i).getTrialdate();
			String trialTimeString =samples.elementAt(i).getTrialtimeString();
			int trialTime = samples.elementAt(i).getTrialtime();
			int frameCount = 0;
			if (ID.equals(samples.elementAt(i+1).getId())){  // check to see if it's the last trial of the day or not
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


								if (timeDrivingFrame > addTime(trialTime,5) && timeDrivingFrame <= addTime(samples.elementAt(i+1).getTrialtime(),5)){
									LateralPositions.add(LateralPosition);
									frameCount++;
								}
								else if (timeDrivingFrame > samples.elementAt(i+1).getTrialtime())
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
								trialTimeString + ","  + Utilities.df3.format(samples.elementAt(i).RT.average()) + "," +
								samples.elementAt(i+1).getTrialtime() + ","  + 
								Utilities.df3.format(samples.elementAt(i+1).RT.average()) + "," +
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


								if (timeDrivingFrame > addTime(trialTime,5) && timeDrivingFrame <= addTime(samples.elementAt(i+1).getTrialtime(),5)){
									LateralPositions.add(LateralPosition);
									frameCount++;
								}
								else if (timeDrivingFrame > samples.elementAt(i+1).getTrialtime())
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
										trialTimeString + ","  + Utilities.df3.format(samples.elementAt(i).RT.average()) + "," +
										samples.elementAt(i+1).getTrialtime() + ","  + 
										Utilities.df3.format(samples.elementAt(i+1).RT.average()) + "," +
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
