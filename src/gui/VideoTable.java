package gui;
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

	public VideoTable(ObservableList<Video> data){

		Callback<TableColumn<Video, String>, TableCell<Video, String>> stringCellFactory =
				new Callback<TableColumn<Video, String>, TableCell<Video, String>>() {
			public TableCell call(TableColumn<Video, String> p) {
				return new StringEditingCell();
			}
		};
		
//		Callback<TableColumn, TableCell> integerCellFactory =
//				new Callback<TableColumn, TableCell>() {
//			public TableCell call(TableColumn p) {
//				return new StringEditingCell();
//			}
//		};

		TableColumn<Video, String> companyName = new TableColumn<Video, String>("Company");
		companyName.setCellValueFactory(new PropertyValueFactory("myCompany"));
		companyName.prefWidthProperty().bind(this.widthProperty().divide(4));
		companyName.setCellFactory(stringCellFactory);
		companyName.setOnEditCommit(
	            new EventHandler<CellEditEvent<Video, String>>() {
	                @Override
	                public void handle(CellEditEvent<Video, String> t) {
	                    ((Video) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setMyCompany(t.getNewValue());
	                }
	             }
	        );
		

		TableColumn<Video,String> nameCol = new TableColumn<Video,String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory("myName"));
		nameCol.prefWidthProperty().bind(this.widthProperty().divide(4));

		TableColumn<Video, Integer> numPlaysCol = new TableColumn<Video, Integer>("Plays");
		numPlaysCol.setCellValueFactory(new PropertyValueFactory("myPlaysRemaining"));
		numPlaysCol.prefWidthProperty().bind(this.widthProperty().divide(4));

		TableColumn<Video, Integer> lengthCol = new TableColumn<Video, Integer>("Length");
		lengthCol.setCellValueFactory(new PropertyValueFactory("myLength"));
		lengthCol.prefWidthProperty().bind(this.widthProperty().divide(4));


		this.getColumns().setAll(companyName, nameCol, numPlaysCol, lengthCol);
		this.setId("table");
		this.setPrefHeight(300);
		this.setItems(data);
		this.setEditable(true);
	}
}
