package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
	private Document myDocument;
	
	public XMLWriter() throws FileNotFoundException, SAXException, IOException, ParserConfigurationException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        myDocument = builder.parse(getClass().getResourceAsStream("/xml/videos.xml"));
	}
	
	public void writeToFile(Video video) throws TransformerException{
		Element videoNode = myDocument.createElement("video");
		videoNode.setAttributeNode(makeNode("title", video.getMyName()));
		videoNode.setAttributeNode(makeNode("company", video.getMyCompany()));
		videoNode.setAttributeNode(makeNode("plays", ""+ video.getMyPlaysRemaining()));
		videoNode.setAttributeNode(makeNode("length", ""+video.getMyLength()));
		myDocument.getDocumentElement().appendChild(videoNode);		
		overwriteXMLFile();
	}

	private Attr makeNode(String name, String value) {
		Attr attr = myDocument.createAttribute(name);
		attr.setValue(value);
		return attr;
	}
	 /**
     * Saves a new XML file with updated document nodes.
     * 
     * @throws TransformerException
     */
    private void overwriteXMLFile () throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(new File("src"+File.separator+"xml"+File.separator+"videos.xml"));
        transformer.transform(new DOMSource(myDocument), result);
        System.out.println("File saved!");
    }
    
    

}
