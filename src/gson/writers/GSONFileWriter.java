package gson.writers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import utilities.ErrorPopup;
import video.LoadedVideo;
import video.PlayedVideo;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GSONFileWriter {

	private static final String ERROR_MSG = "File to store videos could not be found.";
	private static final GsonBuilder GSON_BUILDER = new GsonBuilder();

	public void writeDriverFile(String fileName, List<PlayedVideo> videos) {
		writeToFile(fileName, GSON_BUILDER.create().toJson(videos,
				new TypeToken<List<PlayedVideo>>() {}.getType()));
	}
	
	public void writeMasterFile(List<LoadedVideo> videos) {
		writeToFile("./src/json/videos.json", GSON_BUILDER.create().toJson(videos,
				new TypeToken<List<LoadedVideo>>() {}.getType()));
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

}