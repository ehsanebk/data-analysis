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


/**
 * @author ehsanebk
 *
 */
public class SingaporeDriving_Analysis {

	public static void main(String[] args){

		//Driving_vs_PVT_pure();
		Driving_vs_PVT_processed();

	}
	

	public static void 	Driving_vs_PVT_processed(){
		SamplesDriving samplesDriving = new SamplesDriving(); 
		Samples_Processed  PVTprocessed= new Samples_Processed();
		
		File PVTfile = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "MFPD_PVT_all.txt");
		SamplesPVT PVTsamples = new SamplesPVT(PVTfile);

		File PVTfileOutPutProcessed = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "SDLP_PVT Raw Data Analysis/MFPD_Driving_vs_PVT_processed_new.csv");
		

		for (int i = 0; i < PVTsamples.size(); i++) {
			PVTprocessed.addPVTsample(PVTsamples.get(i));
		}

		samplesDriving.add(PVTsamples);
		
		for (int i = 0; i < samplesDriving.size(); i++) {
			PVTprocessed.addDrivingSample(samplesDriving.get(i));
		}
		
		PVTprocessed.writeToFile(PVTfileOutPutProcessed);
	}
	
	
	public static void 	Driving_vs_PVT_pure(){
		// getting all the pvt data 
		
		File PVTfile = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "MFPD_PVT_all.txt");
		SamplesPVT PVTsamples = new SamplesPVT(PVTfile);
	
		
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
			String protocol = PVTsamples.get(i).getProtocol();
			String ID = PVTsamples.get(i).getId();
			String trial = PVTsamples.get(i).getTrial();
			String trialDate =PVTsamples.get(i).getTrialdate();
			String trialTimeString =PVTsamples.get(i).getTrialtimeString();
			int trialTime = PVTsamples.get(i).getTrialtime();
			int frameCount = 0;

			if (ID.equals(PVTsamples.get(i+1).getId())){  // check to see if it's the last trial of the day or not
				System.out.println("time pvt : " + trialTime);
				if (Integer.valueOf(trial).intValue()!=5){
					File directory = new File ("/Users/ehsanebk/OneDrive - drexel.edu/"
							+ "Driving data - standard deviation lateral position (Singapore)/"
							+ "Driving Data/Protocol "+protocol+" filtered");
					
					for (File file : directory.listFiles())
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


								if (timeDrivingFrame > addTime(trialTime,5) && timeDrivingFrame <= addTime(PVTsamples.get(i+1).getTrialtime(),5)){
									LateralPositions.add(LateralPosition);
									frameCount++;
								}
								else if (timeDrivingFrame > PVTsamples.get(i+1).getTrialtime())
									break; //breaking the while loop

							}
							if (frameCount > 0){
								fout.println(protocol+ ","+ ID +","+ trial + "," + trialDate +","+ 
										trialTimeString + ","  + Utilities.df3.format(PVTsamples.get(i).RT.average()) + "," +
										PVTsamples.get(i+1).getTrialtime() + ","  + 
										Utilities.df3.format(PVTsamples.get(i+1).RT.average()) + "," +
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
