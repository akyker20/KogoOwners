package gui.tableviews;

import gui.tablecolumns.PlayedVideoIntTableCol;
import gui.tablecolumns.VideoStringTableCol;
import video.PlayedVideo;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;


public class DrivingSessionTable extends TableView<PlayedVideo> {

	public static final int NUM_COLS = 4;

	private ObservableList<PlayedVideo> myPlayedVideos;

	public DrivingSessionTable(ObservableList<PlayedVideo> importedVideos){
		myPlayedVideos = importedVideos;

		createColsAndAddToTable();

		this.setId("table");
		this.setItems(myPlayedVideos);
		this.setDisable(true);
	}

	@SuppressWarnings("unchecked")
	private void createColsAndAddToTable() {
		VideoStringTableCol companyName = new VideoStringTableCol(this.widthProperty(), "Company", "myCompany");
		VideoStringTableCol nameCol = new VideoStringTableCol(this.widthProperty(), "Title", "myName");
		PlayedVideoIntTableCol numPlaysPurchasedCol = new PlayedVideoIntTableCol(this.widthProperty(), "Plays", "myPlaysCompleted");
		PlayedVideoIntTableCol revenueCol = new PlayedVideoIntTableCol(this.widthProperty(), "Revenue ($)", "myRevenue");
		this.getColumns().setAll(companyName, nameCol, numPlaysPurchasedCol, revenueCol);
	}
	
	public void reset(){
		myPlayedVideos.clear();
		this.setDisable(true);
	}
}
