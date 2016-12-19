package analysis;

import java.io.File;
import java.util.Vector;

public class Main {

	private static Vector<Data> datas;
	public static void main(String[] args) {
		
		datas = new Vector<Data>();
		File directory = new File("C:\\Users\\eb452\\OneDrive - drexel.edu\\Driving Data(Van Dongen)\\Data");
		File test = new File("C:\\Users\\eb452\\OneDrive - drexel.edu\\Driving Data(Van Dongen)\\Data\\3040B04.rpt");
		//File test = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Data/3040B04.rpt");

		
//		for (File file : directory.listFiles())
//			if (file.getName().endsWith(".rpt")) {
//				Data data = new Data(file);
//				System.out.println("yes");
//			}
		Data data = new Data(test);
		
	}
}
