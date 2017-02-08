package singapore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;


public class SingaporeDriving {

	public static void main(String[] args){
		
		
		// getting all the pvt data 
		Vector<SampleSingaporePVT> samples = new Vector<SampleSingaporePVT>();
		File PVTfile = new File("/Users/ehsanebk/OneDrive - drexel.edu/"
				+ "Driving data - standard deviation lateral position (Singapore)/"
				+ "MFPD_PVT_all.txt");
		samples = (new SingaporePVT(PVTfile)).samples;
		
		
		
		for (int i = 0; i < samples.size(); i++) {
			String protocol = samples.elementAt(i).getProtocol();
			System.out.println(protocol);
			
		}
		
				
		File ProtocolA = new File("/Users/ehsanebk/OneDrive - drexel.edu"
				+ "/Driving data - standard deviation lateral position (Singapore)"
				+ "/Driving Data/Protocol A driving data");

		File ProtocolB = new File("/Users/ehsanebk/OneDrive - drexel.edu"
				+ "/Driving data - standard deviation lateral position (Singapore)"
				+ "/Driving Data/Protocol B driving data");

		String excelFilePath = "/Users/ehsanebk/OneDrive - drexel.edu"
				+ "/Driving data - standard deviation lateral position (Singapore)"
				+ "/Driving Data/Protocol B driving data/508ProcessedData.xlsx";
		try {
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	


//	
	}

}
