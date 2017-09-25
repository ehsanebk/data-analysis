package vanDongen;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import analysis.Tokenizer;

public class DataPVT {
	String ID;
	Conditions condition;
	
	Vector<SessionPVT> sessions;
	
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
				sessions.add(newSession);
			}
				
		}
	}


}
