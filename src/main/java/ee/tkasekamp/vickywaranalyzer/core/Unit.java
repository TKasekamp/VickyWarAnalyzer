package ee.tkasekamp.vickywaranalyzer.core;

/**
 * Everything from infantry to ships
 */
public class Unit {
	private String type = "";
	private int number;
	
	public Unit(String type, int number) {
		super();
		this.type = type;
		this.number = number;
	}
	@Override
	public String toString() {
		return "Unit [type=" + type + ", number=" + number + "]";
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
