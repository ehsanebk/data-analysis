package singapore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import analysis.Tokenizer;
import analysis.Values;
import analysis.Utilities;

public class SamplesLP {


	static int validIntervals = 2000;  // the acceptable interval in millisecond between two valid frames
	static int validLowerNumberOfFrames = 500; // the number of frames in each straight segment that is printed out to the file
	static int validUpperNumberOfFrames = 1500;
	Vector<SampleLP> samples;

	public SamplesLP() {
		samples = new Vector<SampleLP>();
	}
	class SampleLP{
		String protocol;
		String id;
		String trial;
		String trialdate;
		String trialtime;

		Vector<Segment> segments = new Vector<Segment>();
		Vector<Integer> MinMaxSeries = new Vector<Integer>();

		public double numberOfMinMaxAve(){
			Values ave = new Values();
			for (int i = 0; i < segments.size(); i++) {
				ave.add(segments.get(i).numberOfMinMax);
			}
			return ave.average();
		}
		public double distanceBetweenMinMaxAve(){
			
			Values ave = new Values();
			for (int i = 0; i < segments.size(); i++) {
				ave.add(segments.get(i).AverageDistanseBetweenMaxMin);
			}
			return ave.average();
		}
		
	}

	class Segment{
		int numberOfFrames;
		int numberOfMinMax;
		double AverageDistanseBetweenMaxMin;
		List<Integer> MinMixFrameNumbers;
		Values lanePos;  // -100 for any value which is not valid
		int startTime;
		public Segment() {
			lanePos = new Values();
			MinMixFrameNumbers = new Vector<Integer>();
		}
		public boolean valid(){
			return (numberOfFrames > validLowerNumberOfFrames && numberOfFrames > validUpperNumberOfFrames);
		}
	}
	
	public int size(){
		return samples.size();
	}
	
	public SampleLP get(int i){
		return samples.elementAt(i);
	}
	

	
	public void analysis(SamplesPVT PVTsamples){

		// adding driving data
		for (int i = 0; i < PVTsamples.size()-1; i++) {
			Values numberOfMinMaxAve  = new Values();
			Values distanceBetweenMinMaxAve  = new Values();

			Values LanePosition = new Values();
			String protocol = PVTsamples.get(i).getProtocol();
			String ID = PVTsamples.get(i).getId();
			String trial = PVTsamples.get(i).getTrial();
			String trialDate =PVTsamples.get(i).getTrialdate();
			String trialTimeString =PVTsamples.get(i).getTrialtimeString();
			int trialTime = PVTsamples.get(i).getTrialtime();
			int frameCount = 0;
			int startTimeOfsegment = 0;

			SampleLP newSample = new SampleLP();
			newSample.id = ID;
			newSample.protocol = protocol;
			newSample.trial = trial;
			newSample.trialdate = trialDate;
			newSample.trialtime = trialTimeString;

			if (ID.equals(PVTsamples.get(i+1).getId())){  // check to see if it's the last trial of the day or not

				// the directories that the raw driving csv datais being kept. This data is not filtered and it does have the invalid values. 
//				File directory = new File ("/Users/ehsanebk/OneDrive - drexel.edu/"
//						+ "Driving data - standard deviation lateral position (Singapore)/"
//						+ "Driving Data Raw/Protocol "+protocol+" driving data (csv)");
				File directory = new File ("/Users/Ehsan/Desktop/Protocol "+protocol+" driving data (csv)");

				System.out.println(ID+ " time pvt : " + trialTime);
				
				for (File file : directory.listFiles())
					if (file.getName().startsWith(ID)){
						
						
						Tokenizer t = new Tokenizer(file);
						t.readHeaderCSV(); // skiping the first line

						int invalidFrameCounter = 0;
						long lastValidFrameTime = 0;
						while (t.hasMoreTokens()){
							String[] lineCSV = t.readNextLineCSV();
							//reading the csv file
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
							double LateralPosition = Utilities.toDouble(lineCSV[10]);
							int LeftRhoThetaOK = Utilities.toInt(lineCSV[11]);
							int RightRhoThetaOK = Utilities.toInt(lineCSV[12]);
							int LeftSigSpikeOK = Utilities.toInt(lineCSV[13]);
							int RightSigSpikeOK = Utilities.toInt(lineCSV[14]);
							int LaneWidthOK = Utilities.toInt(lineCSV[15]);
							int isDriving = Utilities.toInt(lineCSV[16]);

							int timeDrivingFrame = Integer.valueOf(Time.substring(0, 5).replace(":", "")).intValue(); // getting the time in hhmm format

							// processing the frames and see if the invalid frames between valid frames are more than 1 seconds 
							if (timeDrivingFrame > addTime(trialTime,5) 
									&& timeDrivingFrame <= addTime(PVTsamples.get(i+1).getTrialtime(),5)){
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
									if (LanePosition.size() == 0)
										startTimeOfsegment = timeDrivingFrame;
									LanePosition.add(LateralPosition);
									lastValidFrameTime = date.getTime();
								}else if (validFrame && invalidFrameCounter>0) {
									if ( date.getTime() - lastValidFrameTime < validIntervals){
										for (int j = 0; j < invalidFrameCounter; j++) {
											LanePosition.add(-100);
										}
									}else{										
										//if (LanePosition.size() > validNumberOfFrames){
										System.out.println(LanePosition.size());
										List<Integer> MaxMinValues = MaxMinValues(LanePosition);
										Segment newsegment = new Segment();
										newsegment.numberOfMinMax = MaxMinValues.size();
										newsegment.MinMixFrameNumbers = MaxMinValues;

										// computing average
										double aveDis = 0;
										for (int j = 0; j < (MaxMinValues.size()-1); j++) {
											aveDis += MaxMinValues.get(j+1)- MaxMinValues.get(j);
										}
										newsegment.AverageDistanseBetweenMaxMin = aveDis / (MaxMinValues.size()-1);
										newsegment.numberOfFrames = LanePosition.size();
										newsegment.lanePos = LanePosition;
										newsegment.startTime = startTimeOfsegment;
										
										if (newsegment.valid()){
											newSample.MinMaxSeries.add(MaxMinValues.size());
											newSample.segments.add(newsegment);
										}
										
										
										//}
										LanePosition = new Values();
									}
									invalidFrameCounter = 0;
									lastValidFrameTime = date.getTime();	
								}else if (!validFrame)
									invalidFrameCounter++;				
							}
							// break if the tame is after
							if (timeDrivingFrame > PVTsamples.get(i+1).getTrialtime())
								break;
						}

					}
			}
			samples.add(newSample);
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

	// This function finds the local Min and Max based on the valid intervals in the LanePositions values which is an array
	// In the LanePos Values the invalid values have the value of -100
	private static List<Integer> MaxMinValues (Values LanePositions){  

		int window = 140;

		List<Integer> MaxMin = new ArrayList<Integer>();
		//the values which are not available are represented with -100
		int numberMax = 0;
		int numberMin = 0;
		boolean max;
		boolean min;

		// adding extra for capturing the end min and max
		for (int j = 0; j < 20; j++) {
			LanePositions.add(-100);
		}

		for (int i = (window/2); i < LanePositions.size()-(window/2); i++) {
			if (LanePositions.get(i) > -100 ){
				// local max with the specified interval 
				if ((LanePositions.get(i-(window/2)) < LanePositions.get(i) || LanePositions.get(i-(window/2)) == -100)
						&& (LanePositions.get(i) >LanePositions.get(i+(window/2)) || LanePositions.get(i+(window/2)) == -100)){
					max = true;
					for (int j = i-(window/2); j <= i+(window/2) ; j++) {
						if (LanePositions.get(j) > LanePositions.get(i) && LanePositions.get(j) > -100){
							max = false;
							break;
						}
					}
					if (max){
						MaxMin.add(i);
						numberMax++;
						i += (window/2);
					}
				}
				// local min with the specified interval 
				else if ((LanePositions.get(i-(window/2)) > LanePositions.get(i) || LanePositions.get(i-(window/2)) == -100)
						&& (LanePositions.get(i) < LanePositions.get(i+(window/2)) || LanePositions.get(i+(window/2)) == -100)){
					min = true;
					for (int j = i-(window/2); j <= i+(window/2) ; j++) {
						if (LanePositions.get(j) < LanePositions.get(i) && LanePositions.get(j) > -100){
							min = false;
							break;
						}
					}
					if (min){
						MaxMin.add(i);
						numberMin++;
						i += (window/2);
					}
				}				
			}
		}
		//return numberMax+numberMin;
		return MaxMin;
	}
}
