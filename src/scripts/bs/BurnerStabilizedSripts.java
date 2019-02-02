package scripts.bs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BurnerStabilizedSripts {
	static final String pathTol_T_calib = "C:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\script\\l_T_calibration_Duynslaegher_CombustSciTech_181_1092-1106_2009.txt";
	static final String pathToLengthsToFindTemp = "C:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\script\\lensFig3.txt";
	static Map<Double, Double> length_tempMap = new HashMap<>();
	static ArrayList<Double> lenList = new ArrayList<>();
	
	
	public static void main(String[] args) {
		runTempCalc();
	}

	public static void runTempCalc() {
		readCalibration();
		processLens();
	}
	
	public static void processLens() {
		File workFile = new File(pathToLengthsToFindTemp);
		BufferedReader reader = null;
		try {
		    reader = new BufferedReader(new FileReader(workFile));
		    String text = null;
		    while ((text = reader.readLine()) != null) {
		    	if(text.length() < 2) {
		    		throw new IllegalArgumentException();
		    	}
		    	else {
		    		Double length = Double.parseDouble(text);
		    		System.out.println("" + length + " " +calcT(length / 10));
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
	}
	
	public static void readCalibration() {
		File calibration = new File(pathTol_T_calib);
		String outputPath = "C:\\Users\\Vene\\Desktop\\Szakdoga\\2018Calc\\script\\solOutput.txt";
		BufferedReader reader = null;
		try {
		    reader = new BufferedReader(new FileReader(calibration));
		    String text = null;
		    while ((text = reader.readLine()) != null) {
		    	if(text.length() < 13) {

		    	}
		    	else {
		    		String[] ar = text.split(",");
		    		Double length = Double.parseDouble(ar[0]);
		    		lenList.add(length);
		    		length_tempMap.put(length, Double.parseDouble(ar[1]));
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
	}
	
	public static double calcT(double length) {
		if(length >= lenList.get(lenList.size()-1)) {
			return lenList.get(lenList.size()-1);
		}
		if(length <= lenList.get(0)){
			return lenList.get(0);
		}
		for(int i = 0; i < lenList.size(); i++) {
			if( lenList.get(i) > length) {
				Double higherLen = lenList.get(i);
				Double lowerLen = lenList.get(i-1);
				Double higherTemp = length_tempMap.get(higherLen);
				Double lowerTemp = length_tempMap.get(lowerLen);
				Double steepness = (higherTemp - lowerTemp) / (higherLen - lowerLen); 
				return lowerTemp + (length -lowerLen) * steepness;
			}
		}
		Double value = 0d;
		return value;
	}
}
