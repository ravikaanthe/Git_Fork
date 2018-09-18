package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.utilities.HelperClass;

public class Login_Page {
	WebDriver driver;
	By country_dropdown = By.xpath("//button[@type='button']");
	By select_country = By.linkText("(+91) INDIA");
	By email = By.id("i0116");
	By password = By.id("i0118");
	By next_button = By.id("idSIButton9");
	By backButton = By.id("idBtn_Back");
	By select_equipment = By.xpath("//li[21]/p");
	By first_equipment = By.xpath("//li[1]/p");
	

	// Constructor
		public Login_Page(WebDriver driver) {
			this.driver = driver;

		}

		public void typeemail(String user) {
			try {
				HelperClass.enterWebElement(email, user);
			} catch (NoSuchElementException nse) {
				System.out.println("mobile_number: No such element" + nse.toString());
				throw (nse);
			}

			catch (ElementNotVisibleException enve) {
				try {
					HelperClass.enterWhenVisible(email, user, 3);
				}

				catch (TimeoutException ex) {
					System.out.println("Timeout..mobile_number cannot found:" + ex.toString());
					throw (ex);
				}
			}
		}
		
		
		public void typePassword(String user) {
			try {
				HelperClass.enterWebElement(password, user);
			} catch (NoSuchElementException nse) {
				System.out.println("password: No such element" + nse.toString());
				throw (nse);
			}

			catch (ElementNotVisibleException enve) {
				try {
					HelperClass.enterWhenVisible(password, user, 3);
				}

				catch (TimeoutException ex) {
					System.out.println("Timeout..password cannot found:" + ex.toString());
					throw (ex);
				}
			}
		}
		

		public void clickOnLoginButton() {
			try {
				HelperClass.clickWebelement(next_button);
			} catch (ElementNotVisibleException enve) {
				try {
					HelperClass.clickWhenReady(next_button, 3);
				}

				catch (TimeoutException e) {
					System.out.println("Timeout...login_button cannot be found.." + e.toString());
					throw (e);
				}
			} catch (NoSuchElementException noe) {
				System.out.println("login_button: no such element" + noe.toString());
				throw (noe);
			}
		}
		
		public WebElement clickOnbackButton() {
			try {
				HelperClass.clickWebelement(backButton);
			} catch (ElementNotVisibleException enve) {
				try {
					HelperClass.clickWhenReady(backButton, 3);
				}

				catch (TimeoutException e) {
					System.out.println("Timeout...backButton cannot be found.." + e.toString());
					throw (e);
				}
			} catch (NoSuchElementException noe) {
				System.out.println("backButton: no such element" + noe.toString());
				throw (noe);
			}
			return null;
		
		}
		
		
}
