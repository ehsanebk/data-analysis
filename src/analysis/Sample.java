package analysis;

public class Sample {
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
	
	long record;
	double time;
	double speed;
	double steer;
	double accel, brake;
	double centerLineDev;
	double lanedev;
	double carX, carY;
	double laneWidth;

	// calculated later...
	double acceleration;
	double distanceTraveled;
	double distanceToStopSign;

	public String toString() {
		return Utilities.df3.format(SPEED_MIN)+ "\t"+
				Utilities.df3.format(SPEED_MAX)+ "\t"+
				Utilities.df3.format(SPEED_AVG)+ "\t"+
				Utilities.df3.format(SPEED_STD)+ "\t"+
				Utilities.df3.format(ACCEL_MIN)+ "\t"+
				Utilities.df3.format(ACCEL_MAX)+ "\t"+
				Utilities.df3.format(ACCEL_AVG)+ "\t"+
				Utilities.df3.format(ACCEL_STD)+ "\t"+
				Utilities.df3.format(STEER_MIN)+ "\t"+
				Utilities.df3.format(STEER_MAX)+ "\t"+
				Utilities.df3.format(STEER_AVG)+ "\t"+
				Utilities.df3.format(STEER_STD)+ "\t"+
				Utilities.df3.format(LANEDEV_MIN)+ "\t"+
				Utilities.df3.format(LANEDEV_MAX)+ "\t"+
				Utilities.df3.format(LANEDEV_AVG)+ "\t"+
				Utilities.df3.format(LANEDEV_STD)+ "\t"+
				Utilities.df3.format(BRAKEPDL_MIN)+ "\t"+
				Utilities.df3.format(BRAKEPDL_MAX)+ "\t"+
				Utilities.df3.format(ACCELPDL_MIN)+ "\t"+
				Utilities.df3.format(ACCELPDL_MAX)+ "\t"+
				Utilities.df3.format(BRAKEPDL_COUNT)+ "\t"+
				Utilities.df3.format(TTBRAKE00)+ "\t"+
				Utilities.df3.format(TTACCREL00)+ "\t"+
				Utilities.df3.format(TTACCREL01)+ "\t"+
				Utilities.df3.format(TTACCREL02)+ "\t"+
				Utilities.df3.format(MPG_AVG)+ "\t"+
				Utilities.df3.format(FUELUSED);
	}

	//	Sample(long record, Tokenizer t) {
	//		this.record = record; // t.nextToken(); // already read
	//		t.nextToken(); // count
	//		time = t.nextDouble(); // elap
//		carX = t.nextDouble(); // carX
//		carY = t.nextDouble(); // carY
//		t.nextToken(); // carZ
//		t.nextToken(); // yaw
//		t.nextToken(); // pitch
//		t.nextToken(); // roll
//		t.nextToken(); // clX
//		t.nextToken(); // clY
//		t.nextToken(); // clZ
//		centerLineDev = t.nextDouble(); // clD
//		lanedev = t.nextDouble(); // claneD
//		t.nextToken(); // vX
//		t.nextToken(); // vY
//		t.nextToken(); // vZ
//		t.nextToken(); // TrgX
//		t.nextToken(); // TrgY
//		t.nextToken(); // TrgZ
//		t.nextToken(); // TrgYaw
//		t.nextToken(); // TrgPitch
//		t.nextToken(); // TrgRoll
//		t.nextToken(); // TrgType
//		t.nextToken(); // TrgActive
//		steer = t.nextDouble(); // now in degrees; used to be: Steer: [-1,1],
//								// 120 degrees each direction
//		accel = t.nextDouble(); // Accel
//		brake = t.nextDouble(); // Brake
//		t.nextToken(); // DIO (hex0
//		speed = t.nextDouble(); // mph
//		t.nextToken(); // HeadYaw
//		t.nextToken(); // HeadPitch
//		t.nextToken(); // HeadRoll
//		laneWidth = t.nextDouble(); // LaneWidth
//
//		acceleration = 0; // calculated later as dv/dt
//		distanceTraveled = 0; // calculated later as accumulated distance
//		distanceToStopSign = 0; // calculated later as accumulated distance
//	}
//
//	double distanceSquared(Sample s2) {
//		double dx = carX - s2.carX;
//		double dy = carY - s2.carY;
//		return (dx * dx) + (dy * dy);
//	}
//
//	void calculateExtras(Sample sPrevious) {
//		double dv = speed - sPrevious.speed;
//		double dt = time - sPrevious.time;
//		acceleration = (dt == 0) ? 0 : ((dv / dt) * (5280.0 / 3600.0)); // convert
//																		// to
//																		// feet/second^2
//
//		double traveled = Math.sqrt(distanceSquared(sPrevious)) / 12.0; // inches
//																		// ->
//																		// feet
//		distanceTraveled = sPrevious.distanceTraveled + traveled;
//		distanceToStopSign = sPrevious.distanceToStopSign + traveled;
//	}
//
//	public String toString() {
//		return Utilities.df3.format(time) + "\t"
//				+ Utilities.df2.format(centerLineDev);
//	}
//
//	// Sample (MyTokenizer t)
//	// {
//	// long tStamp = t.nextLong(); // tStamp
//	// time = (tStamp * .001) / 3579545; // derived from High Resolution Timer
//	// information in Data Collection PDF
//	//
//	// t.nextToken(); // X
//	// t.nextToken(); // Y
//	// t.nextToken(); // Z
//	// t.nextToken(); // Yaw
//	// t.nextToken(); // Pitch
//	// t.nextToken(); // Roll
//	// t.nextToken(); // velX
//	// t.nextToken(); // velY
//	// t.nextToken(); // velZ
//	// t.nextToken(); // TrgX
//	// t.nextToken(); // TrgY
//	// t.nextToken(); // TrgZ
//	// t.nextToken(); // TrgYaw
//	// t.nextToken(); // TrgPitch
//	// t.nextToken(); // TrgRoll
//	// t.nextToken(); // TrgType
//	// t.nextToken(); // TrgActive
//	// steer = t.nextDouble(); // Steer
//	// accel = t.nextDouble(); // Accel
//	// brake = t.nextDouble(); // Brake
//	// t.nextToken(); // DIO (hex0
//	// speed = t.nextDouble(); // Imph
//	// t.nextToken(); // HeadYaw
//	// t.nextToken(); // HeadPitch
//	// t.nextToken(); // HeadRoll
//	// }
}
