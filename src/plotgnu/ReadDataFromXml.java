package plotgnu;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ReadDataFromXml {
	
	private List<String> dataList = new ArrayList<String>();
	private Document doc;
	private final String targetProperty;
	
	public static void main(String[] args) {
		ReadDataFromXml r = new ReadDataFromXml("c:\\Users\\Vene\\Documents\\sript\\scriptInput\\flowReactor\\simpleXml\\x30011185.xml", "temperature");
	}
	
	public ReadDataFromXml(String path, String targetProperty) {
		doc = utility.FilesAndDirectories.openXml(path);
		this.targetProperty = targetProperty;
		m();
	}
	
	private void m(){
		Node root = doc.getDocumentElement();
		Node dataGroup = getNamedChild(root, "dataGroup");
		
		List<Node> properties = getNamedChildren(dataGroup, "property");
		String propertyIndex = "-1";
		for (Node property : properties) {
			NamedNodeMap map = property.getAttributes();
			if (map.getNamedItem("name").getTextContent().equals(targetProperty)) {
				propertyIndex =  map.getNamedItem("id").getTextContent();
			}
		}
		
		List<Node> dataPoints = getNamedChildren(dataGroup, "dataPoint");
		
		for ( int i = 0; i < dataPoints.size(); i++) {
			Node n = dataPoints.get(i);
			dataList.add(getNamedChild(n, propertyIndex).getTextContent());
		}
	}
	
	private static Node getNamedChild(final Node parent, final String name) {
		Node child = null;
		NodeList items = parent.getChildNodes();
		for (int i = 0; i < items.getLength(); i++) {
			if (items.item(i).getNodeName().equals(name)) {
				return items.item(i);
			}
		}
		return child;
	}
	
	private static List<Node> getNamedChildren(final Node parent, final String name) {
		ArrayList<Node> children = new ArrayList<>();
		NodeList items = parent.getChildNodes();
		for (int i = 0; i < items.getLength(); i++) {
			if (items.item(i).getNodeName().equals(name)) {
				children.add(items.item(i));
			}
		}
		return children;
	}

	public List<String> getDataList() {
		return dataList;
	}


	
}
