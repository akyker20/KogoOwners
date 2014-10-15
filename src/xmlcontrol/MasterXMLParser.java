package xmlcontrol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import video.Video;
import javafx.collections.ObservableList;


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

	private Map<Video, Node> myVideoNodeMap;
	private ObservableList<Video> myVideoList;
	private Document myDocument;

	public MasterXMLParser(Document document, ObservableList<Video> videoList) {
		myVideoList = videoList;
		myDocument = document;
		myVideoNodeMap = new HashMap<Video, Node>();
	}

	/**
	 * Looks at all the video nodes and builds video instances. Each video instance
	 * is added to the input ArrayList which will hold all the videos and be maintained
	 * in the controller. The map is also updated to map each video instance to its node.
	 * @param videoList - the list that will hold all videos
	 * @param fileAlreadyInitialized - whether or not the file has already been initialized
	 */
	public void buildVideos(ArrayList<Video> videoList, boolean fileAlreadyInitialized) {
		Element root = myDocument.getDocumentElement();
		NodeList videoNodes = root.getElementsByTagName(VIDEO);
		for(int i = 0; i < videoNodes.getLength(); i++){
			Node videoNode = videoNodes.item(i);
			if (videoNode instanceof Element && videoNode.getNodeName().equalsIgnoreCase(VIDEO)) {
				Video video = buildVideoFromNode(videoNode);
				myVideoList.add(video);
				myVideoNodeMap.put(video, videoNode);
			}
		}
	}

	/**
	 * Creates a video instance from the videoNode.
	 * @param videoNode
	 * @param fileAlreadyInitialized
	 * @return
	 */
	private Video buildVideoFromNode(Node videoNode) {
		NamedNodeMap attributes = videoNode.getAttributes();
		int numPlaysPurchased = Integer.parseInt(getAttributeValue(attributes, PLAYS_PURCHASED));
		int numPlaysRemaining = Integer.parseInt(getAttributeValue(attributes, PLAYS_REMAINING));
		int length = Integer.parseInt(getAttributeValue(attributes, LENGTH));
		String title = getAttributeValue(attributes, TITLE);
		String company = getAttributeValue(attributes, COMPANY);
		return new Video(company, title, numPlaysPurchased, numPlaysRemaining, length);
	}

	/**
	 * Helper function to fetch an attribute value.
	 * @param attributes
	 * @param attrName
	 * @return
	 */
	private String getAttributeValue(NamedNodeMap attributes, String attrName) {
		return attributes.getNamedItem(attrName).getNodeValue();
	}

	/**
	 * Allows the XMLController to provide the XMLWriter with the node map so
	 * that editing the driver XML File is simple.
	 * @return the VideoNode map
	 */
	public Map<Video, Node> getVideoNodeMap() {
		return myVideoNodeMap;
	}
}


