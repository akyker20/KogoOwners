package gui;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
		XMLParser videoParser = new XMLParser(videoList, stage);
		NewVideoPrompt newVideoPrompt = new NewVideoPrompt(videoList);
		centerContainer.getChildren().addAll(new VideoTable(videoList), newVideoPrompt);
	}

}
