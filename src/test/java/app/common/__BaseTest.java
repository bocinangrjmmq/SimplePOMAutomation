package app.common;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.SystemUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import app.common.ConfigReader;

public class __BaseTest {

	public UsefulMethods um = new UsefulMethods();
	public static ConfigReader config   = new ConfigReader();
	//public static Logger log;
	private static final Logger log = LogManager.getLogger(__BaseTest.class.getName());

	public static String strUser = config.getUsername(); //ReadingXLSUsername("username");
	public static String strPass = config.getPassword(); //SetupPass.getPass();
	//	private static Credentials cr = new Credentials(strUser, strPass);

	DockerSet dock;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm");
	DateTimeFormatter daily_dtf = DateTimeFormatter.ofPattern("yy.MM.dd");
	LocalDateTime now = LocalDateTime.now();

	public String baseRunType;


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
		String methodST="dockerSetup";
		ThreadContext.put("contextKey",methodST);
		this.baseRunType = runType;
		
		//log = LogManager.getLogger(this.getClass().getName());
		
		//Docker setup
		if (runType.equalsIgnoreCase("remote")) {
			dock = new DockerSet();

			log.info("Starting Docker instance");

			if (SystemUtils.IS_OS_WINDOWS) { 
				log.info("Running on Windows");
				dock.starBat(config.getOperationsBatW().get(0), config.getOperationsBatW().get(1), config.getStatusFile(),
						config.getDockerStatusLine());
			} 
			else if (SystemUtils.IS_OS_LINUX) { 
				log.info("Running on Linux");
				dock.starBat(config.getOperationsBashLnx().get(0), config.getOperationsBashLnx().get(1), config.getStatusFile(),
						config.getDockerStatusLine());
			} 
		}

		else if (runType.equalsIgnoreCase("grid")) {
			dock = new DockerSet();

		
			log.info("Starting DockerGrid instance");
			if (SystemUtils.IS_OS_WINDOWS) {
				log.info("Running on Windows");
				dock.starGridBat(config.getGridStartBatW(),
						config.getGridStartStatusFile(), config.getGridStatusLine());	
			} 
			else if (SystemUtils.IS_OS_LINUX) {
				log.info("Running on Linux");
				dock.starGridBat(config.getGridStartBashLnx(), 
						config.getGridStartStatusFile(), config.getGridStatusLine());
			}
		}

	}

	/***
	 * Credentials configuration:
	 * @deprecated
	 */
	//@BeforeSuite(description="Init")
	//	@BeforeClass(description="Init")
	//	public void init() {
	//		cr.setUsername(strUser);
	//		cr.setPassword(strPass);
	//	}

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
		String methodST="dockerTearDown";
		ThreadContext.put("contextKey",methodST);
		if (baseRunType.equalsIgnoreCase("remote")) {
			//Stop docker instance:
			log.info("Stopping Docker instance");
			if (SystemUtils.IS_OS_WINDOWS) { 
				log.info("Running on Windows");
				dock.stopBat(config.getOperationsBatW().get(2), config.getOperationsBatW().get(1),
						config.getOperationsBatW().get(3), config.getStatusFile(), config.getDockerStopLine());
			} 
			else if (SystemUtils.IS_OS_LINUX) { 
				log.info("Running on Linux");
				dock.stopBat(config.getOperationsBashLnx().get(2), config.getOperationsBashLnx().get(1),
						config.getOperationsBashLnx().get(3), config.getStatusFile(), config.getDockerStopLine());
			} 
		}
		else if (baseRunType.equalsIgnoreCase("grid")) {
			log.info("Stopping DockerGrid instance");
			if (SystemUtils.IS_OS_WINDOWS) {
				log.info("Running on Windows");
				dock.stopGridBatNoLogsGen(config.getGridStopBatW());

			} else if (SystemUtils.IS_OS_LINUX) {
				log.info("Running on Linux");
				dock.stopGridBatNoLogsGen(config.getGridStopBashLnx());
			}
		}

		//Delete Docker's config file if any
		File fi = new File(config.getStatusFile());
		if (fi.delete()) {
			System.out.println("The file was deleted successfully");
		}
	}



}
