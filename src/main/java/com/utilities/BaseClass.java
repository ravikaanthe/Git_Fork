package com.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import bsh.Capabilities;

public class BaseClass {
	public static Properties Param = null;
	public static WebDriver driver = null;
	static boolean state;

	/**
	 * Initializes the driver
	 */

	@BeforeTest
	public void loadWebBrowser() throws Exception {
		try {
			Param = HelperClass.readParam();
		} catch (FileNotFoundException fnd) {
			System.out.println("Param.properties does not exist in the path");
			throw (fnd);
		} catch (IOException io) {
			System.out.println("Unable to read Param.properties file");
			throw (io);
		}

		if (Param.getProperty("browser").equalsIgnoreCase("Firefox")) {
			try {

				

				//Path to the Geck driver
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/BrowserDrivers/" + Param.getProperty("os") + "/geckodriver");

				//Launch Firfox Binary to start with --headless
				FirefoxBinary firefoxBinary = new FirefoxBinary();
				firefoxBinary.addCommandLineOptions("-headless");
				
				//Firefox Options to start Headless browser
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.setBinary(firefoxBinary);

				//Initialize the Firefox Driver
			driver = new FirefoxDriver(firefoxOptions);

				System.out.println("Firefox Driver Instance loaded successfully.");
			}

			catch (WebDriverException wde) {
				System.out.println("Firefox driver cannot be instantiated" + wde.toString());
				throw (wde);
			} catch (Exception ioe) {
				System.out.println("Chrome driver instance failed..." + ioe.toString());
				throw (ioe);
			}
		} else if (Param.getProperty("browser").equalsIgnoreCase("Chrome")) {
			try {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/BrowserDrivers/"
						+ Param.getProperty("os") + "/chromedriver");
				
				ChromeOptions options = new ChromeOptions();
                options.addArguments("headless");
                options.addArguments("window-size=1200x600");
                driver = new ChromeDriver(options);
                
				//driver = new ChromeDriver();
				System.out.println("Chrome Driver Instance loaded successfully.");
			}

			catch (WebDriverException wde) {
				System.out.println("Chrome driver cannot be instantiated" + wde.toString());
				throw (wde);
			} catch (Exception ce) {
				System.out.println("Chrome driver instance failed" + ce.toString());
				throw (ce);
			}

		}

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		//driver.manage().window().maximize();
		driver.get(Param.getProperty("siteURL"));
	}

	/**
	 * Quit the browser & make the objects null
	 */

	@AfterTest
	public void closeWebBrowser() {

		//		
		//		  if (driver != null & Param != null) { driver.quit(); driver = null; Param =
		//		  null; }

	}
}
