package scripts;

import java.io.*;
import java.util.*;
import utility.*;
import utility.fileSamples.IDT_1_4FileSample;
/*
 * find line like : ** ignition delay time is 3.32455 ms
 * and return ignition delay time extracted from this line
 */
public class IDTFromParseInit {
	
	
	
	
	public static void main(String[] args) {
		process();
	}
	
	public static void process() {
		IDTFromParseInit parse = new IDTFromParseInit();
		String dirPath = "scriptInput\\0d\\FMLogOut1_4\\Simple\\PolimiOld\\";
		String outputPath = "sriptOut\\0d\\1_4Simple\\PolimiOld";
		List<String> fileNames  = utility.FilesAndDirectories.getFileNamesFromDirectory(dirPath);
		Collections.sort(fileNames);
		String outPut = parse.createOutput(dirPath, fileNames);
		FilesAndDirectories.writeToFile(outputPath, outPut);
	}
	
	
	public static void test() {
		IDTFromParseInit idtPars = new IDTFromParseInit();
		String path = "c:\\Users\\Vene\\Documents\\sript\\scriptInput\\0d\\FMLogOut1_4\\Simple\\Abian\\FM_log_x10010004_p10_mechTest_init_parset";
		Double idt = idtPars.findIgnitionDelay(path);
		if(!idt.equals(3.32455d)) System.out.println("required 3.32455 got" + idt );
	}

	
	public String getIntiFileName(String s) {
		String[] fileNameSample = {"FM_log_", null, "_p", null, "_mechTest_init_parset"};
		String[] input = s.split(" ");
		if(input.length < 2) {
			System.out.println("error with input : " + s);
		}
		fileNameSample[1] = input[0];
		fileNameSample[3] = input[1];
		return  fileNameSample[0] + fileNameSample[1] + fileNameSample[2] + fileNameSample[3] + fileNameSample[4];
	}
	
	public String createOutput(String dirPath, Iterable<String> filePaths) {
		CustomStringBuilder sb = new CustomStringBuilder();
		ArrayList<String> sample = IDT_1_4FileSample.IDT_1_4_SAMPLE;
		for(String line : sample) {
			Double d = this.findIgnitionDelay(dirPath + utility.Constatns.FILE_SEPARATOR + getIntiFileName(line));
			if(d == null || d <= 0d) {
				System.out.println("WARNING for file: " + getIntiFileName(line) + " could not find IDT!");
			}
			sb.appendWithStringSep(line);
			sb.appendWithLineSep(d);
		}
		return sb.toString();
	}
	
	public double findIgnitionDelay(String filePath) {
		final String KEYWORD = "delay";
		File file = new File(FilesAndDirectories.rootDirectory + filePath);
		BufferedReader reader = null;
		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;
		    String[] line;
		    while ((text = reader.readLine()) != null) {
		    	line = text.split(" ");
		    	if(line.length > 2 && line[2].equals(KEYWORD)) {
		    		return Double.parseDouble(line[5]);
		    	}
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    }
		}
		return -1d;
	}

}
