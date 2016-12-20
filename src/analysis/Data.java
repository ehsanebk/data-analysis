package analysis;

import java.io.File;
import java.util.*;

public class Data {
	int ID;
	int session;
	String condition;
	private long from;
	private long to;
	private Vector<Sample> samples;
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
	
	private int[] bestCasesNum = {3001,3025,3040,3086,
			3206,3232,3256,3275,3386,3408,
			3440,3574,3579,3620};
	private int[] worstCasesNum = {3047,
			3122,3171,3207,3215,3220,
			3309,3311,3359,3421,3512,3570,3674};

	Data(File file) {
		ID = Integer.parseInt(file.getName().substring(0,4));
		session  = Integer.parseInt(file.getName().substring(5, 7));
		if (Utilities.arrayContains(worstCasesNum, ID))
			condition = "WorstCase";
		else
			condition = "BestCase";
		
		
//		System.out.println(Utilities.arrayContains (worstCasesNum, ID));
//		System.out.println(ID);
//		System.out.println(condition);
		
		//samples = new Vector<Sample>();
		sample = new Sample();
		
		Tokenizer t = new Tokenizer(file);
		t.skipLines(4);
		String MetricName;
		while (t.hasMoreTokens()) {			
			t.nextToken();t.nextToken();
			CharSequence str = "STRAIGHT";			
			if (t.nextToken().contains(str)){				
				t.nextToken();t.nextToken();t.nextToken();
				t.nextToken();
				MetricName = t.nextToken();
				switch (MetricName) {
				case "SPEED_MIN" :
					sample.SPEED_MIN += t.nextDouble();
					break;
				case "SPEED_MAX" :
					sample.SPEED_MAX += t.nextDouble();
					break;
				case "SPEED_AVG" :
					sample.SPEED_AVG += t.nextDouble();
					break;
				case "SPEED_STD" :
					sample.SPEED_STD += t.nextDouble();
					break;
				case "ACCEL_MIN" :
					sample.ACCEL_MIN += t.nextDouble();
					break;
				case "ACCEL_MAX" :
					sample.ACCEL_MAX += t.nextDouble();
					break;
				case "ACCEL_AVG" :
					sample.ACCEL_AVG += t.nextDouble();
					break;
				case "ACCEL_STD" :
					sample.ACCEL_STD += t.nextDouble();
					break;
				case "STEER_MIN" :
					sample.STEER_MIN += t.nextDouble();
					break;
				case "STEER_MAX" :
					sample.STEER_MAX += t.nextDouble();
					break;
				case "STEER_AVG" :
					sample.STEER_AVG += t.nextDouble();
					break;
				case "STEER_STD" :
					sample.STEER_STD += t.nextDouble();
					break;
				case "LANEDEV_MIN" :
					sample.LANEDEV_MIN += t.nextDouble();
					break;
				case "LANEDEV_MAX" :
					sample.LANEDEV_MAX += t.nextDouble();
					break;
				case "LANEDEV_AVG" :
					sample.LANEDEV_AVG += t.nextDouble();
					break;
				case "LANEDEV_STD" :
					sample.LANEDEV_STD += t.nextDouble();
					break;
				case "BRAKEPDL_MIN" :
					sample.BRAKEPDL_MIN += t.nextDouble();
					break;
				case "BRAKEPDL_MAX" :
					sample.BRAKEPDL_MAX += t.nextDouble();
					break;
				case "ACCELPDL_MIN" :
					sample.ACCELPDL_MIN += t.nextDouble();
					break;
				case "ACCELPDL_MAX" :
					sample.ACCELPDL_MAX += t.nextDouble();
					break;
				case "BRAKEPDL_COUNT" :
					sample.BRAKEPDL_COUNT += t.nextDouble();
					break;
				case "TTBRAKE00" :
					sample.TTBRAKE00 += t.nextDouble();
					break;
				case "TTACCREL00" :
					sample.TTACCREL00 += t.nextDouble();
					break;
				case "MPG_AVG" :
					sample.MPG_AVG += t.nextDouble();
					break;
				case "FUELUSED" :
					sample.FUELUSED += t.nextDouble();
					break;

				default:
					break;
				}
				
			}
			else
				t.skipLine();
		}
		
		
		// Getting the average :  there are 10 straight segments so the value is divided by 10
		sample.SPEED_MIN /= 10 ;
		sample.SPEED_MAX /= 10 ;
		sample.SPEED_AVG /= 10 ;
		sample.SPEED_STD /= 10 ;
		sample.ACCEL_MIN /= 10 ;
		sample.ACCEL_MAX /= 10 ;
		sample.ACCEL_AVG /= 10 ;
		sample.ACCEL_STD /= 10 ;
		sample.STEER_MIN /= 10 ;
		sample.STEER_MAX /= 10 ;
		sample.STEER_AVG /= 10 ;
		sample.STEER_STD /= 10 ;
		sample.LANEDEV_MIN /= 10 ;
		sample.LANEDEV_MAX /= 10 ;
		sample.LANEDEV_AVG /= 10 ;
		sample.LANEDEV_STD /= 10 ;
		sample.BRAKEPDL_MIN /= 10 ;
		sample.BRAKEPDL_MAX /= 10 ;
		sample.ACCELPDL_MIN /= 10 ;
		sample.ACCELPDL_MAX /= 10 ;
		sample.BRAKEPDL_COUNT /= 10 ;
		sample.TTBRAKE00 /= 10 ;
		sample.TTACCREL00 /= 10 ;
		sample.MPG_AVG /= 10 ;
		sample.FUELUSED /= 10 ;

//		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
//		System.out.println(t.nextDouble());
//		
//		t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();t.nextToken();
//		System.out.println(t.nextDouble());
		
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
