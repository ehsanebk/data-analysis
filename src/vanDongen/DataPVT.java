package vanDongen;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import analysis.Tokenizer;
import analysis.Utilities;

public class DataPVT {
	String ID;
	Conditions condition;
	
	Vector<SessionPVT> sessions;
	
	private static String[] bestCasesNum = {"3001","3025","3040","3086",
			"3206","3232","3256","3275","3386","3408",
			"3440","3574","3579","3620"};
	private static String[] worstCasesNum = {"3047",
			"3122","3171","3207","3215","3220",
			"3309","3311","3359","3421","3512","3570","3674"};
	
	DataPVT() {

		sessions = new Vector<SessionPVT>();
	}
	
	SessionPVT getSessionByNumber(int sNumber){
		for (Iterator<SessionPVT> iterator = sessions.iterator(); iterator.hasNext();) {
			SessionPVT session = (SessionPVT) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session;
		}
		return null;		
	}
	
	void process(File file){
		
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
				SessionPVT newSession = new SessionPVT();
				newSession.trialNumberInFile = counter;
				t.skipLines(6);
				newSession.trialDate = t.readNextLine().substring(17, 24);
				newSession.trialTime = t.readNextLine().substring(16, 20);
				t.skipLines(6);
				String rt = t.readNextLine().substring(2,5);
				rt = rt.replaceAll("\\s+", "");
				while (!rt.equals("0")){
					newSession.RT.add(Integer.valueOf(rt).intValue()); // changing to int
					rt = t.readNextLine().substring(2,5);
					rt = rt.replaceAll("\\s+", "");
				}
				int[] tp1_pre;int[] tp1_post;
				int[] tp2_pre;int[] tp2_post;
				int[] tp3_pre;int[] tp3_post;
				int[] tp4_pre;int[] tp4_post;
						              
				int[] tp6_pre;int[] tp6_post;
				int[] tp7_pre;int[] tp7_post;
				int[] tp8_pre;int[] tp8_post;
				int[] tp9_pre;int[] tp9_post;
				
				if (!ID.equals("3207") || !ID.equals("3232") ) {
					// tp  = Time Point
					tp1_pre= 	{10,18,26,34,43};
					tp1_post= {11,19,27,35,44};
					tp2_pre= 	{12,20,28,36,45};
					tp2_post= {13,21,29,37,46};
					tp3_pre= 	{14,22,30,38,47};
					tp3_post= {15,23,31,39,48};
					tp4_pre= 	{16,24,32,40,49};
					tp4_post= {17,25,33,41,50};
					
					tp6_pre= 	{55,63,71,79,88};
					tp6_post= {56,64,72,80,89};
					tp7_pre= 	{57,65,73,81,90};
					tp7_post= {58,66,74,82,91};
					tp8_pre= 	{59,67,75,83,92};
					tp8_post= {60,68,76,84,93};
					tp9_pre= 	{61,69,77,85,94};
					tp9_post= {62,70,78,86,95};
				}
					
					if (Arrays.asList(tp1_pre).contains(counter)){
						newSession.pre_post = pre_post.Pre;
						newSession.timePoint = 1;
						sessions.add(newSession);
					}
					else if(Arrays.asList(tp1_post).contains(counter)){
						newSession.pre_post = pre_post.Post;
						newSession.timePoint = 1;
						sessions.add(newSession);
					}
					else if(Arrays.asList(tp2_pre).contains(counter)){
						newSession.pre_post = pre_post.Pre;
						newSession.timePoint = 2;
						sessions.add(newSession);
					}
					else if(Arrays.asList(tp2_post).contains(counter)){
						newSession.pre_post = pre_post.Post;
						newSession.timePoint = 2;
						sessions.add(newSession);
					}
					else if(Arrays.asList(tp3_pre).contains(counter)){
						newSession.pre_post = pre_post.Pre;
						newSession.timePoint = 3;
						sessions.add(newSession);
					}
					else if(Arrays.asList(tp3_post).contains(counter)){
						newSession.pre_post = pre_post.Post;
						newSession.timePoint = 3;
						sessions.add(newSession);
					}
					else if(Arrays.asList(tp4_pre).contains(counter)){
						newSession.pre_post = pre_post.Pre;
						newSession.timePoint = 4;
						sessions.add(newSession);
					}
					else if(Arrays.asList(tp4_post).contains(counter)){
						newSession.pre_post = pre_post.Post;
						newSession.timePoint = 4;
						sessions.add(newSession);
					}
				
				
				
			}			
		}
	}
}
