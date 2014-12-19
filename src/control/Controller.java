package control;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import gson.GSONFileReader;
import gson.GSONFileWriter;
import gui.DriverDeliverableBuilder;
import gui.ImportedFilesManager;
import gui.NewVideoPrompt;
import gui.scenes.ImportFilesScene;
import gui.scenes.TableScene;
import gui.tableviews.VideoTable;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import menus.FileMenu;
import utilities.popups.SuccessPopup;
import video.ActiveVideo;
import video.LoadedVideo;

/**
 * The purpose of this class is to create the scene and add it to the stage and
 * then display the stage.
 * 
 * @author Austin Kyker
 *
 */
public class Controller extends Application implements FileControl {

	private static final String CREATED_DRIVER_DEL_MSG = "Successfully created driver deliverable. "
			+ "Refresh and check the ./drivers/ folder.";
	public static final int NUM_DRIVERS = 8;
	private static final int SCREEN_WIDTH = 700;
	private static final int SCREEN_HEIGHT = 350;
	public static final GSONFileWriter GSON_WRITER = new GSONFileWriter();
	public static final GSONFileReader GSON_READER = new GSONFileReader();
	public static final ImportedFilesManager IMPORT_MANAGER = new ImportedFilesManager();
	private static final DriverDeliverableBuilder DRIVER_DELIVERABLE_BUILDER = new DriverDeliverableBuilder();
	private static final String VIDEO_TABLE_TITLE = "Advertisment Data";
	private static final String IMPORT_FILES_TITLE = "Import Files";
	private static final String FILE_CHOOSER_TITLE = "Select Video File";
	private static final String VIDEOS_DIR = "./videos/";

	private ObservableList<LoadedVideo> myVideosList;
	private Stage myStage;
	private TableScene myTableScene;
	private ImportFilesScene myImportFilesScene;
	private FileMenu myFileMenu;
	private Scene myScene;
	private BorderPane myPane;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		myStage = stage;
		myVideosList = GSON_READER.readVideosFromMasterJSON();
		addSceneToStage();
		setupMenu();
		createScenes();
		configureAndShowStage();
	}

	private void createScenes() {
		myTableScene = new TableScene(new VideoTable(myVideosList),
				new NewVideoPrompt(this));
		myImportFilesScene = new ImportFilesScene(this);
	}

	private void setupMenu() {
		MenuBar menuBar = new MenuBar();
		myFileMenu = new FileMenu((FileControl) this);
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

	public void consumeDriverFiles() {
		IMPORT_MANAGER.rewriteImportFiles();
		myVideosList.clear();
		myVideosList.addAll(IMPORT_MANAGER.recalculateVideoData());
		GSON_WRITER.writeMasterFile(myVideosList);
		showVideosPane();
	}

	private void showImportFilePane() {
		myFileMenu.configureImportFileMenuOptions();
		myStage.setTitle(IMPORT_FILES_TITLE);
		myPane.setCenter(myImportFilesScene);
	}

	private void showVideosPane() {
		myFileMenu.configureVideoTableMenuOptions();
		myStage.setTitle(VIDEO_TABLE_TITLE);
		myPane.setCenter(myTableScene);
	}

	public void uploadDriverFiles() {
		myImportFilesScene.reset();
		showImportFilePane();
	}

	public void enableConsumeDriverFilesItem() {
		myFileMenu.disableConsumeDriverFilesItem(false);
	}

	public void backToTable() {
		showVideosPane();
	}

	public void buildDriverFile(String dateStr) {
		DRIVER_DELIVERABLE_BUILDER.buildDriverDeliverableFolder(dateStr);
		List<ActiveVideo> videos = myVideosList.stream()
				.map(video -> new ActiveVideo(video, NUM_DRIVERS))
				.collect(Collectors.toList());
		String driverJSONFileName = "./driver/deliverable_" + dateStr
				+ "/kogo_" + dateStr + ".json";
		GSON_WRITER.writeDriverFile(driverJSONFileName, videos);
		new SuccessPopup(CREATED_DRIVER_DEL_MSG);
	}

	public boolean addNewAdvertisement(LoadedVideo createdAd) {
		if (adCanBeAdded(createdAd)) {
			myVideosList.add(createdAd);
			GSON_WRITER.writeMasterFile(myVideosList);
			return true;
		}
		return false;
	}

	private boolean adCanBeAdded(LoadedVideo createdAd) {
		return adHasNotAlreadyBeenAdded(createdAd)
				&& correspondingVideoFileIsSelectedAndSaved(createdAd);
	}

	private boolean adHasNotAlreadyBeenAdded(LoadedVideo createdAd) {
		return myVideosList.stream().filter(video -> video.equals(createdAd))
				.count() == 0;
	}

	private boolean correspondingVideoFileIsSelectedAndSaved(
			LoadedVideo createdAd) {
		File selectedFileFromFileChooser = getFileFromFileChooser(createdAd);
		return saveVideoFile(selectedFileFromFileChooser, createdAd);
	}

	private boolean saveVideoFile(File fileFromFileChooser,
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

	private File getFileFromFileChooser(LoadedVideo createdAd) {
		FileChooser myFileChooser = createFileChooser(createdAd);
		return myFileChooser.showOpenDialog(new Stage());
	}

	/**
	 * First make a video and place it in the videos folder.
	 * 
	 * @param createdAd
	 */
	private FileChooser createFileChooser(LoadedVideo createdAd) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle(FILE_CHOOSER_TITLE);
		FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter(
				"Video files (*.mp4)",
				getRequiredCorrespondingVideoFileName(createdAd));
		chooser.getExtensionFilters().add(extentionFilter);
		chooser.setInitialDirectory(new File(VIDEOS_DIR));
		return chooser;
	}

	private String getRequiredCorrespondingVideoFileName(LoadedVideo createdAd) {
		return createdAd.getMyCompany().replace(" ", "") + "_"
				+ createdAd.getMyName().replace(" ", "") + ".mp4";
	}
}
