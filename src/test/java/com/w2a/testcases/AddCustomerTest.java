package com.w2a.testcases;
import com.w2a.base.*;
import com.w2a.utilities.TestUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class AddCustomerTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class,dataProvider="dp")
	public void addCustomerTest(Hashtable<String,String> data) throws InterruptedException, IOException {
		
		//verifyEquals("abc", "xyz");
		//added this line just to test if changes are reflecting on github or not.
		click("addCustBtn_Xpath");
		
		type("firstname_Xpath", data.get("firstname"));
		type("lastname_Xpath", data.get("lastname"));
		type("postcode_Xpath", data.get("postcode"));
		click ("addbtn_Xpath");
		
		Thread.sleep(3000);
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains("successfully"),data.get("alerttext"));
		alert.accept();
	
		Thread.sleep(3000);
	}
}
