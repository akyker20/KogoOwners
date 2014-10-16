package video;

public class PlayedVideo extends Video {

	private int myPlaysCompleted;
	
	public PlayedVideo(String company, String name, int playsCompleted, int length){
		super(company, name, length);
		myPlaysCompleted = playsCompleted;
	}
	
	public int getMyPlaysCompleted() {
		return myPlaysCompleted;
	}

	public void incrementCompletedViews(int playsCompleted) {
		myPlaysCompleted += playsCompleted;		
	}
}
