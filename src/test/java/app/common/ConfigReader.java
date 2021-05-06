package app.common;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 
 * This class reads the values from the file configuration.properties
 * 
 * @return Value from configuration.properties
 */
public class ConfigReader {

	private static final Logger log = LogManager.getLogger(ConfigReader.class.getName());
	Properties pro;

	public ConfigReader() {

		try {
			File src = new File("./configuration.properties");

			FileInputStream fis = new FileInputStream(src);

			pro = new Properties();

			pro.load(fis);

		} catch (Exception e) {
			log.debug("Exception is ==" + e.getMessage());
		}
	}

	/**
	 * @return
	 */
	public String getHeader() {
		return pro.getProperty("Header");
	}

	public String getBrowser() {
		return pro.getProperty("Browser");
	}

	public String getUsername() {
		return pro.getProperty("Username");
	}

	public String getPassword() {
		return pro.getProperty("Password");
	}

	public String getBaseURL() {
		return pro.getProperty("BaseURL");
	}

	public String getTestURL() {
		return pro.getProperty("TestURL");
	}

	public String getBaseEnv() {
		return pro.getProperty("BaseEnv");
	}

	public String getHomePageURL() {
		return pro.getProperty("HomePageURL");
	}

	public String getMyAccountPageName() {
		return pro.getProperty("MyAccountPageName");
	}

	public String getLocalURL() {
		return pro.getProperty("LocalURL");
	}

	//----------------------------------------------------------------
	public List<String> getOperationsBatW() {       
		String property = 
				pro.getProperty("OperationsBatW");    
		List<String> propertyList = 
				new ArrayList<>(Arrays.asList(property.split(",")));  
		return propertyList;
	}

	public List<String> getOperationsBashLnx() {       
		String property = 
				pro.getProperty("OperationsBashLnx");    
		List<String> propertyList = 
				new ArrayList<>(Arrays.asList(property.split(",")));  
		return propertyList;
	}

	public String getStatusBat() {
		return pro.getProperty("StatusBat");
	}

	public String getStopBat() {
		return pro.getProperty("StopBat");
	}

	public String getStatusFile() {
		return pro.getProperty("StatusFile");
	}

	public String getDockerStatusLine() {
		return pro.getProperty("DockerStatusLine");
	}

	public String getDockerStopLine() {
		return pro.getProperty("DockerStopLine");
	}

	//---------------------------------------------
	
	public String getGridStartBatW() {
		return pro.getProperty("GridStartBatW");
	}

	public String getGridStopBatW() {
		return pro.getProperty("GridStopBatW");
	}
	
	public String getGridStartBashLnx() {
		return pro.getProperty("GridStartBashLnx");
	}

	public String getGridStopBashLnx() {
		return pro.getProperty("GridStopBashLnx");
	}

	public String getGridStartStatusFile() {
		return pro.getProperty("GridStartStatusFile");
	}

	public String getGridStatusLine() {
		return pro.getProperty("GridStatusLine");
	}

	public String getTestUserFullName() {
		return pro.getProperty("TestUserFullName");
	}
	
}


