package gui.tablecolumns;

import gui.tableviews.DrivingSessionTable;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import video.ActiveVideo;

public class VideoStringTableCol extends TableColumn<ActiveVideo, String> {
	
	public VideoStringTableCol(ReadOnlyDoubleProperty tableWidth, String name, String videoAttrStr){
		super(name);
		setCellValueFactory(new PropertyValueFactory<ActiveVideo, String>(videoAttrStr));
		prefWidthProperty().bind(tableWidth.divide(DrivingSessionTable.NUM_COLS));
	}
}
