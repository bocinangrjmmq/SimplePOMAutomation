package app.common;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Explicit wait for elements returning a boolean value
 * 
 * @param webEl
 * @param timeout
 * @return boolean
 */
public class ExplicitWaitFunctions {
	WebDriver driver;

	private static final Logger log = LogManager.getLogger(ExplicitWaitFunctions.class.getName());

	public ExplicitWaitFunctions(WebDriver driver) {
		this.driver = driver;
	}

	public ExplicitWaitFunctions() {
	}

	/**
	 * Sets the amount of time in SECONDS to wait for a page load to complete before returns false.
	 * 
	 * If the timeout is negative, page loads can be indefinite.
	 * 
	 * @param timeout
	 * @return Boolean value if the page finished loading in the defined timeout.
	 */
	public boolean waitForPageToLoad(int timeout) {
		try {
			log.info("Waiting for page to load:: " + timeout + " seconds...");
			driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
			return true;

		} catch (Exception e) {
			log.error("The page didn't finished loading... "+ e);
			return false;
		}
	}
	
	/**
	 * 
	 *  Method to wait for the page to return "readyState" using JavaScript
	 * 
	 * @param webEl
	 * @param attribute
	 * @param value
	 * @param timeout
	 * @return 
	 */
	public boolean javaScriptWaitWholePageToLoad(int timeout) {
		try {
			log.info("Waiting for max:: " + timeout + " seconds for the Page to be visible");
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until((ExpectedCondition<Boolean>) wd ->
			   ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));			
			
			log.info("The Page was loaded");
			return true;
			
		} catch (Exception e) {
			log.debug("The page didn't load " + e);
			return false;
		}

	}

	/**
	 * Method to wait for the URL to contain and specific string
	 * 
	 * @param webEl
	 * @param timeout
	 * @return
	 */
	public boolean waitForURLToContains(String expString, int timeout) {
		if (expString != null) {
			try {
				log.info("Waiting for max:: " + timeout + " seconds for element " + expString
						+ " to be present in the URL");
				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.urlContains(expString));
				log.info("The URL " + expString + " is present in the URL");
				return true;

			} catch (Exception e) {
				log.debug("The URL doesn't contains " + expString + e);
				return false;
			}
		} else
			log.error("The String is null");
		return false;
	}
	
	/**
	 * Method to wait for the WebElement to contain and specific string
	 * 
	 * @param webEl
	 * @param timeout
	 * @return boolean
	 */
	public boolean waitForElementToContain(WebElement webEl,String expString, int timeout) {
		if (expString != null) {
			try {
				log.info("Waiting for max:: " + timeout + " seconds for string " + expString
						+ " to be present in the Element");
				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.textToBePresentInElement(webEl, expString));
				log.info("The element contains: " + expString);
				return true;

			} catch (Exception e) {
				log.debug("The Element doesn't contains: " + expString + e);
				return false;
			}
		} else
			log.error("The String is null");
		return false;
	}

	/**
	 * Method to wait for the visibility of a WebElement
	 * 
	 * @param webEl - The WebElement 
	 * @param timeout - Time in SECONDS
	 * @return Boolean value - Returns false if the element is not visible after the timeout.
	 */
	public boolean waitForVisibilityOfElement(WebElement webEl, int timeout) {
		if (webEl != null) {

			try {
				log.info("Waiting for max:: " + timeout + " seconds for element " + webEl.toString() + " to be visible");

				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.visibilityOf(webEl));
				log.info("Element " + webEl.toString() + " is present");
				return true;

			} catch (Exception e) {
				log.debug("Element " + webEl.toString() + " is not present " + e);
				return false;
			}
		} else
			log.error("Element is null");
		return false;
	}

	/**
	 * Method to wait for the visibility of an Element
	 * 
	 * @param xpath - The element's xPath 
	 * @param timeout - Time in SECONDS 
	 * @return Boolean value - Returns false if the element is not visible after the timeout.
	 */
	public boolean waitForVisibilityOfElement(String xpath, int timeout) {
		WebElement webEl = driver.findElement(By.xpath(xpath));

		if (webEl != null) {

			try {
				log.info("Waiting for max:: " + timeout + " seconds for element " + webEl.toString() + " to be visible");

				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.visibilityOf(webEl));
				log.info("Element " + webEl.toString() + " is present");
				return true;

			} catch (Exception e) {
				log.debug("Element " + webEl.toString() + " is not present " + e);
				return false;
			}
		} else
			log.error("Element is null");
		return false;
	}

	/**
	 * Looks for a WebElement repeatedly by pooling intervals until timeout happens or until the object is found.
	 * 
	 * @param webEl - The WebElement
	 * @param timeout - Time in SECONDS
	 * @param polling - Time in SECONDS
	 * 
	 * @return Boolean value - Returns false if the element is not visible after the timeout.
	 */
	public boolean fluentWaitForVisibilityOfElement(WebElement webEl, int timeout, int polling) {
		if (webEl != null) {

			WebElement webEl2 = webEl;
			try {

				log.info("Waiting for max: " + timeout + " seconds for element " + webEl2.toString()
				+ " with polling of " + polling + " to be visible");

				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS)
						.pollingEvery(polling, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
				wait.until(ExpectedConditions.visibilityOf(webEl2));
				log.info("Element " + webEl2.toString() + " is present");
				return true;

			} catch (Exception e) {
				log.debug("Element " + webEl2.toString() + " is not present " + e);
				return false;
			}
		} else
			log.error("Element is null");
		return false;
	}

	/**
	 * Looks for a WebElement repeatedly by pooling intervals until timeout happens or until the object is found.
	 * 
	 * @param webEl - The element's xPath
	 * @param timeout - Time in SECONDS
	 * @param polling - Time in SECONDS
	 * 
	 * @return Boolean value - Returns false if the element is not visible after the timeout.
	 */
	public boolean fluentWaitForVisibilityOfElement(String xpath, int timeout, int polling) {		
		WebElement webEl = driver.findElement(By.xpath(xpath));
		if (webEl != null) {
			webEl = driver.findElement(By.xpath(xpath));

			try {
				log.info("Waiting for max: " + timeout + " seconds for element " + webEl.toString()
				+ " with polling of " + polling + " to be visible");

				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS)
						.pollingEvery(polling, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
				wait.until(ExpectedConditions.visibilityOf(webEl));
				log.info("Element " + webEl.toString() + " is present");
				return true;

			} catch (Exception e) {
				log.debug("Element " + webEl.toString() + " is not present " + e);
				return false;
			}
		} else
			log.error("Element is null");
		return false;
	}

	/**
	 * Looks for a List of WebElements repeatedly by pooling intervals until timeout happens or until the object is found.
	 * 
	 * @param webEl - List/collection of WebWlement
	 * @param timeout - Time in SECONDS
	 * @param polling - Time in SECONDS
	 * 
	 * @return Boolean value - Returns false if the whole List of elements is not visible after the timeout.
	 */
	public boolean fluentWaitForVisibilityOfElements(List<WebElement> webEl, int timeout, int polling) {
		if (webEl != null) {
			List<WebElement> webEl2 = webEl;
			try {

				log.info("Waiting for max: " + timeout + " seconds for element " + webEl2.toString()
				+ " with polling of " + polling + " to be visible");

				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS)
						.pollingEvery(polling, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
				wait.until(ExpectedConditions.visibilityOfAllElements(webEl2));
				log.info("Elements " + webEl2.toString() + " are present");
				return true;

			} catch (Exception e) {
				log.debug("Elements " + webEl2.toString() + " are not present " + e);
				return false;
			}
		} else
			log.error("Elements are null");
		return false;
	}

	/**
	 * Method to wait for the visibility of a group of Elements
	 * 
	 * @param webEl - List/collection of WebWlement
	 * @param timeout - Time in SECONDS
	 * @return Boolean value - Returns false if either one element of the List is still visible after the timeout or true when all elements are not visible anymore
	 */
	public boolean waitForInvisibilityOfElements(List<WebElement> allContainers, int timeout) {
		if (allContainers != null) {

			try {
				log.info("Waiting for max:: " + timeout + " seconds for elements " + allContainers.toString()
				+ " to be available");

				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.invisibilityOfAllElements(allContainers));
				log.info("Elements " + allContainers.toString() + " are present");
				return true;

			} catch (Exception e) {
				log.debug("Elements " + allContainers.toString() + " are not present " + e);
				return false;
			}
		} else
			log.error("Elements are null");
		return false;

	}
	
	/**
	 * Method to wait for the visibility of an Element with no logs
	 * 
	 * @param webEl - The WebElement 
	 * @param timeout - Time in SECONDS
	 * @return Boolean value - Returns false if the element is not visible after the timeout.
	 */
	public boolean waitForVisibilityOfElementNoLogs(WebElement webEl, int timeout) {
		if (webEl != null) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.visibilityOf(webEl));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else
			log.error("Element is null");
		return false;
	}

	/**
	 * Method to wait for the visibility of an Element with no logs
	 * 
	 * @param webEl - The element's xPath 
	 * @param timeout - Time in SECONDS
	 * @return Boolean value - Returns false if the element is not visible after the timeout.
	 */
	public boolean waitForVisibilityOfElementNoLogs(String xpath, int timeout) {
		WebElement webEl = driver.findElement(By.xpath(xpath));

		if (webEl != null) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.visibilityOf(webEl));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else
			log.error("Element is null");
		return false;
	}

	/**
	 * Method to wait for the invisibility of an Element
	 * 
	 * @param webEl - The WebElement 
	 * @param timeout - Time in SECONDS
	 * @return Boolean value - Returns false if the element is not visible after the timeout.
	 */
	public boolean waitForElementNotDisplayed(WebElement webEl, int timeout) {
		if (webEl != null) {
			Long startTime = System.currentTimeMillis();

			try {
				log.info("Waiting for max:: " + timeout + " seconds for element " + webEl.toString()
				+ " to not be Displayed");

				while (System.currentTimeMillis() - startTime < timeout * 1000 && webEl.isDisplayed()) {

					log.info("Element " + webEl.toString() + " is not longer Displayed");
					return true;
				}

			} catch (Exception e) {
				log.debug("Element " + webEl.toString() + " is Displayed " + e);
				return false;
			}
		} else
			log.error("Element is null");
		return false;
	}

	/**
	 * Method to wait for the invisibility of an Element
	 * 
	 * @param webEl - The WebElement 
	 * @param timeout - Time in SECONDS
	 * @return Boolean value - Returns false if the element is not Invisible after the timeout or true when elements is not visible anymore
	 */
	public boolean waitForInvisibilityOfElement(WebElement webEl, int timeout) {
		if (webEl != null) {

			try {
				log.info("Waiting for max:: " + timeout + " seconds for element " + webEl.toString()
				+ " to not be present");

				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.invisibilityOf(webEl));
				log.info("Element " + webEl.toString() + " is not longer present");
				return true;

			} catch (Exception e) {
				log.debug("Element " + webEl.toString() + " is present " + e);
				return false;
			}
		} else
			log.error("Element is null");
		return false;
	}

	/**
	 * Method to wait for the invisibility of an Element
	 * 
	 * @param webEl - The element's xPath 
	 * @param timeout - Time in SECONDS
	 * @return Boolean value - Returns false if the element is not Invisible after the timeout or true when elements is not visible anymore
	 */
	public boolean waitForInvisibilityOfElement(String xpath, int timeout) {
		WebElement webEl = driver.findElement(By.xpath(xpath));

		if (webEl != null) {

			try {
				log.info("Waiting for max:: " + timeout + " seconds for element " + webEl.toString()
				+ " to not be present");

				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.invisibilityOf(webEl));
				log.info("Element " + webEl.toString() + " is not longer present");
				return true;

			} catch (Exception e) {
				log.debug("Element " + webEl.toString() + " is present " + e);
				return false;
			}
		} else
			log.error("Element is null");
		return false;
	}

	/**
	 * Method to wait for an Element to be Clickable
	 * 
	 * @param xpath - The WebElement 
	 * @param timeout - Time in SECONDS 
	 * @return Boolean value - Returns false if the element is not yet clickable nor enabled.
	 */
	public boolean waitForElementToBeClickable(WebElement webEl, int timeout) {
		if (webEl != null) {

			try {
				log.info("Waiting for max:: " + timeout + " seconds for element " + webEl.toString()
				+ " to be clickable");

				WebDriverWait wait = new WebDriverWait(driver, timeout);

				wait.until(ExpectedConditions.elementToBeClickable(webEl));
				log.info("Element " + webEl.toString() + " is clickable");
				return true;

			} catch (Exception e) {
				log.debug("Element " + webEl.toString() + " is not clickable " + e);
				return false;
			}
		} else
			log.error("Element is null");
		return false;

	}

	/**
	 * Method to wait for an Element to be Clickable
	 * 
	 * @param xpath - The element's xPath 
	 * @param timeout - Time in SECONDS 
	 * @return Boolean value - Returns false if the element is not yet clickable not enabled.
	 */
	public boolean waitForElementToBeClickable(String xpath, int timeout) {
		WebElement webEl = driver.findElement(By.xpath(xpath));

		if (webEl != null) {

			try {
				log.info("Waiting for max:: " + timeout + " seconds for element " + webEl.toString()
				+ " to be clickable");

				WebDriverWait wait = new WebDriverWait(driver, timeout);

				wait.until(ExpectedConditions.elementToBeClickable(webEl));
				log.info("Element " + webEl.toString() + " is clickable");
				return true;

			} catch (Exception e) {
				log.debug("Element " + webEl.toString() + " is not clickable " + e);
				return false;
			}
		} else
			log.error("Element is null");
		return false;

	}

	/**
	 * Method to wait for an Alert to be Present.
	 * 
	 * @param timeout in SECONDS
	 */
	public boolean waitForAlertToBePresent(int timeout) {
		try {
			new WebDriverWait(driver, timeout)
			.ignoring(NoAlertPresentException.class)
			.until(ExpectedConditions.alertIsPresent());
			log.debug("Alert is present ");
			return true;

		} catch (NoAlertPresentException noAlert) {
			noAlert.getMessage();
			log.error("Alert is not present " + noAlert.getMessage());
			return false;
		}
	}

	public boolean isAlertPresent()	{ 
	    try { 
	        driver.switchTo().alert(); 
	        return true; 
	    } catch (NoAlertPresentException Ex) { 
	        return false; 
	    } 
	}


	/**
	 * Method to wait for the Frame to be available and switch to it
	 * 
	 * @param webEl
	 * @param timeout
	 * @return boolean
	 */
	public boolean waitForFrameAndSwitchToIt(WebElement webEl, int timeout) {
		if (webEl != null) {

			try {
				log.info(
						"Waiting for max:: " + timeout + " seconds for the frame " + webEl.toString() + " to be visible");

				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(webEl));
				log.info("Frame " + webEl.toString() + " is present");
				return true;

			} catch (Exception e) {
				log.debug("Frame " + webEl.toString() + " is not present " + e);
				return false;
			}
		} else
			log.error("Frame is null");
		return false;
	}
	
	/**
	 * Method to wait for the Frame to be available and switch to it
	 * 
	 * @param frameName
	 * @param timeout
	 * @return boolean
	 */
	public boolean waitForFrameAndSwitchToIt(String frameName, int timeout) {
		if (frameName != null) {

			try {
				log.info(
						"Waiting for max:: " + timeout + " seconds for the frame: " + frameName + " to be visible");

				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
				log.info("Frame " + frameName + " is present");
				return true;

			} catch (Exception e) {
				log.debug("Frame " + frameName + " is not present " + e);
				return false;
			}
		} else
			log.error("Frame is null");
		return false;
	}

	/**
	 * Method to wait for the visibility of an Element
	 * 
	 * @param xpath - The element's xPath 
	 * @param timeout - Time in SECONDS 
	 * @return Boolean value - Returns false if the element is not visible after the timeout.
	 */
	public boolean waitForVisibilityOfElements(List<WebElement> webEl, int timeout) {
		if (webEl != null) {

			try {
				log.info("Waiting for max:: " + timeout + " seconds for list of elements " + webEl.toString() + " to be visible");
				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.visibilityOfAllElements(webEl));
				log.info("Elements " + webEl.toString() + " are present");
				return true;

			} catch (Exception e) {
				log.debug("Elements " + webEl.toString() + " are not present " + e);
				return false;
			}
		} else
			log.error("List of Elements is null");
		return false;
	}
}


