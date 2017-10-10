package vanDongen;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import analysis.Tokenizer;
import analysis.Utilities;
import analysis.Values;

public class PVT_all {
	String ID;
	Conditions condition;
	
	Vector<PVT_session> sessions;
	
	private static String[] bestCasesNum = {"3001","3025","3040","3086",
			"3206","3232","3256","3275","3386","3408",
			"3440","3574","3579","3620"};
	private static String[] worstCasesNum = {"3047",
			"3122","3171","3207","3215","3220",
			"3309","3311","3359","3421","3512","3570","3674"};
	
	PVT_all() {

		sessions = new Vector<PVT_session>();
	}
	
	PVT_session getSessionByNumber(int sNumber){
		for (Iterator<PVT_session> iterator = sessions.iterator(); iterator.hasNext();) {
			PVT_session session = (PVT_session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session;
		}
		return null;		
	}
	
	public double getNumberOfLapses_AveOnTimePoints(int tp) {
		Values lapses = new Values();
		for (int i = 0; i < sessions.size(); i++) 
			if (sessions.get(i).timePoint == tp)
				lapses.add(sessions.get(i).getNumberOfLapses());
		return lapses.average();
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
				PVT_session newSession = new PVT_session();
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
				
				// tp  = Time Point
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
					tp2_pre=  new int[]	{12,20,28,36,44};
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
					newSession.timePoint = 6;
					sessions.add(newSession);
				}
				else if(contains(tp6_post,counter)){
					newSession.pre_post = pre_post.Post;
					newSession.timePoint = 6;
					sessions.add(newSession);
				}
				else if(contains(tp7_pre,counter)){
					newSession.pre_post = pre_post.Pre;
					newSession.timePoint = 7;
					sessions.add(newSession);
				}
				else if(contains(tp7_post,counter)){
					newSession.pre_post = pre_post.Post;
					newSession.timePoint = 7;
					sessions.add(newSession);
				}
				else if(contains(tp8_pre,counter)){
					newSession.pre_post = pre_post.Pre;
					newSession.timePoint = 8;
					sessions.add(newSession);
				}
				else if(contains(tp8_post,counter)){
					newSession.pre_post = pre_post.Post;
					newSession.timePoint = 8;
					sessions.add(newSession);
				}
				else if(contains(tp9_pre,counter)){
					newSession.pre_post = pre_post.Pre;
					newSession.timePoint = 9;
					sessions.add(newSession);
				}
				else if(contains(tp9_post,counter)){
					newSession.pre_post = pre_post.Post;
					newSession.timePoint = 9;
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
