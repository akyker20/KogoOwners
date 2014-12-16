package gui.tableviews;

import java.util.Stack;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import video.LoadedVideo;
import editingcells.StringEditingCell;
import gui.Controller;
import gui.tablecolumns.EditableVideoStringTableCol;
import gui.tablecolumns.VideoIntTableCol;

/**
 * This class is a table view that allows advertisements to be edited, added, 
 * and removed.
 * @author Austin Kyker
 *
 */
public class VideoTable extends TableView<LoadedVideo> {

	private static final int TABLE_HEIGHT = 300;

	public static int NUM_COLS = 6;

	private ObservableList<LoadedVideo> myVideos;
	private Stack<LoadedVideo> myRemovedVideos;

	public VideoTable(ObservableList<LoadedVideo> data){
		myVideos = data;

		createColsAndAddToTable();

		this.setId("table");
		this.setPrefHeight(TABLE_HEIGHT);
		this.setItems(myVideos);
		this.setEditable(true);
	}

	@SuppressWarnings("unchecked")
	private void createColsAndAddToTable() {
		Callback<TableColumn<LoadedVideo, String>, TableCell<LoadedVideo, String>> stringCellFactory =
				new Callback<TableColumn<LoadedVideo, String>, TableCell<LoadedVideo, String>>() {
			public TableCell<LoadedVideo, String> call(TableColumn<LoadedVideo, String> p) {
				return new StringEditingCell();
			}
		};
		EditableVideoStringTableCol companyName = new EditableVideoStringTableCol(this.widthProperty(), stringCellFactory, "Company", "myCompany");
		companyName.setOnEditCommit(
				new EventHandler<CellEditEvent<LoadedVideo, String>>() {
					@Override
					public void handle(CellEditEvent<LoadedVideo, String> t) {
						((LoadedVideo) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setMyCompany(t.getNewValue());	  
						Controller.GSON_WRITER.writeMasterFile(myVideos);
					}
				});
		EditableVideoStringTableCol nameCol = new EditableVideoStringTableCol(this.widthProperty(), stringCellFactory, "Title", "myName");
		nameCol.setOnEditCommit(
				new EventHandler<CellEditEvent<LoadedVideo, String>>() {
					@Override
					public void handle(CellEditEvent<LoadedVideo, String> t) {
						((LoadedVideo) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setMyName(t.getNewValue());	  
						Controller.GSON_WRITER.writeMasterFile(myVideos);					}
				});
		VideoIntTableCol numPlaysPurchasedCol = new VideoIntTableCol(this.widthProperty(), "Plays Purchased", "myPlaysPurchased");
		VideoIntTableCol numPlaysRemainingCol = new VideoIntTableCol(this.widthProperty(), "Plays Remaining", "myPlaysRemaining");
		VideoIntTableCol lengthCol = new VideoIntTableCol(this.widthProperty(),"Length (sec)", "myLength");
		VideoIntTableCol revenueCol = new VideoIntTableCol(this.widthProperty(), "Revenue ($)", "myRevenue");
		this.getColumns().setAll(companyName, nameCol, lengthCol, numPlaysPurchasedCol, 
				numPlaysRemainingCol, revenueCol);
	}

	public boolean removeSelectedAdvertisement() {
		if(myRemovedVideos==null) myRemovedVideos = new Stack<LoadedVideo>();
		LoadedVideo videoToRemove = getSelectionModel().getSelectedItem();
		if(myVideos.remove(videoToRemove)){
			Controller.GSON_WRITER.writeMasterFile(myVideos);
			myRemovedVideos.push(videoToRemove);
			return true;
		}
		return false;
	}

	public void undoRemovedVideo() {
		if(!myRemovedVideos.isEmpty()){
			myVideos.add(myRemovedVideos.pop());
			Controller.GSON_WRITER.writeMasterFile(myVideos);
		}
	}

	public boolean areRemovedVideosRemaining(){
		return !myRemovedVideos.isEmpty();
	}
}
