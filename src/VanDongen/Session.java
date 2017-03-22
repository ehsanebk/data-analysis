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
	Vector<StraightSegment> straightSegments;
	
	
	Session(File file){
		straightSegments = new Vector<StraightSegment>();
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
				
				for (int i = 0; i < straightSegments.size(); i++) {
					if (straightSegments.get(i).label.equals(label)){
						segment = straightSegments.get(i);
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
					
					straightSegments.add(segment);
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
			for (int i = 0; i < straightSegments.size(); i++) {
				if (frame >= straightSegments.get(i).frameStart && frame <= straightSegments.get(i).frameStop){
					steer = Utilities.toDouble(line[7]);
					MPH = Utilities.toDouble(line[10]);
					straightSegments.get(i).steer.add(steer);
					straightSegments.get(i).MPH.add(MPH);
				}				
			}
		}
		
		for (int i = 0; i < straightSegments.size(); i++){
			
			straightSegments.get(i).steer = Utilities.lowpass(straightSegments.get(i).steer, 0.5);
			
			Values s = straightSegments.get(i).steer;
			
			// finding Fast Corrective Counter Steering in the values
			
			for (int j = 0; j < s.size()-100; j++) {
				boolean drift = true;
				for (int k = j; k < j+70; k++) {
					if (s.get(k)!= s.get(j))
						drift = false;
				}
				if(Math.abs(s.get(j) - s.get(j+100)) >= 0.2 && drift ){
					straightSegments.get(i).number_FastCorrectiveCounterSteering ++ ;
					j += 100;
				}
					
			}
			
			// finding Prediction error values
			Values e = Utilities.predictionError(straightSegments.get(i).steer);
		
			straightSegments.get(i).steer_STD = straightSegments.get(i).steer.stddev();
			straightSegments.get(i).MPH_STD = straightSegments.get(i).MPH.stddev();
			straightSegments.get(i).steer_Ave = straightSegments.get(i).steer.average();
			straightSegments.get(i).MPH_Ave = straightSegments.get(i).MPH.average();
			straightSegments.get(i).steer_Max = straightSegments.get(i).steer.max();
			straightSegments.get(i).MPH_Max = straightSegments.get(i).MPH.max();
			int number_Frames_Zero_SteerAngel =0;
			int number_Frames_2D_SteerAngel =0;
			int number_Frames_3D_SteerAngel =0;
			for (int j1 = 0; j1 < s.size(); j1++) {
				if (Math.abs(s.get(j1)) == 0)
					number_Frames_Zero_SteerAngel ++;
				if (Math.abs(s.get(j1)) >= 0.2)
					number_Frames_2D_SteerAngel ++;
				if (Math.abs(s.get(j1)) >= 0.3)
					number_Frames_3D_SteerAngel ++;
			}
			straightSegments.get(i).percentage_Frames_Zero_SteerAngel = number_Frames_Zero_SteerAngel / s.size() * 100;
			straightSegments.get(i).percentage_Frames_2D_SteerAngel = number_Frames_2D_SteerAngel / s.size()  * 100;
			straightSegments.get(i).percentage_Frames_3D_SteerAngel = number_Frames_3D_SteerAngel / s.size() * 100 ;
			
			// finding Fast Corrective Counter Steering in the values
			for (int j = 0; j < s.size()-100; j++) {
				boolean drift = true;
				for (int k = j; k < j+70; k++) {
					if (s.get(k)!= s.get(j))
						drift = false;
				}
				if(Math.abs(s.get(j) - s.get(j+100)) >= 0.2 && drift ){
					straightSegments.get(i).number_FastCorrectiveCounterSteering ++ ;
					j += 100;
				}
			}
		
			straightSegments.get(i).predictionError_STD = e.stddev();
			straightSegments.get(i).steeringEntropy = Utilities.steeringEntropy(s);
			// clearing the vectors for saving memory
			straightSegments.get(i).steer.clear();
			straightSegments.get(i).MPH.clear();
		}
			
	}

	double getSessionAverageSPEED_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).SPEED_MIN);
		return values.average();
	}
	double getSessionAverageSPEED_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).SPEED_MAX);
		return values.average();
	}
	double getSessionAverageSPEED_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).SPEED_AVG);
		return values.average();
	}
	double getSessionAverageSPEED_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).SPEED_STD);
		return values.average();
	}
	double getSessionAverageACCEL_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).ACCEL_MIN);
		return values.average();
	}
	double getSessionAverageACCEL_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).ACCEL_MAX);
		return values.average();
	}
	double getSessionAverageACCEL_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).ACCEL_AVG);
		return values.average();
	}
	double getSessionAverageACCEL_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).ACCEL_STD);
		return values.average();
	}
	double getSessionAverageSTEER_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).STEER_MIN);
		return values.average();
	}
	double getSessionAverageSTEER_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).STEER_MAX);
		return values.average();
	}
	double getSessionAverageSTEER_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).STEER_AVG);
		return values.average();
	}
	double getSessionAverageSTEER_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).STEER_STD);
		return values.average();
	}
	double getSessionAverageLANEDEV_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).LANEDEV_MIN);
		return values.average();
	}
	double getSessionAverageLANEDEV_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).LANEDEV_MAX);
		return values.average();
	}
	double getSessionAverageLANEDEV_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).LANEDEV_AVG);
		return values.average();
	}
	double getSessionAverageLANEDEV_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).LANEDEV_STD);
		return values.average();
	}
	double getSessionAverageBRAKEPDL_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).BRAKEPDL_MIN);
		return values.average();
	}
	double getSessionAverageBRAKEPDL_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).BRAKEPDL_MAX);
		return values.average();
	}
	double getSessionAverageACCELPDL_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).ACCELPDL_MIN);
		return values.average();
	}
	double getSessionAverageACCELPDL_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).ACCELPDL_MAX);
		return values.average();
	}
	double getSessionAverageBRAKEPDL_COUNT (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).SPEED_MIN);
		return values.average();
	}
	double getSessionAverageTTBRAKE00 (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).TTBRAKE00);
		return values.average();
	}
	double getSessionAverageTTACCREL00 (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).TTACCREL00);
		return values.average();
	}
	double getSessionAverageTTACCREL01 (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).TTACCREL01);
		return values.average();
	}
	double getSessionAverageTTACCREL02 (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).TTACCREL02);
		return values.average();
	}
	double getSessionAverageMPG_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).MPG_AVG);
		return values.average();
	}
	double getSessionAverageFUELUSED (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).FUELUSED);
		return values.average();
	}
	
	// new values
	double getSessionAveragesteer_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).steer_STD);
		return values.average();
	}
	double getSessionAverageMPH_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).MPH_STD);
		return values.average();
	}
	
	double getSessionAveragesteer_Ave (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).steer_Ave);
		return values.average();
	}
	double getSessionAverageMPH_Ave (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).MPH_Ave);
		return values.average();
	}
	double getSessionAverageZeroSteer (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).percentage_Frames_Zero_SteerAngel);
		return values.average();
	}
	double getSessionAverage2DSteer (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).percentage_Frames_2D_SteerAngel);
		return values.average();
	}
	double getSessionAverage3DSteer (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).percentage_Frames_3D_SteerAngel);
		return values.average();
	}
	double getSessionAverageFastCorrectiveCounterSteering (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).number_FastCorrectiveCounterSteering);
		return values.average();
	}
	
	double getSessionAveragepredicitonError_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).predictionError_STD);
		return values.average();
	}
	double getSessionAveragesteeringEntorpy (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).steeringEntropy);
		return values.average();
	}
}
