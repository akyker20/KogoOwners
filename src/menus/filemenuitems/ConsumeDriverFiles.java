package menus.filemenuitems;

import gui.Controller;
import javafx.scene.control.MenuItem;
import menus.FileMenu;

public class ConsumeDriverFiles extends MenuItem {
	public ConsumeDriverFiles(FileMenu menu){
		super("Consume Driver Files");
		this.setDisable(true);
		this.setOnAction(event->Controller.consumeDriverFiles());
	}
}
