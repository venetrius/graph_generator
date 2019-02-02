package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class FilesAndDirectories {
	public static final String TXT = ".txt";
	//public static final String rootDirectory = "d:\\gergoFiles\\2018\\sript\\";
	public static final String rootDirectory = System.getProperty("user.dir") + "\\script\\";
	
	
	
	public static ArrayList<String> readLinesFromUTF8File(String path){
		ArrayList<String> lineList = new ArrayList<>();
		try {
			File fileDir = new File(rootDirectory + path);
				
			BufferedReader in = new BufferedReader(
			   new InputStreamReader(
	                      new FileInputStream(fileDir), "UTF8"));
			        
			String text;
			      
			while ((text = in.readLine()) != null) {
				text = text.replaceAll("\\s+", " ").trim();
				lineList.add(text);
			}
			        
	                in.close();
		    } 
		    catch (UnsupportedEncodingException e) 
		    {
				System.out.println(e.getMessage());
		    } 
		    catch (IOException e) 
		    {
		    	System.out.println("IO exception is: ");
				System.out.println(e.getMessage());
		    }
		    catch (Exception e)
		    {
				System.out.println(e.getMessage());
		    }
		return lineList;
	}

	
	public static void readEndModifyFile(String path, Double scale){
		CustomStringBuilder cs = new CustomStringBuilder();
		File file = new File(rootDirectory +path);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String text = " ";
			while ((text = reader.readLine()) != null) {
				text = text.replaceAll("\\s+", " ").trim();
				String[] line = text.split(" ");
				for(int i = 0; i < line.length; i++) {
					if(i == 0 || "?".equals(line[i])) {
						cs.appendWithStringSep(line[i]);
					}else {
						Double val = Double.parseDouble(line[i])  * scale;
						cs.appendWithStringSep(val.toString());
					}
				}
				cs.newLine();
			}
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage() + rootDirectory + path);
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		writeToFile(path, cs.toString());
	}
	
	public static void readEndModifyFile(String path, DoubleUnaryOperator operator){
		CustomStringBuilder cs = new CustomStringBuilder();
		File file = new File(rootDirectory +path);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String text = " ";
			while ((text = reader.readLine()) != null) {
				text = text.replaceAll("\\s+", " ").trim();
				String[] line = text.split(" ");
				for(int i = 0; i < line.length; i++) {
					if(i == 0 || "?".equals(line[i])) {
						cs.appendWithStringSep(line[i]);
					}else {
						Double val = operator.applyAsDouble(Double.parseDouble(line[i]));
						cs.appendWithStringSep(val.toString());
					}
				}
				cs.newLine();
			}
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage() + rootDirectory + path);
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		writeToFile(path, cs.toString());
	}
	
	
	public static ArrayList<String> readLinesFromFile(String path){
		System.out.println("rooot dir: " + rootDirectory);
		return readLinesFromFileFullPath(rootDirectory +  path);
	}
	
	public static ArrayList<String> readLinesFromFileFullPath(String path){
		ArrayList<String> lineList = new ArrayList<>();
		File file = new File(path);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String text = " ";
			while ((text = reader.readLine()) != null) {
				text = text.replaceAll("\\s+", " ").trim();
				lineList.add(text);
			}
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage() + path);
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return lineList;
	}
	
	public static void copyFileUsingStreamByPath(String source, String dest) throws IOException{
		File sourceFile = new File(source);
		File destFile = new File(dest);
		if(sourceFile == null || destFile == null) {
			throw new IllegalArgumentException("File could not be opened");
		}
		copyFileUsingStream(sourceFile, destFile);
	}
	
	public static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    
	    }catch (Exception e) {
			System.out.println(e.getMessage());
		
	    } finally {
	        is.close();
	        os.close();
	    }
	}
	
	public static void writeToFile(String outputPath, String text) {
		try (PrintWriter out = new PrintWriter(rootDirectory + outputPath)) {
			out.println(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeListToFile(List<String> list, String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			sb.append(Constatns.newLine);
			sb.append(list.get(i));
		}
		writeToFile(fileName, sb.toString());
	}
	
	public static void writeTo_UTF8_File(String outputPath, String text) {
		System.out.println("hello there -1");
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rootDirectory + outputPath), "UTF-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
			try {
			    out.write(text);
			}catch (UnsupportedEncodingException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
			    try {
					out.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
			System.out.println("done " + rootDirectory + outputPath);
	}	
	
	
	public static List<String> getDirectoryNamesFromDirectory(String dirPath){
		System.out.println("hello there");
		ArrayList<String> fileNameList = new ArrayList<>();
		File folder = new File(rootDirectory + dirPath);
		System.out.println("hello there " + rootDirectory + dirPath);
		File[] listOfFiles = folder.listFiles();
		//System.out.println("list of files " + folder.isDirectory());
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isDirectory()) {
		    	  fileNameList.add(listOfFiles[i].getName());
		      } 
		    }
		return fileNameList;
	}

	/* 
	 * return a list of file names found in a given path
	 */
	public static List<String> getFileNamesFromDirectory(String dirPath){
		System.out.println("hello there");
		ArrayList<String> fileNameList = new ArrayList<>();
		File folder = new File(rootDirectory + dirPath);
		System.out.println("hello there " + rootDirectory + dirPath);
		File[] listOfFiles = folder.listFiles();
		//System.out.println("list of files " + folder.isDirectory());
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  fileNameList.add(listOfFiles[i].getName());
		      } 
		    }
		return fileNameList;
	}
	
	public static List<String> getFilePathsFromDirectory(String dirPath){
		System.out.println("hello there2");
		ArrayList<String> fileNameList = new ArrayList<>();
		File folder = new File(rootDirectory + dirPath);
		File[] listOfFiles = folder.listFiles();
		//System.out.println("list of files " + folder.isDirectory());
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  fileNameList.add(listOfFiles[i].getAbsolutePath());
		      } 
		    }
		return fileNameList;
	}
	
	public static Document openXml(String path) {
		Document doc = null;
		try {
			File inputFile = new File(rootDirectory + path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			//System.out.println(rootDirectory + path);
			doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			//e.printStackTrace(); TODO log the errors
			System.out.println("Error during the load of the xml file: " + rootDirectory + path);
		}

		return doc;
	}
	
}


