package com.w2a.testcases;
import com.w2a.base.*;
import com.w2a.utilities.TestUtil;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class,dataProvider="dp")
	public void openAccountTest(Hashtable<String,String> data) throws InterruptedException {
		
		click("openaccount_Xpath");
		select("customer_Xpath", data.get("customer"));
		select("currency_Xpath", data.get("currency"));
		click("process_Xpath");
		
		Thread.sleep(3000);
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();	
	}
}
