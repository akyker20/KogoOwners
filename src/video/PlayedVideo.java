package video;

public class PlayedVideo {
	
	public static final double CENTS_PER_SECOND = 0.01;

	private String myCompany;
	private String myName;
	private int myPlaysCompleted;
	private int myLength;
	private double myRevenue;

	
	public PlayedVideo(String company, String name, int playsCompleted, int length){
		myCompany = company;
		myName = name;
		myPlaysCompleted = playsCompleted;
		myLength = length;
		myRevenue = myLength*CENTS_PER_SECOND*myPlaysCompleted;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
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
	
	public int getMyPlaysCompleted() {
		return myPlaysCompleted;
	}
	
	public double getMyRevenue(){
		myRevenue = myLength*CENTS_PER_SECOND*myPlaysCompleted;
		double number = myLength*CENTS_PER_SECOND*myPlaysCompleted;;

		myRevenue = Math.round(number * 100.00)/100.00;
		return myRevenue;
	}

	public void incrementCompletedViews(int playsCompleted) {
		myPlaysCompleted += playsCompleted;		
	}
}
