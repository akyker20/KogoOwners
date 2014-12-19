package gui.tablecolumns;

import gui.tableviews.DrivingSessionTable;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import video.ActiveVideo;

/**
 * Class was created to make VideoTable column creation DRYer.
 * @author Austin Kyker
 *
 */
public class PlayedVideoIntTableCol extends TableColumn<ActiveVideo, Integer> {
	
	public PlayedVideoIntTableCol(ReadOnlyDoubleProperty tableWidth, String name, String videoAttrStr){
		super(name);
		setCellValueFactory(new PropertyValueFactory<ActiveVideo, Integer>(videoAttrStr));
		prefWidthProperty().bind(tableWidth.divide(DrivingSessionTable.NUM_COLS));
	}
}
