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

import javax.swing.SortingFocusTraversalPolicy;
import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
				// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			System.out.println("Back button is not displayed");
		}
		Thread.sleep(3000);

		//Verify the Home Page title
		AssertJUnit.assertEquals("MyDay", driver.getTitle());
		System.out.println("Page Title is: " + driver.getTitle());		

		Thread.sleep(6000);

	}

}

