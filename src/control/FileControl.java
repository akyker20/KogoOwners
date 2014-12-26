package control;

import java.time.LocalDate;

public interface FileControl {

	public void consumeDriverFiles();

	public void uploadDriverFiles();

	public void backToTable();

	public void buildDriverFile(LocalDate date);
	
	public void enableConsumeDriverFilesItem();

	public void viewDriverStats();
}