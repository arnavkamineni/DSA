package lab;

import java.util.Objects;

public class Key {
	String make;
	String model;
	int year;
	
	

	public Key(String make, String model, int year) {
		super();
		this.make = make;
		this.model = model;
		this.year = year;
	}



	@Override
	public int hashCode() {
		return Objects.hash(make, model) + year;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key other = (Key) obj;
		return Objects.equals(make, other.make) && Objects.equals(model, other.model) && year == other.year;
	}

	

	
	
	
}
