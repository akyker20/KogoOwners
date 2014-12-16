package xmlcontrol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javafx.collections.ObservableList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import video.LoadedVideo;
import video.PlayedVideo;
import xmlcontrol.writers.ImportedFilesXMLWriter;
import xmlcontrol.writers.MasterXMLWriter;

public class XMLController {

	public static final String FILE_PATH = "./src/xml/videos.json";
	public static final String IMPORTED_FILES_PATH = "./src/xml/imported_files.xml";
	public static final String FILES = "files";
	public static final String MONTH = "month";
	public static final String DAY = "day";
	public static final String YEAR = "year";
	public static final String DATE_REGEX = "(?<month>\\d{1,2})-(?<day>\\d{1,2})-(?<year>\\d{4})";

	private MasterXMLWriter myMasterWriter;
	private ImportedFilesXMLWriter myImportedFilesWriter;
	private ImportedFilesXMLParser myImportedFilesParser;
	private MasterXMLParser myMasterXMLParser;
	private Document myVideosDocument;
	private Document myImportedFilesDocument;
	private ObservableList<LoadedVideo> myVideoList;

	public XMLController() {
		try{
			createVideoDocuments();	
			myMasterXMLParser = new MasterXMLParser(myVideosDocument);
			myMasterWriter = new MasterXMLWriter(myMasterXMLParser.getVideoNodeMap());
			myImportedFilesParser = new ImportedFilesXMLParser(myImportedFilesDocument);
			myImportedFilesWriter = new ImportedFilesXMLWriter(myImportedFilesDocument, myImportedFilesParser.getMap());
		}
		catch(Exception E){
			//handle this
		}
	}

	private void createVideoDocuments() throws ParserConfigurationException,
	SAXException, IOException, FileNotFoundException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		myVideosDocument = builder.parse(new FileInputStream(new File(FILE_PATH)));
		myImportedFilesDocument = builder.parse(new FileInputStream(new File(IMPORTED_FILES_PATH)));
	}

	public ObservableList<LoadedVideo> buildVideosFromMasterXML() {
		myVideoList = myMasterXMLParser.buildVideosFromXML();
		return myVideoList;	
	}

	public void consumeXMLFiles(ObservableList<PlayedVideo> myImportedVideos, List<File> files) {
		myMasterWriter.consumeXMLFiles(myVideosDocument, myVideoList, myImportedVideos);
		myImportedFilesWriter.writeNewImportedFilesToPreviouslyImportedFile(files);
	}

	public boolean canImport(File f) {
		return myImportedFilesParser.canImport(f);
	}
}
