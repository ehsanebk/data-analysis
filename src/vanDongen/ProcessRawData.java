package vanDongen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;
import analysis.Utilities;
import analysis.Values;

public class ProcessRawData {

	private static Vector<SubjectData> participantsData;
	
	private static String[] bestCasesNum = {"3001","3025","3040","3086",
			"3206","3232","3256","3275","3386","3408",
			"3440","3574","3579","3620"};
	private static String[] worstCasesNum = {"3047",
			"3122","3171","3207","3215","3220",
			"3309","3311","3359","3421","3512","3570","3674"};

	public static void main(String[] args) {
				
		participantsData = new Vector<SubjectData>();
		//File directory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Data(report)");
		File directory = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Data(report)");

		for (File file : directory.listFiles()){
			if (file.getName().endsWith(".rpt") && !file.getName().substring(0,4).equals("3620")) { // what is wrong with 3620?
				
				String ID = file.getName().substring(0,4);
				boolean newData = true;
				
				// trying to find whether the ID is new
				SubjectData subjectData=  new SubjectData();	
				for (int i = 0; i < participantsData.size(); i++) {
					if (participantsData.get(i).ID.equals(ID)){
						subjectData = participantsData.get(i);
						newData= false;
						break;
					}
				}
				
				subjectData.sessions.add(new Session(file));
				
				if (newData){
					if (Utilities.arrayContains(worstCasesNum, ID))
						subjectData.condition = Conditions.WorstCase;
					else
						subjectData.condition = Conditions.BestCase;
					subjectData.ID = ID;
					participantsData.add(subjectData);
				}
			}
		}
		
		//File rawDataDirectory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Raw Data flat");
		File rawDataDirectory = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Raw Data flat");
		for (int i = 0; i < participantsData.size(); i++){
			SubjectData subjectData = participantsData.get(i);
			String id = subjectData.ID;
			System.out.println(subjectData.ID);
			for (int j = 0; j < subjectData.sessions.size(); j++) {
				Session s  = subjectData.sessions.get(j);
				String s_number = s.sessionNumber;
				System.out.println(s_number);
				for (File file : rawDataDirectory.listFiles()) {
					if (file.getName().startsWith("DRV") && file.getName().contains(id) && file.getName().contains("B"+ s_number)){
						System.out.println(file.getName());
						s.addRawData(file);
					}
				}		
			}
		}
		
		//File output = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Results_TimePoints.csv");
		File output = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Results_TimePoints_RawData.csv");
		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WriteToFile(outputCSV);
	}
	
	static void WriteToFile(PrintWriter outputCSV) {
		
		Values [] MPH_STD_BestCaseTimePoints =  new Values[10];
		Values [] MPH_STD_WorstCaseTimePoints =  new Values[10];
		Values [] MPH_Ave_BestCaseTimePoints =  new Values[10];
		Values [] MPH_Ave_WorstCaseTimePoints =  new Values[10];
		
		
		
		for (int i = 0; i < 10; i++) {		
			MPH_STD_BestCaseTimePoints[i] = new Values();
			MPH_STD_WorstCaseTimePoints[i] = new Values();
			MPH_Ave_BestCaseTimePoints[i] = new Values();
			MPH_Ave_WorstCaseTimePoints[i] = new Values();
		}
		
		
		for (int i = 0; i < participantsData.size(); i++) {
			SubjectData subjectData = participantsData.get(i);
			System.out.println(subjectData.ID);

			for (int j = 0; j < subjectData.sessions.size(); j++) {
				switch (subjectData.condition){
				case BestCase:
					MPH_STD_BestCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionMPH_STD_RawData());
					MPH_Ave_BestCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionMPH_Ave_RawData());
					break;
				case WorstCase:
					MPH_STD_WorstCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionMPH_STD_RawData());
					MPH_Ave_WorstCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionMPH_Ave_RawData());
				}
			}
		}
		
		////////////////        writing to file       ////////////////////////
		outputCSV.println(",,1,2,3,4,,5,6,7,8");

		// MPH STD
		outputCSV.print("Best_MPH_STD");
		for (int i = 0; i < MPH_STD_BestCaseTimePoints.length; i++) {
			double s=MPH_STD_BestCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_MPH_STD");
		for (int i = 0; i < MPH_STD_WorstCaseTimePoints.length; i++) {
			double s=MPH_STD_WorstCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		// MPH Ave
		outputCSV.print("Best_MPH_Ave");
		for (int i = 0; i < MPH_Ave_BestCaseTimePoints.length; i++) {
			double s=MPH_Ave_BestCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_MPH_Ave");
		for (int i = 0; i < MPH_Ave_WorstCaseTimePoints.length; i++) {
			double s=MPH_Ave_WorstCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();


		outputCSV.close();
	}
}
