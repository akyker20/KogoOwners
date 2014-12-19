package menus.filemenuitems;

import javafx.scene.control.MenuItem;
import control.FileControl;

public class BackToTableItem extends MenuItem {
	public BackToTableItem(FileControl control){
		super("Back to table");
		setOnAction(event->control.backToTable());
	}
}
