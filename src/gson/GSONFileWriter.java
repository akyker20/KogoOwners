package gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import utilities.popups.ErrorPopup;
import video.ActiveVideo;
import video.DriverSessionData;
import video.LoadedVideo;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GSONFileWriter {

	private static final String MASTER_VID_JSON_PATH = "./src/json/videos.json";
	private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
	private static final String DRIVER_IMPORTED_FILES_PATH = "./src/json/imported_videos.json";

	public void writeDriverFile(String fileName, List<ActiveVideo> videos, LocalDate date) {
		DriverSessionData data = new DriverSessionData(videos, date);
		writeToFile(fileName,
				GSON_BUILDER.create().toJson(data, DriverSessionData.class));
	}

	public void writeMasterFile(List<LoadedVideo> videos) {
		writeToFile(
				MASTER_VID_JSON_PATH,
				GSON_BUILDER.create().toJson(videos,
						new TypeToken<List<LoadedVideo>>() {
						}.getType()));
	}
	
	public void writeImportedFiles(List<DriverSessionData> importedFiles) {
		writeToFile(
				DRIVER_IMPORTED_FILES_PATH,
				GSON_BUILDER.create().toJson(importedFiles,
						new TypeToken<List<DriverSessionData>>() {
						}.getType()));
	}

	public void writeToFile(String fileName, String json) {
		try {
			File file = new File(fileName);
			file.setWritable(true);
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			writer.close();
			file.setReadOnly();
		} catch (IOException e) {
			new ErrorPopup("Could not write to " + fileName);
		}
	}
}