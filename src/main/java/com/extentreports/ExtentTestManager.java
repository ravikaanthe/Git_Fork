package com.extentreports;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentTestManager 
{
	static Map extentTestMap = new HashMap();

	public static synchronized ExtentTest getTest() {
		return (ExtentTest)extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

}
