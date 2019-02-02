package plotgnu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import utility.Constatns;
import utility.CustomStringBuilder;
import utility.FilesAndDirectories;

public class PlotFromFMLogs {
	final String rawDataDir;
	
	public static void main(String[] args) {
		PlotFromFMLogs pff = new PlotFromFMLogs("scriptInput\\0d\\FMLogOut1_4\\Ext\\");
		pff.process();
	}
	
	public PlotFromFMLogs(String rawDataDir) {
		this.rawDataDir = rawDataDir;
	}
	
	public void process() {
		StringBuilder sb = null;
		List<String> dirNames = FilesAndDirectories.getDirectoryNamesFromDirectory(rawDataDir);
		List<String> expPointFileNames = utility.FilesAndDirectories.getFileNamesFromDirectory(rawDataDir + Constatns.FILE_SEPARATOR + dirNames.get(0));  
		for (String dirName : dirNames) {
			HashMap<String, HashMap<Integer, Double>> superMap = new HashMap<>();
			String path = rawDataDir + Constatns.FILE_SEPARATOR + dirName;
			String mechanism = dirName;
			System.out.println("Mech: " + mechanism);
			for (String expPointFileName : expPointFileNames) {
				String expName = getExpName(expPointFileName);
				String expPointCount = getExpPointCount(expPointFileName);
				if (!superMap.containsKey(expName) ) {
					superMap.put(expName, new HashMap<>());
				}
				HashMap<Integer, Double> expPointMap = superMap.get(expName);
				List<String> lines = FilesAndDirectories.readLinesFromFile(path + Constatns.FILE_SEPARATOR + expPointFileName);
				String iDT = getIdt(lines);
				expPointMap.put(Integer.parseInt(expPointCount), Double.parseDouble(iDT)/1000);
			}
			CustomStringBuilder cSb = new CustomStringBuilder();
			ArrayList<String> expList = new ArrayList<>(superMap.keySet());
			Collections.sort(expList);
			for (String experiment : expList) {
				HashMap<Integer, Double> expPointMap = superMap.get(experiment);
				for (Integer point : expPointMap.keySet()) {
					cSb.appendWithStringSep("experiments/0d/extended_" +experiment);
					cSb.appendWithStringSep(point.toString());
					cSb.appendWithStringSep("dummy");
					cSb.appendWithStringSep("dummy");
					cSb.appendWithStringSep(expPointMap.get(point).toString());
					cSb.newLine();
				}
			}
			FilesAndDirectories.writeToFile("\\sriptOut\\0d\\1_4Ext\\" + mechanism, cSb.toString());
		}
	}

	private String getExpPointCount(String expPointFileName) {
		char[] chars = expPointFileName.toCharArray();
		if (Character.isDigit(chars[28])) {
			return expPointFileName.substring(27, 29);
		}
		// TODO Auto-generated method stub
		return expPointFileName.substring(27, 28);
	}

	private String getExpName(String expPointFileName) {
		return expPointFileName.substring(16, 26);
	}

	private String getIdt(List<String> fMLog) {
		for (String line : fMLog) {
			if( line.startsWith("** ignition delay time")) {
				String[] splitted = line.split(" ");
				return splitted[5];
			}
		}
		return null;
	}
}
