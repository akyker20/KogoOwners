package gui;
public class Video {

	private String myCompany;
	private String myName;
	private int myPlaysPurchased;
	private int myPlaysRemaining;
	private int myLength;

	/**
	 * Video constructor
	 * @param name
	 * @param playsRemaining
	 * @param length
	 * @param company
	 */
	public Video(String company, String name, int playsPurchased, int playsRemaining, int length){
		myCompany = company;
		myName = name;
		myPlaysPurchased  = playsPurchased;
		myPlaysRemaining = playsRemaining;
		myLength = length;
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

	public int getMyLength() {
		return myLength;
	}

	public String getMyCompany() {
		return myCompany;
	}

	public void setMyCompany(String myCompany) {
		this.myCompany = myCompany;
	}
	
	public int getMyPlaysPurchased() {
		return myPlaysPurchased;
	}
}