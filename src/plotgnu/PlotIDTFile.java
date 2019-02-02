package plotgnu;

import java.util.Arrays;

public class PlotIDTFile extends PlotFile {
	private static final String YAXIS = "gyulladási idõ / µs";
	private static final String XAXIS = " 1000 / T (1 / K)";
	
	public PlotIDTFile(String name, String outputDir, String template, boolean plot) {
		super(name,outputDir,setAxis(template), plot);
	}

	@Override
	protected void appendDataLine(String[] aS) {
		// utility.PrintInfo.printIterable(Arrays.asList(aS)); // TODO teszt line
		Double temperature = Double.parseDouble(aS[1].replace(",",".")); // TODO not convenient
		aS[1] = Double.toString(1000 / temperature);
		super.appendDataLine(aS);
	}
	
	private static String setAxis(String s) {
		return s.replace(X_TITLE, XAXIS).replace(Y_TITLE, YAXIS);
	}
	
	
	
}
