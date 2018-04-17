package vanDongen;

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
public class StraightSegment {
	
	String fileID;
	
	String label;
	
	int frameStart;
	int frameStop;

	double timeStart;
	double timeStop;
	
	// for Reported data 
	double SPEED_MIN_Reported;
	double SPEED_MAX_Reported;
	double SPEED_AVG_Reported;
	double SPEED_STD_Reported;
	double ACCEL_MIN_Reported;
	double ACCEL_MAX_Reported;
	double ACCEL_AVG_Reported;
	double ACCEL_STD_Reported;
	double STEER_MIN_Reported;
	double STEER_MAX_Reported;
	double STEER_AVG_Reported;
	double STEER_STD_Reported;
	double LANEDEV_MIN_Reported;
	double LANEDEV_MAX_Reported;
	double LANEDEV_AVG_Reported;
	double LANEDEV_STD_Reported;
	double BRAKEPDL_MIN_Reported;
	double BRAKEPDL_MAX_Reported;
	double ACCELPDL_MIN_Reported;
	double ACCELPDL_MAX_Reported;
	double BRAKEPDL_COUNT_Reported;
	double TTBRAKE00_Reported;
	double TTACCREL00_Reported;
	double TTACCREL01_Reported;
	double TTACCREL02_Reported;
	double MPG_AVG_Reported;
	double FUELUSED_Reported;
	
	// for Raw Data data 
	Values MPH;
	double MPH_STD_RawData;
	double MPH_Ave_RawData;
	double MPH_Max_RawData;
	
	// for processed(Extracted) data 
	Values frameCount;
	Values elapsedTime;
	Values travelDist;
	Values simTime;
	Values accelPed;
	Values brakePed;
	Values steering;
	Values lanePos;
	Values followDist;
	
	double accelPed_Ave, accelPed_Max, accelPed_STD;
	double brakePed_Ave, brakePed_Max, brakePed_STD;
	double steering_Ave, steering_Max, steering_STD;
	double lanePos_Ave, lanePos_Max, lanePos_STD;
	
	double percentage_Frames_Zero_SteerWheel = 0; // angle less than 0.0001 degree
	double percentage_Frames_3D_SteerWheel = 0;  // angle greater than 3 degree
	double percentage_Frames_2D_SteerWheel = 0;  // angle greater than 2 degree
 	int number_FastCorrectiveCounterSteerWheel = 0; // patterns of slow drifting and fast corrective counter steering in 50 frames   
	
	double predictionError_STD;
	double steeringEntropy;
	
	int numberOfMaxMin;
	int distnaceOfMaxMin;
		
	
	public StraightSegment() {
		MPH = new Values();
		frameCount = new Values();
		elapsedTime = new Values();
		travelDist = new Values();
		simTime = new Values();
		accelPed = new Values();
		brakePed = new Values();
		steering = new Values();
		lanePos = new Values();
		followDist = new Values();
	}
}
