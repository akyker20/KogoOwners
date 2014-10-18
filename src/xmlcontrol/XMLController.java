package xmlcontrol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import video.PlayedVideo;
import video.LoadedVideo;
import xmlcontrol.writers.DriverXMLWriter;
import xmlcontrol.writers.MasterXMLWriter;
import xmlcontrol.writers.ImportedFilesXMLWriter;
import xmlcontrol.writers.XMLWriter;
import javafx.collections.ObservableList;

public class XMLController {
	
	public static final String FILE_PATH = "./src/xml/videos.xml";
	public static final String IMPORTED_FILES_PATH = "./src/xml/imported_files.xml";
	public static final String FILES = "files";
	public static final String MONTH = "month";
	public static final String DAY = "day";
	public static final String YEAR = "year";
	public static final String DATE_REGEX = "(?<month>\\d{1,2})-(?<day>\\d{1,2})-(?<year>\\d{4})";
	
	private DriverXMLWriter myDriverWriter;
	private MasterXMLWriter myMasterWriter;
	private ImportedFilesXMLWriter myImportedFilesWriter;
	private ImportedFilesXMLParser myImportedFilesParser;
	private Document myVideosDocument;
	private Document myImportedFilesDocument;
	private ObservableList<LoadedVideo> myVideoList;
	
	public XMLController(ObservableList<LoadedVideo> videoList) throws FileNotFoundException, 
	SAXException, IOException, ParserConfigurationException, TransformerConfigurationException{
		
		myVideoList = videoList;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		myVideosDocument = builder.parse(new FileInputStream(new File(FILE_PATH)));
		
		MasterXMLParser parser = new MasterXMLParser(myVideosDocument);
		parser.buildVideos(videoList);
		
		myDriverWriter = new DriverXMLWriter();
		myMasterWriter = new MasterXMLWriter(parser.getVideoNodeMap());
		
		myImportedFilesDocument = builder.parse(new FileInputStream(new File(IMPORTED_FILES_PATH)));
		myImportedFilesParser = new ImportedFilesXMLParser(myImportedFilesDocument);
		myImportedFilesWriter = new ImportedFilesXMLWriter(myImportedFilesDocument, myImportedFilesParser.getMap());
	}

	public void editMasterFile(ObservableList<LoadedVideo> myVideosList) throws TransformerException {
		myMasterWriter.editMasterFile(myVideosList);
	}

	public void buildDriverFile(ObservableList<LoadedVideo> myVideosList,
			String fileName) throws TransformerException {
		myDriverWriter.buildDriverFile(myVideosList, fileName);
	}

	public void consumeXMLFiles(ObservableList<PlayedVideo> myImportedVideos, List<File> files) throws TransformerException {
		myMasterWriter.consumeXMLFiles(myVideosDocument, myVideoList, myImportedVideos);
		myImportedFilesWriter.writeNewImportedFiles(files);
	}

	public boolean canImport(File f) {
		return myImportedFilesParser.canImport(f);
	}
}
