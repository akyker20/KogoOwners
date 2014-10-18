package gui.tableviews;

import gui.GUIController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import video.PlayedVideo;
import xmlcontrol.DriverXMLParser;
import xmlcontrol.ImportedFilesXMLParser;
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
	
	private DriverXMLParser myDriverParser;
	private ObservableList<File> myFiles;
	
	public ImportedFilesTable(ObservableList<PlayedVideo> importedVideos, TableView<PlayedVideo> myDriverSessionTable) throws ParserConfigurationException, SAXException, IOException{
		myDriverParser = new DriverXMLParser(importedVideos);
		myFiles = FXCollections.observableArrayList();
		setItems(myFiles);
		
		TableColumn<File, String> importedFileCol = new TableColumn<File, String>("Imported Files");
		importedFileCol.setCellValueFactory(new PropertyValueFactory<File, String>("name"));
		importedFileCol.prefWidthProperty().bind(this.widthProperty());
		getColumns().add(importedFileCol);
		setDisable(true);
		setPrefWidth(260);
		
    	Pattern pattern = Pattern.compile("kogo_(\\w{3})_(\\d{1,2})_(\\d{1,2})_(\\d{2})");

		// When a file is dragged over the scene, the background becomes
		// green and a copy message is displayed near the mouse.
		this.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (db.hasFiles()) {
					event.acceptTransferModes(TransferMode.COPY);
				} else {
					event.consume();
				}
			}
		});

		// When a file is actually dropped it is validated to
		// ensure it has the correct name. The controller is 
		// then called to initialize the driving environment.
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
						Matcher matcher = pattern.matcher(filePath);
				    	if(matcher.find()){
							if(!myFiles.contains(file) && canImport(file)){
								myFiles.add(file);
								try {
									myDriverParser.parseFile(file);
									if(myDriverSessionTable.isDisabled()){
										myDriverSessionTable.setDisable(false);
									}
									GUIController.enableConsumeDriverFilesItem();
									
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

	protected boolean canImport(File f) {
		System.out.println("Can import: " + GUIController.canImport(f));
		return GUIController.canImport(f);
	}

	public void reset() {
		myFiles.clear();
		this.setDisable(true);
	}

	public List<File> getFiles() {
		return myFiles;
	}
}
