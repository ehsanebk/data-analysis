package singapore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import singapore.SamplesLP.SampleLP;


public class SingaporeDriving_LP_Anlysis {

	public static void main(String[] args){

		Driving_vs_PVT_processed();
		//Test();
	}

	static Sample_Processed  PVTprocessed= new Sample_Processed();
	static SamplesLP samplesLP = new SamplesLP();

	// PVT data Raw file
	static File PVTfile = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
			+ "Driving data - standard deviation lateral position (Singapore)/"
			+ "/PVT Raw Data/MFPD_PVT_all.txt");

	//reading previous processed data into the PVTprocessed
	static File PVTfileProcessedOld = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
			+ "Driving data - standard deviation lateral position (Singapore)/"
			+ "SDLP_PVT Raw Data Analysis/MFPD_Driving_vs_PVT_processed.csv");

	public static void 	Test() {
		//reading previous processed data into the PVTprocessed
		File PVTfileProcessedOld = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "SDLP_PVT Raw Data Analysis/MFPD_Driving_vs_PVT_processed.csv");
		PVTprocessed.readProcessedFile(PVTfileProcessedOld);
		//		for (int j = 0; j < PVTprocessed.size(); j++) {
		//			System.out.println(PVTprocessed.get(j).toString());	
		//		}

		SamplesPVT PVTsamples = new SamplesPVT(PVTfile);
		
		samplesLP.analysis(PVTsamples);
		
	}


	public static void 	Driving_vs_PVT_processed() {


		SamplesPVT PVTsamples = new SamplesPVT(PVTfile);


		PVTprocessed.readProcessedFile(PVTfileProcessedOld);

		File PVTfileOutPutProcessedA = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol A LP data.csv");
		PrintWriter foutA = null;
		try {
			foutA = new PrintWriter(PVTfileOutPutProcessedA);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		foutA.println("Protocol A \n");

		File PVTfileOutPutProcessedB = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "Driving Data Raw/Protocol B LP data.csv");
		PrintWriter foutB = null;
		try {
			foutB = new PrintWriter(PVTfileOutPutProcessedB);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		foutB.println("Protocol B \n");


		PrintWriter fout= null;

		samplesLP.analysis(PVTsamples);


		for (int i = 0; i < samplesLP.size(); i++) {

			//writing the segments in two segments files
			String protocol =samplesLP.get(i).protocol;
			if (protocol.equals("A"))
				fout = foutA;
			else if (protocol.equals("B"))
				fout = foutB;

			String id = samplesLP.get(i).id;
			String trialtime = samplesLP.get(i).trialtime;
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

			//adding the SampleLP to sample processed
			PVTprocessed.addMinMaxAve(samplesLP.get(i));

		}

		foutA.close();
		foutB.close();	




		// outputing the processed file
		File PVTfileOutPutProcessed = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "SDLP_PVT Raw Data Analysis/MFPD_Driving_vs_PVT_processed_LP.csv");
		PVTprocessed.writeToFile(PVTfileOutPutProcessed);

	}
}
