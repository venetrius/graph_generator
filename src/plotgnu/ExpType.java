package plotgnu;

public enum ExpType {
	IDT("idt", 4, ""),
	FlameSpeed("flamespeed", 1, ""),
	FlowReactor("flowreactor", 4, "temperature"),
	BurnerStablized("burnerstabilized", 1, "distance");
	
	private final String name;
	private final int index;
	private final String INDEPENDENT_PROPERTY;
	
	public int getIndex() {
		return index;
	}
	
	public String getProperty() {
		return INDEPENDENT_PROPERTY;
	}
	
	private ExpType(String name, int index, String property) {
		this.name = name;
		this.index = index;
		this.INDEPENDENT_PROPERTY = property;
	}
	
	public static ExpType getType(String typeString) {
		for(ExpType expType : ExpType.values()) {
			if(expType.name.equals(typeString)) {
				return expType;
			}
		}
		throw new IllegalArgumentException(typeString + " is not recognised as a expType. Valid ext type are "
				+ " " + IDT.name + " " + " " + FlameSpeed.name + " " + FlowReactor.name + " " + " " + BurnerStablized.name);
	}
}
