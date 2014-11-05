package gui.tablecolumns;

import gui.tableviews.DrivingSessionTable;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import video.PlayedVideo;

public class VideoStringTableCol extends TableColumn<PlayedVideo, String> {
	
	public VideoStringTableCol(ReadOnlyDoubleProperty tableWidth, String name, String videoAttrStr){
		super(name);
		setCellValueFactory(new PropertyValueFactory<PlayedVideo, String>(videoAttrStr));
		prefWidthProperty().bind(tableWidth.divide(DrivingSessionTable.NUM_COLS));
	}
}
