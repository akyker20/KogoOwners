package editingcells;

import video.LoadedVideo;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

/**
 * This class is a super class for different types of editing cells. For now,
 * there will only be a StringEditingCell because the integer fields should not
 * be editable, but this provides a means for future extension.
 * 
 * @author Austin Kyker
 *
 */
public abstract class EditingCell extends TableCell<LoadedVideo, String> {

	protected TextField textField;

	public EditingCell() {
		textField = new TextField();
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
	}

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			createTextField();
			setGraphic(textField);
			textField.selectAll();
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText((String) getItem());
		setGraphic(null);
	}

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (textField != null) {
					textField.setText(getString());
				}
				setText(null);
				setGraphic(textField);
			} else {
				setText(getString());
				setGraphic(null);
			}
		}
	}

	/**
	 * Method must be implemented by the subclasses.
	 */
	public abstract void createTextField();

	protected String getString() {
		return getItem() == null ? "" : getItem().toString();
	}
}