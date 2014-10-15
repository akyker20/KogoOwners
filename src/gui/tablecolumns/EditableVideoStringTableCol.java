package gui.tablecolumns;

import gui.tableviews.VideoTable;
import video.Video;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * Class was created to make VideoTable column creation DRYer.
 * @author Austin Kyker
 *
 */
public class EditableVideoStringTableCol extends TableColumn<Video, String> {
	
	public EditableVideoStringTableCol(VideoTable table, Callback<TableColumn<Video, String>, TableCell<Video, String>> factory, 
			String name, String videoAttrStr){
		super(name);
		setCellFactory(factory);
		setCellValueFactory(new PropertyValueFactory<Video, String>(videoAttrStr));
		prefWidthProperty().bind(table.widthProperty().divide(VideoTable.NUM_COLS));
	}
}
