package vanDongen;

import java.io.File;
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
		System.out.println("Processing PVT file : " + file.getName());
		ID = file.getName();
		
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
				if (!ID.equals("3207") || !ID.equals("3232") ) {
					
				}
				sessions.add(newSession);
			}			
		}
	}
}
