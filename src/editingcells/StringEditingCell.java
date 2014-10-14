package editingcells;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * The purpose of this class is to provide a means for
 * editing the company name and title of a video.
 * @author Austin Kyker
 *
 */
public class StringEditingCell extends EditingCell {

	@Override
	public void createTextField() {
		textField = new TextField(super.getString());
		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ENTER) {
					commitEdit(textField.getText());
				} else if (t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
				}
			}
		});
	}
}
