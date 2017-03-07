package VanDongen;

import java.io.File;
import java.util.*;

import analysis.Tokenizer;
import analysis.Utilities;

public class Data {
	String ID;
	String condition;
	Vector<Session> sessions;
	
	Data() {

		sessions = new Vector<Session>();
	}

}
