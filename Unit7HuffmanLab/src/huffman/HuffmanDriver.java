package huffman;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class HuffmanDriver {
	static String text = "";
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		loadTextFile("test.txt");
		Huffman h = new Huffman(text);
		System.out.println(text);
	}
	
	// loads words from a text file to populate the trie
    public static void loadTextFile(String filePath) {
        System.out.println(filePath);
    	try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                if (!line.isEmpty()) {
                    line += "\n";
                    text += line;
                } else {
                	text += line;
                	break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    	
    }

}
