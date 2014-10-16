package xmlcontrol.writers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javafx.collections.ObservableList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import control.Controller;
import video.Video;
import xmlcontrol.XMLController;

public class DriverXMLWriter extends XMLWriter {

	private static final String DRIVER_PATH = "./src/xml/driver_info.xml";
	private static final boolean FOR_DRIVER = true;
	private static final boolean NOT_FOR_DRIVER = false;

	private DocumentBuilder myBuilder;

	public DriverXMLWriter() throws FileNotFoundException, SAXException, IOException, 
	ParserConfigurationException, TransformerConfigurationException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		myBuilder = factory.newDocumentBuilder();
	}


	/**
	 * Generates the xml file for the drivers.
	 * @throws TransformerException 
	 */
	public void buildDriverFile(ObservableList<Video> videoList, String fileName) throws TransformerException {
		Document document = buildDriverDocument(videoList);
		super.writeFile(document, new File("./src/xml/" + fileName));
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
		for(Video video:videoList){
			videosTag.appendChild(createVideoNode(video, document));
		}
		driverTag.appendChild(videosTag);
		document.appendChild(driverTag);
		return document;
	}


	@Override
	protected Element createVideoNode(Video video, Document document) {
		Element videoNode = document.createElement("video");
		videoNode.setAttributeNode(makeNode(document, "title", video.getMyName()));
		videoNode.setAttributeNode(makeNode(document, "company", video.getMyCompany()));
		videoNode.setAttributeNode(makeNode(document, "length", ""+video.getMyLength()));
		videoNode.setAttributeNode(makeNode(document, "maxPlays", ""+ video.getMyPlaysRemaining()/Controller.NUM_DRIVERS));
		return videoNode;
	}
}