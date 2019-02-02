package scripts;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CreateInput {
	/*static List<Solution> list = new ArrayList<>();
	static StringBuilder sb = new StringBuilder("");
	
	static final String NEW_LINE = "\n";
	static final String PLUSZ = "+";
	static final String MINUSZ = "-";
	static final String QUESTIONMARK = "?";
	static final String path = "c:\\Users\\Vene\\Desktop\\Temp\\input.txt";
	
	public static void main(String[] args) {
		int n = (int) (Math.random()*100000+1);
		sb.append(n + NEW_LINE);
		System.out.println(n);
		boolean sucsess = false;
		for(int i = 0; i<n; i++) {
			while(!sucsess) {
				sucsess = addLine();
			}
			sucsess = false;
		}
		writeToFile(sb.toString());
	}

	private static boolean addLine() {
		int type = (int) (Math.random()*5);
		if(type == 0) {
			if(!(list.size() > 0)) {
				return false;
			}
			int index = (int) (Math.random() * list.size());
			Solution sol = list.get(index);
			list.remove(index);
			sb.append(MINUSZ + " " + sol.k + " " + sol.b + NEW_LINE);
		}
		else if(type < 4) {
			int k = (int) (Math.random() * 100000 + 1);
			int b = (int) (Math.random() * 100000);
			Solution sol = new Solution(k, b);
			list.add(sol);
			sb.append(PLUSZ + " " + sol.k + " " + sol.b + NEW_LINE);
		}
		else {
			int q = (int) (Math.random() * 100000);
			sb.append(QUESTIONMARK + " " + q + NEW_LINE);
		}
		return true;
	}
	
	public static void writeToFile(String text) {
		String outputPath = path;
		try (PrintWriter out = new PrintWriter(outputPath)) {
			out.println(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} */
}
