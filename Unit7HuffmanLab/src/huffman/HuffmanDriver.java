package huffman;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class HuffmanDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// loads words from a text file to populate the trie
    public void loadTextFile(String filePath) {
        System.out.println(filePath);
    	try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine() + "\n";
                
                if (!line.isEmpty()) {
                    
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

}
