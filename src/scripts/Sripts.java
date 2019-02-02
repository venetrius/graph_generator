package scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.DoubleUnaryOperator;

import utility.Constatns;
import utility.FilesAndDirectories;

public class Sripts {
	
	public static String newline = System.getProperty("line.separator");
	
	public static void main(String[] args) {
		String generalInputdir = "scriptInput\\input\\";
		File fileRaw = new File("C:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\script\\rawraw.inp");
		String outputPathRaw = "C:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\script\\raw.inp";
		File fileMech = new File("C:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\script\\rawmech.inp");
		String outputPathMech = "C:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\script\\mech.inp";
		//simpleStringManipulation();
		//findRes();
		formatToOutgen(fileRaw,outputPathRaw);
		formatToOutgen(fileMech,outputPathMech);
		//deleteCTrailers();
		//onePercentofMax("scriptInput\\input\\");
		//Scanner in;
		// stroreNumbers();
		//DoubleUnaryOperator operator = (double val) -> {return Math.log(val);};
		//modFileInDir(generalInputdir, operator);
	}
	
	static void onePercentofMax(String dirPath) {
		List<String> nameList = utility.FilesAndDirectories.getFileNamesFromDirectory(dirPath);
		
		for(String fileName : nameList) {
			Double max = 0d;
			int emptyCount = 0;
			List<String> fileText = FilesAndDirectories.readLinesFromFile(dirPath+fileName);
			for(String line : fileText) {
				String[] words = line.split(" ");
				if(words.length > 1) {
					max = Math.max(max, Double.parseDouble(words[1]));
				}else {
					emptyCount++;
				}
			}
			for(int i = 0; i < fileText.size()-emptyCount;i++) {
				System.out.println(fileName + " " + (max / 100));
			}
			
		}
	}
	
	static void storeNumbers() {
		boolean end = false;
		try(Scanner in = new Scanner(System.in)){
			ArrayList<Integer> list = new ArrayList<>();
			String s;
			while(!end) {
				if((s = in.nextLine()) != null) {
					int i = Integer.parseInt(s);
					if(i < 0) {
						System.out.println("end :");
						for(int j : list) {
							System.out.println(j);
						}
					}
					else {
						boolean contains = list.contains(i);
						if(contains) {
							System.out.println("Old");
						}
						else {
							System.out.println("new");
							list.add(i);
						}
					}
					
				}
			}
		}
	}
	
	static void deleteCTrailers() {
		Scanner in = new Scanner(System.in);
		String line;
		while ((line = in.nextLine()) != null) {
			if(!line.contains("C"))
				System.out.println(line);
		}
	}
	
	public static void simpleStringManipulation() {
		Scanner in = new Scanner(System.in);
		String fileName;
		while( (fileName = in.next()) != null ) {
			//System.out.println(fileName.substring(0, 9) + "     ");
			System.out.println(fileName.substring(14));
		}
		in.close();
	}
	
	// the 5 entry is sim output in IDT sim files
	private static void findRes() {
		try (Scanner in = new Scanner(System.in)){
		    String text = null;
			System.out.println("");
		    while ((text = in.nextLine()) != null) {
		    	text = removeSapeWhiteSpaces(text);
		    	String[] splitted = text.split(" ");
		    	System.out.println(splitted[3]);
		    //	System.out.println(splitted[0] + " " + splitted[1] + " " + splitted[2] + " " +splitted[3] + " " + splitted[4] + " " + splitted[4] + " ");
		    }
		}finally {
			System.out.println("Error occured during process");
		}
	}
	
	public static String removeSapeWhiteSpaces(String text) {
		int len = text.length();
    	int lastLen = text.length();
    	do {
    		//System.out.println(text);
    		text = text.replaceAll("\\s+", " ");
    		text.replaceAll("  ", " ");
    		lastLen = len;
    		len = text.length();
    	}
    	while(len != lastLen); 
		//System.out.println(text);
		return text;
		
	}
	
	public static void writeToFile(String outputPath, String text) {
		try (PrintWriter out = new PrintWriter(outputPath)) {
			out.println(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// read a text file and change it to all columns be exactly 10 character wide
	public static void formatToOutgen(File file, String outputPath) {
		
		StringBuilder sB = new StringBuilder();
		String emptyS = " ";
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String text = null;
			while ((text = reader.readLine()) != null) {
				text = text.replaceAll("\\s+", " ");
				String[] splitted = text.split(" ");
				for (int i = 0 ; i<splitted.length; i++ ) {
					String s = splitted[i];
					sB.append(s);
					int max = i > 0 ? 10 : 20;
					for (int j = s.length(); j < max; j++) {
						sB.append(emptyS);
					}
				}
				sB.append(newline);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writeToFile(outputPath, sB.toString());
	}
	
	public static void modFileInDir(String dirPath, Double scale) {
		List<String> fileNames = FilesAndDirectories.getFileNamesFromDirectory(dirPath);
		for(String fileName : fileNames) {
			FilesAndDirectories.readEndModifyFile(dirPath  + fileName, scale);
		}
	}
	
	public static void modFileInDir(String dirPath, DoubleUnaryOperator operator) {
		List<String> fileNames = FilesAndDirectories.getFileNamesFromDirectory(dirPath);
		for(String fileName : fileNames) {
			FilesAndDirectories.readEndModifyFile(dirPath  + fileName, operator);
		}
	}
}
