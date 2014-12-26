package gui.scenes;

import gui.tableviews.DrivingImportStatsTable;
import gui.tableviews.ImportedFilesTable;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import control.FileControl;

/**
 * Scene for dragging and dropping master XML File. This screen is shown to the
 * user when the application begins. The user will only be able to drag and drop
 * an XML File with the correct title pertaining to the date of the session.
 * 
 * @author Austin Kyker
 *
 */
public class ImportFilesScene extends GUIScene {

	private static final int DATE_PICKER_WIDTH = 260;
	private ImportedFilesTable myImportedFilesTable;
	private DrivingImportStatsTable myDriverSessionTable;
	private DatePicker myDatePicker;
	private FileControl myControl;

	public ImportFilesScene(FileControl fileControl) {
		myControl = fileControl;
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

	private void createTablesToBeShownInScene() {
		myDriverSessionTable = new DrivingImportStatsTable();
		myImportedFilesTable = new ImportedFilesTable(myDriverSessionTable, myControl);
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
		myDatePicker.setValue(null);
		myDatePicker.setDisable(false);
		myDriverSessionTable.reset();
		myImportedFilesTable.reset();
	}
}