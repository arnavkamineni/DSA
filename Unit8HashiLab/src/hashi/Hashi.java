package hashi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Hashi {
    private ArrayList<Island> islands;
    private char[][] grid;
    private HashMap<String, Island> islandMap;

    private int islandIdCounter = 1;

    public Hashi() {
        islands = new ArrayList<>();
        islandMap = new HashMap<>();
    }
    
    public void loadSolution(String filename) {
        ArrayList<String> lines;
        try {
            lines = (ArrayList<String>) Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            System.err.println("Error reading file " + filename + ": " + e.getMessage());
            return;
        }

        int rows = lines.size();
        int cols = 0;
        for (String line : lines) {
            cols = Math.max(cols, line.length());
        }
        grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < cols; j++) {
                if (j < line.length()) {
                    grid[i][j] = line.charAt(j);
                } else {
                    grid[i][j] = ' '; 
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char ch = grid[i][j];
                if (Character.isDigit(ch)) {
                    int required = Character.getNumericValue(ch);
                    Island island = new Island(islandIdCounter++, required, i, j);
                    islands.add(island);
                    islandMap.put(i + "," + j, island);
                }
            }
        }

        for (Island island : islands) {
            int r = island.getRow();
            int c = island.getCol();

            scanDirection(island, r, c, 0, 1);

            scanDirection(island, r, c, 1, 0);
        }
    }

    private void scanDirection(Island island, int r, int c, int dr, int dc) {
        int rows = grid.length;
        int cols = grid[0].length;
        int curR = r + dr;
        int curC = c + dc;
        StringBuilder bridgeBuilder = new StringBuilder();

        char validChar1, validChar2;
        if (dr == 0 && dc == 1) {
            validChar1 = '-';
            validChar2 = '=';
        } else if (dr == 1 && dc == 0) {
            validChar1 = '|';
            validChar2 = '#';
        } else {
            return; 
        }

        while (curR >= 0 && curR < rows && curC >= 0 && curC < cols) {
            char ch = grid[curR][curC];
            if (ch == ' ') {
                if (bridgeBuilder.length() == 0) {
                    curR += dr;
                    curC += dc;
                    continue;
                } else {
                   
                    break;
                }
            }
            if (Character.isDigit(ch)) {
               
                Island other = islandMap.get(curR + "," + curC);
                if (other != null && other != island && bridgeBuilder.length() > 0) {
                    int bridgeCount = 0;
                    
                    char bridgeChar = bridgeBuilder.charAt(0);
                    if ((bridgeChar == validChar1 || bridgeChar == validChar2) &&
                        isUniform(bridgeBuilder.toString(), bridgeChar)) {
                        
                        bridgeCount = (bridgeBuilder.length() >= 2) ? 2 : 1;
                    }
                    island.addConnection(other, bridgeCount);
                    other.addConnection(island, bridgeCount);
                }
                break;
            }
            if (ch == validChar1 || ch == validChar2) {
                if (bridgeBuilder.length() > 0 && ch != bridgeBuilder.charAt(0)) {
                    break;
                }
                bridgeBuilder.append(ch);
            } else {
                break;
            }
            curR += dr;
            curC += dc;
        }
    }

    private boolean isUniform(String s, char ch) {
        for (char c : s.toCharArray()) {
            if (c != ch) {
                return false;
            }
        }
        return true;
    }


    public void displayGraph() {
        System.out.println("Vertices (Islands):");
        for (Island island : islands) {
            System.out.println(island);
        }
        System.out.println("\nEdges (Bridges):");
        for (Island island : islands) {
            for (HashMap.Entry<Island, Integer> entry : island.getConnections().entrySet()) {
                Island neighbor = entry.getKey();
                if (island.getId() < neighbor.getId()) {
                    System.out.println("Edge: " + island + " --(" + entry.getValue() + ")--> " + neighbor);
                }
            }
        }
    }


    public void checkDegrees() {
        System.out.println("\nDegree Check:");
        for (Island island : islands) {
            int total = island.totalBridges();
            if (total != island.getRequiredBridges()) {
                System.out.println("Mismatch: " + island + " requires " + island.getRequiredBridges()
                        + " but has " + total + " bridges.");
            }
        }
    }

    public void checkConnectivity() {
        System.out.println("\nConnectivity Check:");
        if (islands.isEmpty()) return;

        HashSet<Island> visited = new HashSet<>();
        dfs(islands.get(0), visited);

        if (visited.size() != islands.size()) {
            System.out.println("The graph is not fully connected. Disjoint clusters found:");
            ArrayList<HashSet<Island>> clusters = new ArrayList<>();
            HashSet<Island> remaining = new HashSet<>(islands);
            while (!remaining.isEmpty()) {
                Island start = remaining.iterator().next();
                HashSet<Island> cluster = new HashSet<>();
                dfs(start, cluster);
                clusters.add(cluster);
                remaining.removeAll(cluster);
            }
            for (HashSet<Island> cluster : clusters) {
                System.out.println("Cluster: " + cluster);
            }
        } else {
            System.out.println("The graph is fully connected.");
        }
    }

    private void dfs(Island current, HashSet<Island> visited) {
        if (visited.contains(current)) return;
        visited.add(current);
        for (Island neighbor : current.getConnections().keySet()) {
            dfs(neighbor, visited);
        }
    }
}
