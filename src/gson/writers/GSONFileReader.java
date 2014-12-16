package gson.writers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.ErrorPopup;
import video.LoadedVideo;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GSONFileReader {

	private static final String READ_ERROR_MSG = "Could not read from the master videos json file.";
	private static final String MASTER_VIDEOS_PATH = "./src/json/videos.json";
	private static final GsonBuilder GSON_BUILDER = new GsonBuilder();

	public ObservableList<LoadedVideo> readVideosFromMasterJSON() {
		List<LoadedVideo> videos = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					MASTER_VIDEOS_PATH));
			videos = GSON_BUILDER.create().fromJson(br,
					new TypeToken<List<LoadedVideo>>() {}.getType());
		} catch (IOException e) {
			new ErrorPopup(READ_ERROR_MSG);
		}
		if(videos == null) {
			videos = new ArrayList<LoadedVideo>();
		}
		return FXCollections.observableArrayList(videos);
	}

}
