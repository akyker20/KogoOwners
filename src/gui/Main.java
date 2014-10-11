package gui;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args){ launch(args); }

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane pane = StageInitializer.init(stage);
		VBox centerContainer = new VBox(10);
		final ObservableList<Video> videoList = FXCollections.observableArrayList();
		pane.setCenter(centerContainer);
		XMLParser videoParser = new XMLParser(videoList);
		XMLWriter xmlWriter = new XMLWriter();
		centerContainer.getChildren().addAll(new VideoTable(videoList, xmlWriter),
											 new NewVideoPrompt(videoList, xmlWriter));
	}
}