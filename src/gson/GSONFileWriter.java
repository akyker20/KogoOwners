package gson;

import gui.ImportedFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javafx.collections.ObservableList;
import utilities.popups.ErrorPopup;
import video.LoadedVideo;
import video.ActiveVideo;
import video.TransferVideoData;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GSONFileWriter {

	private static final String MASTER_VID_JSON_PATH = "./src/json/videos.json";
	private static final String ERROR_MSG = "File to store videos could not be found.";
	private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
	private static final String DRIVER_IMPORTED_FILES_PATH = "./src/json/imported_videos.json";

	public void writeDriverFile(String fileName, List<ActiveVideo> videos) {
		TransferVideoData data = new TransferVideoData(videos);
		writeToFile(fileName,
				GSON_BUILDER.create().toJson(data, TransferVideoData.class));
	}

	public void writeMasterFile(List<LoadedVideo> videos) {
		writeToFile(
				MASTER_VID_JSON_PATH,
				GSON_BUILDER.create().toJson(videos,
						new TypeToken<List<LoadedVideo>>() {
						}.getType()));
	}

	public void writeToFile(String fileName, String json) {
		try {
			File file = new File(fileName);
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			new ErrorPopup(ERROR_MSG);
		}
	}

	public void writeImportedFiles(List<ImportedFile> importedFiles) {
		writeToFile(
				DRIVER_IMPORTED_FILES_PATH,
				GSON_BUILDER.create().toJson(importedFiles,
						new TypeToken<List<ImportedFile>>() {
						}.getType()));

	}

}