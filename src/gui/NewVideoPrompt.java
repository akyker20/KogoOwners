package gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import video.LoadedVideo;

/**
 * Offers the user ability to add new advertisement information. There
 * horizontal box contains TextFields for each of the necessary attributes.
 * 
 * @author Austin Kyker
 *
 */
public class NewVideoPrompt extends HBox {

	private TextField myCompanyField;
	private TextField myTitleField;
	private TextField myPurchasedPlaysField;
	private TextField myLengthField;

	public NewVideoPrompt() throws FileNotFoundException, SAXException,
			IOException, ParserConfigurationException {

		createNewVideoInputTextFields();
		this.getChildren().addAll(myCompanyField, myTitleField,
				myPurchasedPlaysField, myLengthField, makeNewVideoButton());
		this.setSpacing(15);
	}

	private void createNewVideoInputTextFields() {
		myCompanyField = makeTextField("Company");
		myTitleField = makeTextField("Video Title");
		myPurchasedPlaysField = makeTextField("Plays Purchased");
		myLengthField = makeTextField("Length");
	}

	private TextField makeTextField(String name) {
		TextField field = new TextField();
		field.setPromptText(name);
		return field;
	}

	private Button makeNewVideoButton() {
		final Button addButton = new Button("Add");
		addButton.setOnAction(event -> addNewAdvertisement());
		return addButton;
	}

	private void addNewAdvertisement() {
		if (isNewVideoInputValid()) {
			LoadedVideo createdAd = createNewVideoFromTextFieldInputs();
			if (Controller.addNewAdvertisement(createdAd))
				clearFields();
		}
	}

	private LoadedVideo createNewVideoFromTextFieldInputs() {
		return new LoadedVideo(myCompanyField.getText(),
				myTitleField.getText(), Integer.parseInt(myPurchasedPlaysField
						.getText()), Integer.parseInt(myLengthField.getText()));
	}

	private boolean isNewVideoInputValid() {
		return noVideoFieldIsEmpty() && numberFieldsDontHaveCharacters();
	}

	private boolean numberFieldsDontHaveCharacters() {
		String purchasedPlays = myPurchasedPlaysField.getText();
		String length = myLengthField.getText();
		try {
			Integer.parseInt(purchasedPlays);
			Integer.parseInt(length);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean noVideoFieldIsEmpty() {
		String company = myTitleField.getText();
		String title = myTitleField.getText();
		String purchasedPlays = myPurchasedPlaysField.getText();
		String length = myLengthField.getText();
		return !(company.isEmpty() || title.isEmpty()
				|| purchasedPlays.isEmpty() || length.isEmpty());
	}

	private void clearFields() {
		myCompanyField.clear();
		myTitleField.clear();
		myPurchasedPlaysField.clear();
		myLengthField.clear();
	}
}
