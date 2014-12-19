package video;

import java.util.List;

public class TransferVideoData {
	private boolean myTerminationStatus;
	private List<ActiveVideo> myVideos;
	
	public TransferVideoData(List<ActiveVideo> videos) {
		myTerminationStatus = false;
		myVideos = videos;
	}
	
	public boolean isTerminated() {
		return myTerminationStatus;
	}
	
	public void terminate() {
		myTerminationStatus = true;
	}
	
	public List<ActiveVideo> getVideos() {
		return myVideos;
	}
}