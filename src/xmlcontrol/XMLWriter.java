package xmlcontrol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import control.Controller;
import video.PlayedVideo;
import video.Video;

/**
 * The purpose of this class is to provide a means for generating driver
 * xml files, and also be able to edit, remove, and add advertisements to
 * the master file.
 * @author Austin Kyker
 *
 */
public class XMLWriter {

	public static final String DRIVER_PATH = "./src/xml/driver_info.xml";
	
	private static final boolean FOR_DRIVER = true;
	private static final boolean NOT_FOR_DRIVER = false;

	private DocumentBuilder myBuilder;
	private Transformer myTransformer;

	public XMLWriter() throws FileNotFoundException, SAXException, IOException, 
	ParserConfigurationException, TransformerConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		myBuilder = factory.newDocumentBuilder();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		myTransformer = transformerFactory.newTransformer();
		myTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
	}

	/**
	 * Builds a new document based on the contents of the input observable 
	 * list (the videos in the TableView), and overwrites the master file
	 * with these changes.
	 * @param videoList
	 * @throws TransformerException
	 */
	public void editMasterFile(ObservableList<Video> videoList) throws TransformerException{
		Document document = buildMasterDocument(videoList);
		writeFile(document, new File(MasterXMLParser.FILE_PATH));
	}
	
	/**
	 * Builds a new document based on the video instances provided.
	 * @param videoList
	 * @return
	 */
	private Document buildMasterDocument(ObservableList<Video> videoList){
		Document document = myBuilder.newDocument();
		Element videosTag = document.createElement("videos");
		document.appendChild(videosTag);
		createVideoNodeList(document, videoList, videosTag, NOT_FOR_DRIVER);
		return document;
	}

	/**
	 * Generates the xml file for the drivers.
	 * @throws TransformerException 
	 */
	public void buildDriverFile(ObservableList<Video> videoList, String fileName) throws TransformerException {
		Document document = buildDriverDocument(videoList);
		writeFile(document, new File("./src/xml/" + fileName));
	}


	/**
	 * Sets up the elements required by the Kogo Driver XML Parser.
	 * The status element is given an attribute initialized which is
	 * set to false. The method uses the createVideoNodeList method to
	 * populate its videos node.
	 * @param videoList
	 * @return
	 */
	private Document buildDriverDocument(ObservableList<Video> videoList){
		Document document = myBuilder.newDocument();
		Element driverTag = document.createElement("driver");
		Element statusTag = document.createElement("status");
		statusTag.setAttribute("initialized", "false");
		driverTag.appendChild(statusTag);
		Element videosTag = document.createElement("videos");
		createVideoNodeList(document, videoList, videosTag, FOR_DRIVER);
		driverTag.appendChild(videosTag);
		document.appendChild(driverTag);
		return document;
	}

	/**
	 * Creates a video node list that is different depending on whether this
	 * method is called in the process of generating a driver xml file or 
	 * simply editing the master file.
	 * @param document
	 * @param videoList
	 * @param videosTag
	 * @param forDriver
	 */
	private void createVideoNodeList(Document document, ObservableList<Video> videoList, 
			Element videosTag, boolean forDriver) {
		for(Video video:videoList){
			Element videoNode = document.createElement("video");
			videoNode.setAttributeNode(makeNode(document, "title", video.getMyName()));
			videoNode.setAttributeNode(makeNode(document, "company", video.getMyCompany()));
			if(forDriver){
			videoNode.setAttributeNode(makeNode(document, "maxPlays", ""+ video.getMyPlaysRemaining()/Controller.NUM_DRIVERS));
			}
			else{
				videoNode.setAttributeNode(makeNode(document, "playsPurchased", ""+ video.getMyPlaysPurchased()));
				videoNode.setAttributeNode(makeNode(document, "playsRemaining", ""+ video.getMyPlaysRemaining()));
				
			}
			videoNode.setAttributeNode(makeNode(document, "length", ""+video.getMyLength()));
			videosTag.appendChild(videoNode);	
		}
	}
	
	/**
	 * A helper method to create a new attribute with a name and value.
	 * @param document
	 * @param name
	 * @param value
	 * @return
	 */
	private Attr makeNode(Document document, String name, String value) {
		Attr attr = document.createAttribute(name);
		attr.setValue(value);
		return attr;
	}

	/**
	 * Writes the contents of the document to the XML file specified.
	 * @param document
	 * @param xmlFile
	 * @throws TransformerException
	 */
	private void writeFile(Document document, File xmlFile) throws TransformerException {
		xmlFile.setWritable(true);
		StreamResult result = new StreamResult(xmlFile);
		myTransformer.transform(new DOMSource(document), result);
		xmlFile.setReadOnly();
		System.out.println("File saved!");
	}

	public void consumeXMLFiles(ObservableList<PlayedVideo> importedVideo) {
		
		
	}
}