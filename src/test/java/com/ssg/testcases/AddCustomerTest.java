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

public class AddCustomerTest extends TestBase{		
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
		public void addCustomerTest(Hashtable<String,String> data) throws InterruptedException {		
		
		
		
		if (!data.get("runmode").equalsIgnoreCase("Y")){
			throw new SkipException("Skippin the test data set : Fname = "+ data.get("firstname"));			
		}		
		//log.debug(data.get("firstName") +" "+ data.get("lastName") + " "+ data.get("postalCode"));
		click("addCustBtn_CSS");	type("firstName_CSS",data.get("firstname"));
		type("lastName_XPATH",data.get("lastname"));	type("postalCode_CSS",data.get("postalcode"));	
		Thread.sleep(2000);	click("addCustBtnSubmit_CSS");
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		Thread.sleep(1000);
		alert.accept();		
	}
}
