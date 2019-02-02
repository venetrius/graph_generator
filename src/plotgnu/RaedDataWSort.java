package plotgnu;

import java.util.ArrayList;
import java.util.Collections;



public class RaedDataWSort {
	
	private static StringBuilder datSB = new StringBuilder();
	
	public static void main(String[] args) {
		String inputFile = "C:\\Users\\Vene\\Documents\\sript\\scriptInput\\0d_Results.txt";
		String outputDir = "c:\\Users\\Vene\\Documents\\sript\\sriptOut\\";
		RaedDataWSort rd = new RaedDataWSort();
		rd.read(inputFile, outputDir);
		//ReadData
	}
	
	public void read(String inputPath, String outputDir) {
		ArrayList<String> inpput = utility.FilesAndDirectories.readLinesFromFile(inputPath);
		InnerDataFile df = null;
		for (String s : inpput) {
			String[] line = s.split(utility.Constatns.stringSeparator);
			if( line.length < 2) {
				if( df != null) {
					df.finalizeData();
				}
				return;
			}
			if( df == null) {
				df = new InnerDataFile(line[0], outputDir);
			}
			else if( !line[0].equals(df.name)) {
				df.finalizeData();
				df = new InnerDataFile(line[0], outputDir);
			}
			df.appendDataLine(line);
			
		}
	}
	
	private class InnerDataFile{
		private static final String batTemplate ="gnuplot.exe -e \"set terminal png\" -e \" set term png size 1600,1200\" -e ^\r\n" + 
				"\"set output 'fileName.png'\" -e \"set xlabel 'Temperature / K' \" -e \"set ylabel 'idt / s'\"  -e \"plot 'fileName' ^\r\n" + 
				"using 1:2 with points ps 2 pt 7 title 'mesured', 'fileName' using 1:3 with lines lw 3 title 'Curran', ^\r\n" + 
				"'fileName' using 1:4 with lines lw 4 title 'Galborg', ^\r\n" + 
				"'fileName' using 1:5 with lines lw 4 title 'Polimi' \"";
		
		private final String name;
		private final String outputDir;
		private ArrayList<String> entris = new ArrayList<>();
		
		private InnerDataFile(String name, String outputDir) {
			this.name = name;
			this.outputDir = outputDir;
		}
		
		private void appendLine(String str) {
			entris.add(str);
		}
		
		private void appendDataLine(String[] aS) {
			String s = "";
			for(int i = 1; i < aS.length; i++) {
				s += aS[i] + utility.Constatns.stringSeparator;
			}
			entris.add(s);
		}
		
		private void print() {
			Collections.sort(entris);
			System.out.println(entris.size());
			StringBuilder sb = new StringBuilder();
			for(String entry : entris) {
				sb.append(entry);
				sb.append(utility.Constatns.newLine);
			}
			String text = sb.toString();
			//String path = outputDir + utility.Constatns.FILE_SEPARATOR + this.name;
			String path = outputDir + this.name;
			utility.FilesAndDirectories.writeToFile(path, text);
		}
		
		private void finalizeData() {
			System.out.println("");
			this.print();
			System.out.println(batTemplate.replaceAll("fileName", this.name));
			

		}
	}
	
}
