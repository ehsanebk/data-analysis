package vanDongen;

import analysis.Values;

enum pre_post {Pre, Post};

public class SessionPVT {

	String ID;
	
	pre_post pre_post; // whether the test is pre driving or post.
	String sessionNumber; // Session # is the number of the driving session. 
	int trialNumberInFile; // Trail # is the number of the pvt session by the 
						// order that is been reported in the file.
	int timePoint;  // the time points are in the format of 
					// 1 2 3 4 break 6 7 8 9
	String trialDate;
	String trialTime;
	String task;
	String hand;
	Values RT;
	
	public SessionPVT() {

		RT = new Values();
	}
	
	
	public double getSessionAve(){
		return RT.average();
	}
	
}
