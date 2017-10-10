package singapore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Vector;
import analysis.Tokenizer;
import analysis.Utilities;
import analysis.Values;
import singapore.SamplesDriving.SampleDriving;
import singapore.SampleLP;

/**
 * @author ehsanebk
 *
 */
public class Samples_Processed {

	private Vector<SamplePVTDrivingLP> samples;

	public Samples_Processed() {
		samples = new Vector<SamplePVTDrivingLP>();	
	}

	public SamplePVTDrivingLP get(int i) {
		return samples.elementAt(i);
		
	}
	
	public int size(){
		return samples.size();
	}
	
	//reading a previous file and it's available values
	public void readProcessedFile (File file){
		Tokenizer t = new Tokenizer(file);
		
		t.readHeaderCSV();
		while (t.hasMoreTokens()){
			String[] lineCSV = t.readNextLineCSV();
			
			SamplePVTDrivingLP sample = new SamplePVTDrivingLP();
			sample.protocol = lineCSV[0];
			sample.ID = lineCSV[1];
			sample.trialdate = lineCSV[2];
			
			for (int i = 0; i < sample.lapses.length; i++) {
				if (t.getIndexByHeaderCSV("lapses "+ i) > 0)
					try {
						if (!lineCSV[t.getIndexByHeaderCSV("lapses 0") + i].equals(""))
							sample.lapses[i] = Utilities.toInt(lineCSV[t.getIndexByHeaderCSV("lapses 0") + i]);
					} catch (ArrayIndexOutOfBoundsException e) {
					}
			}
			for (int i = 0; i < sample.alertAve.length; i++) {
				if (t.getIndexByHeaderCSV("lapses 0") > 0)
					try {
						if (!lineCSV[t.getIndexByHeaderCSV("lapses 0") + i].equals(""))
							sample.alertAve[i] = Utilities.toDouble(lineCSV[t.getIndexByHeaderCSV("alert ave 0") + i]);
					} catch (ArrayIndexOutOfBoundsException e) {
					}
			}
			for (int i = 0; i < sample.lanePosSD.length; i++) {
				if (t.getIndexByHeaderCSV("LPSD 0") > 0)
					try {
						if (!lineCSV[t.getIndexByHeaderCSV("LPSD 0") + i].equals(""))
							sample.lanePosSD[i] = Utilities.toDouble(lineCSV[t.getIndexByHeaderCSV("LPSD 0") + i]);
					} catch (ArrayIndexOutOfBoundsException e) {
					}

			}
			for (int i = 0; i < sample.numberOfValidSegments.length; i++) {
				if (t.getIndexByHeaderCSV("# seg 0") > 0)
					try {
						if (!lineCSV[t.getIndexByHeaderCSV("# seg 0") + i].equals(""))
							sample.numberOfValidSegments[i] = Utilities.toInt(lineCSV[t.getIndexByHeaderCSV("# seg 0") + i]);
					} catch (ArrayIndexOutOfBoundsException e) {
					}

			}
			for (int i = 0; i < sample.longestDistanceBetweenMinMaxAve.length; i++) {
				if (t.getIndexByHeaderCSV("Longnest Ave 0") > 0)
					try {
						if (!lineCSV[t.getIndexByHeaderCSV("Longnest Ave 0") + i].equals(""))
							sample.longestDistanceBetweenMinMaxAve[i] = Utilities.toDouble(lineCSV[t.getIndexByHeaderCSV("Longnest Ave 0") + i]);
					} catch (ArrayIndexOutOfBoundsException e) {
					}

			}
			for (int i = 0; i < sample.numberOfMinMaxAve.length; i++) {
				if (t.getIndexByHeaderCSV("MinMax Ave 0") > 0)
					try {
						if (!lineCSV[t.getIndexByHeaderCSV("MinMax Ave 0") + i].equals(""))
							sample.numberOfMinMaxAve[i] = Utilities.toDouble(lineCSV[t.getIndexByHeaderCSV("MinMax Ave 0") + i]);
					} catch (ArrayIndexOutOfBoundsException e) {
					}
			}

			for (int i = 0; i < sample.distanceBetweenMinMaxAve.length; i++) {
				if (t.getIndexByHeaderCSV("MinMaxDis Ave 0") > 0)
					try {
						if (!lineCSV[t.getIndexByHeaderCSV("MinMaxDis Ave 0") + i].equals(""))
							sample.distanceBetweenMinMaxAve[i] = Utilities.toDouble(lineCSV[t.getIndexByHeaderCSV("MinMaxDis Ave 0") + i]);
					} catch (ArrayIndexOutOfBoundsException e) {
					}
			}
			
			addPVTDrivingLPsample(sample);
		}

	}
	
	
	public void writeToFile (File file){
		File PVTfileOutPutProcessed = file;
		PrintWriter foutPVT = null;
		try {
			foutPVT = new PrintWriter(PVTfileOutPutProcessed);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		foutPVT.println("protocol,id,trialdate,"
				+ ",lapses 0,lapses 1,lapses 2,lapses 3,lapses 4,lapses 5,lapses 6,lapses 7,"
				+ ",alert ave 0,alert ave 1,alert ave 2,alert ave 3,alert ave 4,alert ave 5,alert ave 6,alert ave 7,"
				+ ",LPSD 0,LPSD 1,LPSD 2,LPSD 3,LPSD 4,"
				+ ",# seg 0,# seg 1,# seg 2,# seg 3,# seg 4,"
				+ ",Longnest Ave 0,Longnest Ave 1,Longnest Ave 2,Longnest Ave 3,Longnest Ave 4," // average longest time between min and max
				+ ",MinMax Ave 0,MinMax Ave 1,MinMax Ave 2,MinMax Ave 3,MinMax Ave 4,"  // average number of min and max
				+ ",MinMaxDis Ave 0,MinMaxDis Ave 1,MinMaxDis Ave 2,MinMaxDis Ave 3,MinMaxDis Ave 4");
		foutPVT.flush();
		
		for (int i = 0; i < samples.size(); i++) {
			foutPVT.println(samples.get(i).toString());
			foutPVT.flush();
		}
		foutPVT.flush();
		foutPVT.close();
		
	}
	
	public void addPVTDrivingLPsample( SamplePVTDrivingLP sample){
		boolean newData = true;
		for (int i = 0; i < samples.size(); i++) {
			if (samples.get(i).protocol.equals(sample.getProtocol()) && samples.get(i).ID.equals(sample.getID())
					&& samples.get(i).trialdate.equals(sample.getTrialdate())){
				for (int j = 0; j < sample.lapses.length; j++) {
					if (sample.lapses[j] != -1)
						samples.get(i).lapses[j] = sample.lapses[j];
				}
				for (int j = 0; j < sample.alertAve.length; j++) {
					if (sample.alertAve[j] != -1)
						samples.get(i).alertAve[j] = sample.alertAve[j];
				}
				for (int j = 0; j < sample.lanePosSD.length; j++) {
					if (sample.lanePosSD[j] != -1)
						samples.get(i).lanePosSD[j] = sample.lanePosSD[j];
				}
				for (int j = 0; j < sample.numberOfValidSegments.length; j++) {
					if (sample.numberOfValidSegments[j] != -1)
						samples.get(i).numberOfValidSegments[j] = sample.numberOfValidSegments[j];
				}
				for (int j = 0; j < sample.longestDistanceBetweenMinMaxAve.length; j++) {
					if (sample.longestDistanceBetweenMinMaxAve[j] != -1)
						samples.get(i).longestDistanceBetweenMinMaxAve[j] = sample.longestDistanceBetweenMinMaxAve[j];
				}
				for (int j = 0; j < sample.numberOfMinMaxAve.length; j++) {
					if (sample.numberOfMinMaxAve[j] != -1)
						samples.get(i).numberOfMinMaxAve[j] = sample.numberOfMinMaxAve[j];
				}
				for (int j = 0; j < sample.distanceBetweenMinMaxAve.length; j++) {
					if (sample.distanceBetweenMinMaxAve[j] != -1)
						samples.get(i).distanceBetweenMinMaxAve[j] = sample.distanceBetweenMinMaxAve[j];
				}
					
				newData =false;
			}
		}
		if (newData) {
			samples.add(sample);
		}
	}
	
	public void addPVTsample(PVT_session sample){
		//processing the pvt result
		int lapses = 0;
		int falseStarts = 0;
		Values alertResponses = new Values();

		for (int j = 0; j < sample.RT.size(); j++) {
			if (sample.RT.get(j) > 500 )
				lapses++;
			else if (sample.RT.get(j) <= 150)
				falseStarts++;
			else if (sample.RT.get(j) > 150 && sample.RT.get(j) <= 500){
				alertResponses.add(sample.RT.get(j));
			}
		}		
		
		boolean newData = true;
		for (int i = 0; i < samples.size(); i++) {
			if (samples.get(i).protocol.equals(sample.getProtocol()) && samples.get(i).ID.equals(sample.getId())
					&& samples.get(i).trialdate.equals(sample.getTrialdate())){
				samples.get(i).lapses[Integer.parseInt(sample.getTrial())] = lapses;
				samples.get(i).alertAve[Integer.parseInt(sample.getTrial())] = alertResponses.average();
				newData =false;
			}
		}
		if (newData) {
			SamplePVTDrivingLP s = new SamplePVTDrivingLP();
			s.ID = sample.getId();
			s.protocol = sample.getProtocol();
			s.trialdate = sample.getTrialdate();
			s.lapses[Integer.parseInt(sample.getTrial())] = lapses;
			s.alertAve[Integer.parseInt(sample.getTrial())] = alertResponses.average();
			samples.add(s);
		}	
	}

	public void addDrivingSample(SampleDriving drivingS){
		
		boolean newData = true;
		for (int i = 0; i < samples.size(); i++) {
			if (samples.get(i).protocol.equals(drivingS.protocol) && samples.get(i).ID.equals(drivingS.id)
					&& samples.get(i).trialdate.equals(drivingS.trialdate)){
				samples.get(i).lanePosSD[Utilities.toInt(drivingS.trial)] = drivingS.lanePos.stddev();
				newData =false;
			}
		}
		if (newData) {
			SamplePVTDrivingLP s = new SamplePVTDrivingLP();
			s.ID = drivingS.id;
			s.protocol = drivingS.protocol;
			s.trialdate = drivingS.trialdate;
			s.lanePosSD[Utilities.toInt(drivingS.trial)] = drivingS.lanePos.stddev();
			samples.add(s);
		}	

	}
	
	public void addMinMaxAve(SampleLP sample){
		boolean newData = true;
		//check to see if there is any valid segments in the sampleLP
		if (sample.numberOfValidSegments > 0){
			for (int i = 0; i < samples.size(); i++) {
				if (samples.get(i).protocol.equals(sample.protocol) && samples.get(i).ID.equals(sample.id)
						&& samples.get(i).trialdate.equals(sample.trialdate)){
					samples.get(i).numberOfMinMaxAve[Utilities.toInt(sample.trial)] = sample.numberOfMinMaxAve();		
					samples.get(i).numberOfValidSegments[Utilities.toInt(sample.trial)] = sample.numberOfValidSegments;
					samples.get(i).distanceBetweenMinMaxAve[Utilities.toInt(sample.trial)] = sample.distanceBetweenMinMaxAve();
					samples.get(i).longestDistanceBetweenMinMaxAve[Utilities.toInt(sample.trial)] = sample.longestDistanceBetweenMinMaxAve();
					newData =false;
				}
			}
			if (newData) {
				SamplePVTDrivingLP s = new SamplePVTDrivingLP();
				s.ID = sample.id;
				s.protocol = sample.protocol;
				s.trialdate = sample.trialdate;
				s.numberOfMinMaxAve[Utilities.toInt(sample.trial)] = sample.numberOfMinMaxAve();
				s.numberOfValidSegments[Utilities.toInt(sample.trial)] = sample.numberOfValidSegments;
				s.distanceBetweenMinMaxAve[Utilities.toInt(sample.trial)] = sample.distanceBetweenMinMaxAve();
				s.longestDistanceBetweenMinMaxAve[Utilities.toInt(sample.trial)] = sample.longestDistanceBetweenMinMaxAve();
				samples.add(s);
			}	
		}
	}	
}
