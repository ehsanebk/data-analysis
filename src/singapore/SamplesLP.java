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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import analysis.Tokenizer;
import analysis.Values;
import analysis.Utilities;

/**
 * @author ehsanebk
 *
 */
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

	public void analysis(Process_PVT process_PVT) {

		// adding driving data
		for (int i = 0; i < process_PVT.size() - 1; i++) {

			String protocol = process_PVT.get(i).getProtocol();
			String ID = process_PVT.get(i).getId();
			String trial = process_PVT.get(i).getTrial();
			String trialDate = process_PVT.get(i).getTrialdate();
			String trialTimeString = process_PVT.get(i).getTrialtimeString();
			int trialTime = process_PVT.get(i).getTrialtime();


			Segment newsegment = new Segment(); 
			SampleLP newSample = new SampleLP();
			newSample.id = ID;
			newSample.protocol = protocol;
			newSample.trial = trial;
			newSample.trialdate = trialDate;
			newSample.trialtime = trialTimeString;

			// check to see if it's the last trial of the day or not
			if (ID.equals(process_PVT.get(i + 1).getId())) { 

				// the directories that the raw driving csv datais being kept.
				// This data is not filtered and it does have the invalid
				// values.

				File directory = new File ("/Users/Ehsan/OneDrive - drexel.edu/"
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
									&& timeDrivingFrame <= addTime(process_PVT.get(i + 1).getTrialtime(), 5)) {
								
								boolean validFrame = (LeftRhoThetaOK == 1 && RightRhoThetaOK == 1 && LeftSigSpikeOK == 1
										&& RightSigSpikeOK == 1 && LaneWidthOK == 1 && isDriving == 1);

								SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
								Date date=null;
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
											newsegment.lanePos = Utilities.lowpass(newsegment.lanePos ,.09); // smoothing the lane position
											newsegment.numberOfMinMax = Utilities.MaxMinValues(newsegment.lanePos, 100).size();
											newsegment.MinMixFrameNumbers = Utilities.MaxMinValues(newsegment.lanePos, 100);

											
											// computing average and maximum for distances in a segment
											if (newsegment.numberOfMinMax > 1){
												double aveDis = 0;
												long longestDis = 0;
												Date time1 =  newsegment.timesOfFrames.firstElement();
												Date time2 = newsegment.timesOfFrames.get(newsegment.MinMixFrameNumbers.get(0));
												long Dis = time2.getTime() - time1.getTime();
												aveDis += Dis;
												if ( Dis > longestDis )
													longestDis = Dis;
												
												for (int j = 0; j < (newsegment.numberOfMinMax - 1); j++) {
													time1 =  newsegment.timesOfFrames.get(newsegment.MinMixFrameNumbers.get(j));
													time2 =  newsegment.timesOfFrames.get(newsegment.MinMixFrameNumbers.get(j + 1));
													Dis = time2.getTime() - time1.getTime();
													aveDis += Dis;
													if (Dis > longestDis)
														longestDis = Dis;
												}
												time1 =  newsegment.timesOfFrames.get(newsegment.MinMixFrameNumbers.get(newsegment.numberOfMinMax-1));
												time2 =  newsegment.timesOfFrames.lastElement();
												Dis = time2.getTime() - time1.getTime();
												aveDis += Dis;
												if (Dis > longestDis)
													longestDis = Dis;
												
												newsegment.averageTimeBetweenMaxMin = aveDis / (newsegment.numberOfMinMax);
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
							if (timeDrivingFrame > process_PVT.get(i + 1).getTrialtime())
								break;
						}

					}
			}
			samples.add(newSample);
		}
	}

	/**
	 * adding minutes to a time in the form of hhmm
	 * 
	 * @param time
	 * @param add
	 * @return
	 */
	private static int addTime(int time, int add) {
		int m = (time % 100) + add;
		if (m >= 60) {
			return ((time / 100) + (m / 60)) * 100 + (m % 60);
		} else
			return time + add;
	}

}
