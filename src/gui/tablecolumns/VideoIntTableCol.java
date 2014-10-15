package gui.tablecolumns;

import gui.tableviews.VideoTable;
import video.Video;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Class was created to make VideoTable column creation DRYer.
 * @author Austin Kyker
 *
 */
public class VideoIntTableCol extends TableColumn<Video, Integer> {
	
	public VideoIntTableCol(TableView table, String name, String videoAttrStr){
		super(name);
		setCellValueFactory(new PropertyValueFactory<Video, Integer>(videoAttrStr));
		prefWidthProperty().bind(table.widthProperty().divide(VideoTable.NUM_COLS));
	}
}
