package gui.tableviews;

import gui.scenes.Driver;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DriverNamesTable extends TableView<Driver> {

	private static final int TABLE_WIDTH = 150;

	public DriverNamesTable(List<Driver> drivers) {
		this.setId("table");
		addColToTable();
		setPrefWidth(TABLE_WIDTH);
		setItems(FXCollections.observableArrayList(drivers));
	}

	private void addColToTable() {
		TableColumn<Driver, String> driverNameCol = 
				new TableColumn<Driver, String>("Drivers");
		driverNameCol.setCellValueFactory(new PropertyValueFactory<Driver, String>("myName"));
		driverNameCol.prefWidthProperty().bind(this.widthProperty());
		this.getColumns().add(driverNameCol);
	}
	
	public Driver getSelectedDriver() {
		return this.getSelectionModel().getSelectedItem();
	}
}