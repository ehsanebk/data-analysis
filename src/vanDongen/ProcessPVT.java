package vanDongen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Vector;

import analysis.Utilities;
import analysis.Values;

public class ProcessPVT {

	private static Vector<PVT_sessions> participantsDataPVT;

	private static String[] validPVT = {"3040","3047","3086","3122", "3171",
			"3206","3207","3215","3220","3040", "3232",
			"3256","3275","3309","3311","3359","3386","3408","3421",
			"3440","3512","3570","3574",
			"3579","3620","3674"
	};


	public ProcessPVT() {
		participantsDataPVT = new Vector<PVT_sessions>();
	}

	void process(Path dir) {
		try {
			for (Path inPath : Files.newDirectoryStream(dir)) {
				File inPathFile= inPath.toFile();
				if (Files.isDirectory(inPath)) {
					process(inPath);
				} else if (inPathFile.getName().toLowerCase().endsWith(".pvt")
						&& Utilities.arrayContains(validPVT,inPathFile.getName().substring(0, 4) )) {
					PVT_sessions pvt_sessions = new PVT_sessions();
					pvt_sessions.process(inPathFile);
					participantsDataPVT.add(pvt_sessions);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	PVT_sessions getByID (String ID) throws Exception{
		for (PVT_sessions pvt_sessions : participantsDataPVT) {
			if (pvt_sessions.ID.equals(ID))
				return pvt_sessions;
		}
		throw new Exception("PVT for ID " + ID + " Not found in the data!");
	}

	static void WriteToFile(File output) {
		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// the time points are in the format of 
		// 1 2 3 4 break 6 7 8 9
		Values [] PVT_AVE_BestCaseTimePoints =  new Values[10];
		Values [] PVT_AVE_WorstCaseTimePoints =  new Values[10];

		for (int i = 0; i < 10; i++) {					
			PVT_AVE_BestCaseTimePoints[i] = new Values();
			PVT_AVE_WorstCaseTimePoints[i] = new Values();
		}

		for (int i = 0; i < participantsDataPVT.size(); i++) {
			PVT_sessions pVT_sessions = participantsDataPVT.get(i);
			int [] dp = {1,2,3,4,6,7,8,9}; 
			for (int k: dp) {
				switch (pVT_sessions.condition){
				case BestCase:
					PVT_AVE_BestCaseTimePoints[k].add(pVT_sessions.getNumberOfLapses_AveOnTimePoints(k));
					break;
				case WorstCase:
					PVT_AVE_WorstCaseTimePoints[k].add(pVT_sessions.getNumberOfLapses_AveOnTimePoints(k));
					break;
				}
			}
		}

		////////////////        writing to file       ////////////////////////
		outputCSV.println(",,1,2,3,4,,5,6,7,8");

		// PVT AVE
		outputCSV.print("Best_PVT_AVE");
		for (int i = 0; i < PVT_AVE_BestCaseTimePoints.length; i++) {
			if ( PVT_AVE_BestCaseTimePoints[i].size() > 0){
				double s=PVT_AVE_BestCaseTimePoints[i].average(); 
				outputCSV.print(","+s);

			} else 
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_PVT_AVE");
		for (int i = 0; i < PVT_AVE_WorstCaseTimePoints.length; i++) {
			if (PVT_AVE_WorstCaseTimePoints[i].size() > 0){
				double s=PVT_AVE_WorstCaseTimePoints[i].average(); 
				outputCSV.print(","+s);
			}else 
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();		
		outputCSV.print("\n\n\n");
		outputCSV.flush();
		outputCSV.close();
	}

	public static void main(String[] args) {
		ProcessPVT data = new ProcessPVT();

		File directory = new File("./Result(Van Dongen)/PVT Raw data");
		//File directory = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/PVT Raw data");
		data.process(directory.toPath());

		File output = new File("./Result(Van Dongen)/Results_TimePoints_PVT.csv");
		//File output = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Results_TimePoints_PVT.csv");
		WriteToFile(output);


//		// for testing
//		for (int i = 0; i < data.participantsDataPVT.size(); i++) {
//			//for (int i = 0; i < 1; i++) {
//			PVT_sessions participant = participantsDataPVT.get(i);
//			//System.out.print("ID: " +participant.ID);
//
//			if (participant.condition == Conditions.WorstCase){
//				for (int j = 0; j < participant.sessions.size(); j++) {
//					PVT_session session = participant.sessions.get(j);
//					if (session.pre_post == pre_post.Pre && (session.timePoint == 4 || session.timePoint == 8)){
//						System.out.print(session.ID + "," + session.pre_post + "," + session.timePoint);
//						for (int k = 0; k < session.RT.size(); k++) {
//							System.out.print(","+session.RT.get(k));
//						}
//						System.out.print("\n");
//					}
//				}
//			}
//		}		

	}
}
