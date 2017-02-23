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
//	static File PVTfile = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
//			+ "Driving data - standard deviation lateral position (Singapore)/"
//			+ "/PVT Raw Data/MFPD_PVT_all.txt");
	static File PVTfile = new File("/Users/Ehsan/OneDrive - drexel.edu/"
			+ "Driving data - standard deviation lateral position (Singapore)/"
			+ "/PVT Raw Data/MFPD_PVT_all.txt");
	
	
	//reading previous processed data into the PVTprocessed
//	static File PVTfileProcessedOld = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
//			+ "Driving data - standard deviation lateral position (Singapore)/"
//			+ "SDLP_PVT Raw Data Analysis/MFPD_Driving_vs_PVT_processed.csv");
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

//		File LPfileOutLP_SegmentsA = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
//				+ "Driving data - standard deviation lateral position (Singapore)/"
//				+ "Driving Data Raw/Protocol A LP data.csv");
		File LPfileOutLP_SegmentsA = new File("/Users/Ehsan/Desktop/Protocol A LP Segments Data.csv");
		File LPfileOutLP_SeriesA = new File("/Users/Ehsan/Desktop/Protocol A LP Series Data.csv");
		PrintWriter foutLPSegmentsA = null;
		PrintWriter foutLPSeriesA = null;
		try {
			foutLPSegmentsA = new PrintWriter(LPfileOutLP_SegmentsA);
			foutLPSeriesA = new PrintWriter(LPfileOutLP_SeriesA);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		foutLPSegmentsA.println("Protocol A \n");

//		File PVTfileOutPutProcessedB = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
//				+ "Driving data - standard deviation lateral position (Singapore)/"
//				+ "Driving Data Raw/Protocol B LP data.csv");
		File LPfileOutLP_SegmentsB = new File("/Users/Ehsan/Desktop/Protocol B LP Segments.csv");
		File LPfileOutLP_SeriesB = new File("/Users/Ehsan/Desktop/Protocol B LP Series Data.csv");
		PrintWriter foutLPSegmentsB = null;
		PrintWriter foutLPSeriesB = null;
		try {
			foutLPSegmentsB = new PrintWriter(LPfileOutLP_SegmentsB);
			foutLPSeriesB = new PrintWriter(LPfileOutLP_SeriesB);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		foutLPSegmentsB.println("Protocol B \n");


		PrintWriter fout= null;
		PrintWriter foutSeries =null;
		
		//Analyzing the data based on the raw csv data and PVTsamples and make the samplesPL structure
		samplesLP.analysis(PVTsamples);


		for (int i = 0; i < samplesLP.size(); i++) {

			
			String protocol =samplesLP.get(i).protocol;
			String id = samplesLP.get(i).id;
			String trialtime = samplesLP.get(i).trialtime;
			System.out.println(trialtime);
			
			//writing the segments in two segments files
			if (protocol.equals("A"))
				fout = foutLPSegmentsA;
			else if (protocol.equals("B"))
				fout = foutLPSegmentsB;
			
			fout.print( "*"+ id + "*" + ",PVT time:," + trialtime+ "\n");
			fout.flush();

			for (int j = 0; j < samplesLP.get(i).segments.size(); j++) {

				int numberOfFrames =samplesLP.get(i).segments.elementAt(j).numberOfFrames;
				int numberOfMinMax = samplesLP.get(i).segments.elementAt(j).numberOfMinMax;
				int startTimeOfTheSegment  = samplesLP.get(i).segments.elementAt(j).startTime;
				
				fout.print(samplesLP.get(i).protocol +" #F: " + numberOfFrames+" #MN " + numberOfMinMax + " "+ "Time:" + startTimeOfTheSegment+
						samplesLP.get(i).segments.elementAt(j).MinMixFrameNumbers.toString().replaceAll(",", "") + ",");
				for (int l = 0; l < samplesLP.get(i).segments.elementAt(j).lanePos.size(); l++) {
					if (samplesLP.get(i).segments.elementAt(j).lanePos.get(l) > -100){
						fout.print(samplesLP.get(i).segments.elementAt(j).lanePos.get(l) + ",");
						fout.flush();
					}
					else{
						fout.print(",");
						fout.flush();
					}
				}
				fout.print("\n");
				fout.flush();
			}

			
			//writing the LP series
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
			}
			foutSeries.print("\n");
			
			
			//Adding the SampleLP to sample processed.
			//There were no driving after trial 5 PVT so we stop adding when trail is >5.
			if (Utilities.toInt(samplesLP.get(i).trial) < 5 )
				SamplesProcessed.addMinMaxAve(samplesLP.get(i));

		}

		foutLPSegmentsA.close();
		foutLPSegmentsB.close();	

		foutLPSeriesA.close();
		foutLPSeriesB.close();
		

		// outputing the processed file
//		File PVTfileOutPutProcessed = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
//				+ "Driving data - standard deviation lateral position (Singapore)/"
//				+ "SDLP_PVT Raw Data Analysis/MFPD_Driving_vs_PVT_processed_LP.csv");
		File PVTfileOutPutProcessed = new File("/Users/Ehsan/Desktop/TEST__MFPD_Driving_vs_PVT_processed_LP.csv");
		SamplesProcessed.writeToFile(PVTfileOutPutProcessed);

	}
}
