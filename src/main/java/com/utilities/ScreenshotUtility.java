package com.utilities;
import java.util.Properties;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

//In utility Class create a param object

public class ScreenshotUtility implements ITestListener
{	
	
	Properties Param;
	
	//This method will execute before starting of Test suite.
      public void onStart(ITestContext tr) 
      {	
        	
      	  
	    }

	//This method will execute, Once the Test suite is finished.
	public void onFinish(ITestContext tr) 
	{
		
		
	}

	//This method will execute when the test case is passed.
	  public void onTestSuccess(ITestResult tr) 
	  {
       
        //If screenShotOnPass = yes, call captureScreenShot.
		if(Param.getProperty("screenShotOnPass").equalsIgnoreCase("yes"))
		{
			HelperClass.captureScreenShot(tr,"pass");
		}
		
	}

	//This method will execute when test case fails.
	public void onTestFailure(ITestResult tr) 
	{		
		
		if(Param.getProperty("screenShotOnFail").equalsIgnoreCase("yes"))
		{
			HelperClass.captureScreenShot(tr,"fail");
		}
	}

	// This method will execute before the main test start (@Test)
	public void onTestStart(ITestResult tr) 
	{
	   
	   Param=HelperClass.param;
		
	}

	// This method will execute only if any of the main test(@Test) get skipped
	public void onTestSkipped(ITestResult tr) 
	{		
		
	}
	
	public void onTestFailedButWithinSuccessPercentage(ITestResult tr) 
	{
		
	}
	
}