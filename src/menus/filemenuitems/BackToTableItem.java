package menus.filemenuitems;

import gui.Controller;
import menus.FileMenu;
import javafx.scene.control.MenuItem;

public class BackToTableItem extends MenuItem {
	public BackToTableItem(FileMenu fileMenu, Controller control){
		super("Back to table");
		setOnAction(event->control.backToTable());
	}
}
