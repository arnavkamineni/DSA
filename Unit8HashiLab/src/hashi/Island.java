package hashi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Island {
    private int id;                   
    private int requiredBridges;     
    private int row, col;             
    private HashMap<Island, Integer> connections;
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

    public HashMap<Island, Integer> getConnections() {
        return connections;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


    public void addConnection(Island neighbor, int bridges) {
        connections.put(neighbor, connections.getOrDefault(neighbor, 0) + bridges);
    }


    public int totalBridges() {
        int sum = 0;
        for (int count : connections.values()) {
            sum += count;
        }
        return sum;
    }

    @Override
    public String toString() {
        return "Island " + id + " (" + requiredBridges + ") at (" + row + "," + col + ")";
    }
}
