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
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import video.PlayedVideo;
import video.LoadedVideo;
import xmlcontrol.MasterXMLParser;
import xmlcontrol.XMLController;

/**
 * The purpose of this class is to provide a means for generating driver
 * xml files, and also be able to edit, remove, and add advertisements to
 * the master file.
 * @author Austin Kyker
 *
 */
public abstract class XMLWriter {

	private Transformer myTransformer;

	public XMLWriter() throws FileNotFoundException, SAXException, IOException, 
	ParserConfigurationException, TransformerConfigurationException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		myTransformer = transformerFactory.newTransformer();
		myTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
	}


	/**
	 * Creates a video node list that is different depending on whether this
	 * method is called in the process of generating a driver xml file or 
	 * simply editing the master file.
	 * @param document
	 * @param videoList
	 * @param videosTag
	 * @param forDriver
	 */
	protected abstract Element createVideoNode(LoadedVideo video, Document document);
		
	/**
	 * A helper method to create a new attribute with a name and value.
	 * @param document
	 * @param name
	 * @param value
	 * @return
	 */
	protected Attr makeNode(Document document, String name, String value) {
		Attr attr = document.createAttribute(name);
		attr.setValue(value);
		return attr;
	}

	/**
	 * Writes the contents of the document to the XML file specified.
	 * @param document
	 * @param xmlFile
	 * @throws TransformerException
	 */
	protected void writeFile(Document document, File xmlFile) throws TransformerException {
		xmlFile.setWritable(true);
		StreamResult result = new StreamResult(xmlFile);
		myTransformer.transform(new DOMSource(document), result);
		xmlFile.setReadOnly();
		System.out.println("File saved!");
	}
}