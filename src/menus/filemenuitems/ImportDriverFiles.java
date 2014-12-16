package menus.filemenuitems;

import gui.Controller;
import javafx.scene.control.MenuItem;

public class ImportDriverFiles extends MenuItem  {
	public ImportDriverFiles(){
		super("Upload Driver Files");
		setOnAction(event->Controller.uploadDriverFiles());
	}
}
