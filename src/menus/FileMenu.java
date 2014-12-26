package menus;

import javafx.scene.control.Menu;
import menus.filemenuitems.BackToTableItem;
import menus.filemenuitems.ConsumeDriverFiles;
import menus.filemenuitems.DriverStatsItem;
import menus.filemenuitems.GenerateDriverFileMenu;
import menus.filemenuitems.ImportDriverFiles;
import control.FileControl;

/**
 * Class will be used to load files such as previously saved commands or grid
 * configurations.
 * 
 * @author Austin Kyker
 *
 */
public class FileMenu extends Menu {

	private GenerateDriverFileMenu myGenerateDriverFileMenu;
	private ImportDriverFiles myUploadDriverFiles;
	private ConsumeDriverFiles myConsumeDriverFiles;
	private BackToTableItem myReturnToTable;
	private DriverStatsItem myDriverStatsItem;

	public FileMenu(FileControl control) {
		this.setText("File");
		myReturnToTable = new BackToTableItem(control);
		myGenerateDriverFileMenu = new GenerateDriverFileMenu(control);
		myUploadDriverFiles = new ImportDriverFiles(control);
		myConsumeDriverFiles = new ConsumeDriverFiles(control);
		myDriverStatsItem = new DriverStatsItem(control);
	}

	public void disableConsumeDriverFilesItem(boolean disableStatus) {
		myConsumeDriverFiles.setDisable(disableStatus);
	}

	public void configureImportFileMenuOptions() {
		this.getItems().removeAll(myGenerateDriverFileMenu, myUploadDriverFiles, myDriverStatsItem);
		this.getItems().addAll(myConsumeDriverFiles, myReturnToTable);
	}

	public void configureVideoTableMenuOptions() {
		this.getItems().removeAll(myConsumeDriverFiles, myReturnToTable);
		this.getItems().addAll(myGenerateDriverFileMenu, myUploadDriverFiles, myDriverStatsItem);
	}
}
