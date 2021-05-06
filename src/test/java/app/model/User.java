package app.model;

public class User {
	
	String netid;
	String fullName;
	String email;
	String callBackNumber;
	String callBackNumber2;
	String location;
	
	public User() {}

	public User(String netid, String fullName, String email, String callBackNumber, String callBackNumber2,
			String location) {
		super();
		this.netid = netid;
		this.fullName = fullName;
		this.email = email;
		this.callBackNumber = callBackNumber;
		this.callBackNumber2 = callBackNumber2;
		this.location = location;
	}

	public String getNetid() {
		return netid;
	}

	public void setNetid(String netid) {
		this.netid = netid;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCallBackNumber() {
		return callBackNumber;
	}

	public void setCallBackNumber(String callBackNumber) {
		this.callBackNumber = callBackNumber;
	}

	public String getCallBackNumber2() {
		return callBackNumber2;
	}

	public void setCallBackNumber2(String callBackNumber2) {
		this.callBackNumber2 = callBackNumber2;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
