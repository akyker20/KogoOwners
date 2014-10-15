package gui.scenes;

import menus.MenuFeature;
import gui.GUIController;
import gui.NewVideoPrompt;
import gui.tableviews.VideoTable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class TableScene extends Scene {

	public TableScene(BorderPane root, VideoTable videoTable, NewVideoPrompt videoPrompt, 
			MenuFeature menuFeature) {
		super(root, GUIController.SCREEN_WIDTH, GUIController.SCREEN_HEIGHT, Color.CORNSILK);
        root.setPrefSize(GUIController.SCREEN_WIDTH, GUIController.SCREEN_HEIGHT);
        getStylesheets().add(GUIController.STYLESHEET_PACKAGE + "style.css");
        VBox centerContainer = new VBox(10);
		centerContainer.setPadding(new Insets(10));
		root.setCenter(centerContainer);		
		centerContainer.getChildren().addAll(videoTable, videoPrompt);
		root.setTop(menuFeature);
	}
}