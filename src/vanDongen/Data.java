package vanDongen;

import java.util.*;
import analysis.Values;

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
	Object getSessionNumber (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.sessionNumber;
		}
		return null;
	}


	///// new values Raw Data
	Object getSessionSteering_STD_RawData(int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionSteering_STD_RawData();
		}
		return null;
	}
	Object getSessionMPH_STD_RawData (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionMPH_STD_RawData();
		}
		return null;
	}
	
	Object getSessionMPH_Ave_RawData (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionMPH_Ave_RawData();
		}
		return null;
	}


	//////// For the extracted data ////////
	Object getSessionAccelPed_Ave_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAccelPed_Ave_Extracted();
		}
		return null;
	}

	Object getSessionAccelPed_Max_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAccelPed_Max_Extracted();
		}
		return null;
	}
	Object getSessionAccelPed_STD_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAccelPed_STD_Extracted();
		}
		return null;
	}
	// BrakePad
	Object getSessionBrakePed_Ave_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionBrakePed_Ave_Extracted();
		}
		return null;
	}
	Object getSessionBrakePed_Max_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionBrakePed_Max_Extracted();
		}
		return null;
	}
	Object getSessionBrakePed_STD_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionBrakePed_STD_Extracted();
		}
		return null;
	}
	// Lane position
	Object getSessionLanePos_Ave_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionLanePos_Ave_Extracted();
		}
		return null;
	}
	Object getSessionLanePos_Max_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionLanePos_Max_Extracted();
		}
		return null;
	}
	Object getSessionLanePos_STD_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionLanePos_STD_Extracted();
		}
		return null;
	}
	// Steering
	Object getSessionSteering_Ave_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionSteering_Ave_Extracted();
		}
		return null;
	}
	Object getSessionSteering_Max_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionSteering_Max_Extracted();
		}
		return null;
	}
	Object getSessionSteering_STD_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionSteering_STD_Extracted();
		}
		return null;
	}
	Object getSessionZeroSteer_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionZeroSteer_Extracted();
		}
		return null;
	}
	Object getSession2DSteer_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSession2DSteer_Extracted();
		}
		return null;
	}
	Object getSession3DSteer_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSession3DSteer_Extracted();
		}
		return null;
	}
	Object getSessionFastCorrectiveCounterSteering_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionFastCorrectiveCounterSteering_Extracted();
		}
		return null;
	}

	Object getSessionPredicitonError_STD_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionPredicitonError_STD_Extracted();
		}
		return null;
	}
	Object getSessionSteeringEntorpy_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionSteeringEntorpy_Extracted();
		}
		return null;
	}


}
