package gui;

import javafx.collections.ObservableList;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser extends DefaultHandler {

	public static final String FILE_PATH = 
			"src"+File.separator+"xml"+File.separator+"videos.xml";

	private ObservableList<Video> myVideoList;
	private SAXParser saxParser;

	public XMLParser(ObservableList<Video> list) 
			throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory docFactory = SAXParserFactory.newInstance();
		saxParser = docFactory.newSAXParser();
		myVideoList = list;
		parseFile(new File(FILE_PATH));
	}

	private void parseFile(File xmlFile) throws SAXException, IOException {
		try {
			saxParser.parse(xmlFile, this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startElement (String uri,
			String localName,
			String elementName,
			Attributes attributes) throws SAXException {
		if (elementName.equalsIgnoreCase("video")) {
			String title = attributes.getValue("title");
			String company = attributes.getValue("company");
			int length = Integer.parseInt(attributes.getValue("length"));
			int numPlaysPurchased = Integer.parseInt(attributes.getValue("playsPurchased"));
			int numPlaysRemaining = Integer.parseInt(attributes.getValue("playsRemaining"));
			myVideoList.add(new Video(company, title, numPlaysPurchased, numPlaysRemaining, length));
		}
	}
}
