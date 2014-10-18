package gui;

import gui.scenes.ImportFilesScene;
import gui.scenes.TableScene;
import gui.tableviews.VideoTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import video.PlayedVideo;
import video.LoadedVideo;
import xmlcontrol.DriverXMLParser;
import xmlcontrol.XMLController;
import menus.MenuFeature;
import javafx.application.Application;
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
public class GUIController extends Application {

	public static final int NUM_DRIVERS = 8;
    private static final int SCREEN_WIDTH = 700;
    private static final int SCREEN_HEIGHT = 350;
	private static final String VIDEO_TABLE_TITLE = "Advertisment Data";
	private static final String IMPORT_FILES_TITLE = "Import Files";

	private static ObservableList<LoadedVideo> myVideosList;
	private static ObservableList<PlayedVideo> myImportedVideos;
	private static XMLController myXMLController;

	private static Stage myStage;
	private static TableScene myTableScene;
	private static ImportFilesScene myImportFilesScene;
	private static MenuFeature myMenuBar;
	private static Scene myScene;
	private static BorderPane myPane;

	public static void main(String[] args){ launch(args); }

	@Override
	public void start(Stage stage) throws Exception {
		myVideosList = FXCollections.observableArrayList();
		myImportedVideos = FXCollections.observableArrayList();
		myXMLController = new XMLController(myVideosList);

		myStage = stage;
		myPane = new BorderPane();
		myScene = new Scene(myPane, SCREEN_WIDTH, SCREEN_HEIGHT);
		myStage.setScene(myScene);
		
		VideoTable videoTable = new VideoTable(myVideosList);	
		myMenuBar = new MenuFeature(videoTable);
		myPane.setTop(myMenuBar);
		
		NewVideoPrompt videoPrompt = new NewVideoPrompt(myVideosList);
		myTableScene = new TableScene(videoTable, videoPrompt);
		myImportFilesScene = new ImportFilesScene(myImportedVideos);

		configureAndShowStage();
	}

	private void configureAndShowStage() {
		showVideosPane();
		myStage.setResizable(false);
		myStage.show();
	}

	public static void editMasterFile() throws TransformerException {
		myXMLController.editMasterFile(myVideosList);	
	}

	public static void buildDriverFile(String fileName) throws TransformerException {
		myXMLController.buildDriverFile(myVideosList, fileName);
	}

	public static void consumeDriverFiles() throws TransformerException {
		myXMLController.consumeXMLFiles(myImportedVideos, myImportFilesScene.getFiles());
		ArrayList<LoadedVideo> videosToBeRefreshed = new ArrayList<LoadedVideo>();
		for(LoadedVideo video:myVideosList){
			videosToBeRefreshed.add(video);
		}
		myVideosList.clear();
		myVideosList.addAll(videosToBeRefreshed);
		showVideosPane();
	}
	
	private static void showImportFilePane(){
		myMenuBar.configureImportFileMenuOptions();
		myStage.setTitle(IMPORT_FILES_TITLE);
		myPane.setCenter(myImportFilesScene);
	}
	
	private static void showVideosPane(){
		myMenuBar.configureVideoTableFileMenuOptions();
		myStage.setTitle(VIDEO_TABLE_TITLE);
		myPane.setCenter(myTableScene);
	}
	
	public static boolean canImport(File f){
		return myXMLController.canImport(f);
	}

	public static void uploadDriverFiles() {
		myImportFilesScene.reset();
		showImportFilePane();
	}

	public static void enableConsumeDriverFilesItem() {
		myMenuBar.disableConsumeDriverFilesItem(false);
	}

	public static void backToTable() {
		showVideosPane();
	}
}
