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
	
	private static Vector<DataPVT> participantsDataPVT;

	private static String[] validPVT = {"3040","3047","3086","3122", "3171",
			"3206","3207","3215","3220","3040", "3232",
			"3256","3275","3309","3311","3359","3386","3408","3421",
			"3440","3512","3570","3574",
			"3579","3620","3674"
			};
	
	
	public ProcessPVT() {
		participantsDataPVT = new Vector<DataPVT>();
	}
	
	
	
	void process(Path dir) {
		try {
			for (Path inPath : Files.newDirectoryStream(dir)) {
				File inPathFile= inPath.toFile();
				if (Files.isDirectory(inPath)) {
					process(inPath);
				} else if (inPathFile.getName().toLowerCase().endsWith(".pvt")
						 && Utilities.arrayContains(validPVT,inPathFile.getName().substring(0, 4) )) {
					System.out.println(inPathFile.getName());
					DataPVT dataPVT = new DataPVT();
					dataPVT.process(inPathFile);
//					participantsDataPVT.add(dataPVT);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void WriteToFile(File output) {
		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Values [] PVT_AVE_BestCaseTimePoints =  new Values[10];
		Values [] PVT_AVE_WorstCaseTimePoints =  new Values[10];
		


		for (int i = 0; i < 10; i++) {		
			
			PVT_AVE_BestCaseTimePoints[i] = new Values();
			PVT_AVE_WorstCaseTimePoints[i] = new Values();
		}


		for (int i = 0; i < participantsDataPVT.size(); i++) {
			DataPVT data = participantsDataPVT.get(i);
			System.out.println(" Processing to write to file : " + data.ID);
			
			
//			for (int j = 0; j < data.sessions.size(); j++) {
//				switch (data.condition){
//				case BestCase:
//					
//					PVT_AVE_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragepredicitonError_STD_Extracted());
//					steeringEntropy_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragesteeringEntorpy_Extracted());
//					break;
//				case WorstCase:
//					
//					predicitonError_STD_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragepredicitonError_STD_Extracted());
//					steeringEntropy_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragesteeringEntorpy_Extracted());
//					break;
//				}
//			}
		}

		////////////////        writing to file       ////////////////////////
		outputCSV.println(",,1,2,3,4,,5,6,7,8");

		// PVT AVE
		outputCSV.print("Best_PVT_AVE");
		for (int i = 0; i < PVT_AVE_BestCaseTimePoints.length; i++) {
			double s=PVT_AVE_BestCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_PVT_AVE");
		for (int i = 0; i < PVT_AVE_WorstCaseTimePoints.length; i++) {
			double s=PVT_AVE_WorstCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();		
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		


		outputCSV.close();
	}
	
	
	
	
	public static void main(String[] args) {
	    ProcessPVT data = new ProcessPVT();
		
		//File directory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/PVT Raw data");
		File directory = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/PVT Raw data");

		data.process(directory.toPath());
		
		File output = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Results_TimePoints_PVT.csv");
		//File output = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Results_TimePoints_PVT.csv");
		//WriteToFile(output);
		
		
		// for testing
		for (int i = 0; i < participantsDataPVT.size(); i++) {
		//for (int i = 0; i < 1; i++) {
			DataPVT participant = participantsDataPVT.get(i);
			System.out.println("ID: " +participant.ID);

			
			for (int j = 0; j < participant.sessions.size(); j++) {
				SessionPVT session = participant.sessions.get(j);
				System.out.println(session.trialNumberInFile + " " + session.trialDate + " "+ session.trialTime);
//				for (int k = 0; k < session.RT.size(); k++) {
//					System.out.print(session.RT.get(k) + " ");
//				}
			}
			
			System.out.println();
			System.out.println();
		
		}		
	}

}
