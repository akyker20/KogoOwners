package menus.filemenuitems;

import gui.GUIController;
import javafx.scene.control.MenuItem;

public class ImportDriverFiles extends MenuItem  {
	public ImportDriverFiles(){
		super("Upload Driver Files");
		setOnAction(event->GUIController.uploadDriverFiles());
	}
}
