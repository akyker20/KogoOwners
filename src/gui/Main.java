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

		HBox h = new HBox();
		final TextField addCompany = new TextField();
		addCompany.setPromptText("Company");
		final TextField addTitle = new TextField();
		addTitle.setPromptText("Video Title");
		final TextField addPlays = new TextField();
		addPlays.setPromptText("Num Plays");
		final TextField addLength = new TextField();
		addLength.setPromptText("Length");

		final Button addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				videoList.add(new Video(
						addCompany.getText(),
						addTitle.getText(),
						Integer.parseInt(addPlays.getText()),
						Integer.parseInt(addLength.getText())));
				addCompany.clear();
				addTitle.clear();
				addPlays.clear();
				addLength.clear();
			}
		});

		h.getChildren().addAll(addCompany, addTitle, addPlays, addLength, addButton);
		h.setSpacing(3);
		centerContainer.getChildren().addAll(new VideoTable(videoList), h);
	}

}
