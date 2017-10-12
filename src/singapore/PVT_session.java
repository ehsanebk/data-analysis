package singapore;

import analysis.Utilities;
import analysis.Values;

/**
 * @author ehsanebk
 *
 *	In the reaction times the first response is not being considered. **Not sure why.
 *
 */
public class PVT_session {
	String pre;	
	String post;
	String protocol;
	String id;
	String trial;
	String trialdate;
	String trialtime;
	int pvtsn;
	Values time;
	Values RT;
	
	public PVT_session() {
		time = new Values();
		RT = new Values();
	}
	
	
	public String toString() {
		return pre + "\t" +
				post + "\t\t" +
				protocol + "\t\t\t" +
				id + "\t\t" +
				trial + "\t\t" +
				trialdate + "\t" +
				trialtime + "\t\t" +
				pvtsn + "\t" +
				Utilities.df1.format(mean_AlertResponses());
				
				
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
	
	public String getProtocol() {
		return protocol.replace("\"", "");
	}
	public String getId() {
		return id;
	}
	public String getTrialdate() {
		return trialdate.replace("\"", "");
	}
	public String getTrialtimeString() {
		return trialtime.replace("\"", "");
	}
	public int getTrialtime() {
		return Integer.valueOf(trialtime.replace("\"", "")).intValue();
	}
	public Values getRT() {
		return RT;
	}
	public String getTrial() {
		return trial;
	}
}
