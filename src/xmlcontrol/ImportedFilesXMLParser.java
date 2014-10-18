package xmlcontrol;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * XMLParser that uses the SAX library to parse the master file and
 * create video instances that will later be displayed to the user
 * on the front end in a TableView.
 * @author Austin Kyker
 *
 */
public class ImportedFilesXMLParser {

	public static final String FILES = "files";
	private static final String MONTH = "month";
	private static final String DAY = "day";
	private static final String YEAR = "year";
	private static final String DATE_REGEX = "(?<month>\\d{1,2})_(?<day>\\d{1,2})_(?<year>\\d{4})";

	private Map<LocalDate, Node> myDateFilesNodeMap;
	private Document myDocument;
	

	public ImportedFilesXMLParser(Document document) {
		myDocument = document;
		myDateFilesNodeMap = new HashMap<LocalDate, Node>();
		buildMap();
	}


	/**
	 * This map will allow easy parsing when we want to see if a video has been added a certain day
	 */
	public void buildMap() {
		Element root = myDocument.getDocumentElement();
		NodeList fileNodes = root.getElementsByTagName(FILES);
		for(int i = 0; i < fileNodes.getLength(); i++){
			Element fileNode = (Element) fileNodes.item(i);
			LocalDate date = LocalDate.parse(fileNode.getAttribute("date"));
			myDateFilesNodeMap.put(date, fileNode);
		}
	}
	
	public Map<LocalDate, Node> getMap(){
		return myDateFilesNodeMap;
	}


	public boolean canImport(File f) {
		String filePath = f.getAbsoluteFile().toString();
		Pattern pattern = Pattern.compile(DATE_REGEX);
		Matcher matcher = pattern.matcher(filePath);
		if(matcher.find()){
			LocalDate dateOfFile = LocalDate.of(Integer.parseInt(matcher.group(YEAR)), 
					Integer.parseInt(matcher.group(MONTH)), Integer.parseInt(matcher.group(DAY)));
			Node filesNode = myDateFilesNodeMap.get(dateOfFile);
			if(filesNode == null){
				return true;
			}
			else{
				NodeList fileNodesOnDate = filesNode.getChildNodes();
				for(int i = 0; i < fileNodesOnDate.getLength(); i++){
					Node n = fileNodesOnDate.item(i);
					if(n instanceof Element && n.getNodeName().equals("file")){
						if(filePath.substring(filePath.indexOf("kogo_")).equals(((Element) n).getAttribute("name"))){
							return false;
						}
					}
				}				
			}
		}
		return false;
	}
}


