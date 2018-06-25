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

		File output = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Result_PVT/Human_PVT.csv");
		try {
			WriteToFile(output);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File outputRawRT = new File("/Users/Ehsan/OneDrive - Drexel University/Driving Data(Van Dongen)/Result_PVT/Human_PVT_RawRT.csv");
		try {
			WriteToFileRawRT(outputRawRT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	static void WriteToFileRawRT(File output) throws Exception {
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
				Values RT;

				for (int j = 4; j < 44; j++) {					
					outputCSV.print("PRE " +PVTdata.getSessionsNumber(j,pre_post.Pre));
					outputCSV.flush();
					if (PVTdata.getSessionByNumber(j,pre_post.Pre)!= null)
						RT = PVTdata.getSessionByNumber(j,pre_post.Pre).RT;
					else
						RT = new Values();
					
					for (int r = 0; r < RT.size(); r++) {
						outputCSV.print("," + RT.get(r));
					}
					outputCSV.print("\n");
					outputCSV.flush();

					outputCSV.print("Post " +PVTdata.getSessionsNumber(j,pre_post.Post));
					outputCSV.flush();
					if (PVTdata.getSessionByNumber(j,pre_post.Post)!= null)
						RT = PVTdata.getSessionByNumber(j,pre_post.Post).RT;
					else
						RT = new Values();
					
					for (int r = 0; r < RT.size(); r++) {
						outputCSV.print("," + RT.get(r));
					}
					outputCSV.print("\n");
					outputCSV.flush();
				}


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
				Values RT;


				for (int j = 4; j < 44; j++) {					
					outputCSV.print("PRE " +PVTdata.getSessionsNumber(j,pre_post.Pre));
					outputCSV.flush();
					if (PVTdata.getSessionByNumber(j,pre_post.Pre)!=null)
						RT = PVTdata.getSessionByNumber(j,pre_post.Pre).RT;
					else
						RT = new Values();
					
					for (int r = 0; r < RT.size(); r++) {
						outputCSV.print("," + RT.get(r));
					}
					outputCSV.print("\n");
					outputCSV.flush();

					outputCSV.print("Post " +PVTdata.getSessionsNumber(j,pre_post.Post));
					outputCSV.flush();
					if (PVTdata.getSessionByNumber(j,pre_post.Post)!=null)
						RT = PVTdata.getSessionByNumber(j,pre_post.Post).RT;
					else
						RT = new Values();
					
					for (int r = 0; r < RT.size(); r++) {
						outputCSV.print("," + RT.get(r));
					}
					outputCSV.print("\n");
					outputCSV.flush();
				}


				outputCSV.print("\n");
				outputCSV.flush();			
			}		
		}
	}
}

