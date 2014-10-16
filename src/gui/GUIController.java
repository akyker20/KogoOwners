package gui;

import gui.scenes.ImportFilesScene;
import gui.scenes.TableScene;
import gui.tableviews.VideoTable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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

	private static ObservableList<LoadedVideo> myVideosList;
	private static ObservableList<PlayedVideo> myImportedVideos;
	private static XMLController myXMLController;

	private static Stage myStage;
	private static TableScene myTableScene;
	private static ImportFilesScene myImportFilesScene;
	private static MenuFeature myDriverFilesMenuFeature;

	public static void main(String[] args){ launch(args); }

	@Override
	public void start(Stage stage) throws Exception {
		myVideosList = FXCollections.observableArrayList();
		myImportedVideos = FXCollections.observableArrayList();
		myXMLController = new XMLController(myVideosList);

		myStage = stage;
		VideoTable videoTable = new VideoTable(myVideosList);	
		NewVideoPrompt videoPrompt = new NewVideoPrompt(myVideosList);
		myTableScene = new TableScene(videoTable, videoPrompt, new MenuFeature(videoTable));
		myDriverFilesMenuFeature = new MenuFeature(videoTable);
		myImportFilesScene = new ImportFilesScene(myDriverFilesMenuFeature, myImportedVideos);

		configureAndShowStage();
	}

	private void configureAndShowStage() {
		myStage.setScene(myTableScene);
		myStage.setTitle("Kogo Master");
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
		myXMLController.consumeXMLFiles(myImportedVideos);
		ArrayList<LoadedVideo> videosToBeRefreshed = new ArrayList<LoadedVideo>();
		for(LoadedVideo video:myVideosList){
			videosToBeRefreshed.add(video);
		}
		myVideosList.clear();
		myVideosList.addAll(videosToBeRefreshed);
		myStage.setScene(myTableScene);
	}

	public static void uploadDriverFiles() {
		myStage.setScene(myImportFilesScene);
		myImportFilesScene.reset();
	}

	public static void enableConsumeDriverFilesItem() {
		myDriverFilesMenuFeature.enableConsumeDriverFilesItem();
	}
}
