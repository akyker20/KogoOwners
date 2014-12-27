package gui.tableviews;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import video.DriverSessionData;

public class DriverStatsTable extends TableView<DriverSessionData> {

	private static final int TABLE_WIDTH = 450;

	public DriverStatsTable(List<DriverSessionData> files) {
		this.setId("table");
		addColToTable();
		setPrefWidth(TABLE_WIDTH);
		setItems(FXCollections.observableArrayList(files));
	}

	private void addColToTable() {
		this.getColumns().add(makeCol("Date", "date"));
		this.getColumns().add(makeCol("Impact Seconds", "totalImpactSeconds"));
		this.getColumns().add(makeCol("Revenue ($)", "totalRevenue"));
	}
	
	private TableColumn<DriverSessionData, String> makeCol(String heading, String method) {
		TableColumn<DriverSessionData, String> col = new TableColumn<DriverSessionData, String>(heading);
		col.setCellValueFactory(new PropertyValueFactory<DriverSessionData, String>(method));
		col.prefWidthProperty().bind(this.widthProperty().divide(3));
		return col;
	}
}