package app.tsd;

/***
 * @author Jorge Miguel Morales.
 */

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import app.bsn.BSN_AppName_HomePage;
import app.bsn.BSN_AppLoginPage;
import app.common.DriverSetUp;
import app.common.UsefulMethods;
import app.common.WebSiteFunctions;
import app.common.__BaseTest;
import step_definition.Test_Steps;


public class AppName_Low_Suite extends __BaseTest {

	WebDriver driver;

	DriverSetUp ds;
	UsefulMethods um;
	WebSiteFunctions wsf;
	
	private static Logger log = LogManager.getLogger(AppName_Low_Suite.class.getName());

	BSN_AppLoginPage loginPage;
	BSN_AppName_HomePage homePage;
	
	private ExtentHtmlReporter htmlReporter;
	private ExtentReports report;
	private ExtentTest test; //Child test  

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm");
	private DateTimeFormatter daily_dtf = DateTimeFormatter.ofPattern("yy.MM.dd");
	private LocalDateTime now = LocalDateTime.now();

	private String reportName;
	private String reportPath;
	private String priority;
	private String runType;

	String newIncident;
	String openedIncident;
	String problemNumber;
	String changeNumber;

	private Test_Steps testSteps;

	// Login Credentials
	// String strUser   = Credentials.getUsername();
	// String strPass   = Credentials.getPassword();
	//	String strUser   = config.getUsername();//ReadingXLSUsername("username");
	//	String strPass   = SetupPass.getPass();
	
	/***
	 * Setup the ExtentReport by their Priority and Browser type.
	 * @param browser
	 * @param priority
	 * @param runType 
	 */
	@BeforeTest
	@Parameters({ "browser", "priority", "runType"})
	public void setUp(String browser, String priority, String runType) {	
		this.runType = runType;
		this.priority = priority;
		this.runType = runType;

		// ExtentReports configuration:
		System.out.println(priority+" Report has been set up at: " + this.dtf.format(this.now).toString());

		try {
			File dailyDir = new File("./Reports/"+this.daily_dtf.format(this.now).toString());
			dailyDir.mkdir();

			this.reportPath = "./Reports/"+dailyDir.getName()+"/Report_"+ dtf.format(this.now).toString() + "_" + browser + "_" + priority + "_Suite.html";
			this.reportName = dailyDir.getName() + "_" + browser + "_" + priority + "_Suite.html";

			this.report = new ExtentReports();
			this.htmlReporter = new ExtentHtmlReporter(this.reportPath);
			this.htmlReporter.loadXMLConfig("extent-config.xml");
			//True - Append/merge all html reports to one single report.
			this.htmlReporter.setAppendExisting(true);

			this.htmlReporter.config().setDocumentTitle("App AppName "+priority+" report");

			// allow automatic saving of media files relative to the report
			//htmlReporter.config().setAutoCreateRelativePathMedia(true);

			this.report.attachReporter(this.htmlReporter);

		} catch (Exception e) {	
			System.out.println(e.getMessage());
		}
	}

	@BeforeMethod(description= "Init")
	@Parameters({ "browser", "environment"})
	public void setClasses(String browser, String environment) {
		ThreadContext.put("contextKey",this.priority);
		log = LogManager.getLogger(this.getClass().getName());

		ds = new DriverSetUp(browser);

		if (runType.equals("local")) { 
			driver = ds.driveReturn();
			log.info("Local Driver");
		} 
		if (runType.equals("remote") || runType.equalsIgnoreCase("grid")) { 
			driver = ds.driveReturn(config.getLocalURL());
			//driver = ds.driveReturn("http://localhost:4446/wd/hub");
			log.info("Remote Driver");
		}
		log.info("Driver: " + driver);

		testSteps = new Test_Steps(driver);

		um = new UsefulMethods(driver);
		wsf = new WebSiteFunctions(driver);

		loginPage = new BSN_AppLoginPage(driver);
		homePage = new BSN_AppName_HomePage(driver);

		switch(environment) {
		case "sandbox":
			log.info("Sandbox Url: " + config.getBaseURL() + " Browser: " + browser);
			driver.get(config.getBaseURL());
			break;

		case "test":
			log.info("Test Url: " + config.getTestURL() + " Browser: " + browser);
			driver.get(config.getTestURL());
			break;

		case "stage":
			//TODO:
			break;

		case "prod":
			//TODO:
			break;
		}
	}

	//TC01
	@Test(description= "Running TC01") // , invocationCount = 100)
	private void TC01_StepRunner()  {
		log = LogManager.getLogger("TC01");
		test = report.createTest("TC01");

		/**Step 1
		Navigate to App's AppName Test environment:
		App's login screen appears
		 */
		log.info("\n ----------------------------------------------------------------------");
		log.info(" ----------- Step 1 Navigate to App's AppName Test environment");
		test.log(Status.INFO, "Step 1 Navigate to App's AppName Test environment");
		loginPage.login(strUser, strPass);
		
		log.info("App AppName login screen appears");
		test.pass("App AppName login screen appears");

		/**Step 2
		Impersonate as TestUser:

		System allows impersonation.
		The top of the screen now states "TestUser Name" in the in the top right-hand corner of the title bar.
		 */
		log.info("\n ----------------------------------------------------------------------");
		log.info(" ----------- Step 2 Impersonate as TestUser User: \"TestUser Name\"");
		test.log(Status.INFO, "Step 2 Impersonate as TestUser User: \"TestUser Name\"");
		homePage.clickOnImpersonateUser();

		log.info("App Service Automation login screen appears");
		test.pass("App Service Automation login screen appears");

		/**Step 3
		System allows impersonation.
		The top of the screen now states \"TestUser Name\" in the top right hand corner of the screen.
		 */
		log.info("\n ----------------------------------------------------------------------");
		log.info(" ----------- Step 3 System allows impersonation.");
		test.log(Status.INFO, "Step 3 System allows impersonation.");
		homePage.searchImpersonateUser(config.getTestUserFullName());
		homePage.loggedUser(config.getTestUserFullName());

		log.info("The top of the screen now states \"TestUser Name\" in the top right hand corner of the screen.\n");
		test.pass("The top of the screen now states \"TestUser Name\" in the top right hand corner of the screen.");

		/**Step 4
		Log out of AppName.
		User is logged out.
		 */
		log.info("\n ----------------------------------------------------------------------");
		log.info(" ----------- Step 4 Log out of AppName.");
		test.log(Status.INFO, "Step 4 Log out of AppName.");
		homePage.Logout();

		log.info("User is logged out.");
		test.pass("User is logged out.");

		log.info("-----------TC01 Completed------------");


	}

	@AfterTest
	private void end() {
		log.info("----------- Low Test Case Set Completed------------");
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		try {
			if(result.getStatus() == ITestResult.SUCCESS) {
				//Do something here
				log.info("***********Passed***********");
				test.log(Status.PASS, "The Test Case "+ this.getClass().getName() +" is PASS");
			}
			else if(result.getStatus() == ITestResult.FAILURE) {
				log.error("***********Failed***********");

				String method = result.getName();
				String temp = um.getScreenshotBase64(driver, method);
				//log.info(temp);
				StringWriter sw = new StringWriter();
				result.getThrowable().printStackTrace(new PrintWriter(sw));
				String exStackTrace = sw.toString();
				//				log.info("Exception: " + exStackTrace);

				MediaEntityModelProvider mediaModel = MediaEntityBuilder.createScreenCaptureFromBase64String(temp).build();

				test.info(result.getThrowable().getMessage());
				test.log(Status.FAIL, new RuntimeException(exStackTrace), mediaModel);
				//test.info(result.getThrowable().getMessage(), mediaModel);
				//test.log(Status.FAIL, "The Test Case "+ this.getClass().getName() +" is FAIL");
				//test.fail(exStackTrace, mediaModel);

				//test.info(new RuntimeException(exStackTrace));	

				throw new Exception();
			}
			else if(result.getStatus() == ITestResult.SKIP ) {
				log.info("***********Skiped***********");
				test.log(Status.SKIP, "The Test Case "+ this.getClass().getName() +" is SKIP");
				StringWriter sw = new StringWriter();
				result.getThrowable().printStackTrace(new PrintWriter(sw));
				String exStackTrace = sw.toString();
				log.info("Exception: " + exStackTrace);
			}
		} catch(Exception e) {
			log.error(e.getMessage());
		}

		log.info("----------------------------------------------------------------------");
		log.info(" -------------- The Test " + this.getClass().getName() + " has been completed");
		log.info("----------------------------------------------------------------------");	
		report.flush();
		driver.quit();
	}

}
