package utilities.popups;

public class SuccessPopup extends Popup {
	
	private static final String SUCCESS_FILE_LOC = "./src/utilities/success.png";
	private static final String TITLE = "Success";

	public SuccessPopup(String message) {
		super(message, TITLE, SUCCESS_FILE_LOC);
	}
}
