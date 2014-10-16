package gui.tableviews;

import gui.tablecolumns.PlayedVideoIntTableCol;
import gui.tablecolumns.VideoStringTableCol;

import video.PlayedVideo;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;


public class DrivingSessionTable extends TableView<PlayedVideo> {

	public static final int NUM_COLS = 4;

	private ObservableList<PlayedVideo> myPlayedVideos;

	@SuppressWarnings("unchecked")
	public DrivingSessionTable(ObservableList<PlayedVideo> importedVideos){
		myPlayedVideos = importedVideos;

		VideoStringTableCol companyName = new VideoStringTableCol(this, "Company", "myCompany");
		VideoStringTableCol nameCol = new VideoStringTableCol(this, "Title", "myName");
		PlayedVideoIntTableCol numPlaysPurchasedCol = new PlayedVideoIntTableCol(this, "Plays", "myPlaysCompleted");
		PlayedVideoIntTableCol revenueCol = new PlayedVideoIntTableCol(this, "Revenue ($)", "myRevenue");

		this.getColumns().setAll(companyName, nameCol, numPlaysPurchasedCol, revenueCol);

		this.setId("table");
		this.setItems(myPlayedVideos);
		this.setDisable(true);
	}
}
