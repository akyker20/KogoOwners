package gson;

import gui.scenes.Driver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.popups.ErrorPopup;
import video.DriverSessionData;
import video.LoadedVideo;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GSONFileReader {

	private static final String IMPORTED_FILES_READ_ERROR = "Failed to read imported video files.";
	private static final String VID_DATA_EXTRACTION_ERROR = "Could not extract video data from single Json File";
	private static final String READ_ERROR_MSG = "Could not read from the master videos json file.";
	private static final String MASTER_VIDEOS_PATH = "./src/json/videos.json";
	private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
	private static final String IMPORTED_VIDEOS_PATH = "./src/json/imported_videos.json";
	private static final String DRIVERS_PATH = "./src/json/drivers.json";
	private static final String DRIVER_ERROR = "Could not read drivers from drivers.json";

	public ObservableList<LoadedVideo> readVideosFromMasterJSON() {
		List<LoadedVideo> videos = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					MASTER_VIDEOS_PATH));
			videos = GSON_BUILDER.create().fromJson(br,
					new TypeToken<List<LoadedVideo>>() {
					}.getType());
		} catch (IOException e) {
			new ErrorPopup(READ_ERROR_MSG);
		}
		if (videos == null) {
			videos = new ArrayList<LoadedVideo>();
		}
		return FXCollections.observableArrayList(videos);
	}

	/**
	 * Used in owner application to extract the imported file video data.
	 */
	public DriverSessionData extractVideoData(File importedJson) {
		DriverSessionData data = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(importedJson));
			data = GSON_BUILDER.create().fromJson(br, DriverSessionData.class);
		} catch (IOException e) {
			new ErrorPopup(VID_DATA_EXTRACTION_ERROR);
		}
		return data;
	}

	public List<DriverSessionData> readImportedFiles() {
		List<DriverSessionData> importedFiles = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					IMPORTED_VIDEOS_PATH));
			importedFiles = GSON_BUILDER.create().fromJson(br,
					new TypeToken<List<DriverSessionData>>() {}.getType());
		} catch (IOException e) {
			new ErrorPopup(IMPORTED_FILES_READ_ERROR);
		}
		if (importedFiles == null) {
			importedFiles = new ArrayList<DriverSessionData>();
		}
		return importedFiles;
	}

	public List<Driver> readDrivers() {
		List<Driver> drivers = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(DRIVERS_PATH));
			drivers = GSON_BUILDER.create().fromJson(br,
					new TypeToken<List<Driver>>() {}.getType());
		} catch (IOException e) {
			new ErrorPopup(DRIVER_ERROR);
		}
		return drivers;
	}
}