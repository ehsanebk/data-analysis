package vanDongen;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Vector;
import analysis.Tokenizer;
import analysis.Utilities;
import analysis.Values;

public class PVT_sessions {
	
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
	Conditions condition;
	
	Vector<PVT_session> sessions;
	
	private static String[] bestCasesNum = {"3001","3025","3040","3086",
			"3206","3232","3256","3275","3386","3408",
			"3440","3574","3579","3620"};
	private static String[] worstCasesNum = {"3047",
			"3122","3171","3207","3215","3220",
			"3309","3311","3359","3421","3512","3570","3674"};
	
	PVT_sessions() {

		sessions = new Vector<PVT_session>();
	}
	
	PVT_session getSessionByNumber(int sNumber, pre_post p){
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return session;
		}
		return null;	
	}
	
	Object getSessionsNumber(int sNumber, pre_post p) {
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return session.sessionNumber;
		}
		return null;	
	}
	String getSessionsTime(int sNumber, pre_post p) {
		SimpleDateFormat timeFormat = new SimpleDateFormat ("MM/dd/yy HH:mm"); // for parsing date formats
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return timeFormat.format(session.time);
		}
		return null;	
	}
	Object getSessionsLapses(int sNumber, pre_post p) {
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return session.getSessionNumberOfLapses();
		}
		return null;	
	}
	
	Object getSessionsFalseStarts(int sNumber, pre_post p) {
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return session.getSessionNumberOfFalseStarts();
		}
		return null;	
	}
	
	Object getSessionsPercentOfLapses(int sNumber, pre_post p) {
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return (session.getSessionNumberOfLapses()/(double)session.RT.size()) * 100;
		}
		return null;	
	}
	
	Object getSessionsPercentOfFalseStarts(int sNumber, pre_post p) {
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return (session.getSessionNumberOfFalseStarts()/(double)session.RT.size()) * 100;
		}
		return null;	
	}
	
	Object getSessionsAveAlertResponses(int sNumber, pre_post p) {
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return session.getSessionAveAlertResponses();
		}
		return null;	
	}
	
	Object getSessionsMedianAlertResponses(int sNumber, pre_post p) {
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return session.getSessionMedianAlertResponses();
		}
		return null;	
	}
	
	Object getSessionsAveResponses(int sNumber, pre_post p) {
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return session.getSessionAveResponses();
		}
		return null;	
	}
	
	Object getSessionsMeidanResponses(int sNumber, pre_post p) {
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return session.getSessionMeidanResponses();
		}
		return null;	
	}
	
	Object getSessionsLSNRapx(int sNumber, pre_post p) {
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return session.getSessionLSNRapx();
		}
		return null;	
	}
	
	Object getSessionsBlockLapses(int sNumber, int blockNumber, pre_post p) {
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return session.getBlockLapses(blockNumber);
		}
		return null;	
	}
	
	Object getSessionsBlockLSNRapx(int sNumber, int blockNumber, pre_post p) {
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return session.getBlockLSNRapx(blockNumber);
		}
		return null;	
	}

	Object getSessionsBlockProportionOfLapses(int sNumber, int blockNumber, pre_post p) {
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber && session.pre_post.equals(p))
				return session.getBlockLapses(blockNumber)/(double)session.getRTblock(blockNumber).size();
		}
		return null;	
	}

	public double getNumberOfLapses_AveOnTimePoints(int tp,  pre_post p) {
		Values lapses = new Values();
		for (int i = 0; i < sessions.size(); i++) 
			if (sessions.get(i).timePoint == tp && sessions.get(i).pre_post == p && sessions.get(i).RT.size() > 0 )
				lapses.add(sessions.get(i).getSessionNumberOfLapses());
		return lapses.average();
	}
	
	public double getLSNRapx_AveOnTimePoints(int tp,  pre_post p) {
		Values LSNRapx = new Values();
		for (int i = 0; i < sessions.size(); i++) 
			if (sessions.get(i).timePoint == tp && sessions.get(i).pre_post == p && sessions.get(i).RT.size() > 0 )
				LSNRapx.add(sessions.get(i).getSessionLSNRapx());
		return LSNRapx.average();
	}
	
	public double getNumberOfLapsesAtEachBlock_AveOnTimePoints(int tp,int blockNumber,  pre_post p) {
		Values lapses = new Values();
		// skipping the first response time ??
		for (int i = 0; i < sessions.size(); i++) 
			if (sessions.get(i).timePoint == tp && sessions.get(i).pre_post == p && sessions.get(i).RT.size() > 0)
				lapses.add(sessions.get(i).getBlockLapses(blockNumber));
		return lapses.average();
	}
	
	public double getLSNRapxAtEachBlock_AveOnTimePoints(int tp,int blockNumber,  pre_post p) {
		Values LSNRapx = new Values();
		// skipping the first response time ??
		for (int i = 0; i < sessions.size(); i++) 
			if (sessions.get(i).timePoint == tp && sessions.get(i).pre_post == p && sessions.get(i).RT.size() > 0)
				LSNRapx.add(sessions.get(i).getBlockLSNRapx(blockNumber));
		return LSNRapx.average();
	}
	
	public double getProportionOfLapses_AveOnTimePoints(int tp,  pre_post p) {
		Values lapses = new Values();
		for (int i = 0; i < sessions.size(); i++) 
			if (sessions.get(i).timePoint == tp && sessions.get(i).pre_post == p && sessions.get(i).RT.size() > 0 )
				lapses.add((double)sessions.get(i).getSessionNumberOfLapses()/sessions.get(i).RT.size());
		return lapses.average();
	}
	
	public double getProportionOfLapsesAtEachBlock_AveOnTimePoints(int tp,int blockNumber,  pre_post p) {
		Values lapses = new Values();
		// skipping the first response time ??
		for (int i = 0; i < sessions.size(); i++) 
			if (sessions.get(i).timePoint == tp && sessions.get(i).pre_post == p && sessions.get(i).RT.size() > 0)
				lapses.add((double)sessions.get(i).getBlockLapses(blockNumber)/sessions.get(i).getRTblock(blockNumber).size());
		return lapses.average();
	}
	
	
	void process(File file){
		
		SimpleDateFormat dateParser = new SimpleDateFormat ("MM/dd/yyhhmm"); // for parsing date formats
		ID = file.getName().substring(0,4);
		System.out.println("Processing PVT file : " + ID);
		if (Utilities.arrayContains(worstCasesNum, ID))
			condition = Conditions.WorstCase;
		else
			condition = Conditions.BestCase;
		
		Tokenizer t = new Tokenizer(file);
		int counter=0;
		while(t.hasMoreTokens()){
			String line = t.readNextLine();
			if (line.contains("PVT DATA")){
				counter++;
				PVT_session newSession = new PVT_session();
				newSession.ID = ID;
				newSession.trialNumberInFile = counter;
				t.skipLines(6);
				String trialDate = t.readNextLine().substring(17, 24);
				String trialTime = t.readNextLine().substring(16, 20);
				
				try {
					newSession.time = dateParser.parse(trialDate+trialTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				//Block newBlock = newSession.new Block();
				//newBlock.startTime = 0;
				
				t.skipLines(6);
				line = t.readNextLine();
				String rt = line.substring(1,5);
				rt = rt.replaceAll("\\s+", "");
				String timeFromStart = line.substring(14,20);
				timeFromStart = timeFromStart.replaceAll("\\s+", "");
				while (true){ // dropping the first RT!!!
					line = t.readNextLine();
					rt = line.substring(0,5);
					rt = rt.replaceAll("\\s+", "");
					if (rt.equals("0"))
						break;
					timeFromStart = line.substring(14,20);
					timeFromStart = timeFromStart.replaceAll("\\s+", "");
					
					newSession.RT.add(Integer.valueOf(rt).intValue()); // changing to int
					newSession.timeOfReactionsFromStart.add(Double.valueOf(timeFromStart).doubleValue());
					//newBlock.RT.add(Integer.valueOf(rt).intValue()); // changing to int
				}
				
				// Finding the session number ,,,, sn = session number start from 4 to 44
				int[][] sn;
				if (ID.equals("3207")){
					sn = new int [][] 
						   {{10,11},{12,13},{14,15},{16,17},
							{18,19},{20,21},{22,23},{24,25},
							{27,28},{29,30},{31,32},{33,34}, 
							{35,36},{37,38},{39,40},{41,42}, 
							{44,45},{46,47},{48,49},{50,51}, 
							{56,57},{58,59},{60,61},{62,63},
							{64,65},{66,67},{68,69},{70,71},
							{72,73},{74,75},{76,77},{78,79},
							{80,81},{82,83},{84,85},{86,87},
							{89,90},{91,92},{93,94},{95,96}};
				}
				else if (ID.equals("3232")){
					sn = new int [][] 
						   {{10,11},{12,13},{14,15},{16,17},
							{18,19},{20,21},{22,23},{24,25},
							{26,27},{28,29},{30,31},{32,33}, 
							{34,35},{	  },{37,38},{39,40}, 
							{42,43},{44,45},{46,47},{48,49}, 
							{54,55},{56,57},{58,59},{60,61},
							{62,63},{64,65},{66,67},{68,69},
							{70,71},{72,73},{74,75},{76,77},
							{78,79},{80,81},{82,  },{83,84},
							{86,87},{88,89},{90,91},{92,93}};
				}
				else{
					sn = new int [][] 
						   {{10,11},{12,13},{14,15},{16,17},
							{18,19},{20,21},{22,23},{24,25}, 
							{26,27},{28,29},{30,31},{32,33}, 
							{34,35},{36,37},{38,39},{40,41}, 
							{43,44},{45,46},{47,48},{49,50}, 
							{55,56},{57,58},{59,60},{61,62},
							{63,64},{65,66},{67,68},{69,70},
							{71,72},{73,74},{75,76},{77,78},
							{79,80},{81,82},{83,84},{85,86},
							{88,89},{90,91},{92,93},{94,95}};
				}

				for (int i = 0; i < sn.length; i++) {
					if (contains(sn[i],counter))
						newSession.sessionNumber = i+4;
				}
				
				// Finding the time points ,,, tp  = Time Point
				int[] tp1_pre=new int[5];int[] tp1_post=new int[5];
				int[] tp2_pre=new int[5];int[] tp2_post=new int[5];
				int[] tp3_pre=new int[5];int[] tp3_post=new int[5];
				int[] tp4_pre=new int[5];int[] tp4_post=new int[5];
						              
				int[] tp6_pre=new int[5];int[] tp6_post=new int[5];
				int[] tp7_pre=new int[5];int[] tp7_post=new int[5];
				int[] tp8_pre=new int[5];int[] tp8_post=new int[5];
				int[] tp9_pre=new int[5];int[] tp9_post=new int[5];
				
				
				if (ID.equals("3207")){
					tp1_pre = new int[]	{10,18,27,35,44};
					tp1_post= new int[]	{11,19,28,36,45};
					tp2_pre=  new int[]	{12,20,29,37,46};
					tp2_post= new int[]	{13,21,30,38,47};
					tp3_pre=  new int[]	{14,22,31,39,48};
					tp3_post= new int[]	{15,23,32,40,49};
					tp4_pre=  new int[]	{16,24,33,41,50};
					tp4_post= new int[]	{17,25,34,42,51};
					
					tp6_pre=  new int[]	{56,64,72,80,89};
					tp6_post= new int[]	{57,65,73,81,90};
					tp7_pre=  new int[]	{58,66,74,82,91};
					tp7_post= new int[]	{59,67,75,83,92};
					tp8_pre=  new int[]	{60,68,76,84,93};
					tp8_post= new int[]	{61,69,77,85,94};
					tp9_pre=  new int[]	{62,70,78,86,95};
					tp9_post= new int[]	{63,71,79,87,96};
				}
				else if (ID.equals("3232")){
					tp1_pre = new int[]	{10,18,26,34,42};
					tp1_post= new int[]	{11,19,27,35,43};
					tp2_pre=  new int[]	{12,20,28   ,44};
					tp2_post= new int[]	{13,21,29   ,45};
					tp3_pre=  new int[]	{14,22,30,37,46};
					tp3_post= new int[]	{15,23,31,38,47};
					tp4_pre=  new int[]	{16,24,32,39,48};
					tp4_post= new int[]	{17,25,33,40,49};
					
					tp6_pre=  new int[]	{54,62,70,78,86};
					tp6_post= new int[]	{55,63,71,79,87};
					tp7_pre=  new int[]	{56,64,72,80,88};
					tp7_post= new int[]	{57,65,73,81,89};
					tp8_pre=  new int[]	{58,66,74,82,90};
					tp8_post= new int[]	{59,67,75   ,91};
					tp9_pre=  new int[]	{60,68,76,83,92};
					tp9_post= new int[]	{61,69,77,84,93};
				}
				else {
					tp1_pre = new int[]	{10,18,26,34,43};
					tp1_post= new int[]	{11,19,27,35,44};
					tp2_pre=  new int[]	{12,20,28,36,45};
					tp2_post= new int[]	{13,21,29,37,46};
					tp3_pre=  new int[]	{14,22,30,38,47};
					tp3_post= new int[]	{15,23,31,39,48};
					tp4_pre=  new int[]	{16,24,32,40,49};
					tp4_post= new int[]	{17,25,33,41,50};
					
					tp6_pre=  new int[]	{55,63,71,79,88};
					tp6_post= new int[]	{56,64,72,80,89};
					tp7_pre=  new int[]	{57,65,73,81,90};
					tp7_post= new int[]	{58,66,74,82,91};
					tp8_pre=  new int[]	{59,67,75,83,92};
					tp8_post= new int[]	{60,68,76,84,93};
					tp9_pre=  new int[]	{61,69,77,85,94};
					tp9_post= new int[]	{62,70,78,86,95};
				}
				
				if (contains(tp1_pre,counter)){
					newSession.pre_post = pre_post.Pre;
					newSession.timePoint = 1;
					sessions.add(newSession);
				}
				else if(contains(tp1_post,counter)){
					newSession.pre_post = pre_post.Post;
					newSession.timePoint = 1;
					sessions.add(newSession);
				}
				else if(contains(tp2_pre,counter)){
					newSession.pre_post = pre_post.Pre;
					newSession.timePoint = 2;
					sessions.add(newSession);
				}
				else if(contains(tp2_post,counter)){
					newSession.pre_post = pre_post.Post;
					newSession.timePoint = 2;
					sessions.add(newSession);
				}
				else if(contains(tp3_pre,counter)){
					newSession.pre_post = pre_post.Pre;
					newSession.timePoint = 3;
					sessions.add(newSession);
				}
				else if(contains(tp3_post,counter)){
					newSession.pre_post = pre_post.Post;
					newSession.timePoint = 3;
					sessions.add(newSession);
				}
				else if(contains(tp4_pre,counter)){
					newSession.pre_post = pre_post.Pre;
					newSession.timePoint = 4;
					sessions.add(newSession);
				}
				else if(contains(tp4_post,counter)){
					newSession.pre_post = pre_post.Post;
					newSession.timePoint = 4;
					sessions.add(newSession);
				}
				// 34 hour Break 
				if (contains(tp6_pre,counter)){
					newSession.pre_post = pre_post.Pre;
					newSession.timePoint = 5;
					sessions.add(newSession);
				}
				else if(contains(tp6_post,counter)){
					newSession.pre_post = pre_post.Post;
					newSession.timePoint = 5;
					sessions.add(newSession);
				}
				else if(contains(tp7_pre,counter)){
					newSession.pre_post = pre_post.Pre;
					newSession.timePoint = 6;
					sessions.add(newSession);
				}
				else if(contains(tp7_post,counter)){
					newSession.pre_post = pre_post.Post;
					newSession.timePoint = 6;
					sessions.add(newSession);
				}
				else if(contains(tp8_pre,counter)){
					newSession.pre_post = pre_post.Pre;
					newSession.timePoint = 7;
					sessions.add(newSession);
				}
				else if(contains(tp8_post,counter)){
					newSession.pre_post = pre_post.Post;
					newSession.timePoint = 7;
					sessions.add(newSession);
				}
				else if(contains(tp9_pre,counter)){
					newSession.pre_post = pre_post.Pre;
					newSession.timePoint = 8;
					sessions.add(newSession);
				}
				else if(contains(tp9_post,counter)){
					newSession.pre_post = pre_post.Post;
					newSession.timePoint = 8;
					sessions.add(newSession);
				}
				
				
			}			
		}
	}
	
	public boolean contains(final int[] array, final int key) {
		for(int x:array) 
	        if (x == key) 
	            return true;
		return false;

	    
	}
	
}
