package utility;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

public class PrintInfo {
	public static void printMatrix(Object[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.println(matrix[i][j]);
			}
		}
	}
	
	public static void printMatrix(double[][] matrix) {
		 NumberFormat formatter = new DecimalFormat("#0.000");  
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(formatter.format(matrix[i][j]) + " ");
			}
			System.out.println("");
		}
	}
	
	public static void printIterable(Iterable iterable) {
		for (Object o : iterable ) {
			System.out.println(o);
		}
	}
	
	public static void printMap(Map<String,ArrayList<String>> map) {
		System.out.println("printing map");
		for(String key : map.keySet()) {
			System.out.println("key : " + key);
			printIterable(map.get(key));
		}
		System.out.println("printing map ended");
	}
	
}
