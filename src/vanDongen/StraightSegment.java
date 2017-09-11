package vanDongen;

import analysis.Values;

public class StraightSegment {
	
	String fileID;
	
	String label;
	
	int frameStart;
	int frameStop;

	double timeStart;
	double timeStop;
	
	double SPEED_MIN;
	double SPEED_MAX;
	double SPEED_AVG;
	double SPEED_STD;
	double ACCEL_MIN;
	double ACCEL_MAX;
	double ACCEL_AVG;
	double ACCEL_STD;
	double STEER_MIN;
	double STEER_MAX;
	double STEER_AVG;
	double STEER_STD;
	double LANEDEV_MIN;
	double LANEDEV_MAX;
	double LANEDEV_AVG;
	double LANEDEV_STD;
	double BRAKEPDL_MIN;
	double BRAKEPDL_MAX;
	double ACCELPDL_MIN;
	double ACCELPDL_MAX;
	double BRAKEPDL_COUNT;
	double TTBRAKE00;
	double TTACCREL00;
	double TTACCREL01;
	double TTACCREL02;
	double MPG_AVG;
	double FUELUSED;
	
	Values MPH;
	Values roundedSteer;
	
	double MPH_STD;
	double roundedSteer_STD;
	
	double MPH_Ave;
	double roundedSteer_Ave;
	
	double MPH_Max;
	double roundedSteer_Max;
	
	double percentage_Frames_Zero_SteerAngel_rounded = 0;
	double percentage_Frames_3D_SteerAngel_rounded = 0;  // angle greater than 0.03
	double percentage_Frames_2D_SteerAngel_rounded = 0;  // angle greater than 0.02
	int number_FastCorrectiveCounterSteering_rounded = 0; // patterns of slow drifting and fast corrective counter steering in 50 frames   
	
	double predictionError_STD_rounded;
	double SteeringEntropy_rounded;
	
	int numberOfMaxMin_rounded;
	int distnaceOfMaxMin_rounded;
	
	// for processed data 
	Values frameCount;
	Values elapsedTime;
	Values travelDist;
	Values simTime;
	Values accelPed;
	Values brakePed;
	Values steerWheel;
	Values lanePos;
	Values followDist;
	
	double accelPed_Ave, accelPed_Max, accelPed_STD;
	double brakePed_Ave, brakePed_Max, brakePed_STD;
	double steerWheel_Ave, steerWheel_Max, steerWheel_STD;
	
	
	//double percentage_Frames_Zero_SteerWheel = 0;
	double percentage_Frames_3D_SteerWheel = 0;  // angle greater than 0.03
	double percentage_Frames_2D_SteerWheel = 0;  // angle greater than 0.02
	int number_FastCorrectiveCounterSteerWheel = 0; // patterns of slow drifting and fast corrective counter steering in 50 frames   
	
	double predictionError_STD;
	double steeringEntropy;
	
	int numberOfMaxMin;
	int distnaceOfMaxMin;
	
	
	
	
	
	public StraightSegment() {
		MPH = new Values();
		roundedSteer = new Values();
		
		frameCount = new Values();
		elapsedTime = new Values();
		travelDist = new Values();
		simTime = new Values();
		accelPed = new Values();
		brakePed = new Values();
		steerWheel = new Values();
		lanePos = new Values();
		followDist = new Values();
	}
}
