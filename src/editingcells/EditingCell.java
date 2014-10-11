package editingcells;
import gui.Video;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

public abstract class EditingCell extends TableCell<Video, String> {

		protected TextField textField;

		public EditingCell() {
			textField = new TextField();
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
		}

		@Override
		public void startEdit() {
			if (!isEmpty()) {
				super.startEdit();
				createTextField();
				setText(null);
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
					System.out.println(getString());
					setText(getString());
					setGraphic(null);
				}
			}
		}

		public abstract void createTextField();

		protected String getString() {
			return getItem() == null ? "" : getItem().toString();
		}
	}