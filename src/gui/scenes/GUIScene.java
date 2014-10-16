package gui.scenes;

import menus.MenuFeature;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public abstract class GUIScene extends Scene {
	
    private static final int SCREEN_WIDTH = 700;
    private static final int SCREEN_HEIGHT = 350;
    private static final String STYLESHEET_PACKAGE = "Stylesheets/style.css";
	
	protected BorderPane myRoot;

	public GUIScene(BorderPane root, MenuFeature menuFeature) {
		super(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		myRoot = root;
		getStylesheets().add(STYLESHEET_PACKAGE);
		myRoot.setTop(menuFeature);
	}
}