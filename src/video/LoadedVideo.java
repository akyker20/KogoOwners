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

	/**
	 * 
	 * @param company
	 * @param name
	 * @param length
	 * @param playsPurchased
	 */
	public LoadedVideo(String company, String name, int length, int playsPurchased) {
		super(company, name, length, playsPurchased, 0);
	}

	public LoadedVideo(ActiveVideo importedVid) {
		super(importedVid.getMyCompany(), importedVid.getMyName(), importedVid
				.getMyLength(), importedVid.getMyPlaysPurchased(), importedVid.getMyPlays());
		
		
	}

	public int getMyPlaysRemaining() {
		return this.getMyPlaysPurchased() - this.getMyPlays();
	}

	@Override
	public void addStudentViews(int numViews) {
		myPlays += numViews;	
	}
}