package auto;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;


public class AutoComplete {
	
	public AutoComplete() {
		
	}
	
	
	public static void loadTextFile(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line); 
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}
