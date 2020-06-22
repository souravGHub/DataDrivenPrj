package com.ssg.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.ssg.utilities.ExcelReader;
import com.ssg.utilities.ExtentManager;
import com.ssg.utilities.TestUtil;

public class TestBase {

	
	/*
	 * WebDriver
	 * Properties
	 * Logs
	 * ExtentReport
	 * DB 
	 * Excel
	 * Mail
	 */
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") +"\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports extentRep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser; 
	@BeforeSuite
	public void SetUp() {		
		if(driver ==null) {			
			try {
				fis = new 	FileInputStream(System.getProperty("user.dir") +"\\src\\test\\resources\\properties\\config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Conf File Loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis = new 	FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR File Loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			if(System.getenv("browser") != null && !System.getenv("browser").isEmpty())//Check id browser selection is done in jenkins
			{
				browser = System.getenv("browser"); // set browser selection from Jenkkins
			}else {
				browser=config.getProperty("browser");// else set from config.properties file.
			}
			config.setProperty("browser",browser);
			
			
			if(config.getProperty("browser").contentEquals("firefox")){
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\geckodriver.exe"); // We need to use this is latest firefox driver is being used
				driver = new FirefoxDriver();	
			}else if(config.getProperty("browser").contentEquals("chrome")) {
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();	
				log.debug("Chrome Launched");
			}
			else if(config.getProperty("browser").contentEquals("ie")) {
				System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated to "+config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),TimeUnit.SECONDS);
			wait = new WebDriverWait(driver,5);
		}
	}
	public void click(String locator) {
		
		if(locator.endsWith("_CSS")) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
			}else if (locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator))).click();
			}
		test.log(LogStatus.INFO, "Clicking on " + locator);
	}
	public void type(String locator,String value) {
		if(locator.endsWith("_CSS")) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);		
		}else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);	
			}else if (locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);	
			}		
		test.log(LogStatus.INFO, "Typing in " + locator + " Entered : "+ value);
	}
	WebElement dropdown;
	public void select(String locator,String value) {
		if(locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));		
		}else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));	
			}else if (locator.endsWith("_ID")) {
				dropdown = driver.findElement(By.id(OR.getProperty(locator)));	
			}		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		
		test.log(LogStatus.INFO, "Selecting from dropdown " + locator + " Value is  : "+ value);
	}
	
	public boolean IsElementPresent(By by) {
	try {
	driver.findElement(by);
	return true;
	}catch(NoSuchElementException e) {
		return false;
	}
}
	public static void verifyEquals(String expected, String actual) throws IOException {
		try {	
			Assert.assertEquals(expected,actual);
		}catch(Throwable t) {
			TestUtil.captureScreenShot();
			//Report NG
			Reporter.log("<BR> Verification Failure" + t.getMessage() +"<br>");
			Reporter.log("<a target = '_blank' href=" + TestUtil.ScreenShotName +">"
					+ "<img src =" + TestUtil.ScreenShotName +" height=100 width=100 </a><br><br>");			
			//Extent Reports
			test.log(LogStatus.FAIL,"Verification failed : "+ t.getMessage());
			test.log(LogStatus.FAIL,test.addScreenCapture(TestUtil.ScreenShotName));
	 	}
	}
	@AfterSuite
	public void TearDown() {
		if(driver!= null) {
		driver.quit();		
		}
		log.debug("Test Execution completed!!!!");
	}	
}
