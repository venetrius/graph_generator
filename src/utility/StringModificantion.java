package utility;

import java.util.Collections;
import java.util.List;

public class StringModificantion {
	List<String> text;
	
	public StringModificantion(String path) {
		text = utility.FilesAndDirectories.readLinesFromFile(path);
	}
	
	public void sortText(){
		Collections.sort(text);
	}
	
	public void reverseSortText() {
		Collections.sort(text, Collections.reverseOrder());
	}
	
	public String getText(){
		StringBuilder sb = new StringBuilder();
		for(String s : text) {
			sb.append(s);
			sb.append(Constatns.newLine);
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		reversSortFiles();
		/* String path = "c:\\Users\\Vene\\Documents\\sript\\scriptInput\\sort";
		String pathOut = "c:\\Users\\Vene\\Documents\\sript\\scriptInput\\sortOut";
		StringModificantion sm = new StringModificantion(path);
		sm.reverseSortText();
		utility.FilesAndDirectories.writeToFile(pathOut, sm.getText());  */
	}

	private static void reversSortFiles() {
		String dir ="c:\\Users\\Vene\\Documents\\sript\\scriptInput\\0ddone\\";
		List<String> fileNames = utility.FilesAndDirectories.getFileNamesFromDirectory(dir);
		for(String file : fileNames) {
			StringModificantion sm = new StringModificantion(dir + utility.Constatns.FILE_SEPARATOR + file);
			sm.reverseSortText();
			utility.FilesAndDirectories.writeToFile(dir + utility.Constatns.FILE_SEPARATOR + file, sm.getText());
		}
	}
}
