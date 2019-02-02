package plotgnu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.CustomStringBuilder;

/*
 * read optimapp ooutput (calculated extended xml) and simple plot data files (only containing the point in the includid in experiments)
 * to create extended plotting data files 
 */
public class ReadExtendedOutput {
	private StringBuilder sb = new StringBuilder();
	private final String PREFIX = "extended_";
	private static int orGrowth;
	private static ExpType expType;
	
	public static void main(String[] args) {
		
		readExtendedData(ExpType.BurnerStablized);
		
	}



	public static void readExtendedData(ExpType expType) {
		orGrowth = 10;
		String simpResDir = null;
		String outputDir = null;
		String extRawResDirPath = null;

		switch (expType) {
		case IDT:
			extRawResDirPath = "scriptInput\\Curran_0d_extended\\";
			simpResDir = "scriptInput\\0ddone\\";
			outputDir = "sriptOut\\0d\\extDone\\";
			orGrowth = 10;
			break;
		case FlameSpeed:
			extRawResDirPath = "scriptInput\\FS\\ExtRawInput\\";
			simpResDir = "scriptInput\\FS\\SimpleDone\\";
			outputDir = "sriptOut\\FS\\ExtDone\\";
			orGrowth = 3;
			break;
		case FlowReactor:
			extRawResDirPath = "scriptInput\\flowReactor\\extRawOutPut\\";
			simpResDir = "scriptInput\\flowReactor\\simpDone\\";
			outputDir = "sriptOut\\flowR\\extDone\\";
			orGrowth = 10;
			break;
		case BurnerStablized:
		default:
			throw new IllegalArgumentException("expreriment type :" +expType + "is not supported for extended input handling");
		}
		new ReadExtendedOutput(extRawResDirPath, simpResDir, outputDir, orGrowth, expType);
	}
	

	
	public ReadExtendedOutput(final String extRawResDirPath, final String simpResDir, final String outputDir, final int orderOfGrowth, final ExpType exp) {
		expType = exp;
		// read raw output files
		ArrayList<Map<String, ArrayList<String>>> resultsByMechsByExp = readAll(extRawResDirPath);
		
		// read not extended plot file names
		List<String> simpFileNames = utility.FilesAndDirectories.getFileNamesFromDirectory(simpResDir);
		
		for (String fileName : simpFileNames) {
			ArrayList<ArrayList<String>> resultsByMech = new ArrayList<>();
			for(int i = 0; i < resultsByMechsByExp.size(); i++) {
				ArrayList<String> list = resultsByMechsByExp.get(i).get(PREFIX+fileName);
				if(list == null && exp == ExpType.FlowReactor) {
					list = resultsByMechsByExp.get(i).get(PREFIX+fileName);
				}
				if (list == null) {
					throw new IllegalStateException("No rawdata entry found with name: " + PREFIX+fileName);
				}
				resultsByMech.add(list);
			}
			
			ArrayList<String> textSimpPlot = utility.FilesAndDirectories.readLinesFromFile(simpResDir + fileName);
			resultsByMech = adjustRawOutput(resultsByMech,textSimpPlot);  // TODO test in other case than IDT
			double last = Double.parseDouble(textSimpPlot.get(0).split(" ")[0]);
			sb.append(textSimpPlot.get(0));
			sb.append(utility.Constatns.newLine);
			int count = 1;
			System.out.println(fileName);
			for(int i = 1; i < textSimpPlot.size(); i++) {
				if (textSimpPlot.get(i).length() > 1) {
					double next = Double.parseDouble(textSimpPlot.get(i).split(" ")[0]);
					double[] interpolatedIndVar = GeneratePoints.interpolate(last, next, orderOfGrowth);
					for (int j = 0; j < orderOfGrowth; j++) {
						appendLine(count, interpolatedIndVar[j], resultsByMech);
						count++;
					}
					last = next;
					sb.append(textSimpPlot.get(i));
					sb.append(utility.Constatns.newLine);
					count++;
				}
			}
			utility.FilesAndDirectories.writeToFile(outputDir+fileName, sb.toString().replaceAll(",",".")); // TODO not nice reaplace
			sb = new StringBuilder();
		}
	}
	
	/*
	 * double check on the order of the extended input
	 */
	private ArrayList<ArrayList<String>> adjustRawOutput(ArrayList<ArrayList<String>> resultsByMech, ArrayList<String> text) {
		double first = Double.parseDouble(text.get(0).split(" ")[3]);
		double second = Double.parseDouble(text.get(1).split(" ")[3]);
		double middle = Double.parseDouble(resultsByMech.get(1).get(1));
		if( (first > middle && second > middle) || (first < middle && second < middle) ) {
			System.out.println("reversed!");
			ArrayList<ArrayList<String>> newResultsByMech = new ArrayList<>();
			for(int i=0; i < resultsByMech.size(); i++) {
				newResultsByMech.add(reverseList(resultsByMech.get(i)));
			}
			resultsByMech = newResultsByMech;
		}
		middle = Double.parseDouble(resultsByMech.get(1).get(1));
		/*if( false && (( first > middle && second > middle) || (first < middle && second < middle) )) { // TODO
			System.out.println("WARNING: rererereversed!  " + "first: " + first + " second: " + second + " middel :" + middle);
			//utility.PrintInfo.printIterable(resultsByMech.get(1));
			if( (expType == ExpType.IDT)) { // TODO WTF
				ArrayList<ArrayList<String>> newResultsByMech = new ArrayList<>();
				for(int i=0; i < resultsByMech.size(); i++) {
					newResultsByMech.add(reverseList(resultsByMech.get(i)));
				}
			resultsByMech = newResultsByMech;
			}
		} */
		return resultsByMech;
	}

	private void appendLine(final int i,final  double indVar, ArrayList<ArrayList<String>> resultsByMech) {
		sb.append(indVar);
		sb.append(utility.Constatns.stringSeparator);
		sb.append("?");
		sb.append(utility.Constatns.stringSeparator);
		for (ArrayList<String> arrayList : resultsByMech) {
			if(arrayList == null) {
				throw new IllegalArgumentException("Array is null");
			}if(arrayList.size() < i) {
				throw new IllegalArgumentException("Array size is smaller than : " + i);
			}
			String value = ( Double.parseDouble(arrayList.get(i)) >= 0 ) ? arrayList.get(i) : "?";
			sb.append(arrayList.get(i));
			sb.append(utility.Constatns.stringSeparator);
		}
		sb.append(utility.Constatns.newLine);
		
	}

	public ArrayList<Map<String, ArrayList<String>>> readAll(final String dirPath){
		ArrayList<Map<String, ArrayList<String>>> resultsByMechsByExp = new ArrayList<Map<String, ArrayList<String>>>();
		List<String> fileNames = utility.FilesAndDirectories.getFileNamesFromDirectory(dirPath);
		for(String fileName : fileNames) {
			Map<String,ArrayList<String>> resByMechByExperiment = read(dirPath+fileName);
			resultsByMechsByExp.add(resByMechByExperiment);
		}
		return resultsByMechsByExp;
	}
	
	public Map<String, ArrayList<String>> read(final String path) {
		final int resIndex = expType.getIndex();
		Map<String, ArrayList<String>> resultsByExperiment = new HashMap<String, ArrayList<String>>();
		ArrayList<String> results = new ArrayList<>();
		ArrayList<String> text = utility.FilesAndDirectories.readLinesFromFile(path);
		String experiment = "";

		// System.out.println(s);
		if (expType == ExpType.FlowReactor) {
			HashMap<String, ArrayList<String>> experimetnsBySpec = new HashMap<>();
			String specName = "";
			for (String s : text) {
				String[] line = s.split(" ");
				String nextName = extractExpName(line).substring(0, 9);
				if (!nextName.equals(experiment)) {
					for (String key : experimetnsBySpec.keySet()) {
						resultsByExperiment.put("extended_" + experiment + "_" + key, sortByAccordingToGroth(experimetnsBySpec.get(key), 10));
					}
					experimetnsBySpec.clear();
					experiment = nextName;
				}
				specName = line[2];
				ArrayList<String> list = experimetnsBySpec.get(specName);
				if (list == null) {
					experimetnsBySpec.put(specName, new ArrayList<>());
					list = experimetnsBySpec.get(specName);
				}
				list.add(line[4]);
			}
			for (String key : experimetnsBySpec.keySet()) {
				System.out.println("name2 : " + "extended_" + experiment + "_" + key);
				resultsByExperiment.put("extended_" + experiment + "_" + key, sortByAccordingToGroth(experimetnsBySpec.get(key), 10));
			}
		} else {
			for (String s : text) {
				if (s.length() > 10) {
					String[] line = s.split(utility.Constatns.stringSeparator);
					String newExperient = extractExpName(line);
					if (experiment.equals(newExperient)) {
						results.add(line[resIndex]);
					} else {
						if (results.size() > 0) {
							results = sortByAccordingToGroth(results, orGrowth);
							resultsByExperiment.put(experiment, results);
						}
						experiment = newExperient;
						results = new ArrayList<>();
						results.add(line[resIndex]);
					}
				}
			}
			results = sortByAccordingToGroth(results, orGrowth);
			resultsByExperiment.put(experiment, results);
		}
		return resultsByExperiment;
	}
	
	private String extractExpName(String[] expLine) {
		switch (expType) {
		case IDT:
			return extractExpNameIDT(expLine[0]);
		case FlameSpeed:
			return extractExpNameFlameSpeed(expLine[0]);
		case FlowReactor:
			String specName = expLine[2];
			//System.out.println("extracted:" + expLine[0].substring(40,49) + "_" + specName);
			return expLine[0].substring(40,49) + "_" + specName  ;
		default:
			throw new IllegalArgumentException("experiment type is not known");
		}
	}

	public String extractExpNameIDT(final String pathToExp) {
		return pathToExp.substring(15,33);
	}
	
	public String extractExpNameFlameSpeed(final String flameId) {
		return flameId.substring(0,18);
	}
	
	public ArrayList<String> reverseList(List<String> list){
		ArrayList<String> reversedList = new ArrayList<>();
		for ( int i = list.size() -1; i > -1 ; i--) {
			reversedList.add(list.get(i));
		}
		return reversedList;
	}
	
	public ArrayList<String> sortByAccordingToGroth(ArrayList<String> list, final int orderOfGrowth){
		String[] newAr = new String[list.size()];
		ArrayList<String> newList = new ArrayList<>();
		int originalPoint = (list.size() - 1) / (orderOfGrowth + 1) + 1;
		for(int i = 0 ; i < originalPoint -1; i++) {
			newAr[i * (orderOfGrowth + 1)] = list.get(i);
			for (int j = 1; j <= orderOfGrowth; j++) {
				newAr[i * (orderOfGrowth + 1) + j] = list.get(originalPoint + i * orderOfGrowth + j -1);
			}
		}
		newAr[newAr.length -1] = list.get(originalPoint -1);
		for(String s : newAr) {
			newList.add(s);
		}
		return newList;
		
	}
}
