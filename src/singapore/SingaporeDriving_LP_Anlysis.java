package singapore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import analysis.Utilities;

/**
 * @author ehsanebk
 *
 */

public class SingaporeDriving_LP_Anlysis {

	public static void main(String[] args){

		Driving_vs_PVT_processed();
		//Test();
	}

	public static String[] completedA ={"520","521","522","525","526","529","531","533","536","538","540","541","542","544","545","546","552","555","556","564"};
	public static String[] completedB ={"508","520","525","526","528","529","530","531","532","540","541","542","545","556","561","564"};
			
	static Samples_Processed  SamplesProcessed;
	static SamplesLP samplesLP;

	// PVT data Raw file
	static File PVTfile = new File("/Users/Ehsan/OneDrive - drexel.edu/"
			+ "Driving data - standard deviation lateral position (Singapore)/"
			+ "PVT Raw Data/MFPD_PVT_all.txt");
	
	//reading previous processed data into the PVTprocessed
	static File PVTfileProcessedOld = new File("/Users/Ehsan/OneDrive - drexel.edu/"
			+ "Driving data - standard deviation lateral position (Singapore)/"
			+ "SDLP_PVT Raw Data Analysis/MFPD_Driving_vs_PVT_processed.csv");

	public static void 	Test() {
		
		SamplesProcessed= new Samples_Processed();
		samplesLP = new SamplesLP();
		
		SamplesProcessed.readProcessedFile(PVTfileProcessedOld);
		//		for (int j = 0; j < PVTprocessed.size(); j++) {
		//			System.out.println(PVTprocessed.get(j).toString());	
		//		}

		PVT_all PVTsamples = new PVT_all(PVTfile);
		
		samplesLP.analysis(PVTsamples);
		
	}

	public static void 	Driving_vs_PVT_processed() {

		SamplesProcessed= new Samples_Processed();
		samplesLP = new SamplesLP();
		
		//reading all the PVT data and add them to PVTsamples data structure
		PVT_all pVT_all = new PVT_all(PVTfile);

		//reading the data available from other old files
		SamplesProcessed.readProcessedFile(PVTfileProcessedOld);

		File LPfileOutLP_SegmentsA = new File("/Users/Ehsan/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol A LP Segments.csv");
		File LPfileOutLP_SeriesA = new File("/Users/Ehsan/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol A LP Series.csv");
		File LPfileOutLPMinMaxDistance_SeriesA = new File("/Users/Ehsan/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol A LP Longest Distance Series.csv");
	
		PrintWriter foutLPSegmentsA = null;
		PrintWriter foutLPSeriesA = null;
		PrintWriter foutLPLongestDistanceSeriesA = null;
		try {
			foutLPSegmentsA = new PrintWriter(LPfileOutLP_SegmentsA);
			foutLPSeriesA = new PrintWriter(LPfileOutLP_SeriesA);
			foutLPLongestDistanceSeriesA = new PrintWriter(LPfileOutLPMinMaxDistance_SeriesA);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		foutLPSegmentsA.println("Protocol A \n");
		foutLPSegmentsA.flush();
		foutLPSeriesA.println("Protocol A \n");
		foutLPSeriesA.flush();
		foutLPLongestDistanceSeriesA.println("Protocol A \n");
		foutLPLongestDistanceSeriesA.flush();

		File LPfileOutLP_SegmentsB = new File("/Users/Ehsan/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol B LP Segments.csv");
		File LPfileOutLP_SeriesB = new File("/Users/Ehsan/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol B LP Series.csv");
		File LPfileOutLPMinMaxDistance_SeriesB = new File("/Users/Ehsan/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol B LP Longest Distance Series.csv");
		
		PrintWriter foutLPSegmentsB = null;
		PrintWriter foutLPSeriesB = null;
		PrintWriter foutLPLongestDistanceSeriesB = null;
		try {
			foutLPSegmentsB = new PrintWriter(LPfileOutLP_SegmentsB);
			foutLPSeriesB = new PrintWriter(LPfileOutLP_SeriesB);
			foutLPLongestDistanceSeriesB = new PrintWriter(LPfileOutLPMinMaxDistance_SeriesB);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		foutLPSegmentsB.println("Protocol B \n");
		foutLPSegmentsB.flush();
		foutLPSeriesB.println("Protocol B \n");
		foutLPSeriesB.flush();
		foutLPLongestDistanceSeriesB.println("Protocol B \n");
		foutLPLongestDistanceSeriesB.flush();
		

		PrintWriter foutSegments= null;
		PrintWriter foutSeries =null;
		PrintWriter foutDistanceSeries =null;
		
		//Analyzing the data based on the raw csv data and PVTsamples and make the samplesPL structure
		samplesLP.analysis(pVT_all);


		for (int i = 0; i < samplesLP.size(); i++) {

			
			String protocol =samplesLP.get(i).protocol;
			String id = samplesLP.get(i).id;
			String trialtime = samplesLP.get(i).trialtime;
			//System.out.println(trialtime);
			

//			// to check if the trial is a completed trial
//			if (!(protocol.equals("A") && Arrays.asList(SingaporeDriving_LP_Anlysis.completedA).contains(id)) || 
//					!(protocol.equals("B") && Arrays.asList(SingaporeDriving_LP_Anlysis.completedB).contains(id)))
//				continue;
			
			//writing the segments in to two segments files
			if (protocol.equals("A"))
				foutSegments = foutLPSegmentsA;
			else if (protocol.equals("B"))
				foutSegments = foutLPSegmentsB;
			
			foutSegments.print( "*"+ id + "*" + ",PVT time:," + trialtime+ "\n");
			foutSegments.flush();

			for (int j = 0; j < samplesLP.get(i).segments.size(); j++) {

				int numberOfFrames =samplesLP.get(i).segments.elementAt(j).numberOfFrames;
				int numberOfMinMax = samplesLP.get(i).segments.elementAt(j).numberOfMinMax;
				int startTimeOfTheSegment  = samplesLP.get(i).segments.elementAt(j).startTime;
				long totalTimeOfTheSegment =  samplesLP.get(i).segments.elementAt(j).segmentTime;
				
				foutSegments.print(j + "-" +samplesLP.get(i).protocol +" #F: " + numberOfFrames+" #MN " + numberOfMinMax + " "
						+ "StartTime:" + startTimeOfTheSegment + " " + "Total(ms):" + totalTimeOfTheSegment + " "
						+ samplesLP.get(i).segments.elementAt(j).MinMixFrameNumbers.toString().replaceAll(",", "") + ",");
				foutSegments.flush();
				
				
				for (int l = 0; l < samplesLP.get(i).segments.elementAt(j).lanePos.size(); l++) {
					if (samplesLP.get(i).segments.elementAt(j).lanePos.get(l) > -100){
						foutSegments.print(samplesLP.get(i).segments.elementAt(j).lanePos.get(l) + ",");
						foutSegments.flush();
					}
					else{
						foutSegments.print(",");
						foutSegments.flush();
					}
				}
				foutSegments.print("\n");
				foutSegments.flush();
				
				// checking the timing
				foutSegments.print(j + "-" +samplesLP.get(i).protocol +" #F: " + numberOfFrames+" #MN " + numberOfMinMax 
						+ " "+ "Time:" + startTimeOfTheSegment+
						" Times,");
				foutSegments.flush();

				for (int l = 0; l < samplesLP.get(i).segments.elementAt(j).timesOfFrames.size(); l++) {
					if (samplesLP.get(i).segments.elementAt(j).timesOfFrames.get(l) != null){
						foutSegments.print(samplesLP.get(i).segments.elementAt(j).timesOfFrames.get(l).getTime() + ",");
						foutSegments.flush();
					}
					else{
						foutSegments.print(",");
						foutSegments.flush();
					}
				}
				foutSegments.print("\n");
				foutSegments.flush();
				
				for (int l = 0; l < samplesLP.get(i).segments.elementAt(j).timesOfFrames.size(); l++) {
					if (samplesLP.get(i).segments.elementAt(j).timesOfFrames.get(l) != null){
						foutSegments.print(samplesLP.get(i).segments.elementAt(j).timesOfFrames.get(l) + ",");
						foutSegments.flush();
					}
					else{
						foutSegments.print(",");
						foutSegments.flush();
					}
				}
				foutSegments.print("\n");
				foutSegments.flush();
				
				
				// For testing
				// writing low pass filter for the segments with alpha = 0.09
//				foutSegments.print(j + "-" +samplesLP.get(i).protocol +" #F: " + numberOfFrames+" #MN " + numberOfMinMax + " "+ "Time:" + startTimeOfTheSegment+
//						samplesLP.get(i).segments.elementAt(j).MinMixFrameNumbers.toString().replaceAll(",", "") + ",");
//				foutSegments.flush();
//				
//				Values lowpass = Utilities.lowpass(samplesLP.get(i).segments.elementAt(j).lanePos ,.09); // with alpha = 0.1
//				for (int l = 0; l < lowpass.size(); l++) {
//					if (lowpass.get(l) > -100){
//						foutSegments.print(lowpass.get(l) + ",");
//						foutSegments.flush();
//					}
//					else{
//						foutSegments.print(",");
//						foutSegments.flush();
//					}
//				}
//				foutSegments.print("\n");
//				foutSegments.flush();
				//////
				
			}
			
			
			/////writing the LP series ///////
			if (protocol.equals("A"))
				foutSeries = foutLPSeriesA;
			else if (protocol.equals("B"))
				foutSeries = foutLPSeriesB;
		
			foutSeries.print( "*"+ id + "*" + " PVT time: " + trialtime);
			foutSeries.flush();
			
			for (int j = 0; j < samplesLP.get(i).MinMaxSeries.size(); j++) {
				if (samplesLP.get(i).MinMaxSeries.get(j)> 0)
					foutSeries.print("," +samplesLP.get(i).MinMaxSeries.get(j) );
				else
					foutSeries.print(",");
				foutSeries.flush();
			}
			foutSeries.print("\n");
			foutSeries.flush();
			
			////writing the Distance series ///////////
			if (protocol.equals("A"))
				foutDistanceSeries = foutLPLongestDistanceSeriesA;
			else if (protocol.equals("B"))
				foutDistanceSeries = foutLPLongestDistanceSeriesB;
		
			foutDistanceSeries.print( "*"+ id + "*" + " PVT time: " + trialtime);
			foutDistanceSeries.flush();
			for (int j = 0; j < samplesLP.get(i).longestDistanceBetweenMinMaxSeries.size(); j++) {
				if (samplesLP.get(i).longestDistanceBetweenMinMaxSeries.get(j)> 0)
					foutDistanceSeries.print("," +samplesLP.get(i).longestDistanceBetweenMinMaxSeries.get(j) );
				else
					foutDistanceSeries.print(",");
				foutDistanceSeries.flush();
			}
			foutDistanceSeries.print("\n");
			foutDistanceSeries.flush();
			
			//Adding the SampleLP to sample processed.
			//There were no driving after trial 5 PVT so we stop adding when trail is >5.
			if (Utilities.toInt(samplesLP.get(i).trial) < 5 )
				SamplesProcessed.addMinMaxAve(samplesLP.get(i));
			
		}
		foutSegments.close();
		foutSeries.close();
		foutDistanceSeries.close();

		// outputing the processed file
		File OutPutProcessed = new File("/Users/Ehsan/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Driving_vs_PVT_vs_LP_processed.csv");
		
		//File OutPutProcessed = new File("/Users/Ehsan/Desktop/Driving_vs_PVT_vs_LP_processed.csv");
		SamplesProcessed.writeToFile(OutPutProcessed);

	}
}
