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
	
	
	/**
	 * @param sNumber
	 * @return Session associated with the number
	 * session numbers
	 * 
	 *     time points: 1	2	3	4		5	6	7	8
	 *     ––––––––––––––––––––––––––––––––––––––––––––––––––
	 *     				4	5	6	7		24	25	26	27
	 *     				8	9	10	11		28	29	30	31
	 *     				12	13	14	15		32	33	34	35
	 *     				16	17	18	19		36	37	38	39
	 *     				20	21	22	23		40	41	42	43
	 */
	Session getSessionByNumber(int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session;
		}
		
		return null;
		
	}

}
