package menus;

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
        
        MenuItem generateDriverFiles = new MenuItem("Make Driver Files");
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
        
        this.getItems().addAll(removeVideo, undoRemoveVideo, generateDriverFiles);
    }
}
