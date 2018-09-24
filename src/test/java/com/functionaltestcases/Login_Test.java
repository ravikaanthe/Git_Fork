package com.functionaltestcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;



import com.pages.Login_Page;
import com.utilities.BaseClass;
import com.utilities.ExcelRead;
import com.utilities.HelperClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Iterator;

import javax.swing.SortingFocusTraversalPolicy;
import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class Login_Test extends BaseClass {

	Login_Page login;
	HelperClass hc;

	@BeforeClass
	public void initobj() {

		login = new Login_Page(driver);
		hc = new HelperClass(driver);
	}

	@AfterClass
	public void deleteObjects() {
		try {
			if (login != null && hc != null) {
				login = null;
				hc = null;
				System.out.println("sinin & Helper Class objects deleted successfully...");
			}
		} catch (NullPointerException ne) {
			System.out.print("sinin Objects already deleted...");
		}
	}


	@Test(dataProvider = "TestData", dataProviderClass = ExcelRead.class, priority = 1)
	public void loginToDRLWithInValidCred(String email, String password) throws InterruptedException {
		
		 // then ask for all the performance logs from this request
        // one of them will contain the Network.responseReceived method
        // and we shall find the "last recorded url" response
        LogEntries logs = driver.manage().logs().get("performance");

        int status = -1;

        System.out.println("\nList of log entries:\n");

        for (Iterator<LogEntry> it = logs.iterator(); it.hasNext();)
        {
            LogEntry entry = it.next();

            try
            {
                JSONObject json = new JSONObject(entry.getMessage());

//                System.out.println(json.toString());

                JSONObject message = json.getJSONObject("message");
                String method = message.getString("method");

                if (method != null
                        && "Network.responseReceived".equals(method))
                {
                    JSONObject params = message.getJSONObject("params");

                    JSONObject response = params.getJSONObject("response");
                    String messageUrl = response.getString("url");

                    if ((Param.getProperty("siteURL")).equals(messageUrl))
                    {
                        status = response.getInt("status");

                        System.out.println(
                                "---------- bingo !!!!!!!!!!!!!! returned response for "
                                        + messageUrl + ": " + status);

                        System.out.println(
                                "---------- bingo !!!!!!!!!!!!!! headers: "
                                        + response.get("headers"));
                    }
                }
            } catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println("\nstatus code: " + status);
    
		
		//Enter Email Address
		login.typeemail(email);
		//Click On Login button
		login.clickOnLoginButton();
		Thread.sleep(3000);
		//Enter Password
		login.typePassword(password);
		//Click On Login button
		login.clickOnLoginButton();
		Thread.sleep(3000);
		//Click on No button if DRLdev should remember your credentials
		try {
			login.clickOnbackButton();
		} catch (Exception e) {
			System.out.println("Back button is not displayed");
		}


	}

	@Test(dataProvider = "TestData", dataProviderClass = ExcelRead.class, priority = 2)
	public void loginToDRLWithValidCred(String email, String password) throws InterruptedException {

		
		//Enter Email Address
		login.typeemail(email);
		//Click On Login button
		login.clickOnLoginButton();
		Thread.sleep(3000);
		//Enter Password
		login.typePassword(password);
		//Click On Login button
		login.clickOnLoginButton();
		Thread.sleep(3000);

		//Click on No button if DRLdev should remember your credentials
		try {
			login.clickOnbackButton();
		} catch (Exception e) {
			System.out.println("Back button is not displayed");
		}
		Thread.sleep(5000);

		 // then ask for all the performance logs from this request
        // one of them will contain the Network.responseReceived method
        // and we shall find the "last recorded url" response
        LogEntries logs = driver.manage().logs().get("performance");

        int status = -1;

        System.out.println("\nList of log entries:\n");

        for (Iterator<LogEntry> it = logs.iterator(); it.hasNext();)
        {
            LogEntry entry = it.next();

            try
            {
                JSONObject json = new JSONObject(entry.getMessage());

//                System.out.println(json.toString());

                JSONObject message = json.getJSONObject("message");
                String method = message.getString("method");

                if (method != null
                        && "Network.responseReceived".equals(method))
                {
                    JSONObject params = message.getJSONObject("params");

                    JSONObject response = params.getJSONObject("response");
                    String messageUrl = response.getString("url");

                    if ((Param.getProperty("siteURL")).equals(messageUrl))
                    {
                        status = response.getInt("status");

                        System.out.println(
                                "---------- bingo !!!!!!!!!!!!!! returned response for "
                                        + messageUrl + ": " + status);

                        System.out.println(
                                "---------- bingo !!!!!!!!!!!!!! headers: "
                                        + response.get("headers"));
                    }
                }
            } catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println("\nstatus code: " + status);
    
		
		//Verify the Home Page title
		AssertJUnit.assertEquals("MyDay", driver.getTitle());
		System.out.println("Page Title is: " + driver.getTitle());		

		Thread.sleep(6000);

	}


}

