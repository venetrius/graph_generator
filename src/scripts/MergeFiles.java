package scripts;

import java.util.*;

import utility.Constatns;
import utility.CustomStringBuilder;
import utility.FilesAndDirectories;

public class MergeFiles {
	public static void main(String[] args) {
		String dir = "scriptInput\\merge\\";
		String outDir = "sriptOut\\merged\\merged.txt";
		List<String> fileNames = utility.FilesAndDirectories.getFileNamesFromDirectory(dir);
		CustomStringBuilder sb = new CustomStringBuilder();
		for(String fileName : fileNames) {
			List<String> file = FilesAndDirectories.readLinesFromFile(dir + Constatns.FILE_SEPARATOR + fileName);
			Integer count = 1;
			for(String line : file) {
				if(line.length() > 0) {
					sb.appendWithStringSep(fileName);
					sb.appendWithStringSep((count++).toString());
					sb.appendWithLineSep(line);
				}
			}
		}
		FilesAndDirectories.writeToFile(outDir,sb.toString());
	}
	
	
}
