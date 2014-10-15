package menus.filemenuitems;

import gui.GUIController;
import menus.FileMenu;
import javafx.scene.control.MenuItem;

public class UploadDriverFiles extends MenuItem  {
	public UploadDriverFiles(GUIController controller){
		super("Upload Driver Files");
		setOnAction(event->controller.uploadDriverFiles());
	}
}
