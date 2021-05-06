package app.common;

/***
 * @author Eduardo Alvarez 
 * @editor Jorge Morales
 */

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;


public class SeleniumActions {
	WebDriver driver;
	Actions actions;
	ExplicitWaitFunctions exwait;
	JavaScriptFunctions jsf;
	private static final Logger log = LogManager.getLogger(SeleniumActions.class.getName());

	public SeleniumActions(WebDriver driver) {
		this.driver = driver;
		exwait = new ExplicitWaitFunctions(driver);
		jsf = new JavaScriptFunctions(driver);
	    actions = new Actions(driver);
	}

	
	/**
	 * 
	 * This Method Clicks Ctrl+S and then Enter
	 *
	 * @throws Throwable  
	 * 
	 */
	public void saveFileRobot() throws  Throwable {
		Thread.sleep(10000);
		Robot robot = new Robot();
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_S);
		log.info("The Click Ctrl+S ");
		//Thread.sleep(5000);
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		log.info("The Click Enter ");
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_S);
		log.info("The release Ctrl+S ");
		robot.keyRelease(KeyEvent.VK_ENTER);
		log.info("The release Enter");
		Thread.sleep(11000);
		log.info("The Click Ctrl+S and Enter ");
	}
	
	/**
	 * 
	 * This Method double clicks on a selected WebElement
	 * 
	 * @param webE
	 */
	public void doubleClickSelectedElement(WebElement webE) {
		actions.keyDown(Keys.LEFT_CONTROL)
	    .click(webE)
	    .keyUp(Keys.LEFT_CONTROL)
	    .doubleClick(webE)
	    .build()
	    .perform();
		log.info("The Double click on the WebElement: "+ webE);
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This Method returns the trimmed text from a WebElement
	 * 
	 * @param webEl
	 * @return string
	 */
	public String getElementText(WebElement webEl) {
		String text = webEl.getText().trim();
		log.info("The text of the WebElement: "+ webEl +" is : "+text);
		return text;
	}
	
	/**
	 * This Method returns the trimmed text from a WebElement
	 * 
	 * @param webEl
	 * @return string
	 */
	public String getElementText(WebElement webEl, int timeout) {
		Assert.assertTrue(
				exwait.waitForVisibilityOfElement(webEl, Constants.EXPLICIT_WAIT_BSN_MIDWAIT));
		jsf.shadeElem(webEl);
		String text = webEl.getText().trim();
		log.info("The text of the WebElement: "+ webEl +" is : "+text);
		return text;
	}
	
	/**
	 * This Method returns the trimmed text from a WebElement with no logs
	 * 
	 * @param webEl
	 * @return string
	 */
	public String getElementTextNoLogs(WebElement webEl) {
		String text = webEl.getText().trim();
		return text;
	}
	
	/**
	 * This Method returns the trimmed text from a textbox WebElement
	 * 
	 * @param webEl
	 * @return string
	 */
	public String getInnerText(WebElement webEl) {
		String text = webEl.getAttribute("value");
		log.info("The text of the WebElement: " + webEl + " is : " + text);
		return text;
	}
	
	/**
	 * Get the tag name of this element.
	 * E.g. "input"
	 * 
	 * @param webEl - The WebElement
	 * 
	 * @return Tag of the webEl
	 */
	public String getTagName(WebElement webEl) {
		String tagName = webEl.getTagName();
		return tagName;
	}

	/**
	 * Gets the Value of the attribute "class"
	 * 
	 * @param webEl - The WebElement
	 * @return Value of the attribute "class"
	 */
	public String getClass(WebElement webEl) {
		String className = webEl.getAttribute("class");
		return className;
	}

	/**
	 * Gets the Value of the given attribute
	 * 
	 * @param webEl - The WebElement
	 * @param attributeName
	 * @return Value of the attribute
	 */
	public String getAttributeValue(WebElement webEl, String attributeName) {
		String ariaRequired = webEl.getAttribute(attributeName);
		return ariaRequired;
	}

	/**
	 * Gets the Value of the attribute "required"
	 * 
	 * @param webEl - The WebElement
	 * @return Value of the attribute "required"
	 */
	public String getRequired(WebElement webEl) {
		String required = webEl.getAttribute("required");
		return required;
	}

	/**
	 * 
	 * This Method clicks on a WebElement
	 * 
	 * @param webEl
	 */
	public void clickOnElement(WebElement webEl) {
		Assert.assertTrue(exwait.javaScriptWaitWholePageToLoad(Constants.EXPLICIT_WAIT_BSN_LONGWAIT));
		//Assert.assertTrue(exwait.waitForVisibilityOfElement(webEl, Constants.EXPLICIT_WAIT_BSN_LONGWAIT));
		Assert.assertTrue(exwait.waitForElementToBeClickable(webEl, Constants.EXPLICIT_WAIT_BSN_LONGWAIT));
		String webElName = webEl.toString();
		webEl.click();
		log.info("The Click on a WebElement: "+ webElName );
	}

	public void clickOnElementFromList(List<WebElement> webEl, String elementText) {
		for (WebElement elem : webEl) {
			String elemText = this.getElementText(elem);
			if(elementText.equalsIgnoreCase(elemText)) {
				jsf.shadeElem(elem);
				this.clickOnElement(elem);
				break;
			}
		}
	}
	
	/**
	 * 
	 * This Method verifies if a radio button WebElement is selected
	 * 
	 * @param webEl
	 * @return boolean
	 */
	public boolean radioButtonIsSelected(WebElement webEl) {
		Boolean isSelected = webEl.isSelected();
		log.info("The WebElement: "+ webEl + " is selected = "+ isSelected );
		return isSelected;		
	}
	
	/**
	 * 
	 * This Method Send a String to a WebElement
	 * 
	 * @param webEl
	 * @param text
	 */
	public void sendTextToElement(WebElement webEl, String text) {
		webEl.clear();
		webEl.sendKeys(text);
		log.info("Send the text to the WebElement: "+ webEl  );		
	}
	
	/**
	 * 
	 * This Method Send a String to a WebElement
	 * 
	 * @param webEl
	 * @param text
	 */
	public void sendTextToElementNoClear(WebElement webEl, String text) {
		webEl.sendKeys(text);
		log.info("Send the text to the WebElement: "+ webEl  );		
	}
	
	/**
	 * 
	 * This method returns all the WebElements from a dropdown
	 * 
	 * @param webEl
	 * @return List<WebElement>
	 */
	public List<WebElement> returnOptionsFromDropDown(WebElement webEl) {
		Select dropdown = new Select(webEl);
		List<WebElement> options = dropdown.getOptions();
		return options;
	}
	
	/**
	 * 
	 * This method Selects an option based on the value
	 * 
	 * @param webEl
	 * @param option
	 */
	public  void selectOneOptionFromDropDownByValue(WebElement webEl, String option) {
		Select dropdown = new Select(webEl);
		dropdown.selectByValue(option);
		log.info("The option: "+ option + " was selected from the dropdown: "+webEl);
	}
	
	/**
	 * 
	 * This method Selects an option based on the value
	 * 
	 * @param webEl
	 * @param option
	 */
	public  void selectOneOptionFromDropDownByText(WebElement webEl, String optionText) {
		Select dropdown = new Select(webEl);
		dropdown.selectByVisibleText(optionText);
		log.info("The option: "+ optionText + " was selected from the dropdown: "+webEl);
	}
	
	/**
	 * 
	 * This method Selects an option based on the index
	 * 
	 * @param webEl
	 * @param index
	 */
	public  void selectOneOptionFromDropDownByIndex(WebElement webEl, int index) {
		Select dropdown = new Select(webEl);
		dropdown.selectByIndex(index);
		log.info("The index: "+ index + " was selected from the dropdown: "+webEl);
	}
	
	/**
	 * 
	 * This method returns all the Options converted to Strings from a dropdown
	 * 
	 * @param webEl
	 * @return List<String>
	 */
	public List<String> returnOptionsFromDropDownString(WebElement webEl) {
		Select dropdown = new Select(webEl);
		List<WebElement> options = dropdown.getOptions();
		List<String> optionsString = new ArrayList<String>();
		for (int i = 0; i <= options.size() - 1; i++) {
			String optString = options.get(i).getText();
			optionsString.add(i, optString);
		}
		return optionsString;
	}
	
	
	public String concatStringList(List<String> list, String delimiter) {
		String conStr = String.join(delimiter, list);
		log.info("The String is : " + conStr);
		return conStr;
	}
	
	public List<String> getTheTextFromAListWebElements(List<WebElement> list) {
		List<String> optionsString = new ArrayList<String>();
		for (int i = 0; i <= list.size() - 1; i++) {
			String str = getElementTextNoLogs(list.get(i));
			optionsString.add(i, str);
		}
		return optionsString;
	}
	
	public List<String> getTheTextFromASelectedWebElements(List<WebElement> list) {
		List<String> optionsString = new ArrayList<String>();
		int j=0;
		for (int i = 0; i <= list.size() - 1; i++) {
			if (list.get(i).isSelected()) {
				String str = getElementTextNoLogs(list.get(i));
				optionsString.add(j, str);
				j++;
			}
		}
		return optionsString;
	}

	public String getCurrentURL() {
		String currentURL=driver.getCurrentUrl();
		log.info("The Current URL is : " + currentURL);
		return currentURL;
	}
	
}
