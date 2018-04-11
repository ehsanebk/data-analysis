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

		File directory = new File("/Users/ehsanebk/OneDrive - drexel.edu/Driving Data(Van Dongen)/PVT Raw data");
		//File directory = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/PVT Raw data");
		data.process(directory.toPath());

		File output = new File("./Result(VanDongen)/Results_TimePoints_PVT.csv");
		//File outputProportion = new File("./Result(VanDongen)/Results_TimePoints_PVT_L_Proportion.csv");
		//File output = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Results_TimePoints_PVT.csv");
		WriteToFileNumberOfLapses(output);
		//WriteToFileProportionOfLapses(outputProportion);


//		// for testing
//		for (int i = 0; i < data.participantsDataPVT.size(); i++) {
//			//for (int i = 0; i < 1; i++) {
//			PVT_sessions participant = participantsDataPVT.get(i);
//			System.out.println();
//			System.out.print("ID: " +participant.ID + " ");
//
//			if (participant.condition == Conditions.WorstCase){
//				for (int j = 0; j < participant.sessions.size(); j++) {
//					PVT_session session = participant.sessions.get(j);
//					System.out.print("**"+session.sessionNumber + " " + session.timePoint);
//					
//					
////					if (session.pre_post == pre_post.Pre && (session.timePoint == 1 || session.timePoint == 8)){
////						System.out.print(session.ID + "," + session.pre_post + "," + session.timePoint + " ");
////						System.out.print(session.getBlockLapses(1));
//////						Values B1 = session.getRTblock(1);
//////						for (int k = 0; k < B1.size(); k++) {
//////							System.out.print(","+B1.get(k));
//////						}
////						System.out.print("****");
////						System.out.print(session.getBlockLapses(2));
//////						Values B2 = session.getRTblock(2);
//////						for (int k = 0; k < B2.size(); k++) {
//////							System.out.print(","+B2.get(k));
//////						}
////						System.out.print("\n");
////					}
//				}
//			}
//		}		
//		// End of testing
		
		
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


	PVT_sessions getByID (String ID) throws Exception{
		for (PVT_sessions pvt_sessions : participantsDataPVT) {
			if (pvt_sessions.ID.equals(ID))
				return pvt_sessions;
		}
		throw new Exception("PVT for ID " + ID + " Not found in the data!");
	}

	static void WriteToFileNumberOfLapses(File output) {
		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// the time points are in the format of 
		// 1 2 3 4 break 5 6 7 8 
		Values [] Lapses_AVE_BestCaseTimePoints_PRE =  new Values[10];
		Values [] Lapses_AVE_WorstCaseTimePoints_PRE =  new Values[10];
		Values [] Lapses_AVE_BestCaseTimePoints_POST =  new Values[10];
		Values [] Lapses_AVE_WorstCaseTimePoints_POST =  new Values[10];
		
		Values [] LSNRapx_AVE_BestCaseTimePoints_PRE =  new Values[10];
		Values [] LSNRapx_AVE_WorstCaseTimePoints_PRE =  new Values[10];
		Values [] LSNRapx_AVE_BestCaseTimePoints_POST =  new Values[10];
		Values [] LSNRapx_AVE_WorstCaseTimePoints_POST =  new Values[10];

		for (int i = 0; i < 10; i++) {					
			Lapses_AVE_BestCaseTimePoints_PRE[i] = new Values();
			Lapses_AVE_WorstCaseTimePoints_PRE[i] = new Values();
			Lapses_AVE_BestCaseTimePoints_POST[i] = new Values();
			Lapses_AVE_WorstCaseTimePoints_POST[i] = new Values();
			
			LSNRapx_AVE_BestCaseTimePoints_PRE[i] = new Values();
			LSNRapx_AVE_WorstCaseTimePoints_PRE[i] = new Values();
			LSNRapx_AVE_BestCaseTimePoints_POST[i] = new Values();
			LSNRapx_AVE_WorstCaseTimePoints_POST[i] = new Values();
		}

		
		for (int i = 0; i < participantsDataPVT.size(); i++) {
			PVT_sessions pVT_sessions = participantsDataPVT.get(i);
			int [] dp = {1,2,3,4,5,6,7,8}; 
			for (int k: dp) {
				switch (pVT_sessions.condition){
				case BestCase:
					Lapses_AVE_BestCaseTimePoints_PRE[k].add(pVT_sessions.getNumberOfLapses_AveOnTimePoints(k , pre_post.Pre));
					Lapses_AVE_BestCaseTimePoints_POST[k].add(pVT_sessions.getNumberOfLapses_AveOnTimePoints(k , pre_post.Post));
					LSNRapx_AVE_BestCaseTimePoints_PRE[k].add(pVT_sessions.getLSNRapx_AveOnTimePoints(k , pre_post.Pre));
					LSNRapx_AVE_BestCaseTimePoints_POST[k].add(pVT_sessions.getLSNRapx_AveOnTimePoints(k , pre_post.Post));
					break;
				case WorstCase:
					Lapses_AVE_WorstCaseTimePoints_PRE[k].add(pVT_sessions.getNumberOfLapses_AveOnTimePoints(k, pre_post.Pre));
					Lapses_AVE_WorstCaseTimePoints_POST[k].add(pVT_sessions.getNumberOfLapses_AveOnTimePoints(k, pre_post.Post));
					LSNRapx_AVE_WorstCaseTimePoints_PRE[k].add(pVT_sessions.getLSNRapx_AveOnTimePoints(k, pre_post.Pre));
					LSNRapx_AVE_WorstCaseTimePoints_POST[k].add(pVT_sessions.getLSNRapx_AveOnTimePoints(k, pre_post.Post));
					break;
				}
			}
		}

		////////////////        writing to file       ////////////////////////
		outputCSV.println("PRE Lapses");
		outputCSV.println(",,,1,2,3,4,,5,6,7,8");
		// PVT AVE
		outputCSV.print("Best_Lapses_AVE_PRE,");
		for (int i = 0; i < Lapses_AVE_BestCaseTimePoints_PRE.length; i++) {
			if (i==5)
				outputCSV.print(",");
			if ( Lapses_AVE_BestCaseTimePoints_PRE[i].size() > 0){
				double s=Lapses_AVE_BestCaseTimePoints_PRE[i].average(); 
				outputCSV.print(","+s);
			} else 
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_Lapses_AVE_PRE,");
		for (int i = 0; i < Lapses_AVE_WorstCaseTimePoints_PRE.length; i++) {
			if (i==5)
				outputCSV.print(",");
			if (Lapses_AVE_WorstCaseTimePoints_PRE[i].size() > 0){
				double s=Lapses_AVE_WorstCaseTimePoints_PRE[i].average(); 
				outputCSV.print(","+s);
			}else 
				outputCSV.print(",");
		}	
		outputCSV.print("\n\n\n");
		outputCSV.flush();
		
		outputCSV.println("PRE LSNRapx");
		outputCSV.println(",,,1,2,3,4,,5,6,7,8");
		// PVT AVE
		outputCSV.print("Best_LSNRapx_AVE_PRE,");
		for (int i = 0; i < LSNRapx_AVE_BestCaseTimePoints_PRE.length; i++) {
			if (i==5)
				outputCSV.print(",");
			if ( LSNRapx_AVE_BestCaseTimePoints_PRE[i].size() > 0){
				double s=LSNRapx_AVE_BestCaseTimePoints_PRE[i].average(); 
				outputCSV.print(","+s);
			} else 
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_LSNRapx_AVE_PRE,");
		for (int i = 0; i < LSNRapx_AVE_WorstCaseTimePoints_PRE.length; i++) {
			if (i==5)
				outputCSV.print(",");
			if (LSNRapx_AVE_WorstCaseTimePoints_PRE[i].size() > 0){
				double s=LSNRapx_AVE_WorstCaseTimePoints_PRE[i].average(); 
				outputCSV.print(","+s);
			}else 
				outputCSV.print(",");
		}	
		outputCSV.print("\n\n\n");
		outputCSV.flush();
		
		
		
		outputCSV.println("POST Lapses");
		outputCSV.println(",,,1,2,3,4,,5,6,7,8");
		// PVT AVE
		outputCSV.print("Best_Lapses_AVE_POST,");
		for (int i = 0; i < Lapses_AVE_BestCaseTimePoints_POST.length; i++) {
			if (i==5)
				outputCSV.print(",");
			if ( Lapses_AVE_BestCaseTimePoints_POST[i].size() > 0){
				double s=Lapses_AVE_BestCaseTimePoints_POST[i].average(); 
				outputCSV.print(","+s);
			} else 
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_Lapses_AVE_POST,");
		for (int i = 0; i < Lapses_AVE_WorstCaseTimePoints_POST.length; i++) {
			if (i==5)
				outputCSV.print(",");
			if (Lapses_AVE_WorstCaseTimePoints_POST[i].size() > 0){
				double s=Lapses_AVE_WorstCaseTimePoints_POST[i].average(); 
				outputCSV.print(","+s);
			}else 
				outputCSV.print(",");
		}	
		outputCSV.print("\n\n\n");
		outputCSV.flush();
		
		outputCSV.println("POST LSNRapx");
		outputCSV.println(",,,1,2,3,4,,5,6,7,8");
		// PVT AVE
		outputCSV.print("Best_LSNRapx_AVE_POST,");
		for (int i = 0; i < LSNRapx_AVE_BestCaseTimePoints_POST.length; i++) {
			if (i==5)
				outputCSV.print(",");
			if ( LSNRapx_AVE_BestCaseTimePoints_POST[i].size() > 0){
				double s=LSNRapx_AVE_BestCaseTimePoints_POST[i].average(); 
				outputCSV.print(","+s);
			} else 
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_LSNRapx_AVE_POST,");
		for (int i = 0; i < LSNRapx_AVE_WorstCaseTimePoints_POST.length; i++) {
			if (i==5)
				outputCSV.print(",");
			if (LSNRapx_AVE_WorstCaseTimePoints_POST[i].size() > 0){
				double s=LSNRapx_AVE_WorstCaseTimePoints_POST[i].average(); 
				outputCSV.print(","+s);
			}else 
				outputCSV.print(",");
		}	
		outputCSV.print("\n\n\n");
		outputCSV.flush();
		
		////////////////     writing the blocks to file       ////////////////////////
		// Every session has two blocks in this experiment (PVT is 10 min, two 5-min Blocks)
		Values [][] PVT_BlockAVE_BestCaseTimePoints_PRE =  new Values[10][2];
		Values [][] PVT_BlockAVE_WorstCaseTimePoints_PRE =  new Values[10][2];
		Values [][] PVT_BlockAVE_BestCaseTimePoints_POST =  new Values[10][2];
		Values [][] PVT_BlockAVE_WorstCaseTimePoints_POST =  new Values[10][2];
		
		for (int i = 0; i < 10; i++) 
			for (int j = 0 ; j < 2; j++) {
				PVT_BlockAVE_BestCaseTimePoints_PRE[i][j] = new Values();
				PVT_BlockAVE_WorstCaseTimePoints_PRE[i][j] = new Values();
				PVT_BlockAVE_BestCaseTimePoints_POST[i][j] = new Values();
				PVT_BlockAVE_WorstCaseTimePoints_POST[i][j] = new Values();
			}

		for (int i = 0; i < participantsDataPVT.size(); i++) {
			PVT_sessions pVT_sessions = participantsDataPVT.get(i);
			int [] dp = {1,2,3,4,5,6,7,8}; 
			for (int k: dp) {
				for (int j = 0; j < 2; j++) {
					switch (pVT_sessions.condition){
					case BestCase:
						PVT_BlockAVE_BestCaseTimePoints_PRE[k][j].add(
								pVT_sessions.getNumberOfLapsesAtEachBlock_AveOnTimePoints(k , j, pre_post.Pre));
						PVT_BlockAVE_BestCaseTimePoints_POST[k][j].add(
								pVT_sessions.getNumberOfLapsesAtEachBlock_AveOnTimePoints(k , j, pre_post.Post));
						break;
					case WorstCase:
						PVT_BlockAVE_WorstCaseTimePoints_PRE[k][j].add(
								pVT_sessions.getNumberOfLapsesAtEachBlock_AveOnTimePoints(k , j, pre_post.Pre));
						PVT_BlockAVE_WorstCaseTimePoints_POST[k][j].add(
								pVT_sessions.getNumberOfLapsesAtEachBlock_AveOnTimePoints(k , j, pre_post.Post));
						break;
					}
				}
			}
		}

		outputCSV.println("PRE BLOCK DATA");
		outputCSV.println(",,,1,,,2,,,3,,,4,,,,5,,,6,,,7,,,8,,");
		// PVT AVE
		outputCSV.print("Best_PVT_BlockAVE_PRE,");
		for (int i = 1; i < 10; i++) {
			if (i==5)
				outputCSV.print(",");
			outputCSV.print(",");
			for (int j = 0; j < 2; j++) {
				if ( PVT_BlockAVE_BestCaseTimePoints_PRE[i][j].size() > 0){
					double s=PVT_BlockAVE_BestCaseTimePoints_PRE[i][j].average(); 
					outputCSV.print(","+s);
				} else 
					outputCSV.print(",");
			}
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_PVT_BlockAVE_PRE,");
		for (int i = 1; i < 10; i++) {
			if (i==5)
				outputCSV.print(",");
			outputCSV.print(",");
			for (int j = 0; j < 2; j++) {
				if ( PVT_BlockAVE_WorstCaseTimePoints_PRE[i][j].size() > 0){
					double s=PVT_BlockAVE_WorstCaseTimePoints_PRE[i][j].average(); 
					outputCSV.print(","+s);
				} else 
					outputCSV.print(",");
			}
		}
		outputCSV.print("\n\n\n");
		outputCSV.flush();
		
		outputCSV.println("POST BLOCK DATA");
		outputCSV.println(",,,1,,,2,,,3,,,4,,,,5,,,6,,,7,,,8,,");
		// PVT AVE
		outputCSV.print("Best_PVT_BlockAVE_POST,");
		for (int i = 1; i < 10; i++) {
			if (i==5)
				outputCSV.print(",");
			outputCSV.print(",");
			for (int j = 0; j < 2; j++) {
				if ( PVT_BlockAVE_BestCaseTimePoints_POST[i][j].size() > 0){
					double s=PVT_BlockAVE_BestCaseTimePoints_POST[i][j].average(); 
					outputCSV.print(","+s);
				} else 
					outputCSV.print(",");
			}
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_PVT_BlockAVE_POST,");
		for (int i = 1; i < 10; i++) {
			if (i==5)
				outputCSV.print(",");
			outputCSV.print(",");
			for (int j = 0; j < 2; j++) {
				if ( PVT_BlockAVE_WorstCaseTimePoints_POST[i][j].size() > 0){
					double s=PVT_BlockAVE_WorstCaseTimePoints_POST[i][j].average(); 
					outputCSV.print(","+s);
				} else 
					outputCSV.print(",");
			}
		}
		outputCSV.print("\n\n\n");
		outputCSV.flush();
		
		
		///
		outputCSV.print("Best PVT Blocks Raw");
		for (int i = 0; i < participantsDataPVT.size(); i++) {
			PVT_sessions participant = participantsDataPVT.get(i);
			if (participant.condition == Conditions.BestCase){
				outputCSV.print("\n");
				outputCSV.print(participant.ID + ",");
				// Going through the sessions ( 4 to 43)
				for (int j = 4; j < 44; j++) {
					outputCSV.print(",,*S:"+j +" #L:" + participant.getSessionsLapses(j, pre_post.Pre) + "*");
					outputCSV.print(","+participant.getSessionsBlockLapses(j, 0, pre_post.Pre) +"," +participant.getSessionsBlockLapses(j, 1, pre_post.Pre));
					outputCSV.print(",,*S:"+j +" #L:"+ participant.getSessionsLapses(j, pre_post.Post) + "*");
					outputCSV.print(","+participant.getSessionsBlockLapses(j, 0, pre_post.Post) +"," +participant.getSessionsBlockLapses(j, 1, pre_post.Post));
					
				}
			}
		}

		outputCSV.print("\n\n\n");
		outputCSV.print("Worst PVT Blocks Raw");
		for (int i = 0; i < participantsDataPVT.size(); i++) {
			PVT_sessions participant = participantsDataPVT.get(i);
			if (participant.condition == Conditions.WorstCase){
				outputCSV.print("\n");
				outputCSV.print(participant.ID + ",");
				// Going through the sessions ( 4 to 43)
				for (int j = 4; j < 44; j++) {
					outputCSV.print(",,*S:"+j +" #L:"+ participant.getSessionsLapses(j, pre_post.Pre) + "*");
					outputCSV.print(","+participant.getSessionsBlockLapses(j, 0, pre_post.Pre) +"," +participant.getSessionsBlockLapses(j, 1, pre_post.Pre));
					outputCSV.print(",,*S:"+j +" #L:"+ participant.getSessionsLapses(j, pre_post.Post) + "*");
					outputCSV.print(","+participant.getSessionsBlockLapses(j, 0, pre_post.Post) +"," +participant.getSessionsBlockLapses(j, 1, pre_post.Post));
				}
			}
		}
	
		
		
		outputCSV.close();
	}

	static void WriteToFileProportionOfLapses(File output) {
		PrintWriter outputCSV = null;
		try {
			outputCSV = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// the time points are in the format of 
		// 1 2 3 4 break 5 6 7 8 
		Values [] PVT_PROPORTION_AVE_BestCaseTimePoints_PRE =  new Values[10];
		Values [] PVT_PROPORTION_AVE_WorstCaseTimePoints_PRE =  new Values[10];
		Values [] PVT_PROPORTION_AVE_BestCaseTimePoints_POST =  new Values[10];
		Values [] PVT_PROPORTION_AVE_WorstCaseTimePoints_POST =  new Values[10];

		for (int i = 0; i < 10; i++) {					
			PVT_PROPORTION_AVE_BestCaseTimePoints_PRE[i] = new Values();
			PVT_PROPORTION_AVE_WorstCaseTimePoints_PRE[i] = new Values();
			PVT_PROPORTION_AVE_BestCaseTimePoints_POST[i] = new Values();
			PVT_PROPORTION_AVE_WorstCaseTimePoints_POST[i] = new Values();
		}

		
		for (int i = 0; i < participantsDataPVT.size(); i++) {
			PVT_sessions pVT_sessions = participantsDataPVT.get(i);
			int [] dp = {1,2,3,4,5,6,7,8}; 
			for (int k: dp) {
				switch (pVT_sessions.condition){
				case BestCase:
					PVT_PROPORTION_AVE_BestCaseTimePoints_PRE[k].add(pVT_sessions.getProportionOfLapses_AveOnTimePoints(k , pre_post.Pre));
					PVT_PROPORTION_AVE_BestCaseTimePoints_POST[k].add(pVT_sessions.getProportionOfLapses_AveOnTimePoints(k , pre_post.Post));
					break;
				case WorstCase:
					PVT_PROPORTION_AVE_WorstCaseTimePoints_PRE[k].add(pVT_sessions.getProportionOfLapses_AveOnTimePoints(k, pre_post.Pre));
					PVT_PROPORTION_AVE_WorstCaseTimePoints_POST[k].add(pVT_sessions.getProportionOfLapses_AveOnTimePoints(k, pre_post.Post));
					break;
				}
			}
		}

		////////////////        writing to file       ////////////////////////
		outputCSV.println("PRE DATA");
		outputCSV.println(",,,1,2,3,4,,5,6,7,8");
		// PVT AVE
		outputCSV.print("Best_PVT_AVE_PRE,");
		for (int i = 0; i < PVT_PROPORTION_AVE_BestCaseTimePoints_PRE.length; i++) {
			if (i==5)
				outputCSV.print(",");
			if ( PVT_PROPORTION_AVE_BestCaseTimePoints_PRE[i].size() > 0){
				double s=PVT_PROPORTION_AVE_BestCaseTimePoints_PRE[i].average(); 
				outputCSV.print(","+s);
			} else 
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_PVT_AVE_PRE,");
		for (int i = 0; i < PVT_PROPORTION_AVE_WorstCaseTimePoints_PRE.length; i++) {
			if (i==5)
				outputCSV.print(",");
			if (PVT_PROPORTION_AVE_WorstCaseTimePoints_PRE[i].size() > 0){
				double s=PVT_PROPORTION_AVE_WorstCaseTimePoints_PRE[i].average(); 
				outputCSV.print(","+s);
			}else 
				outputCSV.print(",");
		}	
		outputCSV.print("\n\n\n");
		outputCSV.flush();
		
		outputCSV.println("POST DATA");
		outputCSV.println(",,,1,2,3,4,,5,6,7,8");
		// PVT AVE
		outputCSV.print("Best_PVT_AVE_POST,");
		for (int i = 0; i < PVT_PROPORTION_AVE_BestCaseTimePoints_POST.length; i++) {
			if (i==5)
				outputCSV.print(",");
			if ( PVT_PROPORTION_AVE_BestCaseTimePoints_POST[i].size() > 0){
				double s=PVT_PROPORTION_AVE_BestCaseTimePoints_POST[i].average(); 
				outputCSV.print(","+s);
			} else 
				outputCSV.print(",");
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_PVT_AVE_POST,");
		for (int i = 0; i < PVT_PROPORTION_AVE_WorstCaseTimePoints_POST.length; i++) {
			if (i==5)
				outputCSV.print(",");
			if (PVT_PROPORTION_AVE_WorstCaseTimePoints_POST[i].size() > 0){
				double s=PVT_PROPORTION_AVE_WorstCaseTimePoints_POST[i].average(); 
				outputCSV.print(","+s);
			}else 
				outputCSV.print(",");
		}	
		outputCSV.print("\n\n\n");
		outputCSV.flush();
		
		////////////////     writing the blocks to file       ////////////////////////
		// Every session has two blocks in this experiment (PVT is 10 min, two 5-min Blocks)
		Values [][] PVT_PROPORTION_BlockAVE_BestCaseTimePoints_PRE =  new Values[10][2];
		Values [][] PVT_PROPORTION_BlockAVE_WorstCaseTimePoints_PRE =  new Values[10][2];
		Values [][] PVT_PROPORTION_BlockAVE_BestCaseTimePoints_POST =  new Values[10][2];
		Values [][] PVT_PROPORTION_BlockAVE_WorstCaseTimePoints_POST =  new Values[10][2];
		
		for (int i = 0; i < 10; i++) 
			for (int j = 0 ; j < 2; j++) {
				PVT_PROPORTION_BlockAVE_BestCaseTimePoints_PRE[i][j] = new Values();
				PVT_PROPORTION_BlockAVE_WorstCaseTimePoints_PRE[i][j] = new Values();
				PVT_PROPORTION_BlockAVE_BestCaseTimePoints_POST[i][j] = new Values();
				PVT_PROPORTION_BlockAVE_WorstCaseTimePoints_POST[i][j] = new Values();
			}

		for (int i = 0; i < participantsDataPVT.size(); i++) {
			PVT_sessions pVT_sessions = participantsDataPVT.get(i);
			int [] dp = {1,2,3,4,5,6,7,8}; 
			for (int k: dp) {
				for (int j = 0; j < 2; j++) {
					switch (pVT_sessions.condition){
					case BestCase:
						PVT_PROPORTION_BlockAVE_BestCaseTimePoints_PRE[k][j].add(
								pVT_sessions.getProportionOfLapsesAtEachBlock_AveOnTimePoints(k , j, pre_post.Pre));
						PVT_PROPORTION_BlockAVE_BestCaseTimePoints_POST[k][j].add(
								pVT_sessions.getProportionOfLapsesAtEachBlock_AveOnTimePoints(k , j, pre_post.Post));
						break;
					case WorstCase:
						PVT_PROPORTION_BlockAVE_WorstCaseTimePoints_PRE[k][j].add(
								pVT_sessions.getProportionOfLapsesAtEachBlock_AveOnTimePoints(k , j, pre_post.Pre));
						PVT_PROPORTION_BlockAVE_WorstCaseTimePoints_POST[k][j].add(
								pVT_sessions.getProportionOfLapsesAtEachBlock_AveOnTimePoints(k , j, pre_post.Post));
						break;
					}
				}
			}
		}

		outputCSV.println("PRE BLOCK DATA");
		outputCSV.println(",,,1,,,2,,,3,,,4,,,,5,,,6,,,7,,,8,,");
		// PVT AVE
		outputCSV.print("Best_PVT_BlockAVE_PRE,");
		for (int i = 1; i < 10; i++) {
			if (i==5)
				outputCSV.print(",");
			outputCSV.print(",");
			for (int j = 0; j < 2; j++) {
				if ( PVT_PROPORTION_BlockAVE_BestCaseTimePoints_PRE[i][j].size() > 0){
					double s=PVT_PROPORTION_BlockAVE_BestCaseTimePoints_PRE[i][j].average(); 
					outputCSV.print(","+s);
				} else 
					outputCSV.print(",");
			}
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_PVT_BlockAVE_PRE,");
		for (int i = 1; i < 10; i++) {
			if (i==5)
				outputCSV.print(",");
			outputCSV.print(",");
			for (int j = 0; j < 2; j++) {
				if ( PVT_PROPORTION_BlockAVE_WorstCaseTimePoints_PRE[i][j].size() > 0){
					double s=PVT_PROPORTION_BlockAVE_WorstCaseTimePoints_PRE[i][j].average(); 
					outputCSV.print(","+s);
				} else 
					outputCSV.print(",");
			}
		}
		outputCSV.print("\n\n\n");
		outputCSV.flush();
		
		outputCSV.println("POST BLOCK DATA");
		outputCSV.println(",,,1,,,2,,,3,,,4,,,,5,,,6,,,7,,,8,");
		// PVT AVE
		outputCSV.print("Best_PVT_BlockAVE_POST,");
		for (int i = 1; i < 10; i++) {
			if (i==5)
				outputCSV.print(",");
			outputCSV.print(",");
			for (int j = 0; j < 2; j++) {
				if ( PVT_PROPORTION_BlockAVE_BestCaseTimePoints_POST[i][j].size() > 0){
					double s=PVT_PROPORTION_BlockAVE_BestCaseTimePoints_POST[i][j].average(); 
					outputCSV.print(","+s);
				} else 
					outputCSV.print(",");
			}
		}
		outputCSV.print("\n");
		outputCSV.flush();
		outputCSV.print("Worst_PVT_BlockAVE_POST,");
		for (int i = 1; i < 10; i++) {
			if (i==5)
				outputCSV.print(",");
			outputCSV.print(",");
			for (int j = 0; j < 2; j++) {
				if ( PVT_PROPORTION_BlockAVE_WorstCaseTimePoints_POST[i][j].size() > 0){
					double s=PVT_PROPORTION_BlockAVE_WorstCaseTimePoints_POST[i][j].average(); 
					outputCSV.print(","+s);
				} else 
					outputCSV.print(",");
			}
		}
		outputCSV.print("\n\n\n");
		outputCSV.flush();
		
		
		///
		outputCSV.print("Best PVT Blocks Raw");
		for (int i = 0; i < participantsDataPVT.size(); i++) {
			PVT_sessions participant = participantsDataPVT.get(i);
			if (participant.condition == Conditions.BestCase){
				outputCSV.print("\n");
				outputCSV.print(participant.ID + ",");
				// Going through the sessions ( 4 to 43)
				for (int j = 4; j < 44; j++) {
					outputCSV.print(",,*S:"+j +" #L:" + participant.getSessionsLapses(j, pre_post.Pre) + "*");
					outputCSV.print(","+participant.getSessionsBlockLapses(j, 0, pre_post.Pre) +"," +participant.getSessionsBlockLapses(j, 1, pre_post.Pre));
					outputCSV.print(",,*S:"+j +" #L:"+ participant.getSessionsLapses(j, pre_post.Post) + "*");
					outputCSV.print(","+participant.getSessionsBlockLapses(j, 0, pre_post.Post) +"," +participant.getSessionsBlockLapses(j, 1, pre_post.Post));
					
				}
			}
		}

		outputCSV.print("\n\n\n");
		outputCSV.print("Worst PVT Blocks Raw");
		for (int i = 0; i < participantsDataPVT.size(); i++) {
			PVT_sessions participant = participantsDataPVT.get(i);
			if (participant.condition == Conditions.WorstCase){
				outputCSV.print("\n");
				outputCSV.print(participant.ID + ",");
				// Going through the sessions ( 4 to 43)
				for (int j = 4; j < 44; j++) {
					outputCSV.print(",,*S:"+j +" #L:"+ participant.getSessionsLapses(j, pre_post.Pre) + "*");
					outputCSV.print(","+participant.getSessionsBlockLapses(j, 0, pre_post.Pre) +"," +participant.getSessionsBlockLapses(j, 1, pre_post.Pre));
					outputCSV.print(",,*S:"+j +" #L:"+ participant.getSessionsLapses(j, pre_post.Post) + "*");
					outputCSV.print(","+participant.getSessionsBlockLapses(j, 0, pre_post.Post) +"," +participant.getSessionsBlockLapses(j, 1, pre_post.Post));
				}
			}
		}
	
		
		
		outputCSV.close();
	}
}
