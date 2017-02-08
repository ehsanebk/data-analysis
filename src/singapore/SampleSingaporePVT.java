package singapore;

import analysis.Utilities;
import analysis.Values;

public class SampleSingaporePVT {
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
	
	public SampleSingaporePVT() {
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
	public String getTrialtime() {
		return trialtime.replace("\"", "");
	}
	public Values getRT() {
		return RT;
	}
	
}
