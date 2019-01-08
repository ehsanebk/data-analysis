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

	/**
	 * session numbers
	 * 
	 *     time points: 1	2	3	4		5	6	7	8
	 *     ––––––––––––––––––––––––––––––––––––––––––––––––––
	 *     				4	5	6	7		24	25	26	27
	 *     				8	9	10	11		28	29	30	31
	 *     				12	13	14	15		32	33	34	35
	 *     				16	17	18	19		36	37	38	39
	 *     				20	21	22	23		40	41	42	43
	 *     
	 *     Each session has pre and post values
	 */

	private static Vector<PVT_sessions> participantsDataPVT;

	private static String[] validPVT = {"3040","3047","3086","3122", "3171",
			"3206","3207","3215","3220","3040", "3232",
			"3256","3275","3309","3311","3359","3386","3408","3421",
			"3440","3512","3570","3574",
			"3579","3620","3674"
	};


	public static void main(String[] args) {
		ProcessPVT data = new ProcessPVT();

		File directory = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/PVT Raw data");
		data.process(directory.toPath());

//		File output = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Result_PVT/Human_PVT.csv");
//		try {
//			WriteToFile(output);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		File outputRawLapses = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Result_PVT/Human_PVT_RawLapses.csv");
		try {
			WriteToFileRawLapses(outputRawLapses);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		File outputSPSS_Lapses = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Result_PVT/Human_PVT_SPSS_Lapses.csv");
//		try {
//			WriteToFileSPSS_Lapses(outputSPSS_Lapses);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		File outputSPSS_LSNR_apx = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Result_PVT/Human_PVT_SPSS_LSNR_apx.csv");
//		try {
//			WriteToFileSPSS_LSNR_apx(outputSPSS_LSNR_apx);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}


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


	static PVT_sessions getByID (String ID) throws Exception{
		for (PVT_sessions pvt_sessions : participantsDataPVT) {
			if (pvt_sessions.ID.equals(ID))
				return pvt_sessions;
		}
		throw new Exception("PVT for ID " + ID + " Not found in the data!");
	}

	static void WriteToFileSPSS_Lapses(File output) throws Exception {
		PrintWriter outputSPSS = null;
		try {
			outputSPSS = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		outputSPSS.println("Subj. #,Cond."
				+ ",early_d1_t1_pre_b1,early_d1_t1_pre_b2,early_d1_t1_post_b1,early_d1_t1_post_b2"
				+ ",early_d1_t2_pre_b1,early_d1_t2_pre_b2,early_d1_t2_post_b1,early_d1_t2_post_b2"
				+ ",early_d1_t3_pre_b1,early_d1_t3_pre_b2,early_d1_t3_post_b1,early_d1_t3_post_b2"
				+ ",early_d1_t4_pre_b1,early_d1_t4_pre_b2,early_d1_t4_post_b1,early_d1_t4_post_b2"
				
				+ ",early_d2_t1_pre_b1,early_d2_t1_pre_b2,early_d2_t1_post_b1,early_d2_t1_post_b2"
				+ ",early_d2_t2_pre_b1,early_d2_t2_pre_b2,early_d2_t2_post_b1,early_d2_t2_post_b2"
				+ ",early_d2_t3_pre_b1,early_d2_t3_pre_b2,early_d2_t3_post_b1,early_d2_t3_post_b2"
				+ ",early_d2_t4_pre_b1,early_d2_t4_pre_b2,early_d2_t4_post_b1,early_d2_t4_post_b2"
				
				+ ",early_d3_t1_pre_b1,early_d3_t1_pre_b2,early_d3_t1_post_b1,early_d3_t1_post_b2"
				+ ",early_d3_t2_pre_b1,early_d3_t2_pre_b2,early_d3_t2_post_b1,early_d3_t2_post_b2"
				+ ",early_d3_t3_pre_b1,early_d3_t3_pre_b2,early_d3_t3_post_b1,early_d3_t3_post_b2"
				+ ",early_d3_t4_pre_b1,early_d3_t4_pre_b2,early_d3_t4_post_b1,early_d3_t4_post_b2"
				
				+ ",early_d4_t1_pre_b1,early_d4_t1_pre_b2,early_d4_t1_post_b1,early_d4_t1_post_b2"
				+ ",early_d4_t2_pre_b1,early_d4_t2_pre_b2,early_d4_t2_post_b1,early_d4_t2_post_b2"
				+ ",early_d4_t3_pre_b1,early_d4_t3_pre_b2,early_d4_t3_post_b1,early_d4_t3_post_b2"
				+ ",early_d4_t4_pre_b1,early_d4_t4_pre_b2,early_d4_t4_post_b1,early_d4_t4_post_b2"
				
				+ ",early_d5_t1_pre_b1,early_d5_t1_pre_b2,early_d5_t1_pre_b1,early_d5_t1_pre_b2"
				+ ",early_d5_t2_pre_b1,early_d5_t2_pre_b2,early_d5_t2_pre_b1,early_d5_t2_pre_b2"
				+ ",early_d5_t3_pre_b1,early_d5_t3_pre_b2,early_d5_t3_pre_b1,early_d5_t3_pre_b2"
				+ ",early_d5_t4_pre_b1,early_d5_t4_pre_b2,early_d5_t4_pre_b1,early_d5_t4_pre_b2"
				
				+ ",late_d1_t1_pre_b1,late_d1_t1_pre_b2,late_d1_t1_post_b1,late_d1_t1_post_b2"
				+ ",late_d1_t2_pre_b1,late_d1_t2_pre_b2,late_d1_t2_post_b1,late_d1_t2_post_b2"
				+ ",late_d1_t3_pre_b1,late_d1_t3_pre_b2,late_d1_t3_post_b1,late_d1_t3_post_b2"
				+ ",late_d1_t4_pre_b1,late_d1_t4_pre_b2,late_d1_t4_post_b1,late_d1_t4_post_b2"
				
				+ ",late_d2_t1_pre_b1,late_d2_t1_pre_b2,late_d2_t1_post_b1,late_d2_t1_post_b2"
				+ ",late_d2_t2_pre_b1,late_d2_t2_pre_b2,late_d2_t2_post_b1,late_d2_t2_post_b2"
				+ ",late_d2_t3_pre_b1,late_d2_t3_pre_b2,late_d2_t3_post_b1,late_d2_t3_post_b2"
				+ ",late_d2_t4_pre_b1,late_d2_t4_pre_b2,late_d2_t4_post_b1,late_d2_t4_post_b2"
				                                                   
				+ ",late_d3_t1_pre_b1,late_d3_t1_pre_b2,late_d3_t1_post_b1,late_d3_t1_post_b2"
				+ ",late_d3_t2_pre_b1,late_d3_t2_pre_b2,late_d3_t2_post_b1,late_d3_t2_post_b2"
				+ ",late_d3_t3_pre_b1,late_d3_t3_pre_b2,late_d3_t3_post_b1,late_d3_t3_post_b2"
				+ ",late_d3_t4_pre_b1,late_d3_t4_pre_b2,late_d3_t4_post_b1,late_d3_t4_post_b2"
				                                                
				+ ",late_d4_t1_pre_b1,late_d4_t1_pre_b2,late_d4_t1_post_b1,late_d4_t1_post_b2"
				+ ",late_d4_t2_pre_b1,late_d4_t2_pre_b2,late_d4_t2_post_b1,late_d4_t2_post_b2"
				+ ",late_d4_t3_pre_b1,late_d4_t3_pre_b2,late_d4_t3_post_b1,late_d4_t3_post_b2"
				+ ",late_d4_t4_pre_b1,late_d4_t4_pre_b2,late_d4_t4_post_b1,late_d4_t4_post_b2"
				                                                   
				+ ",late_d5_t1_pre_b1,late_d5_t1_pre_b2,late_d5_t1_post_b1,late_d5_t1_post_b2"
				+ ",late_d5_t2_pre_b1,late_d5_t2_pre_b2,late_d5_t2_post_b1,late_d5_t2_post_b2"
				+ ",late_d5_t3_pre_b1,late_d5_t3_pre_b2,late_d5_t3_post_b1,late_d5_t3_post_b2"
				+ ",late_d5_t4_pre_b1,late_d5_t4_pre_b2,late_d5_t4_post_b1,late_d5_t4_post_b2"
				
				+ "\n"
				);
		for (int i = 0; i < participantsDataPVT.size(); i++) {

			PVT_sessions PVTdata = participantsDataPVT.get(i);
			outputSPSS.print(PVTdata.ID+","+PVTdata.condition);
			for (int j = 4; j < 44; j++) {
				outputSPSS.print(
						"," + PVTdata.getSessionsBlockLapses(j, 0, pre_post.Pre)+ "," + PVTdata.getSessionsBlockLapses(j, 1, pre_post.Pre) + 
						"," + PVTdata.getSessionsBlockLapses(j, 0, pre_post.Post)+ "," + PVTdata.getSessionsBlockLapses(j, 1, pre_post.Post));
				outputSPSS.flush();
			}
			outputSPSS.print("\n");
		}
		outputSPSS.close();
	}
	
	static void WriteToFileSPSS_LSNR_apx(File output) throws Exception {
		PrintWriter outputSPSS = null;
		try {
			outputSPSS = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		outputSPSS.println("Subj. #,Cond."
				+ ",early_d1_t1_pre_b1,early_d1_t1_pre_b2,early_d1_t1_post_b1,early_d1_t1_post_b2"
				+ ",early_d1_t2_pre_b1,early_d1_t2_pre_b2,early_d1_t2_post_b1,early_d1_t2_post_b2"
				+ ",early_d1_t3_pre_b1,early_d1_t3_pre_b2,early_d1_t3_post_b1,early_d1_t3_post_b2"
				+ ",early_d1_t4_pre_b1,early_d1_t4_pre_b2,early_d1_t4_post_b1,early_d1_t4_post_b2"
				
				+ ",early_d2_t1_pre_b1,early_d2_t1_pre_b2,early_d2_t1_post_b1,early_d2_t1_post_b2"
				+ ",early_d2_t2_pre_b1,early_d2_t2_pre_b2,early_d2_t2_post_b1,early_d2_t2_post_b2"
				+ ",early_d2_t3_pre_b1,early_d2_t3_pre_b2,early_d2_t3_post_b1,early_d2_t3_post_b2"
				+ ",early_d2_t4_pre_b1,early_d2_t4_pre_b2,early_d2_t4_post_b1,early_d2_t4_post_b2"
				
				+ ",early_d3_t1_pre_b1,early_d3_t1_pre_b2,early_d3_t1_post_b1,early_d3_t1_post_b2"
				+ ",early_d3_t2_pre_b1,early_d3_t2_pre_b2,early_d3_t2_post_b1,early_d3_t2_post_b2"
				+ ",early_d3_t3_pre_b1,early_d3_t3_pre_b2,early_d3_t3_post_b1,early_d3_t3_post_b2"
				+ ",early_d3_t4_pre_b1,early_d3_t4_pre_b2,early_d3_t4_post_b1,early_d3_t4_post_b2"
				
				+ ",early_d4_t1_pre_b1,early_d4_t1_pre_b2,early_d4_t1_post_b1,early_d4_t1_post_b2"
				+ ",early_d4_t2_pre_b1,early_d4_t2_pre_b2,early_d4_t2_post_b1,early_d4_t2_post_b2"
				+ ",early_d4_t3_pre_b1,early_d4_t3_pre_b2,early_d4_t3_post_b1,early_d4_t3_post_b2"
				+ ",early_d4_t4_pre_b1,early_d4_t4_pre_b2,early_d4_t4_post_b1,early_d4_t4_post_b2"
				
				+ ",early_d5_t1_pre_b1,early_d5_t1_pre_b2,early_d5_t1_pre_b1,early_d5_t1_pre_b2"
				+ ",early_d5_t2_pre_b1,early_d5_t2_pre_b2,early_d5_t2_pre_b1,early_d5_t2_pre_b2"
				+ ",early_d5_t3_pre_b1,early_d5_t3_pre_b2,early_d5_t3_pre_b1,early_d5_t3_pre_b2"
				+ ",early_d5_t4_pre_b1,early_d5_t4_pre_b2,early_d5_t4_pre_b1,early_d5_t4_pre_b2"
				
				+ ",late_d1_t1_pre_b1,late_d1_t1_pre_b2,late_d1_t1_post_b1,late_d1_t1_post_b2"
				+ ",late_d1_t2_pre_b1,late_d1_t2_pre_b2,late_d1_t2_post_b1,late_d1_t2_post_b2"
				+ ",late_d1_t3_pre_b1,late_d1_t3_pre_b2,late_d1_t3_post_b1,late_d1_t3_post_b2"
				+ ",late_d1_t4_pre_b1,late_d1_t4_pre_b2,late_d1_t4_post_b1,late_d1_t4_post_b2"
				
				+ ",late_d2_t1_pre_b1,late_d2_t1_pre_b2,late_d2_t1_post_b1,late_d2_t1_post_b2"
				+ ",late_d2_t2_pre_b1,late_d2_t2_pre_b2,late_d2_t2_post_b1,late_d2_t2_post_b2"
				+ ",late_d2_t3_pre_b1,late_d2_t3_pre_b2,late_d2_t3_post_b1,late_d2_t3_post_b2"
				+ ",late_d2_t4_pre_b1,late_d2_t4_pre_b2,late_d2_t4_post_b1,late_d2_t4_post_b2"
				                                                   
				+ ",late_d3_t1_pre_b1,late_d3_t1_pre_b2,late_d3_t1_post_b1,late_d3_t1_post_b2"
				+ ",late_d3_t2_pre_b1,late_d3_t2_pre_b2,late_d3_t2_post_b1,late_d3_t2_post_b2"
				+ ",late_d3_t3_pre_b1,late_d3_t3_pre_b2,late_d3_t3_post_b1,late_d3_t3_post_b2"
				+ ",late_d3_t4_pre_b1,late_d3_t4_pre_b2,late_d3_t4_post_b1,late_d3_t4_post_b2"
				                                                
				+ ",late_d4_t1_pre_b1,late_d4_t1_pre_b2,late_d4_t1_post_b1,late_d4_t1_post_b2"
				+ ",late_d4_t2_pre_b1,late_d4_t2_pre_b2,late_d4_t2_post_b1,late_d4_t2_post_b2"
				+ ",late_d4_t3_pre_b1,late_d4_t3_pre_b2,late_d4_t3_post_b1,late_d4_t3_post_b2"
				+ ",late_d4_t4_pre_b1,late_d4_t4_pre_b2,late_d4_t4_post_b1,late_d4_t4_post_b2"
				                                                   
				+ ",late_d5_t1_pre_b1,late_d5_t1_pre_b2,late_d5_t1_post_b1,late_d5_t1_post_b2"
				+ ",late_d5_t2_pre_b1,late_d5_t2_pre_b2,late_d5_t2_post_b1,late_d5_t2_post_b2"
				+ ",late_d5_t3_pre_b1,late_d5_t3_pre_b2,late_d5_t3_post_b1,late_d5_t3_post_b2"
				+ ",late_d5_t4_pre_b1,late_d5_t4_pre_b2,late_d5_t4_post_b1,late_d5_t4_post_b2"
				
				+ "\n"
				);
		for (int i = 0; i < participantsDataPVT.size(); i++) {

			PVT_sessions PVTdata = participantsDataPVT.get(i);
			outputSPSS.print(PVTdata.ID+","+PVTdata.condition);
			for (int j = 4; j < 44; j++) {
				outputSPSS.print(
						"," + PVTdata.getSessionsBlockLSNRapx(j, 0, pre_post.Pre)+ "," + PVTdata.getSessionsBlockLSNRapx(j, 1, pre_post.Pre) + 
						"," + PVTdata.getSessionsBlockLSNRapx(j, 0, pre_post.Post)+ "," + PVTdata.getSessionsBlockLSNRapx(j, 1, pre_post.Post));
				outputSPSS.flush();
			}
			outputSPSS.print("\n");
		}
		outputSPSS.close();
	}
	
	
	
	static void WriteToFile(File output) throws Exception {
		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		outputCSV.println("Best Case");
		for (int i = 0; i < participantsDataPVT.size(); i++) {
			PVT_sessions PVTdata = participantsDataPVT.get(i);
			if (PVTdata.condition.equals(Conditions.BestCase)){
				System.out.println("Writing to file (PVT data): " + PVTdata.ID);
				outputCSV.println(PVTdata.ID+":"+PVTdata.condition );
				outputCSV.flush();

				outputCSV.print("Session #");
				for (int j = 4; j < 44; j++) {					
					outputCSV.print("," + PVTdata.getSessionsNumber(j,pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre Lapses");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsLapses(j, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre average RT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsAveResponses(j, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre average Alert RT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsAveAlertResponses(j, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre LSNR_apx");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsLSNRapx(j, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post Lapses");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsLapses(j, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post average RT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsAveResponses(j, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post average Alert RT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsAveAlertResponses(j, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post LSNR_apx");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsLSNRapx(j, pre_post.Post));
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
		for (int i = 0; i < participantsDataPVT.size(); i++) {
			PVT_sessions PVTdata = participantsDataPVT.get(i);
			if (PVTdata.condition.equals(Conditions.WorstCase)){
				System.out.println("Writing to file (PVT data): " + PVTdata.ID);
				outputCSV.println(PVTdata.ID+":"+PVTdata.condition );
				outputCSV.flush();

				outputCSV.print("Session #");
				for (int j = 4; j < 44; j++) {					
					outputCSV.print("," + PVTdata.getSessionsNumber(j,pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre Lapses");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsLapses(j, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre average RT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsAveResponses(j, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre average Alert RT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsAveAlertResponses(j, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Pre LSNR_apx");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsLSNRapx(j, pre_post.Pre));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post Lapses");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsLapses(j, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post average RT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsAveResponses(j, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post average Alert RT");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsAveAlertResponses(j, pre_post.Post));
					outputCSV.flush();
				}
				outputCSV.print("\n");

				outputCSV.print("Post LSNR_apx");
				for (int j = 4; j < 44; j++) {
					outputCSV.print("," + getByID(PVTdata.ID).getSessionsLSNRapx(j, pre_post.Post));
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
	
	
	static void WriteToFileRawLapses(File output) throws Exception {
		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		outputCSV.println("Condition,ID,session #,TimeInSession,RT");
		
		// Best case
		for (int i = 0; i < participantsDataPVT.size(); i++) {
			PVT_sessions PVTdata = participantsDataPVT.get(i);
			if (PVTdata.condition.equals(Conditions.BestCase)){
				System.out.println("Writing to file (PVT data): " + PVTdata.ID + " " + PVTdata.condition);
				
				Values RT;
				Values timeOfReactionsFromStart;

				for (int j = 4; j < 44; j++) {					
					if (PVTdata.getSessionByNumber(j,pre_post.Pre)!= null){
						RT = PVTdata.getSessionByNumber(j,pre_post.Pre).RT;
						timeOfReactionsFromStart = PVTdata.getSessionByNumber(j,pre_post.Pre).timeOfReactionsFromStart; 
					}
					else{
						RT = new Values();
						timeOfReactionsFromStart = new Values(); 
					}
					
					for (int r = 0; r < RT.size(); r++) {
						if (RT.get(r) > 500 && RT.get(r) < 30000)   // checking if the RT is a lapse
							outputCSV.println("Day,"+ PVTdata.ID
									+ ", PRE " +PVTdata.getSessionsNumber(j,pre_post.Pre)
									+ "," + timeOfReactionsFromStart.get(r) + "," + RT.get(r));
					}
					outputCSV.flush();

					if (PVTdata.getSessionByNumber(j,pre_post.Post)!= null){
						RT = PVTdata.getSessionByNumber(j,pre_post.Post).RT;
						timeOfReactionsFromStart = PVTdata.getSessionByNumber(j,pre_post.Post).timeOfReactionsFromStart;
					}
					else{
						RT = new Values();
						timeOfReactionsFromStart = new Values(); 
					}
					
					for (int r = 0; r < RT.size(); r++) {
						if (RT.get(r) > 500 && RT.get(r) < 30000)   // checking if the RT is a lapse
							outputCSV.println("Day,"+ PVTdata.ID
									+ ", POST " +PVTdata.getSessionsNumber(j,pre_post.Pre)
									+ "," + timeOfReactionsFromStart.get(r) + "," + RT.get(r));
					}
					outputCSV.flush();
					
				}
			}	
		}

		outputCSV.println(",");
		
		outputCSV.println("Condition,ID,session #,TimeInSession,RT");
		//Worst Case
		for (int i = 0; i < participantsDataPVT.size(); i++) {
			PVT_sessions PVTdata = participantsDataPVT.get(i);
			if (PVTdata.condition.equals(Conditions.WorstCase)){
				System.out.println("Writing to file (PVT data): " + PVTdata.ID + " "  + PVTdata.condition);
				
				Values RT;
				Values timeOfReactionsFromStart;

				for (int j = 4; j < 44; j++) {					
					if (PVTdata.getSessionByNumber(j,pre_post.Pre)!= null){
						RT = PVTdata.getSessionByNumber(j,pre_post.Pre).RT;
						timeOfReactionsFromStart = PVTdata.getSessionByNumber(j,pre_post.Pre).timeOfReactionsFromStart; 
					}
					else{
						RT = new Values();
						timeOfReactionsFromStart = new Values(); 
					}
					
					for (int r = 0; r < RT.size(); r++) {
						if (RT.get(r) > 500 && RT.get(r) < 30000)   // checking if the RT is a lapse
							outputCSV.println("Night,"+ PVTdata.ID
									+ ", PRE " +PVTdata.getSessionsNumber(j,pre_post.Pre)
									+ "," + timeOfReactionsFromStart.get(r) + "," + RT.get(r));
					}
					outputCSV.flush();

					if (PVTdata.getSessionByNumber(j,pre_post.Post)!= null){
						RT = PVTdata.getSessionByNumber(j,pre_post.Post).RT;
						timeOfReactionsFromStart = PVTdata.getSessionByNumber(j,pre_post.Post).timeOfReactionsFromStart;
					}
					else{
						RT = new Values();
						timeOfReactionsFromStart = new Values(); 
					}
					
					for (int r = 0; r < RT.size(); r++) {
						if (RT.get(r) > 500 && RT.get(r) < 30000)   // checking if the RT is a lapse
							outputCSV.println("Night,"+ PVTdata.ID
									+ ", POST " +PVTdata.getSessionsNumber(j,pre_post.Pre)
									+ "," + timeOfReactionsFromStart.get(r) + "," + RT.get(r));
					}
					outputCSV.flush();
					
				}	
			}		
		}
	}
}

