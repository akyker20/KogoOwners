package editingcells;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class StringEditingCell extends EditingCell {

	@Override
	public void createTextField() {
		textField = new TextField(super.getString());
		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override public void handle(KeyEvent t) {
				System.out.println("HI");
				if (t.getCode() == KeyCode.ENTER) {
					commitEdit(textField.getText());
				} else if (t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
				}
			}
		});
	}
}
