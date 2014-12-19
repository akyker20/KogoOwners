package gui;

import java.io.File;
import java.util.List;

import control.Controller;
import video.ActiveVideo;


public class ImportedFile {
	private String myFileName;
	private List<ActiveVideo> myVideos;
	
	public ImportedFile(File file) {
		myFileName = file.getName();
		myVideos = Controller.GSON_READER.extractVideoData(file).getVideos();
	}
	
	public String getFileName() {
		return myFileName;
	}
	
	public List<ActiveVideo> getVideoData() {
		return myVideos;
	}
}
