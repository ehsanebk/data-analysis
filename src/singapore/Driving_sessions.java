package singapore;

import java.util.Date;
import analysis.Values;

public class Driving_sessions {
	String protocol;
	String id;
	
	// Timings:  15 min  1st_hour 2nd_hour 3rd_hour 4th_hour 5th_hour
	//			 0-1 	 2-3 	  4-5 	   6-7 	    8-9 	 10-11
	// ** if a time does not exist it will be NULL at the end
	
	DrivingTrial[] trials =  new DrivingTrial[6];
	
	public Driving_sessions() {
		
	}
	
	
	/* (non-Javadoc)
	 * for testing the timings 
	 */
	public String toStringTiming (){
		String s = "";
		s += id + "->";
		for (int i = 0; i < trials.length; i++) {
			s+= trials[i].startTime + "==" + trials[i].stopTime + "|||";
		}
		return s;
	}
	
	
	
	class DrivingTrial{
		Date startTime;
		Date stopTime;
		Values lanePos;
		double LP_STD;
		int frameCount;
		
		public DrivingTrial() {
			lanePos = new Values();
		}
	}

}
