package scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * 
 * @author Vene
 *in case of OptimaPP cannot find ignition type, and the ignition definition is 95% c of NH3 than use this with CAUCTION
 */
public class AllOut {
	
	public static void main(String[] args) {
		test();
	}
	
	public static void test() {
		AllOut idtPars = new AllOut();
		String path = "c:\\Users\\Vene\\Documents\\sript\\scriptInput\\0d\\FMLogOut1_4\\Simple\\Abian\\FM_log_x10010004_p10_mechTest_init_parset";
		Double idt = idtPars.findIgnitionDelay(path);
		if(idt.equals(3.32455d)) System.out.println("required 3.32455 got" + idt );
	}
	
	public double findIgnitionDelay(String filePath) {
		final String KEYWORD = "delay";
		File file = new File(filePath);
		BufferedReader reader = null;
		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;
		    String[] line;
		    while ((text = reader.readLine()) != null) {
		    	line = text.split(" ");
		    	if(line.length > 1 && line[2].equals(KEYWORD)) {
		    		return Double.parseDouble(line[4]);
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
	
	
	
	/*
}
	// Curran index 11, 27 sub:	filePath.substring(71, 80) + filePath.substring(90, 94)
	//Galborg 7 18 filePath.substring(73, 82) + filePath.substring(92, 96)
	//Polimi 11 17
	static int index1 = 7;
	static int index2 = 18;
	
	static ArrayList<String> fileList = new ArrayList<>();
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		//File file = new File("C:\\Users\\Vene\\Desktop\\Szakdoga\\0413\\fromGergo\\QuickIgnition\\CurranJOBS\\list.txt");
		File file = new File("C:\\Users\\Vene\\Desktop\\Szakdoga\\0413\\fromGergo\\QuickIgnition\\GlarborgJOBS\\list.txt");
		//File file = new File("C:\\Users\\Vene\\Desktop\\Szakdoga\\0413\\fromGergo\\QuickIgnition\\PolimiJOBS\\list.txt");
		BufferedReader reader = null;
		readFileList(file, reader);
		boolean first =false;
		if(first) {
			findLocation(new File(fileList.get(0)), reader);
		}
		else {
			for(String filePath : fileList) {
				File outPutFile = new File(filePath);
				readAndProcess(outPutFile, reader, filePath.substring(71, 80) + filePath.substring(90, 94));
			}
		}		
	}

	private static void findLocation( File file,
			BufferedReader reader) {
		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;
		    boolean first = true;
		    while ((text = reader.readLine()) != null) {
		    	String[] splitted = text.split(" ");
	    		if(first) {
	    			int count = 0;
	    			for(String d : splitted) {
	    				System.out.println("" + (count++) + " " + d);
	    			}
	    			first = false;
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
	
	private static void readAndProcess( File file,
			BufferedReader reader, String fileName) {
		List<Double> timeList = new ArrayList<>();
		List<Double> list1 = new ArrayList<>();
		List<Double> list2 = new ArrayList<>();
		try {

		    reader = new BufferedReader(new FileReader(file));
		    String text = null;
		    boolean first = true;
		    while ((text = reader.readLine()) != null) {
		    	int len = text.length();
		    	int lastLen = text.length();
		    	*//*do {
		    		System.out.println(text);
		    		text.replaceAll("  ", " ");
		    		lastLen = len;
		    		len = text.length();
		    	}
		    	while(len != lastLen); 
	    		System.out.println(text); *//*
		    	String[] splitted = text.split(" ");
		    	timeList.add((Double.parseDouble(splitted[0])));
		    	list1.add((Double.parseDouble(splitted[index1])));
		    	list2.add((Double.parseDouble(splitted[index2])));
	    		*//*if(first) {
	    			int count = 0;
	    			for(String d : splitted) {
	    				System.out.println("" + (count++) + " " + d);
	    			}
	    			first = false;
	    		}*/	/*	        
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
		int ig1 = findIgnition(list1);
		if(ig1 < 0) {
			System.out.print(fileName + " ignition does not found :  " );
		}
		else {	
			System.out.print(fileName + " " + timeList.get(ig1) + " " + list1.get(ig1) + "   :   ");
		}
		int ig2 = findIgnition(list2);
		if(ig2 < 0) {
			System.out.println( " ignition does not found" );
		}
		else {
			System.out.println(" " + timeList.get(ig2) + " " + list1.get(ig2));
		}
	}

	private static void readFileList(File file, BufferedReader reader) {
		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;
		    while ((text = reader.readLine()) != null) {
		    		fileList.add(text);
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
	
	static int findIgnition(List<Double> list) {
		double t95percent = list.get(0) * 0.95d;
		int count = 0;
		for(double d : list) {
			if(d < t95percent) {
				return count;
			}
			count++;
		}
		return -1;
	}
	

*/
/*
 * Beolvas fileból output file listát, azon végig iterál, minden sorból kiveszi az adott számút és kiírja a 95 os csökkenéshez tartozó idõpontot
 * 
 * 
 * */
}