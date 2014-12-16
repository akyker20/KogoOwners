package video;

/**
 * Model that represents an advertising video and holds information about this
 * video.
 * 
 * @author Austin Kyker
 *
 */
public class LoadedVideo extends Video {

	public static final double CENTS_PER_SECOND = 0.01;

	private int myPlaysPurchased;
	private int myPlaysRemaining;

	public LoadedVideo(String company, String name, int playsPurchased,
			int length) {
		this(company, name, playsPurchased, playsPurchased, length);
	}

	public LoadedVideo(String company, String name, int playsPurchased,
			int playsRemaining, int length) {
		super(company, name, length);
		myPlaysPurchased = playsPurchased;
		myPlaysRemaining = playsRemaining;
	}

	public int getMyPlaysRemaining() {
		return myPlaysRemaining;
	}

	public int getMyPlaysPurchased() {
		return myPlaysPurchased;
	}

	public void setMyPlaysRemaining(int newPlaysRemaining) {
		myPlaysRemaining = newPlaysRemaining;
	}

	@Override
	public int getMyPlaysCompleted() {
		return myPlaysPurchased - myPlaysRemaining;
	}
}