package gui.tableviews;

import gui.ImportedFile;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DriverStatsTable extends TableView<ImportedFile> {

	private static final int TABLE_WIDTH = 450;

	public DriverStatsTable(List<ImportedFile> files) {
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
	
	private TableColumn<ImportedFile, String> makeCol(String heading, String method) {
		TableColumn<ImportedFile, String> col = new TableColumn<ImportedFile, String>(heading);
		col.setCellValueFactory(new PropertyValueFactory<ImportedFile, String>(method));
		col.prefWidthProperty().bind(this.widthProperty().divide(3));
		return col;
	}
}