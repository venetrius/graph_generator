package plotgnu;

public class Main {
	public static void main(String args[]) {
		if(args.length < 2) {
			System.out.print("hello there, please read the instructions to use the plotWGnuPlot");

		}else {
			String task = args[0];
			ExpType expType = ExpType.getType(args[1]);
			switch (task) {
			case "plotSimple":
				ReadData.processSimpleInput(expType);
				break;
			case "plotExtended":
				ReadExtendedOutput.readExtendedData(expType);;
				break;
			default:
				throw new IllegalArgumentException(args[0] + " is not recognised as a functionality");
			}
		}
	}
}
