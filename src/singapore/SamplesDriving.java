package singapore;

import java.io.File;
import java.util.Vector;

import analysis.Tokenizer;
import analysis.Utilities;
import analysis.Values;

/**
 * @author ehsanebk
 *
 */
public class SamplesDriving {
	
	
	Vector<SampleDriving> samples;

	class SampleDriving{
		String protocol;
		String id;
		String trial;
		String trialdate;
		String trialtime;
		Values lanePos;
		
		public SampleDriving() {
			lanePos = new Values();
		}
	}

	public SamplesDriving() {
		samples = new Vector<SampleDriving>();
	}

	public String toString(int i) {
		return 
				samples.elementAt(i).protocol + "\t\t\t" +
				samples.elementAt(i).id + "\t\t" +
				samples.elementAt(i).trial + "\t\t" +
				samples.elementAt(i).trialdate + "\t" +
				samples.elementAt(i).trialtime + "\t\t" +

				Utilities.df2.format(samples.elementAt(i).lanePos.average());
	}

	public int size(){
		return samples.size();
	}

	public SampleDriving get(int i){
		return samples.elementAt(i);
	}

	
	//adding files which are filtered for the valid ones
	public void add(Process_PVT PVTsamples){
		
		// adding driving data
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

				// Directories were the filtered ( valid part of the driving data) is kept 
				File directory = new File ("/Users/ehsanebk/OneDrive - drexel.edu/"
						+ "Driving data - standard deviation lateral position (Singapore)/"
						+ "Driving Data Raw/Protocol "+protocol+" filtered");

				for (File file : directory.listFiles())
					if (file.getName().startsWith(ID)){
						
						Tokenizer t = new Tokenizer(file);
						t.skipLine(); // skiping the first line
						while (t.hasMoreTokens()){
							String[] lineCSV = t.readNextLineCSV();
							//reading the tokens
							String Video = lineCSV[0];
							String Time = lineCSV[1];
							String LeftRho = lineCSV[2];
							String LeftTheta = lineCSV[3];
							String RightRho = lineCSV[4];
							String RightTheta = lineCSV[5];
							String LeftX = lineCSV[6];
							String RightX = lineCSV[7];
							String LaneWidth = lineCSV[8];
							String LaneCenter = lineCSV[9];
							double LateralPosition = Double.valueOf(lineCSV[10]).doubleValue();
							int LeftRhoThetaOK = Integer.valueOf(lineCSV[11]).intValue();
							int RightRhoThetaOK = Integer.valueOf(lineCSV[12]).intValue();
							int LeftSigSpikeOK = Integer.valueOf(lineCSV[13]).intValue();
							int RightSigSpikeOK = Integer.valueOf(lineCSV[14]).intValue();
							int LaneWidthOK = Integer.valueOf(lineCSV[15]).intValue();
							int isDriving = Integer.valueOf(lineCSV[16]).intValue();

							int timeDrivingFrame = Integer.valueOf(Time.substring(0, 5).replace(":", "")).intValue(); // getting the time in hhmm format


							if (timeDrivingFrame > addTime(trialTime,5) && timeDrivingFrame <= addTime(PVTsamples.get(i+1).getTrialtime(),5)){
								LateralPositions.add(LateralPosition);
								frameCount++;
							}
							else if (timeDrivingFrame > PVTsamples.get(i+1).getTrialtime())
								break; //breaking the while loop
						}
						
						
						if (frameCount > 0){
							SampleDriving newsample = new SampleDriving();
							newsample.protocol = protocol;
							newsample.id = ID;
							newsample.trialdate = trialDate;
							newsample.trial = trial;
							newsample.lanePos = LateralPositions;							
							samples.add(newsample);
						}
						break;
					}
			}
		}
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
