package app.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WebSiteFunctions {

	WebDriver driver;
	private ExplicitWaitFunctions ewaits;
	private final Logger log = LogManager.getLogger(this.getClass().getName());

	public WebSiteFunctions(WebDriver driver) {
		this.driver = driver;
		ewaits = new ExplicitWaitFunctions(driver);
	}

	/*** 
	 * Move to internal frame "gsft_main".
	 * @throws  NoSuchFrameException if the iframe does not exists in the DOM
	 */
	public void frameIn() {
		// Chance to Main Content frame
		try {
			//			WebDriverWait wait = new WebDriverWait(driver, 25);
			//			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("gsft_main"));
			Assert.assertTrue(ewaits.waitForVisibilityOfElement(driver.findElement(By.xpath("//iframe[@id='gsft_main']")), Constants.EXPLICIT_WAIT_BSN_LONGWAIT));
			Thread.sleep(1000);
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='gsft_main']")));
			log.debug("switched to frame: gsft_main");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/*** 
	 * Move to outer/parent frame .
	 */
	public void frameOut() {
		//Go back to main frame
		try {

			//driver.switchTo().parentFrame();
			driver.switchTo().defaultContent();
			log.debug("switched to defaultContent");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Moves the given @id frame.
	 * @param frame
	 */
	public void changeToIFrame(String frame) {
		WebElement iframe = driver.findElement(By.tagName("iframe"));

		if (iframe.getAttribute("id").equals(frame) && iframe != null) {
			// Chance to Main Content frame
			driver.switchTo().frame(frame);
			log.debug("switched to frame: " + frame);
		}
		else {
			//Go back to main frame
			driver.switchTo().defaultContent();
			log.debug("switched to default content");
		}
	}

	/**
	 * Moves the given frame.
	 * @param iframe
	 */
	public void changeToIFrame(WebElement iframe) {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT_BSN_MIDWAIT)
		.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));

		//		WebElement iframe =  driver.findElement(By.className(frame));
		//
		//		if (iframe != null) {
		//			// Chance to Main Content frame
		//			driver.switchTo().frame(iframe);
		//			log.debug("switched to frame: " + frame);
		//		}
		//		else {
		//			//Go back to main frame
		//			driver.switchTo().defaultContent();
		//			log.debug("iframe not found");
		//		}

	}

	/*** 
	 * Reload web page and move to the inner frame
	 */
	public void reloadPage() {
		//Tricked
		try {
			//			Thread.sleep(Constants.THREAD_30_SECONDS);
			Thread.sleep(Constants.THREAD_LONG_SLEEP);
			driver.navigate().refresh();
			log.debug(" - Site reloaded");

			Thread.sleep(Constants.THREAD_LONG_SLEEP);
			frameIn();
			log.debug(" - Moved to inner frame");

		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}

	}

	/** Navigates to the previous page (Click "Back" function)
	 * 
	 */
	public void goBackHomePage() {
		try {
			driver.navigate().back();		
			Thread.sleep(Constants.THREAD_LONG_SLEEP*2);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Click the element of the given xPath.
	 * @param xPath
	 */
	public void clickOnButtonBy(String xPath /*, int waitTime*/) {
		//ewaits.waitForVisibilityOfElement(xPath, waitTime);

		driver.findElement(By.xpath(xPath)).click();
	}

	/**
	 * Click the element of the given WebElement reference.
	 * @param webEl
	 */
	public void clickOnButtonBy(WebElement webEl /*, int waitTime*/) {
		//ewaits.waitForVisibilityOfElement(webEl, waitTime);

		webEl.click();
	}

	/**
	 * Type into the given xPath.
	 * @param webEl
	 * @param keysToSend
	 */
	public void sendKeysBy(String xPath /*, int waitTime*/, String keysToSend) {
		//ewaits.waitForVisibilityOfElement(xPath, waitTime);

		driver.findElement(By.xpath(xPath)).sendKeys(keysToSend);
	}

	/**
	 * Type into the given WebElement reference.
	 * @param webEl
	 * @param keysToSend
	 */
	public void sendKeysBy(WebElement webEl/*, int waitTime*/, String keysToSend) {
		//ewaits.waitForVisibilityOfElement(webEl, waitTime);

		webEl.sendKeys(keysToSend);
		log.debug("Sending Keys : " + keysToSend + " to element : " + webEl);
	}

	/**
	 * Does the action of Mouse Hover over a WebElement.
	 * @param webEl
	 */
	public void mousehover(WebElement webEl) {
		Actions hover = new Actions(driver);
		hover.moveToElement(webEl);
		hover.build();
		hover.perform();

	}


}
