package lab;

import java.io.File;
import java.io.FileNotFoundException;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File("Sport car price.csv");
		CustomHashMap m = new CustomHashMap();
		try {
			m.loadFromFile(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
