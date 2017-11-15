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
	
	session getTrail(int i){
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
		String trial;
		Date trialTime;
		int pvtsn;
		Values time;
		Values RT;
		SimpleDateFormat timeParser = 
				new SimpleDateFormat ("hhmm");
		SimpleDateFormat dateParser = 
				new SimpleDateFormat ("MM/dd/yy");

		public session() {
			time = new Values();
			RT = new Values();
		}


		// for the function below the counter starts from 1 because 
		// the first reaction time is not being considered in the calculations

		public int getLapses() {
			int counter = 0;
			for (int i = 1; i < RT.size(); i++) {
				if (RT.get(i) > 500 )
					counter++;
			}
			return counter;
		}

		public int getFalseAlerts(){
			int counter = 0;
			for (int i = 1; i < RT.size(); i++) {
				if (RT.get(i) < 150 )
					counter++;
			}
			return counter;
		}

		public double mean_AlertResponses(){
			Values r = new Values();
			for (int i = 1; i < RT.size(); i++) {
				if (RT.get(i) >= 150 && RT.get(i) <= 500 )
					r.add(RT.get(i));;
			}
			return r.mean();
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
		public String getTrial() {
			return trial;
		}
		
	}
}
