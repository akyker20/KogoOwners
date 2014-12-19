package menus.filemenuitems;

import javafx.scene.control.MenuItem;
import control.FileControl;

public class ImportDriverFiles extends MenuItem  {
	public ImportDriverFiles(FileControl control){
		super("Upload Driver Files");
		setOnAction(event->control.uploadDriverFiles());
	}
}
