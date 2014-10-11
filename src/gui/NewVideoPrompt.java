package gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class NewVideoPrompt extends HBox {
	
	private XMLWriter myXMLWriter;
	private TextField myCompanyField;
	private TextField myTitleField;
	private TextField myPlaysField;
	private TextField myLengthField;
	
	public NewVideoPrompt(ObservableList<Video> videoList) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException{
		myXMLWriter = new XMLWriter();
		
		myCompanyField = makeTextField("Company");		
		myTitleField = makeTextField("Video Title");
		myPlaysField = makeTextField("Num Plays");
		myLengthField = makeTextField("Length");

		this.getChildren().addAll(myCompanyField, myTitleField, 
				myPlaysField, myLengthField, makeNewVideoButton(videoList));
		this.setSpacing(3);
	}

	private TextField makeTextField(String name) {
		TextField field = new TextField();
		field.setPromptText(name);
		return field;
	}

	private Button makeNewVideoButton(ObservableList<Video> videoList) {
		final Button addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Video video = new Video(
						myCompanyField.getText(),
						myTitleField.getText(),
						Integer.parseInt(myPlaysField.getText()),
						Integer.parseInt(myLengthField.getText()));
				videoList.add(video);
				try {
					myXMLWriter.writeToFile(video);
				} catch (TransformerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				clearFields();
			}
		});
		return addButton;
	}
	
	private void clearFields() {
		myCompanyField.clear();
		myTitleField.clear();
		myPlaysField.clear();
		myLengthField.clear();
	}
}
