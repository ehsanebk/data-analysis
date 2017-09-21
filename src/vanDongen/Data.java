package vanDongen;

import java.io.File;
import java.util.*;

enum Conditions {BestCase, WorstCase};

public class Data {

	String ID;
	Conditions condition;
	
	Vector<Session> sessions;
	
	Data() {

		sessions = new Vector<Session>();
	}
	
	Session getSessionByNumber(int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session;
		}
		
		return null;
		
	}

}
