package hape;

public class Injury implements Comparable<Injury>{
	String ailment;
	int priority;
	public Injury(String ailment, int priority) {
		super();
		this.ailment = ailment;
		this.priority = priority;
	}
	
	@Override
	public String toString() {
		return ailment + ", " + priority + "| ";
	}

	public Injury() {
		ailment = "Broken heart";
		priority = 0;
	}
	
	public int compareToMax(Injury o) {
		// TODO Auto-generated method stub
		return o.priority - this.priority;
	}
	
	public int compareTo(Injury o, boolean isMin) {
		// TODO Auto-generated method stub
		return isMin ? this.compareTo(o) : this.compareToMax(o);
	}

	@Override
	public int compareTo(Injury o) {
		// TODO Auto-generated method stub
		return this.priority - o.priority;
	}
	
}
