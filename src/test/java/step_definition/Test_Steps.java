package step_definition;

import com.aventstack.extentreports.*;

import app.bsn.BSN_AppName_HomePage;
import app.bsn.BSN_AppLoginPage;
import app.common.BaseTest;
import app.model.User;

import org.apache.logging.log4j.*;
import org.openqa.selenium.WebDriver;


public class Test_Steps extends BaseTest{

	WebDriver driver;
//	String strUser = Credentials.getUsername();
//	String strPass = Credentials.getPassword();

	BSN_AppLoginPage loginPage;
	BSN_AppName_HomePage homePage;

	public Test_Steps(WebDriver driver) {
		this.driver = driver;
		
		loginPage = new BSN_AppLoginPage(driver);
		homePage = new BSN_AppName_HomePage(driver);
	}

	
	public void logInToEnvironment(Integer stepNum, Logger log, ExtentTest test, User user, String env) throws InterruptedException {
		log.info("\n ----------------------------------------------------------------------");
		log.info(" -------------- Step "+ stepNum +" Navigate to App's AppName " + env + " environment");
		test.log(Status.INFO, "Step "+ stepNum + " Navigate to App's AppName "+ env +" environment");
		//driver.manage().window().maximize();
		loginPage.login(strUser, strPass);
		log.info("App Service Automation login screen appears");
		test.pass("App Service Automation login screen appears");
	}
	
	public void impersonateAs(Integer stepNum, Logger log, ExtentTest test, User user) throws Throwable {
		log.info("\n ----------------------------------------------------------------------");
		log.info(" -------------- Step "+ stepNum +" Impersonate an ITIL User: \""+ user.getFullName()+"\"");
		test.log(Status.INFO, "Step "+ stepNum +" Impersonate an ITIL User: \""+ user.getFullName()+"\"");

		homePage.clickOnImpersonateUser();
		homePage.searchImpersonateUser(user.getNetid());

		log.info("\nSystem allows impersonation.");
		homePage.loggedUser(user.getFullName());
		log.info("The top of the screen now states \""+user.getFullName()+"\" in the top right hand corner of the screen.\n");
		test.pass("The top of the screen now states \""+user.getFullName()+"\" in the top right hand corner of the screen.");
	}

	public void logOut(Integer stepNum, Logger log, ExtentTest test) {
		log.info("\n ----------------------------------------------------------------------");
		log.info(" -------------- Step "+ stepNum +" Log out of AppName.");
		test.log(Status.INFO, "Step "+ stepNum +" Log out of AppName.");
		homePage.Logout();

		log.info("User is logged out.");
		test.pass("User is logged out.");
	}

}
