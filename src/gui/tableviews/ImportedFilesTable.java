package gui.tableviews;

import gson.InputDriverFileXMLParser;
import gui.Controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import video.PlayedVideo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class ImportedFilesTable extends TableView<File> {
	
	private InputDriverFileXMLParser myDriverParser;
	private ObservableList<File> myFiles;
	private LocalDate mySelectedDate;
	
	public ImportedFilesTable(ObservableList<PlayedVideo> importedVideos, TableView<PlayedVideo> myDriverSessionTable) throws ParserConfigurationException, SAXException, IOException{
		myDriverParser = new InputDriverFileXMLParser(importedVideos);
		myFiles = FXCollections.observableArrayList();
		
		addColToTable();
		
		setDisable(true);
		setPrefWidth(260);
		setItems(myFiles);
		
		mySelectedDate = LocalDate.now();


		this.setOnDragOver(event->handleDragOver(event));

		// When a file is actually dropped it is validated to ensure it is from the correct day
		// THIS NEEDS TO BE CLEANED UP!!!
		this.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles()) {
					success = true;
					String filePath = null;
					for (File file:db.getFiles()) {
						filePath = file.getAbsolutePath();
						String dateString = mySelectedDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
				    	Pattern pattern = Pattern.compile("kogo_(\\w{3})_("+ dateString +")");
						Matcher matcher = pattern.matcher(filePath);
				    	if(matcher.find()){
							if(!myFiles.contains(file) && canImport(file)){
								myFiles.add(file);
								try {
									myDriverParser.parseFile(file);
									if(myDriverSessionTable.isDisabled()){
										myDriverSessionTable.setDisable(false);
									}
									Controller.enableConsumeDriverFilesItem();
									
								} catch (SAXException | IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
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
		TableColumn<File, String> importedFileCol = new TableColumn<File, String>("Imported Files");
		importedFileCol.setCellValueFactory(new PropertyValueFactory<File, String>("name"));
		importedFileCol.prefWidthProperty().bind(this.widthProperty());
		this.getColumns().add(importedFileCol);
	}

	protected boolean canImport(File f) {
//		return Controller.XML_CONTROLLER.canImport(f);
		return true;
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
