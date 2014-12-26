package gui.scenes;

import gui.tableviews.DriverNamesTable;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import control.Controller;

public class DriverStatsScene extends GUIScene {
	
	private List<Driver> myDrivers;
	private DriverNamesTable myDriverNamesTable;
	private DriverInformation myDriverInformation;
	
	public DriverStatsScene() {
		myDrivers = Controller.GSON_READER.readDrivers();
		myDriverNamesTable = new DriverNamesTable(myDrivers);
		BorderPane leftPane = new BorderPane();
		leftPane.setCenter(myDriverNamesTable);
		leftPane.setPadding(new Insets(10, 0, 10, 10));
		this.setLeft(leftPane);
		myDriverInformation = new DriverInformation();
		this.setCenter(myDriverInformation);	
		myDriverInformation.showInformation(myDrivers.get(0));
	}
}