package lab;

public class CarMap {
	Entry[] hashTable;
	public static final int defaultCapacity = 10;
	public CarMap() {
		hashTable = new Entry[defaultCapacity];
	}
}
