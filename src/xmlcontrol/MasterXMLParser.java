package xmlcontrol;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import video.LoadedVideo;


/**
 * XMLParser that uses the SAX library to parse the master file and
 * create video instances that will later be displayed to the user
 * on the front end in a TableView.
 * @author Austin Kyker
 *
 */
public class MasterXMLParser {

	public static final String LENGTH = "length";
	public static final String COMPANY = "company";
	public static final String TITLE = "title";
	public static final String VIDEO = "video";
	public static final String STATUS = "status";
	public static final String INITIALIZED = "initialized";
	public static final String PLAYS_PURCHASED = "playsPurchased";
	public static final String PLAYS_REMAINING = "playsRemaining";

	private Map<LoadedVideo, Node> myVideoNodeMap;
	private Document myDocument;

	public MasterXMLParser(Document document) {
		myDocument = document;
		myVideoNodeMap = new HashMap<LoadedVideo, Node>();
	}

	public ObservableList<LoadedVideo> buildVideosFromXML() {
		ObservableList<LoadedVideo> videoList = FXCollections.observableArrayList();
		Element root = myDocument.getDocumentElement();
		NodeList videoNodes = root.getElementsByTagName(VIDEO);
		buildNodeMapAndAddVideosToList(videoList, videoNodes);
		return videoList;
	}

	private void buildNodeMapAndAddVideosToList(
			ObservableList<LoadedVideo> videoList, NodeList videoNodes) {
		for(int i = 0; i < videoNodes.getLength(); i++){
			Node videoNode = videoNodes.item(i);
			if (videoNode instanceof Element && videoNode.getNodeName().equalsIgnoreCase(VIDEO)) {
				LoadedVideo video = buildVideoFromNode(videoNode);
				videoList.add(video);
				myVideoNodeMap.put(video, videoNode);
			}
		}
	}

	private LoadedVideo buildVideoFromNode(Node videoNode) {
		NamedNodeMap attributes = videoNode.getAttributes();
		int numPlaysPurchased = Integer.parseInt(getAttributeValue(attributes, PLAYS_PURCHASED));
		int numPlaysRemaining = Integer.parseInt(getAttributeValue(attributes, PLAYS_REMAINING));
		int length = Integer.parseInt(getAttributeValue(attributes, LENGTH));
		String title = getAttributeValue(attributes, TITLE);
		String company = getAttributeValue(attributes, COMPANY);
		return new LoadedVideo(company, title, numPlaysPurchased, numPlaysRemaining, length);
	}

	private String getAttributeValue(NamedNodeMap attributes, String attrName) {
		return attributes.getNamedItem(attrName).getNodeValue();
	}

	public Map<LoadedVideo, Node> getVideoNodeMap() {
		return myVideoNodeMap;
	}
}


