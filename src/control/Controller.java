package control;
import javax.xml.transform.TransformerException;

import menus.MenuFeature;
import gui.NewVideoPrompt;
import gui.GUIController;
import gui.tableviews.VideoTable;
import video.PlayedVideo;
import video.Video;
import xmlcontrol.DriverXMLParser;
import xmlcontrol.MasterXMLParser;
import xmlcontrol.XMLWriter;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The purpose of this class is to create the video table view,
 * initialize the parsers, and add the menu.
 * @author Austin Kyker
 *
 */
public class Controller extends Application {

	public static final int NUM_DRIVERS = 8;
	
	private GUIController myGUIController;
	private XMLWriter myWriter;
	private ObservableList<Video> myVideosList;
	private ObservableList<PlayedVideo> myImportedVideos;
	
	public static void main(String[] args){ launch(args); }

	@Override
	public void start(Stage stage) throws Exception {
		myVideosList = FXCollections.observableArrayList();
		myImportedVideos = FXCollections.observableArrayList();
		myGUIController = new GUIController(stage, myVideosList, this, 
				new DriverXMLParser(myImportedVideos), myImportedVideos);
		new MasterXMLParser(myVideosList);
		myWriter = new XMLWriter();
	}

	public void editMasterFile() throws TransformerException {
		myWriter.editMasterFile(myVideosList);	
	}

	public void buildDriverFile(String fileName) throws TransformerException {
		myWriter.buildDriverFile(myVideosList, fileName);
		
	}

	public void consumeDriverFiles() {
		myWriter.consumeXMLFiles(myImportedVideos);
		
	}
}