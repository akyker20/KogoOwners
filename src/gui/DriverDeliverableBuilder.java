package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import utilities.ErrorPopup;

public class DriverDeliverableBuilder {
	private static final String COPY_DIR_ERROR_MSG = 
			"Problem copying deliverable directory.";

	/**
	 * Relies on a videos folder being in the project with all the video files
	 * within. Generates the xml file for the drivers.
	 */
	public void buildDriverDeliverableFolder(String fileName) {
		String dateStr = LocalDate.now().format(
				DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		new File("./driver/deliverable_" + dateStr + "/").mkdir();

		copyVideosIntoDeliverableFolder(dateStr);
	}

	private void copyVideosIntoDeliverableFolder(String dateStr) {
		File videoSource = new File("./videos/");
		File target = new File("./driver/deliverable_" + dateStr + "/videos/");
		try {
			copyDirectory(videoSource, target);
		} catch (IOException e) {
			new ErrorPopup(COPY_DIR_ERROR_MSG);
		}
	}

	// If targetLocation does not exist, it will be created.
	public void copyDirectory(File sourceLocation, File targetLocation)
			throws IOException {
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]), new File(
						targetLocation, children[i]));
			}
		} else {

			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}

	}
}
