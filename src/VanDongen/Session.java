package VanDongen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;
import analysis.Tokenizer;
import analysis.Utilities;
import analysis.Values;

public class Session {

	String ID;
	String sessionNumber;
	Vector<StraightSegment> straightSegment;
	
	
	Session(File file){
		straightSegment = new Vector<StraightSegment>();
		ID = file.getName().substring(0,4);
		sessionNumber  = file.getName().substring(5, 7);
		
		StraightSegment segment;
		
		Tokenizer t = new Tokenizer(file);
		t.skipLines(4);
		String MetricName;
		while (t.hasMoreTokens()) {			
			t.nextToken();t.nextToken();	
			String label = t.nextToken();
				
			if (label.contains("STRAIGHT")){
				
				segment= new StraightSegment();
				boolean newSegment = true;
				
				for (int i = 0; i < straightSegment.size(); i++) {
					if (straightSegment.get(i).label.equals(label)){
						segment = straightSegment.get(i);
						newSegment =false;
						break;
					}
				}		
				
				int frameStart = t.nextInt();
				int frameStop = t.nextInt();
				double timeStart = t.nextDouble();
				double timeStop = t.nextDouble();
				
				MetricName = t.nextToken();
				switch (MetricName) {
				case "SPEED_MIN" :
					segment.SPEED_MIN = t.nextDouble();
					break;
				case "SPEED_MAX" :
					segment.SPEED_MAX = t.nextDouble();
					break;
				case "SPEED_AVG" :
					segment.SPEED_AVG = t.nextDouble();
					break;
				case "SPEED_STD" :
					segment.SPEED_STD = t.nextDouble();
					break;
				case "ACCEL_MIN" :
					segment.ACCEL_MIN = t.nextDouble();
					break;
				case "ACCEL_MAX" :
					segment.ACCEL_MAX = t.nextDouble();
					break;
				case "ACCEL_AVG" :
					segment.ACCEL_AVG = t.nextDouble();
					break;
				case "ACCEL_STD" :
					segment.ACCEL_STD = t.nextDouble();
					break;
				case "STEER_MIN" :
					segment.STEER_MIN = t.nextDouble();
					break;
				case "STEER_MAX" :
					segment.STEER_MAX = t.nextDouble();
					break;
				case "STEER_AVG" :
					segment.STEER_AVG = t.nextDouble();
					break;
				case "STEER_STD" :
					segment.STEER_STD = t.nextDouble();
					break;
				case "LANEDEV_MIN" :
					segment.LANEDEV_MIN = t.nextDouble();
					break;
				case "LANEDEV_MAX" :
					segment.LANEDEV_MAX = t.nextDouble();
					break;
				case "LANEDEV_AVG" :
					segment.LANEDEV_AVG = t.nextDouble();
					break;
				case "LANEDEV_STD" :
					segment.LANEDEV_STD = t.nextDouble();
					break;
				case "BRAKEPDL_MIN" :
					segment.BRAKEPDL_MIN = t.nextDouble();
					break;
				case "BRAKEPDL_MAX" :
					segment.BRAKEPDL_MAX = t.nextDouble();
					break;
				case "ACCELPDL_MIN" :
					segment.ACCELPDL_MIN = t.nextDouble();
					break;
				case "ACCELPDL_MAX" :
					segment.ACCELPDL_MAX = t.nextDouble();
					break;
				case "BRAKEPDL_COUNT" :
					segment.BRAKEPDL_COUNT = t.nextDouble();
					break;
				case "TTBRAKE00" :
					segment.TTBRAKE00 = t.nextDouble();
					break;
				case "TTACCREL00" :
					segment.TTACCREL00 = t.nextDouble();
					break;
				case "MPG_AVG" :
					segment.MPG_AVG = t.nextDouble();
					break;
				case "FUELUSED" :
					segment.FUELUSED = t.nextDouble();
					break;

				default:
					break;
				}
				
				if (newSegment) {
					segment.label = label;
					segment.frameStart = frameStart;
					segment.frameStop = frameStop;
					segment.timeStart = timeStart;
					segment.timeStop = timeStop;
					
					straightSegment.add(segment);
				}
				
			}
			else
				t.skipLine();
		}	
	}
	
	void addRawData(File file){
		Tokenizer t = new Tokenizer(file);
		t.skipLines(10);
		int frame;
		double X, Y, Z, P, R, H, steer, accel, brake, MPH, D_spd, elapsed, gear, cltch, rpm;
		
		while (t.hasMoreTokens()) {
			String[] line = t.readNextLine().split("\\s+");
			frame = Utilities.toInt(line[0]);
			for (int i = 0; i < straightSegment.size(); i++) {
				if (frame >= straightSegment.get(i).frameStart && frame <= straightSegment.get(i).frameStop){
					steer = Utilities.toDouble(line[7]);
					MPH = Utilities.toDouble(line[10]);
					straightSegment.get(i).steer.add(steer);
					//straightSegment.get(i).MPH.add(MPH);
				}				
			}
		}
		
		File directory = new File("/Users/ehsanebk/"
				+ "OneDrive - drexel.edu/Driving Data(Van Dongen)/StraightSegmentsValues/" + ID );
		
		if (!directory.exists())
			directory.mkdirs();
		
		File sessionFile = new File(directory.getPath() +"/" + sessionNumber + ".csv");
		
		PrintWriter output = null;
		try {
			output = new PrintWriter(sessionFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//writing to files
		for (int i = 0; i < straightSegment.size(); i++){	 
			for (int j = 0; j < straightSegment.get(i).steer.size(); j++) {
				output.print(straightSegment.get(i).steer.get(j) + ",");
				output.flush();
			}
			output.print("\n");
			output.flush();
			
			straightSegment.get(i).steer.clear();			
		}
		
		output.close();
		
	}

	double getSessionAverageSPEED_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).SPEED_MIN);
		return values.average();
	}
	double getSessionAverageSPEED_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).SPEED_MAX);
		return values.average();
	}
	double getSessionAverageSPEED_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).SPEED_AVG);
		return values.average();
	}
	double getSessionAverageSPEED_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).SPEED_STD);
		return values.average();
	}
	double getSessionAverageACCEL_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).ACCEL_MIN);
		return values.average();
	}
	double getSessionAverageACCEL_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).ACCEL_MAX);
		return values.average();
	}
	double getSessionAverageACCEL_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).ACCEL_AVG);
		return values.average();
	}
	double getSessionAverageACCEL_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).ACCEL_STD);
		return values.average();
	}
	double getSessionAverageSTEER_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).STEER_MIN);
		return values.average();
	}
	double getSessionAverageSTEER_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).STEER_MAX);
		return values.average();
	}
	double getSessionAverageSTEER_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).STEER_AVG);
		return values.average();
	}
	double getSessionAverageSTEER_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).STEER_STD);
		return values.average();
	}
	double getSessionAverageLANEDEV_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).LANEDEV_MIN);
		return values.average();
	}
	double getSessionAverageLANEDEV_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).LANEDEV_MAX);
		return values.average();
	}
	double getSessionAverageLANEDEV_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).LANEDEV_AVG);
		return values.average();
	}
	double getSessionAverageLANEDEV_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).LANEDEV_STD);
		return values.average();
	}
	double getSessionAverageBRAKEPDL_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).BRAKEPDL_MIN);
		return values.average();
	}
	double getSessionAverageBRAKEPDL_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).BRAKEPDL_MAX);
		return values.average();
	}
	double getSessionAverageACCELPDL_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).ACCELPDL_MIN);
		return values.average();
	}
	double getSessionAverageACCELPDL_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).ACCELPDL_MAX);
		return values.average();
	}
	double getSessionAverageBRAKEPDL_COUNT (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).SPEED_MIN);
		return values.average();
	}
	double getSessionAverageTTBRAKE00 (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).TTBRAKE00);
		return values.average();
	}
	double getSessionAverageTTACCREL00 (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).TTACCREL00);
		return values.average();
	}
	double getSessionAverageTTACCREL01 (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).TTACCREL01);
		return values.average();
	}
	double getSessionAverageTTACCREL02 (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).TTACCREL02);
		return values.average();
	}
	double getSessionAverageMPG_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).MPG_AVG);
		return values.average();
	}
	double getSessionAverageFUELUSED (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).FUELUSED);
		return values.average();
	} 
	
}
