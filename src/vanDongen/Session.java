package vanDongen;

import java.io.File;
import java.util.Vector;
import analysis.Tokenizer;
import analysis.Utilities;
import analysis.Values;


/**
 * In this code there are three type of data files:
 * 
 * 1- "Reported Data" which the the report summary reported  by the application
 * 
 * 2- "Raw Data" that is the Raw data with below fields
 *     Frame	X	Y	Z	P	R	H	steer	accel	brake	MPH	D_spd	elapsed	gear	cltch	rpm
 *  
 * 3- "Extracted Data" which is the data that was extracted from the binary files and has the below fields
 *     frameCount	elapsedTime	travelDist	simTime	accelPed	brakePed	steerWheel	lanePos	followDist
 * 
 * @author Ehsan
 *
 */
public class Session {

	String ID;
	String sessionNumber;
	int timePoint=0;   // the time points are in the format of 
					// 1 2 3 4 break 5 6 7 8
	Vector<StraightSegment> straightSegments;
	
	
	Session(File reportedFile){
		straightSegments = new Vector<StraightSegment>();
		ID = reportedFile.getName().substring(0,4);
		sessionNumber  = reportedFile.getName().substring(5, 7);
		
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
			timePoint = 5;
		if ( sessionNumberInt == 25 || sessionNumberInt == 29 || sessionNumberInt == 33 
				|| sessionNumberInt == 37 || sessionNumberInt == 41 )
			timePoint = 6;
		if ( sessionNumberInt == 26 || sessionNumberInt == 30 || sessionNumberInt == 34 
				|| sessionNumberInt == 38 || sessionNumberInt == 42 )
			timePoint = 7;
		if ( sessionNumberInt == 27 || sessionNumberInt == 31 || sessionNumberInt == 35 
				|| sessionNumberInt == 39 || sessionNumberInt == 43 )
			timePoint = 8;
		
		
		StraightSegment segment;
		Tokenizer t = new Tokenizer(reportedFile);
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
					segment.SPEED_MIN_Reported = t.nextDouble();
					break;
				case "SPEED_MAX" :
					segment.SPEED_MAX_Reported = t.nextDouble();
					break;
				case "SPEED_AVG" :
					segment.SPEED_AVG_Reported = t.nextDouble();
					break;
				case "SPEED_STD" :
					segment.SPEED_STD_Reported = t.nextDouble();
					break;
				case "ACCEL_MIN" :
					segment.ACCEL_MIN_Reported = t.nextDouble();
					break;
				case "ACCEL_MAX" :
					segment.ACCEL_MAX_Reported = t.nextDouble();
					break;
				case "ACCEL_AVG" :
					segment.ACCEL_AVG_Reported = t.nextDouble();
					break;
				case "ACCEL_STD" :
					segment.ACCEL_STD_Reported = t.nextDouble();
					break;
				case "STEER_MIN" :
					segment.STEER_MIN_Reported = t.nextDouble();
					break;
				case "STEER_MAX" :
					segment.STEER_MAX_Reported = t.nextDouble();
					break;
				case "STEER_AVG" :
					segment.STEER_AVG_Reported = t.nextDouble();
					break;
				case "STEER_STD" :
					segment.STEER_STD_Reported = t.nextDouble();
					break;
				case "LANEDEV_MIN" :
					segment.LANEDEV_MIN_Reported = t.nextDouble();
					break;
				case "LANEDEV_MAX" :
					segment.LANEDEV_MAX_Reported = t.nextDouble();
					break;
				case "LANEDEV_AVG" :
					segment.LANEDEV_AVG_Reported = t.nextDouble();
					break;
				case "LANEDEV_STD" :
					segment.LANEDEV_STD_Reported = t.nextDouble();
					break;
				case "BRAKEPDL_MIN" :
					segment.BRAKEPDL_MIN_Reported = t.nextDouble();
					break;
				case "BRAKEPDL_MAX" :
					segment.BRAKEPDL_MAX_Reported = t.nextDouble();
					break;
				case "ACCELPDL_MIN" :
					segment.ACCELPDL_MIN_Reported = t.nextDouble();
					break;
				case "ACCELPDL_MAX" :
					segment.ACCELPDL_MAX_Reported = t.nextDouble();
					break;
				case "BRAKEPDL_COUNT" :
					segment.BRAKEPDL_COUNT_Reported = t.nextDouble();
					break;
				case "TTBRAKE00" :
					segment.TTBRAKE00_Reported = t.nextDouble();
					break;
				case "TTACCREL00" :
					segment.TTACCREL00_Reported = t.nextDouble();
					break;
				case "MPG_AVG" :
					segment.MPG_AVG_Reported = t.nextDouble();
					break;
				case "FUELUSED" :
					segment.FUELUSED_Reported = t.nextDouble();
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
	
	public void addRawData(File file){
		System.out.println("Processing Raw Data: " + file.getName());
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
					straightSegments.get(i).MPH.add(MPH);
				}				
			}
		}
		
		for (int i = 0; i < straightSegments.size(); i++){
					
			straightSegments.get(i).MPH_STD_RawData = straightSegments.get(i).MPH.stddev();
			straightSegments.get(i).MPH_Ave_RawData = straightSegments.get(i).MPH.average();
			straightSegments.get(i).MPH_Max_RawData = straightSegments.get(i).MPH.max();

			// clearing the vectors for saving memory
			straightSegments.get(i).MPH.clear();
		}

	}

	public void addProssesedData(File file) {
		System.out.println("Processing Extracted Data: " + file.getName());
		Tokenizer t = new Tokenizer(file);
		t.skipLines(10);
		int frame;
		double  frameCount,elapsedTime,travelDist,simTime,accelPed,brakePed,steerWheel,lanePos,followDist;
		
		while (t.hasMoreTokens()) {
			String[] line = t.readNextLine().split("\\s+");
			frame = Utilities.toInt(line[0]);
			for (int i = 0; i < straightSegments.size(); i++) {
				if (frame >= straightSegments.get(i).frameStart && frame <= straightSegments.get(i).frameStop){
					frameCount = Utilities.toDouble(line[0]);
					elapsedTime = Utilities.toDouble(line[1]);
					travelDist = Utilities.toDouble(line[2]);
					simTime = Utilities.toDouble(line[3]);
					accelPed = Utilities.toDouble(line[4]);
					brakePed = Utilities.toDouble(line[5]);
					steerWheel = Utilities.toDouble(line[6]);
					lanePos = Utilities.toDouble(line[7]);
					followDist = Utilities.toDouble(line[8]);
					straightSegments.get(i).frameCount.add(frameCount);
					straightSegments.get(i).elapsedTime.add(elapsedTime);
					straightSegments.get(i).travelDist.add(travelDist);
					straightSegments.get(i).simTime.add(simTime);
					straightSegments.get(i).accelPed.add(accelPed);
					straightSegments.get(i).brakePed.add(brakePed);
					straightSegments.get(i).steering.add(steerWheel);
					straightSegments.get(i).lanePos.add(lanePos);
					straightSegments.get(i).followDist.add(followDist);
				}				
			}
		}
		
		for (int i = 0; i < straightSegments.size(); i++){
			
			//straightSegments.get(i).steer = Utilities.lowpass(straightSegments.get(i).steer, 0.5);
			
			Values s = straightSegments.get(i).steering;
			
			straightSegments.get(i).accelPed_STD = straightSegments.get(i).accelPed.stddev();
			straightSegments.get(i).accelPed_Ave = straightSegments.get(i).accelPed.average();
			straightSegments.get(i).accelPed_Max = straightSegments.get(i).accelPed.max();
			
			straightSegments.get(i).brakePed_STD = straightSegments.get(i).brakePed.stddev();
			straightSegments.get(i).brakePed_Ave = straightSegments.get(i).brakePed.average();
			straightSegments.get(i).brakePed_Max = straightSegments.get(i).brakePed.max();
		
			straightSegments.get(i).steering_STD = straightSegments.get(i).steering.stddev();
			straightSegments.get(i).steering_Ave = straightSegments.get(i).steering.average();
			straightSegments.get(i).steering_Max = straightSegments.get(i).steering.max();
			
			straightSegments.get(i).lanePos_STD = straightSegments.get(i).lanePos.stddev();
			straightSegments.get(i).lanePos_Ave = straightSegments.get(i).lanePos.average();
			straightSegments.get(i).lanePos_Max = straightSegments.get(i).lanePos.max();
			
			int number_Frames_Zero_SteerAngel =0;
			int number_Frames_2D_SteerAngel =0;
			int number_Frames_3D_SteerAngel =0;
			for (int j1 = 0; j1 < s.size(); j1++) {
				if (Math.abs(s.get(j1)) < 0.00001)
					number_Frames_Zero_SteerAngel ++;
				if (Math.abs(s.get(j1)) > 0.034)
					number_Frames_2D_SteerAngel ++;
				if (Math.abs(s.get(j1)) > 0.052)
					number_Frames_3D_SteerAngel ++;
			}
			straightSegments.get(i).percentage_Frames_Zero_SteerWheel = (double)number_Frames_Zero_SteerAngel / s.size() *100;
			straightSegments.get(i).percentage_Frames_2D_SteerWheel = (double)number_Frames_2D_SteerAngel / s.size() *100;
			straightSegments.get(i).percentage_Frames_3D_SteerWheel = (double)number_Frames_3D_SteerAngel / s.size() *100;

			// finding Fast Corrective Counter Steering in the values  : FCCS		
			for (int j = 0; j < s.size()-100; j++) {
				boolean drift = true;
				for (int k = j; k < j+70; k++) {
					if (s.get(k)!= s.get(j))
						drift = false;
				}
				if(Math.abs(s.get(j) - s.get(j+100)) >= 0.02 && drift ){	
					straightSegments.get(i).number_FastCorrectiveCounterSteerWheel ++ ;
					j += 100;
				}
			}

			// finding Prediction error values
			Values e = Utilities.predictionError(straightSegments.get(i).steering);
			straightSegments.get(i).predictionError_STD = e.stddev();

			straightSegments.get(i).steeringEntropy = Utilities.steeringEntropy(s);
			
			// clearing the vectors for saving memory
			straightSegments.get(i).frameCount.clear();
			straightSegments.get(i).elapsedTime.clear();
			straightSegments.get(i).travelDist.clear();
			straightSegments.get(i).simTime.clear();
			straightSegments.get(i).accelPed.clear();
			straightSegments.get(i).brakePed.clear();
			straightSegments.get(i).steering.clear();
			straightSegments.get(i).lanePos.clear();
			straightSegments.get(i).followDist.clear();
			
		}
		
	}
	
	int getSessionNumber(){
		return Utilities.toInt(sessionNumber);
	}
	
	// Getting the values from Reported data
	double getSessionSPEED_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).SPEED_MIN_Reported);
		return values.average();
	}
	double getSessionSPEED_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).SPEED_MAX_Reported);
		return values.average();
	}
	double getSessionSPEED_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).SPEED_AVG_Reported);
		return values.average();
	}
	double getSessionSPEED_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).SPEED_STD_Reported);
		return values.average();
	}
	double getSessionACCEL_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).ACCEL_MIN_Reported);
		return values.average();
	}
	double getSessionACCEL_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).ACCEL_MAX_Reported);
		return values.average();
	}
	double getSessionACCEL_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).ACCEL_AVG_Reported);
		return values.average();
	}
	double getSessionACCEL_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).ACCEL_STD_Reported);
		return values.average();
	}
	double getSessionSTEER_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).STEER_MIN_Reported);
		return values.average();
	}
	double getSessionSTEER_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).STEER_MAX_Reported);
		return values.average();
	}
	double getSessionSTEER_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).STEER_AVG_Reported);
		return values.average();
	}
	double getSessionSTEER_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).STEER_STD_Reported);
		return values.average();
	}
	double getSessionLANEDEV_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).LANEDEV_MIN_Reported);
		return values.average();
	}
	double getSessionLANEDEV_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).LANEDEV_MAX_Reported);
		return values.average();
	}
	double getSessionLANEDEV_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).LANEDEV_AVG_Reported);
		return values.average();
	}
	double getSessionLANEDEV_STD (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).LANEDEV_STD_Reported);
		return values.average();
	}
	double getSessionBRAKEPDL_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).BRAKEPDL_MIN_Reported);
		return values.average();
	}
	double getSessionBRAKEPDL_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).BRAKEPDL_MAX_Reported);
		return values.average();
	}
	double getSessionACCELPDL_MIN (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).ACCELPDL_MIN_Reported);
		return values.average();
	}
	double getSessionACCELPDL_MAX (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).ACCELPDL_MAX_Reported);
		return values.average();
	}
	double getSessionBRAKEPDL_COUNT (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).SPEED_MIN_Reported);
		return values.average();
	}
	double getSessionTTBRAKE00 (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).TTBRAKE00_Reported);
		return values.average();
	}
	double getSessionTTACCREL00 (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).TTACCREL00_Reported);
		return values.average();
	}
	double getSessionTTACCREL01 (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).TTACCREL01_Reported);
		return values.average();
	}
	double getSessionTTACCREL02 (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).TTACCREL02_Reported);
		return values.average();
	}
	double getSessionMPG_AVG (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).MPG_AVG_Reported);
		return values.average();
	}
	double getSessionFUELUSED (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).FUELUSED_Reported);
		return values.average();
	}
	
	///// new values Raw Data
	double getSessionMPH_STD_RawData(){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).MPH_STD_RawData);
		return values.average();
	}
	double getSessionMPH_Ave_RawData(){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).MPH_Ave_RawData);
		return values.average();
	}
	double getSessionPredicitonError_STD_RawData(){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).predictionError_STD);
		return values.average();
	}
	double getSessionSteeringEntorpy_RawData(){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).steeringEntropy);
		return values.average();
	}

	//////// For the extracted data ////////
	// Accelpad
	double getSessionAccelPed_Ave_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).accelPed_Ave);
		return values.average();
	}
	double getSessionAccelPed_Max_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).accelPed_Max);
		return values.average();
	}
	double getSessionAccelPed_STD_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).accelPed_STD);
		return values.average();
	}
	// BrakePad
	double getSessionBrakePed_Ave_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).brakePed_Ave);
		return values.average();
	}
	double getSessionBrakePed_Max_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).brakePed_Max);
		return values.average();
	}
	double getSessionBrakePed_STD_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).brakePed_STD);
		return values.average();
	}
	// Lane position
	double getSessionLanePos_Ave_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).lanePos_Ave);
		return values.average();
	}
	double getSessionLanePos_Max_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).lanePos_Max);
		return values.average();
	}
	double getSessionLanePos_STD_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).lanePos_STD);
		return values.average();
	}
	
	//Returning the lane position in the first half (0) and second half (1)
	// The first two segments are dropped for data clarity...
	// The fist half is segment 2 to 6 and second is 6 to 10 (10 segments in total)
	double getSessionLanePosAtEachHalf_STD_Extracted (int half){
		Values values = new Values();
		for (int i = 2+ half*4; i < (2 + half*4) + 4; i++)
			values.add(straightSegments.get(i).lanePos_STD);
		return values.average();
	}
	
	// Steering
	double getSessionSteering_Ave_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).steering_Ave);
		return values.average();
	}
	double getSessionSteering_Max_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).steering_Max);
		return values.average();
	}
	double getSessionSteering_STD_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).steering_STD);
		return values.average();
	}
	//Returning the Steering Angle STD in the first half (0) and second half (1)
	// The first two segments are dropped for data clarity...
	// The fist half is segment 2 to 6 and second is 6 to 10 (10 segments in total)
	double getSessionSteeringAtEachHalf_STD_Extracted (int half){
		Values values = new Values();
		for (int i = 2+ half*4; i < (2 + half*4) + 4; i++)
			values.add(straightSegments.get(i).steering_STD);
		return values.average();
	}

	
	
	double getSessionZeroSteer_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).percentage_Frames_Zero_SteerWheel);
		return values.average();
	}
	double getSession2DSteer_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).percentage_Frames_2D_SteerWheel);
		return values.average();
	}
	double getSession3DSteer_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).percentage_Frames_3D_SteerWheel);
		return values.average();
	}
	double getSessionFastCorrectiveCounterSteering_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).number_FastCorrectiveCounterSteerWheel);
		return values.average();
	}
	
	double getSessionPredicitonError_STD_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).predictionError_STD);
		return values.average();
	}
	double getSessionSteeringEntorpy_Extracted (){
		Values values = new Values();
		for (int i = 0; i < straightSegments.size(); i++)
			values.add(straightSegments.get(i).steeringEntropy);
		return values.average();
	}
}
