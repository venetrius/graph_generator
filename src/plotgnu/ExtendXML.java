package plotgnu;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import utility.FilesAndDirectories;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ExtendXML {
	private static final int orderOfGrowth = 10;

	public static void main(String[] args) {
		/* 0d
		String inputDir = "scriptInput\\0d\\";
		String outputDir ="sriptOut\\extended_0d\\";
		*/
		//processXml(newPath, path);
		String inputDir = "scriptInput\\FS\\";
		String outputDir ="sriptOut\\extended_FS\\";
		
		processAllXmlInDir(inputDir, outputDir);
	}
	
	public static void processAllXmlInDir(String inDir, String outDit) {
		List<String> fileNames = utility.FilesAndDirectories.getFileNamesFromDirectory(inDir);
		for (String fileName : fileNames) {
			processXml(inDir+fileName, outDit + "extended_" + fileName);
		}
	}

	private static void processXml(String path, String newPath) {
		//TODO
		System.out.println("XML path " +path);
		Document doc = FilesAndDirectories.openXml(path);

		// read doc, find experiments
		Node root = doc.getDocumentElement();
		Node dataGroup = getNamedChild(root, "dataGroup");
		List<Node> dataPoints = getNamedChildren(dataGroup, "dataPoint");
		//utility.PrintInfo.printIterable(dataPoints); // TODO test line

		// read all values from dataPoints in experiment
		int height = dataPoints.size();
		int width = dataPoints.get(0).getChildNodes().getLength();
		double[][] matrix = new double[height][width];
		for (int i = 0; i < dataPoints.size(); i++) {
			Node nextData = dataPoints.get(i);
			NodeList values = nextData.getChildNodes();
			for (int j = 0; j < values.getLength(); j++) {
				System.out.println("text content:" + values.item(j).getTextContent()); // TODO test line
				matrix[i][j] = Double.parseDouble(values.item(j).getTextContent());
			}
		}
		// generate a matrix that contains ONLY the interpolated values
		double[][] newMatrix = GeneratePoints.calculatePoint(matrix, orderOfGrowth);
		// 
		for (int i = 0; i < dataPoints.size() - 1; i++) {
			Node node = dataPoints.get(i);
			for (int j = 0; j < orderOfGrowth; j++) {
				Node newNode = node.cloneNode(true);
				doc.adoptNode(newNode);
				setValues(newNode, newMatrix[i * orderOfGrowth + j]);
				dataGroup.appendChild(newNode);
			}
		}
	/*	for (Node n : dataPoints) {
			Node newNode = n.cloneNode(true);
			doc.adoptNode(newNode);
			dataGroup.appendChild(newNode);
		} */
		printXML(doc, newPath);
	}

	private static void setValues(Node node, double[] ds) {
		NumberFormat formatter = new DecimalFormat("#0.000000");
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			nodeList.item(i).setTextContent(formatter.format(ds[i]).replace(',', '.')); // TODO not nice not efficient , -> .
		}

	}

	public static void printXML(Document doc, String path) {
		String xmlString = "Errod during process";
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			// initialize StreamResult with File object to save to file
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);

			xmlString = result.getWriter().toString();
		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessage());

		} catch (TransformerException e) {
			System.out.println(e.getMessage());
		}
		// System.out.println(xmlString);

		utility.FilesAndDirectories.writeToFile(path, xmlString);
	}

	public static Node getNamedChild(final Node parent, final String name) {
		Node child = null;
		NodeList items = parent.getChildNodes();
		for (int i = 0; i < items.getLength(); i++) {
			if (items.item(i).getNodeName().equals(name)) {
				return items.item(i);
			}
		}
		return child;
	}

	public static List<Node> getNamedChildren(final Node parent, final String name) {
		List<Node> children = new ArrayList<>();
		NodeList items = parent.getChildNodes();
		for (int i = 0; i < items.getLength(); i++) {
			if (items.item(i).getNodeName().equals(name)) {
				children.add(items.item(i));
			}
		}
		return children;
	}
}