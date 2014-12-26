package gui;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import control.Controller;
import video.ActiveVideo;
import video.TransferVideoData;


public class ImportedFile {
	private String myFileName;
	private List<ActiveVideo> myVideos;
	private LocalDate myDate;
	
	public ImportedFile(File file) {
		myFileName = file.getName();
		TransferVideoData data = Controller.GSON_READER.extractVideoData(file);
		myVideos = data.getVideos();
		myDate = data.getDate();
	}
	
	public String getFileName() {
		return myFileName;
	}
	
	public List<ActiveVideo> getVideoData() {
		return myVideos;
	}
	
	public LocalDate getDate() {
		return myDate;
	}
	
	public double getTotalRevenue() {
		return myVideos.stream().map(vid->vid.getMyRevenue()).reduce(0.0, (a, b) -> a+b); 
	}
	
	public int getTotalImpactSeconds() {
		return myVideos.stream().map(vid->vid.getMyPlays()*vid.getMyLength()).reduce(0, (a, b) -> a+b);
	}
}