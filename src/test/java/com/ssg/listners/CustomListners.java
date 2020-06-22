package com.ssg.listners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;
import com.ssg.base.TestBase;
import com.ssg.utilities.MonitoringMail;
import com.ssg.utilities.TestConfig;
import com.ssg.utilities.TestUtil;


public class CustomListners extends TestBase implements ITestListener,ISuiteListener{
	String msgBody;
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		test = extentRep.startTest(result.getName().toUpperCase());
		//runModes
	}

	public void onTestSuccess(ITestResult tst) {
		// TODO Auto-generated method stub
		test.log(LogStatus.PASS,tst.getName().toUpperCase()+ "PASS");
		extentRep.endTest(test);
		extentRep.flush();
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		System.setProperty("org.uncommons.reportng.escape-output","false");
		//Reporter.log("Capturing Screen Shot...");
		try {
			TestUtil.captureScreenShot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test.log(LogStatus.FAIL,result.getName().toUpperCase()+ "Failed with Exception: "+ result.getThrowable());
		test.log(LogStatus.FAIL,test.addScreenCapture(TestUtil.ScreenShotName));
		
		Reporter.log("Click to see screen shot:");
		Reporter.log("<a target = '_blank' href=" + TestUtil.ScreenShotName +">ScreenShot</a>");
		Reporter.log("<BR><BR>");
		Reporter.log("<a target = '_blank' href=" + TestUtil.ScreenShotName +">"
				+ "<img src =" + TestUtil.ScreenShotName +" height=100 width=100 </a>");
		extentRep.endTest(test);
		extentRep.flush();		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub		
		test.log(LogStatus.SKIP, result.getName().toUpperCase()+" Skipped the test");
		extentRep.endTest(test);
		extentRep.flush();
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		MonitoringMail mail = new MonitoringMail();
		
		try {
			msgBody = "http://" + InetAddress.getLocalHost().getHostAddress()+":8080/job/DataDrivenLivePrj/EXTENT_5fReport/";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(msgBody);
		
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, msgBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
