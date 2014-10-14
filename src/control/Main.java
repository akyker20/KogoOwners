package control;
import menus.MenuFeature;
import gui.NewVideoPrompt;
import gui.StageInitializer;
import gui.VideoTable;
import video.Video;
import xmlcontrol.XMLParser;
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
public class Main extends Application {

	public static final int NUM_DRIVERS = 8;
	
	public static void main(String[] args){ launch(args); }

	@Override
	public void start(Stage stage) throws Exception {
		final ObservableList<Video> videoList = FXCollections.observableArrayList();
		BorderPane pane = StageInitializer.init(stage);
		VBox centerContainer = new VBox(10);
		centerContainer.setPadding(new Insets(10));
		pane.setCenter(centerContainer);
		XMLParser videoParser = new XMLParser(videoList);
		XMLWriter xmlWriter = new XMLWriter();
		VideoTable videoTable = new VideoTable(videoList, xmlWriter);
		NewVideoPrompt videoPrompt = new NewVideoPrompt(videoList, xmlWriter);
		centerContainer.getChildren().addAll(videoTable, videoPrompt);
		pane.setTop(new MenuFeature(videoTable));
	}
}