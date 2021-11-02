package com.w2a.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtil;

public class TestBase {

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log;
	public static WebDriverWait wait;
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	
	
	//setup method before any test suite
	
	@BeforeSuite
	public void setUp() throws IOException {

		if (driver == null) {

			// ********************* Initialize logger
			// *****************************************************************************

			Date d = new Date();

			System.setProperty("current.date", d.toString().replace(":", "_").replace(" ", "_"));

			PropertyConfigurator
					.configure(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\log4j.properties");

			log = Logger.getLogger(TestBase.class.getName());

			// ******************************* logs have been used in below part of the
			// code*****************************************

			// ********************** Initialize config properties file
			// ************************************************************

			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			config.load(fis);
			log.info("config file has been loaded");

			// ********************** Initialize OR properties file
			// ****************************************************************
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			OR.load(fis);
			log.info("OR file has been loaded");

			// ********************** Initialize browser
			// ***************************************************************************
			if (config.getProperty("browser").equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
				log.info("Starting Chrome");
			}
			if (config.getProperty("browser").equals("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\geckodriver.exe");
				driver = new ChromeDriver();
				log.info("Starting Firefox");
			} else if (config.getProperty("browser").equals("edge")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\msedgedriver.exe");
				driver = new ChromeDriver();
				log.info("Starting Edge");
			}

			driver.get(config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);
			log.info("Opened URL");
		}
	}
	
	
	//utility method to click on an element
	
	public void click(String locator) {

		if (locator.endsWith("_Xpath")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ClassName")) {
			driver.findElement(By.className(OR.getProperty(locator))).click();
		}

		test.log(LogStatus.INFO, "Clicking on : " + locator);
	}

	
	//utility method to type in an text-box located by an element
	
	public void type(String locator, String value) {
		
		if (locator.endsWith("_Xpath")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ClassName")) {
			driver.findElement(By.className(OR.getProperty(locator))).sendKeys(value);
		}
		
		test.log(LogStatus.INFO, "Typing in : " + locator + " and entered value as : " + value);
	}

	
	// Ulitity method to check if any element is present or not
	
	public boolean isElementPresent(By by) {

		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	
	//utility method to verify if two strings are identical or not
	
	public static void verifyEquals(String exepcted, String actual) throws IOException {
		
		try {
			Assert.assertEquals(actual, exepcted);
			
		}catch (Throwable t){
			
			//For reportNG
			TestUtil.captureScreenshot();
			Reporter.log("<br>"+"verification failure: "+t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenShotName+"><img src="+TestUtil.screenShotName+" height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			
			//For ExentReport
			test.log(LogStatus.FAIL, "Verification Failed with exception"+t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenShotName));	
		}
	}
	
	
	//utility method to select an element from dropdown
	
	static WebElement dropdown;
		
	public void select(String locator, String value) {
		
		if (locator.endsWith("_Xpath")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		} else if (locator.endsWith("_ClassName")) {
			dropdown = driver.findElement(By.className(OR.getProperty(locator)));
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		
		test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as : " + value);	}
	
	//utility method to close the browser	
	
	@AfterSuite
	public static void tearDown() {
		
		driver.quit();
	}

}
