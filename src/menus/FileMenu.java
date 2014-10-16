package menus;

import javax.xml.transform.TransformerException;

import menus.filemenuitems.ConsumeDriverFiles;
import menus.filemenuitems.GenerateDriverFileMenu;
import menus.filemenuitems.RemoveVideoItem;
import menus.filemenuitems.UndoRemoveVideoItem;
import menus.filemenuitems.ImportDriverFiles;
import gui.GUIController;
import gui.tableviews.VideoTable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Class will be used to load files such as previously saved commands or
 * grid configurations.
 * @author Austin Kyker
 *
 */
public class FileMenu extends Menu {

	private VideoTable myVideoTable;
	private MenuItem myUndoRemoveVideo;
	private MenuItem myRemoveVideo;
	private GenerateDriverFileMenu myGenerateDriverFileMenu;
	private ImportDriverFiles myUploadDriverFiles;
	private ConsumeDriverFiles myConsumeDriverFiles;
	private GUIController myGUIController;

	public FileMenu(VideoTable table, GUIController controller){
		
		myGUIController = controller;
		myVideoTable = table;

		this.setText("File");

		myUndoRemoveVideo = new UndoRemoveVideoItem(this);
		myRemoveVideo = new RemoveVideoItem(this);
		myGenerateDriverFileMenu = new GenerateDriverFileMenu(this);
		myUploadDriverFiles = new ImportDriverFiles(controller);
		try {
			myConsumeDriverFiles = new ConsumeDriverFiles(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.getItems().addAll(myRemoveVideo, myUndoRemoveVideo, 
				myGenerateDriverFileMenu, myUploadDriverFiles, myConsumeDriverFiles);
	}

	/**
	 * Removes a video and if there are no videos that have been removed (no
	 * videos on the undo stack), then the UndoRemoveVideo icon is enabled.
	 */
	public void tryToRemoveVideo() {
		try {
			if(myVideoTable.removeSelectedItem() && myUndoRemoveVideo.isDisable()){
				myUndoRemoveVideo.setDisable(false);
			}
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Tries to undo a video removal. If there are no longer any removed videos on the
	 * stack after the video is added back in (removed from the stack), then the 
	 * UndoRemove MenuItem is disabled (There are no removals to undo).
	 */
	public void tryToUndoRemove() {
		try {
			myVideoTable.undoRemovedVideo();
			if(!myVideoTable.areRemovedVideosRemaining()){
				myUndoRemoveVideo.setDisable(true);
			}
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Attempts to generate a driver xml file with a filename that includes
	 * the input date string. This method is called by clicking on a
	 * GenerateDriverFileMenu menu item.
	 * @param dateString
	 */
	public void generateDriverFile(String dateString) {
		String fileName = dateString.replace('/', '_');
		try {
			myVideoTable.buildDriverFile("kogo_" + fileName + ".xml");
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void consumeDriverFiles() {
		try {
			myGUIController.consumeDriverFiles();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void enableConsumeDriverFilesItem() {
		myConsumeDriverFiles.setDisable(false);
	}
}
