package analysis;

import java.io.File;
import java.util.Vector;

public class Main {

	private static Vector<Data> datas;
	public static void main(String[] args) {
		
		Vector<Vector<Sample>> worstCase = new Vector<Vector<Sample>>(8);
		Vector<Vector<Sample>> bestCase = new Vector<Vector<Sample>>(8);
		
		for (int i = 0; i < 8; i++) {
			worstCase.add(new Vector<Sample>());
			bestCase.add(new Vector<Sample>());
		}
		
		
		datas = new Vector<Data>();
		File directory = new File("C:\\Users\\eb452\\OneDrive - drexel.edu\\Driving Data(Van Dongen)\\Data");
		//File test = new File("C:\\Users\\eb452\\OneDrive - drexel.edu\\Driving Data(Van Dongen)\\Data\\3040B04.rpt");
		//File test = new File("/Users/Ehsan/OneDrive - drexel.edu/Driving Data(Van Dongen)/Data/3040B04.rpt");

		
		for (File file : directory.listFiles())
			if (file.getName().endsWith(".rpt")) {
				Data data = new Data(file);
				datas.add(data);
				//System.out.println(file.getName());
			}
		//Data data = new Data(test);
		//System.out.println(datas.get(0).sample.LANEDEV_MAX);
		
		for (int i = 0; i < datas.size(); i++) {
			Data d = datas.elementAt(i);
			int session = d.session;

			if ( session == 4 || session == 8 || session == 12 || session == 16 || session == 20 )
				if (d.condition == "WorstCase")	
					worstCase.elementAt(0).add(d.sample);
				else
					bestCase.elementAt(0).add(d.sample);
			if ( session == 5 || session == 9 || session == 13 || session == 17 || session == 21 )
				if (d.condition == "WorstCase")
					worstCase.elementAt(1).add(d.sample);
				else
					bestCase.elementAt(1).add(d.sample);
			if ( session == 6 || session == 10 || session == 14 || session == 18 || session == 22 )
				if (d.condition == "WorstCase")
					worstCase.elementAt(2).add(d.sample);
				else
					bestCase.elementAt(2).add(d.sample);
			if ( session == 7 || session == 11 || session == 15 || session == 19 || session == 23 )
				if (d.condition == "WorstCase")
					worstCase.elementAt(3).add(d.sample);
				else
					bestCase.elementAt(3).add(d.sample);



			if ( session == 24 || session == 28 || session == 32 || session == 36 || session == 40 )
				if (d.condition == "WorstCase")
					worstCase.elementAt(4).add(d.sample);
				else
					bestCase.elementAt(4).add(d.sample);
			if ( session == 25 || session == 29 || session == 33 || session == 37 || session == 41 )
				if (d.condition == "WorstCase")
					worstCase.elementAt(5).add(d.sample);
				else
					bestCase.elementAt(5).add(d.sample);
			if ( session == 26 || session == 30 || session == 34 || session == 38 || session == 42 )
				if (d.condition == "WorstCase")
					worstCase.elementAt(6).add(d.sample);
				else
					bestCase.elementAt(6).add(d.sample);
			if ( session == 27 || session == 31 || session == 35 || session == 39 || session == 43 )
				if (d.condition == "WorstCase")
					worstCase.elementAt(7).add(d.sample);
				else
					bestCase.elementAt(7).add(d.sample);
		}
		
		System.out.println(bestCase.elementAt(2).size());
		System.out.println(worstCase.elementAt(2).size());
	}
}
