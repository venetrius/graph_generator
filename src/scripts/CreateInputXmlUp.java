package scripts;

import java.util.Scanner;

public class CreateInputXmlUp {
	
	final static String input1 = "input/xml/up/";
	final static String output1 = "outputs/XmlToTxtUpgrade/";
	final static String input2 = "input/xml/reXml/";
	final static String output2 = "outputs/TxtUpgradeToXml/";
	
	final static String XML =".xml";
	final static String TXT =".txt";
	
	
	public static void main(String[] args) {
		System.out.println("Hello");
		Scanner in = new Scanner(System.in);
		String toTxt = "";
		String backToXml ="";
		String fileName;
		while(!(fileName = in.next()).equals("END") ) {
			fileName = fileName.replaceAll(".xml", "");
			toTxt = toTxt + "\n" + input1 + fileName + XML + " " + output1 + fileName + TXT;
			backToXml = backToXml + "\n" + input2 + fileName + TXT + " " + output2 + fileName + XML;
		}
		System.out.println(toTxt);
		System.out.println("");
		System.out.println(backToXml);
	}
}
