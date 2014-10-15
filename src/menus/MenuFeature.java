package menus;

import gui.GUIController;
import gui.VideoTable;
import javafx.scene.control.MenuBar;

/**
 * Menu bar to add menus.
 * @author Austin Kyker
 *
 */
public class MenuFeature extends MenuBar {
	public MenuFeature(VideoTable table, GUIController controller){
		this.getMenus().add(new FileMenu(table, controller));
	}
}
