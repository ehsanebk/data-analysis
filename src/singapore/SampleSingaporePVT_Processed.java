package singapore;

import java.awt.font.FontRenderContext;
// class for saving the processed PVT data
import java.util.Vector;

import javax.print.attribute.standard.RequestingUserName;

import org.apache.commons.collections4.functors.ForClosure;

import analysis.Utilities;
import analysis.Values;

public class SampleSingaporePVT_Processed {

	private Vector<DrivingPVTsamples> samples;

	public class DrivingPVTsamples{
		String protocol;
		String ID;
		String trialdate;
		int[] lapses = new int[8];
		double[] alertAve = new double[8];
		double[] lanePosSD = new double[5];
		public DrivingPVTsamples() {
			for (int i = 0; i < alertAve.length; i++) 
				alertAve[i] = -1;
			for (int i = 0; i < lapses.length; i++) 
				lapses[i] = -1;
			for (int i = 0; i < lanePosSD.length; i++) 
				lanePosSD[i] = -1;
			
		}
	}
	

	public SampleSingaporePVT_Processed() {
		samples = new Vector<DrivingPVTsamples>();
		
		
	}

	public DrivingPVTsamples get(int i) {
		return samples.elementAt(i);
		
	}
	public String toString(int i){
		String s = ";";
		s = samples.get(i).protocol+","+samples.get(i).ID+","+ samples.get(i).trialdate +",,";
		for (int j = 0; j < samples.get(i).lapses.length; j++) {
			if (samples.get(i).lapses[j] >= 0)
				s+= samples.get(i).lapses[j];
			s+= ",";
		}
		s += ",";
		for (int j = 0; j < samples.get(i).alertAve.length; j++) {
			if (samples.get(i).alertAve[j] >= 0)
				s+= Utilities.df1.format(samples.get(i).alertAve[j]);
			s+= ",";
		}
		s += ",";
		for (int j = 0; j < samples.get(i).lanePosSD.length; j++) {
			if (samples.get(i).lanePosSD[j] >= 0)
				s+= Utilities.df4.format(samples.get(i).lanePosSD[j]);
			s+= ",";
		}
		return s;
	}

	public void addPVTsample(SampleSingaporePVT sample){
		//processing the pvt result
		int lapses = 0;
		int falseStarts = 0;
		Values alertResponses = new Values();

		for (int j = 0; j < sample.RT.size(); j++) {
			if (sample.RT.get(j) > 500 )
				lapses++;
			else if (sample.RT.get(j) < 150)
				falseStarts++;
			else if (sample.RT.get(j) >= 150 && sample.RT.get(j) <= 500){
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
			DrivingPVTsamples s = new DrivingPVTsamples();
			s.ID = sample.getId();
			s.protocol = sample.getProtocol();
			s.trialdate = sample.getTrialdate();
			s.lapses[Integer.parseInt(sample.getTrial())] = lapses;
			s.alertAve[Integer.parseInt(sample.getTrial())] = alertResponses.average();
			samples.add(s);

		}	
		
	}

	public void addDrivingSample(String Protocol, String ID, String trialDate, String trial, double lanePosSD){


		boolean newData = true;
		for (int i = 0; i < samples.size(); i++) {
			if (samples.get(i).protocol.equals(Protocol) && samples.get(i).ID.equals(ID)
					&& samples.get(i).trialdate.equals(trialDate)){
				samples.get(i).lanePosSD[Integer.parseInt(trial)] = lanePosSD;
				newData =false;
			}
		}
		if (newData) {
			DrivingPVTsamples s = new DrivingPVTsamples();
			s.ID = ID;
			s.protocol = Protocol;
			s.trialdate = trialDate;
			s.lanePosSD[Integer.parseInt(trial)] = lanePosSD;
			samples.add(s);
		}	

	}

	public int size(){
		return samples.size();
	}

}
