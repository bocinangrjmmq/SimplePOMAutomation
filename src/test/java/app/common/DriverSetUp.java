package app.common;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSetUp {
	
	WebDriver driver;
	protected static ThreadLocal<RemoteWebDriver> threadedDriver = new ThreadLocal<>();
	RemoteWebDriver rdriver;
	String browser;
	ConfigReader config = new ConfigReader();
	private static final Logger log = LogManager.getLogger(DriverSetUp.class.getName());

	public DriverSetUp(String browser) {
		this.browser = browser;
	}

	/**
	 * 
	 * Method to select the driver specified by the user
	 *  
	 * @return Driver
	 */
	public WebDriver driveReturn() {
		System.setProperty("webdriver.chrome.driver",
				"./Drivers/chromedriver.exe");

		//https://github.com/mozilla/geckodriver/releases
		System.setProperty("webdriver.gecko.driver",  
				"./Drivers/geckodriver.exe");


		try {
			if (browser.equalsIgnoreCase("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized");
				options.addArguments("--disable-infobars");
				options.addArguments("--disable-notifications");
//				options.addArguments("--incognito");
//				options.addArguments("--headless");
				
				DesiredCapabilities cap = DesiredCapabilities.chrome(); 
				cap.setCapability("goog:chromeOptions", options); 

				//driver = new ChromeDriver(options);
				driver = new ChromeDriver(cap);

			} 
			else if (browser.equalsIgnoreCase("firefox")) {
				FirefoxOptions options = new FirefoxOptions();
				options.addPreference("browser.download.folderList", 1);
				options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
				options.addPreference("intl.accept_languages","en-us");
				options.addPreference("pdfjs.disabled", true);
				driver =  new FirefoxDriver(options);
			}
		} catch (Exception e) {
			log.error(browser + " is not a valid browser");
			e.printStackTrace();
		}
		return driver;
	}

	/**
	 * 
	 * Method to select the driver to run remotely
	 *  
	 * @return RemoteDriver
	 */
	public RemoteWebDriver driveReturn(String url) {
	//public ThreadLocal<RemoteWebDriver> driveReturn(String url) {
		try {
			if (browser.equalsIgnoreCase("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized");
				options.addArguments("--disable-infobars");
				options.addArguments("--disable-notifications");
				//
				options.addArguments("--disable-gpu");

				DesiredCapabilities cap = DesiredCapabilities.chrome(); 
				cap.setCapability("goog:chromeOptions", options); 
				
				URL u = new URL (url);
				rdriver = new RemoteWebDriver(u, cap);
				//threadedDriver.set(new RemoteWebDriver(u, cap));

			} else if (browser.equalsIgnoreCase("firefox")) {
				DesiredCapabilities cap= DesiredCapabilities.firefox();

				URL u = new URL (url);
				rdriver = new RemoteWebDriver(u, cap);
				//threadedDriver.set(new RemoteWebDriver(u, cap));
			}

			//rdriver.setFileDetector(new LocalFileDetector());
			//rdriver.manage().window().setSize(new Dimension(1382, 754));

		} catch (Exception e) {
			log.error(browser + " is not a valid browser");
			e.printStackTrace();
		}
//		 try {
//	            driver = threadedDriver.get();
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
		return rdriver;
	}


}

