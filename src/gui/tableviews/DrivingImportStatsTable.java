package gui.tableviews;

import control.Controller;
import gui.tablecolumns.PlayedVideoIntTableCol;
import gui.tablecolumns.VideoStringTableCol;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import video.ActiveVideo;

public class DrivingImportStatsTable extends TableView<ActiveVideo> {

	public static final int NUM_COLS = 4;

	private ObservableList<ActiveVideo> myCurrentlyImportedVideos;

	public DrivingImportStatsTable() {
		myCurrentlyImportedVideos = Controller.IMPORT_MANAGER
				.getCurrentlyImportedVideos();
		createColsAndAddToTable();
		this.setId("table");
		this.setItems(myCurrentlyImportedVideos);
		this.setDisable(true);
	}

	@SuppressWarnings("unchecked")
	private void createColsAndAddToTable() {
		VideoStringTableCol companyName = new VideoStringTableCol(
				this.widthProperty(), "Company", "myCompany");
		VideoStringTableCol nameCol = new VideoStringTableCol(
				this.widthProperty(), "Title", "myName");
		PlayedVideoIntTableCol numPlaysPurchasedCol = new PlayedVideoIntTableCol(
				this.widthProperty(), "Plays", "myPlays");
		PlayedVideoIntTableCol revenueCol = new PlayedVideoIntTableCol(
				this.widthProperty(), "Revenue ($)", "myRevenue");
		this.getColumns().setAll(companyName, nameCol, numPlaysPurchasedCol,
				revenueCol);
	}

	public void reset() {
		myCurrentlyImportedVideos.clear();
		this.setDisable(true);
	}
}
