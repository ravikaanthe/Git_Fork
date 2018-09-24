package com.extentreports;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager 
{

	private static ExtentReports extent;
	 
    public synchronized static ExtentReports getReporter(){
        if(extent == null){
            //Set HTML reporting file location
            extent = new ExtentReports(System.getProperty("user.dir")+ "test-output" +"/extent.html", true);
        }
        return extent;
    }
}
