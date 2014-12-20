package gui.tableviews;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import video.ActiveVideo;
import control.Controller;
import control.FileControl;

public class ImportedFilesTable extends TableView<File> {

	private static final int TABLE_WIDTH = 260;
	private ObservableList<File> myFiles;
	private LocalDate mySelectedDate;
	private FileControl myControl;
	private TableView<ActiveVideo> myDriverSessionTable;

	public ImportedFilesTable(TableView<ActiveVideo> driverSessionTable,
			FileControl control) {
		myFiles = FXCollections.observableArrayList();
		myDriverSessionTable = driverSessionTable;
		myControl = control;
		addColToTable();
		setDisable(true);
		setPrefWidth(TABLE_WIDTH);
		setItems(myFiles);

		this.setOnDragOver(event -> handleDragOver(event));
		this.setOnDragDropped(event -> handleFileDrop(event));
	}

	private void handleFileDrop(DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasFiles()) {
			success = true;
			for (File file : db.getFiles()) {
				String filePath = file.getPath();
				Matcher matcher = getFilePatternMatcher(filePath);
				if (matcher.find() && fileCanBeImported(file)) {
					importFileAndConfigureScene(file);
				}
			}
		}
		event.setDropCompleted(success);
		event.consume();
	}

	private void importFileAndConfigureScene(File file) {
		myFiles.add(file);
		Controller.IMPORT_MANAGER.importVideoFile(file);
		if (myDriverSessionTable.isDisabled()) {
			myDriverSessionTable.setDisable(false);
		}
		myControl.enableConsumeDriverFilesItem();
	}

	private boolean fileCanBeImported(File file) {
		return !myFiles.contains(file)
				&& Controller.IMPORT_MANAGER.canImport(file);
	}

	private Matcher getFilePatternMatcher(String filePath) {
		String dateString = mySelectedDate.format(DateTimeFormatter
				.ofPattern("MM-dd-yyyy"));
		Pattern pattern = Pattern.compile("kogo_(\\w{3})_(" + dateString + ")");
		Matcher matcher = pattern.matcher(filePath);
		return matcher;
	}

	private void handleDragOver(DragEvent event) {
		Dragboard db = event.getDragboard();
		if (db.hasFiles())
			event.acceptTransferModes(TransferMode.COPY);
		else
			event.consume();
	}

	private void addColToTable() {
		TableColumn<File, String> importedFileCol = new TableColumn<File, String>(
				"Imported Files");
		importedFileCol
				.setCellValueFactory(new PropertyValueFactory<File, String>(
						"name"));
		importedFileCol.prefWidthProperty().bind(this.widthProperty());
		this.getColumns().add(importedFileCol);
	}

	public void reset() {
		myFiles.clear();
		this.setDisable(true);
	}

	public void setDate(LocalDate date) {
		mySelectedDate = date;
	}
}
