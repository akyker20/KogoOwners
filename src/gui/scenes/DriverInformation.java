package gui.scenes;

import gui.ImportedFile;
import gui.tableviews.DriverStatsTable;

import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import control.Controller;

public class DriverInformation extends BorderPane {
	
	private DriverStatsTable myDriverStatsTable;

	public DriverInformation() {
		setPadding(new Insets(10));
	}

	public void showInformation(Driver driver) {
		List<ImportedFile> driversFiles = 
				Controller.GSON_READER.readImportedFiles()
				.stream()
				.filter(file -> file.getFileName().contains(driver.getMyInitials()))
				.collect(Collectors.toList());
		myDriverStatsTable = new DriverStatsTable(driversFiles);
		this.setCenter(myDriverStatsTable);
	}
}
