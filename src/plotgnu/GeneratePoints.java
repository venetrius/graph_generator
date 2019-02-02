package plotgnu;

import java.util.List;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class GeneratePoints {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		StringBuilder xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append("<?xml version=\"1.0\"?> <class> </class>");
		ByteArrayInputStream input = new ByteArrayInputStream(
		   xmlStringBuilder.toString().getBytes("UTF-8"));
		Document doc = builder.parse(input);
	}
	
	/* public static void processTxt(String path) {
		List<String> lineList = utility.FilesAndDirectories.readLinesFromFile(path); }*/
	
	public static double[][] calculatePoint(double[][] matrix, int orderOfGrowth) {
		int width = matrix[0].length;
		int length = matrix.length;
		int newWitdth = width; // old wrong(width - 1) * (orderOfGrowth + 1) + 1;
		int newLength = (length -1) * (orderOfGrowth);
		double[][] newMartrix = new double[(newLength)][newWitdth];
		for(int i = 0; i < length -1; i++) {
			for(int j = 0; j < width; j++) {
				double[] newPoints = interpolate(matrix[i][j], matrix[i+1][j], orderOfGrowth);
				for(int k=0; k < orderOfGrowth; k++) {
					newMartrix[i * orderOfGrowth + k][j] = newPoints[k];
				}
			}
		}
		return newMartrix;
	}
	
	public static double[] interpolate(double p1, double p2, int numberOfPoints) {
		double[] interpolatedValues = new double[numberOfPoints];
		double steppingValue = (p2 - p1) / (numberOfPoints + 1);
		for(int i = 0; i < numberOfPoints; i++) {
			interpolatedValues[i] = p1 + (i + 1) * steppingValue;
		}
		return interpolatedValues;
	}
	
}
