package com.utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;



public class HelperClass {
	static RemoteWebDriver driver;
	static Properties param;

	public HelperClass(WebDriver driver) {
		HelperClass.driver = (RemoteWebDriver) driver;
	}

	/**
	 * Enter data in the WebElemet when the expected condition is met
	 * 
	 * @param locator
	 * @param data
	 * @param timeout
	 */
	public static void enterWhenVisible(By locator, String data, int timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		element.sendKeys(data);
		element = null;
	}

	/**
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public static String getWhenVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		String LblTxt = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
		return LblTxt;
	}

	/**
	 * Returns the text when element is displayed in the page
	 * 
	 * @param locator
	 * @return
	 */
	public static String getTextVisible(By locator) {
		WebElement element = driver.findElement(locator);
		String LblTxt = "";
		if (element.isDisplayed()) {
			LblTxt = element.getText();
		}
		return LblTxt;
	}

	/**
	 * Clicks on the WebElement when the expected condition is met
	 * 
	 * @param locator
	 * @param timeout
	 */
	public static void clickWhenReady(By locator, int timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();
		element = null;
	}

	/**
	 * Click on the WebElement when enabled
	 * 
	 * @param locator
	 */
	public static void clickWebelement(By locator) {
		WebElement element = driver.findElement(locator);
		boolean elementIsClickable = element.isEnabled();
		if (elementIsClickable) {
			element.click();
			element = null;
		} else {
			System.out.print(locator.toString() + ":Element not clickable");
		}
	}

	/**
	 * 
	 * @param locator1
	 * @param locator2
	 */
	public static void selectDropdownWebElement(By locator1, By locator2) {
		WebElement element1 = driver.findElement(locator1);
		element1.click();
		WebElement element2 = driver.findElement(locator2);
		element2.click();
	}
	

	/**
	 * Select value from dropdown
	 * 
	 * @param locator
	 * @param data
	 */
	public static void selectDropdownWebElement(By locator, String data) {
		WebElement element = driver.findElement(locator);
		Select dropdown = new Select(element);
		try {
			dropdown.selectByVisibleText(data);
		} catch (Exception e1) {
			try {
				dropdown.selectByValue(data);
			} catch (Exception e2) {
				try {
					dropdown.selectByIndex(Integer.valueOf(data));
				} catch (Exception e3) {
				}
			}
		}
	}

	/**
	 * Select value from choose and select dropdown
	 * 
	 * @param locator1
	 * @param locator2
	 * @param locator3
	 * @param data
	 */
	public static void selectChooseandSelectWebElement(By locator1, By locator2, By locator3, String data) {
		WebElement element = driver.findElement(locator1);
		element.click();
		WebElement element2 = driver.findElement(locator2);
		element2.sendKeys(data);
		WebElement element3 = driver.findElement(locator3);
		element3.click();
	}

	/**
	 * 
	 * Enter data in WebElement
	 * 
	 * @param locator
	 * @param data
	 */
	public static void enterWebElement(By locator, String data) {
		WebElement element = driver.findElement(locator);
		String text = element.getText();
		if (text.isEmpty()) {
			element.clear();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			element.sendKeys(data);

		} else {
			element.clear();
			element.sendKeys(data);
		}
		element = null;
	}

	/**
	 * Upload File
	 * 
	 * @param locator
	 * @param filepath
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public static void uploadFile(By locator, String filepath) throws AWTException, InterruptedException {
		WebElement element = driver.findElement(locator);
		element.click();
		StringSelection ss = new StringSelection(filepath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Robot robot;
		robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_V);
		Thread.sleep(1000);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(1000);
	}

	/**
	 * Method to verify whether page is loaded successfully or not
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean checkPageComplete() throws Exception {
		boolean state = false;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// This loop will rotate for 10 times to check If page Is ready after every 1
		// second.
		for (int i = 0; i < 9; i++) {
			try {
				Thread.sleep(1000);
				if (js.executeScript("return document.readyState").toString().equals("complete")) {
					state = true;
					System.out.println("Page loaded successfully");
					break;
				}
			} catch (Exception e) {
				System.out.print("Page cannot be loaded...");
				throw (e);
			}
		}
		return state;
	}

	/**
	 * Reads the properties file
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Properties readParam() throws IOException {
		param = new Properties();
		FileInputStream fip = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/com/utilities/Param.properties");
		param.load(fip);
		System.out.println("Param.properties file loaded successfully.");
		return param;
	}

	/**
	 * 
	 * @param locator
	 * @return
	 */
	public static String getListPage(By locator) {
		WebElement element = driver.findElement(locator);
		String title = element.getText();
		return title;
	}

	/**
	 * 
	 * Method to take screenshot on test success or failure
	 * 
	 * @param result
	 * @param status
	 */
	public static void captureScreenShot(ITestResult result, String status) {
		String destDir = "";
		String methodName = result.getMethod().getRealClass().getSimpleName() + "."
				+ result.getMethod().getMethodName();
		System.out.println("Method Name:" + methodName);
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");

			// If status = fail then set folder name "screenshots/Failures"
			if (status.equalsIgnoreCase("fail")) {
				destDir = "Screenshots/Failures";
			}
			// If status = pass then set folder name "screenshots/Success"
			else if (status.equalsIgnoreCase("pass")) {
				destDir = "Screenshots/Success";
			}

			// To create folder to store screenshots
			new File(destDir).mkdirs();

			// Set file name with combination of test class name + date time.
			String destFile = methodName + " - " + dateFormat.format(new Date()) + ".png";

			FileHandler.copy(scrFile, new File(destDir + "/" + destFile));
		} catch (IOException e) {
			System.out.println("Exception while taking screenshot or copying the files to destination folder:" + ""
					+ e.toString());
		}
	}

	/**
	 * Method to direct selecting the dropdown value
	 * 
	 * @param locator1
	 * @param locator2
	 */
	public static void directSelectChooseandSelectWebElement(By locator1, By locator2) {
		WebElement element1 = driver.findElement(locator1);
		element1.click();
		WebElement element2 = driver.findElement(locator2);
		element2.click();

	}

	public static void enterWhenVisible(By locator1, By locator2, By locator3, String data, int timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator1));
		element.click();
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator2));
		element.sendKeys(data);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator3));
		element.click();
		element = null;

	}

	/**
	 * Date Selection from date picket
	 * 
	 * @param locator
	 * @param Date
	 * @throws InterruptedException
	 */
	public static void selectDateFromDatePiker(By locator, String Date) throws InterruptedException {
		String[] splitdate = Date.split("-");
		String date = splitdate[0];
		String month = splitdate[1];
		String year = splitdate[2];
		WebElement element = driver.findElement(locator);
		element.click();
		Select monthDropdown = new Select(driver.findElement(By.cssSelector("select.ui-datepicker-month")));
		monthDropdown.selectByVisibleText(month);
		Thread.sleep(3000);
		Select yearDropDown = new Select(driver.findElement(By.cssSelector("select.ui-datepicker-year")));
		yearDropDown.selectByVisibleText(year);
		driver.findElement(By.linkText(date)).click();
	}
	
	public static void mouseActions(By locator1, By locator2) {
		Actions action = new Actions(driver);
		WebElement element1 = driver.findElement(locator1);
		action.moveToElement(element1).build().perform();
		WebElement element2 = driver.findElement(locator2);
		element2.click();
	}
}
