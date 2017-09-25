package vanDongen;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import analysis.Tokenizer;

public class DataPVT {
	String ID;
	Conditions condition;
	
	static Vector<SessionPVT> sessions;
	
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
	
	static void process(File file){
		System.out.println("Processing PVT file : " + file.getName());
		Tokenizer t = new Tokenizer(file);
		
		while(t.hasMoreTokens()){
			String line = t.readNextLine();
			if (line.contains("PVT DATA")){
				SessionPVT newSession = new SessionPVT();
				t.skipLines(7);
				newSession.trialDate = t.readNextLine().substring(17, 24);
				newSession.trialTime = t.readNextLine().substring(17, 24);
				t.skipLines(6);
				String rt = t.readNextLine().substring(1,4);
				rt.replaceAll("\\s+", "");
				while (!rt.equals("0")){
					newSession.RT.add(Integer.valueOf(rt).intValue()); // changing to int
					rt = t.readNextLine().substring(1,4);
				}
				sessions.add(newSession);
			}
				
		}
	}
	

}
