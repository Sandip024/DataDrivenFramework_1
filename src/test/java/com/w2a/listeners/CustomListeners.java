package com.w2a.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.TestBase;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.TestUtil;

public class CustomListeners extends TestBase implements ITestListener {

	public void onTestStart(ITestResult result) {

		// With below commented code eaach parameterization tests look as separate test. So we have added this into onStart method of this interface
		// test = rep.startTest(result.getName().toUpperCase());
		// rep.endTest(test);
		// rep.flush();

		if (!TestUtil.isTestRunable(result.getName(), excel)) {

			throw new SkipException("");
		}

	}

	public void onTestSuccess(ITestResult result) {
		
		//test.log(LogStatus.PASS, result.getName().toUpperCase() +" IS PASSED");
		test.log(LogStatus.PASS,result.getName().toUpperCase() +" IS PASSED");
		rep.endTest(test);
		rep.flush(); // without this command, report wont be generated

	}

	public void onTestFailure(ITestResult result) {

		System.setProperty("org.uncommons.reportng.escape-output", "false");

		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ExtentReport
		// test.log(LogStatus.FAIL, result.getTestName().toUpperCase()+"Failed with
		// exception"+result.getThrowable());
		test.log(LogStatus.FAIL, "Test Failed with exception: " + result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenShotName));

		// ReportNG
		Reporter.log("Click image below to see failed test Screenshot:-");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenShotName + "><img src=" + TestUtil.screenShotName
				+ " height=200 width=200></img></a>");

		rep.endTest(test);
		rep.flush(); // without this command report wont be generated

	}

	public void onTestSkipped(ITestResult result) {
		test.log(LogStatus.SKIP, "Skipped the test:("+result.getName()+") as the runmode is set to NO, with an exceeption:("+  result.getThrowable()+")");
		rep.endTest(test);
		rep.flush(); // without this command report wont be generated
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public void onStart(ITestContext context) {
		
		// Using this code here wont let each parameter set to create separate test in extent report, instead it executes
		// all tests inside one test one below another.
		
		test = rep.startTest(context.getName().toUpperCase());

	}

	public void onFinish(ITestContext context) {

	}

}
