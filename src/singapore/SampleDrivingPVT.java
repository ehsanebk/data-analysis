package singapore;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import analysis.Utilities;
import analysis.Values;

public class SampleDrivingPVT {
	String protocol;
	String ID;
	String trialdate;
	int[] lapses = new int[8];
	double[] alertAve = new double[8];
	double[] lanePosSD = new double[5];
	double[] numberOfMinMaxAve = new double[5];
	double[] distanceBetweenMinMaxAve  = new double[5];

	Values[] lanePos = new Values[5];
	
	public SampleDrivingPVT() {
		for (int i = 0; i < alertAve.length; i++) 
			alertAve[i] = -1;
		for (int i = 0; i < lapses.length; i++) 
			lapses[i] = -1;
		for (int i = 0; i < lanePosSD.length; i++) 
			lanePosSD[i] = -1;
		for (int i = 0; i < numberOfMinMaxAve.length; i++) 
			numberOfMinMaxAve[i] = -1;
		for (int i = 0; i < distanceBetweenMinMaxAve.length; i++) 
			distanceBetweenMinMaxAve[i] = -1;
		for (int i = 0; i < lanePos.length; i++) 
			lanePos[i] = new Values();
		
	}
	public String toString(){
		String s = ";";
		s = protocol+","+ID+","+ trialdate +",,";
		for (int j = 0; j < lapses.length; j++) {
			if (lapses[j] >= 0)
				s+= lapses[j];
			s+= ",";
		}
		s += ",";
		for (int j = 0; j < alertAve.length; j++) {
			if (alertAve[j] >= 0)
				s+= Utilities.df1.format(alertAve[j]);
			s+= ",";
		}
		s += ",";
		for (int j = 0; j < lanePosSD.length; j++) {
			if (lanePosSD[j] >= 0)
				s+= Utilities.df4.format(lanePosSD[j]);
			s+= ",";
		}
		s += ",";
		for (int j = 0; j < numberOfMinMaxAve.length; j++) {
			if (numberOfMinMaxAve[j] >= 0)
				s+= Utilities.df2.format(numberOfMinMaxAve[j]);
			s+= ",";
		}
		
		return s;
	}
	public String getID() {
		return ID;
	}
	public String getProtocol() {
		return protocol;
	}
	public String getTrialdate() {
		return trialdate;
	}
}
