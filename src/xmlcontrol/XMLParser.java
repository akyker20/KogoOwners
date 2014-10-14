package xmlcontrol;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import video.Video;

/**
 * XMLParser that uses the SAX library to parse the master file and
 * create video instances that will later be displayed to the user
 * on the front end in a TableView.
 * @author Austin Kyker
 *
 */
public class XMLParser extends DefaultHandler {

	public static final String FILE_PATH = "./src/xml/videos.xml";
	public static final String VIDEO = "video";
	public static final String TITLE = "title";
	public static final String COMPANY = "company";
	public static final String LENGTH = "length";
	public static final String PLAYS_PURCHASED = "playsPurchased";
	public static final String PLAYS_REMAINING = "playsRemaining";
	

	private ObservableList<Video> myVideoList;
	private SAXParser saxParser;

	public XMLParser(ObservableList<Video> list) 
			throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory docFactory = SAXParserFactory.newInstance();
		saxParser = docFactory.newSAXParser();
		myVideoList = list;
		parseFile(new File(FILE_PATH));
	}

	/**
	 * SAXParser parses the document.
	 * @param xmlFile
	 * @throws SAXException
	 * @throws IOException
	 */
	private void parseFile(File xmlFile) throws SAXException, IOException {
		try {
			saxParser.parse(xmlFile, this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * When a video element is found, it is found and a video instance is
	 * created and added to the observanble list so that it can be displayed
	 * in the TableView.
	 */
	@Override
	public void startElement (String uri,
			String localName,
			String elementName,
			Attributes attributes) throws SAXException {
		if (elementName.equalsIgnoreCase(VIDEO)) {
			String title = attributes.getValue(TITLE);
			String company = attributes.getValue(COMPANY);
			int length = Integer.parseInt(attributes.getValue(LENGTH));
			int numPlaysPurchased = Integer.parseInt(attributes.getValue(PLAYS_PURCHASED));
			int numPlaysRemaining = Integer.parseInt(attributes.getValue(PLAYS_REMAINING));
			myVideoList.add(new Video(company, title, numPlaysPurchased, numPlaysRemaining, length));
		}
	}
}
