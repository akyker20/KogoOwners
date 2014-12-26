package menus.filemenuitems;

import javafx.scene.control.MenuItem;
import control.FileControl;

public class DriverStatsItem extends MenuItem {
	public DriverStatsItem(FileControl control){
		super("Driver Stats");
		this.setOnAction(event->control.viewDriverStats());
	}
}
