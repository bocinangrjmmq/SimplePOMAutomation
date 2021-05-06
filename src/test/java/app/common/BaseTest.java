package app.common;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.Logger;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.SystemUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import app.model.Credentials;
import app.common.ConfigReader;

public class BaseTest {

	private static ExtentHtmlReporter htmlReporter;
	public static ExtentReports report;
	public static ExtentTest parentTest;
	public static ExtentTest test; //Child test  

	public UsefulMethods um = new UsefulMethods();
	public static ConfigReader config   = new ConfigReader();
	public Logger log;

	public static String strUser = config.getUsername(); //ReadingXLSUsername("username");
	public static String strPass = config.getPassword(); //SetupPass.getPass();
	private static Credentials cr = new Credentials(strUser, strPass);

	DockerSet dock;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm");
	DateTimeFormatter daily_dtf = DateTimeFormatter.ofPattern("yy.MM.dd");
	LocalDateTime now = LocalDateTime.now();

	String reportName;
	String reportPath;
	String browser;
	String priority;
	String runType;

	
	/***
	 * Method to set up Docker to run in a RemoteWebDriver instance
	 * Runs Before the Suite execution. 
	 * 
	 * @param runType "local/remote" is defined in the Suite.xml file.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	//TODO: Comment bellow TestNG annotations to run a single TC class without Docker
	@BeforeSuite(description="Docker Init")
	@Parameters({"runType"})
	public void dockerSetup(String runType) throws IOException, InterruptedException {
		this.runType = runType;
		
		//Docker setup
		if (runType.equals("remote")) {
			dock = new DockerSet();

			//Delete Docker's config file if any
			File fi = new File(config.getStatusFile());
			if (fi.delete()) {
				System.out.println("The file was deleted successfully");
				//dock.stopBat(config.getOperationsBat().get(2), config.getOperationsBat().get(1),
				//config.getOperationsBat().get(3), config.getStatusFile(), config.getDockerStopLine());
			}

			System.out.println("Starting Docker instance");
			
			if (SystemUtils.IS_OS_WINDOWS) { 
				System.out.println("Running on Windows");
				
				dock.starBat(config.getOperationsBatW().get(0), config.getOperationsBatW().get(1), config.getStatusFile(),
						config.getDockerStatusLine());
			} 
			else if (SystemUtils.IS_OS_LINUX) { 
				System.out.println("Running on Linux");
				
				dock.starBat(config.getOperationsBashLnx().get(0), config.getOperationsBashLnx().get(1), config.getStatusFile(),
						config.getDockerStatusLine());
			} 
		}
	}
	
	/***
	 * Credentials configuration:
	 * @deprecated
	 */
	//@BeforeSuite(description="Init")
	@BeforeClass(description="Init")
	public void init() {
		cr.setUsername(strUser);
		cr.setPassword(strPass);
	}

	/***
	 * Setup the ExtentReport by their Priority and Browser type.
	 * @param browser
	 * @param priority
	 */
	@BeforeTest
	@Parameters({ "browser", "priority"})
	public void setUp(String browser, String priority) {	
		this.browser = browser;
		this.priority = priority;
		
		// ExtentReports configuration:
		System.out.println("Report has been set up at: " + dtf.format(now).toString());
		
		try {
			File dailyDir = new File("./Reports/"+daily_dtf.format(now).toString());
			dailyDir.mkdir();
			
			this.reportPath = "./Reports/"+dailyDir.getName()+"/Report_"+ dtf.format(now).toString() + "_" + browser + "_" + priority + "_Suite.html";
			this.reportName = dailyDir.getName() + "_" + browser + "_" + priority + "_Suite.html";
			
			report = new ExtentReports();
			htmlReporter = new ExtentHtmlReporter(reportPath);
			htmlReporter.loadXMLConfig("extent-config.xml");
			//True - Append/merge all html reports to one single report.
			htmlReporter.setAppendExisting(true);
			
			htmlReporter.config().setDocumentTitle("App AppName "+priority+" report");
			
			// allow automatic saving of media files relative to the report
			//htmlReporter.config().setAutoCreateRelativePathMedia(true);
			
			report.attachReporter(htmlReporter);
			
		} catch (Exception e) {	
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Writes test information from the started reporter in the .html file and saved in the Reports directory.
	 *  **Also the functionality to send the email via email has been created but disabled.
	 *  Runs After the Test execution. 
	 */
	@AfterTest
	public void tearDown() {
		System.out.println("----------- "+ this.priority +" Incident Test Cases Completed------------");
		report.flush();
		
		//Email report for priority
		//um.sendEmailWithReport(this.reportPath, this.reportName);
	}

	/***
	 * 
	 * Method to tear down the Docker.
	 * 
	 * Runs after the Suite execution.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	//@AfterClass
	@AfterSuite
	public void dockerTearDown() throws IOException, InterruptedException  {
		if (runType.equals("remote")) {
			//Stop docker instance:
			System.out.println("Stopping Docker instance");
			if (SystemUtils.IS_OS_WINDOWS) { 
				System.out.println("Running on Windows");
				
				dock.stopBat(config.getOperationsBatW().get(2), config.getOperationsBatW().get(1),
						config.getOperationsBatW().get(3), config.getStatusFile(), config.getDockerStopLine());

			} 
			else if (SystemUtils.IS_OS_LINUX) { 
				System.out.println("Running on Linux");
				
				dock.stopBat(config.getOperationsBashLnx().get(2), config.getOperationsBashLnx().get(1),
						config.getOperationsBashLnx().get(3), config.getStatusFile(), config.getDockerStopLine());
			} 
		}
	}
	
}
