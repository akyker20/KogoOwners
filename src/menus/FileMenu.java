package menus;

import javafx.scene.control.Menu;
import menus.filemenuitems.BackToTableItem;
import menus.filemenuitems.ConsumeDriverFiles;
import menus.filemenuitems.GenerateDriverFileMenu;
import menus.filemenuitems.ImportDriverFiles;
import menus.filemenuitems.RemoveVideoItem;
import menus.filemenuitems.UndoRemoveVideoItem;

/**
 * Class will be used to load files such as previously saved commands or
 * grid configurations.
 * @author Austin Kyker
 *
 */
public class FileMenu extends Menu {

	private UndoRemoveVideoItem myUndoRemoveVideo;
	private RemoveVideoItem myRemoveVideo;
	private GenerateDriverFileMenu myGenerateDriverFileMenu;
	private ImportDriverFiles myUploadDriverFiles;
	private ConsumeDriverFiles myConsumeDriverFiles;
	private BackToTableItem myReturnToTable;

	public FileMenu(){

		this.setText("File");

		myReturnToTable = new BackToTableItem(this);
		myUndoRemoveVideo = new UndoRemoveVideoItem(this);
		myRemoveVideo = new RemoveVideoItem(this);
		myGenerateDriverFileMenu = new GenerateDriverFileMenu(this);
		myUploadDriverFiles = new ImportDriverFiles();
		try {
			myConsumeDriverFiles = new ConsumeDriverFiles(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	/**
//	 * Removes a video and if there are no videos that have been removed (no
//	 * videos on the undo stack), then the UndoRemoveVideo icon is enabled.
//	 */
//	public void tryToRemoveVideo() {
//		try {
//			if(myVideoTable.removeSelectedItem() && myUndoRemoveVideo.isDisable()){
//				myUndoRemoveVideo.setDisable(false);
//			}
//		} catch (TransformerException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}

//	/**
//	 * Tries to undo a video removal. If there are no longer any removed videos on the
//	 * stack after the video is added back in (removed from the stack), then the 
//	 * UndoRemove MenuItem is disabled (There are no removals to undo).
//	 */
//	public void tryToUndoRemove() {
//		try {
//			myVideoTable.undoRemovedVideo();
//			if(!myVideoTable.areRemovedVideosRemaining()){
//				myUndoRemoveVideo.setDisable(true);
//			}
//		} catch (TransformerException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}

	public void disableConsumeDriverFilesItem(boolean disableStatus) {
		myConsumeDriverFiles.setDisable(disableStatus);
	}

	public void configureImportFileMenuOptions() {
		this.getItems().removeAll(myRemoveVideo, myUndoRemoveVideo, myGenerateDriverFileMenu, myUploadDriverFiles);
		this.getItems().addAll(myConsumeDriverFiles, myReturnToTable);
	}

	public void configureVideoTableMenuOptions() {
		this.getItems().removeAll(myConsumeDriverFiles, myReturnToTable);
		this.getItems().addAll(myRemoveVideo, myUndoRemoveVideo, myGenerateDriverFileMenu, myUploadDriverFiles);

	}
}
