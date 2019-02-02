package scripts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import utility.FilesAndDirectories;

/**
 * Az outgen format még a scripst osztályban van
 * @author Vene
 *
 */
public class OutgenStuff {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String path = "C:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\script\\mechtestResult.txt";
		processMechTestResult(path);
		/*ArrayList<String> list = FilesAndDirectories.readLinesFromFile(path);
		Collections.sort(list);
		for(String line : list) {
			System.out.println(line);
		} */
	}
	
	
	
	// in case the experiment output contains concentration profile for more species
	public static void processMechTestResult(String path) throws FileNotFoundException, IOException {
		//System.out.println("Strart");
		ArrayList<String> lines = FilesAndDirectories.readLinesFromFile(path);
		ArrayList<String> processed = new ArrayList<>();
		for(String line : lines) {
			String[] segments = line.split(" ");
				String newLine = segments[0].substring(24, 33) + "_" + segments[2] + " " + segments[1] + " " + segments[3] + " " +  segments[4];
				processed.add(newLine);
		}
		Collections.sort(processed);
		for(String line : processed) {
			System.out.println(line);
		}
	}
}
