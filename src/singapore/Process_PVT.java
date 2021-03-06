package singapore;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import analysis.Tokenizer;
import analysis.Utilities;
import singapore.PVT_sessions.session;

/**
 * @author ehsanebk
 *
 */
public class Process_PVT {
	
	Vector<PVT_sessions> samples;
	
	Process_PVT(File PVTfile) throws ParseException{
		Tokenizer t = new Tokenizer(PVTfile);
		SimpleDateFormat dateParser = new SimpleDateFormat ("MM/dd/yy hhmm"); // for parsing date formats

		t.skipLine(); //skip the titles column
		
		//reading the first line
		samples = new Vector<PVT_sessions>();
		
		PVT_sessions currentSample = new PVT_sessions();
		session currentSession = currentSample.new session();
		
		currentSession.pre = t.nextToken();
		currentSession.post = t.nextToken();
		currentSample.protocol =t.nextToken().replace("\"", "");
		currentSample.id = t.nextToken();
		currentSession.trial = t.nextInt();
		
		String trialdate = t.nextToken().replace("\"", "");
		String trialtime = t.nextToken().replace("\"", "");
		currentSession.trialTime =  dateParser.parse(trialdate+ " " + trialtime);
        
		currentSession.pvtsn = t.nextInt();
		currentSession.time.add(t.nextDouble());
		currentSession.RT.add(t.nextDouble());
		
		while (t.hasMoreTokens()) {	
			
			String pre = t.nextToken();
			String post = t.nextToken();
			String protocol =t.nextToken().replace("\"", "");
			String id = t.nextToken();
			int trial = t.nextInt();
			trialdate = t.nextToken().replace("\"", "");
			trialtime = t.nextToken().replace("\"", "");
			Date trialTime =  dateParser.parse(trialdate+ " " + trialtime);
			int pvtsn = t.nextInt();
			double time = t.nextDouble();
			double RT = t.nextDouble();
			
			if (protocol.equals(currentSample.protocol) && id.equals(currentSample.id)
					&& trial==currentSession.trial && trialTime.equals(currentSession.trialTime)
					&& pvtsn==currentSession.pvtsn){
				currentSession.time.add(time);
				currentSession.RT.add(RT);
			}
			else{
				if (protocol.equals(currentSample.protocol) && id.equals(currentSample.id)
						&& trial!=currentSession.trial 
						&& !trialTime.equals(currentSession.trialTime)){
					currentSample.sessions.add(currentSession);
					currentSession = currentSample.new session();
					currentSession.pre = pre;
					currentSession.post = post;
					currentSession.trial = trial;
					currentSession.trialTime = trialTime;
					currentSession.pvtsn = pvtsn;
					currentSession.time.add(time);
					currentSession.RT.add(RT);
				}
				// when there is a new sample ( new ID)
				else if (!protocol.equals(currentSample.protocol) || !id.equals(currentSample.id)){
					currentSample.sessions.add(currentSession);
					samples.add(currentSample);
					
					currentSample = new PVT_sessions();
					currentSample.protocol = protocol;
					currentSample.id = id;
					
					currentSession = currentSample.new session();
					currentSession.pre = pre;
					currentSession.post = post;
					currentSession.trial = trial;
					currentSession.trialTime = trialTime;
					currentSession.pvtsn = pvtsn;
					currentSession.time.add(time);
					currentSession.RT.add(RT);
				}		
			}		
		}
		samples.add(currentSample);
	}
	
	public void toFile(File PVTfileOutPut){
		try {
			PrintWriter fout = new PrintWriter(PVTfileOutPut);
			fout.println("pre	post	protocol	id		trial	trialdate	trialtime	pvtsn	AlertResponsesAve");
			for (int i = 0; i < samples.size(); i++)
				for (int j = 0; j < samples.elementAt(i).sessions.size(); j++) {
					session s = samples.elementAt(i).sessions.get(j);
					String line=  s.pre + "\t" +
							s.post + "\t\t" +						
							samples.elementAt(i).getProtocol() + "\t\t\t" +
							samples.elementAt(i).getId() + "\t\t" +
							s.trial + "\t\t" +
							s.dateParser.format(s.trialTime) + "\t" +
							s.timeParser.format(s.trialTime) + "\t\t" +
							s.pvtsn + "\t" +
							Utilities.df1.format(s.mean_AlertResponses());
					fout.println(line);
				}
			
			fout.close();
		} catch (IOException exc) {
			System.err.println("IOException: " + exc.getMessage());
		}
	}
	
	public PVT_sessions get(String ID, String protocol) throws Exception{
		
		for (PVT_sessions pvt : samples) {
			if (pvt.id.equals(ID) && pvt.protocol.equals(protocol))
				return pvt; 
		}
		throw new Exception("ID " + ID + " Not found in the data!");
	}
	
	public int size(){
		return samples.size();
	}
	
	public static void main(String[] args) throws Exception {
		
		
		File PVTfile = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "PVT Raw Data/MFPD_PVT_all.txt");
		Process_PVT singaporePVT = new Process_PVT(PVTfile);
		
		
		File PVTfileOutPut = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "PVT Raw Data/MFPD_PVT_average_new.txt");
		singaporePVT.toFile(PVTfileOutPut); 
	}

}
