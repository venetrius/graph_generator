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

public class ModifySolFile {

	/* 
	 * before TotalEnthalpy [J/kg] should be a molefraction-molefraction during the process time so no data is lost*/
	static boolean inCSpecBlokk = false;
	static String output = "";
	static final String MASSFRACTION ="massfraction";
	static final String MOLFRACTION = "molefraction";
	static final String PRODRATE ="ProdRate";
	static final String C ="C";
	static final String NEW_LINE = "\n";

	public static void main(String[] args) {
		File inputFile = new File("C:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\script\\solInput.txt");
		String outputPath = "C:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\script\\solOutput.txt";
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
		    reader = new BufferedReader(new FileReader(inputFile));
		    String text = null;
		    while ((text = reader.readLine()) != null) {
		    	if(text.length() < 13) {
		    		if(!inCSpecBlokk)
		    			sb.append(text + NEW_LINE);
		    	}
		    	else {
		    		if(!text.substring(0, 12).equals(MASSFRACTION) && !text.substring(0, 12).equals(MOLFRACTION)
		    				&& !text.substring(0, 8).equals(PRODRATE)) {
			    		if(!inCSpecBlokk) {
			    			sb.append(text + NEW_LINE);
			    		}
			    	}
		    		else {		    			
		    			if(text.substring(8).contains(C)) {
		    				System.out.println("off " + text);
		    				inCSpecBlokk = true;
		    			}
		    			else {System.out.println(text.substring(8));
		    				System.out.println("off " + text);
		    				inCSpecBlokk = false;
		    				sb.append(text + NEW_LINE);
		    			}
		    		}
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
		try (PrintWriter out = new PrintWriter(outputPath)) {
			out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
