package plotgnu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import utility.CustomStringBuilder;
import utility.FilesAndDirectories;


public class ReadData {
    private static final String batPath = "sriptOut\\flowR\\data.bat";
    private static String batTemplate =
            //"gnuplot.exe -e \"set terminal png\" -e \" set term png size 1600,1200\" -e ^\r\n" +
            "gnuplot.exe -e \"set term pngcairo  size 700,500\" -e ^\r\n" +
                    "\"set output 'fileName.png'\" -e \"set xlabel 'Xaxis' \" -e \"set ylabel 'Yaxis'\"   -e \"set key left top \" -e \"plot 'fileName' ^\r\n" +
                    "using 1:2 with points ps 2 pt 7 title 'mesured', 'fileName' using 1:3 with lines lw 3 title 'Curran', ^\r\n" +
                    "'fileName' using 1:4 with lines lw 4 title 'Galborg', ^\r\n" +
                    "'fileName' using 1:5 with lines lw 4 title 'Polimi', ^\r\n" +
                    "'fileName' using 1:6 with lines lw 4 title 'Krishana' \"";
    private static String batString = "gnuplot.exe -e \"set term pngcairo  size 700,500\" -e ^\r\n" +
            "\"set output 'fileName.png'\" -e \"set xlabel 'Xaxis' \" -e \"set ylabel 'Yaxis'\"  -e \"set key left top \" -e \"plot 'fileName' ^\r\n" +
            "using 1:2 with points ps 2 pt 7 title 'mesured', ";

    private static StringBuilder datSB = new StringBuilder();
    private static String rawDataDir;
    private static String outpudir;
    private static String xmlDir;
    private static final String flowReactorRawDataDir = "scriptInput\\flowReactor\\simpleRawOutPut\\";
    private static final String flowReactorXmlDir = "scriptInput\\flowReactor\\simpleXml\\";
    private static final String flowReactorOutpudir = "sriptOut\\flowR\\";
    private static final String BurnerStabRawDataDir = "scriptInput\\BSFlame\\simpleRawOutPut\\";
    private static final String BurnerStabXmlDir = "scriptInput\\BSFlame\\simpleXml\\";
    private static final String BurnerStabOutpudir = "sriptOut\\BS\\simpleDone\\";
    private static ReadData rd = new ReadData();

    public static void main(String[] args) {
    	
    	
    	ExpType expType = ExpType.IDT;
    	processSimpleInput(expType);
		/*String inputFile0d = "scriptInput\\0d_Results.txt";
		String inputFileFlameSpeed = "scriptInput\\flameSpeed_Results.txt";
		String outputDir = "sriptOut\\";
		String axisNames = "scriptInput\\flameSpeedXaxisTitles.txt";
		String inputFlowReactor ="scriptInput\\flowReactor_Results.txt";
		//PlotFlameSpeed.readXaxisTitles(axisNames);
		PlotFlowReactor.createBatFile(batPath); 
		ReadData rd = new ReadData();
		rd.readFlowReactorData(inputFlowReactor, outputDir);
		PlotIDTFile.createBatFile(batPath);
		 */
        
        //rd.readIDt("scriptInput\\0d_Results.txt","sriptOut\\0d\\simpleDone\\", false);
        //PlotIDTFile.createBatFile("sriptOut\\0d\\simpleDone\\data.bat");
        //rd.readBurnerStabData(BurnerStabRawDataDir, BurnerStabXmlDir, BurnerStabOutpudir);
        //rd.readFlowReactorData(flowReactorRawDataDir, flowReactorXmlDir, flowReactorOutpudir);
    }

	public static void processSimpleInput(final ExpType expType) {
		switch (expType) {
		case FlameSpeed:
			rawDataDir = "scriptInput\\FS\\SimpleInput\\flameSpeed_Results.txt";
    		outpudir = "sriptOut\\FS\\SimpleDone\\";
    		String axisNames = "scriptInput\\flameSpeedXaxisTitles.txt";
    		PlotFlameSpeed.readXaxisTitles(axisNames);
    		rd.readFlameSpeedData(rawDataDir, outpudir);
    		PlotFile.createBatFile(outpudir+"data.bat");
			break;
		case BurnerStablized:
			rawDataDir = "scriptInput\\BSFlame\\simpleXml\\";
    		outpudir = "sriptOut\\BS\\simpleDone\\";
    		xmlDir = "scriptInput\\BSFlame\\simpleXml\\";
    		rd.readBurnerStabData(rawDataDir, xmlDir, outpudir);
    		//PlotFile.createBatFile(outpudir+"data.bat");
			break;
		case FlowReactor:
			rd.readFlowReactorData(flowReactorRawDataDir, flowReactorXmlDir, flowReactorOutpudir);
			break;
		case IDT:
			rd.readIDt("scriptInput\\0d\\0d_Results.txt","sriptOut\\0d\\simpleDone\\", false);
			PlotIDTFile.createBatFile("sriptOut\\0d\\simpleDone\\data.bat");
			break;
		default:
			break;
		}
	}

    public static void setBatString(final String inputFilesdir){
        List<String> mechNames = utility.FilesAndDirectories.getFileNamesFromDirectory(inputFilesdir);
        setBatString((String[])mechNames.toArray());
    }

    public static void setBatString(Iterable<String> nameList){
        int count = 3;
        for(String name : nameList){
            batString += "'fileName' using 1:" + count++ + " with lines lw 3 title '" + name + "', ^\r\n";
        }
        batString = batString.substring(0,batString.length() - 5) + "\"";
        System.out.println(batString);// TODO
    }
    
    public static void setBatString(final  String[] nameList){
        int count = 3;
        for(String name : nameList){
            batString += "'fileName' using 1:" + count++ + " with lines lw 3 title '" + name + "', ^\r\n";
        }
        batString = batString.substring(0,batString.length() - 5) + "\"";
    }
    

    public void readIDt(final String inputPath, final String outputDir, final boolean sort) {
        ArrayList<String> inpput = utility.FilesAndDirectories.readLinesFromFile(inputPath);
        PlotIDTFile df = null;
        boolean first = true;
        for (String s : inpput) {
            String[] line = s.split(utility.Constatns.stringSeparator);
            if(first){
                setBatString(Arrays.copyOfRange(line, 3, line.length));
                first = false;
            }
            else {
                if (line.length < 2) {
                    if (df != null) {
                        df.finalizeData(sort);
                    }
                    return;
                }
                if (df == null) {
                    df = new PlotIDTFile(line[0], outputDir, batString, sort);
                } else if (!line[0].equals(df.getName())) {
                    df.finalizeData(sort);
                    df = new PlotIDTFile(line[0], outputDir, batString, sort);
                }
                df.appendDataLine(line);
            }
        }
        df.finalizeData(sort);
    }

    public void readFlameSpeedData(final String inputPath, final String outputDir) {
        ArrayList<String> inpput = utility.FilesAndDirectories.readLinesFromFile(inputPath);
      //  System.out.println(inpput.size()); // TODO test line
        PlotFlameSpeed df = null;
        for (String s : inpput) {
            String[] line = s.split(utility.Constatns.stringSeparator);
            System.out.println(s); // TODO test line
            if (line.length < 2) {
                if (df != null) {
                    df.finalizeData(true);
                }
                return;
            }
            if (df == null) {
                df = new PlotFlameSpeed(line[0], outputDir, batTemplate, line[1]);
            } else if (!line[0].equals(df.getName())) {
                df.finalizeData(true);
                df = new PlotFlameSpeed(line[0], outputDir, batTemplate, line[1]);
            }
            df.appendDataLine(line);

        }
        df.finalizeData(true);
    }

    /*
     * Crate a file with a following properties
     * file name (contains the name of a measured species); temperature, measured, simulated...;
     */
    public HashMap<String, List<String>> readIndependentVariableData(List<String> rawOutput, final String xmlDirPath, final ExpType expType) {
        HashMap<String, List<String>> map = new HashMap<>();
        ReadDataFromXml readFXml;

        String fileName = "";
        if (expType == ExpType.FlowReactor) {
            for (String next : rawOutput) {
                String[] line = next.split(" ");
                if (!fileName.equals(line[0])) {
                    fileName = subtractRESPECTFileName(line[0]);
                    readFXml = new ReadDataFromXml(xmlDirPath + fileName + ".xml", expType.getProperty());
                    List<String> temperatures = readFXml.getDataList();
                    map.put(fileName, temperatures);
                }
            }
        }
        if (expType == ExpType.BurnerStablized) {
            for (String next : rawOutput) {
                String[] line = next.split(" ");
                if (line[0].equals("Flame")) {
                    fileName = line[2].substring(0, 9);
                    readFXml = new ReadDataFromXml(xmlDirPath + fileName + ".xml", expType.getProperty());
                    List<String> temperatures = readFXml.getDataList();
                    map.put(fileName, temperatures);
                }
            }
        }
        return map;
    }

    public void readFlowReactorData(final String inputFilesdir, final String xmlDirPath, final String outputDir) {
        CustomStringBuilder bat = new CustomStringBuilder();

        CustomStringBuilder cSb = new CustomStringBuilder();
        List<String> rawInputFileNames = utility.FilesAndDirectories.getFileNamesFromDirectory(flowReactorRawDataDir); 
        setBatString(rawInputFileNames);
        String batchText = batString;
        
        ArrayList<List<String>> results = getReadFileList(flowReactorRawDataDir, rawInputFileNames);
        HashMap<String, List<String>> experimentTempMap = readIndependentVariableData(results.get(0), xmlDirPath, ExpType.FlowReactor);
        String expName = "";
        HashMap<String, CustomStringBuilder> experimetnsBySpec = new HashMap<>();
        List<String> temperatures = null;
        for (int i = 0; i < results.get(0).size(); i++) {
            String[] line = results.get(0).get(i).split(" ");
            String nextName = subtractRESPECTFileName(line[0]);
            String specName = line[2];
            int index = Integer.parseInt(line[1]) - 1;
            if (!nextName.equals(expName)) {
                for (String key : experimetnsBySpec.keySet()) {
                    String fileName = outputDir + utility.Constatns.FILE_SEPARATOR + expName + "_" + key;
                    utility.FilesAndDirectories.writeToFile(fileName, experimetnsBySpec.get(key).toString());
                    String formattedKey = formatKey(key);
                    bat.appendLine(batchText.replace("Xaxis", "Temperature").replace("Yaxis", "c_{ " + formattedKey + "} /mol").replace("fileName", expName + "_" + key));
                }
                experimetnsBySpec.clear();
                expName = nextName;
                temperatures = experimentTempMap.get(expName);
            }
            if (experimetnsBySpec.get(specName) == null) {
                experimetnsBySpec.put(specName, new CustomStringBuilder());
            }
            cSb = experimetnsBySpec.get(specName);
            cSb.appendWithStringSep(temperatures.get(index));
            cSb.appendWithStringSep(line[3]);
            for (List<String> list : results) {
                cSb.appendWithStringSep(list.get(i).split(" ")[4]);
            }
            cSb.newLine();

        }
        for (String key : experimetnsBySpec.keySet()) {
            String fileName = outputDir + utility.Constatns.FILE_SEPARATOR + expName + "_" + key;
            utility.FilesAndDirectories.writeToFile(fileName, experimetnsBySpec.get(key).toString());
            String formattedKey = formatKey(key);
            bat.appendLine(batchText.replace("Xaxis", "Temperature").replace("Yaxis", "c_{ " + formattedKey + "} /mol").replace("fileName", expName + "_" + key));
        }
        utility.FilesAndDirectories.writeTo_UTF8_File(batPath, bat.toString());
    }

    private String formatKey(final String key) {
    	HashSet<String> numberSet = new HashSet<>();
    	StringBuilder number = new StringBuilder();
		boolean inNumberSequence = false;
		char[] charArray = key.toCharArray();
		for (int i = 0; i < key.length(); i++) {
			char c = charArray[i];
			if(inNumberSequence) {
				if (! Character.isDigit(c)) {
					numberSet.add(number.toString());
					number.setLength(0);
					inNumberSequence = false;
				}
				else {
					number.append(c); 
				}
			}
			else if ( Character.isDigit(c)) {
				number.append(c);
				inNumberSequence = true;
			}
		}
		if(number.length() > 0) {
			numberSet.add(number.toString());
		}
		String ret = key;
		for (String next : numberSet) {
			ret = ret.replace(next, "_{" + next + "}");
		}
		return ret;
	}

	private ArrayList<List<String>> getReadFileList(final String directory, List<String> fileNames) {
        ArrayList<List<String>> results = new ArrayList<>();
        for (String fileName : fileNames) {
        	//System.out.println("path " + fileName);
            results.add(utility.FilesAndDirectories.readLinesFromFile(directory + utility.Constatns.FILE_SEPARATOR +fileName));
        }
        return results;
    }

    public void readBurnerStabData(final String inputFilesdir, final String xmlDirPath, final String outputDir) {
        CustomStringBuilder bat = new CustomStringBuilder();
        String batchText = batTemplate;

        CustomStringBuilder cSb = new CustomStringBuilder();
        List<String> rawInputFileNames = utility.FilesAndDirectories.getFileNamesFromDirectory(BurnerStabRawDataDir);

        ArrayList<List<String>> results = getReadFileList(BurnerStabRawDataDir, rawInputFileNames);
        HashMap<String, List<String>> experimentDistanceMap = readIndependentVariableData(results.get(0), xmlDirPath, ExpType.BurnerStablized);
        String expName = "";
        String specName = "";
        List<String> distances = null;
        List<String> mesuredValues = null;
        int lineIndex = 0;
        boolean end = false;

        while (!end && lineIndex < results.get(0).size()) {
            String nextLine = results.get(0).get(lineIndex);
            if (nextLine.isEmpty()) {
                bat.appendLine(batchText.replace("Xaxis", "távolság / mm").replace("Yaxis", "c " +"_{" +  formatKey(specName) +  "}" + " / mol").replace("fileName", expName));
                utility.FilesAndDirectories.writeToFile(outputDir + expName, cSb.toString());
                cSb.clear();
                expName = "";
            } else if (expName.equals("")) {
                if (nextLine.isEmpty()) {
                    end = true;
                } else {
                    expName = nextLine.substring(10, 19);
                    System.out.println(expName);
                    distances = experimentDistanceMap.get(expName);
                    lineIndex++;
                    specName = results.get(0).get(lineIndex).split(" ")[1];
                    ReadDataFromXml readFXml = new ReadDataFromXml(xmlDirPath + expName + ".xml", "composition");
                    mesuredValues = readFXml.getDataList();
                }
            } else {
                String[] line = nextLine.split(" ");
                int expIndex = Integer.parseInt(line[0]);
                cSb.appendWithStringSep(distances.get(expIndex - 1));
                cSb.appendWithStringSep(mesuredValues.get(expIndex - 1));
                for (List<String> list : results) {
                    cSb.appendWithStringSep(list.get(lineIndex).split(" ")[1]);
                }
                cSb.newLine();

            }
            lineIndex++;
        }
        utility.FilesAndDirectories.writeTo_UTF8_File(outputDir + "data.bat", bat.toString());

    }


    private String subtractRESPECTFileName(final String s) {
        int len = s.length();
        return s.substring(len - 13, len - 4);
    }

}
