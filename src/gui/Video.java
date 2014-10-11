package gui;
public class Video {

	private String myName;
	private int myPlaysRemaining;
	private int myLength;
	private String myCompany;

	/**
	 * Video constructor
	 * @param name
	 * @param playsRemaining
	 * @param length
	 * @param company
	 */
	public Video(String company, String name, int playsRemaining, int length){
		myName = name;
		myPlaysRemaining = playsRemaining;
		myLength = length;
		myCompany = company;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public int getMyPlaysRemaining() {
		return myPlaysRemaining;
	}

	public void setMyPlaysRemaining(int myPlaysRemaining) {
		this.myPlaysRemaining = myPlaysRemaining;
	}

	public int getMyLength() {
		return myLength;
	}

	public void setMyLength(int myLength) {
		this.myLength = myLength;
	}

	public String getMyCompany() {
		return myCompany;
	}

	public void setMyCompany(String myCompany) {
		this.myCompany = myCompany;
	}
}