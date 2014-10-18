package menus;

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
	
	public MenuFeature(VideoTable table){
		myMenu = new FileMenu(table);
		this.getMenus().add(myMenu);
	}

	public void disableConsumeDriverFilesItem(boolean disableStatus) {
		myMenu.disableConsumeDriverFilesItem(disableStatus);
		
	}

	public void configureImportFileMenuOptions() {
		myMenu.configureImportFileMenuOptions();
	}
	public void configureVideoTableFileMenuOptions() {
		myMenu.configureVideoTableMenuOptions();
	}
}
