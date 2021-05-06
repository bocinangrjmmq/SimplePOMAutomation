package app.common;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class SupportValidations {
	WebDriver driver;
	SeleniumActions selact;
	ExplicitWaitFunctions exwait;
	JavaScriptFunctions jsf;
	private static final Logger log = LogManager.getLogger(SupportValidations.class.getName());

	public SupportValidations(WebDriver driver) {
		this.driver = driver;
		exwait = new ExplicitWaitFunctions(driver);
		selact = new SeleniumActions(driver);
		jsf = new JavaScriptFunctions(driver);
	}

	/**
	 * 
	 * This method verifies that a WebElement is present
	 * 
	 * @param webEl
	 *
	 */
	public void verifyElementExists(WebElement webEl, int timeout) {
		Assert.assertTrue(exwait.waitForVisibilityOfElement(webEl, timeout));
		jsf.shadeElem(webEl);
		log.info("The WebElement: " + webEl + " is present");
	}


	/**
	 * 
	 * This method verifies that a WebElement has the right Header
	 * 
	 * @param actualStr
	 * @param expectedStr
	 */
	public void verifyStringContains(String actualStr, String expectedStr) {
		log.info("The Actual String is: " +actualStr +" and contains the Expected String: " + expectedStr);
		Assert.assertTrue(actualStr.toLowerCase().contains(expectedStr.toLowerCase()));
	}
	
	public void verifyStringIsNull(String expectedStr) {
		Assert.assertNull(expectedStr);
	}
	
	public void verifyStringNotEquals(String actualStr, String expectedStr) {
		Assert.assertFalse(actualStr.toLowerCase().contains(expectedStr.toLowerCase()));
	}

	/**
	 * 
	 * This method verifies that a WebElement has the right Header
	 * 
	 * @param wElement
	 * @param expectedHeader
	 * @param timeout
	 */
	public void verifyPageHeader(WebElement wElement, String expectedHeader, int timeout) {
		Assert.assertTrue(
				exwait.waitForVisibilityOfElement(wElement, timeout));
		jsf.shadeElem(wElement);
		String str = selact.getElementText(wElement);

		log.info("The Actual Header is: " + str + " The Expected Header is: " + expectedHeader);
		Assert.assertEquals(str, expectedHeader);
	}

	/**
	 * 
	 * This method verifies that a WebElement is equal to an expected value
	 * 
	 * @param wElement
	 * @param expectedText
	 * @param timeout
	 */
	public void verifyElementIsEqual(WebElement wElement, String expectedText, int timeout) {
		Assert.assertTrue(
				exwait.waitForVisibilityOfElement(wElement, timeout));
		jsf.shadeElem(wElement);
		String str = selact.getElementText(wElement);

		log.info("The Actual element is: " + str + " The Expected element is: " + expectedText);
		Assert.assertEquals(str, expectedText);
	}

	/**
	 * 
	 * This method verifies that a WebElement is equal to an expected value
	 * 
	 * @param wElement
	 * @param expectedText
	 * @param timeout
	 */
	public void verifyElementIsEqualFromAttribute(WebElement wElement, String expectedText, String att, int timeout) {
		Assert.assertTrue(
				exwait.waitForVisibilityOfElement(wElement, timeout));
		jsf.shadeElem(wElement);
		String str = selact.getAttributeValue(wElement, att);

		log.info("The Actual element is: " + str + " The Expected element is: " + expectedText);
		Assert.assertEquals(str, expectedText);
	}
	
	/**
	 * 
	 * This method verifies that a WebElement is not empty
	 * 
	 * @param wElement
	 * @param expectedText
	 * @param timeout
	 */
	public void verifyElementIsNotEmptyFromAttribute(WebElement wElement, String att, int timeout) {
		Assert.assertTrue(
				exwait.waitForVisibilityOfElement(wElement, timeout));
		jsf.shadeElem(wElement);
		String str = selact.getAttributeValue(wElement, att);

		log.info("The Actual element is: " + str + " from the attribute: " + att);
		Assert.assertFalse(str.equals(""));
	}

	/**
	 * 
	 * This method verifies that a WebElement contains an expected value
	 * 
	 * @param wElement
	 * @param expectedText
	 * @param timeout
	 */
	public void verifyElementContainsTextFromAttribute(WebElement wElement, String expectedText, String att, int timeout) {
		Assert.assertTrue(
				exwait.waitForVisibilityOfElement(wElement, timeout));
		jsf.shadeElem(wElement);
		String str = selact.getAttributeValue(wElement, att);

		log.info("The Actual element is: " + str + " The Expected element is: " + expectedText);
		Assert.assertTrue(str.contains(expectedText));
	}

	/**
	 * 
	 * This method verifies that a WebElement has the right Header
	 * 
	 * @param wElement
	 * @param expectedHeader
	 */
	public void verifyElementContains(WebElement wElement, String expectedHeader, int timeout) {
		Assert.assertTrue(
				exwait.waitForElementToContain(wElement,expectedHeader, timeout));
		jsf.shadeElem(wElement);
		String str = selact.getElementText(wElement);

		log.info("The Actual text is: " + str );
		Assert.assertTrue(str.contains(expectedHeader));
	}
	
	/**
	 * NEW
	 */
	public void verifySelectedOptionFromSelect(WebElement wSelect, String expectedOption, int timeout) {
		Assert.assertTrue(
				exwait.waitForVisibilityOfElement(wSelect, timeout));
		Select select = new Select(wSelect);
		String str = select.getFirstSelectedOption().getText();

		log.info("The Actual Select option is: " + str + " and contains: " + expectedOption);
		this.verifyStringContains(str, expectedOption);
	}

	/**
	 * 
	 * This method verifies that all the members in a List of WebElements contain a Expected text
	 * 
	 * @param wElement
	 * @param expectedHeader
	 */
	public void verifyListContainsText(List<WebElement> wElement, String expectedHeader) {
		for(WebElement elem:wElement){
			//for (int i = 0; i <= wElement.size() - 1; i++) {
			jsf.shadeElem(elem);
			String str = selact.getElementTextNoLogs(elem);
			Assert.assertTrue(str.contains(expectedHeader));
			log.info("The Actual Element is: " + str + " and contains: " + expectedHeader);
		}
	}

	/**
	 * 
	 * This method verifies that One of the members in a List of WebElements contain a Expected text
	 * 
	 * @param wElement
	 * @param expectedHeader
	 */
	public void verifyListContainsTitle(List<WebElement> wElement, String expectedTitle) {
		for(WebElement elem:wElement){
			//for (int i = 0; i <= wElement.size() - 1; i++) {
			String str = selact.getElementTextNoLogs(elem);
			if (str.contains(expectedTitle)) {
				jsf.shadeElem(elem);
				log.info("The Actual Element is: " + str + " and contains: " + expectedTitle);
				break;
			}
		}
	}

	/**
	 * 
	 * This method verifies that all the members in a List of WebElements contain a Expected text
	 * 
	 * @param wElement
	 * @param expectedHeader
	 */
	public void verifyListExists(List<WebElement> wElements, int timeout) {
		Assert.assertTrue(exwait.waitForVisibilityOfElements(wElements, timeout));
		/*for(WebElement elem : wElement){
			this.verifyElementExists(elem, timeout);
			jsf.shadeElem(elem);
		}*/
	}

	/**
	 * 
	 * This method verifies that one of the members in a List of WebElements contain
	 * a Expected text and breaks after find the element
	 * 
	 * @param wElement
	 * @param expectedText
	 */
	public void verifyOneMemberContainsText(List<WebElement> wElement, String expectedText) {
		for (int i = 0; i <= wElement.size() - 1; i++) {
			jsf.shadeElem(wElement.get(i));
			String str = selact.getElementTextNoLogs(wElement.get(i));

			if (str.contains(expectedText)) {
				Assert.assertTrue(str.contains(expectedText));
				log.info("The Actual Element is: " + str + " and contains: " + expectedText);
				break;
			} else if (!str.contains(expectedText)) {
				log.info("The Actual Element is: " + str + " and don't contains the expected text ");
			} else if (!str.contains(expectedText) && i <= wElement.size() - 1) {
				Assert.assertTrue(str.contains(expectedText));
				log.info("The Actual Element is: " + str + " and don't contains the expected text");
			}
		}
	}

	/**
	 * 
	 * This method verifies that a List of WebElements equals another List
	 * 
	 * @param wElement
	 * @param expectedElement
	 */
	public void verify2ListAreEqual(List<WebElement> wElement, List<String> expectedElement) {
		for (int i = 0; i <= wElement.size() - 1; i++) {
			jsf.shadeElem(wElement.get(i));
			jsf.scrollToElement(wElement.get(i));
			String str = selact.getElementTextNoLogs(wElement.get(i));

			Assert.assertEquals(str, expectedElement.get(i));
			log.info("The Actual Element is: " + str + " and The Expected Element is: " + expectedElement.get(i));
		}
	}

	/**
	 * 
	 * This method verifies the page header and the loggead user on a page
	 * 
	 * @param pageHeader
	 * @param expectedHeader
	 * @param loggedUser
	 * @param expectedUser
	 * @param timeout
	 */
	public void verifyLandingPageWithUser(WebElement pageHeader, String expectedHeader, WebElement loggedUser,
			String expectedUser,String expectedURL ,int timeout) {
		verifyPageHeader(pageHeader, expectedHeader, timeout);
		verifyElementIsEqual(loggedUser, expectedUser, timeout);
		verifyStringContains(selact.getCurrentURL(), expectedURL);
	}

	/**
	 * 
	 * @param webEl
	 * @param webEl1
	 * @param expectedURL
	 * @param timeout
	 */
	public void verifyLandingPageElements(WebElement webEl, WebElement webEl1,
			String expectedURL ,int timeout) {
		verifyElementExists(webEl, timeout);
		verifyElementExists(webEl1, timeout);

		String str1 =expectedURL.split("https://dynamicforms.ngwebsolutions.com/Submit/Form/")[0];
		verifyStringContains(selact.getCurrentURL(), str1);

		//verifyStringContains(selact.getCurrentURL(), expectedURL);
	}

	/**
	 * 
	 * @param webEl
	 * @param actualUser
	 * @param expectedUser
	 * @param expectedURL
	 * @param timeout
	 */

	public void verifyLandingPageWithUser(WebElement webEl, WebElement actualUser,
			String expectedUser,String expectedURL ,int timeout) {
		verifyElementExists(webEl, timeout);
		verifyElementExists(actualUser, timeout);
		String str = selact.getElementText(actualUser);
		verifyStringContains(str, expectedUser);
		String str1 =expectedURL.replace("side_door.do", "");
		str1 = str1.replace("/main/showMain.rdo", "");
		str1 = str1.replace("/Auth/Saml2/66356571", "");
		str1 = str1.replace("/schools/AppCollegeAlumniFund/login", "");
		//		str1 = str1.replace("/query/create/2", "");
		//		verifyStringContains(selact.getCurrentURL().replace("/account/login?ret=L2RhdGFzb3VyY2VzL0FY", ""), str1);
		verifyStringContains(selact.getCurrentURL(), str1);
	}

}