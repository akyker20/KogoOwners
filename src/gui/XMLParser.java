package gui;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLParser extends DefaultHandler {

	private ObservableList<Video> myVideoList;
	private FileChooser chooser = new FileChooser();
	private SAXParser saxParser;
	private Stage myStage;

	public XMLParser(ObservableList<Video> list, Stage stage) throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory docFactory = SAXParserFactory.newInstance();
		saxParser = docFactory.newSAXParser();
		myVideoList = list;
		myStage = stage;
		configureFileChooser(chooser);
		parseFile();
	}

	private static void configureFileChooser (FileChooser fileChooser) {
		fileChooser.setTitle("Open XML File");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") +
				System.getProperty("file.separator") + "src" +
				System.getProperty("file.separator") + "xml"));
		fileChooser.getExtensionFilters()
		.addAll(
				new FileChooser.ExtensionFilter("All Files", "*.*"),
				new FileChooser.ExtensionFilter("XML", "*.xml"));
	}

	private void parseFile () throws SAXException, IOException {
		File xmlFile = chooser.showOpenDialog(myStage);
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
			int numPlaysRemaining = Integer.parseInt(attributes.getValue("plays"));
			myVideoList.add(new Video(company, title, numPlaysRemaining, length));
		}
	}
}
