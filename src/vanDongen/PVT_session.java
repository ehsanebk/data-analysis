package vanDongen;

import java.util.Date;
import java.util.Vector;

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
	Values timeOfReactionsFromStart;
	Vector<Block> blocks;
	
	public PVT_session() {
		RT = new Values();
		//blocks = new Vector<Block>();
	}
	
	public double getSessionAveAlertResponses(){
		return RT.averageInRange(150, 500);
	}
	
	public int getSessionNumberOfLapses(){
		int l = 0;
		for (int i = 0; i < timeOfReactionsFromStart.size(); i++) 
			if (timeOfReactionsFromStart.get(i) <= 299.0 && RT.get(i) >= 500)
				l++;
		return l;
	}
	public double getBlock1AveAlertResponses(){
		return RT.averageInRange(150, 500);
	}
	
	public Values RT_Block(int blockNumber){ // starts from 1
		Values v = new Values();
		if (blockNumber == 1)
			v.add(RT.get(0));
		for (int i = 1; i < timeOfReactionsFromStart.size(); i++) 
			if (timeOfReactionsFromStart.get(i-1) > 300.0*(blockNumber-1)-1 && timeOfReactionsFromStart.get(i-1) <= 300.0*(blockNumber)-1){
				v.add(RT.get(i));
			}
		return v;
	}
	
	public int getBlockLapses(int blockNumber){
		int l = 0;
		for (int i = 0; i < RT.size(); i++) 
			if (RT.get(i) >= 500)
				l++;
		return l;
	}
	
	public double getBlockAveAlertResponses(int blockNumber){
		return RT.averageInRange(150, 500);
	}
	
	
	// 5-min blocks
	public class Block {
		Values RT = new Values();
		double startTime;
		double totalBlockTime;
		int alertResponse[] = new int[35]; // Alert responses (150-500ms, 10ms
		// intervals )
		
		int numberOfResponses = 0;
		int sleepAttacks = 0;
		
//		public int[] alertResponseProportion(){
//			
//		}
		
		public int getNumberOfFalseAlerts(){
			int c = 0;
			for (int i = 0; i < RT.size(); i++) {
				double r = RT.get(i);
				if (r < .150)
					c++;
			}
			return c;
		}

		public int getNumberOfLapses(){
			int l = 0;
			for (int i = 0; i < RT.size(); i++) 
				if (RT.get(i) >= 500)
					l++;
			return l;
		}
		
		public double getFalseAlertProportion() {
			return (double)getNumberOfFalseAlerts()/ RT.size();
		}
		public double getLapsesProportion() {
			return (double)getNumberOfLapses() / RT.size();
		}
		public double getMeanAlertReactionTimes() {
			Values Alert = new Values();
			for (int i = 0; i < RT.size(); i++) {
				double r = RT.get(i);
				if (r <= .500 && r >= .150)
					Alert.add(r);
			}
			return Alert.average();
		}
	}

	
}
