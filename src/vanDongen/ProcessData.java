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

public class ProcessData {
	private static Vector<SubjectData> participantsData;
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
		File directoryPVT = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/PVT Raw data");
		PVTdata.process(directoryPVT.toPath());

		participantsData = new Vector<SubjectData>();
		File directory = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Data(report)");
		for (File file : directory.listFiles()){

			//if (file.getName().endsWith(".rpt") && file.getName().substring(0,4).equals("3232")) { // for testing 
			if (file.getName().endsWith(".rpt")) {
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
		
		File extractedDataDirectory = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Drexel Extracted");
		process(extractedDataDirectory.toPath());

		File rawDataDirectory = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Raw Data flat");
		for (int i = 0; i < participantsData.size(); i++){
			SubjectData subjectData = participantsData.get(i);
			String id = subjectData.ID;
			for (int j = 0; j < subjectData.sessions.size(); j++) {
				Session s  = subjectData.sessions.get(j);
				String s_number = s.sessionNumber;
				for (File file : rawDataDirectory.listFiles()) {
					if (file.getName().startsWith("DRV") && file.getName().contains(id) && file.getName().contains("B"+ s_number)){
						s.addRawData(file);
					}
				}		
			}
		}
		
		try {		
//			File output = 
//					new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Result_Human_Driving/Results_Human_TimePoints_Extracted.csv");
//			WriteToFileTimePoints(output);

//			File outputIndividualTimePoints = 
//					new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Result_Human_Driving/Results_Human_TimePoints_Extracted_Individual.csv");
//			WriteToFileIndividual(outputIndividualTimePoints);
//
//			File outputCumulativeTimePoints = 
//					new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Result_Human_Driving/Results_Human_TimePoints_Extracted_Cumulative.csv");
//			WriteToFileCumulativeAffect(outputCumulativeTimePoints);

//			File outputInividualExtracted = 
//					new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Result_Human_Driving/Results_Human_Individual_Extracted.csv");
//			WriteToFileIndividual(outputInividualExtracted);
//			
//			File outputInividualPVT_Blocks = 
//					new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Result_Human_Driving/Results_Human_Individual_PVT_Blocks.csv");
//			WriteToFileInividualPVTBlocks(outputInividualPVT_Blocks);
			
//			File outputSPSS_LatDev = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Analysis/Human_LatDev_SPSS.csv");
//			WriteToFileSPSS_LatDevSTD(outputSPSS_LatDev);
//
//			File outputSPSS_SteeringDev = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Analysis/Human_SteeringDev_SPSS.csv");
//			WriteToFileSPSS_SteeringDevSTD(outputSPSS_SteeringDev);
//			
//			File outputSPSS_MPH_Dev = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Analysis/Human_MPH_Dev_SPSS.csv");
//			WriteToFileSPSS_MPH_STD(outputSPSS_MPH_Dev);
		
			File outputInividualDrivingSegments = 
					new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Result_Human_Driving/Results_Human_Individual_DrivingSegments.csv");
			WriteToFileInividualDrivingSegments(outputInividualDrivingSegments);
			
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
							SubjectData subjectData = participantsData.get(i);
							for (int j = 0; j < subjectData.sessions.size(); j++) {
								Session s  = subjectData.sessions.get(j);
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


	static String titlesForSPSSfile = "Subj. #,Cond."
			+ ",early_d1_t1_b1,early_d1_t1_b2,early_d1_t1_b3,early_d1_t1_b4,early_d1_t1_b5,early_d1_t1_b6,early_d1_t1_b7,early_d1_t1_b8"
			+ ",early_d1_t2_b1,early_d1_t2_b2,early_d1_t2_b3,early_d1_t2_b4,early_d1_t2_b5,early_d1_t2_b6,early_d1_t2_b7,early_d1_t2_b8"
			+ ",early_d1_t3_b1,early_d1_t3_b2,early_d1_t3_b3,early_d1_t3_b4,early_d1_t3_b5,early_d1_t3_b6,early_d1_t3_b7,early_d1_t3_b8"
			+ ",early_d1_t4_b1,early_d1_t4_b2,early_d1_t4_b3,early_d1_t4_b4,early_d1_t4_b5,early_d1_t4_b6,early_d1_t4_b7,early_d1_t4_b8"
			                                              
			+ ",early_d2_t1_b1,early_d2_t1_b2,early_d2_t1_b3,early_d2_t1_b4,early_d2_t1_b5,early_d2_t1_b6,early_d2_t1_b7,early_d2_t1_b8"
			+ ",early_d2_t2_b1,early_d2_t2_b2,early_d2_t2_b3,early_d2_t2_b4,early_d2_t2_b5,early_d2_t2_b6,early_d2_t2_b7,early_d2_t2_b8"
			+ ",early_d2_t3_b1,early_d2_t3_b2,early_d2_t3_b3,early_d2_t3_b4,early_d2_t3_b5,early_d2_t3_b6,early_d2_t3_b7,early_d2_t3_b8"
			+ ",early_d2_t4_b1,early_d2_t4_b2,early_d2_t4_b3,early_d2_t4_b4,early_d2_t4_b5,early_d2_t4_b6,early_d2_t4_b7,early_d2_t4_b8"
			                                               
			+ ",early_d3_t1_b1,early_d3_t1_b2,early_d3_t1_b3,early_d3_t1_b4,early_d3_t1_b5,early_d3_t1_b6,early_d3_t1_b7,early_d3_t1_b8"
			+ ",early_d3_t2_b1,early_d3_t2_b2,early_d3_t2_b3,early_d3_t2_b4,early_d3_t2_b5,early_d3_t2_b6,early_d3_t2_b7,early_d3_t2_b8"
			+ ",early_d3_t3_b1,early_d3_t3_b2,early_d3_t3_b3,early_d3_t3_b4,early_d3_t3_b5,early_d3_t3_b6,early_d3_t3_b7,early_d3_t3_b8"
			+ ",early_d3_t4_b1,early_d3_t4_b2,early_d3_t4_b3,early_d3_t4_b4,early_d3_t4_b5,early_d3_t4_b6,early_d3_t4_b7,early_d3_t4_b8"
			                                              
			+ ",early_d4_t1_b1,early_d4_t1_b2,early_d4_t1_b3,early_d4_t1_b4,early_d4_t1_b5,early_d4_t1_b6,early_d4_t1_b7,early_d4_t1_b8"
			+ ",early_d4_t2_b1,early_d4_t2_b2,early_d4_t2_b3,early_d4_t2_b4,early_d4_t2_b5,early_d4_t2_b6,early_d4_t2_b7,early_d4_t2_b8"
			+ ",early_d4_t3_b1,early_d4_t3_b2,early_d4_t3_b3,early_d4_t3_b4,early_d4_t3_b5,early_d4_t3_b6,early_d4_t3_b7,early_d4_t3_b8"
			+ ",early_d4_t4_b1,early_d4_t4_b2,early_d4_t4_b3,early_d4_t4_b4,early_d4_t4_b5,early_d4_t4_b6,early_d4_t4_b7,early_d4_t4_b8"
			                                              
			+ ",early_d5_t1_b1,early_d5_t1_b2,early_d5_t1_b3,early_d5_t1_b4,early_d5_t1_b5,early_d5_t1_b6,early_d5_t1_b7,early_d5_t1_b8"
			+ ",early_d5_t2_b1,early_d5_t2_b2,early_d5_t2_b3,early_d5_t2_b4,early_d5_t2_b5,early_d5_t2_b6,early_d5_t2_b7,early_d5_t2_b8"
			+ ",early_d5_t3_b1,early_d5_t3_b2,early_d5_t3_b3,early_d5_t3_b4,early_d5_t3_b5,early_d5_t3_b6,early_d5_t3_b7,early_d5_t3_b8"
			+ ",early_d5_t4_b1,early_d5_t4_b2,early_d5_t4_b3,early_d5_t4_b4,early_d5_t4_b5,early_d5_t4_b6,early_d5_t4_b7,early_d5_t4_b8"
			
			+ ",late_d1_t1_b1,late_d1_t1_b2,late_d1_t1_b3,late_d1_t1_b4,late_d1_t1_b5,late_d1_t1_b6,late_d1_t1_b7,late_d1_t1_b8"
			+ ",late_d1_t2_b1,late_d1_t2_b2,late_d1_t2_b3,late_d1_t2_b4,late_d1_t2_b5,late_d1_t2_b6,late_d1_t2_b7,late_d1_t2_b8"
			+ ",late_d1_t3_b1,late_d1_t3_b2,late_d1_t3_b3,late_d1_t3_b4,late_d1_t3_b5,late_d1_t3_b6,late_d1_t3_b7,late_d1_t3_b8"
			+ ",late_d1_t4_b1,late_d1_t4_b2,late_d1_t4_b3,late_d1_t4_b4,late_d1_t4_b5,late_d1_t4_b6,late_d1_t4_b7,late_d1_t4_b8"
			                                           
			+ ",late_d2_t1_b1,late_d2_t1_b2,late_d2_t1_b3,late_d2_t1_b4,late_d2_t1_b5,late_d2_t1_b6,late_d2_t1_b7,late_d2_t1_b8"
			+ ",late_d2_t2_b1,late_d2_t2_b2,late_d2_t2_b3,late_d2_t2_b4,late_d2_t2_b5,late_d2_t2_b6,late_d2_t2_b7,late_d2_t2_b8"
			+ ",late_d2_t3_b1,late_d2_t3_b2,late_d2_t3_b3,late_d2_t3_b4,late_d2_t3_b5,late_d2_t3_b6,late_d2_t3_b7,late_d2_t3_b8"
			+ ",late_d2_t4_b1,late_d2_t4_b2,late_d2_t4_b3,late_d2_t4_b4,late_d2_t4_b5,late_d2_t4_b6,late_d2_t4_b7,late_d2_t4_b8"
			                                                             
			+ ",late_d3_t1_b1,late_d3_t1_b2,late_d3_t1_b3,late_d3_t1_b4,late_d3_t1_b5,late_d3_t1_b6,late_d3_t1_b7,late_d3_t1_b8"
			+ ",late_d3_t2_b1,late_d3_t2_b2,late_d3_t2_b3,late_d3_t2_b4,late_d3_t2_b5,late_d3_t2_b6,late_d3_t2_b7,late_d3_t2_b8"
			+ ",late_d3_t3_b1,late_d3_t3_b2,late_d3_t3_b3,late_d3_t3_b4,late_d3_t3_b5,late_d3_t3_b6,late_d3_t3_b7,late_d3_t3_b8"
			+ ",late_d3_t4_b1,late_d3_t4_b2,late_d3_t4_b3,late_d3_t4_b4,late_d3_t4_b5,late_d3_t4_b6,late_d3_t4_b7,late_d3_t4_b8"
			                                                 
			+ ",late_d4_t1_b1,late_d4_t1_b2,late_d4_t1_b3,late_d4_t1_b4,late_d4_t1_b5,late_d4_t1_b6,late_d4_t1_b7,late_d4_t1_b8"
			+ ",late_d4_t2_b1,late_d4_t2_b2,late_d4_t2_b3,late_d4_t2_b4,late_d4_t2_b5,late_d4_t2_b6,late_d4_t2_b7,late_d4_t2_b8"
			+ ",late_d4_t3_b1,late_d4_t3_b2,late_d4_t3_b3,late_d4_t3_b4,late_d4_t3_b5,late_d4_t3_b6,late_d4_t3_b7,late_d4_t3_b8"
			+ ",late_d4_t4_b1,late_d4_t4_b2,late_d4_t4_b3,late_d4_t4_b4,late_d4_t4_b5,late_d4_t4_b6,late_d4_t4_b7,late_d4_t4_b8"
			                                                             
			+ ",late_d5_t1_b1,late_d5_t1_b2,late_d5_t1_b3,late_d5_t1_b4,late_d5_t1_b5,late_d5_t1_b6,late_d5_t1_b7,late_d5_t1_b8"
			+ ",late_d5_t2_b1,late_d5_t2_b2,late_d5_t2_b3,late_d5_t2_b4,late_d5_t2_b5,late_d5_t2_b6,late_d5_t2_b7,late_d5_t2_b8"
			+ ",late_d5_t3_b1,late_d5_t3_b2,late_d5_t3_b3,late_d5_t3_b4,late_d5_t3_b5,late_d5_t3_b6,late_d5_t3_b7,late_d5_t3_b8"
			+ ",late_d5_t4_b1,late_d5_t4_b2,late_d5_t4_b3,late_d5_t4_b4,late_d5_t4_b5,late_d5_t4_b6,late_d5_t4_b7,late_d5_t4_b8"
			
			+ "\n";
			
	static void WriteToFileSPSS_LatDevSTD(File output) throws Exception {
		PrintWriter outputSPSS = null;
		try {
			outputSPSS = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		outputSPSS.println(titlesForSPSSfile);
		
		for (int i = 0; i < participantsData.size(); i++) {

			SubjectData subjectData = participantsData.get(i);
			outputSPSS.print(subjectData.ID+","+subjectData.condition);
			for (int j = 4; j < 44; j++) {
				for (int seg = 2; seg < 10; seg++) {   // dropping the first 2 segment
					outputSPSS.print("," + subjectData.getSessionLanePosAtSegment_STD_Extracted(j, seg));
					outputSPSS.flush();
				}
			}
			outputSPSS.print("\n");
		}
		outputSPSS.close();
	}
	
	static void WriteToFileSPSS_SteeringDevSTD(File output) throws Exception {
		PrintWriter outputSPSS = null;
		try {
			outputSPSS = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		outputSPSS.println(titlesForSPSSfile);
		
		for (int i = 0; i < participantsData.size(); i++) {

			SubjectData subjectData = participantsData.get(i);
			outputSPSS.print(subjectData.ID+","+subjectData.condition);
			for (int j = 4; j < 44; j++) {
				for (int seg = 2; seg < 10; seg++) {   // dropping the first 2 segment
					outputSPSS.print("," + subjectData.getSessionSteeringAtSegment_STD_Extracted(j, seg) );
					outputSPSS.flush();
				}
			}
			outputSPSS.print("\n");
		}
		outputSPSS.close();
	}
	
	
	static void WriteToFileSPSS_MPH_STD(File output) throws Exception {
		PrintWriter outputSPSS = null;
		try {
			outputSPSS = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		outputSPSS.println(titlesForSPSSfile);
		
		for (int i = 0; i < participantsData.size(); i++) {

			SubjectData subjectData = participantsData.get(i);
			outputSPSS.print(subjectData.ID+","+subjectData.condition);
			for (int j = 4; j < 44; j++) {
				for (int seg = 2; seg < 10; seg++) {   // dropping the first 2 segment
					outputSPSS.print("," + subjectData.getSessionMPHAtSegment_STD_RawData(j, seg) );
					outputSPSS.flush();
				}
			}
			outputSPSS.print("\n");
		}
		outputSPSS.close();
	}
	
	/**
	 * @param outputIndividualTimePointsCSV
	 * For now, this function just writes the individual values 
	 * @throws Exception 
	 */
	private static void WriteToFileIndividualWithTime(File outputIndividualTimePoints) throws Exception {

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
				",Pre Lapses,,,Driving,,,,Post Lapses,,,Pre Lapses,,,Driving,,,,Post Lapses,,,Pre Lapses,,,"
						+ "Driving,,,,Post Lapses,,,Pre Lapses,,,Driving,,,,Post Lapses,,,"
						+ ","
						+ "Pre Lapses,,,Driving,,,,Post Lapses,,,Pre Lapses,,,Driving,,,,Post Lapses,,,"
						+ "Pre Lapses,,,Driving,,,,Post Lapses,,,Pre Lapses,,,Driving,,,,Post Lapses,,,");
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
			SubjectData subject = participantsData.get(i);
			System.out.println("Writing to file (Indidual Data): " + subject.ID +" " + subject.sessions.size());
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
	 * This function write to file the cumulative affect first regarding the pvt data
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
				SubjectData subjectData = participantsData.get(i);
				if (subjectData.condition.equals(Conditions.BestCase)){
					System.out.println("Writing to file (Cumulative Affect): " + subjectData.ID +" time point: " + timePoint);
					outputCSV.println(subjectData.ID+","+subjectData.condition );
					outputCSV.println(",Session #,Time pre,Pre Lapses,Post Lapses,");
					outputCSV.flush();
					for (int j = 0; j < subjectData.sessions.size(); j++) {
						if (subjectData.sessions.get(j).timePoint == timePoint){
							int s = subjectData.sessions.get(j).getSessionNumber();
							outputCSV.print("," +subjectData.sessions.get(j).sessionNumber);
							outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsTime(s,pre_post.Pre));
							outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsLapses(s,pre_post.Pre));
							outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsLapses(s,pre_post.Post));
							outputCSV.print(",");
							outputCSV.flush();
							outputCSV.print(",,LP_STD");
							for (int k = 0; k < subjectData.sessions.get(j).straightSegments.size(); k++) {
								outputCSV.print(","+subjectData.sessions.get(j).straightSegments.get(k).lanePos_STD);
								outputCSV.flush();
							}
							outputCSV.print(",,Steering_STD");
							for (int k = 0; k < subjectData.sessions.get(j).straightSegments.size(); k++) {
								outputCSV.print("," + subjectData.sessions.get(j).straightSegments.get(k).steering_STD);
								outputCSV.flush();
							}
							outputCSV.print(",,MPH_STD");
							for (int k = 0; k < subjectData.sessions.get(j).straightSegments.size(); k++) {
								outputCSV.print("," + subjectData.sessions.get(j).straightSegments.get(k).MPH_STD_RawData);
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
				SubjectData subjectData = participantsData.get(i);
				if (subjectData.condition.equals(Conditions.WorstCase)){
					System.out.println("Writing to file (Cumulative Affect): " + subjectData.ID +" time point: " + timePoint);
					outputCSV.println(subjectData.ID+","+subjectData.condition );
					outputCSV.println(",Session #,Time pre,Pre Lapses,Post Lapses,");
					outputCSV.flush();
					for (int j = 0; j < subjectData.sessions.size(); j++) {
						if (subjectData.sessions.get(j).timePoint == timePoint){
							int s = subjectData.sessions.get(j).getSessionNumber();
							outputCSV.print("," +subjectData.sessions.get(j).sessionNumber);
							outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsTime(s,pre_post.Pre));
							outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsLapses(s,pre_post.Pre));
							outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsLapses(s,pre_post.Post));
							outputCSV.print(",");
							outputCSV.flush();
							outputCSV.print(",,LP_STD");
							for (int k = 0; k < subjectData.sessions.get(j).straightSegments.size(); k++) {
								outputCSV.print(","+subjectData.sessions.get(j).straightSegments.get(k).lanePos_STD);
								outputCSV.flush();
							}
							outputCSV.print(",,Steering_STD");
							for (int k = 0; k < subjectData.sessions.get(j).straightSegments.size(); k++) {
								outputCSV.print("," + subjectData.sessions.get(j).straightSegments.get(k).steering_STD);
								outputCSV.flush();
							}
							outputCSV.print(",,MPH_STD");
							for (int k = 0; k < subjectData.sessions.get(j).straightSegments.size(); k++) {
								outputCSV.print("," + subjectData.sessions.get(j).straightSegments.get(k).MPH_STD_RawData);
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
	private static void WriteToFileIndividual(File output) throws Exception {

		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		outputCSV.println("Best Case");
		for (int i = 0; i < participantsData.size(); i++) {
			SubjectData subjectData = participantsData.get(i);
			if (subjectData.condition.equals(Conditions.BestCase)){
				System.out.println("Writing to file (Individual Corrolation): " + subjectData.ID);
				outputCSV.println(subjectData.ID+":"+subjectData.condition );
				outputCSV.flush();
				
				outputCSV.print("Session #");
				for (int j = 4; j < 44; j++) {					
					outputCSV.print("," + subjectData.getSessionNumber(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre Lapses(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsPercentOfLapses(j,pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre FalseStarts(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsPercentOfFalseStarts(j,pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre Median Alert RT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsMedianAlertResponses(j,pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre LSNR_apx");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsLSNRapx(j,pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post Lapses(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsPercentOfLapses(j,pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post FalseStarts(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsPercentOfFalseStarts(j,pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post Median Alert RT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsMedianAlertResponses(j,pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post LSNR_apx");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsLSNRapx(j,pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				
				outputCSV.print("LP_STD");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + subjectData.getSessionLanePos_STD_Extracted(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Steering_STD");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + subjectData.getSessionSteering_STD_Extracted(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("MPH_STD");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + subjectData.getSessionMPH_STD_RawData(j)); // the extracted data does not have the MPH 
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("\n");
				outputCSV.flush();
				}	
			}
		
		outputCSV.println("Worst Case");
		for (int i = 0; i < participantsData.size(); i++) {
			SubjectData subjectData = participantsData.get(i);
			if (subjectData.condition.equals(Conditions.WorstCase)){
				System.out.println("Writing to file (Individual Corrolation): " + subjectData.ID);
				outputCSV.println(subjectData.ID+":"+subjectData.condition );
				outputCSV.flush();
				
				outputCSV.print("Session #");
				for (int j = 4; j < 44; j++) {					
					outputCSV.print("," + subjectData.getSessionNumber(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre Lapses(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsPercentOfLapses(j,pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre FalseStarts(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsPercentOfFalseStarts(j,pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre Median Alert RT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsAveResponses(j,pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre LSNR_apx");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsLSNRapx(j,pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post Lapses(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsPercentOfLapses(j,pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post FalseStarts(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsPercentOfFalseStarts(j,pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post Median Alert RT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsAveResponses(j,pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post LSNR_apx");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsLSNRapx(j,pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				
				outputCSV.print("LP_STD");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + subjectData.getSessionLanePos_STD_Extracted(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Steering_STD");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + subjectData.getSessionSteering_STD_Extracted(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("MPH_STD");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + subjectData.getSessionMPH_STD_RawData(j)); // the extracted data does not have the MPH 
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("\n");
				outputCSV.flush();
			}		
		}
	}

	private static void WriteToFileInividualPVTBlocks(File output) throws Exception {
		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		outputCSV.println("Best Case");
		for (int i = 0; i < participantsData.size(); i++) {
			SubjectData subjectData = participantsData.get(i);
			if (subjectData.condition.equals(Conditions.BestCase)){
				System.out.println("Writing to file (Individual Corrolation): " + subjectData.ID);
				outputCSV.println(subjectData.ID+":"+subjectData.condition );
				outputCSV.flush();

				outputCSV.print("Session #");
				for (int j = 4; j < 44; j++) {					
					outputCSV.print("," + subjectData.getSessionNumber(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre Lapses B1(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfLapses(j, 0, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre Lapses B2(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfLapses(j, 1, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post Lapses B1(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfLapses(j, 0, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post Lapses B2(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfLapses(j, 1, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre FalseStarts B1(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfFalseStarts(j, 0, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre FalseStarts B2(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfFalseStarts(j, 1, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post FalseStarts B1(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfFalseStarts(j, 0, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post FalseStarts B2(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfFalseStarts(j, 1, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre Median B1(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockMedianAlertResponses(j, 0, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre Median B2(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockMedianAlertResponses(j, 1, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post Median B1(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockMedianAlertResponses(j, 0, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post Medain B2(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockMedianAlertResponses(j, 1, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				
				outputCSV.print("Pre LSNR_apx B1");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockLSNRapx(j, 0, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre LSNR_apx B2");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockLSNRapx(j, 1, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("post LSNR_apx B1");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockLSNRapx(j, 0, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("post LSNR_apx B2");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockLSNRapx(j, 1, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");


				outputCSV.print(",");
				outputCSV.print("\n");

				outputCSV.print("\n");
				outputCSV.flush();
			}	
		}

		outputCSV.println("Worst Case");
		for (int i = 0; i < participantsData.size(); i++) {
			SubjectData subjectData = participantsData.get(i);
			if (subjectData.condition.equals(Conditions.WorstCase)){
				System.out.println("Writing to file (Individual Corrolation): " + subjectData.ID);
				outputCSV.println(subjectData.ID+":"+subjectData.condition );
				outputCSV.flush();

				outputCSV.print("Session #");
				for (int j = 4; j < 44; j++) {					
					outputCSV.print("," + subjectData.getSessionNumber(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre Lapses B1(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfLapses(j, 0, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre Lapses B2(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfLapses(j, 1, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post Lapses B1(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfLapses(j, 0, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post Lapses B2(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfLapses(j, 1, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre FalseStarts B1(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfFalseStarts(j, 0, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre FalseStarts B2(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfFalseStarts(j, 1, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post FalseStarts B1(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfFalseStarts(j, 0, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post FalseStarts B2(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockPercentOfFalseStarts(j, 1, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre Median B1(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockMedianAlertResponses(j, 0, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre Median B2(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockMedianAlertResponses(j, 1, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post Median B1(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockMedianAlertResponses(j, 0, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Post Medain B2(P)");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockMedianAlertResponses(j, 1, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre LSNR_apx B1");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockLSNRapx(j, 0, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("Pre LSNR_apx B2");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockLSNRapx(j, 1, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("post LSNR_apx B1");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockLSNRapx(j, 0, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");
				
				outputCSV.print("post LSNR_apx B2");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + PVTdata.getByID(subjectData.ID).getSessionsBlockLSNRapx(j, 1, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");


				outputCSV.print(",");
				outputCSV.print("\n");

				outputCSV.print("\n");
				outputCSV.flush();
			}		
		}
	}

	private static void WriteToFileInividualDrivingSegments(File output) throws Exception {
		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		outputCSV.println("Best Case");
		for (int i = 0; i < participantsData.size(); i++) {
			SubjectData subjectData = participantsData.get(i);
			if (subjectData.condition.equals(Conditions.BestCase)){
				System.out.println("Writing to file (Individual Corrolation): " + subjectData.ID);
				outputCSV.println(subjectData.ID+":"+subjectData.condition );
				outputCSV.flush();

				outputCSV.print("Session #");
				for (int j = 4; j < 44; j++) {					
					outputCSV.print("," + subjectData.getSessionNumber(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				
				for (int S = 2; S < 10 ; S++) {
					outputCSV.print("LatDev Seg " + S);
					for (int j = 4; j < 44; j++) {
						outputCSV.print("," + subjectData.getSessionLanePosAtSegment_STD_Extracted(j, S));
						outputCSV.flush();
					}
					outputCSV.print("\n");
					
				}
				
				for (int S = 2; S < 10 ; S++) {
					outputCSV.print("SteeringSTD Seg " + S);
					for (int j = 4; j < 44; j++) {
						outputCSV.print("," + subjectData.getSessionSteeringAtSegment_STD_Extracted(j, S));
						outputCSV.flush();
					}
					outputCSV.print("\n");
					
				}
				
				for (int S = 2; S < 10 ; S++) {
					outputCSV.print("MPH_STD Seg " + S);
					for (int j = 4; j < 44; j++) {
						outputCSV.print("," + subjectData.getSessionMPHAtSegment_STD_RawData(j, S));
						outputCSV.flush();
					}
					outputCSV.print("\n");
					
				}
				

				outputCSV.print(",");
				outputCSV.print("\n");

				outputCSV.print("\n");
				outputCSV.flush();
			}	
		}

		outputCSV.println("Worst Case");
		for (int i = 0; i < participantsData.size(); i++) {
			SubjectData subjectData = participantsData.get(i);
			if (subjectData.condition.equals(Conditions.WorstCase)){
				System.out.println("Writing to file (Individual Corrolation): " + subjectData.ID);
				outputCSV.println(subjectData.ID+":"+subjectData.condition );
				outputCSV.flush();

				outputCSV.print("Session #");
				for (int j = 4; j < 44; j++) {					
					outputCSV.print("," + subjectData.getSessionNumber(j));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				for (int S = 2; S < 10 ; S++) {
					outputCSV.print("LatDev Seg " + S);
					for (int j = 4; j < 44; j++) {
						outputCSV.print("," + subjectData.getSessionLanePosAtSegment_STD_Extracted(j, S));
						outputCSV.flush();
					}
					outputCSV.print("\n");
					
				}
				
				for (int S = 2; S < 10 ; S++) {
					outputCSV.print("SteeringSTD Seg " + S);
					for (int j = 4; j < 44; j++) {
						outputCSV.print("," + subjectData.getSessionSteeringAtSegment_STD_Extracted(j, S));
						outputCSV.flush();
					}
					outputCSV.print("\n");
					
				}
				
				for (int S = 2; S < 10 ; S++) {
					outputCSV.print("MPH_STD Seg " + S);
					for (int j = 4; j < 44; j++) {
						outputCSV.print("," + subjectData.getSessionMPHAtSegment_STD_RawData(j, S));
						outputCSV.flush();
					}
					outputCSV.print("\n");
					
				}


				outputCSV.print(",");
				outputCSV.print("\n");

				outputCSV.print("\n");
				outputCSV.flush();
			}		
		}
	}

	static void WriteToFileTimePoints(File output) {
		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Values [] steer_STD_BestCaseTimePoints =  new Values[10];
		Values [] steer_STD_WorstCaseTimePoints =  new Values[10];


		Values [] lanePos_STD_BestCaseTimePoints =  new Values[10];
		Values [] lanePos_STD_WorstCaseTimePoints =  new Values[10];

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

		Values [] MPH_STD_BestCaseTimePoints =  new Values[10];
		Values [] MPH_STD_WorstCaseTimePoints =  new Values[10];

		for (int i = 0; i < 10; i++) {

			steer_STD_BestCaseTimePoints[i] = new Values();
			steer_STD_WorstCaseTimePoints[i] = new Values();

			lanePos_STD_BestCaseTimePoints[i] = new Values();
			lanePos_STD_WorstCaseTimePoints[i] = new Values();

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

			MPH_STD_BestCaseTimePoints[i] = new Values();
			MPH_STD_WorstCaseTimePoints[i] = new Values();
		}


		for (int i = 0; i < participantsData.size(); i++) {
			SubjectData subjectData = participantsData.get(i);
			System.out.println("Writing to file (Time Points): " + subjectData.ID);

			for (int j = 0; j < subjectData.sessions.size(); j++) {
				switch (subjectData.condition){
				case BestCase:
					steer_STD_BestCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionSteering_STD_Extracted());
					lanePos_STD_BestCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionLanePos_STD_Extracted());

					zeroSteer_Percentage_BestCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionZeroSteer_Extracted());
					steer2D_Percentage_BestCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSession2DSteer_Extracted());
					steer3D_Percentage_BestCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSession3DSteer_Extracted());
					FCCS_BestCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionFastCorrectiveCounterSteering_Extracted());

					predicitonError_STD_BestCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionPredicitonError_STD_Extracted());
					steeringEntropy_BestCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionSteeringEntorpy_Extracted());
					if (subjectData.sessions.get(j).getSessionMPH_STD_RawData()!=0)
						MPH_STD_BestCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionMPH_STD_RawData());
					break;
				case WorstCase:
					steer_STD_WorstCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionSteering_STD_Extracted());
					lanePos_STD_WorstCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionLanePos_STD_Extracted());

					zeroSteer_Percentage_WorstCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionZeroSteer_Extracted());
					steer2D_Percentage_WorstCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSession2DSteer_Extracted());
					steer3D_Percentage_WorstCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSession3DSteer_Extracted());
					FCCS_WorstCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionFastCorrectiveCounterSteering_Extracted());

					predicitonError_STD_WorstCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionPredicitonError_STD_Extracted());
					steeringEntropy_WorstCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionSteeringEntorpy_Extracted());
					if (subjectData.sessions.get(j).getSessionMPH_STD_RawData()!=0)
						MPH_STD_WorstCaseTimePoints[subjectData.sessions.get(j).timePoint].add(subjectData.sessions.get(j).getSessionMPH_STD_RawData());
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

		// MPH STD
		outputCSV.print("Best_MPH_STD");
		for (int i = 0; i < MPH_STD_BestCaseTimePoints.length; i++) {
			if (i==5)
				outputCSV.print(",");
			double s=MPH_STD_BestCaseTimePoints[i].average(); 
			outputCSV.print(","+s);
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_MPH_STD");
		for (int i = 0; i < MPH_STD_WorstCaseTimePoints.length; i++) {
			if (i==5)
				outputCSV.print(",");
			double s=MPH_STD_WorstCaseTimePoints[i].average(); 
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

}

