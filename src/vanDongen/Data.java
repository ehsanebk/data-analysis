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


	//////////////////new values
	Object getSessionAveragesteer_STD (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.sessionNumber;
		}
		return null;
	}
	Object getSessionAverageMPH_STD (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAverageMPH_STD();
		}
		return null;
	}
	
	Object getSessionAverageMPH_Ave (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAverageMPH_Ave();
		}
		return null;
	}


	//////// For the extracted data ////////
	Object getSessionAccelPed_Ave_Extraxted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAccelPed_Ave_Extraxted();
		}
		return null;
	}

	Object getSessionAccelPed_Max_Extraxted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAccelPed_Max_Extraxted();
		}
		return null;
	}
	Object getSessionAccelPed_STD_Extraxted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAccelPed_STD_Extraxted();
		}
		return null;
	}
	// BrakePad
	Object getSessionBrakePed_Ave_Extraxted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionBrakePed_Ave_Extraxted();
		}
		return null;
	}
	Object getSessionBrakePed_Max_Extraxted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionBrakePed_Max_Extraxted();
		}
		return null;
	}
	Object getSessionBrakePed_STD_Extraxted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionBrakePed_STD_Extraxted();
		}
		return null;
	}
	// SteerWheel
	Object getSessionSteerWheel_Ave_Extraxted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionSteerWheel_Ave_Extraxted();
		}
		return null;
	}
	Object getSessionSteerWheel_Max_Extraxted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionBrakePed_Max_Extraxted();
		}
		return null;
	}
	Object getSessionSteerWheel_STD_Extraxted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionBrakePed_STD_Extraxted();
		}
		return null;
	}
	// Lane position
	Object getSessionLanePos_Ave_Extraxted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionLanePos_Ave_Extraxted();
		}
		return null;
	}
	Object getSessionLanePos_Max_Extraxted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionLanePos_Max_Extraxted();
		}
		return null;
	}
	Object getSessionLanePos_STD_Extraxted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionLanePos_STD_Extraxted();
		}
		return null;
	}
	// Steering
	Object getSessionAverageZeroSteer_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAverageZeroSteer_Extracted();
		}
		return null;
	}
	Object getSessionAverage2DSteer_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAverage2DSteer_Extracted();
		}
		return null;
	}
	Object getSessionAverage3DSteer_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAverage3DSteer_Extracted();
		}
		return null;
	}
	Object getSessionAverageFastCorrectiveCounterSteering_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAverageFastCorrectiveCounterSteering_Extracted();
		}
		return null;
	}

	Object getSessionAveragepredicitonError_STD_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAveragepredicitonError_STD_Extracted();
		}
		return null;
	}
	Object getSessionAveragesteeringEntorpy_Extracted (int sNumber){
		for (Iterator<Session> iterator = sessions.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			if (Integer.valueOf(session.sessionNumber).intValue() == sNumber)
				return session.getSessionAveragesteeringEntorpy_Extracted();
		}
		return null;
	}


}
