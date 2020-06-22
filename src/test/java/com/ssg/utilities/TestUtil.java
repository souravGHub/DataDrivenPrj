package com.ssg.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.ssg.base.TestBase;

public class TestUtil extends TestBase{
	public static String ScreenShotPath;
	public static String ScreenShotName;
	public static void captureScreenShot() throws IOException {
		
		File scrShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		Date d = new Date();
		ScreenShotName = d.toString().replace(":","").replace(" ","_") + ".jpg";
		FileUtils.copyFile(scrShot,new File(System.getProperty("user.dir")+
				"\\target\\surefire-reports\\html\\"+ScreenShotName));
	}
	
	@DataProvider(name="dp")
	public Object[][] getData(Method m){//this 'Method m' will get the method from where it is called
		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		log.debug("ROWS :" +rows + " COLS: "+  cols);	
				
		Object[][] data = new Object [rows -1][1];
		Hashtable <String,String> hTable = null;
		
		for (int rowNum = 2;rowNum <=rows;rowNum++) {
			hTable = new Hashtable <String,String>();
			for (int colNum = 0;colNum <cols;colNum++) {
				hTable.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum-2][0] = hTable;				
			}		
		}
		return data;	
	}	
	
	public static boolean isTestRunnable(String testName, ExcelReader excel) {
				
		String sheetName = "test_suite";
		int rows= excel.getRowCount(sheetName);		
		for (int rowNum = 2;rowNum <=rows;rowNum++) {			
			String  testCase = excel.getCellData(sheetName, "TCID", rowNum);			
			if(testCase.equalsIgnoreCase(testName)) {				
				String runMode = excel.getCellData(sheetName, "runmode" , rowNum);
					if(runMode.equalsIgnoreCase("Y")) {
						return true;
					}else{
						return false;
					}
			}
			
		}
		return false;
	}
	
	
}
