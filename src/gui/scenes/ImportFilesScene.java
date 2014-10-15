package gui.scenes;

import gui.GUIController;
import gui.tableviews.DrivingSessionTable;
import gui.tableviews.ImportedFilesTable;
import gui.tableviews.VideoTable;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import menus.MenuFeature;

import org.xml.sax.SAXException;

import video.PlayedVideo;
import video.Video;
import xmlcontrol.DriverXMLParser;
import control.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

/**
 * Scene for dragging and dropping master XML File.
 * This screen is shown to the user when the application begins.
 * The user will only be able to drag and drop an XML File with
 * the correct title pertaining to the date of the session.
 * @author Austin Kyker
 *
 */
public class ImportFilesScene extends Scene {

	private TableView<File> myImportedFilesView;
	private TableView<PlayedVideo> myDriverSessionTable;
	private LocalDate myDateSelected;
	private BorderPane myPane;
	private VBox myLeftContainer;
	private VBox myRightContainer;

	public ImportFilesScene(BorderPane root, MenuFeature menuBar, DriverXMLParser parser, ObservableList<PlayedVideo> importedVideos) {
		super(root, GUIController.SCREEN_WIDTH, GUIController.SCREEN_HEIGHT);
		getStylesheets().add(GUIController.STYLESHEET_PACKAGE + "style.css");
		myPane = root;
		final DatePicker datePicker = new DatePicker();
		datePicker.setPrefWidth(260);
		datePicker.setOnAction(event -> removeDatePickerAddListView(datePicker));
		root.setTop(menuBar);
		myLeftContainer = new VBox(10);
		myLeftContainer.setPadding(new Insets(10));
		root.setLeft(myLeftContainer);

		ObservableList<File> files = FXCollections.observableArrayList();
		
		myDriverSessionTable = new DrivingSessionTable(importedVideos);
		myImportedFilesView = new ImportedFilesTable(files, parser, myDriverSessionTable);
		
		
		myLeftContainer.getChildren().addAll(datePicker, myImportedFilesView);
		
		
		myRightContainer = new VBox();
		myRightContainer.getChildren().add(myDriverSessionTable);
		myRightContainer.setPadding(new Insets(10, 10, 10, 0));
		root.setCenter(myRightContainer);
		


		
	}

	private void removeDatePickerAddListView(DatePicker datePicker) {
		myDateSelected = datePicker.getValue();
		datePicker.setDisable(true);
		myImportedFilesView.setDisable(false);
	}
}
