package app.bsn;

/***
 * @Autor Miguel Morales
 */

import static org.testng.Assert.*;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import app.common.BaseTest;
import app.common.Constants;
import app.common.ExplicitWaitFunctions;
import app.common.JavaScriptFunctions;
import app.common.SeleniumActions;
import app.common.SupportValidations;
import app.common.WebSiteFunctions;

public class BSN_AppName_HomePage {

	WebDriver driver;
	private JavaScriptFunctions jsf;
	private WebSiteFunctions wsf;
	private ExplicitWaitFunctions ewaits;
	private SeleniumActions selact;
	private SupportValidations sva;
	private static final Logger log = LogManager.getLogger(BSN_AppName_HomePage.class.getName());

	public BSN_AppName_HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this); 
		ewaits = new ExplicitWaitFunctions(driver);
		jsf = new JavaScriptFunctions(driver);
		wsf = new WebSiteFunctions(driver);
		selact = new SeleniumActions(driver);
		sva = new SupportValidations(driver);
	}

	/*Elements present in Home Page*/
	//Top bar search
	@FindBy(xpath = "//form[@class='form-inline navpage-global-search ng-non-bindable']//div[@class='input-group-transparent']")
	private WebElement btnTopBarSearch;

	@FindBy(xpath = "//input[@id='sysparm_search']")
	private WebElement txtTopBarSearch; 

	@FindBy(xpath = "//a[@title='Portal home']/span")
	private WebElement txtTitle; 			

	@FindBy(xpath = "//div[@id='nav_west_north']//input[@id='filter']")
	private WebElement txtFilterNavigator;
	
	@FindBy(xpath = "//span[contains(@class,'user-name')]")
	private WebElement btnUsername;			  

	@FindBy(xpath = "//a[@id='glide_ui_impersonator']")
	private WebElement btnImpersonate;          

	@FindBy(xpath = "//span[@id='select2-chosen-2']")
	private WebElement btnSelectImpersonateUser;

	@FindBy(xpath = "//input[@id='s2id_autogen2_search']")
	private WebElement txtSearchImpersonateUser; 

	@FindBy(xpath = "//a[contains(text(),'Logout')]")
	private WebElement btnLogout;

	@FindBy(xpath = "//button[@class='btn btn-link fa fa-close dismiss-notifications ng-scope']")
	private WebElement closeTopUpdateDialogButton;

	@FindBy(xpath = "//div[1]/span[1]/div[1]/div[3]/div[1]/span[1]/a[3]/b[1]")
	private WebElement lblFilterCriteria;

	//Home Page Loading...
	@FindBy(xpath = "//h1[contains(@class,'loading-message')]")
	private WebElement lblLoadingMessage;

	@FindBy(xpath = "//a[@id='allApps_tab']")
	private WebElement btnAllAppsTab;

	//***************************************************
	// TOP BAR 
	//***************************************************

	/*** 
	 *@param String Incident number.
	 * Type the Incident Number in the Top Search box.
	 * Then move to internal frame.
	 */
	public void topBarSearch(String newIncident) {
		wsf.frameOut();
		Assert.assertTrue(ewaits.waitForPageToLoad(Constants.EXPLICIT_WAIT_BSN_MIDWAIT));
		Assert.assertTrue(ewaits.waitForVisibilityOfElement(driver.findElement(By.xpath("//iframe[@id='gsft_main']")), Constants.EXPLICIT_WAIT_BSN_MIDWAIT));
		Assert.assertTrue(ewaits.waitForVisibilityOfElement(btnAllAppsTab, Constants.EXPLICIT_WAIT_BSN_MIDWAIT));
		Assert.assertTrue(ewaits.waitForVisibilityOfElement(btnUsername, Constants.EXPLICIT_WAIT_BSN_MIDWAIT));
		Assert.assertTrue(ewaits.waitForElementToBeClickable(btnTopBarSearch, Constants.CLICKABLE_MIDWAIT));
		wsf.frameIn();
	}

	private void waitForHomePageToLoad() {
		log.debug("Wait for \"Page Loading...\" is not present");
		//Wait for "Page Loading..." is not present
		if (ewaits.waitForVisibilityOfElement(lblLoadingMessage, Constants.EXPLICIT_WAIT_BSN_SHORTWAIT) == true) {
			wsf.frameOut();
			Assert.assertTrue(ewaits.waitForVisibilityOfElement(btnUsername, Constants.EXPLICIT_WAIT_BSN_MIDWAIT));
			jsf.shadeElem(btnUsername);
		}

	}

	/***
	 * @Action Click on the User's name top left button, 
	 * Then Click on "Impersonate User"
	 */
	public void clickOnImpersonateUser()  {
		log.info("Impersonate as ITIL User:");
		wsf.frameOut();
		log.debug("MAIN HOME PAGE LOADING ...");
		Assert.assertTrue(ewaits.waitForPageToLoad(Constants.EXPLICIT_WAIT_BSN_LONGWAIT));//THREAD_30_SECONDS));

		Assert.assertTrue(ewaits.waitForVisibilityOfElement(btnAllAppsTab, Constants.EXPLICIT_WAIT_BSN_LONGWAIT));
		Assert.assertTrue(ewaits.waitForVisibilityOfElement(btnUsername, Constants.EXPLICIT_WAIT_BSN_LONGWAIT));
		jsf.clickOnElement(btnUsername);

		Assert.assertTrue(ewaits.waitForVisibilityOfElement(btnImpersonate, Constants.EXPLICIT_WAIT_BSN_LONGWAIT));
		Assert.assertTrue(ewaits.waitForElementToBeClickable(btnImpersonate, Constants.CLICKABLE_MIDWAIT));

		jsf.clickOnElement(btnImpersonate);
	}

	/***
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Param User's NetID 
	 * Insert the user's netId in the Impersonate User search box.
	 * Click Enter to impersonate as required netid.   
	 */
	public void searchImpersonateUser(String netid) {
		Assert.assertTrue(ewaits.waitForPageToLoad(Constants.EXPLICIT_WAIT_BSN_LONGWAIT));//THREAD_30_SECONDS));
		sva.verifyElementExists(btnSelectImpersonateUser, Constants.EXPLICIT_WAIT_BSN_LONGWAIT);

		sva.verifyElementExists(txtSearchImpersonateUser, Constants.EXPLICIT_WAIT_BSN_LONGWAIT);
		selact.sendTextToElement(txtSearchImpersonateUser, netid);
	}

	/**
	 * @Param Logged In user name value from the top right.
	 */
	public void loggedUser(String expectedFullname)  {
		Assert.assertTrue(ewaits.waitForPageToLoad(Constants.EXPLICIT_WAIT_BSN_LONGWAIT));
		this.waitForHomePageToLoad();

		sva.verifyElementIsEqual(btnUsername, expectedFullname, Constants.EXPLICIT_WAIT_BSN_LONGWAIT);
		log.info("Impersonated as: " + expectedFullname);
	}

	/**
	 * @return String Text of the filter criteria of Works.
	 */
	public void getFilterCriteria(String expectedFilterCriteria) {
		Assert.assertTrue(ewaits.waitForVisibilityOfElement(lblFilterCriteria, Constants.EXPLICIT_WAIT_BSN_SHORTWAIT));
		//jsf.shadeElem(lblFilterCriteria)));

		String filterCriteria = lblFilterCriteria.getText();
		assertTrue(filterCriteria.contains(expectedFilterCriteria));
		log.info("Filter coantains: "+expectedFilterCriteria);
	}

	public void setSearchFilterNavigation(String filter) {
		sva.verifyElementExists(txtFilterNavigator, Constants.EXPLICIT_WAIT_BSN_MIDWAIT);
		selact.sendTextToElement(txtFilterNavigator, filter);
		
	}

	/***
	 * @Action Click on the User's name top left button, 
	 * Then Click on "Impersonate User"
	 */
	public void Logout()  {
		wsf.frameOut();
		Assert.assertTrue(ewaits.waitForVisibilityOfElement(btnUsername, Constants.EXPLICIT_WAIT_BSN_SHORTWAIT));
		wsf.clickOnButtonBy(btnUsername);
		
		Assert.assertTrue(ewaits.waitForVisibilityOfElement(btnLogout, Constants.EXPLICIT_WAIT_BSN_SHORTWAIT));
		wsf.clickOnButtonBy(btnLogout);
	}

	/**
	 * Handle -.Leave Site? - Changes you made may not be saved.
	 * @Action Click Leave button.
	 */
	public void handleLeaveSite() {
		// Handle -.Leave Site? - Changes you made may not be saved.
		if(ewaits.isAlertPresent()) {
			driver.switchTo().alert().accept();
		}
	}
}
