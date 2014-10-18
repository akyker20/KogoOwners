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
public class ImportFilesScene extends GUIBox {

	private ImportedFilesTable myImportedFilesView;
	private DrivingSessionTable myDriverSessionTable;
	private ObservableList<PlayedVideo> myImportedVideos;
	private DatePicker myDatePicker;

	public ImportFilesScene(ObservableList<PlayedVideo> importedVideos) 
			throws ParserConfigurationException, SAXException, IOException {
		
		myImportedVideos = importedVideos;
		myDatePicker = new DatePicker();
		myDatePicker.setPrefWidth(260);
		myDatePicker.setOnAction(event -> removeDatePickerAddListView());
		
		VBox leftContainer = new VBox(10);
		leftContainer.setPadding(new Insets(10));
		this.setLeft(leftContainer);
		
		myDriverSessionTable = new DrivingSessionTable(myImportedVideos);
		myImportedFilesView = new ImportedFilesTable(myImportedVideos, myDriverSessionTable);
				
		leftContainer.getChildren().addAll(myDatePicker, myImportedFilesView);
			
		VBox rightContainer = new VBox();
		rightContainer.getChildren().add(myDriverSessionTable);
		rightContainer.setPadding(new Insets(10, 10, 10, 0));
		this.setCenter(rightContainer);
	}
	
	private void removeDatePickerAddListView() {
		myImportedFilesView.setDate(myDatePicker.getValue());
		myDatePicker.setDisable(true);
		myImportedFilesView.setDisable(false);
	}

	public void reset() {
		myImportedVideos.clear();
		myDatePicker.setValue(null);
		myDatePicker.setDisable(false);
		myDriverSessionTable.reset();
		myImportedFilesView.reset();
	}

	public List<File> getFiles() {
		return myImportedFilesView.getFiles();
	}
}
