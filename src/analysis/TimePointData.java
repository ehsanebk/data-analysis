package analysis;

import java.util.ArrayList;

public class TimePointData {
	ArrayList<Double> SPEED_MIN = new ArrayList<Double>();
	ArrayList<Double> SPEED_MAX = new ArrayList<Double>();
	ArrayList<Double> SPEED_AVG = new ArrayList<Double>();
	ArrayList<Double> SPEED_STD = new ArrayList<Double>();
	ArrayList<Double> ACCEL_MIN = new ArrayList<Double>();
	ArrayList<Double> ACCEL_MAX = new ArrayList<Double>();
	ArrayList<Double> ACCEL_AVG = new ArrayList<Double>();
	ArrayList<Double> ACCEL_STD = new ArrayList<Double>();
	ArrayList<Double> STEER_MIN = new ArrayList<Double>();
	ArrayList<Double> STEER_MAX = new ArrayList<Double>();
	ArrayList<Double> STEER_AVG = new ArrayList<Double>();
	ArrayList<Double> STEER_STD = new ArrayList<Double>();
	ArrayList<Double> LANEDEV_MIN = new ArrayList<Double>();
	ArrayList<Double> LANEDEV_MAX = new ArrayList<Double>();
	ArrayList<Double> LANEDEV_AVG = new ArrayList<Double>();
	ArrayList<Double> LANEDEV_STD = new ArrayList<Double>();
	ArrayList<Double> BRAKEPDL_MIN = new ArrayList<Double>();
	ArrayList<Double> BRAKEPDL_MAX = new ArrayList<Double>();
	ArrayList<Double> ACCELPDL_MIN = new ArrayList<Double>();
	ArrayList<Double> ACCELPDL_MAX = new ArrayList<Double>();
	ArrayList<Double> BRAKEPDL_COUNT = new ArrayList<Double>();
	ArrayList<Double> TTBRAKE00 = new ArrayList<Double>();
	ArrayList<Double> TTACCREL00 = new ArrayList<Double>();
	ArrayList<Double> TTACCREL01 = new ArrayList<Double>();
	ArrayList<Double> TTACCREL02 = new ArrayList<Double>();
	ArrayList<Double> MPG_AVG = new ArrayList<Double>();
	ArrayList<Double> FUELUSED = new ArrayList<Double>();
	
	
	
	public void add(Sample s){
		SPEED_MIN.add(s.SPEED_MIN);
		SPEED_MAX.add(s.SPEED_MAX);
		SPEED_AVG.add(s.SPEED_AVG);
		SPEED_STD.add(s.SPEED_STD);
		ACCEL_MIN.add(s.ACCEL_MIN);
		ACCEL_MAX.add(s.ACCEL_MAX);
		ACCEL_AVG.add(s.ACCEL_AVG);
		ACCEL_STD.add(s.ACCEL_STD);
		STEER_MIN.add(s.STEER_MIN);
		STEER_MAX.add(s.STEER_MAX);
		STEER_AVG.add(s.STEER_AVG);
		STEER_STD.add(s.STEER_STD);
		LANEDEV_MIN.add(s.LANEDEV_MIN);
		LANEDEV_MAX.add(s.LANEDEV_MAX);
		LANEDEV_AVG.add(s.LANEDEV_AVG);
		LANEDEV_STD.add(s.LANEDEV_STD);
		BRAKEPDL_MIN.add(s.BRAKEPDL_MIN);
		BRAKEPDL_MAX.add(s.BRAKEPDL_MAX);
		ACCELPDL_MIN.add(s.ACCELPDL_MIN);
		ACCELPDL_MAX.add(s.ACCELPDL_MAX);
		BRAKEPDL_COUNT.add(s.BRAKEPDL_COUNT);
		TTBRAKE00.add(s.TTBRAKE00);
		TTACCREL00.add(s.TTACCREL00);
		TTACCREL01.add(s.TTACCREL01);
		TTACCREL02.add(s.TTACCREL02);
		MPG_AVG.add(s.MPG_AVG);
		FUELUSED.add(s.FUELUSED);
		
	}
	
	public Sample getAverage(){
		Sample s =  new Sample();
		s.SPEED_MIN = calculateAverage ( SPEED_MIN);
		s.SPEED_MAX = calculateAverage ( SPEED_MAX);
		s.SPEED_AVG = calculateAverage ( SPEED_AVG);
		s.SPEED_STD = calculateAverage ( SPEED_STD);
		s.ACCEL_MIN = calculateAverage ( ACCEL_MIN);
		s.ACCEL_MAX = calculateAverage ( ACCEL_MAX);
		s.ACCEL_AVG = calculateAverage ( ACCEL_AVG);
		s.ACCEL_STD = calculateAverage ( ACCEL_STD);
		s.STEER_MIN = calculateAverage ( STEER_MIN);
		s.STEER_MAX = calculateAverage ( STEER_MAX);
		s.STEER_AVG = calculateAverage ( STEER_AVG);
		s.STEER_STD = calculateAverage ( STEER_STD);
		s.LANEDEV_MIN = calculateAverage ( LANEDEV_MIN);
		s.LANEDEV_MAX = calculateAverage ( LANEDEV_MAX);
		s.LANEDEV_AVG = calculateAverage ( LANEDEV_AVG);
		s.LANEDEV_STD = calculateAverage ( LANEDEV_STD);
		s.BRAKEPDL_MIN = calculateAverage ( BRAKEPDL_MIN);
		s.BRAKEPDL_MAX = calculateAverage ( BRAKEPDL_MAX);
		s.ACCELPDL_MIN = calculateAverage ( ACCELPDL_MIN);
		s.ACCELPDL_MAX = calculateAverage ( ACCELPDL_MAX);
		s.BRAKEPDL_COUNT = calculateAverage ( BRAKEPDL_COUNT);
		s.TTBRAKE00 = calculateAverage ( TTBRAKE00);
		s.TTACCREL00 = calculateAverage ( TTACCREL00);
		s.TTACCREL01 = calculateAverage ( TTACCREL01);
		s.TTACCREL02 = calculateAverage ( TTACCREL02);
		s.MPG_AVG = calculateAverage ( MPG_AVG);
		s.FUELUSED = calculateAverage ( FUELUSED);
		
		return s;
	}
	
	private double calculateAverage(ArrayList<Double> list) {
		  double sum = 0;
		  if(!list.isEmpty()) {
		    for ( double e : list) {
		        sum += e;
		    }
		    return sum / list.size();
		  }
		  return sum;
		}
	

}
