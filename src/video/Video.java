package video;

public abstract class Video {
	
	public static final double CENTS_PER_SECOND = 0.010;
	
	private String myCompany;
	private String myName;
	private int myLength;
	private double myRevenue;
	
	public Video(String company, String name, int length) {
		myCompany = company;
		myName = name;
		myLength = length;
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
	
	public double getMyRevenue(){
		myRevenue = myLength*CENTS_PER_SECOND*getMyPlaysCompleted();
		return Math.round(myRevenue * 100.00)/100.00;
	}
	
	public abstract int getMyPlaysCompleted();
}
