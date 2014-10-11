package gui;

import javax.xml.transform.TransformerException;
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
	
	public static int NUM_COLS = 5;
	
	public VideoTable(ObservableList<Video> data, XMLWriter writer){
		
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
							writer.writeToFile(data);
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
		lengthCol.setCellValueFactory(new PropertyValueFactory("myLength"));
		lengthCol.prefWidthProperty().bind(this.widthProperty().divide(NUM_COLS));


		this.getColumns().setAll(companyName, nameCol, numPlaysPurchasedCol, numPlaysRemainingCol, lengthCol);
		this.setId("table");
		this.setPrefHeight(300);
		this.setItems(data);
		this.setEditable(true);
	}
}
