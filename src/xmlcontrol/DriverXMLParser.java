package xmlcontrol;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import video.PlayedVideo;


/**
 * XMLParser that uses the SAX library to parse the master file and
 * create video instances that will later be displayed to the user
 * on the front end in a TableView.
 * @author Austin Kyker
 *
 */
public class DriverXMLParser extends DefaultHandler {

	public static final String VIDEO = "video";
	public static final String TITLE = "title";
	public static final String COMPANY = "company";
	public static final String LENGTH = "length";
	public static final String PLAYS = "plays";


	private ObservableList<PlayedVideo> myImportedVideos;
	private SAXParser saxParser;
	private ArrayList<PlayedVideo> videosToRefresh;

	public DriverXMLParser(ObservableList<PlayedVideo> importedVideos) 
			throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory docFactory = SAXParserFactory.newInstance();
		saxParser = docFactory.newSAXParser();
		myImportedVideos = importedVideos;
		videosToRefresh = new ArrayList<PlayedVideo>();
	}

	/**
	 * SAXParser parses the document.
	 * @param xmlFile
	 * @throws SAXException
	 * @throws IOException
	 */
	public void parseFile(File xmlFile) throws SAXException, IOException {
		try {
			saxParser.parse(xmlFile, this);
			refreshTable();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Refreshes the table by removing and then adding the videos
	 * that were updated.
	 */
	private void refreshTable() {
		if(!videosToRefresh.isEmpty()){
			myImportedVideos.removeAll(videosToRefresh);
			myImportedVideos.addAll(videosToRefresh);
			videosToRefresh.clear();
		}
	}

	/**
	 * When a video element is found, it is found and a video instance is
	 * created and added to the observable list so that it can be displayed
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
			int playsCompleted = Integer.parseInt(attributes.getValue(PLAYS));
			for(PlayedVideo video:myImportedVideos){
				if(company.equalsIgnoreCase(video.getMyCompany()) && title.equalsIgnoreCase(video.getMyName())){
					video.incrementCompletedViews(playsCompleted);
					videosToRefresh.add(video);
					return;
				}
			}
			myImportedVideos.add(new PlayedVideo(company, title, playsCompleted, length));
		}
	}
}
