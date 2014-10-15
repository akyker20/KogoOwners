package gui.tablecolumns;

import gui.tableviews.DrivingSessionTable;
import video.PlayedVideo;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Class was created to make VideoTable column creation DRYer.
 * @author Austin Kyker
 *
 */
public class PlayedVideoIntTableCol extends TableColumn<PlayedVideo, Integer> {
	
	public PlayedVideoIntTableCol(TableView<PlayedVideo> table, String name, String videoAttrStr){
		super(name);
		setCellValueFactory(new PropertyValueFactory<PlayedVideo, Integer>(videoAttrStr));
		prefWidthProperty().bind(table.widthProperty().divide(DrivingSessionTable.NUM_COLS));
	}
}
