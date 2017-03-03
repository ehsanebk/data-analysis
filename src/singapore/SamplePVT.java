package singapore;

import analysis.Utilities;
import analysis.Values;

/**
 * @author ehsanebk
 *
 */
public class SamplePVT {
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
	
	public SamplePVT() {
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
				Utilities.df1.format(RT.average());
				
				
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
