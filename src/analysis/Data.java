package analysis;

import java.io.File;
import java.util.*;

public class Data {
	private String name;
	private String session;
	private long from;
	private long to;
	//private Vector<Sample> samples;
	Sample sample;
	
	// Values speeds = new Values();
	// Values accels = new Values();
	// Values brakes = new Values();
	// Values steers = new Values();
	// Values laterals = new Values();
	// Values decelerations = new Values();
	// static final double extraTime = 5.0; // extra time added onto end of task
	// // for analysis
	// static final double halfLaneWidth = 123; // half of lane width for
	// highway
	// // (246")
	// static final double laneDepartThreshold = halfLaneWidth - 41; // car is
	// 82"
	// // wide

	Data(File file) {
		name = file.getName().substring(0,4);
		session  = file.getName().substring(5, 7);
		
		//samples = new Vector<Sample>();
		sample= new Sample();
		
		Tokenizer t = new Tokenizer(file);
		t.skipLines(4);
		// 1
		
		while (t.hasMoreTokens()) {
			t.nextToken();t.nextToken();
			CharSequence str = "STRAIGHT";
			if (t.nextToken().contains(str)){
				t.nextToken();t.nextToken();t.nextToken();t.nextToken();
				System.out.println(t.nextToken());
			}
			else
				t.skipLine();
		
		}
		
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.SPEED_MIN += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.SPEED_MAX += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.SPEED_AVG += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.SPEED_STD += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.ACCEL_MIN += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.ACCEL_MAX += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.ACCEL_AVG += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.ACCEL_STD += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.STEER_MIN += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.STEER_MAX += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.STEER_AVG += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.STEER_STD += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.LANEDEV_MIN += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.LANEDEV_MAX += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.LANEDEV_AVG += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.LANEDEV_STD += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.BRAKEPDL_MIN += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.BRAKEPDL_MAX += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.ACCELPDL_MIN += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.ACCELPDL_MAX += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.BRAKEPDL_COUNT += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.TTACCREL00 += t.nextDouble();
		t.skipLines(2);
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.MPG_AVG += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.FUELUSED += t.nextDouble();
		//2
		t.skipLines(26);
		captureData(t);
		//3
		t.skipLines(25);
		captureData(t);
		//4		
		captureData(t);
		//5
		captureData(t);
		//6
		captureData(t);
		//7
		captureData(t);
		//8
		
		
		
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		System.out.println(t.nextDouble());
		
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		System.out.println(t.nextDouble());
		
//		Tokenizer t = new Tokenizer(file);
//		t.nextToken();
//		name = t.nextToken();
//		t.skipLines(32);
//		t.nextToken();
//		from = t.nextLong();
//		t.nextToken();
//		to = t.nextLong();
//		t.skipLines(2);
//
//		System.out.println(name + " | " + from + "-" + to);
//
//		while (t.hasMoreTokens()) {
//			long record = t.nextLong();
//			if (record < to) {
//				Sample s = new Sample(record, t);
//				samples.add(s);
//			}
//		}
//
//		for (int i = 1; i < samples.size(); i++) {
//			Sample s1 = samples.elementAt(i - 1);
//			Sample s2 = samples.elementAt(i);
//			s2.calculateExtras(s1);
//		}

		// if (samples.size() > 0) {
		// // fix distanceToStopSign
		// int index = 0;
		// for (; index < samples.size(); index++)
		// if (samples.elementAt(index).record == stopRecord)
		// break;
		// if (index >= samples.size())
		// index = 0;
		// double zeroDistance = samples.elementAt(index).distanceToStopSign;
		// for (int i = 0; i < samples.size(); i++) {
		// Sample s = samples.elementAt(i);
		// s.distanceToStopSign = zeroDistance - s.distanceToStopSign;
		// }
		// }
		// else System.out.println ("ERROR? -- no samples");
	}

	void captureData(Tokenizer t){
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.SPEED_MIN += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.SPEED_MAX += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.SPEED_AVG += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.SPEED_STD += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.ACCEL_MIN += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.ACCEL_MAX += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.ACCEL_AVG += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.ACCEL_STD += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.STEER_MIN += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.STEER_MAX += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.STEER_AVG += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.STEER_STD += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.LANEDEV_MIN += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.LANEDEV_MAX += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.LANEDEV_AVG += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.LANEDEV_STD += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.BRAKEPDL_MIN += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.BRAKEPDL_MAX += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.ACCELPDL_MIN += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.ACCELPDL_MAX += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.BRAKEPDL_COUNT += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.TTACCREL00 += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.MPG_AVG += t.nextDouble();
		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
		sample.FUELUSED += t.nextDouble();

	}
	// Data trimTask(double startTime, double endTime) {
	// Data d2 = new Data();
	// for (int i = 0; i < samples.size(); i++) {
	// Sample s = samples.elementAt(i);
	// if (s.time >= startTime && s.time <= endTime + extraTime)
	// d2.samples.add(s);
	// }
	// return d2;
	// }
	//
	// Data trimGeographic(Sample start, double duration) {
	// double startTime = 0;
	// double minD = 1e20;
	// for (int i = 0; i < samples.size(); i++) {
	// Sample s = samples.elementAt(i);
	// double d = s.distanceSquared(start);
	// if (d < minD) {
	// startTime = s.time;
	// minD = d;
	// }
	// }
	// return trimTask(startTime, startTime + duration);
	// }
	//
	// Sample getSample(int i) {
	// if (i < 0 || i >= samples.size())
	// return null;
	// return samples.elementAt(i);
	// }
	//
	// double totalTime() {
	// return (samples.size() == 0) ? 0
	// : (getSample(samples.size() - 1).time - getSample(0).time);
	// }
	//
	// void analyze() {
	// for (int i = 0; i < samples.size(); i++) {
	// Sample s = samples.elementAt(i);
	// speeds.add(s.speed);
	// accels.add(s.accel);
	// brakes.add(s.brake);
	// steers.add(s.steer);
	// laterals.add(s.lanedev); // (s.centerLineDev - halfLaneWidth);
	// decelerations.add(-s.acceleration);
	// }
	// }
	//
	// int indexOfLowestSpeed() {
	// if (samples.size() == 0)
	// return 0;
	// int lowestIndex = 0;
	// double lowestSpeed = samples.elementAt(0).speed;
	// for (int i = 1; i < samples.size(); i++) {
	// Sample s = samples.elementAt(i);
	// if (s.speed < lowestSpeed) {
	// lowestSpeed = s.speed;
	// lowestIndex = i;
	// }
	// }
	// return lowestIndex;
	// }
	//
	// int indexOfFirstBrakePress() {
	// int i = 0;
	// for (; i < samples.size(); i++)
	// if (samples.elementAt(i).brake > 0)
	// return i;
	// return 0;
	// }
	//
	// double stopDistanceAtLowestSpeed() {
	// if (isHighway)
	// return 0;
	// return samples.elementAt(indexOfLowestSpeed()).distanceToStopSign;
	// }
	//
	// double stopDistanceAtFirstPedalPress() {
	// if (isHighway)
	// return 0;
	// return samples.elementAt(indexOfFirstBrakePress()).distanceToStopSign;
	// }
	//
	// double stopMaxDeceleration() {
	// return decelerations.max();
	// }
	//
	// double stopAverageDeceleration() {
	// if (isHighway)
	// return 0;
	// int iBrake = indexOfFirstBrakePress();
	// int iLowestSpeed = indexOfLowestSpeed();
	// if (iBrake > iLowestSpeed)
	// return 0;
	// double dspeed = samples.elementAt(iBrake).speed
	// - samples.elementAt(iLowestSpeed).speed;
	// dspeed *= (5280.0 / 3600.0); // convert to feet/second
	// return dspeed
	// / (samples.elementAt(iLowestSpeed).time - samples
	// .elementAt(iBrake).time);
	// }
	//
	// double speedMean() {
	// return speeds.mean();
	// }
	//
	// double speedSD() {
	// return speeds.stddev();
	// }
	//
	// double speedMin() {
	// return speeds.min();
	// }
	//
	// double speedMax() {
	// return speeds.max();
	// }
	//
	// double accelMean() {
	// return accels.mean();
	// }
	//
	// double accelSD() {
	// return accels.stddev();
	// }
	//
	// double accelMin() {
	// return accels.min();
	// }
	//
	// double accelMax() {
	// return accels.max();
	// }
	//
	// double accelMX() {
	// return accels.meanCrossings();
	// } // / totalTime(); }
	//
	// double accelSDX() {
	// return accels.stddevCrossings(1.0);
	// }
	//
	// double brakeCount() {
	// return brakes.nonZeroRuns();
	// }
	//
	// double brakeMax() {
	// return brakes.max();
	// }
	//
	// double steerMean() {
	// return steers.mean();
	// }
	//
	// double steerSD() {
	// return steers.stddev();
	// }
	//
	// double steerMX() {
	// return steers.meanCrossings();
	// } // / totalTime(); }
	//
	// double steerSDX() {
	// return steers.stddevCrossings(1.0);
	// }
	//
	// double lateralMean() {
	// return laterals.mean();
	// }
	//
	// double lateralSD() {
	// return laterals.stddev();
	// }
	//
	// double lateralMin() {
	// return laterals.min();
	// }
	//
	// double lateralMax() {
	// return laterals.max();
	// }
	//
	// double lateralRMSE() {
	// return laterals.rmse();
	// }
	//
	// int lateralDepart() {
	// int numDepartures = 0;
	// boolean inDeparture = false;
	// for (int i = 0; i < laterals.size(); i++) {
	// double x = Math.abs(laterals.get(i));
	// if (!inDeparture && x > laneDepartThreshold) {
	// numDepartures++;
	// inDeparture = true;
	// } else if (inDeparture && x < laneDepartThreshold)
	// inDeparture = false;
	// }
	// return numDepartures;
	// }
	//
	// double lateralDepartTime() {
	// double departureTime = 0;
	// boolean inDeparture = false;
	// for (int i = 0; i < laterals.size(); i++) {
	// double x = Math.abs(laterals.get(i));
	// if (!inDeparture && x > laneDepartThreshold) {
	// inDeparture = true;
	// } else if (inDeparture && x < laneDepartThreshold)
	// inDeparture = false;
	// if (inDeparture && (i + 1 < samples.size()))
	// departureTime += samples.elementAt(i + 1).time
	// - samples.elementAt(i).time;
	// }
	// return departureTime;
	// }
	//
	// double taskTime() {
	// return totalTime() - extraTime;
	// }
	//
	// Vector<SubSeg> segmentTime() {
	// double segmentSize = 5.0; // seconds
	// Vector<SubSeg> v = new Vector<SubSeg>();
	// SubSeg ss = new SubSeg();
	// double start = samples.elementAt(0).time;
	// for (int i = 0; i < samples.size(); i++) {
	// Sample s = samples.elementAt(i);
	// if (s.time > start + segmentSize) {
	// v.add(ss);
	// ss = new SubSeg();
	// start += segmentSize;
	// }
	// ss.speeds.add(s.speed);
	// ss.steers.add(s.steer);
	// }
	// if (ss.speeds.size() > 0)
	// v.add(ss);
	// return v;
	// }
	//
	// Vector<SubSeg> segmentDistance() {
	// // int segments = 40;
	// Vector<SubSeg> v = new Vector<SubSeg>();
	// SubSeg ss = new SubSeg();
	// double start = samples.elementAt(0).distanceTraveled;
	// // double end = samples.elementAt(samples.size()-1).distanceTraveled;
	// // double delta = (end - start) / segments;
	// double delta = 50; // make segments 50 feet long
	// for (int i = 0; i < samples.size(); i++) {
	// Sample s = samples.elementAt(i);
	// if (s.distanceTraveled > start + delta && i < samples.size() - 1) {
	// v.add(ss);
	// ss = new SubSeg();
	// start += delta;
	// }
	// ss.add(s);
	// }
	// // if (ss.speeds.size() > 0) v.add (ss);
	// return v;
	// }
}
