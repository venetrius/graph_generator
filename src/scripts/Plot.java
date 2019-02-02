package scripts;

import java.util.ArrayList;

public class Plot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void processFile(String path) {
		ArrayList<String> lines = null;
		try {
		lines = utility.FilesAndDirectories.readLinesFromFile(path);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String experiment = "";
		for(String line : lines ) {
			String[] data = line.split(" ");
			if(experiment.equals(data[0])){
				
			}

		}
	}

}
