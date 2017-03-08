package VanDongen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

import analysis.Utilities;

public class Main {

	private static Vector<Data> datas;
	
	private static String[] bestCasesNum = {"3001","3025","3040","3086",
			"3206","3232","3256","3275","3386","3408",
			"3440","3574","3579","3620"};
	private static String[] worstCasesNum = {"3047",
			"3122","3171","3207","3215","3220",
			"3309","3311","3359","3421","3512","3570","3674"};

	public static void main(String[] args) {
				
		datas = new Vector<Data>();
		File directory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Data");

		for (File file : directory.listFiles()){
			if (file.getName().endsWith(".rpt")) {
				
				String ID = file.getName().substring(0,4);
				boolean newData = true;
				
				Data data=  new Data();	
				for (int i = 0; i < datas.size(); i++) {
					if (datas.get(i).ID.equals(ID)){
						data = datas.get(i);
						newData= false;
						break;
					}
				}
				
				data.sessions.add(new Session(file));
				
				if (newData){
					if (Utilities.arrayContains(worstCasesNum, ID))
						data.condition = "WorstCase";
					else
						data.condition = "BestCase";
					data.ID = ID;
					datas.add(data);
				}
			}
		}
		
		File rawDataDirectory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Raw Data flat");
		for (int i = 0; i < datas.size(); i++){
			Data data = datas.get(i);
			String id = data.ID;
			System.out.println(data.ID);
			for (int j = 0; j < data.sessions.size(); j++) {
				Session s  = data.sessions.get(j);
				String s_number = s.sessionNumber;
				System.out.println(s_number);
				for (File file : rawDataDirectory.listFiles()) {
					if (file.getName().startsWith("DRV") && file.getName().contains(id) && file.getName().contains("B"+ s_number)){
						System.out.println(file.getName());
						s.addRawData(file);
					}
				}		
			}
		}
		
		File output = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Results_Steer.csv");
		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < datas.size(); i++) {

			Data data = datas.get(i);
			outputCSV.println(data.ID);
			outputCSV.flush();
			System.out.println(data.ID);
			for (int j = 0; j < data.sessions.size(); j++) {
				outputCSV.println(","+data.sessions.get(j).sessionNumber );
				outputCSV.flush();
				for (int j2 = 0; j2 < data.sessions.get(j).straightSegment.size(); j2++) {
					for (int k = 0; k < data.sessions.get(j).straightSegment.get(j2).steer.size(); k++) {
						outputCSV.print(data.sessions.get(j).straightSegment.get(j2).steer.get(k)+",");
						outputCSV.flush();
					}
					outputCSV.print("\n");
					outputCSV.flush();
				}
			}
			outputCSV.close();
			
//			if ( data.sessions == 4 || session == 8 || session == 12 || session == 16 || session == 20 )
//				if (d.condition == "WorstCase")	
//					worstCase.elementAt(0).add(d.sample);
//				else
//					bestCase.elementAt(0).add(d.sample);
//			if ( session == 5 || session == 9 || session == 13 || session == 17 || session == 21 )
//				if (d.condition == "WorstCase")
//					worstCase.elementAt(1).add(d.sample);
//				else
//					bestCase.elementAt(1).add(d.sample);
//			if ( session == 6 || session == 10 || session == 14 || session == 18 || session == 22 )
//				if (d.condition == "WorstCase")
//					worstCase.elementAt(2).add(d.sample);
//				else
//					bestCase.elementAt(2).add(d.sample);
//			if ( session == 7 || session == 11 || session == 15 || session == 19 || session == 23 )
//				if (d.condition == "WorstCase")
//					worstCase.elementAt(3).add(d.sample);
//				else
//					bestCase.elementAt(3).add(d.sample);
//
//
//
//			if ( session == 24 || session == 28 || session == 32 || session == 36 || session == 40 )
//				if (d.condition == "WorstCase")
//					worstCase.elementAt(4).add(d.sample);
//				else
//					bestCase.elementAt(4).add(d.sample);
//			if ( session == 25 || session == 29 || session == 33 || session == 37 || session == 41 )
//				if (d.condition == "WorstCase")
//					worstCase.elementAt(5).add(d.sample);
//				else
//					bestCase.elementAt(5).add(d.sample);
//			if ( session == 26 || session == 30 || session == 34 || session == 38 || session == 42 )
//				if (d.condition == "WorstCase")
//					worstCase.elementAt(6).add(d.sample);
//				else
//					bestCase.elementAt(6).add(d.sample);
//			if ( session == 27 || session == 31 || session == 35 || session == 39 || session == 43 )
//				if (d.condition == "WorstCase")
//					worstCase.elementAt(7).add(d.sample);
//				else
//					bestCase.elementAt(7).add(d.sample);
		}


//		System.out.println("TimePoint" + "\t" + "SPEED_MIN" + "\t" + "SPEED_MAX" + "\t" + "SPEED_AVG" + 
//				"\t" + "SPEED_STD" + "\t" + "ACCEL_MIN" + "\t" + 
//				"ACCEL_MAX" + "\t" + "ACCEL_AVG" + "\t" + 
//				"ACCEL_STD" + "\t" + "STEER_MIN" + "\t" + "STEER_MAX" + 
//				"\t" + "STEER_AVG" + "\t" + "STEER_STD" + "\t" + 
//				"LANEDEV_MIN" + "\t" + "LANEDEV_MAX" + "\t" + "LANEDEV_AVG" + 
//				"\t" + "LANEDEV_STD" + "\t" + "BRAKEPDL_MIN" + "\t" + 
//				"BRAKEPDL_MAX" + "\t" + "ACCELPDL_MIN" + "\t" + "ACCELPDL_MAX" + 
//				"\t" + "BRAKEPDL_COUNT" + "\t" + "TTBRAKE00" + "\t" + 
//				"TTACCREL00" + "\t" + "TTACCREL01" + "\t" + "TTACCREL02" + "\t" + 
//				"MPG_AVG" + "\t" + "FUELUSED");
//
//		System.out.println("Best Case");
//		for (int i = 0; i < 8; i++) {
//			System.out.println( i + "\t" + bestCase.elementAt(i).getAverage().toString());	
//		}
//		
//		System.out.println("Worst Case");
//		for (int i = 0; i < 8; i++) {
//			System.out.println( i + "\t" + worstCase.elementAt(i).getAverage().toString());	
//		}
//		
//		System.out.println("-----------------");
//		
//		System.out.println(bestCase.elementAt(0).SPEED_AVG.size() );
		
	}
}
