package scripts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class SortAndMultiply {
	public static void main(String[] args) {
		HashMap<Double, String> map = new HashMap<>();
		ArrayList<Double> list = new ArrayList<>();
		try(Scanner in = new Scanner(System.in)){
			String line = "";
			int decimator = 10000;
			while((line = in.nextLine()).length() >3) {
				line.replaceAll("\\s","");
				//System.out.println(line);
				String[] ar = line.split(",");
				//System.out.println(ar.length);
				Double d = Double.parseDouble(ar[0].substring(0, 10));
				list.add(d);
				map.put(d, ar[0].substring(11));
			}
		}
		catch(Error e) {
			
		}
		Collections.sort(list);
		for(Double d : list) {
			System.out.println(d + "  " + map.get(d));
		}
	}
}
