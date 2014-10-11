package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.ObservableList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLWriter {
	
	private DocumentBuilder myBuilder;
	
	public XMLWriter() throws FileNotFoundException, SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        myBuilder = factory.newDocumentBuilder();
	}
	
	public void writeToFile(ObservableList<Video> videoList) throws TransformerException{
        Document document = myBuilder.newDocument();
        document.appendChild(document.createElement("videos"));
		for(Video video:videoList){
			Element videoNode = document.createElement("video");
			videoNode.setAttributeNode(makeNode(document, "title", video.getMyName()));
			videoNode.setAttributeNode(makeNode(document, "company", video.getMyCompany()));
			videoNode.setAttributeNode(makeNode(document, "plays", ""+ video.getMyPlaysRemaining()));
			videoNode.setAttributeNode(makeNode(document, "length", ""+video.getMyLength()));
			document.getDocumentElement().appendChild(videoNode);	
		}
		writeFile(document);
	}

	private Attr makeNode(Document document, String name, String value) {
		Attr attr = document.createAttribute(name);
		attr.setValue(value);
		return attr;
	}
	 /**
     * Saves a new XML file with updated document nodes.
     * 
     * @throws TransformerException
     */
    private void writeFile (Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(new File("src"+File.separator+"xml"+File.separator+"videos.xml"));
        transformer.transform(new DOMSource(document), result);
        System.out.println("File saved!");
    }
    
    

}
