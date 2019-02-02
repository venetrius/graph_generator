package plotgnu;

import java.util.ArrayList;
import java.util.Collections;

public class PlotFile {
	
	public static final String X_TITLE = "Xaxis";
	public static final String Y_TITLE = "Yaxis";
	private static StringBuilder batSb = new StringBuilder(
			"@rem need to change cmd font https://superuser.com/questions/237081/whats-the-code-page-of-utf-8#272184" + utility.Constatns.newLine +
			"chcp 65001" + utility.Constatns.newLine);
	private final String batTemplate;
	private final String name;
	private final String outputDir;
	private ArrayList<String> entris = new ArrayList<>();
	

	public PlotFile(String name, String outputDir, String template, boolean sort) {
		this.name = name;
		this.outputDir = outputDir;
		this.batTemplate = template;
	}

	protected void appendDataLine(String[] aS) {
		String s = "";
		for (int i = 1; i < aS.length; i++) {
			s += aS[i] + utility.Constatns.stringSeparator;
		}
		entris.add(s);
	}

	public void print(boolean sort) {
		if(sort) {
			Collections.sort(entris);
		}
		StringBuilder sb = new StringBuilder();
		for (String entry : entris) {
			sb.append(entry);
			sb.append(utility.Constatns.newLine);
		}
		String text = sb.toString();
		//System.out.println(text);
		// String path = outputDir + utility.Constatns.FILE_SEPARATOR + this.name;
		String path = outputDir + this.name;
		utility.FilesAndDirectories.writeToFile(path, text);
	}

	public void finalizeData(boolean sort) {
		this.print(sort);
		batSb.append(batTemplate.replaceAll("fileName", this.name));
		batSb.append(utility.Constatns.newLine);
		batSb.append(utility.Constatns.newLine);
	}
	
	public static void createBatFile(String filePath) {
		System.out.println("creating data.bat : " + filePath);
		//utility.FilesAndDirectories.writeToFile(filePath, batSb.toString());
		utility.FilesAndDirectories.writeTo_UTF8_File(filePath, batSb.toString());
	}

	public String getName() {
		return this.name;
	}
}
