package com.ssg.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ssg.base.TestBase;
import com.ssg.utilities.TestUtil;

public class OpenAccTest extends TestBase{	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void openAccTest(Hashtable <String,String> data) throws InterruptedException {
		if (!TestUtil.isTestRunnable("OpenAccTest", excel)) {
			throw new SkipException("Skippin the test "+ "OpenAccTest "+"as Runmode is 'N'");			
		}
		click("openAccBtn_CSS");
		select("customer_CSS",data.get("customer"));
		select("currency_CSS",data.get("currency"));		
		click("processBtnSubmit_CSS");
		Thread.sleep(1000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));		
		alert.accept();				
	}	
}
