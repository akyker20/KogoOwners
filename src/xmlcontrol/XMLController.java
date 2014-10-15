package xmlcontrol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import video.PlayedVideo;
import video.Video;
import javafx.collections.ObservableList;

public class XMLController {
	
	public static final String FILE_PATH = "./src/xml/videos.xml";
	
	private XMLWriter myWriter;
	private Document myDocument;
	private ObservableList<Video> myVideoList;
	
	public XMLController(ObservableList<Video> videoList) throws FileNotFoundException, 
	SAXException, IOException, ParserConfigurationException, TransformerConfigurationException{
		
		myVideoList = videoList;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		myDocument = builder.parse(new FileInputStream(new File(FILE_PATH)));
		
		MasterXMLParser parser = new MasterXMLParser(myDocument);
		parser.buildVideos(videoList);
		myWriter = new XMLWriter(parser.getVideoNodeMap());
	}

	public void editMasterFile(ObservableList<Video> myVideosList) throws TransformerException {
		myWriter.editMasterFile(myVideosList);
	}

	public void buildDriverFile(ObservableList<Video> myVideosList,
			String fileName) throws TransformerException {
		myWriter.buildDriverFile(myVideosList, fileName);
	}

	public void consumeXMLFiles(ObservableList<PlayedVideo> myImportedVideos) throws TransformerException {
		myWriter.consumeXMLFiles(myDocument, myVideoList, myImportedVideos);		
	}
}
