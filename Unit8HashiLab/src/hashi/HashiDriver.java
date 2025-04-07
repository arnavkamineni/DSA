package hashi;

// ====================================================================
// HashiDriver Class
// This is the entry point for the application.
// It loads the solution from a file, displays the graph,
// and runs the necessary checks for the Hashi puzzle.
public class HashiDriver {
    public static void main(String[] args) {
        // Create a new Hashi puzzle instance.
        Hashi hashi = new Hashi();
        // The file containing the potential solution. Make sure the file is in the correct directory.
        String filename = "puzzle_solution.txt";

        // Load the solution from file and construct the graph.
        hashi.loadSolution(filename);

        // Display the graph: list islands (vertices) and bridges (edges).
        hashi.displayGraph();

        // Check that every island's connected bridge count matches the required number.
        hashi.checkDegrees();

        // Check that all islands are connected in one component.
        hashi.checkConnectivity();
    }
}
