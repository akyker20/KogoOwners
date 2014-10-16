package gui.tableviews;

import java.util.Stack;

import javax.xml.transform.TransformerException;

import video.LoadedVideo;
import editingcells.StringEditingCell;
import gui.GUIController;
import gui.tablecolumns.VideoIntTableCol;
import gui.tablecolumns.EditableVideoStringTableCol;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * This class is a table view that allows advertisements to be edited, added, 
 * and removed.
 * @author Austin Kyker
 *
 */
public class VideoTable extends TableView<LoadedVideo> {

	public static int NUM_COLS = 6;

	private ObservableList<LoadedVideo> myVideos;
	private Stack<LoadedVideo> myRemovedVideos;

	@SuppressWarnings("unchecked")
	public VideoTable(ObservableList<LoadedVideo> data){
		myVideos = data;
		myRemovedVideos = new Stack<LoadedVideo>();

		Callback<TableColumn<LoadedVideo, String>, TableCell<LoadedVideo, String>> stringCellFactory =
				new Callback<TableColumn<LoadedVideo, String>, TableCell<LoadedVideo, String>>() {
			public TableCell<LoadedVideo, String> call(TableColumn<LoadedVideo, String> p) {
				return new StringEditingCell();
			}
		};

		EditableVideoStringTableCol companyName = new EditableVideoStringTableCol(this, stringCellFactory, "Company", "myCompany");
		companyName.setOnEditCommit(
				new EventHandler<CellEditEvent<LoadedVideo, String>>() {
					@Override
					public void handle(CellEditEvent<LoadedVideo, String> t) {
						((LoadedVideo) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setMyCompany(t.getNewValue());	  
						editMasterFile();
					}
				});

		EditableVideoStringTableCol nameCol = new EditableVideoStringTableCol(this, stringCellFactory, "Title", "myName");
		nameCol.setOnEditCommit(
				new EventHandler<CellEditEvent<LoadedVideo, String>>() {
					@Override
					public void handle(CellEditEvent<LoadedVideo, String> t) {
						((LoadedVideo) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setMyName(t.getNewValue());	  
						editMasterFile();
					}
				});
		
		VideoIntTableCol numPlaysPurchasedCol = new VideoIntTableCol(this, "Plays Purchased", "myPlaysPurchased");
		VideoIntTableCol numPlaysRemainingCol = new VideoIntTableCol(this, "Plays Remaining", "myPlaysRemaining");
		VideoIntTableCol lengthCol = new VideoIntTableCol(this,"Length (sec)", "myLength");
		VideoIntTableCol revenueCol = new VideoIntTableCol(this, "Revenue ($)", "myRevenue");

		this.getColumns().setAll(companyName, nameCol, lengthCol, numPlaysPurchasedCol, 
				numPlaysRemainingCol, revenueCol);
		
		this.setId("table");
		this.setPrefHeight(300);
		this.setItems(myVideos);
		this.setEditable(true);
	}

	/**
	 * Calls the xml writer to save edits to master file.
	 */
	protected void editMasterFile() {
		try {
			GUIController.editMasterFile();
		} catch (TransformerException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Removes selected advertisement from the table. Maintains the advertisement
	 * in a stack so that if the user removed the advertisement by mistake, they
	 * can undo their error.
	 * @return
	 * @throws TransformerException
	 */
	public boolean removeSelectedItem() throws TransformerException {
		LoadedVideo videoToRemove = getSelectionModel().getSelectedItem();
		if(myVideos.remove(videoToRemove)){
			GUIController.editMasterFile();
			if(!myRemovedVideos.contains(videoToRemove)){
				myRemovedVideos.push(videoToRemove);
			}
			return true;
		}
		return false;
	}

	/**
	 * Adds the most recently removed video back into the table. This is
	 * to ensure a user does not remove a video by mistake and loses the data.
	 * @throws TransformerException
	 */
	public void undoRemovedVideo() throws TransformerException{
		if(!myRemovedVideos.isEmpty()){
			myVideos.add(myRemovedVideos.pop());
			GUIController.editMasterFile();
		}
	}

	/**
	 * @return true if the user has removed videos and not undone these removes.
	 */
	public boolean areRemovedVideosRemaining(){
		return !myRemovedVideos.isEmpty();
	}

	/**
	 * Builds the file that will be sent to Drivers.
	 * @param fileName - the name the created file will be given.
	 * @throws TransformerException
	 */
	public void buildDriverFile(String fileName) throws TransformerException {
		GUIController.buildDriverFile(fileName);	
	}
}
