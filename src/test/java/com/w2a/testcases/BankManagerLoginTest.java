package com.w2a.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	
	@Test
	public void bankManagerLoginTest() throws IOException, InterruptedException {
		
		//verifyEquals("abc", "xyz");
		
		click("bmlBtn_Xpath");
	
		Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("addCustBtn_Xpath"))),"Bank Manager Login Test failed");
		log.info("Login succesfull");
		
		Thread.sleep(3000);
		
	}
	
	
	
}
