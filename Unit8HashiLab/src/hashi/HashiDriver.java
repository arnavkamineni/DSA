package hashi;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class HashiDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean runAgain = true;

        while (runAgain) {
            System.out.print("\nEnter the name of the puzzle file: ");
            String fileName = scanner.nextLine();

            Hashi puzzle = new Hashi();
            try {
                puzzle.loadPuzzle(fileName); // Load puzzle from file
            } catch (FileNotFoundException e) {
                System.err.println("Error: File not found - " + fileName);
                continue; // Skip to next iteration if file not found
            }

            System.out.println("\n=== Graph Representation ===");
            puzzle.printGraph(); // Display islands and bridges

            System.out.println("\n=== Solution Validation ===");
            puzzle.validateSolution(); // Check bridge counts and connectivity

            // Ask if the user wants to try another puzzle
            System.out.print("\nWould you like to check another puzzle? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            runAgain = response.equals("yes") || response.equals("y");
        }

        System.out.println("Goodbye!");
        scanner.close();
    }
}
