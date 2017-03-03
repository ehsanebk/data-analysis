package singapore;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import analysis.Values;

/**
 * @author ehsanebk
 *
 */
public class Segment {
	// the number of frames in each
	// straight segment that is
	// printed out to the file
	static int validLowerNumberOfFrames = 400; 
	static int validUpperNumberOfFrames = 5000;


	int numberOfFrames;
	int numberOfMinMax;
	double averageTimeBetweenMaxMin; // time in ms
	long longestTimeBetweenMaxMin; //time in ms
	long segmentTime; // total segment time in ms
	List<Integer> MinMixFrameNumbers;

	// lane positions and their time in the frame for each segment
	Values lanePos; // -100 for any value which is not valid
	Vector<Date> timesOfFrames;  // -100 for any value which is not valid
	int startTime;

	public Segment() {
		lanePos = new Values();
		timesOfFrames = new Vector<Date>();
		MinMixFrameNumbers = new Vector<Integer>();
	}

	public boolean valid() {
		return (numberOfFrames > validLowerNumberOfFrames && numberOfFrames < validUpperNumberOfFrames);
	}
}
