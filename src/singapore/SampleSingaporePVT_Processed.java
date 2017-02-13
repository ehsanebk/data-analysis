package singapore;

// class for saving the processed PVT data
import java.util.Vector;

import javax.print.attribute.standard.RequestingUserName;

import analysis.Utilities;
import analysis.Values;

public class SampleSingaporePVT_Processed {

	private Vector<PVTsamples> samples;

	private class PVTsamples{
		String protocol;
		String ID;
		String trialdate;
		int[] lapses = new int[8];
		double[] alertAve = new double[8];
	}

	public SampleSingaporePVT_Processed() {
		samples = new Vector<PVTsamples>();
	}

	public String toString(int i){
		String s = ";";
		s = samples.get(i).protocol+","+samples.get(i).ID+","+ samples.get(i).trialdate +",,";
		for (int j = 0; j < 8; j++) {
			if (samples.get(i).lapses[j] > 0)
				s+= samples.get(i).lapses[j];
			s+= ",";
		}
		s += ",";
		for (int j = 0; j < 8; j++) {
			if (samples.get(i).alertAve[j] > 0)
				s+= Utilities.df1.format(samples.get(i).alertAve[j]);
			s+= ",";
		}
		return s;
	}

	public void add(SampleSingaporePVT sample){
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
		
		if (samples.size() == 0){
			PVTsamples s = new PVTsamples();
			s.ID = sample.getId();
			s.protocol = sample.getProtocol();
			s.trialdate = sample.getTrialdate();
			s.lapses[Integer.parseInt(sample.getTrial())] = lapses;
			s.alertAve[Integer.parseInt(sample.getTrial())] = alertResponses.average();
			samples.add(s);
		}
		else{
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
				PVTsamples s = new PVTsamples();
				s.ID = sample.getId();
				s.protocol = sample.getProtocol();
				s.trialdate = sample.getTrialdate();
				s.lapses[Integer.parseInt(sample.getTrial())] = lapses;
				s.alertAve[Integer.parseInt(sample.getTrial())] = alertResponses.average();
				samples.add(s);

			}	
		}
	}


	public int size(){
		return samples.size();
	}

}
