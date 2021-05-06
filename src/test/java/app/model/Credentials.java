package app.model;

public class Credentials {
	
	static String username;
	static String password;
	
	public Credentials () {}
	
	public Credentials (String uname, String passwd) {
		username = uname;
		password = passwd;
	}

	public void setUsername(String uname) {
		username = uname;
	}
	
	public void setPassword(String passwd) {
		password = passwd;
	}
	
	
	public static String getUsername() {
		return username;
	}
	
	public static String getPassword() {
		return password;
	}
}
