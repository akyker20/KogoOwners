package menus.filemenuitems;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import control.FileControl;

/**
 * A menu with menu items representing the next couple of days so that
 * the user can specify what day the generated driver xml file will be for.
 * @author Austin Kyker
 *
 */
public class GenerateDriverFileMenu extends Menu {

	private static final String ITEM_TITLE = "Make Driver Files";
	private static final int NUM_DAYS_OPTION = 3;
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	private FileControl myControl;

	public GenerateDriverFileMenu(FileControl control){
		super(ITEM_TITLE);
		myControl = control;
		addDateOptionsItems();
	}

	private void addDateOptionsItems() {
		LocalDate[] dates = getArrayOfStringDays();	
		MenuItem[] dateItems = new MenuItem[NUM_DAYS_OPTION];
		for(int i = 0; i < dates.length; i++){
			dateItems[i] = new MenuItem(dates[i].format(FORMATTER));
			final int index = i;
			dateItems[i].setOnAction(event->
			myControl.buildDriverFile(dates[index]));
			getItems().add(dateItems[i]);
		}
	}

	private LocalDate[] getArrayOfStringDays() {
		GregorianCalendar cal = new GregorianCalendar();
		LocalDate[] dates = new LocalDate[NUM_DAYS_OPTION];
		for(int i=0; i < NUM_DAYS_OPTION; i++){			
			dates[i] = LocalDate.of(cal.get(Calendar.YEAR), 
					cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
			cal.add(Calendar.DATE, 1);
		}
		return dates;
	}
}
