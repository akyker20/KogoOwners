package xmlcontrol.writers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javafx.collections.ObservableList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import video.PlayedVideo;
import video.LoadedVideo;
import xmlcontrol.MasterXMLParser;
import xmlcontrol.XMLController;

public class MasterXMLWriter extends XMLWriter {

	private DocumentBuilder myBuilder;
	private Map<LoadedVideo, Node> myVideoNodeMap;
	private File myMasterFile;

	public MasterXMLWriter(Map<LoadedVideo, Node> videoNodeMap)  throws FileNotFoundException, SAXException, IOException, 
	ParserConfigurationException, TransformerConfigurationException  {
		myVideoNodeMap = videoNodeMap;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		myBuilder = factory.newDocumentBuilder();
		myMasterFile = new File(XMLController.FILE_PATH);
	}

	/**
	 * Builds a new document based on the contents of the input observable 
	 * list (the videos in the TableView), and overwrites the master file
	 * with these changes.
	 * @param videoList
	 * @throws TransformerException
	 */
	public void editMasterFile(ObservableList<LoadedVideo> videoList) {
		Document document = buildMasterDocument(videoList);
		super.writeFile(document, myMasterFile);
	}

	/**
	 * Builds a new document based on the video instances provided.
	 * @param videoList
	 * @return
	 */
	private Document buildMasterDocument(ObservableList<LoadedVideo> videoList){
		Document document = myBuilder.newDocument();
		Element videosTag = document.createElement("videos");
		document.appendChild(videosTag);
		for(LoadedVideo video:videoList){
			videosTag.appendChild(createVideoNode(video, document));
		}
		return document;
	}

	/**
	 * Consumes the xml files uploaded and updates the master xml file appropriately.
	 * @param document
	 * @param videos
	 * @param importedVideos
	 * @throws TransformerException
	 */
	public ObservableList<LoadedVideo> consumeXMLFiles(Document document, ObservableList<LoadedVideo> videos, 
			ObservableList<PlayedVideo> importedVideos) {
		for(PlayedVideo importedVideo:importedVideos){
			LoadedVideo videoIdentified = null;
			for(LoadedVideo video:videos){
				if(importedVideo.getMyCompany().equalsIgnoreCase(video.getMyCompany()) && 
						importedVideo.getMyName().equalsIgnoreCase(video.getMyName())){
					videoIdentified = video;
					break;
				}
			}
			Element videoNode = (Element) myVideoNodeMap.get(videoIdentified);
			int newPlaysRemaining = getNewRemainingPlays(videoIdentified, importedVideo);
			videoIdentified.setMyPlaysRemaining(newPlaysRemaining);
			videoNode.setAttribute(MasterXMLParser.PLAYS_REMAINING, "" + newPlaysRemaining);
		}
		writeFile(document, myMasterFile);	
		return videos;
	}

	/**
	 * Helper method for consumeXMLFile that computes the new value of the playsRemaining
	 * attrbute.
	 * @param videoIdentified
	 * @param importedVideo
	 * @return
	 */
	private int getNewRemainingPlays(LoadedVideo videoIdentified,
			PlayedVideo importedVideo) {
		return videoIdentified.getMyPlaysRemaining() - importedVideo.getMyPlaysCompleted();
	}

	@Override
	public Element createVideoNode(LoadedVideo video, Document document) {
		Element videoNode = document.createElement("video");
		videoNode.setAttributeNode(makeNode(document, "title", video.getMyName()));
		videoNode.setAttributeNode(makeNode(document, "company", video.getMyCompany()));
		videoNode.setAttributeNode(makeNode(document, "length", ""+video.getMyLength()));
		videoNode.setAttributeNode(makeNode(document, "playsPurchased", ""+ video.getMyPlaysPurchased()));
		videoNode.setAttributeNode(makeNode(document, "playsRemaining", ""+ video.getMyPlaysRemaining()));	
		return videoNode;
	}
}
