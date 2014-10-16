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
import video.LoadedVideo;
import xmlcontrol.writers.DriverXMLWriter;
import xmlcontrol.writers.MasterXMLWriter;
import xmlcontrol.writers.XMLWriter;
import javafx.collections.ObservableList;

public class XMLController {
	
	public static final String FILE_PATH = "./src/xml/videos.xml";
	
	private DriverXMLWriter myDriverWriter;
	private MasterXMLWriter myMasterWriter;
	private Document myDocument;
	private ObservableList<LoadedVideo> myVideoList;
	
	public XMLController(ObservableList<LoadedVideo> videoList) throws FileNotFoundException, 
	SAXException, IOException, ParserConfigurationException, TransformerConfigurationException{
		
		myVideoList = videoList;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		myDocument = builder.parse(new FileInputStream(new File(FILE_PATH)));
		
		MasterXMLParser parser = new MasterXMLParser(myDocument);
		parser.buildVideos(videoList);
		
		myDriverWriter = new DriverXMLWriter();
		myMasterWriter = new MasterXMLWriter(parser.getVideoNodeMap());
	}

	public void editMasterFile(ObservableList<LoadedVideo> myVideosList) throws TransformerException {
		myMasterWriter.editMasterFile(myVideosList);
	}

	public void buildDriverFile(ObservableList<LoadedVideo> myVideosList,
			String fileName) throws TransformerException {
		myDriverWriter.buildDriverFile(myVideosList, fileName);
	}

	public void consumeXMLFiles(ObservableList<PlayedVideo> myImportedVideos) throws TransformerException {
		myMasterWriter.consumeXMLFiles(myDocument, myVideoList, myImportedVideos);		
	}
}
