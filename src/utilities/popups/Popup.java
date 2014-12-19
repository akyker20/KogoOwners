package utilities.popups;

import utilities.StringImageConverter;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Utility class that easily allows for the creation of error and success
 * popups, the major source of user feedback in this application.
 * 
 * @author Austin Kyker
 *
 */
public abstract class Popup extends Stage {

	private static final int ERROR_POPUP_WIDTH = 260;
	private static final int ERROR_POPUP_HEIGHT = 225;

	public Popup(String message, String title, String imgLoc) {
		VBox box = new VBox(10);
		box.setAlignment(Pos.CENTER);
		ImageView imgView = StringImageConverter.getImageView(
				ERROR_POPUP_WIDTH, ERROR_POPUP_HEIGHT, imgLoc);
		Label label = new Label(message);
		label.setPrefWidth(ERROR_POPUP_WIDTH);
		label.setWrapText(true);
		box.getChildren().addAll(label, imgView);
		this.setScene(new Scene(box));
		this.setTitle(title);
		this.setResizable(false);
		this.show();
	}
}