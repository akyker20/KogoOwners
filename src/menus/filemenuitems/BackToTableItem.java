package menus.filemenuitems;

import menus.FileMenu;
import javafx.scene.control.MenuItem;

public class BackToTableItem extends MenuItem {
	public BackToTableItem(FileMenu fileMenu){
		super("Back to table");
		setOnAction(event->fileMenu.backToVideoTable());
	}
}
