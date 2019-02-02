package plotgnu;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class PlotFlameSpeed extends PlotFile {
	private static final String YAXIS = "lángsebesség cm / s";
	private static HashMap<String, String> key_xAxisMap = new HashMap<>();
	private static HashMap<String, String> xAxis_keyMap = new HashMap<>();

	public PlotFlameSpeed(String name, String outputDir, String template, String XAXIS) {
		super(name, outputDir, setAxis(template, key_xAxisMap.get(XAXIS)), false);
		//System.out.println(XAXIS);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void appendDataLine(String[] aS) {
		aS[1] = "";
		super.appendDataLine(aS);
	}
	
	
	
	private static String setAxis(String s, String XAXIS) {
		return s.replace("'"+X_TITLE+"'", XAXIS).replace(Y_TITLE, YAXIS);
	}
	
	public static void readXaxisTitles(String path) {
		//System.out.println(path);
		List<String> input = utility.FilesAndDirectories.readLinesFromUTF8File(path);
		HashSet<String> set = new HashSet<>();
		String key = "key";
		int count = 0;
		for(String s : input) {
			if(! xAxis_keyMap.containsKey(s)) {
				key_xAxisMap.put(key+count, "'"+s +"'");
				xAxis_keyMap.put(s,key+count);
				if(s.equals("f ")) {
					//System.out.println("Found!!");
					key_xAxisMap.put(key+count, utility.Constatns.PHI);
				}
				count++;
			}
			//System.out.println(s);
		}
	}
}
