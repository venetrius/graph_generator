package scripts;

import java.util.ArrayList;
import java.util.Scanner;

public class PrecizImageReading {
	public static void main(String[] args) {
		String result = "";
		Scanner in = new Scanner(System.in);
		boolean stop =false;
		while(! stop) {
			String line = in.nextLine();
			if(line.equals("stop")) {
				stop = true;
			}
			else {
				String stringValues= line;
				stringValues += " " + in.nextLine();
				stringValues += " " + in.nextLine();
				ArrayList<Double> triangle = getTriangle(stringValues);
				result += getResult(triangle);
			}
		}
		System.out.println(result);
	}
	
	private static String getResult(ArrayList<Double> triangle) {
		Double x = (triangle.get(0) + triangle.get(4) + triangle.get(2) *2) / 4;
		Double y = (triangle.get(1) + triangle.get(5) + triangle.get(3) *2) / 4;
		return "\n " + x + " " + y;
	}

	private static ArrayList<Double> getTriangle(String line) {
		ArrayList<Double> triangle = new ArrayList<>();
		String[] values = line.split(" ");
		for(String s : values) {
			triangle.add(getDouble(s));
		}
		return triangle;
	}

	static Double getDouble(String s){
		return Double.parseDouble(s);
	}
	
	
	
	
}
