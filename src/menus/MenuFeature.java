package menus;

import xmlcontrol.XMLWriter;
import gui.VideoTable;
import javafx.scene.control.MenuBar;

public class MenuFeature extends MenuBar {
	public MenuFeature(VideoTable table){
		this.getMenus().add(new FileMenu(table));
	}
}
