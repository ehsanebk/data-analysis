package VanDongen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;
import analysis.Utilities;
import analysis.Values;

public class Main {

	private static Vector<Data> datas;
	
	private static String[] bestCasesNum = {"3001","3025","3040","3086",
			"3206","3232","3256","3275","3386","3408",
			"3440","3574","3579","3620"};
	private static String[] worstCasesNum = {"3047",
			"3122","3171","3207","3215","3220",
			"3309","3311","3359","3421","3512","3570","3674"};

	public static void main(String[] args) {
				
		datas = new Vector<Data>();
		File directory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Data");

		for (File file : directory.listFiles()){
			if (file.getName().endsWith(".rpt") && !file.getName().substring(0,4).equals("3620")) {
				
				String ID = file.getName().substring(0,4);
				boolean newData = true;
				
				Data data=  new Data();	
				for (int i = 0; i < datas.size(); i++) {
					if (datas.get(i).ID.equals(ID)){
						data = datas.get(i);
						newData= false;
						break;
					}
				}
				
				data.sessions.add(new Session(file));
				
				if (newData){
					if (Utilities.arrayContains(worstCasesNum, ID))
						data.condition = Conditions.WorstCase;
					else
						data.condition = Conditions.BestCase;
					data.ID = ID;
					datas.add(data);
				}
			}
		}
		
		File rawDataDirectory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Raw Data flat");
		for (int i = 0; i < datas.size(); i++){
			Data data = datas.get(i);
			String id = data.ID;
			System.out.println(data.ID);
			for (int j = 0; j < data.sessions.size(); j++) {
				Session s  = data.sessions.get(j);
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
		
		File output = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Results_TimePoints.csv");
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
		Values [] steer_STD_BestCaseTimePoints =  new Values[10];
		Values [] steer_STD_WorstCaseTimePoints =  new Values[10];
		Values [] steer_Ave_BestCaseTimePoints =  new Values[10];
		Values [] steer_Ave_WorstCaseTimePoints =  new Values[10];
		
		Values [] MPH_STD_BestCaseTimePoints =  new Values[10];
		Values [] MPH_STD_WorstCaseTimePoints =  new Values[10];
		Values [] MPH_Ave_BestCaseTimePoints =  new Values[10];
		Values [] MPH_Ave_WorstCaseTimePoints =  new Values[10];
		
		Values [] zeroSteer_Ave_BestCaseTimePoints =  new Values[10];
		Values [] zeroSteer_Ave_WorstCaseTimePoints =  new Values[10];
		Values [] predicitonError_STD_BestCaseTimePoints =  new Values[10];
		Values [] predicitonError_STD_WorstCaseTimePoints =  new Values[10];
		
		for (int i = 0; i < 10; i++) {		
			steer_STD_BestCaseTimePoints[i] = new Values();
			steer_STD_WorstCaseTimePoints[i] = new Values();
			steer_Ave_BestCaseTimePoints[i] = new Values();
			steer_Ave_WorstCaseTimePoints[i] = new Values();
			MPH_STD_BestCaseTimePoints[i] = new Values();
			MPH_STD_WorstCaseTimePoints[i] = new Values();
			MPH_Ave_BestCaseTimePoints[i] = new Values();
			MPH_Ave_WorstCaseTimePoints[i] = new Values();
			zeroSteer_Ave_BestCaseTimePoints[i] = new Values();
			zeroSteer_Ave_WorstCaseTimePoints[i] = new Values();
			predicitonError_STD_BestCaseTimePoints[i] = new Values();
			predicitonError_STD_WorstCaseTimePoints[i] = new Values();
		}
		
		
		for (int i = 0; i < datas.size(); i++) {
			Data data = datas.get(i);
			System.out.println(data.ID);

			for (int j = 0; j < data.sessions.size(); j++) {
				switch (data.condition){
				case BestCase:
					steer_STD_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragesteer_STD());
					MPH_STD_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverageMPH_STD());
					steer_Ave_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragesteer_Ave());
					MPH_Ave_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverageMPH_Ave());
					zeroSteer_Ave_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverageZeroSteer());
					predicitonError_STD_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragepredicitonError_STD());
					break;
				case WorstCase:
					steer_STD_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragesteer_STD());
					MPH_STD_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverageMPH_STD());
					steer_Ave_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragesteer_Ave());
					MPH_Ave_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverageMPH_Ave());
					zeroSteer_Ave_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverageZeroSteer());
					predicitonError_STD_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragepredicitonError_STD());
				}
			}
		}
		
		// writing to file
		outputCSV.println(",,1,2,3,4,,5,6,7,8");

		// steer STD
		outputCSV.print("Best_steer_STD");
		for (int i = 0; i < steer_STD_BestCaseTimePoints.length; i++) {
			double s=steer_STD_BestCaseTimePoints[i].average(); 
			if (s > 0)
				outputCSV.print(","+s);
			else
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_steer_STD");
		for (int i = 0; i < steer_STD_WorstCaseTimePoints.length; i++) {
			double s=steer_STD_WorstCaseTimePoints[i].average(); 
			if (s > 0)
				outputCSV.print(","+s);
			else
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();		
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		// steer Ave
		outputCSV.print("Best_steer_Ave");
		for (int i = 0; i < steer_Ave_BestCaseTimePoints.length; i++) {
			double s=steer_Ave_BestCaseTimePoints[i].average(); 
			if (s > 0)
				outputCSV.print(","+s);
			else
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_steer_Ave");
		for (int i = 0; i < steer_Ave_WorstCaseTimePoints.length; i++) {
			double s=steer_Ave_WorstCaseTimePoints[i].average(); 
			if (s > 0)
				outputCSV.print(","+s);
			else
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		// Zero Steer Ave : Number of Frames
		outputCSV.print("Best_ZeroSteer_Ave");
		for (int i = 0; i < zeroSteer_Ave_BestCaseTimePoints.length; i++) {
			double s= zeroSteer_Ave_BestCaseTimePoints[i].average(); 
			if (s > 0)
				outputCSV.print(","+s);
			else
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_ZeroSteer_Ave");
		for (int i = 0; i < zeroSteer_Ave_WorstCaseTimePoints.length; i++) {
			double s= zeroSteer_Ave_WorstCaseTimePoints[i].average(); 
			if (s > 0)
				outputCSV.print(","+s);
			else
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		// MPH STD
		outputCSV.print("Best_MPH_STD");
		for (int i = 0; i < MPH_STD_BestCaseTimePoints.length; i++) {
			double s=MPH_STD_BestCaseTimePoints[i].average(); 
			if (s > 0)
				outputCSV.print(","+s);
			else
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_MPH_STD");
		for (int i = 0; i < MPH_STD_WorstCaseTimePoints.length; i++) {
			double s=MPH_STD_WorstCaseTimePoints[i].average(); 
			if (s > 0)
				outputCSV.print(","+s);
			else
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		// predicitonError STD
		outputCSV.print("Best_predictionError_STD");
		for (int i = 0; i < predicitonError_STD_BestCaseTimePoints.length; i++) {
			double s=predicitonError_STD_BestCaseTimePoints[i].average(); 
			if (s > 0)
				outputCSV.print(","+s);
			else
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_predicitonError_STD");
		for (int i = 0; i < predicitonError_STD_WorstCaseTimePoints.length; i++) {
			double s=predicitonError_STD_WorstCaseTimePoints[i].average(); 
			if (s > 0)
				outputCSV.print(","+s);
			else
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		// MPH Ave
		outputCSV.print("Best_MPH_Ave");
		for (int i = 0; i < MPH_Ave_BestCaseTimePoints.length; i++) {
			double s=MPH_Ave_BestCaseTimePoints[i].average(); 
			if (s > 0)
				outputCSV.print(","+s);
			else
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_MPH_Ave");
		for (int i = 0; i < MPH_Ave_WorstCaseTimePoints.length; i++) {
			double s=MPH_Ave_WorstCaseTimePoints[i].average(); 
			if (s > 0)
				outputCSV.print(","+s);
			else
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();
		
		
		outputCSV.close();
	}
}
