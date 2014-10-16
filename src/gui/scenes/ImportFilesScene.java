package gui.scenes;

import gui.GUIController;
import gui.tableviews.DrivingSessionTable;
import gui.tableviews.ImportedFilesTable;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.xml.parsers.ParserConfigurationException;

import menus.MenuFeature;

import org.xml.sax.SAXException;

import video.PlayedVideo;
import xmlcontrol.DriverXMLParser;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
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

	private TableView<File> myImportedFilesView;
	private TableView<PlayedVideo> myDriverSessionTable;
	private DatePicker myDatePicker;

	public ImportFilesScene(BorderPane root, GUIController controller, MenuFeature menuBar, 
			ObservableList<PlayedVideo> importedVideos) throws ParserConfigurationException, 
			SAXException, IOException {
		
		super(root, menuBar);
		
		myDatePicker = new DatePicker();
		myDatePicker.setPrefWidth(260);
		myDatePicker.setOnAction(event -> removeDatePickerAddListView());
		
		VBox leftContainer = new VBox(10);
		leftContainer.setPadding(new Insets(10));
		root.setLeft(leftContainer);
		
		myDriverSessionTable = new DrivingSessionTable(importedVideos);
		myImportedFilesView = new ImportedFilesTable(controller, new DriverXMLParser(importedVideos), myDriverSessionTable);
				
		leftContainer.getChildren().addAll(myDatePicker, myImportedFilesView);
			
		VBox rightContainer = new VBox();
		rightContainer.getChildren().add(myDriverSessionTable);
		rightContainer.setPadding(new Insets(10, 10, 10, 0));
		root.setCenter(rightContainer);

	}

	private void removeDatePickerAddListView() {
		myDatePicker.setDisable(true);
		myImportedFilesView.setDisable(false);
	}
}
