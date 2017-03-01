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

	static int validIntervals = 2000; // the acceptable interval in millisecond
	// between two valid frames
	
	Vector<SampleLP> samples;

	public SamplesLP() {
		samples = new Vector<SampleLP>();
	}


	public int size() {
		return samples.size();
	}

	public SampleLP get(int i) {
		return samples.elementAt(i);
	}

	public void analysis(SamplesPVT PVTsamples) {

		// adding driving data
		for (int i = 0; i < PVTsamples.size() - 1; i++) {

			String protocol = PVTsamples.get(i).getProtocol();
			String ID = PVTsamples.get(i).getId();
			String trial = PVTsamples.get(i).getTrial();
			String trialDate = PVTsamples.get(i).getTrialdate();
			String trialTimeString = PVTsamples.get(i).getTrialtimeString();
			int trialTime = PVTsamples.get(i).getTrialtime();

			Segment newsegment = new Segment(); 
			SampleLP newSample = new SampleLP();
			newSample.id = ID;
			newSample.protocol = protocol;
			newSample.trial = trial;
			newSample.trialdate = trialDate;
			newSample.trialtime = trialTimeString;

			// check to see if it's the last trial of the day or not
			if (ID.equals(PVTsamples.get(i + 1).getId())) { 

				// the directories that the raw driving csv datais being kept.
				// This data is not filtered and it does have the invalid
				// values.

				File directory = new File ("/Users/ehsanebk/OneDrive - drexel.edu/"
						+ "Driving data - standard deviation lateral position (Singapore)/"
						+ "Driving Data Raw/Protocol "+protocol+" driving data (csv)");
				//File directory = new File("/Users/Ehsan/Desktop/Protocol " + protocol + " driving data (csv)");
				// System.out.println(ID+ " time pvt : " + trialTime);

				for (File file : directory.listFiles())
					if (file.getName().startsWith(ID)) {

						Tokenizer t = new Tokenizer(file);
						t.readHeaderCSV(); // skiping the first line

						int invalidFrameCounter = 0;
						long lastValidFrameTime = 0;
						while (t.hasMoreTokens()) {
							String[] lineCSV = t.readNextLineCSV();
							// reading the csv file
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

							// getting the time in hhmm format
							int timeDrivingFrame = Utilities.toInt(Time.substring(0, 5).replace(":", "")); 

							// processing the frames and see if the invalid
							// frames between valid frames are more than 1
							// seconds
							if (timeDrivingFrame > addTime(trialTime, 5)
									&& timeDrivingFrame <= addTime(PVTsamples.get(i + 1).getTrialtime(), 5)) {
								
								boolean validFrame = (LeftRhoThetaOK == 1 && RightRhoThetaOK == 1 && LeftSigSpikeOK == 1
										&& RightSigSpikeOK == 1 && LaneWidthOK == 1 && isDriving == 1);

								DateFormat formatter = new SimpleDateFormat("hh:mm:ss:SSS");
								Date date = null;
								try {
									date = formatter.parse(Time);
								} catch (ParseException e) {
									e.printStackTrace();
								}

								
								if (validFrame && (invalidFrameCounter == 0)) {
									newsegment.numberOfFrames ++;
									newsegment.lanePos.add(LateralPosition);
									newsegment.timesOfFrames.add(date);
									lastValidFrameTime = date.getTime();
								} else if (validFrame && invalidFrameCounter > 0) {
									if (date.getTime() - lastValidFrameTime < validIntervals) {
										for (int j = 0; j < invalidFrameCounter; j++) {
											newsegment.lanePos.add(-100);
											newsegment.timesOfFrames.add(null);
											newsegment.numberOfFrames ++;
										}
										//adding the data from the current valid frame
										newsegment.numberOfFrames ++;
										newsegment.lanePos.add(LateralPosition);
										newsegment.timesOfFrames.add(date);
										
									} else {
										if (newsegment.valid()) {
											newsegment.lanePos =Utilities.lowpass(newsegment.lanePos ,.10); // smoothing the lane position
											newsegment.numberOfMinMax = MaxMinValues(newsegment.lanePos).size();
											newsegment.MinMixFrameNumbers = MaxMinValues(newsegment.lanePos);

											
											// computing average and maximum for distances in a segment
											if (newsegment.numberOfMinMax > 1){
												double aveDis = 0;
												long longestDis = 0;
												for (int j = 0; j < (newsegment.numberOfMinMax - 1); j++) {
													Date time1 =  newsegment.timesOfFrames.get(newsegment.MinMixFrameNumbers.get(j));
													Date time2 =  newsegment.timesOfFrames.get(newsegment.MinMixFrameNumbers.get(j + 1));
													long Dis = time2.getTime() - time1.getTime();
													aveDis += Dis;
													if (Dis > longestDis)
														longestDis = Dis;
												}
												newsegment.averageTimeBetweenMaxMin = aveDis / (MaxMinValues(newsegment.lanePos).size() - 1);
												newsegment.longestTimeBetweenMaxMin = longestDis;	
											}
											
											if (newsegment.numberOfMinMax == 1){
												long longestDis = 0;
												Date time1 =  newsegment.timesOfFrames.firstElement();
												Date time2 =  newsegment.timesOfFrames.get(newsegment.MinMixFrameNumbers.get(0));
												Date time3 =  newsegment.timesOfFrames.lastElement();
												longestDis = time2.getTime() - time1.getTime();
												if (time3.getTime() - time2.getTime() >longestDis )
													longestDis = time3.getTime() - time2.getTime();
												newsegment.longestTimeBetweenMaxMin = longestDis;
											}
											
											if (newsegment.numberOfMinMax == 0){
												long longestDis = 0;
												Date time1 =  newsegment.timesOfFrames.firstElement();
												Date time2 =  newsegment.timesOfFrames.lastElement();
												longestDis = time2.getTime() - time1.getTime();
												newsegment.longestTimeBetweenMaxMin = longestDis;
											}
											
											
											// computing the time of the segment
											newsegment.segmentTime = newsegment.timesOfFrames.lastElement().getTime() 
													- newsegment.timesOfFrames.firstElement().getTime();
											
											newSample.MinMaxSeries.add(newsegment.numberOfMinMax);
											
											if (newsegment.averageTimeBetweenMaxMin > 0)
												newSample.distanceBetweenMinMaxSeries.add(newsegment.averageTimeBetweenMaxMin);
											if (newsegment.longestTimeBetweenMaxMin > 0)
												newSample.longestDistanceBetweenMinMaxSeries.add(newsegment.longestTimeBetweenMaxMin);
											newSample.numberOfValidSegments ++ ;
											// new segments are added to the new sample
											newSample.segments.add(newsegment);
										}
										
										// being done with the segment and starting a new segment
										newsegment = new Segment(); 
										newsegment.startTime = timeDrivingFrame;
										//adding the data from the current valid frame
										newsegment.numberOfFrames ++;
										newsegment.lanePos.add(LateralPosition);
										newsegment.timesOfFrames.add(date);

									}
									// setting back the values
									invalidFrameCounter = 0;
									lastValidFrameTime = date.getTime();
								} else if (!validFrame)
									invalidFrameCounter++;
							}
							// break if the tame is after
							if (timeDrivingFrame > PVTsamples.get(i + 1).getTrialtime())
								break;
						}

					}
			}
			samples.add(newSample);
		}
	}

	// adding minutes to a time in the form of hhmm
	private static int addTime(int time, int add) {
		int m = (time % 100) + add;
		if (m >= 60) {
			return ((time / 100) + (m / 60)) * 100 + (m % 60);
		} else
			return time + add;
	}

	// This function finds the local Min and Max based on the valid intervals in
	// the LanePositions values which is an array
	// In the LanePos Values the invalid values have the value of -100
	private static List<Integer> MaxMinValues(Values LanePositions) {

		int window = 140;

		List<Integer> MaxMin = new ArrayList<Integer>();
		// the values which are not available are represented with -100
		int numberMax = 0;
		int numberMin = 0;
		boolean max;
		boolean min;

		// adding extra for capturing the end min and max
		for (int j = 0; j < 20; j++) {
			LanePositions.add(-100);
		}

		for (int i = (window / 2); i < LanePositions.size() - (window / 2); i++) {
			if (LanePositions.get(i) > -100) {
				// local max with the specified interval
				if ((LanePositions.get(i - (window / 2)) < LanePositions.get(i)
						|| LanePositions.get(i - (window / 2)) == -100)
						&& (LanePositions.get(i) > LanePositions.get(i + (window / 2))
								|| LanePositions.get(i + (window / 2)) == -100)) {
					max = true;
					for (int j = i - (window / 2); j <= i + (window / 2); j++) {
						if (LanePositions.get(j) > LanePositions.get(i) && LanePositions.get(j) > -100) {
							max = false;
							break;
						}
					}
					if (max) {
						MaxMin.add(i);
						numberMax++;
						i += (window / 2);
					}
				}
				// local min with the specified interval
				else if ((LanePositions.get(i - (window / 2)) > LanePositions.get(i)
						|| LanePositions.get(i - (window / 2)) == -100)
						&& (LanePositions.get(i) < LanePositions.get(i + (window / 2))
								|| LanePositions.get(i + (window / 2)) == -100)) {
					min = true;
					for (int j = i - (window / 2); j <= i + (window / 2); j++) {
						if (LanePositions.get(j) < LanePositions.get(i) && LanePositions.get(j) > -100) {
							min = false;
							break;
						}
					}
					if (min) {
						MaxMin.add(i);
						numberMin++;
						i += (window / 2);
					}
				}
			}
		}
		// return numberMax+numberMin;
		return MaxMin;
	}
}
