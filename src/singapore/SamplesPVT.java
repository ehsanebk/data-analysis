package singapore;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import analysis.Tokenizer;

/**
 * @author ehsanebk
 *
 */
public class SamplesPVT {
	
	Vector<SamplePVT> samples;
	
	SamplesPVT(File PVTfile){
		Tokenizer t = new Tokenizer(PVTfile);
		
		//skip the column titles
		t.skipLine();
		
		//reading the first line
		samples = new Vector<SamplePVT>();
		
		SamplePVT currentSample = new SamplePVT();
		
		String pre;	
		String post;
		String protocol;
		String id;
		String trial;
		String trialdate;
		String trialtime;
		int pvtsn;
		double time;
		double RT;
		
		currentSample.pre = t.nextToken();
		currentSample.post = t.nextToken();
		currentSample.protocol =t.nextToken();
		currentSample.id = t.nextToken();
		currentSample.trial = t.nextToken();
		currentSample.trialdate = t.nextToken();
		currentSample.trialtime = t.nextToken();
		currentSample.pvtsn = t.nextInt();
		currentSample.time.add(t.nextDouble());
		RT = t.nextDouble();
		if (RT > 150)
			currentSample.RT.add(RT);
		
		while (t.hasMoreTokens()) {	
			
			pre = t.nextToken();
			post = t.nextToken();
			protocol =t.nextToken();
			id = t.nextToken();
			trial = t.nextToken();
			trialdate = t.nextToken();
			trialtime = t.nextToken();
			pvtsn = t.nextInt();
			time = t.nextDouble();
			RT = t.nextDouble();
			
			if (pre.equals(currentSample.pre) && post.equals(currentSample.post)
					&& protocol.equals(currentSample.protocol) && id.equals(currentSample.id)
					&& trial.equals(currentSample.trial) && trialdate.equals(currentSample.trialdate)
					&& trialtime.equals(currentSample.trialtime) && pvtsn==currentSample.pvtsn
					){
				currentSample.time.add(time);
				if (RT > 150)
					currentSample.RT.add(RT);
			}
			else{
				samples.add(currentSample);
				currentSample = new SamplePVT();
				
				currentSample.pre = pre;
				currentSample.post = post;
				currentSample.protocol = protocol;
				currentSample.id = id;
				currentSample.trial = trial;
				currentSample.trialdate = trialdate;
				currentSample.trialtime = trialtime;
				currentSample.pvtsn = pvtsn;
				currentSample.time.add(time);
				currentSample.RT.add(RT);
							
			}		
		}
	}
	
	public void toFile(File PVTfileOutPut){
		try {
			PrintWriter fout = new PrintWriter(PVTfileOutPut);
			fout.println("pre	post	protocol	id		trial	trialdate	trialtime	pvtsn	RTaverage");
			for (int i = 0; i < samples.size(); i++) 
				fout.println(samples.elementAt(i).toString());
			fout.close();
		} catch (IOException exc) {
			System.err.println("IOException: " + exc.getMessage());
		}
	}
	
	public SamplePVT get(int i){
		return samples.elementAt(i);
	}
	public int size(){
		return samples.size();
	}
	
	public static void main(String[] args) {
		
		File PVTfile = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "MFPD_PVT_all.txt");
		SamplesPVT singaporePVT = new SamplesPVT(PVTfile);
		
		File PVTfileOutPut = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "MFPD_PVT_average.txt");
		singaporePVT.toFile(PVTfileOutPut); 
	}

}
