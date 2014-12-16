package gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import video.PlayedVideo;


/**
 * XMLParser that uses the SAX library to parse the input driver file and
 * create video instances that will later be displayed to the user
 * on the front end in a TableView.
 * @author Austin Kyker
 *
 */
public class InputDriverFileXMLParser extends DefaultHandler {

	public static final String VIDEO = "video";
	public static final String TITLE = "title";
	public static final String COMPANY = "company";
	public static final String LENGTH = "length";
	public static final String PLAYS = "plays";


	private ObservableList<PlayedVideo> myImportedVideos;
	private SAXParser saxParser;

	public InputDriverFileXMLParser(ObservableList<PlayedVideo> importedVideos) 
			throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory docFactory = SAXParserFactory.newInstance();
		saxParser = docFactory.newSAXParser();
		myImportedVideos = importedVideos;
	}

	public void parseFile(File xmlFile) throws SAXException, IOException {
		try {
			saxParser.parse(xmlFile, this);
			refreshTable();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void refreshTable() {
		List<PlayedVideo> tempList = new ArrayList<PlayedVideo>();
		for(PlayedVideo video:myImportedVideos){
			tempList.add(video);
		}
		myImportedVideos.clear();
		myImportedVideos.addAll(tempList);
	}

	@Override
	public void startElement (String uri, String localName, String elementName, 
			Attributes attributes) throws SAXException {
		
		if (elementName.equalsIgnoreCase(VIDEO)) {
			PlayedVideo videoCreatedFromNode = createVideoFromAttributes(attributes);
			for(PlayedVideo video:myImportedVideos){
				if(videoCreatedFromNode.sameAs(video)){
					video.incrementCompletedViews(videoCreatedFromNode.getMyPlaysCompleted());
					return;
				}
			}
			myImportedVideos.add(videoCreatedFromNode);
		}
	}

	private PlayedVideo createVideoFromAttributes(Attributes attributes) {
		String title = attributes.getValue(TITLE);
		String company = attributes.getValue(COMPANY);
		int length = Integer.parseInt(attributes.getValue(LENGTH));
		int playsCompleted = Integer.parseInt(attributes.getValue(PLAYS));
		return new PlayedVideo(company, title, playsCompleted, length);
	}

}
