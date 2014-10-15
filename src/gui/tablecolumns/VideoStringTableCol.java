package gui.tablecolumns;

import gui.tableviews.DrivingSessionTable;
import video.PlayedVideo;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VideoStringTableCol extends TableColumn<PlayedVideo, String> {
	
	public VideoStringTableCol(TableView table, String name, String videoAttrStr){
		super(name);
		setCellValueFactory(new PropertyValueFactory<PlayedVideo, String>(videoAttrStr));
		prefWidthProperty().bind(table.widthProperty().divide(DrivingSessionTable.NUM_COLS));
	}
}
