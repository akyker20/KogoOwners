package gui.scenes;

import gui.NewVideoPrompt;
import gui.tableviews.VideoTable;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 * This class represents the scene where the owner can add, remove, and edit
 * video entries.
 * @author Austin Kyker
 *
 */
public class TableScene extends GUIScene {

	public TableScene(VideoTable videoTable, NewVideoPrompt videoPrompt) {
        VBox centerContainer = new VBox(10);
		centerContainer.setPadding(new Insets(10));
		centerContainer.getChildren().addAll(videoTable, videoPrompt.getContainer());
		this.setCenter(centerContainer);		
	}
}