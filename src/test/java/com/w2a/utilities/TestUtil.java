package com.w2a.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.w2a.base.TestBase;

public class TestUtil extends TestBase {
	
	public static String screenShotPath;
	public static String screenShotName;
	
	public static void captureScreenshot() throws IOException {
				
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Date d = new Date();
		screenShotName = d.toString().replace(" ", "_").replace(":", "_") + ".jpg";
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+screenShotName));		
	}
	
	@DataProvider(name="dp")
	public Object[][] getData(Method m){
		
		String sheetname=m.getName();
		
		int rows = excel.getRowCount(sheetname);
		int cols = excel.getColumnCount(sheetname);
		
		
		Object[][] data = new Object[rows-1][1];
		
		Hashtable<String,String> table=null;
		
		for(int rowNum=2; rowNum<=rows; rowNum++) {
			
			table = new Hashtable<String, String>();
			
			for(int colNum=0; colNum<cols; colNum++) {
				//data[0][0]
				
				table.put(excel.getCellData(sheetname, colNum, 1), excel.getCellData(sheetname, colNum, rowNum));
				
				data[rowNum-2][0]=table;	
			}
		}
		return data;	
	}
	
	public static boolean isTestRunable (String testName, ExcelReader excel) {
		
		String sheetname = "test_suite";
		int rows = excel.getRowCount(sheetname);
		
		for(int rNum=2; rNum<=rows; rNum++) {
			
			String testCase = excel.getCellData(sheetname, "TCID", rNum);
			if(testCase.equalsIgnoreCase(testName)){
				String runmode = excel.getCellData(sheetname, "Runmode", rNum);
				
				if(runmode.equalsIgnoreCase("Y"))
					return true;
				else
					return false;
			}
		}
		return false;
	}
}
