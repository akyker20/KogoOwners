package video;
public class Video {
	
	public static final double CENTS_PER_SECOND = 0.01;

	private String myCompany;
	private String myName;
	private int myPlaysPurchased;
	private int myPlaysRemaining;
	private int myLength;
	private double myRevenue;

	
	public Video(String company, String name, int playsPurchased, int length){
		this(company, name, playsPurchased, playsPurchased, length);
	}
	
	public Video(String company, String name, int playsPurchased, int playsRemaining, int length){
		myCompany = company;
		myName = name;
		myPlaysPurchased  = playsPurchased;
		myPlaysRemaining = playsRemaining;
		myLength = length;
		myRevenue = myLength*CENTS_PER_SECOND*(myPlaysPurchased - myPlaysRemaining);
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
	
	public double getMyRevenue(){
		return myRevenue;
	}
}