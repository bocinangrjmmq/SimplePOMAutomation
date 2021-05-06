package app.model;

//import static app.model.ReadXLSCredentials.ReadingXLSUsername;

import org.testng.annotations.BeforeSuite;

import app.common.ConfigReader;
import app.common.SetupPass;

public class CredentialsProvider {

	static ConfigReader config = new ConfigReader();
	
	//public static String strUser   = ReadingXLSUsername("username");
	public static String strUser   = config.getUsername();
	public static String strPass   = SetupPass.getPass();

	public static Credentials cr = new Credentials(strUser,strPass);

	@BeforeSuite(description="Init")
	public void init () {
		cr.setUsername(strUser);
		cr.setPassword(strPass);
	}	 
}
