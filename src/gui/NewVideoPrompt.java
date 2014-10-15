package gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import video.Video;
import xmlcontrol.XMLWriter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Offers the user ability to add new advertisement information.
 * There horizontal box contains TextFields for each of the necessary
 * attributes.
 * @author Austin Kyker
 *
 */
public class NewVideoPrompt extends HBox {
	
	private GUIController myGUIController;
	private TextField myCompanyField;
	private TextField myTitleField;
	private TextField myPurchasedPlaysField;
	private TextField myLengthField;
	
	public NewVideoPrompt(ObservableList<Video> videoList, GUIController controller) 
			throws FileNotFoundException, SAXException, IOException, 
			ParserConfigurationException {
		
		myGUIController = controller;
		myCompanyField = makeTextField("Company");		
		myTitleField = makeTextField("Video Title");
		myPurchasedPlaysField = makeTextField("Plays Purchased");
		myLengthField = makeTextField("Length");

		this.getChildren().addAll(myCompanyField, myTitleField, 
				myPurchasedPlaysField, myLengthField, makeNewVideoButton(videoList));
		this.setSpacing(15);
	}

	/**
	 * Helper method to make creation of text fields DRYer
	 * @param name
	 * @return a textfield
	 */
	private TextField makeTextField(String name) {
		TextField field = new TextField();
		field.setPromptText(name);
		return field;
	}

	/**
	 * Creates the new video button. Adds a listener that validates
	 * the input data before creating a new video.
	 * @param videoList
	 * @return
	 */
	private Button makeNewVideoButton(ObservableList<Video> videoList) {
		final Button addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(isInputValidated()){
					Video video = new Video(
							myCompanyField.getText(),
							myTitleField.getText(),
							Integer.parseInt(myPurchasedPlaysField.getText()),
							Integer.parseInt(myLengthField.getText()));
					videoList.add(video);
					try {
						myGUIController.editMasterFile();
					} catch (TransformerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					clearFields();
				}
			}
		});
		return addButton;
	}
	
	/**
	 * Validates input - Checks that no inputs are empty and that the last 
	 * two text fields are numbers.
	 * @return true if inputs are valid.
	 */
	private boolean isInputValidated() {		
		String company = myTitleField.getText();
		String title = myTitleField.getText();
		String purchasedPlays = myPurchasedPlaysField.getText();
		String length = myLengthField.getText();
		
		if(company.isEmpty() || title.isEmpty() || purchasedPlays.isEmpty() || length.isEmpty()){
			return false;
		}	
		try{
			Integer.parseInt(purchasedPlays);
			Integer.parseInt(length);
		}
		catch(NumberFormatException e){
			return false;
		}
		return true;		
	}
	
	/**
	 * Clears all the text fields. This is called after a new entry is added.
	 */
	private void clearFields() {
		myCompanyField.clear();
		myTitleField.clear();
		myPurchasedPlaysField.clear();
		myLengthField.clear();
	}
}
