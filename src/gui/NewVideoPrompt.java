package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import video.LoadedVideo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Offers the user ability to add new advertisement information.
 * There horizontal box contains TextFields for each of the necessary
 * attributes.
 * @author Austin Kyker
 *
 */
public class NewVideoPrompt extends HBox {

	private TextField myCompanyField;
	private TextField myTitleField;
	private TextField myPurchasedPlaysField;
	private TextField myLengthField;

	public NewVideoPrompt(ObservableList<LoadedVideo> videoList) 
			throws FileNotFoundException, SAXException, IOException, 
			ParserConfigurationException {

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
	private Button makeNewVideoButton(ObservableList<LoadedVideo> videoList) {
		final Button addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(isInputValidated() && getAndSaveVideoFile()){
					LoadedVideo video = new LoadedVideo(
							myCompanyField.getText(),
							myTitleField.getText(),
							Integer.parseInt(myPurchasedPlaysField.getText()),
							Integer.parseInt(myLengthField.getText()));
					videoList.add(video);
					try {
						GUIController.editMasterFile();
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

	protected boolean getAndSaveVideoFile() {
		return saveFile(getFileFromFileChooser());
	}

	private boolean saveFile(File fileFromFileChooser) {
		if(fileFromFileChooser == null)
			return false;
		try {
			Files.copy(fileFromFileChooser.toPath(), 
					new File("./videos/"+getRequiredFileName()).toPath(), REPLACE_EXISTING);
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private File getFileFromFileChooser () {
		FileChooser myFileChooser = new FileChooser();
		myFileChooser.setTitle("Select Video File");
		FileChooser.ExtensionFilter extentionFilter =
				new FileChooser.ExtensionFilter(
						"Video files (*.mp4)", getRequiredFileName());
		myFileChooser.getExtensionFilters().add(extentionFilter);
		myFileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		return myFileChooser.showOpenDialog(new Stage());
	}

	private String getRequiredFileName() {
		return myCompanyField.getText().replace(" ", "") + "_" + myTitleField.getText().replace(" ", "") + ".mp4";
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
