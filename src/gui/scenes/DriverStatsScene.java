package gui.scenes;

import gui.tableviews.DriverNamesTable;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import control.Controller;

public class DriverStatsScene extends GUIScene implements Observer {
	
	private List<Driver> myDrivers;
	private DriverNamesTable myDriverNamesTable;
	private DriverInformation myDriverInformation;
	
	public DriverStatsScene() {
		myDrivers = Controller.GSON_READER.readDrivers();
		myDriverNamesTable = new DriverNamesTable(myDrivers);
		myDriverNamesTable.addObserver(this);
		BorderPane leftPane = new BorderPane();
		leftPane.setCenter(myDriverNamesTable.getTable());
		leftPane.setPadding(new Insets(10, 0, 10, 10));
		this.setLeft(leftPane);
		myDriverInformation = new DriverInformation();
		this.setCenter(myDriverInformation);	
		myDriverInformation.showInformation(myDrivers.get(0));
	}

	/**
	 * Called when the driver names table selection model is changed.
	 * -A new driver is selected.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		myDriverInformation.showInformation(((Driver) arg1));
	}
}