package scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import utility.Constatns;

import utility.FilesAndDirectories;


public class SplineFitScripts {
	private static String directories = "c:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\script\\scriptInput.txt";
	private static String path_txtFiles = "c:\\Users\\Vene\\Desktop\\Szakdoga\\feldolgozott cikkek\\Flowreactor\\Song_Fuel_191_358_365_2016.pdf\\TXTv2\\";
	private static String targetP= "c:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\simulation\\flowReactor\\minimal_spline_fit\\calculations\\";
	private static int dependentVariableNo = 1;
	private static int independentVariableNo = 0;
	
	
	
	public static void main(String[] args) throws IOException {
		/*String path_splineFit = "c:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\simulation\\flowReactor\\minimal_spline_fit\\calculations\\sample\\minimal_spline_fit.exe";
		String dest = "c:\\Users\\Vene\\Desktop\\masolt.exe";
		FilesAndDirectories.copyFileUsingStreamByPath(path_splineFit, dest); */
		ArrayList<String> directoryNames = FilesAndDirectories.readLinesFromFile(directories);
		String targetPath = targetP;
		createDirectorys(targetPath, directoryNames);
	}
	
	private static void createDirectorys(String targetPath, List<String> directoryNames) throws IOException{
		for(String name : directoryNames) {
			String dst = targetPath + name;
//			 new File(dst).mkdirs();
			 copyExecutables(dst+ "\\");
			 readAndPrintData(path_txtFiles+name+FilesAndDirectories.TXT, dst+"//data.txt");
		}
	}

	private static void copyExecutables(String dest) throws IOException {
		String sourcePath = "c:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\simulation\\flowReactor\\minimal_spline_fit\\calculations\\sample\\";
		String splineFit = "minimal_spline_fit.exe";
		String plot = "plot_absln_inv.bat";
		String run = "run.bat";
		FilesAndDirectories.copyFileUsingStreamByPath(sourcePath+splineFit, dest+splineFit);
		FilesAndDirectories.copyFileUsingStreamByPath(sourcePath+run, dest+run);
		FilesAndDirectories.copyFileUsingStreamByPath(sourcePath+plot, dest+plot);
	}
	
	private static void readAndPrintData(String inputPath, String outputPath) throws FileNotFoundException, IOException {
		ArrayList<String> lineList;
		File file = new File(inputPath);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			boolean preparation = true;
			boolean readPhase = false;
			boolean readPhase2 = false;
			boolean finished = false;
			lineList = new ArrayList<>();
			String text = " ";
			StringBuilder sb = new StringBuilder("");
			
			while (!finished && (text = reader.readLine()) != null ) {
				if(preparation && text.contains("Varied values:")) {
					preparation = false;
					readPhase = true;
				}
				else if(readPhase) {
					if(readPhase2) {
						text = text.replaceAll("\\s+", " ");
						String[] splitted = text.split(" ");
						if(text.contains("Comment:") || splitted.length < 2) {
							finished = true;
						}
						else {			
							sb.append(splitted[independentVariableNo]);
							sb.append(Constatns.stringSeparator);
							sb.append(splitted[dependentVariableNo]);
							sb.append(Constatns.newLine);
						}
					}
					readPhase2 = true;
				}
				
			}
			FilesAndDirectories.writeToFile(outputPath, sb.toString());
		}
	}
	
}
