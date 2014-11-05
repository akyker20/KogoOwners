package menus.filemenuitems;

import menus.FileMenu;
import javafx.scene.control.MenuItem;

/**
 * A MenuItem to allow user to remove an advertisement.
 * @author Austin Kyker
 *
 */
public class RemoveVideoItem extends MenuItem {
	
	public RemoveVideoItem(FileMenu fileMenu){
		super("Remove Video");
//		setOnAction(event->fileMenu.tryToRemoveVideo());
	}
}
