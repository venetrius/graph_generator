package scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class SortTxt {
	public static void main(String[] args) {
		ArrayList<String> fileNames = readFilenames();
		for(String fileName : fileNames) {
			if(analyzeTxt(fileName))
				System.out.println(fileName);
		}
	}
	
	public static ArrayList<String> readFilenames(){
		ArrayList<String> fileNames =new ArrayList<>();
		File folder = new File("c:\\Users\\Vene\\Desktop\\temp\\flowReactorExperiments");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        fileNames.add(file.getName());
		    }
		}
		return fileNames;
	}

	
	// read a text file and change it to all columns be exactly 10 character wide
		public static boolean analyzeTxt(String fileName) {
			String path = "c:\\Users\\Vene\\Desktop\\temp\\flowReactorExperiments\\";
			File file = new File(path + fileName);
			StringBuilder sB = new StringBuilder();
			String emptyS = " ";
			boolean start = false;
			boolean stop = false;
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String text = null;
				while ((text = reader.readLine()) != null && !stop) {
					if(!start) {
						if(text.contains("Common")) {
							start = true;
						}
					}
					else if(start) {
						if(text.contains("NO") || text.contains("N2O") || text.contains("CH4") || text.contains("CO")) {
							return false;
						}
						else if(text.contains("Varied experimental conditions and measured results:")) {
							return true;
						}
					}
				}
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			String errorMessage = start ? "varied conditions are not found" : "common conditions not found";
			System.out.println("Error in " + fileName + " : " + errorMessage);
			return false;
		}
}
