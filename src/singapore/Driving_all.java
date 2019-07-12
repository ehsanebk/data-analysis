package singapore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import analysis.Tokenizer;
import analysis.Values;
import singapore.Driving_sessions.DrivingTrial;


public class Driving_all {

	Vector<Driving_sessions> subjects;

	public static String[] completedA ={"520","521","522","525","526","529","531",
			"533","536","538","540","541","542","544","545","546","552","555","556","564"};
	public static String[] completedB ={"508","520","525","526","528","529","530",
			"531","532","540","541","542","545","556","561","564"};

	public static void main(String[] args) {
		Driving_all test =  new Driving_all();
		//		for (int i = 0; i < test.subjects.size(); i++) {
		//			Driving_sessions s = test.subjects.get(i);
		//			System.out.println(s.id);
		//			for (int j = 0; j < s.trials.length; j++) {
		//				System.out.println(s.trials[j].startTime + "=="
		//						+ s.trials[j].stopTime + "||" + s.trials[j].LP_STD + " count: " + s.trials[j].frameCount);	
		//			}
		//			System.out.println("--------------------------------------------------");
		//		}

		File file = new File("/Users/Ehsan/OneDrive - Drexel University/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "/Result/LP_PVT_all.csv");
		try {
			test.writeToFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Driving_all() {

		subjects = new Vector<Driving_sessions>();

		// Reading the timing of the driving sessions from the files provided
		// 1- Protocol A session timings
		// 2- Protocol B session timings
		String[] protocols = {"A" , "B" };

		for (String protocol : protocols){

			File timing = new File ("/Users/Ehsan/OneDrive - Drexel University/"
					+ "Driving data - standard deviation lateral position (Singapore)/"
					+ "Protocol " + protocol + " session timings.csv");

			Tokenizer t = new Tokenizer(timing);
			t.skipLine(); // skiping the first line

			SimpleDateFormat dateParser1 = new SimpleDateFormat ("dd-MMM-yyHH:mm:ss"); // 15-May-11 13:05:03

			while (t.hasMoreTokens()){
				String[] lineCSV = t.readNextLineCSV();
				//reading the tokens
				String ID = lineCSV[0];
				if (ID.equals("")) // break in case it's an empty line
					break;

				String Date_of_Trial = lineCSV[1];
				String Trial_Type_A_B = lineCSV[2];

				// Timings:  15 min  1st_hour 2nd_hour 3rd_hour 4th_hour
				//			 0-1 	 2-3 	  4-5 	   6-7 	    8-9 	
				// ** if a time does not exist it will be NULL at the end
				Date[] timings = new Date[10]; 
				for (int j = 0; j < timings.length; j++) {
					timings [j] = null; //**
					try {
						timings[j] = dateParser1.parse(Date_of_Trial+lineCSV[j+3]);
					} catch (Exception e) {	
						// We leave the timing to be null in case of exceptions
					}
				}

				// making a new driving_sessions data for the line read
				Driving_sessions subject =  new Driving_sessions();
				subject.id = ID;
				subject.protocol = Trial_Type_A_B;
				for (int j = 0; j < timings.length; j= j+2) {
					DrivingTrial trial = subject.new DrivingTrial();
					if (timings[j]!=null){
						trial.startTime = timings[j];
						trial.stopTime =  timings[j+1];
					}
					subject.trials[j/2] = trial;
				}

				subjects.add(subject);
				//System.out.println(subject.toStringTiming());
			}
		}

		// getting the driving data form the files in filtered directory 
		// based on the data gathered from the timings

		for (Driving_sessions subject : subjects){
//			if (!subject.id.equals("523")) {
//				continue;
//			}
			System.out.println("Processing Driving for Subject : " + subject.id);
			// Directories were the filtered ( valid part of the driving data) is kept 
			File directory = new File ("/Users/Ehsan/OneDrive - Drexel University/"
					+ "Driving data - standard deviation lateral position (Singapore)/"
					+ "Driving Data Raw/Protocol "+ subject.protocol+" filtered");

			File file= null;

			//  finding the right file in the directory and save it as file variable
			for (File f : directory.listFiles()){
				if (f.getName().startsWith(subject.id)){
					file = f;
					break;
				}
			}

			SimpleDateFormat dateParser2 = new SimpleDateFormat ("yyyyMMddHH:mm:ss:SSS"); // 2011051514:55:15:627

			// For saving memory and not to store all the numbers of LP
			Values[] lanePos = new Values[5];
			for (int i = 0; i < lanePos.length; i++) {
				lanePos[i]= new Values();
			}

			Tokenizer t = new Tokenizer(file);
			t.skipLine(); // Skipping the first line
			while (t.hasMoreTokens()){
				String[] lineCSV = t.readNextLineCSV();
				//reading the tokens
				String Video = lineCSV[0];
				Date time = null;
				try {
					time = dateParser2.parse(Video.substring(0, 8)+lineCSV[1]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//				if (time.after(subject.trials[1].startTime) && 
				//						time.before(subject.trials[1].stopTime))
				//					System.out.println("in"); 
				//				
				//				System.out.println(time + "  " + subject.trials[1].startTime +"––––" + subject.trials[1].stopTime);
				String LaneWidth = lineCSV[8];
				String LaneCenter = lineCSV[9];
				double LateralPosition = Double.valueOf(lineCSV[10]).doubleValue();

				for (int i = 0; i < subject.trials.length; i++) {
					if (subject.trials[i].startTime != null && 
							time.after(subject.trials[i].startTime) && 
							time.before(subject.trials[i].stopTime)){

						lanePos[i].add(LateralPosition);
						//subject.trials[i].lanePos.add(LateralPosition);
						subject.trials[i].frameCount++;
						break;
					}
				}
				// getting the LP standard deviations
				for (int i = 0; i < lanePos.length; i++) {
					subject.trials[i].LP_STD =  lanePos[i].stddev();
				}
			}
		}
	}

	public Driving_sessions getByID(String id){
		for (Driving_sessions subject : subjects){
			if (subject.id.equals(id))
				return subject;
		}
		return null;
	}

	public void writeToFile (File file) throws Exception{
		File PVTfileOutPutProcessed = file;
		PrintWriter foutPVT = null;
		try {
			foutPVT = new PrintWriter(PVTfileOutPutProcessed);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		foutPVT.println("protocol,id,"
				+ ",PVT 1,,,,PVT 2,,,,PVT 3,,,,PVT 4,,,,PVT 5,,,,PVT 6,,,,PVT 7,,,,"
				+ ",Driving 1,,,Driving 2,,,Driving 3,,,Driving 4,,,"
				);
		foutPVT.flush();

		foutPVT.println(",,"
				+ ",Time,% Lapse,% FalseStarts, Median Alert RT,Time,% Lapse,% FalseStarts, Median Alert RT"
				+ ",Time,% Lapse,% FalseStarts, Median Alert RT,Time,% Lapse,% FalseStarts, Median Alert RT"
				+ ",Time,% Lapse,% FalseStarts, Median Alert RT,Time,% Lapse,% FalseStarts, Median Alert RT"
				+ ",Time,% Lapse,% FalseStarts, Median Alert RT,"
				+ ",Start-Stop,LPSD,Frame Count,Start-Stop,LPSD,Frame Count,"
				+ "Start-Stop,LPSD,Frame Count,Start-Stop,LPSD,Frame Count"
				);
		foutPVT.flush();

		// Getting the PVT data ready
		File PVTfile = new File("/Users/Ehsan/OneDrive - Drexel University/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "PVT Raw Data/MFPD_PVT_all.txt");
		Process_PVT singaporePVT = new Process_PVT(PVTfile);


		for (int i = 0; i < subjects.size(); i++) {

			Driving_sessions driving = subjects.get(i);
			PVT_sessions pvt = singaporePVT.get(driving.id, driving.protocol);
			foutPVT.print(driving.protocol+","+driving.id+"," +pvt.sessions.get(0).getTrialdate() +",");
			for (int j = 1; j <= 7  ; j++) {
				if (pvt.getTrail(j) != null)
					foutPVT.print(pvt.getTrail(j).getTrialtime() +","
							+ pvt.getTrail(j).getProportionOfLapses()*100 + ","
							+ pvt.getTrail(j).getProportionOfFalseStarts()*100 + ","
							+ pvt.getTrail(j).getSessionMedianAlertResponses() + ",") ; // percentage of lapses
				else
					foutPVT.print(","+ ",,,") ;
			}
			foutPVT.flush();
			for (int j = 1; j < driving.trials.length; j++) {
				foutPVT.print(","+ driving.trials[j].getStartTime() + "-" +driving.trials[j].getStopTime() 
						+"," + driving.trials[j].LP_STD
						+"," + driving.trials[j].frameCount ) ;
			}
			foutPVT.print("\n");
			foutPVT.flush();
		}
		foutPVT.flush();
		foutPVT.close();	
	}
}
