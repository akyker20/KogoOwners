package menus.filemenuitems;

import javafx.scene.control.MenuItem;

/**
 * MenuItem for undoing the removal of a video.
 * @author Austin Kyker
 *
 */
public class UndoRemoveVideoItem extends MenuItem {
	
	public UndoRemoveVideoItem(){
		super("Undo Remove");
		setDisable(true);
//		setOnAction(event->fileMenu.tryToUndoRemove());
	}
}
