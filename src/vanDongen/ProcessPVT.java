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
	
	private static String[] bestCasesNum = {"3001","3025","3040","3086",
			"3206","3232","3256","3275","3386","3408",
			"3440","3574","3579","3620"};
	private static String[] worstCasesNum = {"3047",
			"3122","3171","3207","3215","3220",
			"3309","3311","3359","3421","3512","3570","3674"};

	public ProcessPVT() {
		participantsDataPVT = new Vector<DataPVT>();
	}
	
	static void process(Path dir) {
		try {
			for (Path inPath : Files.newDirectoryStream(dir)) {
				File inPathFile= inPath.toFile();
				if (Files.isDirectory(inPath)) {
					process(inPath);
				} else if (inPathFile.getName().toLowerCase().endsWith(".pvt")) {
					DataPVT dataPVT = new DataPVT();
					dataPVT.ID = inPathFile.getName();
					if (Utilities.arrayContains(worstCasesNum, dataPVT.ID))
						dataPVT.condition = Conditions.WorstCase;
					else
						dataPVT.condition = Conditions.BestCase;
					participantsDataPVT.add(dataPVT);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		//File directory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/PVT Raw data");
		File directory = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/PVT Raw data");
		
		ProcessPVT.process(directory.toPath());
		
		//File output = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Results_TimePoints_PVT.csv");
		File output = 
				new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Results_TimePoints_PVT.csv");
		WriteToFile(output);
		
		//File outputIndividualTimePoints = 
		//		new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Results_TimePoints_Extracted_Indivual.csv");
		File outputIndividualTimePoints = 
				new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Results_TimePoints_Extracted_Individual.csv");
		//WriteToFileIndividual(outputIndividualTimePoints);
	}

	


	static void WriteToFile(File output) {
		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Values [] steer_STD_BestCaseTimePoints =  new Values[10];
		Values [] steer_STD_WorstCaseTimePoints =  new Values[10];
		Values [] steer_Ave_BestCaseTimePoints =  new Values[10];
		Values [] steer_Ave_WorstCaseTimePoints =  new Values[10];

		Values [] lanePos_STD_BestCaseTimePoints =  new Values[10];
		Values [] lanePos_STD_WorstCaseTimePoints =  new Values[10];
		Values [] lanePos_Ave_BestCaseTimePoints =  new Values[10];
		Values [] lanePos_Ave_WorstCaseTimePoints =  new Values[10];

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
			
			lanePos_STD_BestCaseTimePoints[i] = new Values();
			lanePos_STD_WorstCaseTimePoints[i] = new Values();
			lanePos_Ave_BestCaseTimePoints[i] = new Values();
			lanePos_Ave_WorstCaseTimePoints[i] = new Values();
			
			zeroSteer_Percentage_BestCaseTimePoints[i] =  new Values();
			zeroSteer_Percentage_WorstCaseTimePoints[i] =  new Values();
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
			System.out.println(" Processing to write to file : " + data.ID);

			for (int j = 0; j < data.sessions.size(); j++) {
				switch (data.condition){
				case BestCase:
					steer_STD_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteerWheel_STD_Extraxted());
					lanePos_STD_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionLanePos_STD_Extraxted());
					steer_Ave_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteerWheel_Ave_Extraxted());
					lanePos_Ave_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionLanePos_Ave_Extraxted());
					
					zeroSteer_Percentage_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverageZeroSteer_Extracted());
					steer2D_Percentage_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverage2DSteer_Extracted());
					steer3D_Percentage_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverage3DSteer_Extracted());
					FCCS_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverageFastCorrectiveCounterSteering_Extracted());

					predicitonError_STD_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragepredicitonError_STD_Extracted());
					steeringEntropy_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragesteeringEntorpy_Extracted());
					break;
				case WorstCase:
					steer_STD_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteerWheel_STD_Extraxted());
					lanePos_STD_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionLanePos_STD_Extraxted());
					steer_Ave_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteerWheel_Ave_Extraxted());
					lanePos_Ave_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionLanePos_Ave_Extraxted());
					
					zeroSteer_Percentage_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverageZeroSteer_Extracted());
					steer2D_Percentage_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverage2DSteer_Extracted());
					steer3D_Percentage_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverage3DSteer_Extracted());
					FCCS_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAverageFastCorrectiveCounterSteering_Extracted());

					predicitonError_STD_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragepredicitonError_STD_Extracted());
					steeringEntropy_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionAveragesteeringEntorpy_Extracted());
					break;
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

		// lanePos STD
		outputCSV.print("Best_lanePos_STD");
		for (int i = 0; i < lanePos_STD_BestCaseTimePoints.length; i++) {
			double s=lanePos_STD_BestCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_lanePos_STD");
		for (int i = 0; i < lanePos_STD_WorstCaseTimePoints.length; i++) {
			double s=lanePos_STD_WorstCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();

		// lanePos Ave
		outputCSV.print("Best_lanePos_Ave");
		for (int i = 0; i < lanePos_Ave_BestCaseTimePoints.length; i++) {
			double s=lanePos_Ave_BestCaseTimePoints[i].average(); 

			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_lanePos_Ave");
		for (int i = 0; i < lanePos_Ave_WorstCaseTimePoints.length; i++) {
			double s=lanePos_Ave_WorstCaseTimePoints[i].average(); 

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
