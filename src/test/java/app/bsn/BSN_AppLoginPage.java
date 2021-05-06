package app.bsn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import app.common.ConfigReader;
import app.common.Constants;
import app.common.ExplicitWaitFunctions;
import app.common.JavaScriptFunctions;
import app.common.WebSiteFunctions;

public class BSN_AppLoginPage {
	
	WebDriver driver;
	private JavaScriptFunctions jsf;
	private WebSiteFunctions wsf;
	private static final Logger log = LogManager.getLogger(BSN_AppLoginPage.class.getName());
	static ExplicitWaitFunctions ewaits = new ExplicitWaitFunctions();
	ConfigReader config = new ConfigReader();
	
	public BSN_AppLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); 
        ewaits = new ExplicitWaitFunctions(driver);
        jsf = new JavaScriptFunctions(driver);
        wsf = new WebSiteFunctions(driver);
    }
	
	//--------------------Sandbox--------------------
	@FindBy(xpath = "//input[@id='user_name']")
	private WebElement sandboxTxtUser; 

	@FindBy(xpath = "//input[@id='user_password']")
	private WebElement sandboxTxtPass; 

	@FindBy(xpath = "//button[@id='sysverb_login']")
	private WebElement sandboxBtnLogin;
	//--------------------------------------------	
	//--------------------Test--------------------
	@FindBy(xpath = "//input[@id='user_name']")
	private WebElement txtUser; 

	@FindBy(xpath = "//input[@id='user_password']")
	private WebElement txtPass; 

	@FindBy(xpath = "//button[@id='sysverb_login']")
	private WebElement btnLogin;
	//--------------------------------------------
		
	@FindBy(xpath = "//*[@class='common-button-primary']")
	private WebElement btnOK;
	
	@FindBy(xpath = "//*[@id='dialog-message']/div[2]/input[1]")
	private WebElement btnOut;
	
	@FindBy(xpath = "//*[@id='content-right']/ul[1]/li[1]/a")
	private WebElement lnkLogin;
	
	@FindBy(xpath = "//*[@id='_shib_idp_globalConsent']")
	private WebElement radioThirdOptn;
	
	@FindBy(xpath = "//input[@value=\"Accept\"]")
	private WebElement btnAccept;
	
	//User lastname, firstname element 
	@FindBy(xpath = "//*[@id=\"usernameMenu\"]/span")
	private WebElement lblLogedInAs;
	
	/*** 
	 * Login into AppName side_door login page with credentials
	 * @param strUser - Username
	 * @param strPass - Username's password
	 * @throws InterruptedException 
	 */
	public void login(String strUser, String strPass) {
			Assert.assertTrue(ewaits.javaScriptWaitWholePageToLoad(Constants.EXPLICIT_WAIT_BSN_LONGWAIT));
			Assert.assertTrue(ewaits.waitForVisibilityOfElement(driver.findElement(By.xpath("//iframe[@id='gsft_main']")), Constants.EXPLICIT_WAIT_BSN_LONGWAIT));
			wsf.frameIn();
			
			Assert.assertTrue(ewaits.fluentWaitForVisibilityOfElement(txtUser, Constants.EXPLICIT_WAIT_BSN_SHORTWAIT, 5));
			Assert.assertTrue(ewaits.fluentWaitForVisibilityOfElement(txtPass, Constants.EXPLICIT_WAIT_BSN_SHORTWAIT, 5));
			Assert.assertTrue(ewaits.fluentWaitForVisibilityOfElement(btnLogin, Constants.EXPLICIT_WAIT_BSN_SHORTWAIT, 5));
			txtUser.sendKeys(strUser);
			txtPass.sendKeys(strPass);

			btnLogin.click();
	}
	
	/*** 
	 * @return login full name.
	 */
	public String getLogedFullName() {
		Assert.assertTrue(ewaits.fluentWaitForVisibilityOfElement(lblLogedInAs, Constants.EXPLICIT_WAIT_BSN_SHORTWAIT, 5));
		String actualFullName = lblLogedInAs.getText();
		return actualFullName;
	}
		
	/*** 
	 * Log out from AppName
	 */
	public void logout() {
		try {
		driver.get("https://App.edu/logout");
		driver.manage().deleteAllCookies();
		driver.close();
		
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	/*** 
	 * AppName - SANDBOX : 
	 * Don't ask me again message box.
	 * @deprecated
	 */
	public void clickOnRadioButtonAccept() {
		try {
			Thread.sleep(Constants.THREAD_SHORT_SLEEP);
			Assert.assertTrue(ewaits.fluentWaitForVisibilityOfElement(radioThirdOptn, Constants.EXPLICIT_WAIT_BSN_SHORTWAIT, 10));
			Assert.assertTrue(ewaits.fluentWaitForVisibilityOfElement(btnAccept, Constants.EXPLICIT_WAIT_BSN_SHORTWAIT, 10));

			Thread.sleep(Constants.THREAD_SHORT_SLEEP);
			radioThirdOptn.click();
			Thread.sleep(Constants.THREAD_SHORT_SLEEP);
			btnAccept.click();
			Thread.sleep(Constants.THREAD_SHORT_SLEEP);
			
		}catch (Exception e) {
			log.error(e.getMessage());		
			
		}
	}
			
}
