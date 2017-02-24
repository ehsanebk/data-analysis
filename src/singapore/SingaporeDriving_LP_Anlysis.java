package singapore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import analysis.Utilities;
import singapore.SamplesLP.SampleLP;


public class SingaporeDriving_LP_Anlysis {

	public static void main(String[] args){

		Driving_vs_PVT_processed();
		//Test();
	}

	static Samples_Processed  SamplesProcessed;
	static SamplesLP samplesLP;

	// PVT data Raw file
	static File PVTfile = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
			+ "Driving data - standard deviation lateral position (Singapore)/"
			+ "PVT Raw Data/MFPD_PVT_all.txt");
//	static File PVTfile = new File("/Users/Ehsan/OneDrive - drexel.edu/"
//			+ "Driving data - standard deviation lateral position (Singapore)/"
//			+ "/PVT Raw Data/MFPD_PVT_all.txt");
	
	
	//reading previous processed data into the PVTprocessed
	static File PVTfileProcessedOld = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
			+ "Driving data - standard deviation lateral position (Singapore)/"
			+ "SDLP_PVT Raw Data Analysis/MFPD_Driving_vs_PVT_processed.csv");
//	static File PVTfileProcessedOld = new File("/Users/Ehsan/OneDrive - drexel.edu/"
//			+ "Driving data - standard deviation lateral position (Singapore)/"
//			+ "SDLP_PVT Raw Data Analysis/MFPD_Driving_vs_PVT_processed.csv");

	public static void 	Test() {
		
		SamplesProcessed= new Samples_Processed();
		samplesLP = new SamplesLP();
		
		SamplesProcessed.readProcessedFile(PVTfileProcessedOld);
		//		for (int j = 0; j < PVTprocessed.size(); j++) {
		//			System.out.println(PVTprocessed.get(j).toString());	
		//		}

		SamplesPVT PVTsamples = new SamplesPVT(PVTfile);
		
		samplesLP.analysis(PVTsamples);
		
	}


	public static void 	Driving_vs_PVT_processed() {

		SamplesProcessed= new Samples_Processed();
		samplesLP = new SamplesLP();
		
		//reading all the PVT data and add them to PVTsamples data structure
		SamplesPVT PVTsamples = new SamplesPVT(PVTfile);

		//reading the data available from other old files
		SamplesProcessed.readProcessedFile(PVTfileProcessedOld);

		File LPfileOutLP_SegmentsA = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol A LP Segments.csv");
		File LPfileOutLP_SeriesA = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol A LP Series.csv");
		File LPfileOutLPMinMaxDistance_SeriesA = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol A LP Distance Series.csv");
//		File LPfileOutLP_SegmentsA = new File("/Users/Ehsan/Desktop/Protocol A LP Segments Data.csv");
//		File LPfileOutLP_SeriesA = new File("/Users/Ehsan/Desktop/Protocol A LP Series Data.csv");
		
		PrintWriter foutLPSegmentsA = null;
		PrintWriter foutLPSeriesA = null;
		PrintWriter foutLPDistanceSeriesA = null;
		try {
			foutLPSegmentsA = new PrintWriter(LPfileOutLP_SegmentsA);
			foutLPSeriesA = new PrintWriter(LPfileOutLP_SeriesA);
			foutLPDistanceSeriesA = new PrintWriter(LPfileOutLPMinMaxDistance_SeriesA);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		foutLPSegmentsA.println("Protocol A \n");

		File LPfileOutLP_SegmentsB = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol B LP Segments.csv");
		File LPfileOutLP_SeriesB = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol B LP Series.csv");
		File LPfileOutLPMinMaxDistance_SeriesB = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol B LP Distance Series.csv");
		
//		File LPfileOutLP_SegmentsB = new File("/Users/Ehsan/Desktop/Protocol B LP Segments.csv");
//		File LPfileOutLP_SeriesB = new File("/Users/Ehsan/Desktop/Protocol B LP Series Data.csv");
		
		PrintWriter foutLPSegmentsB = null;
		PrintWriter foutLPSeriesB = null;
		PrintWriter foutLPDistanceSeriesB = null;
		try {
			foutLPSegmentsB = new PrintWriter(LPfileOutLP_SegmentsB);
			foutLPSeriesB = new PrintWriter(LPfileOutLP_SeriesB);
			foutLPDistanceSeriesB = new PrintWriter(LPfileOutLPMinMaxDistance_SeriesB);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		foutLPSegmentsB.println("Protocol B \n");


		PrintWriter foutSegments= null;
		PrintWriter foutSeries =null;
		PrintWriter foutDistanceSeries =null;
		
		//Analyzing the data based on the raw csv data and PVTsamples and make the samplesPL structure
		samplesLP.analysis(PVTsamples);


		for (int i = 0; i < samplesLP.size(); i++) {

			
			String protocol =samplesLP.get(i).protocol;
			String id = samplesLP.get(i).id;
			String trialtime = samplesLP.get(i).trialtime;
			//System.out.println(trialtime);
			
			//writing the segments in two segments files
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
				//System.out.println(numberOfFrames);
				
				foutSegments.print(samplesLP.get(i).protocol +" #F: " + numberOfFrames+" #MN " + numberOfMinMax + " "+ "Time:" + startTimeOfTheSegment+
						samplesLP.get(i).segments.elementAt(j).MinMixFrameNumbers.toString().replaceAll(",", "") + ",");
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
				foutDistanceSeries = foutLPDistanceSeriesA;
			else if (protocol.equals("B"))
				foutDistanceSeries = foutLPDistanceSeriesB;
		
			foutDistanceSeries.print( "*"+ id + "*" + " PVT time: " + trialtime);
			foutDistanceSeries.flush();
			for (int j = 0; j < samplesLP.get(i).distanceBetweenMinMaxSeries.size(); j++) {
				if (samplesLP.get(i).distanceBetweenMinMaxSeries.get(j)> 0)
					foutDistanceSeries.print("," +samplesLP.get(i).distanceBetweenMinMaxSeries.get(j) );
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
		File OutPutProcessed = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Driving_vs_PVT_vs_LP_processed.csv");
		
		//File OutPutProcessed = new File("/Users/Ehsan/Desktop/Driving_vs_PVT_vs_LP_processed.csv");
		SamplesProcessed.writeToFile(OutPutProcessed);

	}
}
