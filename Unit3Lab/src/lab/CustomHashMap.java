package lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class CustomHashMap {
	Entry[] hashTable;
	public static final int defaultCapacity = 10;
	
	public CustomHashMap() {
		hashTable = new Entry[defaultCapacity];
	}
	
	public static void loadFromFile(File f) throws FileNotFoundException {
		
		if (!f.isFile())
			return;
		
		Scanner input = new Scanner(f);
		input.useDelimiter(",");
		int len = 0;
		while (input.hasNext()) {
			String [] arr = new String[8];
			for (int i = 0; i < 8; i++) {
				arr[i] = input.next();
			
			}
			System.out.print(len++);
			System.out.println(Arrays.toString(arr));
		}
		
	}
}
