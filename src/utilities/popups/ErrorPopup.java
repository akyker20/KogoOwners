package utilities.popups;


public class ErrorPopup extends Popup {

	private static final String ERROR_FILE_LOC = "./src/utilities/popups/error.png";
	private static final String TITLE = "Error";

	public ErrorPopup(String message) {
		super(message, TITLE, ERROR_FILE_LOC);
	}
}
