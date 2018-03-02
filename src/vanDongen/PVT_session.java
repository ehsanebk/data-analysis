package vanDongen;

import java.util.Date;
import analysis.Values;

enum pre_post {Pre, Post};

public class PVT_session {

	String ID;
	
	pre_post pre_post; // whether the test is pre driving or post.
	int sessionNumber; // Session # is the number of the driving session. 
	int trialNumberInFile; // Trail # is the number of the pvt session by the 
						// order that is been reported in the file.
	int timePoint;  // the time points are in the format of 
					// 1 2 3 4 break 6 7 8 9
//	String trialDate;
//	String trialTime;
	Date time;
	String task;
	String hand;
	Values RT;
	
	public PVT_session() {
		RT = new Values();
	}
	
	public double getSessionAve(){
		return RT.average();
	}
	
	public int getNumberOfLapses(){
		int l = 0;
		for (int i = 0; i < RT.size(); i++) 
			if (RT.get(i) >= 500)
				l++;
		return l;
	}
}
