package utility;

public class CustomStringBuilder {
	private StringBuilder sb = new StringBuilder();
	
	public String toString() {
		return sb.toString();
	}

	public void appendLine(String s) {
		sb.append(s);
		sb.append(Constatns.newLine);
	}
	
	public void appendWithStringSep(String s) {
		sb.append(s);
		sb.append(Constatns.stringSeparator);
	}
	
	public void appendWithLineSep(String s) {
		sb.append(s);
		sb.append(Constatns.newLine);
	}
	
	public void appendWithLineSep(Object s) {
		appendWithLineSep(s.toString());
	}
	
	public void newLine() {
		sb.append(Constatns.newLine);
	}
	
	public void clear() {
		sb.setLength(0);
	}
}
