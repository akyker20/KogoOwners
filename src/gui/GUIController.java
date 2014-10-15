package gui;
import gui.scenes.ImportFilesScene;
import gui.scenes.TableScene;
import gui.tableviews.VideoTable;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import control.Controller;
import video.PlayedVideo;
import video.Video;
import xmlcontrol.DriverXMLParser;
import menus.MenuFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The purpose of this class is to create the scene and add it to the stage
 * and then display the stage.
 * @author Austin Kyker
 *
 */
public class GUIController {

    public static final int SCREEN_WIDTH = 700;
    public static final int SCREEN_HEIGHT = 350;
    public static final String STYLESHEET_PACKAGE = "Stylesheets/";
    
    private Stage myStage;
    private Controller myControl;
    private TableScene myTableScene;
    private ImportFilesScene myImportFilesScene;
    
    public GUIController(Stage stage, ObservableList<Video> videoList, Controller control,
    		DriverXMLParser parser, ObservableList<PlayedVideo> importedVideos) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException{
    	myStage = stage;
    	myControl = control;
    	VideoTable videoTable = new VideoTable(videoList, this);	
		NewVideoPrompt videoPrompt = new NewVideoPrompt(videoList, this);
		myTableScene = new TableScene(new BorderPane(), videoTable, videoPrompt, new MenuFeature(videoTable, this));
		myImportFilesScene = new ImportFilesScene(new BorderPane(),  new MenuFeature(videoTable, this), parser, importedVideos);
		configureAndShowStage();
    }

	private void configureAndShowStage() {
		myStage.setScene(myTableScene);
        myStage.setTitle("Kogo Master");
        myStage.setResizable(false);
        myStage.show();
	}

	public void editMasterFile() throws TransformerException {
		myControl.editMasterFile();
	}

	public void buildDriverFile(String fileName) throws TransformerException {
		myControl.buildDriverFile(fileName);	
	}

	public void uploadDriverFiles() {
		myStage.setScene(myImportFilesScene);
	}
}
