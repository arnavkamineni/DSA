package hashi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HashiDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		fileTo2DArray(null);
	}
	
	 public static ArrayList<String[]> fileTo2DArray(String filePath) {
	        ArrayList<String[]> data = new ArrayList<>();
	        
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                data.add(line.split(""));
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        return data;
	 }

}
