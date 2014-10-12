package gui;

import java.util.ArrayList;
import java.util.Stack;

import javax.xml.transform.TransformerException;

import video.Video;
import xmlcontrol.XMLWriter;
import editingcells.StringEditingCell;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class VideoTable extends TableView<Video> {

	public static int NUM_COLS = 6;
	private ObservableList<Video> myVideos;
	private XMLWriter myWriter;
	private Stack<Video> myRemovedVideos;

	public VideoTable(ObservableList<Video> data, XMLWriter writer){

		myVideos = data;
		myWriter = writer;
		myRemovedVideos = new Stack<Video>();

		Callback<TableColumn<Video, String>, TableCell<Video, String>> stringCellFactory =
				new Callback<TableColumn<Video, String>, TableCell<Video, String>>() {
			public TableCell call(TableColumn<Video, String> p) {
				return new StringEditingCell();
			}
		};

		TableColumn<Video, String> companyName = new TableColumn<Video, String>("Company");
		companyName.setCellValueFactory(new PropertyValueFactory("myCompany"));
		companyName.prefWidthProperty().bind(this.widthProperty().divide(NUM_COLS));
		companyName.setCellFactory(stringCellFactory);
		companyName.setOnEditCommit(
				new EventHandler<CellEditEvent<Video, String>>() {
					@Override
					public void handle(CellEditEvent<Video, String> t) {
						((Video) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setMyCompany(t.getNewValue());	  
						try {
							myWriter.writeToFile(myVideos);
						} catch (TransformerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				);


		TableColumn<Video,String> nameCol = new TableColumn<Video,String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory("myName"));
		nameCol.prefWidthProperty().bind(this.widthProperty().divide(NUM_COLS));

		TableColumn<Video, Integer> numPlaysPurchasedCol = new TableColumn<Video, Integer>("Plays Purchased");
		numPlaysPurchasedCol.setCellValueFactory(new PropertyValueFactory("myPlaysPurchased"));
		numPlaysPurchasedCol.prefWidthProperty().bind(this.widthProperty().divide(NUM_COLS));

		TableColumn<Video, Integer> numPlaysRemainingCol = new TableColumn<Video, Integer>("Plays Remaining");
		numPlaysRemainingCol.setCellValueFactory(new PropertyValueFactory("myPlaysRemaining"));
		numPlaysRemainingCol.prefWidthProperty().bind(this.widthProperty().divide(NUM_COLS));

		TableColumn<Video, Integer> lengthCol = new TableColumn<Video, Integer>("Length");
		lengthCol.setCellValueFactory(new PropertyValueFactory<Video, Integer>("myLength"));
		lengthCol.prefWidthProperty().bind(this.widthProperty().divide(NUM_COLS));

		TableColumn<Video, Double> revenueCol = new TableColumn<Video, Double>("Revenue ($)");
		revenueCol.setCellValueFactory(new PropertyValueFactory<Video, Double>("myRevenue"));
		revenueCol.prefWidthProperty().bind(this.widthProperty().divide(NUM_COLS));


		this.getColumns().setAll(companyName, nameCol, lengthCol, numPlaysPurchasedCol, 
				numPlaysRemainingCol, revenueCol);
		this.setId("table");
		this.setPrefHeight(300);
		this.setItems(myVideos);
		this.setEditable(true);
	}

	public boolean removeSelectedItem() throws TransformerException {
		Video videoToRemove = getSelectionModel().getSelectedItem();
		if(myVideos.remove(videoToRemove)){
			myWriter.writeToFile(myVideos);
			if(!myRemovedVideos.contains(videoToRemove)){
				myRemovedVideos.push(videoToRemove);
			}
			return true;
		}
		return false;
	}

	/**
	 * Adds the most recently removed video back into the table.
	 * @throws TransformerException
	 */
	public void undoRemovedVideo() throws TransformerException{
		if(!myRemovedVideos.isEmpty()){
			myVideos.add(myRemovedVideos.pop());
			myWriter.writeToFile(myVideos);
		}
	}
	
	/**
	 * @return true if the user has removed videos and not undone these removes.
	 */
	public boolean areRemovedVideosRemaining(){
		return !myRemovedVideos.isEmpty();
	}
}
