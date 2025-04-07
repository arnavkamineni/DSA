package hashi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// ====================================================================
// Island Class
// Represents a single island in the Hashi puzzle.
// Each island has an identifier, grid coordinates (row, col),
// a required bridge count, and a map of connected neighboring islands.
public class Island {
    private int id;                   // Unique island id
    private int requiredBridges;      // The number printed on the island (bridge requirement)
    private int row, col;             // Grid position of the island
    // Map storing neighbor island and the number of bridges to that neighbor
    private Map<Island, Integer> connections;

    /**
     * Constructor for Island.
     * @param id Unique identifier for the island.
     * @param requiredBridges The number printed on the island.
     * @param row The row coordinate in the grid.
     * @param col The column coordinate in the grid.
     */
    public Island(int id, int requiredBridges, int row, int col) {
        this.id = id;
        this.requiredBridges = requiredBridges;
        this.row = row;
        this.col = col;
        this.connections = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public int getRequiredBridges() {
        return requiredBridges;
    }

    public Map<Island, Integer> getConnections() {
        return connections;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * Adds (or updates) a connection (bridge) between this island and a neighbor.
     * @param neighbor The island to connect to.
     * @param bridges Number of bridges to add.
     */
    public void addConnection(Island neighbor, int bridges) {
        // Add bridges to an existing connection or create a new one
        connections.put(neighbor, connections.getOrDefault(neighbor, 0) + bridges);
    }

    /**
     * Returns the total number of bridges currently connected to this island.
     * @return Sum of bridges connecting to all neighbors.
     */
    public int totalBridges() {
        int sum = 0;
        for (int count : connections.values()) {
            sum += count;
        }
        return sum;
    }

    @Override
    public String toString() {
        // Represent the island with its id, required bridge count, and grid position.
        return "Island " + id + " (" + requiredBridges + ") at (" + row + "," + col + ")";
    }
}
