package VanDongen;

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
	Values steer;
	
	double MPH_STD;
	double steer_STD;
	
	double MPH_Ave;
	double steer_Ave;
	
	double MPH_Max;
	double steer_Max;
	
	int number_Frames_Zero_SteerAngel = 0;
	
	double predictionError_STD;
	
	int numberOfMaxMin;
	int distnaceOfMaxMin;
	
	public StraightSegment() {
		MPH = new Values();
		steer = new Values();
		
	}
}
