package singapore;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import analysis.Tokenizer;
import analysis.Values;
import singapore.Driving_sessions.DrivingTrial;


public class Driving_all {

	Vector<Driving_sessions> subjects;

	public Driving_all() {

		subjects = new Vector<Driving_sessions>();
		
		// Reading the timing of the driving sessions from the files provided
		// 1- Protocol A session timings
		// 2- Protocol B session timings
		String[] protocols = {"A" , "B" };

		for (String protocol : protocols){

			File timing = new File ("/Users/Ehsan/OneDrive - drexel.edu/"
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

				// Timings:  15 min  1st_hour 2nd_hour 3rd_hour 4th_hour 5th_hour
				//			 0-1 	 2-3 	  4-5 	   6-7 	    8-9 	 10-11
				// ** if a time does not exist it will be NULL at the end
				Date[] timings = new Date[12]; 
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
			
			// Directories were the filtered ( valid part of the driving data) is kept 
			File directory = new File ("/Users/Ehsan/OneDrive - drexel.edu/"
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
			
			Tokenizer t = new Tokenizer(file);
			
			t.skipLine(); // skiping the first line
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
				
				String LaneWidth = lineCSV[8];
				String LaneCenter = lineCSV[9];
				double LateralPosition = Double.valueOf(lineCSV[10]).doubleValue();
			
				for (int i = 0; i < subject.trials.length; i=i+2) {
					Values lanePos =  new Values();
					if (subject.trials[i] != null && 
							time.after(subject.trials[i].startTime) && 
							time.before(subject.trials[i].stopTime)){
						lanePos.add(LateralPosition);
						subject.trials[i].LP_STD =  lanePos.stddev();
						//subject.trials[i].lanePos.add(LateralPosition);
						subject.trials[i].frameCount++;
					}
					else if (time.after(subject.trials[i].stopTime))
						break; //breaking the while loop
				}
			}
		}
	}

	public static void main(String[] args) {
		Driving_all test =  new Driving_all();
		for (int i = 0; i < test.subjects.size(); i++) {
			Driving_sessions s = test.subjects.get(i);
			System.out.println(s.id);
			for (int j = 0; j < s.trials.length; j++) {
				System.out.println(s.trials[j].startTime + "=="
						+ s.trials[j].stopTime + "||" + s.trials[j].LP_STD);	
			}
			System.out.println("--------------------------------------------------");
		}
	}
}
