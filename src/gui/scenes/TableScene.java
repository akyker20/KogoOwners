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

/**
 * This class represents the scene where the owner can add, remove, and edit
 * video entries.
 * @author Austin Kyker
 *
 */
public class TableScene extends GUIScene {

	public TableScene(BorderPane root, VideoTable videoTable, NewVideoPrompt videoPrompt, 
			MenuFeature menuFeature) {
		super(root, menuFeature);
        VBox centerContainer = new VBox(10);
		centerContainer.setPadding(new Insets(10));
		root.setCenter(centerContainer);		
		centerContainer.getChildren().addAll(videoTable, videoPrompt);
	}
}