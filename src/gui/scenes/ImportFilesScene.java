package gui.scenes;

import gui.tableviews.DrivingSessionTable;
import gui.tableviews.ImportedFilesTable;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import video.PlayedVideo;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

/**
 * Scene for dragging and dropping master XML File.
 * This screen is shown to the user when the application begins.
 * The user will only be able to drag and drop an XML File with
 * the correct title pertaining to the date of the session.
 * @author Austin Kyker
 *
 */
public class ImportFilesScene extends GUIScene {

	private static final int DATE_PICKER_WIDTH = 260;
	private ImportedFilesTable myImportedFilesTable;
	private DrivingSessionTable myDriverSessionTable;
	private ObservableList<PlayedVideo> myImportedVideos;
	private DatePicker myDatePicker;

	public ImportFilesScene(ObservableList<PlayedVideo> importedVideos) 
			throws ParserConfigurationException, SAXException, IOException {
		
		myImportedVideos = importedVideos;	
		initializeDatePicker();
		createTablesToBeShownInScene();		
		buildLeftColumnOfScene();		
		buildRightColumnOfScene();
	}

	private void buildRightColumnOfScene() {
		VBox rightContainer = new VBox();
		rightContainer.getChildren().add(myDriverSessionTable);
		rightContainer.setPadding(new Insets(10, 10, 10, 0));
		this.setCenter(rightContainer);
	}

	private void buildLeftColumnOfScene() {
		VBox leftContainer = new VBox(10);
		leftContainer.setPadding(new Insets(10));
		leftContainer.getChildren().addAll(myDatePicker, myImportedFilesTable);
		this.setLeft(leftContainer);
	}

	private void createTablesToBeShownInScene() throws ParserConfigurationException, SAXException, IOException {
		myDriverSessionTable = new DrivingSessionTable(myImportedVideos);
		myImportedFilesTable = new ImportedFilesTable(myImportedVideos, myDriverSessionTable);
	}

	private void initializeDatePicker() {
		myDatePicker = new DatePicker();
		myDatePicker.setPrefWidth(DATE_PICKER_WIDTH);
		myDatePicker.setOnAction(event -> removeDatePickerAddListView());
	}
	
	private void removeDatePickerAddListView() {
		myImportedFilesTable.setDate(myDatePicker.getValue());
		myDatePicker.setDisable(true);
		myImportedFilesTable.setDisable(false);
	}

	public void reset() {
		myImportedVideos.clear();
		myDatePicker.setValue(null);
		myDatePicker.setDisable(false);
		myDriverSessionTable.reset();
		myImportedFilesTable.reset();
	}

	public List<File> getFiles() {
		return myImportedFilesTable.getFiles();
	}
}
