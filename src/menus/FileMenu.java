package menus;

import javafx.scene.control.Menu;
import menus.filemenuitems.BackToTableItem;
import menus.filemenuitems.ConsumeDriverFiles;
import menus.filemenuitems.GenerateDriverFileMenu;
import menus.filemenuitems.ImportDriverFiles;
import menus.filemenuitems.RemoveVideoItem;
import menus.filemenuitems.UndoRemoveVideoItem;
import control.FileControl;

/**
 * Class will be used to load files such as previously saved commands or grid
 * configurations.
 * 
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

	public FileMenu(FileControl control) {

		this.setText("File");
		myReturnToTable = new BackToTableItem(control);
		myUndoRemoveVideo = new UndoRemoveVideoItem();
		myRemoveVideo = new RemoveVideoItem();
		myGenerateDriverFileMenu = new GenerateDriverFileMenu(control);
		myUploadDriverFiles = new ImportDriverFiles(control);
		myConsumeDriverFiles = new ConsumeDriverFiles(control);
	}

	public void disableConsumeDriverFilesItem(boolean disableStatus) {
		myConsumeDriverFiles.setDisable(disableStatus);
	}

	public void configureImportFileMenuOptions() {
		this.getItems().removeAll(myRemoveVideo, myUndoRemoveVideo,
				myGenerateDriverFileMenu, myUploadDriverFiles);
		this.getItems().addAll(myConsumeDriverFiles, myReturnToTable);
	}

	public void configureVideoTableMenuOptions() {
		this.getItems().removeAll(myConsumeDriverFiles, myReturnToTable);
		this.getItems().addAll(myRemoveVideo, myUndoRemoveVideo,
				myGenerateDriverFileMenu, myUploadDriverFiles);

	}
}
