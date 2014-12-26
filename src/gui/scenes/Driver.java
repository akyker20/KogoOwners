package gui.scenes;

public class Driver {
	
	private String myName;
	private String myInitials;
	
	public Driver(String name, String initials) {
		myName = name;
		myInitials = initials;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public void setMyInitials(String myInitials) {
		this.myInitials = myInitials;
	}
	
	public String getMyName() {
		return myName;
	}

	public String getMyInitials() {
		return myInitials;
	}
}
