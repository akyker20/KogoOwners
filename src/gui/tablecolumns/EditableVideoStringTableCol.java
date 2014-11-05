package gui.tablecolumns;

import gui.tableviews.VideoTable;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import video.LoadedVideo;

/**
 * Class was created to make VideoTable column creation DRYer.
 * @author Austin Kyker
 *
 */
public class EditableVideoStringTableCol extends TableColumn<LoadedVideo, String> {
	
	public EditableVideoStringTableCol(ReadOnlyDoubleProperty tableWidth, Callback<TableColumn<LoadedVideo, String>, TableCell<LoadedVideo, String>> factory, 
			String name, String videoAttrStr){
		super(name);
		setCellFactory(factory);
		setCellValueFactory(new PropertyValueFactory<LoadedVideo, String>(videoAttrStr));
		prefWidthProperty().bind(tableWidth.divide(VideoTable.NUM_COLS));
	}
}
