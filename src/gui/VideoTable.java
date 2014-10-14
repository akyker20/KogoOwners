package gui;

import java.util.Stack;

import javax.xml.transform.TransformerException;

import video.Video;
import xmlcontrol.XMLWriter;
import editingcells.StringEditingCell;
import gui.tablecolumns.VideoIntTableCol;
import gui.tablecolumns.VideoStringTableCol;
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
public class VideoTable extends TableView<Video> {

	public static int NUM_COLS = 6;

	private ObservableList<Video> myVideos;
	private XMLWriter myWriter;
	private Stack<Video> myRemovedVideos;

	@SuppressWarnings("unchecked")
	public VideoTable(ObservableList<Video> data, XMLWriter writer){

		myVideos = data;
		myWriter = writer;
		myRemovedVideos = new Stack<Video>();

		Callback<TableColumn<Video, String>, TableCell<Video, String>> stringCellFactory =
				new Callback<TableColumn<Video, String>, TableCell<Video, String>>() {
			public TableCell<Video, String> call(TableColumn<Video, String> p) {
				return new StringEditingCell();
			}
		};

		VideoStringTableCol companyName = new VideoStringTableCol(this, stringCellFactory, "Company", "myCompany");
		companyName.setOnEditCommit(
				new EventHandler<CellEditEvent<Video, String>>() {
					@Override
					public void handle(CellEditEvent<Video, String> t) {
						((Video) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setMyCompany(t.getNewValue());	  
						editMasterFile();
					}
				});

		VideoStringTableCol nameCol = new VideoStringTableCol(this, stringCellFactory, "Title", "myName");
		nameCol.setOnEditCommit(
				new EventHandler<CellEditEvent<Video, String>>() {
					@Override
					public void handle(CellEditEvent<Video, String> t) {
						((Video) t.getTableView().getItems().get(
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
			myWriter.editMasterFile(myVideos);
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
		Video videoToRemove = getSelectionModel().getSelectedItem();
		if(myVideos.remove(videoToRemove)){
			myWriter.editMasterFile(myVideos);
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
			myWriter.editMasterFile(myVideos);
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
		myWriter.buildDriverFile(myVideos, fileName);	
	}
}
