package gui.tablecolumns;

import gui.tableviews.VideoTable;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import video.LoadedVideo;

/**
 * Class was created to make VideoTable column creation DRYer.
 * 
 * @author Austin Kyker
 *
 */
public class VideoIntTableCol extends TableColumn<LoadedVideo, Integer> {

	public VideoIntTableCol(ReadOnlyDoubleProperty tableWidth, String name,
			String videoAttrStr) {
		super(name);
		setCellValueFactory(new PropertyValueFactory<LoadedVideo, Integer>(
				videoAttrStr));
		prefWidthProperty().bind(tableWidth.divide(VideoTable.NUM_COLS));
	}
}
