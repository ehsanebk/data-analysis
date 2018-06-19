package vanDongen;

import java.util.Date;
import java.util.Vector;
import analysis.Values;

enum pre_post {Pre, Post};

public class PVT_session {

	/**
	 *  session numbers : sNumber
	 * 
	 *     time points: 1	2	3	4		5	6	7	8
	 *     ––––––––––––––––––––––––––––––––––––––––––––––––––
	 *     				4	5	6	7		24	25	26	27
	 *     				8	9	10	11		28	29	30	31
	 *     				12	13	14	15		32	33	34	35
	 *     				16	17	18	19		36	37	38	39
	 *     				20	21	22	23		40	41	42	43
	 *     
	 *     Each session has pre and post values
	 */
	
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
	
	public PVT_session() {
		RT = new Values();
		timeOfReactionsFromStart = new Values();
		//blocks = new Vector<Block>();
	}

	// Sleep attacks at :
	// 3207  S#10 Pre
	// 3440  S#34 Post
	public boolean sleepAttacks(){
		for (int i = 0; i < RT.size(); i++) 
			if ( RT.get(i) == 30000)
				return true;
		return false;

	}
	
	public double getSessionAveAlertResponses(){
		return RT.averageInRange(150, 500);
	}
	
	public double getSessionAveResponses(){
		return RT.average();
	}
	
	public int getSessionNumberOfLapses(){
		int l = 0;
		for (int i = 0; i < RT.size(); i++) 
			if ( RT.get(i) >= 500 && RT.get(i) < 30000)
				l++;
		return l;
	}
	
	/**
	 * @return Log-transformed Signal-to-Noise Ratio (LSNR) approximation
	 */
	public double getSessionLSNRapx(){
		// LSNR_apx = B ((1/N) sum_1^N (1 / RT_i))    B = 3855ms
		int N = 0;
		int B = 3855;
		double sum = 0;
		for (int i = 0; i < RT.size(); i++) 
			if ( RT.get(i) >= 150 && RT.get(i) < 30000){
				sum = sum + 1.0 / RT.get(i);
				N++;
			}
		return B * ((1.0/N) * sum);
	}
	
	public Values getRTblock(int blockNumber){ // starts from 0
		Values v = new Values();
		if (blockNumber == 0)
			v.add(RT.get(0));
		for (int i = 1; i < timeOfReactionsFromStart.size(); i++) 
			if (timeOfReactionsFromStart.get(i-1) > 300.0*(blockNumber)-1 && timeOfReactionsFromStart.get(i-1) <= 300.0*(blockNumber+1)-1){
				if (RT.get(i) < 30000)
					v.add(RT.get(i));
			}
		return v;
	}
	
	/**
	 * @param blockNumber
	 * starts from 0
	 * @return
	 */
	public int getBlockLapses(int blockNumber){
		Values RTblock = getRTblock(blockNumber);
		int l = 0;
		for (int i = 0; i < RTblock.size(); i++) 
			if (RTblock.get(i) >= 500 && RT.get(i) < 30000)
				l++;
		return l;
	}

	/**
	 * starts from 0
	 * @return Log-transformed Signal-to-Noise Ratio (LSNR) approximation
	 */
	public double getBlockLSNRapx(int blockNumber){
		Values RTblock = getRTblock(blockNumber);
		// LSNR_apx = B ((1/N) sum_1^N (1 / RT_i))    B = 3855ms
		int N = 0;
		int B = 3855;
		double sum = 0;
		for (int i = 0; i < RTblock.size(); i++) 
			if ( RTblock.get(i) >= 150 && RT.get(i) < 30000){
				sum = sum + 1.0 / RTblock.get(i);
				N++;
			}
		return B * ((1.0/N) * sum);
	}

	public double getBlockAveAlertResponses(int blockNumber){
		Values RTblock = getRTblock(blockNumber);
		return RTblock.averageInRange(150, 500);
	}
	
}
