package com.ssg.base;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ssg.utilities.ExcelReader;
public class myPractice {
	public static FileInputStream fis = null;
	public static void main(String[] args) throws IOException {		
		Properties prop = new Properties();				
		fis = new FileInputStream (System.getProperty("user.dir")+
		"\\src\\test\\resources\\properties\\config.properties");
		prop.load(fis);	
		String browser = prop.getProperty("browser");
		String url = prop.getProperty("testsiteurl");
		String implicitWaitTime = prop.getProperty("implicit.wait");		
		System.out.println("Browser : "+ browser);
		System.out.println("WebSite : "+ url);
		System.out.println("Implicit Wait : "+ implicitWaitTime);		
		
		/*
		 * 
		 * 
		 */
						
		//Creating a file 		
		//File file1 = new File("C:\\JavaRWFiles\\TestingFile.txt");
		File file1 = new File("C:\\JavaRWFiles\\TestingFile.csv");	
			file1.createNewFile();
		//writing to a notepad
		FileWriter fw = new FileWriter(file1,true);
		BufferedWriter fileBWriter = new BufferedWriter(fw);
		fileBWriter.write("Please write inside this file");fileBWriter.newLine();
		fileBWriter.write("This is my second line");fileBWriter.newLine();
		//fileBWriter.close();
		fileBWriter.flush();
		//and reading
		FileReader fr = new FileReader("C:\\JavaRWFiles\\TestingFile.txt");
		BufferedReader fileBReader = new BufferedReader(fr);
		/*System.out.println(fileBReader.readLine());//prints first line only
		System.out.println(fileBReader.readLine());//prints second line only*/
		String line="";
		while((line = fileBReader.readLine())!=null) {
			System.out.println(line);
		}
		fileBWriter.close();
		fileBReader.close();
		
		//xlsx file  write....
		XSSFWorkbook xlwb = new XSSFWorkbook();
		XSSFSheet xlsh = xlwb.createSheet("Sheet1"); 
//		XSSFRow row1 = xlsh.createRow(0);
//		Cell cellA = row1.createCell(0);
//		Cell cellB = row1.createCell(1);
//		cellA.setCellValue("Santa");
//		cellB.setCellValue("Banta");
		
		for (int row = 0; row<10;row ++) {
			XSSFRow row1 = xlsh.createRow(row);
			for (int col = 0; col<10;col ++) {
				Cell cellA = row1.createCell(col);
				cellA.setCellValue((int)(Math.random()*100));				
			}	
		}
	File fileXlsx = new File("C:\\JavaRWFiles\\TestingFile.xlsx");		
	FileOutputStream fos = new FileOutputStream(fileXlsx);
	xlwb.write(fos);
	fos.close();
	System.out.println("File is created");	
	//xlsx file  read....
	
	Logger log = Logger.getLogger("devpinoyLogger");
	
	
	ExcelReader	excel = new ExcelReader("C:\\JavaRWFiles\\testdata.xlsx");
	String sheetName = "trainer";
	log.debug("Getting row and column count");
	byte rowC = (byte) excel.getRowCount(sheetName );
	byte colC = (byte) excel.getColumnCount(sheetName);	
	System.out.print("Row="+rowC + " :: Cell " + colC);	
	log.debug("Getting data from excel");
	System.out.print(excel.getCellData(sheetName, "username",2));
	log.debug("Writing data to excel");
	excel.setCellData(sheetName, "username",3,"Ishita");	
	}
}
