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
	int timePoint;
	Vector<StraightSegment> straightSegment;
	
	
	Session(File file){
		straightSegment = new Vector<StraightSegment>();
		ID = file.getName().substring(0,4);
		sessionNumber  = file.getName().substring(5, 7);
		
		int sessionNumberInt = Utilities.toInt(sessionNumber);
		
		if ( sessionNumberInt == 4 || sessionNumberInt == 8 || sessionNumberInt == 12 
				|| sessionNumberInt == 16 || sessionNumberInt == 20 )
			timePoint = 1;
		if ( sessionNumberInt == 5 || sessionNumberInt == 9 || sessionNumberInt == 13 
				|| sessionNumberInt == 17 || sessionNumberInt == 21 )
			timePoint = 2;
		if ( sessionNumberInt == 6 || sessionNumberInt == 10 || sessionNumberInt == 14 
				|| sessionNumberInt == 18 || sessionNumberInt == 22 )
			timePoint = 3;
		if ( sessionNumberInt == 7 || sessionNumberInt == 11 || sessionNumberInt == 15 
				|| sessionNumberInt == 19 || sessionNumberInt == 23 )
			timePoint = 4;

		if ( sessionNumberInt == 24 || sessionNumberInt == 28 || sessionNumberInt == 32 
				|| sessionNumberInt == 36 || sessionNumberInt == 40 )
			timePoint = 6;
		if ( sessionNumberInt == 25 || sessionNumberInt == 29 || sessionNumberInt == 33 
				|| sessionNumberInt == 37 || sessionNumberInt == 41 )
			timePoint = 7;
		if ( sessionNumberInt == 26 || sessionNumberInt == 30 || sessionNumberInt == 34 
				|| sessionNumberInt == 38 || sessionNumberInt == 42 )
			timePoint = 8;
		if ( sessionNumberInt == 27 || sessionNumberInt == 31 || sessionNumberInt == 35 
				|| sessionNumberInt == 39 || sessionNumberInt == 43 )
			timePoint = 9;
		
		
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
//					straightSegment.get(i).MPH.add(MPH);
				}				
			}
		}
		
		for (int i = 0; i < straightSegment.size(); i++){	 
//			straightSegment.get(i).steer_STD = straightSegment.get(i).steer.stddev();
//			straightSegment.get(i).MPH_STD = straightSegment.get(i).MPH.stddev();
//			straightSegment.get(i).steer_Ave = straightSegment.get(i).steer.average();
//			straightSegment.get(i).MPH_Ave = straightSegment.get(i).MPH.average();
//			straightSegment.get(i).steer_Max = straightSegment.get(i).steer.max();
//			straightSegment.get(i).MPH_Max = straightSegment.get(i).MPH.max();
			for (int j = 0; j < straightSegment.get(i).steer.size(); j++) {
				if (straightSegment.get(i).steer.get(j) == 0)
					straightSegment.get(i).number_Frames_Zero_SteerAngel ++;
			}				
			straightSegment.get(i).steer.clear();
			straightSegment.get(i).MPH.clear();
		}
			
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
	
	// new values
	double getSessionAveragesteer_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).steer_STD);
		return values.average();
	}
	double getSessionAverageMPH_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).MPH_STD);
		return values.average();
	}
	
	double getSessionAveragesteer_Ave (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).steer_Ave);
		return values.average();
	}
	double getSessionAverageMPH_Ave (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).MPH_Ave);
		return values.average();
	}
	
	double getSessionAverageZeroSteer (){
		Values values = new Values();
		for (int i = 0; i < straightSegment.size(); i++)
			values.add(straightSegment.get(i).number_Frames_Zero_SteerAngel);
		return values.average();
	}
}
