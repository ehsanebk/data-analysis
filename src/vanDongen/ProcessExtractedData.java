package vanDongen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Vector;
import analysis.Utilities;
import analysis.Values;

public class ProcessExtractedData {
	private static Vector<Data> participantsData;
	private static ProcessPVT PVTdata;

	private static String[] bestCasesNum = {"3001","3025","3040","3086",
			"3206","3232","3256","3275","3386","3408",
			"3440","3574","3579","3620"};
	private static String[] worstCasesNum = {"3047",
			"3122","3171","3207","3215","3220",
			"3309","3311","3359","3421","3512","3570","3674"};

	public static void main(String[] args) {

		//procssing the PVT data
		PVTdata = new ProcessPVT();
		File directoryPVT = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/PVT Raw data");
		//File directoryPVT = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/PVT Raw data");
		PVTdata.process(directoryPVT.toPath());

		participantsData = new Vector<Data>();
		File directory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Data(report)");
		//File directory = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Data(report)");
		for (File file : directory.listFiles()){

			//if (file.getName().endsWith(".rpt") && file.getName().substring(0,4).equals("3232")) { // for testing 
			if (file.getName().endsWith(".rpt")) {
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
		
		File extractedDataDirectory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Drexel Extracted");
		//File extractedDataDirectory = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Drexel Extracted");
		process(extractedDataDirectory.toPath());

		File rawDataDirectory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Raw Data flat");
		//File rawDataDirectory = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Raw Data flat");
		for (int i = 0; i < participantsData.size(); i++){
			Data data = participantsData.get(i);
			String id = data.ID;
			for (int j = 0; j < data.sessions.size(); j++) {
				Session s  = data.sessions.get(j);
				String s_number = s.sessionNumber;
				for (File file : rawDataDirectory.listFiles()) {
					if (file.getName().startsWith("DRV") && file.getName().contains(id) && file.getName().contains("B"+ s_number)){
						s.addRawData(file);
					}
				}		
			}
		}
		
		try {		
			File output = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Result_Human_Driving/Results_TimePoints_Extracted.csv");
			//		File output = 
			//				new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Result_Human_Driving/Results_TimePoints_Extracted.csv");
			WriteToFile(output);

			//		File outputIndividualTimePoints = 
			//				new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Result_Human_Driving/Results_TimePoints_Extracted_Individual.csv");
			File outputIndividualTimePoints = 
					new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Result_Human_Driving/Results_TimePoints_Extracted_Individual.csv");
			WriteToFileIndividual(outputIndividualTimePoints);


			//		File outputIndividualTimePoints = 
			//				new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Result_Human_Driving/Results_TimePoints_Extracted_Individual.csv");
			File outputCumulativeTimePoints = 
					new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Result_Human_Driving/Results_TimePoints_Extracted_Cumulative.csv");
			WriteToFileCumulativeAffect(outputCumulativeTimePoints);

			File outputInividualExtracted = 
					new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/Result_Human_Driving/Results_Human_Individual_Extracted.csv");
			WriteToFileInividualCorrelation(outputInividualExtracted);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void process(Path dir) {
		try {
			for (Path inPath : Files.newDirectoryStream(dir)) {
				File inPathFile= inPath.toFile();
				if (Files.isDirectory(inPath)) {
					process(inPath);
				} else if (inPathFile.getName().toLowerCase().endsWith(".rec.txt") 
						&& inPathFile.getName().toString().toLowerCase().startsWith("drv")) {
					String ID = inPathFile.getName().substring(4,8);
					for (int i = 0; i < participantsData.size(); i++){
						if(participantsData.get(i).ID.equals(ID))
						{
							Data data = participantsData.get(i);
							for (int j = 0; j < data.sessions.size(); j++) {
								Session s  = data.sessions.get(j);
								String s_number = s.sessionNumber;	
								if (inPathFile.getName().contains("B"+ s_number)){
									s.addProssesedData(inPathFile);
									break;
								}
							}		
							break;
						}
					}
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
					steer_STD_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteering_STD_Extracted());
					lanePos_STD_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionLanePos_STD_Extracted());
					steer_Ave_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteering_Ave_Extracted());
					lanePos_Ave_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionLanePos_Ave_Extracted());

					zeroSteer_Percentage_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionZeroSteer_Extracted());
					steer2D_Percentage_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSession2DSteer_Extracted());
					steer3D_Percentage_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSession3DSteer_Extracted());
					FCCS_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionFastCorrectiveCounterSteering_Extracted());

					predicitonError_STD_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionPredicitonError_STD_Extracted());
					steeringEntropy_BestCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteeringEntorpy_Extracted());
					break;
				case WorstCase:
					steer_STD_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteering_STD_Extracted());
					lanePos_STD_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionLanePos_STD_Extracted());
					steer_Ave_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteering_Ave_Extracted());
					lanePos_Ave_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionLanePos_Ave_Extracted());

					zeroSteer_Percentage_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionZeroSteer_Extracted());
					steer2D_Percentage_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSession2DSteer_Extracted());
					steer3D_Percentage_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSession3DSteer_Extracted());
					FCCS_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionFastCorrectiveCounterSteering_Extracted());

					predicitonError_STD_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionPredicitonError_STD_Extracted());
					steeringEntropy_WorstCaseTimePoints[data.sessions.get(j).timePoint].add(data.sessions.get(j).getSessionSteeringEntorpy_Extracted());
					break;
				}
			}
		}

		////////////////        writing to file       ////////////////////////
		outputCSV.println(",,1,2,3,4,,5,6,7,8");

		// steer STD
		outputCSV.print("Best_steer_STD");
		for (int i = 0; i < steer_STD_BestCaseTimePoints.length; i++) {
			if (i==5)
				outputCSV.print(",");
			double s=steer_STD_BestCaseTimePoints[i].average(); 
			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_steer_STD");
		for (int i = 0; i < steer_STD_WorstCaseTimePoints.length; i++) {
			if (i==5)
				outputCSV.print(",");
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
			if (i==5)
				outputCSV.print(",");
			double s=steer_Ave_BestCaseTimePoints[i].average(); 
			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_steer_Ave");
		for (int i = 0; i < steer_Ave_WorstCaseTimePoints.length; i++) {
			if (i==5)
				outputCSV.print(",");
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
			if (i==5)
				outputCSV.print(",");
			double s=lanePos_STD_BestCaseTimePoints[i].average(); 
			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_lanePos_STD");
		for (int i = 0; i < lanePos_STD_WorstCaseTimePoints.length; i++) {
			if (i==5)
				outputCSV.print(",");
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
			if (i==5)
				outputCSV.print(",");
			double s=lanePos_Ave_BestCaseTimePoints[i].average(); 
			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_lanePos_Ave");
		for (int i = 0; i < lanePos_Ave_WorstCaseTimePoints.length; i++) {
			if (i==5)
				outputCSV.print(",");
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
			if (i==5)
				outputCSV.print(",");
			double s= zeroSteer_Percentage_BestCaseTimePoints[i].average(); 
			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_ZeroSteer_Percentage");
		for (int i = 0; i < zeroSteer_Percentage_WorstCaseTimePoints.length; i++) {
			if (i==5)
				outputCSV.print(",");
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
			if (i==5)
				outputCSV.print(",");
			double s= steer2D_Percentage_BestCaseTimePoints[i].average(); 
			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_Steer2D_Percentage");
		for (int i = 0; i < steer2D_Percentage_WorstCaseTimePoints.length; i++) {
			if (i==5)
				outputCSV.print(",");
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
			if (i==5)
				outputCSV.print(",");
			double s= steer3D_Percentage_BestCaseTimePoints[i].average(); 
			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_Steer3D_Percentage");
		for (int i = 0; i < steer3D_Percentage_WorstCaseTimePoints.length; i++) {
			if (i==5)
				outputCSV.print(",");
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
			if (i==5)
				outputCSV.print(",");
			double s=FCCS_BestCaseTimePoints[i].average(); 
			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_FCCS");
		for (int i = 0; i < FCCS_WorstCaseTimePoints.length; i++) {
			if (i==5)
				outputCSV.print(",");
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
			if (i==5)
				outputCSV.print(",");
			double s=predicitonError_STD_BestCaseTimePoints[i].average(); 
			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_predicitonError_STD");
		for (int i = 0; i < predicitonError_STD_WorstCaseTimePoints.length; i++) {
			if (i==5)
				outputCSV.print(",");
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
			if (i==5)
				outputCSV.print(",");
			double s=steeringEntropy_BestCaseTimePoints[i].average(); 
			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_steeringEntropy");
		for (int i = 0; i < steeringEntropy_WorstCaseTimePoints.length; i++) {
			if (i==5)
				outputCSV.print(",");
			double s=steeringEntropy_WorstCaseTimePoints[i].average(); 
			outputCSV.print(","+s);

		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("\n\n\n");
		outputCSV.flush();
		
		outputCSV.close();
	}

	/**
	 * @param outputIndividualTimePointsCSV
	 * For now, this function just writes the individual values 
	 * @throws Exception 
	 */
	private static void WriteToFileIndividual(File outputIndividualTimePoints) throws Exception {

		PrintWriter output = null;
		try {
			output = new PrintWriter(outputIndividualTimePoints);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		output.println(
				",1,,,,,,,,,,2,,,,,,,,,,3,,,,,,,,,,4,,,,,,,,,,"+
				",5,,,,,,,,,,6,,,,,,,,,,7,,,,,,,,,,8,,,,,,,,,,");
		output.println(
				",Pre PVT,,,Driving,,,,Post PVT,,,Pre PVT,,,Driving,,,,Post PVT,,,Pre PVT,,,"
						+ "Driving,,,,Post PVT,,,Pre PVT,,,Driving,,,,Post PVT,,,"
						+ ","
						+ "Pre PVT,,,Driving,,,,Post PVT,,,Pre PVT,,,Driving,,,,Post PVT,,,"
						+ "Pre PVT,,,Driving,,,,Post PVT,,,Pre PVT,,,Driving,,,,Post PVT,,,");
		output.println(
				",#,Time,Lapses,#,LPSD,SteeringSTD,MPH_STD,#,Time,Lapses,"
						+ "#,Time,Lapses,#,LPSD,SteeringSTD,MPH_STD,#,Time,Lapses,"
						+ "#,Time,Lapses,#,LPSD,SteeringSTD,MPH_STD,#,Time,Lapses,"
						+ "#,Time,Lapses,#,LPSD,SteeringSTD,MPH_STD,#,Time,Lapses,"
						+ ","
						+ "#,Time,Lapses,#,LPSD,SteeringSTD,MPH_STD,#,Time,Lapses,"
						+ "#,Time,Lapses,#,LPSD,SteeringSTD,MPH_STD,#,Time,Lapses,"
						+ "#,Time,Lapses,#,LPSD,SteeringSTD,MPH_STD,#,Time,Lapses,"
						+ "#,Time,Lapses,#,LPSD,SteeringSTD,MPH_STD,#,Time,Lapses,");

		SimpleDateFormat timeFormat = new SimpleDateFormat ("MM/dd/yy HH:mm"); // for parsing date formats
		for (int i = 0; i < participantsData.size(); i++) {
			Data subject = participantsData.get(i);
			System.out.println(" Processing to write to file (indidual data): " + subject.ID +" " + subject.sessions.size());

			output.println(subject.ID + ",Extracted");
			for (int k = 4; k <24 ; k++) {

				//pre PVT
				output.print("," + PVTdata.getByID(subject.ID).getSessionsNumber(k,pre_post.Pre));
				output.print("," + PVTdata.getByID(subject.ID).getSessionsTime(k,pre_post.Pre));
				output.print("," + PVTdata.getByID(subject.ID).getSessionsLapses(k,pre_post.Pre));
				//driving
				output.print("," + subject.getSessionNumber(k));
				output.print("," + subject.getSessionLanePos_STD_Extracted(k));
				output.print("," + subject.getSessionSteering_STD_Extracted(k));
				output.print("," + subject.getSessionMPH_STD_RawData(k));
				//post PVT
				output.print("," + PVTdata.getByID(subject.ID).getSessionsNumber(k,pre_post.Post));
				output.print("," + PVTdata.getByID(subject.ID).getSessionsTime(k,pre_post.Post));
				output.print("," + PVTdata.getByID(subject.ID).getSessionsLapses(k,pre_post.Post));

				if (k%4 ==3 ){
					output.print("\n");output.flush();
				}
			}
			output.println(",Break");output.flush();
			for (int k = 24; k <44 ; k++) {

				//pre PVT
				output.print("," + PVTdata.getByID(subject.ID).getSessionsNumber(k,pre_post.Pre));
				output.print("," + PVTdata.getByID(subject.ID).getSessionsTime(k,pre_post.Pre));
				output.print("," + PVTdata.getByID(subject.ID).getSessionsLapses(k,pre_post.Pre));
				//driving
				output.print("," + subject.getSessionNumber(k));
				output.print("," + subject.getSessionLanePos_STD_Extracted(k));
				output.print("," + subject.getSessionSteering_STD_Extracted(k));
				output.print("," + subject.getSessionMPH_STD_RawData(k));
				//post PVT
				output.print("," + PVTdata.getByID(subject.ID).getSessionsNumber(k,pre_post.Post));
				output.print("," + PVTdata.getByID(subject.ID).getSessionsTime(k,pre_post.Post));
				output.print("," + PVTdata.getByID(subject.ID).getSessionsLapses(k,pre_post.Post));

				if (k%4 ==3 ){
					output.print("\n");output.flush();
				}
			}
			output.print("\n\n");output.flush();

			//			// For reported 
			//			output.println(data.ID + ",Reported");
			//			output.println(",1,2,3,4");
			//			for (int k = 4; k <24 ; k++) {
			//				if (data.getSessionByNumber(k) != null){
			////					output.print(k);
			////					for (int j = 0; j < data.getSessionByNumber(k).straightSegments.size(); j++) {
			////						output.print("," + data.getSessionByNumber(k).straightSegments.elementAt(j).LANEDEV_STD);
			////					}
			//					output.print("," + data.getSessionByNumber(k).getSessionAverageLANEDEV_STD());
			//				}
			//				if (k%4 ==3 ){
			//					output.print("\n");output.flush();
			//				}
			//			}
			//			output.println(",Break");output.flush();
			//			for (int k = 24; k <44 ; k++) {
			//				if (data.getSessionByNumber(k) != null){
			////					output.print(k);
			////					for (int j = 0; j < data.getSessionByNumber(k).straightSegments.size(); j++) {
			////						output.print("," + data.getSessionByNumber(k).straightSegments.elementAt(j).LANEDEV_STD);
			////					}
			//					output.print("," + data.getSessionByNumber(k).getSessionAverageLANEDEV_STD());
			//				}
			//				if (k%4 ==3 ){
			//					output.print("\n");output.flush();
			//				}
			//			}
			//			output.print("\n\n");output.flush();	
		}
	}
	
	
	/**
	 * @param outputIndividualCumulative
	 * THis function write to file the cumulative affect first regarding the pvt data
	 * and second based on the straight segments in time points 
	 * @throws Exception
	 */
	private static void WriteToFileCumulativeAffect(File outputIndividualCumulative) throws Exception {

		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(outputIndividualCumulative);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		outputCSV.println("Best Case");
		for (int timePoint = 1; timePoint <= 8; timePoint++) {
			outputCSV.println("Time Point "+ timePoint);

			for (int i = 0; i < participantsData.size(); i++) {
				Data data = participantsData.get(i);
				System.out.println(" Processing to write to file : " + data.ID);
				if (data.condition.equals(Conditions.BestCase)){
					outputCSV.println(data.ID+","+data.condition );
					outputCSV.println(",Session #,Time pre,Pre PVT,Post PVT,");
					outputCSV.flush();
					for (int j = 0; j < data.sessions.size(); j++) {
						if (data.sessions.get(j).timePoint == timePoint){
							int s = data.sessions.get(j).getSessionNumber();
							outputCSV.print("," +data.sessions.get(j).sessionNumber);
							outputCSV.print("," + PVTdata.getByID(data.ID).getSessionsTime(s,pre_post.Pre));
							outputCSV.print("," + PVTdata.getByID(data.ID).getSessionsLapses(s,pre_post.Pre));
							outputCSV.print("," + PVTdata.getByID(data.ID).getSessionsLapses(s,pre_post.Post));
							outputCSV.print(",");
							outputCSV.flush();
							outputCSV.print(",,LP_STD");
							for (int k = 0; k < data.sessions.get(j).straightSegments.size(); k++) {
								outputCSV.print(","+data.sessions.get(j).straightSegments.get(k).lanePos_STD);
								outputCSV.flush();
							}
							outputCSV.print(",,Steering_STD");
							for (int k = 0; k < data.sessions.get(j).straightSegments.size(); k++) {
								outputCSV.print("," + data.sessions.get(j).straightSegments.get(k).steering_STD);
								outputCSV.flush();
							}
							outputCSV.print(",,MPH_STD");
							for (int k = 0; k < data.sessions.get(j).straightSegments.size(); k++) {
								outputCSV.print("," + data.sessions.get(j).straightSegments.get(k).SPEED_STD);
								outputCSV.flush();
							}
							outputCSV.print("\n");
							outputCSV.flush();
						}
					}	
				}	
			}
		}

		outputCSV.println("***");
		outputCSV.println("Worst Case");
		for (int timePoint = 1; timePoint <= 8; timePoint++) {
			outputCSV.println("Time Point "+ timePoint);

			for (int i = 0; i < participantsData.size(); i++) {
				Data data = participantsData.get(i);
				System.out.println(" Processing to write to file : " + data.ID);
				if (data.condition.equals(Conditions.WorstCase)){
					outputCSV.println(data.ID+","+data.condition );
					outputCSV.println(",Session #,Time pre,Pre PVT,Post PVT,");
					outputCSV.flush();
					for (int j = 0; j < data.sessions.size(); j++) {
						if (data.sessions.get(j).timePoint == timePoint){
							int s = data.sessions.get(j).getSessionNumber();
							outputCSV.print("," +data.sessions.get(j).sessionNumber);
							outputCSV.print("," + PVTdata.getByID(data.ID).getSessionsTime(s,pre_post.Pre));
							outputCSV.print("," + PVTdata.getByID(data.ID).getSessionsLapses(s,pre_post.Pre));
							outputCSV.print("," + PVTdata.getByID(data.ID).getSessionsLapses(s,pre_post.Post));
							outputCSV.print(",");
							outputCSV.flush();
							outputCSV.print(",,LP_STD");
							for (int k = 0; k < data.sessions.get(j).straightSegments.size(); k++) {
								outputCSV.print(","+data.sessions.get(j).straightSegments.get(k).lanePos_STD);
								outputCSV.flush();
							}
							outputCSV.print(",,Steering_STD");
							for (int k = 0; k < data.sessions.get(j).straightSegments.size(); k++) {
								outputCSV.print("," + data.sessions.get(j).straightSegments.get(k).steering_STD);
								outputCSV.flush();
							}
							outputCSV.print(",,MPH_STD");
							for (int k = 0; k < data.sessions.get(j).straightSegments.size(); k++) {
								outputCSV.print("," + data.sessions.get(j).straightSegments.get(k).SPEED_STD);
								outputCSV.flush();
							}
							outputCSV.print("\n");
							outputCSV.flush();
						}
					}	
				}	
			}
		}
		outputCSV.close();
	}
	
	/**
	 * @param output
	 * THis function write to file the cumulative affect first regarding the pvt data
	 * and second based on the straight segments in time points 
	 * @throws Exception
	 */
	private static void WriteToFileInividualCorrelation(File output) throws Exception {

		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		outputCSV.println("Best Case");
		for (int i = 0; i < participantsData.size(); i++) {
			Data data = participantsData.get(i);
			System.out.println(" Processing to write to file : " + data.ID);
			if (data.condition.equals(Conditions.BestCase)){
				outputCSV.println(data.ID+","+data.condition );
				outputCSV.flush();
				
				outputCSV.print("Session #");
				for (int j = 4; j < 44; j++) {					
					outputCSV.print("," + data.getSessionNumber(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre PVT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(data.ID).getSessionsLapses(j,pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post PVT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(data.ID).getSessionsLapses(j,pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("LP_STD");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + data.getSessionLanePos_STD_Extracted(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Steering_STD");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + data.getSessionSteering_STD_Extracted(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Steer>3");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + data.getSession3DSteer_Extracted(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("MPH_STD");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + data.getSessionMPH_STD_RawData(j)); // the extracted data does not have the MPH 
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("\n");
				outputCSV.flush();
				}	
			}
		
		outputCSV.println("Worst Case");
		for (int i = 0; i < participantsData.size(); i++) {
			Data data = participantsData.get(i);
			System.out.println(" Processing to write to file : " + data.ID);
			if (data.condition.equals(Conditions.WorstCase)){
				outputCSV.println(data.ID+","+data.condition );
				outputCSV.flush();
				
				outputCSV.print("Session #");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + data.getSessionNumber(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre PVT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(data.ID).getSessionsLapses(j,pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post PVT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(data.ID).getSessionsLapses(j,pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("LP_STD");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + data.getSessionLanePos_STD_Extracted(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Steering_STD");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + data.getSessionSteering_STD_Extracted(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Steer>3");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + data.getSession3DSteer_Extracted(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("MPH_STD");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + data.getSessionMPH_STD_RawData(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("\n");
				outputCSV.flush();
				}	
			}
		}
}

