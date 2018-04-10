package vanDongen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;
import analysis.Utilities;
import analysis.Values;

public class ProcessRawData {

	private static Vector<Data> participantsData;
	
	private static String[] bestCasesNum = {"3001","3025","3040","3086",
			"3206","3232","3256","3275","3386","3408",
			"3440","3574","3579","3620"};
	private static String[] worstCasesNum = {"3047",
			"3122","3171","3207","3215","3220",
			"3309","3311","3359","3421","3512","3570","3674"};

	public static void main(String[] args) {
				
		participantsData = new Vector<Data>();
		//File directory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Data(report)");
		File directory = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Data(report)");

		for (File file : directory.listFiles()){
			if (file.getName().endsWith(".rpt") && !file.getName().substring(0,4).equals("3620")) { // what is wrong with 3620?
				
				String ID = file.getName().substring(0,4);
				boolean newData = true;
				
				// trying to find whether the ID is new
				Data data=  new Data();	
				for (int i = 0; i < participantsData.size(); i++) {
					if (participantsData.get(i).ID.equals(ID)){
						data = participantsData.get(i);
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
					participantsData.add(data);
				}
			}
		}
		
		//File rawDataDirectory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Raw Data flat");
		File rawDataDirectory = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Raw Data flat");
		for (int i = 0; i < participantsData.size(); i++){
			Data data = participantsData.get(i);
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
		Values [] steer_STD_BestCaseTimePoints =  new Values[10];
		Values [] steer_STD_WorstCaseTimePoints =  new Values[10];
		Values [] steer_Ave_BestCaseTimePoints =  new Values[10];
		Values [] steer_Ave_WorstCaseTimePoints =  new Values[10];
		
		Values [] MPH_STD_BestCaseTimePoints =  new Values[10];
		Values [] MPH_STD_WorstCaseTimePoints =  new Values[10];
		Values [] MPH_Ave_BestCaseTimePoints =  new Values[10];
		Values [] MPH_Ave_WorstCaseTimePoints =  new Values[10];
		
		Values [] zeroSteer_Percentage_BestCaseTimePoints =  new Values[10];
		Values [] zeroSteer_Percentage_WorstCaseTimePoints =  new Values[10];
		
		Values [] steer2D_Percentage_BestCaseTimePoints =  new Values[10];
		Values [] steer2D_Percentage_WorstCaseTimePoints =  new Values[10];
		
		Values [] steer3D_Percentage_BestCaseTimePoints =  new Values[10];
		Values [] steer3D_Percentage_WorstCaseTimePoints =  new Values[10];
		
		// Fast Corrective Counter Steering
		Values [] FCCS_BestCaseTimePoints =  new Values[10];
		Values [] FCCS_WorstCaseTimePoints =  new Values[10];
		
		
		Values [] predicitonError_STD_BestCaseTimePoints =  new Values[10];
		Values [] predicitonError_STD_WorstCaseTimePoints =  new Values[10];
		
		Values [] steeringEntropy_BestCaseTimePoints =  new Values[10];
		Values [] steeringEntropy_WorstCaseTimePoints =  new Values[10];
		
		
		for (int i = 0; i < 10; i++) {		
			steer_STD_BestCaseTimePoints[i] = new Values();
			steer_STD_WorstCaseTimePoints[i] = new Values();
			steer_Ave_BestCaseTimePoints[i] = new Values();
			steer_Ave_WorstCaseTimePoints[i] = new Values();
			MPH_STD_BestCaseTimePoints[i] = new Values();
			MPH_STD_WorstCaseTimePoints[i] = new Values();
			MPH_Ave_BestCaseTimePoints[i] = new Values();
			MPH_Ave_WorstCaseTimePoints[i] = new Values();
			zeroSteer_Percentage_BestCaseTimePoints[i] = new Values();
			zeroSteer_Percentage_WorstCaseTimePoints[i] = new Values();
			steer2D_Percentage_BestCaseTimePoints[i] = new Values();
			steer2D_Percentage_WorstCaseTimePoints[i] = new Values();
			steer3D_Percentage_BestCaseTimePoints[i] = new Values();
			steer3D_Percentage_WorstCaseTimePoints[i] = new Values();
			
			FCCS_BestCaseTimePoints[i] = new Values();
			FCCS_WorstCaseTimePoints[i] = new Values();
			
			predicitonError_STD_BestCaseTimePoints[i] = new Values();
			predicitonError_STD_WorstCaseTimePoints[i] = new Values();
			steeringEntropy_BestCaseTimePoints[i] = new Values();
			steeringEntropy_WorstCaseTimePoints[i] = new Values();
		}
		
		
		for (int i = 0; i < participantsData.size(); i++) {
			Data data = participantsData.get(i);
			System.out.println(data.ID);

			for (int j = 0; j < data.sessions.size(); j++) {
				switch (data.condition){
				case BestCase:
					steer_STD_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteering_STD_RawData());
					MPH_STD_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionMPH_STD_RawData());
					steer_Ave_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteering_Ave_RawData());
					MPH_Ave_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionMPH_Ave_RawData());
					zeroSteer_Percentage_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionZeroSteer_RawData());
					steer2D_Percentage_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSession2DSteer_RawData());
					steer3D_Percentage_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSession3DSteer_RawData());
					FCCS_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionFastCorrectiveCounterSteering_RawData());
					
					predicitonError_STD_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionPredicitonError_STD_RawData());
					steeringEntropy_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteeringEntorpy_RawData());
					break;
				case WorstCase:
					steer_STD_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteering_STD_RawData());
					MPH_STD_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionMPH_STD_RawData());
					steer_Ave_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteering_Ave_RawData());
					MPH_Ave_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionMPH_Ave_RawData());
					zeroSteer_Percentage_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionZeroSteer_RawData());
					steer2D_Percentage_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSession2DSteer_RawData());
					steer3D_Percentage_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSession3DSteer_RawData());
					FCCS_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionFastCorrectiveCounterSteering_RawData());
					
					predicitonError_STD_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionPredicitonError_STD_RawData());
					steeringEntropy_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteeringEntorpy_RawData());
				}
			}
		}
		
		////////////////        writing to file       ////////////////////////
		outputCSV.println(",,1,2,3,4,,5,6,7,8");

		// steer STD
		outputCSV.print("Best_steer_STD");
		for (int i = 0; i < steer_STD_BestCaseTimePoints.length; i++) {
			double s=steer_STD_BestCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_steer_STD");
		for (int i = 0; i < steer_STD_WorstCaseTimePoints.length; i++) {
			double s=steer_STD_WorstCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();		
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		// steer Ave
		outputCSV.print("Best_steer_Ave");
		for (int i = 0; i < steer_Ave_BestCaseTimePoints.length; i++) {
			double s=steer_Ave_BestCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_steer_Ave");
		for (int i = 0; i < steer_Ave_WorstCaseTimePoints.length; i++) {
			double s=steer_Ave_WorstCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();

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

		// Zero Steer Ave : Percentage of Frames
		outputCSV.print("Best_ZeroSteer_Percentage");
		for (int i = 0; i < zeroSteer_Percentage_BestCaseTimePoints.length; i++) {
			double s= zeroSteer_Percentage_BestCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_ZeroSteer_Percentage");
		for (int i = 0; i < zeroSteer_Percentage_WorstCaseTimePoints.length; i++) {
			double s= zeroSteer_Percentage_WorstCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		// more than 0.2 degree Steer Ave : Percentage of Frames
		outputCSV.print("Best_Steer2D_Percentage");
		for (int i = 0; i < steer2D_Percentage_BestCaseTimePoints.length; i++) {
			double s= steer2D_Percentage_BestCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_Steer2D_Percentage");
		for (int i = 0; i < steer2D_Percentage_WorstCaseTimePoints.length; i++) {
			double s= steer2D_Percentage_WorstCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		// more than 0.3 degree Steer Ave : Percentage of Frames
		outputCSV.print("Best_Steer3D_Percentage");
		for (int i = 0; i < steer3D_Percentage_BestCaseTimePoints.length; i++) {
			double s= steer3D_Percentage_BestCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_Steer3D_Percentage");
		for (int i = 0; i < steer3D_Percentage_WorstCaseTimePoints.length; i++) {
			double s= steer3D_Percentage_WorstCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		// number of Fast Corrective Counter Steering : FCCS
		outputCSV.print("Best_FCCS");
		for (int i = 0; i < FCCS_BestCaseTimePoints.length; i++) {
			double s=FCCS_BestCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_FCCS");
		for (int i = 0; i < FCCS_WorstCaseTimePoints.length; i++) {
			double s=FCCS_WorstCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		// predicitonError STD
		outputCSV.print("Best_predictionError_STD");
		for (int i = 0; i < predicitonError_STD_BestCaseTimePoints.length; i++) {
			double s=predicitonError_STD_BestCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_predicitonError_STD");
		for (int i = 0; i < predicitonError_STD_WorstCaseTimePoints.length; i++) {
			double s=predicitonError_STD_WorstCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		// Steering Entropy Average
		outputCSV.print("Best_steeringEntropy");
		for (int i = 0; i < steeringEntropy_BestCaseTimePoints.length; i++) {
			double s=steeringEntropy_BestCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_steeringEntropy");
		for (int i = 0; i < steeringEntropy_WorstCaseTimePoints.length; i++) {
			double s=steeringEntropy_WorstCaseTimePoints[i].average(); 
			
			outputCSV.print(","+s);
			
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();



		outputCSV.close();
	}
}
