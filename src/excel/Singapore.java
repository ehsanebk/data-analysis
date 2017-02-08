package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import analysis.Data;


public class Singapore {

	public static void main(String[] args) throws IOException {
		
		
		File ProtocolA = new File("/Users/ehsanebk/OneDrive - drexel.edu"
				+ "/Driving data - standard deviation lateral position (Singapore)"
				+ "/Driving Data/Protocol A driving data");

		File ProtocolB = new File("/Users/ehsanebk/OneDrive - drexel.edu"
				+ "/Driving data - standard deviation lateral position (Singapore)"
				+ "/Driving Data/Protocol B driving data");

		String excelFilePath = "/Users/ehsanebk/OneDrive - drexel.edu"
				+ "/Driving data - standard deviation lateral position (Singapore)"
				+ "/Driving Data/Protocol B driving data/508ProcessedData.xlsx";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();

			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.print(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(cell.getNumericCellValue());
					break;
				}
				System.out.print(" - ");
			}
			System.out.println();
		}

		workbook.close();
		inputStream.close();
	


		
//		for (File file : ProtocolA.listFiles())
//			if (file.getName().endsWith("ProcessedData.xlsx") && !file.getName().startsWith("~")) {
//				FileInputStream inputStream = new FileInputStream(file);
//				
//				Workbook workbook = new XSSFWorkbook(inputStream);
//		        Sheet firstSheet = workbook.getSheetAt(0);
//		        Iterator<Row> iterator = firstSheet.iterator();
//		         
//		        Row nextRow = iterator.next();
//	            Iterator<Cell> cellIterator = nextRow.cellIterator();
//	             
//	            while (cellIterator.hasNext()) {
//	                Cell cell = cellIterator.next();
//	                System.out.print(cell.getNumericCellValue()); 
//	                
//	                System.out.print(" - ");
//	            }
//	            System.out.println("test");
//		        
//		        
////		        while (iterator.hasNext()) {
////		            Row nextRow = iterator.next();
////		            Iterator<Cell> cellIterator = nextRow.cellIterator();
////		             
////		            while (cellIterator.hasNext()) {
////		                Cell cell = cellIterator.next();
////		                System.out.print(cell.getNumericCellValue()); 
////		                
////		                System.out.print(" - ");
////		            }
////		            System.out.println();
////		        }
//		         
//		        workbook.close();
//		        inputStream.close();
//				
//			}
//		
//	
	}

}
