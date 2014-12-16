package gui.scenes;

import javafx.scene.layout.BorderPane;

public abstract class GUIScene extends BorderPane {
	
    private static final int SCREEN_WIDTH = 700;
    private static final int SCREEN_HEIGHT = 350;
    private static final String STYLESHEET_PACKAGE = "Stylesheets/style.css";

	public GUIScene() {
		this.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.getStylesheets().add(STYLESHEET_PACKAGE);
	}
}