package gui.tableviews;

import gui.scenes.Driver;

import java.util.List;
import java.util.Observable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DriverNamesTable extends Observable {

	private static final int TABLE_WIDTH = 150;

	private TableView<Driver> myTable;

	public DriverNamesTable(List<Driver> drivers) {
		myTable = new TableView<Driver>();
		myTable.setId("table");
		addColToTable();
		myTable.setPrefWidth(TABLE_WIDTH);
		myTable.setItems(FXCollections.observableArrayList(drivers));
		addSelectionListener();
	}

	private void addSelectionListener() {
		myTable.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<Driver>() {
			@Override
			public void changed(
					ObservableValue<? extends Driver> observableValue,
					Driver oldValue, Driver driverSelected) {
				DriverNamesTable.this.setChanged();
				DriverNamesTable.this.notifyObservers(driverSelected);
			}
		});
	}

	private void addColToTable() {
		TableColumn<Driver, String> driverNameCol = 
				new TableColumn<Driver, String>("Drivers");
		driverNameCol.setCellValueFactory(
				new PropertyValueFactory<Driver, String>("myName"));
		driverNameCol.prefWidthProperty().bind(myTable.widthProperty());
		myTable.getColumns().add(driverNameCol);
	}

	public TableView<Driver> getTable() {
		return myTable;
	}
}