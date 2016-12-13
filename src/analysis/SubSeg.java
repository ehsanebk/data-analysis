package analysis;

public class SubSeg {
	Values speeds, steers, accels;

	SubSeg() {
		speeds = new Values();
		steers = new Values();
		accels = new Values();
	}

	void add(Sample s) {
		speeds.add(s.speed);
		steers.add(s.steer);
		accels.add(s.accel);
	}

	double speedMean() {
		return speeds.mean();
	}

	double speedSD() {
		return speeds.stddev();
	}

	double steerMean() {
		return steers.mean();
	}

	double steerSD() {
		return steers.stddev();
	}

	double accelMean() {
		return accels.mean();
	}

	double accelSD() {
		return accels.stddev();
	}
}
