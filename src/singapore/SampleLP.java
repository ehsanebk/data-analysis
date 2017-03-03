package singapore;

import java.util.Vector;
import analysis.Values;
import singapore.Segment;

/**
 * @author ehsanebk
 *
 */
public class SampleLP {

	String protocol;
	String id;
	String trial;
	String trialdate;
	String trialtime;
	int numberOfValidSegments = 0;

	Vector<Segment> segments = new Vector<Segment>();
	Vector<Integer> MinMaxSeries = new Vector<Integer>();
	Vector<Double> distanceBetweenMinMaxSeries = new Vector<Double>();
	Vector<Long> longestDistanceBetweenMinMaxSeries = new Vector<Long>();
	
	public double numberOfMinMaxAve() {
		Values ave = new Values();
		for (int i = 0; i < segments.size(); i++) {
			ave.add(segments.get(i).numberOfMinMax);
		}
		return ave.average();
	}

	public double distanceBetweenMinMaxAve() {

		Values ave = new Values();
		for (int i = 0; i < segments.size(); i++) {
			ave.add(segments.get(i).averageTimeBetweenMaxMin);
		}
		return ave.average();
	}
	public double longestDistanceBetweenMinMaxAve() {

		Values ave = new Values();
		for (int i = 0; i < segments.size(); i++) {
			ave.add(segments.get(i).longestTimeBetweenMaxMin);
		}
		return ave.average();
	}
	
}
