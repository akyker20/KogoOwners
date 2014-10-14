package menus;

import gui.VideoTable;
import javafx.scene.control.MenuBar;

/**
 * Menu bar to add menus.
 * @author Austin Kyker
 *
 */
public class MenuFeature extends MenuBar {
	public MenuFeature(VideoTable table){
		this.getMenus().add(new FileMenu(table));
	}
}
