package app.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptFunctions {

	WebDriver driver;
	private static final Logger log = LogManager.getLogger(JavaScriptFunctions.class.getName());

	/*** 
	 * JSF Constructor
	 * @param WebDriver
	 */
	public JavaScriptFunctions(WebDriver driver) {
		this.driver = driver;
	}

	/*** 
	 * Highlight web element
	 * @param WebElement
	 */
	public void shadeElem(WebElement wElement) {
		//WebElement wElement = driver.findElement(By.xpath(elementXPath));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: #FDFF47; border: 2px solid #000000;');",
				wElement);
		try {
			Thread.sleep(500);
//			Thread.sleep(300);
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		//js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", wElement);
		js.executeScript("arguments[0].setAttribute('style','border: solid 1px white');", wElement);
	}

	/*** 
	 * Scroll / move to a web element
	 * @param WebElement
	 */
	public void scrollToElement(WebElement element) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", element);

			log.info("Scrolling the element "+ element+ " into view");
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	/***
	 * Scrolls to the right side of the page till the Element comes into view.
	 * @param WebElement
	 */
	public void scrollTillElementRight(WebElement element) {
		//JavascriptExecutor jse = (JavascriptExecutor) driver;
		// Scroll to div's most right:
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollRight = arguments[0].scrollWidth", element);

		log.info("Scrolled to element "+ element );

	}

	/***
	 * Scrolls to the left side of the page until the Element comes into view.
	 * @param WebElement
	 */
	public void scrollTillElementLeft(WebElement element) {
		//JavascriptExecutor jse = (JavascriptExecutor) driver;
		// Scroll to div's most right:
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth", element);

		log.info("Scrolled to element "+ element );

	}

	/***
	 * Scrolls to the right side of the page 7000 pixels
	 */
	public void scrollRight() {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(7000,0)");
	}

	/***
	 * Scrolls to the left side of the page 7000 pixels
	 */
	public void scrollLeft() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(-7000,0)");
	}

	/***
	 * Scrolls to the top of the page
	 */
	public void scrollUp() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,0)", "");
		log.info("Scrolling up");
	}

	/***
	 * Scrolls down page 700 pixels
	 */
	public void scrollDown() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		//jse.executeScript("window.scrollBy(0,250)", "");
		jse.executeScript("scroll(0, 900);");
		log.info("Scrolling down");
	}

	/***
	 * Scroll till end of the page
	 */
	public void scrollToBottom() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		log.info("Scrolling till end of the page");
	}

	/**
	 * Executes JavaScript in the context to click on a WebElement
	 * @param element - WebElement
	 */
	public void clickOnElement(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);

		log.info("Element" + element +" clicked");

	}

	/**
	 * Blur out from a WebEl
	 * @param webEl
	 */
	public void blur(WebElement webEl) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].focus(); arguments[0].blur(); return true", webEl);

		log.info("Blurring out from element " + webEl);
	}


}
