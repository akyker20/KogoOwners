package menus.filemenuitems;

import javafx.scene.control.MenuItem;

import menus.FileMenu;

/**
 * MenuItem for undoing the removal of a video.
 * @author Austin Kyker
 *
 */
public class UndoRemoveVideoItem extends MenuItem {
	
	public UndoRemoveVideoItem(FileMenu fileMenu){
		super("Undo Remove");
		setDisable(true);
//		setOnAction(event->fileMenu.tryToUndoRemove());
	}
}
