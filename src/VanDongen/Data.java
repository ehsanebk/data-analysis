package VanDongen;

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

}
