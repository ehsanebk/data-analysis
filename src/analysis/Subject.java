package analysis;

public class Subject {
	// boolean verbose = true;
	//
	// String path;
	// String name, type = "unknown";
	// Data data[];
	// boolean isHighway, isOld;
	//
	// Subject(String path, Tokenizer master, String roadName) {
	// this.path = path;
	//
	// name = master.nextToken();
	// type = master.nextToken();
	//
	// // String task1 = master.nextToken();
	// // String task2 = master.nextToken();
	// // master.nextToken();
	// // int radio = (task1.equals("radio") ? 1 : (task2.equals("radio") ? 2 :
	// // 3));
	// // int coin = (task1.equals("coin") ? 1 : (task2.equals("coin") ? 2 :
	// // 3));
	// // int cd = (task1.equals("cd") ? 1 : (task2.equals("cd") ? 2 : 3));
	// // if (radio+coin+cd != 6) System.err.println
	// // ("wrong task names in ALL.txt!");
	//
	// String filename = path + name; // + "_brake.csv";
	// Tokenizer t = new Tokenizer(filename);
	//
	// isHighway = (name.indexOf("hw") >= 0);
	// isOld = (name.indexOf("old") >= 0);
	//
	// t.skipLines(isHighway ? 35 : 34);
	//
	// int line1 = t.line;
	// t.skipUntilBlankLine();
	// int line2 = t.line;
	//
	// int numSegments = line2 - line1;
	// data = new Data[numSegments];
	//
	// // if (!isHighway) numSegments = 1;
	// if (!isHighway)
	// numSegments -= 2;
	//
	// if (verbose)
	// System.out.print(name + " (" + type + ") ");
	// for (int i = 0; i < numSegments; i++) {
	// data[i] = new Data(t, isHighway, isOld);
	// if (!data[i].name.equals(roadName)) {
	// data[i] = null;
	// if (verbose)
	// System.out.print(".");
	// } else {
	// if (verbose)
	// System.out.print("*");
	// }
	// }
	// if (verbose)
	// System.out.println();
	//
	// for (int i = 0; i < numSegments; i++)
	// if (data[i] != null)
	// data[i].analyze();
	//
	// // String filename = path + name + "_base.csv";
	// // Tokenizer t = new Tokenizer (filename);
	// //
	// // t.skipLines (34);
	// // int line1 = t.line;
	// // t.skipUntilBlankLine();
	// // int line2 = t.line;
	// //
	// // int numSegments = line2 - line1;
	// // data = new Data[numSegments];
	// //
	// // System.out.print (name + " (" + type + ") ");
	// // for (int i=0 ; i<numSegments ; i++)
	// // {
	// // data[i] = new Data (t);
	// // System.out.print (".");
	// // }
	// // System.out.println ();
	// //
	// // for (int i=0 ; i<numSegments ; i++)
	// // data[i].analyze();
	//
	// // double segmentTimes[] = getSegmentTimes (name);
	// // double startRadio = segmentTimes[(radio-1)*2];
	// // double endRadio = segmentTimes[(radio-1)*2+1];
	// // double startCoin = segmentTimes[(coin-1)*2];
	// // double endCoin = segmentTimes[(coin-1)*2+1];
	// // double startCD = segmentTimes[(cd-1)*2];
	// // double endCD = segmentTimes[(cd-1)*2+1];
	// //
	// // Data taskData = new Data (name + "_task");
	// // data[1] = taskData.trimTask (startRadio, endRadio);
	// // data[3] = taskData.trimTask (startCoin, endCoin);
	// // data[5] = taskData.trimTask (startCD, endCD);
	// //
	// // Data baseData = new Data (name + "_base");
	// // data[0] = baseData.trimGeographic (data[1].getSample(0), endRadio -
	// // startRadio);
	// // data[2] = baseData.trimGeographic (data[3].getSample(0), endCoin -
	// // startCoin);
	// // data[4] = baseData.trimGeographic (data[5].getSample(0), endCD -
	// // startCD);
	// }
	//
	// double[] getSegmentTimes(String name) {
	// double times[] = new double[6];
	// Tokenizer t = new Tokenizer(path + name + "_log.txt");
	//
	// t.skipLines(3);
	//
	// int i = 0;
	// while (t.hasMoreTokens()) {
	// long tStamp = t.nextLong();
	// String hex = t.nextToken();
	// t.nextToken();
	//
	// if (hex.equals("0xDE10")) // event marker
	// {
	// if (i < 6)
	// times[i] = ((tStamp * .001) / 3579545);
	// i++;
	// }
	// }
	// if (i != 6) {
	// System.out.println("Subject " + name + " had " + i
	// + " events instead of 6!");
	// // System.out.print ("  times:");
	// // for (int k=0 ; k<i ; k++) System.out.print (" " +
	// // Utilities.df3.format(times[k]));
	// // System.out.println();
	// }
	// return times;
	// }
	//
	// int getSegmentCount() {
	// return data.length;
	// }
	//
	// // int getSegmentCount() { return segments.length; }
	//
	// Data getData(int index) {
	// return data[index];
	// }
	//
	// Data getData(String name) {
	// for (int i = 0; i < data.length; i++)
	// if (data[i] != null && data[i].name.equals(name))
	// return data[i];
	// return null;
	// }
	//
	// /*
	// * static String segments[] = {"SHwy03"}; //, "SHwy04", "CHwy04",
	// * "CRural02"};
	// *
	// * static String headerString () { String s = "Subj"; for (int i=0 ;
	// * i<segments.length ; i++) s += "\t"+segments[i]; return s; }
	// *
	// * static String headerString2 () { String s = "Subj"; for (int i=0 ;
	// * i<segments.length ; i++) s += "\t"+segments[i]+"\t"; return s; }
	// *
	// * Values getValues (Selector s) { Values vals = new Values(); for (int
	// i=0
	// * ; i<segments.length ; i++) vals.add (s.value (getData(segments[i])));
	// * return vals; }
	// *
	// * Values getDataRow () { Values vals = new Values(); for (int i=0 ;
	// * i<segments.length ; i++) for (int j=0 ; j<Selector.selectors.length ;
	// * j++) vals.add (Selector.selectors[j].value (getData(segments[i])));
	// * return vals; }
	// *
	// * static String getDataRowHeader1 () { String s = ""; for (int i=0 ;
	// * i<segments.length ; i++) for (int j=0 ; j<Selector.selectors.length ;
	// * j++) s += segments[i] + "\t"; return s; }
	// *
	// * static String getDataRowHeader2 () { String s = ""; for (int i=0 ;
	// * i<segments.length ; i++) for (int j=0 ; j<Selector.selectors.length ;
	// * j++) s += Selector.labels[j] + "\t"; return s; }
	// */
}
