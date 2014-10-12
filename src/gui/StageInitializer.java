package gui;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class StageInitializer {

    public static final int SCREEN_WIDTH = 700;
    public static final int SCREEN_HEIGHT = 350;
    public static final String STYLESHEET_PACKAGE = "Stylesheets/";

    public static BorderPane init (Stage stage) {
        BorderPane pane = new BorderPane();
        pane.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        Scene scene = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT, Color.CORNSILK);
        scene.getStylesheets().add(STYLESHEET_PACKAGE + "style.css");
        stage.setScene(scene);
        stage.setTitle("Kogo Master");
        stage.setResizable(false);
        stage.show();
        return pane;
    }
}
