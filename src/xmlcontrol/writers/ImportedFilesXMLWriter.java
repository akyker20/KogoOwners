package xmlcontrol.writers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import video.LoadedVideo;
import xmlcontrol.XMLController;

public class ImportedFilesXMLWriter extends XMLWriter {

	private Document myDocument;
	private Map<LocalDate, Node> myDateFilesNodeMap;



	public ImportedFilesXMLWriter(Document document, Map<LocalDate, Node> map) throws FileNotFoundException,
			SAXException, IOException, ParserConfigurationException,
			TransformerConfigurationException {
		super();
		myDateFilesNodeMap = map;
		myDocument = document;
	}
	
	public void writeNewImportedFiles(List<File> files) throws TransformerException{
		for(File f:files){
			if(appendFileToDocument(f)){
				super.writeFile(myDocument, new File(XMLController.IMPORTED_FILES_PATH));
			}
		}
	}
	
	
	/**
	 * Returns true if the file is appended to the document. This happens if the file
	 * was not previously in the document.
	 * @param f
	 * @return
	 */
	private boolean appendFileToDocument(File f){
		String filePath = f.getAbsoluteFile().toString();
		Pattern pattern = Pattern.compile(XMLController.DATE_REGEX);
		Matcher matcher = pattern.matcher(filePath);
		if(matcher.find()){
			LocalDate dateOfFile = LocalDate.of(Integer.parseInt(matcher.group(XMLController.YEAR)), 
					Integer.parseInt(matcher.group(XMLController.MONTH)), Integer.parseInt(matcher.group(XMLController.DAY)));
			Node filesNode = myDateFilesNodeMap.get(dateOfFile);
			if(filesNode == null){
				Element newFilesNode = myDocument.createElement("files");
				newFilesNode.setAttribute("date", dateOfFile.toString());
				Element newFileNode = myDocument.createElement("file");
				newFileNode.setAttribute("name", filePath.substring(filePath.indexOf("kogo_")));
				newFilesNode.appendChild(newFileNode);
				myDocument.getDocumentElement().appendChild(newFilesNode);
				myDateFilesNodeMap.put(dateOfFile, newFilesNode);
			}
			else{
				NodeList fileNodesOnDate = filesNode.getChildNodes();
				for(int i = 0; i < fileNodesOnDate.getLength(); i++){
					Node n = fileNodesOnDate.item(i);
					if(n instanceof Element && n.getNodeName().equals("file")){
						if(filePath.substring(filePath.indexOf("kogo_")).equals(((Element) n).getAttribute("name"))){
							System.out.println(filePath.substring(filePath.indexOf("kogo_")) + " already exists");
							return false;
						}
					}
				}
				Element newFileNode = myDocument.createElement("file");
				newFileNode.setAttribute("name", filePath.substring(filePath.indexOf("kogo_")));
				filesNode.appendChild(newFileNode);
				
			}
		}
		return true;
	}
	

	@Override
	protected Element createVideoNode(LoadedVideo video, Document document) {
		return null;
	}

}
