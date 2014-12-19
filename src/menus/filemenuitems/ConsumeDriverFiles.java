package menus.filemenuitems;

import javafx.scene.control.MenuItem;
import control.FileControl;

public class ConsumeDriverFiles extends MenuItem {
	public ConsumeDriverFiles(FileControl control){
		super("Consume Driver Files");
		this.setDisable(true);
		this.setOnAction(event->control.consumeDriverFiles());
	}
}
