package menus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.transform.TransformerException;

import xmlcontrol.XMLWriter;
import gui.VideoTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Class will be used to load files such as previously saved commands or
 * grid configurations.
 * @author Austin Kyker
 *
 */
public class FileMenu extends Menu {



	public FileMenu(VideoTable table){
		this.setText("File");

		MenuItem undoRemoveVideo = new MenuItem("Undo Remove");
		undoRemoveVideo.setDisable(true);
		undoRemoveVideo.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				try {
					table.undoRemovedVideo();
					if(!table.areRemovedVideosRemaining()){
						undoRemoveVideo.setDisable(true);
					}
				} catch (TransformerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		MenuItem removeVideo = new MenuItem("Remove Video");
		removeVideo.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				try {
					if(table.removeSelectedItem() && undoRemoveVideo.isDisable()){
						undoRemoveVideo.setDisable(false);
					}
				} catch (TransformerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Menu generateDriverFiles = new Menu("Make Driver Files");
		generateDriverFiles.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				try {
					table.buildDriverFile();
				} catch (TransformerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
		int month = cal.get(GregorianCalendar.MONTH);
		int year = cal.get(GregorianCalendar.YEAR);
		String[] dateStr = new String[5];
		int r = 0;
		for(int i=day; i < (day+5); i++){
			cal.set(year, month, i);
			Date date = cal.getTime();
			dateStr[r] = sdf.format(date);
			r++;
		}
		MenuItem[] dates = new MenuItem[5];
		for(int i = 0; i < dateStr.length; i++){
			dates[i] = new MenuItem(dateStr[i]);
			generateDriverFiles.getItems().add(dates[i]);
		}
		

		this.getItems().addAll(removeVideo, undoRemoveVideo, generateDriverFiles);
	}
}
