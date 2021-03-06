package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import control.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.popups.ErrorPopup;
import video.ActiveVideo;
import video.DriverSessionData;
import video.LoadedVideo;

public class ImportedFilesManager {
	private static final String FILE_ALREADY_IMPORTED_ERROR = "The file you are trying to import has already been imported.";
	private List<DriverSessionData> myImportedFiles;
	private List<ActiveVideo> myCurrentlyImportedVideos;
	private List<DriverSessionData> myPreviouslyImportedFiles;
	private List<ActiveVideo> myVideosToRefreshed;

	public ImportedFilesManager() {
		myVideosToRefreshed = new ArrayList<ActiveVideo>();
		myImportedFiles = new ArrayList<DriverSessionData>();
		myCurrentlyImportedVideos = FXCollections.observableArrayList();
		myPreviouslyImportedFiles = Controller.GSON_READER.readImportedFiles();
	}

	/**
	 * Returns true if the file has not been previously imported - if
	 * there are no previously imported files with the same name.
	 */
	public boolean canImport(File file) {
		myPreviouslyImportedFiles = Controller.GSON_READER.readImportedFiles();
		for(DriverSessionData importedFile:myPreviouslyImportedFiles) {
			System.out.println(file.getName());
			System.out.println(importedFile.getFileName());
			if(file.getName().equalsIgnoreCase(importedFile.getFileName())) {
				new ErrorPopup(FILE_ALREADY_IMPORTED_ERROR);
				return false;
			}
		}
		return true;
	}

	public void importVideoFile(File file) {
		DriverSessionData importedFile = Controller.GSON_READER.extractVideoData(file);
		myImportedFiles.add(importedFile);
		for (ActiveVideo vid : importedFile.getVideos()) {
			importVideo(vid);
		}
		refreshTable();
	}

	private void importVideo(ActiveVideo vid) {
		for (ActiveVideo importedVideo : myCurrentlyImportedVideos) {
			if (vid.sameAs(importedVideo)) {
				importedVideo.incrementCompletedViews(vid.getMyPlays());
				myVideosToRefreshed.add(importedVideo);
				return;
			}
		}
		myCurrentlyImportedVideos.add(vid);
	}


	/**
	 * Refreshes the table by removing and then adding the videos
	 * that were updated.
	 */
	private void refreshTable() {
		if(!myVideosToRefreshed.isEmpty()){
			myCurrentlyImportedVideos.removeAll(myVideosToRefreshed);
			myCurrentlyImportedVideos.addAll(myVideosToRefreshed);
			myVideosToRefreshed.clear();
		}
	}

	public ObservableList<ActiveVideo> getCurrentlyImportedVideos() {
		return (ObservableList<ActiveVideo>) myCurrentlyImportedVideos;
	}

	public List<LoadedVideo> recalculateVideoData() {
		List<LoadedVideo> updatedVideos = new ArrayList<LoadedVideo>();
		for(DriverSessionData file:getAllImportedFiles()) {
			for(ActiveVideo importedVid:file.getVideos()) {
				int indexOfVideo = updatedVideos.indexOf(importedVid);
				if(indexOfVideo != -1) {
					updatedVideos.get(indexOfVideo).addStudentViews(importedVid.getMyPlays());			
				}
				else {
					updatedVideos.add(new LoadedVideo(importedVid));
				}
			}
		}
		return updatedVideos;
	}

	/**
	 * Updates the stored imported files in json.
	 */
	public void rewriteImportFiles() {
		List<DriverSessionData> allImportedFiles = getAllImportedFiles();
		Controller.GSON_WRITER.writeImportedFiles(allImportedFiles);		
	}

	private List<DriverSessionData> getAllImportedFiles() {
		List<DriverSessionData> allImportedFiles = new ArrayList<DriverSessionData>();
		allImportedFiles.addAll(myPreviouslyImportedFiles);
		allImportedFiles.addAll(myImportedFiles);
		return allImportedFiles;
	}
}