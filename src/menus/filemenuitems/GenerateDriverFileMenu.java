package menus.filemenuitems;

import gui.GUIController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import menus.FileMenu;

/**
 * A menu with menu items representing the next couple of days so that
 * the user can specify what day the generated driver xml file will be for.
 * @author Austin Kyker
 *
 */
public class GenerateDriverFileMenu extends Menu {
	
	private static final int NUM_DAYS_OPTION = 3;

	public GenerateDriverFileMenu(FileMenu fileMenu){
		super("Make Driver Files");

		String[] dateStr = getArrayOfStringDays();
		
		MenuItem[] dates = new MenuItem[NUM_DAYS_OPTION];
		for(int i = 0; i < dateStr.length; i++){
			dates[i] = new MenuItem(dateStr[i]);
			final int index = i;
			dates[i].setOnAction(event->GUIController.buildDriverFile(getDriverFileName(dateStr[index])));
			getItems().add(dates[i]);
		}
	}

	private String getDriverFileName(String date){
		String fileName = date.replace('/', '-');
		return "kogo_" + fileName + ".xml";
	}
	
	/**
	 * @return an array of String representing the next NUM_DAYS_OPTION days.
	 */
	private String[] getArrayOfStringDays() {
		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
		int month = cal.get(GregorianCalendar.MONTH);
		int year = cal.get(GregorianCalendar.YEAR);
		String[] dateStr = new String[NUM_DAYS_OPTION];
		int r = 0;
		for(int i=day; i < (day+NUM_DAYS_OPTION); i++){
			cal.set(year, month, i);
			Date date = cal.getTime();
			dateStr[r] = sdf.format(date);
			r++;
		}
		return dateStr;
	}
}
