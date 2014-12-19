package gui.tableviews;

import gui.Controller;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import video.ActiveVideo;

public class ImportedFilesTable extends TableView<File> {

	private ObservableList<File> myFiles;
	private LocalDate mySelectedDate;
	private Controller myControl;

	public ImportedFilesTable(TableView<ActiveVideo> myDriverSessionTable, Controller control) {
		myFiles = FXCollections.observableArrayList();
		myControl = control;
		addColToTable();
		
		setDisable(true);
		setPrefWidth(260);
		setItems(myFiles);

		mySelectedDate = LocalDate.now();

		this.setOnDragOver(event -> handleDragOver(event));

		// When a file is actually dropped it is validated to ensure it is from
		// the correct day
		// THIS NEEDS TO BE CLEANED UP!!!
		this.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles()) {
					success = true;
					String filePath = null;
					for (File file : db.getFiles()) {
						filePath = file.getAbsolutePath();
						String dateString = mySelectedDate
								.format(DateTimeFormatter
										.ofPattern("MM-dd-yyyy"));
						Pattern pattern = Pattern.compile("kogo_(\\w{3})_("
								+ dateString + ")");
						Matcher matcher = pattern.matcher(filePath);
						if (matcher.find()) {
							if (!myFiles.contains(file)
									&& Controller.IMPORT_MANAGER.canImport(file)) {
								myFiles.add(file);
								Controller.IMPORT_MANAGER.importVideoFile(file);
								if (myDriverSessionTable.isDisabled()) {
									myDriverSessionTable.setDisable(false);
								}
								myControl.enableConsumeDriverFilesItem();
							}
						}
					}
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
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

	public List<File> getFiles() {
		return myFiles;
	}

	public void setDate(LocalDate date) {
		mySelectedDate = date;

	}
}
