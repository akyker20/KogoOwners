package xmlcontrol.writers;

import gui.GUIController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.ObservableList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import video.LoadedVideo;

public class DriverXMLWriter extends XMLWriter {

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
	public void buildDriverFile(ObservableList<LoadedVideo> videoList, String fileName) throws TransformerException {
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
	private Document buildDriverDocument(ObservableList<LoadedVideo> videoList){
		Document document = myBuilder.newDocument();
		Element driverTag = document.createElement("driver");
		Element statusTag = document.createElement("status");
		statusTag.setAttribute("initialized", "false");
		driverTag.appendChild(statusTag);
		Element videosTag = document.createElement("videos");
		for(LoadedVideo video:videoList){
			videosTag.appendChild(createVideoNode(video, document));
		}
		driverTag.appendChild(videosTag);
		document.appendChild(driverTag);
		return document;
	}


	@Override
	protected Element createVideoNode(LoadedVideo video, Document document) {
		Element videoNode = document.createElement("video");
		videoNode.setAttributeNode(makeNode(document, "title", video.getMyName()));
		videoNode.setAttributeNode(makeNode(document, "company", video.getMyCompany()));
		videoNode.setAttributeNode(makeNode(document, "length", ""+video.getMyLength()));
		videoNode.setAttributeNode(makeNode(document, "maxPlays", ""+ video.getMyPlaysRemaining()/GUIController.NUM_DRIVERS));
		return videoNode;
	}
}