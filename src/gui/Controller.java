package gui;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import gson.writers.GSONFileReader;
import gson.writers.GSONFileWriter;
import gui.scenes.ImportFilesScene;
import gui.scenes.TableScene;
import gui.tableviews.VideoTable;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import menus.FileMenu;
import video.LoadedVideo;
import video.PlayedVideo;

/**
 * The purpose of this class is to create the scene and add it to the stage and
 * then display the stage.
 * 
 * @author Austin Kyker
 *
 */
public class Controller extends Application {

	public static final int NUM_DRIVERS = 8;
	private static final int SCREEN_WIDTH = 700;
	private static final int SCREEN_HEIGHT = 350;
	public static final GSONFileWriter GSON_WRITER = new GSONFileWriter();
	public static final GSONFileReader GSON_READER = new GSONFileReader();
	private static final DriverDeliverableBuilder DRIVER_DELIVERABLE_BUILDER = 
			new DriverDeliverableBuilder();
	private static final String VIDEO_TABLE_TITLE = "Advertisment Data";
	private static final String IMPORT_FILES_TITLE = "Import Files";
	private static final String FILE_CHOOSER_TITLE = "Select Video File";
	private static final String VIDEOS_DIR = "./videos/";

	private static ObservableList<LoadedVideo> myVideosList;
	private static ObservableList<PlayedVideo> myImportedVideos;

	private static Stage myStage;
	private static TableScene myTableScene;
	private static ImportFilesScene myImportFilesScene;
	private static FileMenu myFileMenu;
	private static Scene myScene;
	private static BorderPane myPane;
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		myStage = stage;
		myVideosList = GSON_READER.readVideosFromMasterJSON();
		addSceneToStage();
		setupMenu();
		createScenes();
		configureAndShowStage();
	}

	private void createScenes() throws Exception {
		myTableScene = new TableScene(new VideoTable(myVideosList),
				new NewVideoPrompt());
		myImportedVideos = FXCollections.observableArrayList();
		myImportFilesScene = new ImportFilesScene(myImportedVideos);
	}

	private void setupMenu() {
		MenuBar menuBar = new MenuBar();
		myFileMenu = new FileMenu();
		menuBar.getMenus().add(myFileMenu);
		myPane.setTop(menuBar);
	}

	private void addSceneToStage() {
		myPane = new BorderPane();
		myScene = new Scene(myPane, SCREEN_WIDTH, SCREEN_HEIGHT);
		myStage.setScene(myScene);
	}

	private void configureAndShowStage() {
		showVideosPane();
		myStage.setResizable(false);
		myStage.show();
	}

	public static void consumeDriverFiles() {
//		XML_CONTROLLER.consumeXMLFiles(myImportedVideos,
//				myImportFilesScene.getFiles());
		ArrayList<LoadedVideo> videosToBeRefreshed = new ArrayList<LoadedVideo>();
		for (LoadedVideo video : myVideosList) {
			videosToBeRefreshed.add(video);
		}
		myVideosList.clear();
		myVideosList.addAll(videosToBeRefreshed);
		showVideosPane();
	}

	private static void showImportFilePane() {
		myFileMenu.configureImportFileMenuOptions();
		myStage.setTitle(IMPORT_FILES_TITLE);
		myPane.setCenter(myImportFilesScene);
	}

	private static void showVideosPane() {
		myFileMenu.configureVideoTableMenuOptions();
		myStage.setTitle(VIDEO_TABLE_TITLE);
		myPane.setCenter(myTableScene);
	}

	public static void uploadDriverFiles() {
		myImportFilesScene.reset();
		showImportFilePane();
	}

	public static void enableConsumeDriverFilesItem() {
		myFileMenu.disableConsumeDriverFilesItem(false);
	}

	public static void backToTable() {
		showVideosPane();
	}

	public static void buildDriverFile(String fileName) {
		DRIVER_DELIVERABLE_BUILDER.buildDriverDeliverableFolder(fileName);
		List<PlayedVideo> videos = myVideosList.stream()
				.map(video -> new PlayedVideo(video.getMyName(),
						video.getMyCompany(), video.getMyLength(), 
						video.getMyPlaysRemaining()/NUM_DRIVERS))
				.collect(Collectors.toList());
		String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		
		String driverJSONFileName = "./driver/deliverable_" + dateStr + "/" + fileName;
		GSON_WRITER.writeDriverFile(driverJSONFileName, videos);
	}

	// Code below attempts to add new advertisement

	public static boolean addNewAdvertisement(LoadedVideo createdAd) {
		if (adCanBeAdded(createdAd)) {
			myVideosList.add(createdAd);
			GSON_WRITER.writeMasterFile(myVideosList);
			return true;
		}
		return false;
	}

	private static boolean adCanBeAdded(LoadedVideo createdAd) {
		return adHasNotAlreadyBeenImported(createdAd)
				&& correspondingVideoFileIsSelectedAndSaved(createdAd);
	}

	private static boolean adHasNotAlreadyBeenImported(LoadedVideo createdAd) {
		return myVideosList.stream().filter(video -> video.sameAs(createdAd))
				.count() == 0;
	}

	private static boolean correspondingVideoFileIsSelectedAndSaved(
			LoadedVideo createdAd) {
		File selectedFileFromFileChooser = getFileFromFileChooser(createdAd);
		return saveVideoFile(selectedFileFromFileChooser, createdAd);
	}

	private static boolean saveVideoFile(File fileFromFileChooser,
			LoadedVideo createdAd) {
		if (fileFromFileChooser == null)
			return false;
		try {
			Files.copy(fileFromFileChooser.toPath(), new File(
					getRequiredCorrespondingVideoFileName(createdAd)).toPath(),
					REPLACE_EXISTING);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static File getFileFromFileChooser(LoadedVideo createdAd) {
		FileChooser myFileChooser = createFileChooser(createdAd);
		return myFileChooser.showOpenDialog(new Stage());
	}

	/**
	 * First make a video and place it in the videos folder.
	 * @param createdAd
	 * @return
	 */
	private static FileChooser createFileChooser(LoadedVideo createdAd) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle(FILE_CHOOSER_TITLE);
//		FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter(
//				"Video files (*.mp4)",
//				getRequiredCorrespondingVideoFileName(createdAd));
//		chooser.getExtensionFilters().add(extentionFilter);
		chooser.setInitialDirectory(new File(VIDEOS_DIR));
		return chooser;
	}

	private static String getRequiredCorrespondingVideoFileName(
			LoadedVideo createdAd) {
		return VIDEOS_DIR + createdAd.getMyCompany().replace(" ", "") + "_"
				+ createdAd.getMyName().replace(" ", "") + ".mp4";
	}
}
