package app.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadXLSCredentials {
	
	public static String ReadingXLSUsername(String strTagName)  {
		String strTagValue = "";
		String strUserNXLS = "";
		String strLastname = "";
		String strFirstname = "";
		String strNetID = "";
			
		try {
		
		File excelFile = new File (".\\snData.xlsx");
		FileInputStream fis = new FileInputStream(excelFile);
		
		//create XSSF workbook object
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		
		//get first sheet
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		//Row
		Iterator<Row> rowIt = sheet.iterator();
		
		//Headers
		//Row rowHeaders = rowIt.next();
		//Values
		Row row = rowIt.next();
		
		//Cell
		Iterator<Cell> cellIterator = row.cellIterator();
		
		Cell cell = cellIterator.next();
		System.out.println("Username: " +cell.toString());
		strUserNXLS = cell.toString();
	
		cell = cellIterator.next();
		System.out.println("LastName: " +cell.toString());
		strLastname = cell.toString();

		cell = cellIterator.next();
		System.out.println("FirstName: " +cell.toString());
		strFirstname = cell.toString();
		
		cell = cellIterator.next();
		System.out.println("NetID: " +cell.toString());
		strNetID = cell.toString();
		
		switch (strTagName) {
		case "username":
			strTagValue = strUserNXLS;
			break;	
		case "lastname":
			strTagValue = strLastname;
			break;	
		case "firstname":
			strTagValue = strFirstname;
			break;
		case "netid":
			strTagValue = strNetID;
			break;
		default:
			strTagValue = "element Not Found in XLS columns";

		}
		
		fis.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return strTagValue; 
	}
	
	

}
