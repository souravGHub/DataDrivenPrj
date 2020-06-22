package com.ssg.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;

import org.testng.annotations.Test;

import com.ssg.base.TestBase;

public class BankManagerLoginTest extends TestBase{
	@Test
	public void bankManagerLoginTest() throws InterruptedException {		
		
		try {
			verifyEquals("ABC","XYZ");//soft failure
			Thread.sleep(3000);			
			System.out.println("After Assersion.....");
		}catch(Throwable t) {
			System.out.println("Inside catch- Title error!!!!");
		}		
		log.debug("Inside LoginAsBankManager");
		click("bnkMgrLogInBtn_CSS");		
		Assert.assertTrue(IsElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))),"Login not successful");	
		log.debug("Assert Successful...");
		log.debug("LoginSuccessful...");		
	}
}
