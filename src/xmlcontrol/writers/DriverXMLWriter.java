package xmlcontrol.writers;

import gui.GUIController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javafx.collections.ObservableList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import video.LoadedVideo;

public class DriverXMLWriter extends XMLWriter {

	private DocumentBuilder myBuilder;

	public DriverXMLWriter() throws FileNotFoundException, SAXException, IOException, 
	ParserConfigurationException, TransformerConfigurationException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		myBuilder = factory.newDocumentBuilder();
	}


	/**
	 * Generates the xml file for the drivers.
	 * @throws TransformerException 
	 * @throws IOException 
	 */
	public void buildDriverFile(ObservableList<LoadedVideo> videoList, String fileName) throws TransformerException, IOException {
		Document document = buildDriverDocument(videoList);
		new File("./driver/deliverable/").mkdir();
		
		File driverXMLFile = new File("./driver/deliverable/" + fileName);
		super.writeFile(document, driverXMLFile);
		
		File videoSource = new File("./videos/");
		File target = new File("./driver/deliverable/videos");
		copyDirectory(videoSource, target);
	}
	
	  // If targetLocation does not exist, it will be created.
    public void copyDirectory(File sourceLocation , File targetLocation)
    throws IOException {
        
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }
            
            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {
            
            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);
            
            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }


	/**
	 * Sets up the elements required by the Kogo Driver XML Parser.
	 * The status element is given an attribute initialized which is
	 * set to false. The method uses the createVideoNodeList method to
	 * populate its videos node.
	 * @param videoList
	 * @return
	 */
	private Document buildDriverDocument(ObservableList<LoadedVideo> videoList){
		Document document = myBuilder.newDocument();
		Element driverTag = document.createElement("driver");
		Element statusTag = document.createElement("status");
		statusTag.setAttribute("initialized", "false");
		driverTag.appendChild(statusTag);
		Element videosTag = document.createElement("videos");
		for(LoadedVideo video:videoList){
			videosTag.appendChild(createVideoNode(video, document));
		}
		driverTag.appendChild(videosTag);
		document.appendChild(driverTag);
		return document;
	}


	@Override
	protected Element createVideoNode(LoadedVideo video, Document document) {
		Element videoNode = document.createElement("video");
		videoNode.setAttributeNode(makeNode(document, "title", video.getMyName()));
		videoNode.setAttributeNode(makeNode(document, "company", video.getMyCompany()));
		videoNode.setAttributeNode(makeNode(document, "length", ""+video.getMyLength()));
		videoNode.setAttributeNode(makeNode(document, "maxPlays", ""+ video.getMyPlaysRemaining()/GUIController.NUM_DRIVERS));
		return videoNode;
	}
}