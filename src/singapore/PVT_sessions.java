package singapore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import analysis.Utilities;
import analysis.Values;

/**
 * @author ehsanebk
 *
 *	In the reaction times the first response is not being considered. **Not sure why.
 *
 */
public class PVT_sessions {

	String protocol;
    String id;
	Vector<session> sessions;
	public PVT_sessions() {
		sessions = new Vector<session>();
	}
	
	public session getTrail(int i){
		for (session s: sessions) {
			if (Integer.valueOf(s.trial).intValue() == i )
				return s;
		}
		return null;
	}
	
	public String getProtocol() {
		return protocol.replace("\"", "");
	}
	public String getId() {
		return id;
	}
	
	class session{
		String pre;	
		String post;
		int trial;
		Date trialTime;
		int pvtsn;
		Values time;
		Values RT;
		SimpleDateFormat timeParser = 
				new SimpleDateFormat ("HH:mm");
		SimpleDateFormat dateParser = 
				new SimpleDateFormat ("MM/dd/yy");

		public session() {
			time = new Values();
			RT = new Values();
		}


		// for the function below the counter starts from 1 because 
		// the first reaction time is not being considered in the calculations

		public int getNumberOfLapses() {
			int counter = 0;
			for (int i = 1; i < RT.size(); i++) {
				if (RT.get(i) > 500 && RT.get(i) < 29999)
					counter++;
			}
			return counter;
		}
		
		public double getProportionOfLapses() {
			return getNumberOfLapses()/(double)(RT.size()-1);
		}
		
		public int getNumberOfFalseAlerts(){
			int counter = 0;
			for (int i = 1; i < RT.size(); i++) {
				if (RT.get(i) < 150 )
					counter++;
			}
			return counter;
		}

		public double getProportionOfFalseStarts(){
			return getNumberOfFalseAlerts()/(double)(RT.size()-1);
		}
		
		public double mean_AlertResponses(){
			Values r = new Values();
			for (int i = 1; i < RT.size(); i++) {
				if (RT.get(i) >= 150 && RT.get(i) <= 500 )
					r.add(RT.get(i));;
			}
			return r.mean();
		}
		
		public double getSessionMedianAlertResponses(){
			return RT.medianInRange(150, 500);
		}
		
		
		public double slowest_AlertResponses(){
			Values r = new Values();
			for (int i = 1; i < RT.size(); i++) {
				if (RT.get(i) >= 150 && RT.get(i) <= 500 )
					r.add(RT.get(i));;
			}
			return r.min();
		}
		public double fastest_AlertResponses(){
			Values r = new Values();
			for (int i = 1; i < RT.size(); i++) {
				if (RT.get(i) >= 150 && RT.get(i) <= 500 )
					r.add(RT.get(i));;
			}
			return r.max();
		}
		public double SD_AlertResponses(){
			Values r = new Values();
			for (int i = 0; i < RT.size(); i++) {
				if (RT.get(i) >= 150 && RT.get(i) <= 500 )
					r.add(RT.get(i));;
			}
			return r.stddev();
		}

		
		public String getTrialdate() {
			return dateParser.format(trialTime);
		}
		public String getTrialtime() {
			return timeParser.format(trialTime);
		}
		
		public Values getRT() {
			return RT;
		}
		public int getTrial() {
			return trial;
		}
		
	}
}
