package menus;

import control.Controller;
import gui.GUIController;
import gui.tableviews.VideoTable;
import javafx.scene.control.MenuBar;

/**
 * Menu bar to add menus.
 * @author Austin Kyker
 *
 */
public class MenuFeature extends MenuBar {
	
	private FileMenu myMenu;
	
	public MenuFeature(VideoTable table, GUIController controller){
		myMenu = new FileMenu(table, controller);
		this.getMenus().add(myMenu);
	}

	public void enableConsumeDriverFilesItem() {
		myMenu.enableConsumeDriverFilesItem();
		
	}
}
