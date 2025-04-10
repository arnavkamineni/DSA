package hashi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Hashi {
    private int rows;
    private int cols;
    private char[][] grid;
    private ArrayList<Island> islands;
    private ArrayList<Bridge> bridges;
    private HashMap<String, Island> islandMap; // maps coordinates to Island objects

    public Hashi() {
        islands = new ArrayList<>();
        bridges = new ArrayList<>();
        islandMap = new HashMap<>();
    }

    // Loads puzzle from file and builds the graph: identifies islands and bridges
    public void loadPuzzle(String fileName) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileName));
        ArrayList<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        sc.close();

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        rows = lines.size();
        cols = lines.get(0).length();
        grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        // create islands at digit positions
        int islandId = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (Character.isDigit(grid[i][j])) {
                    int required = Character.getNumericValue(grid[i][j]);
                    Island island = new Island(islandId, i, j, required);
                    islands.add(island);
                    islandMap.put(i + "," + j, island);
                    islandId++;
                }
            }
        }

        // detect horizontal bridges and connect islands
        for (int i = 0; i < rows; i++) {
            int j = 0;
            while (j < cols) {
                if (Character.isDigit(grid[i][j])) {
                    Island leftIsland = islandMap.get(i + "," + j);
                    int k = j + 1;
                    if (k < cols && (grid[i][k] == '-' || grid[i][k] == '=')) {
                        int weight = (grid[i][k] == '-') ? 1 : 2;
                        while (k < cols && (grid[i][k] == '-' || grid[i][k] == '=')) {
                            k++;
                        }
                        if (k < cols && Character.isDigit(grid[i][k])) {
                            Island rightIsland = islandMap.get(i + "," + k);
                            bridges.add(new Bridge(leftIsland, rightIsland, weight));
                            leftIsland.addBridges(weight);
                            rightIsland.addBridges(weight);
                        }
                        j = k;
                        continue;
                    }
                }
                j++;
            }
        }

        // detect vertical bridges and connect islands
        for (int j = 0; j < cols; j++) {
            int i = 0;
            while (i < rows) {
                if (Character.isDigit(grid[i][j])) {
                    Island topIsland = islandMap.get(i + "," + j);
                    int k = i + 1;
                    if (k < rows && (grid[k][j] == '|' || grid[k][j] == '#')) {
                        int weight = (grid[k][j] == '|') ? 1 : 2;
                        while (k < rows && (grid[k][j] == '|' || grid[k][j] == '#')) {
                            k++;
                        }
                        if (k < rows && Character.isDigit(grid[k][j])) {
                            Island bottomIsland = islandMap.get(k + "," + j);
                            bridges.add(new Bridge(topIsland, bottomIsland, weight));
                            topIsland.addBridges(weight);
                            bottomIsland.addBridges(weight);
                        }
                        i = k;
                        continue;
                    }
                }
                i++;
            }
        }
    }

    // Displays islands and bridges
    public void printGraph() {
        System.out.println("Islands:");
        for (Island island : islands) {
            System.out.println(island);
        }
        System.out.println("\nBridges:");
        for (Bridge bridge : bridges) {
            System.out.println(bridge);
        }
    }

    // Returns islands that don't have correct bridge counts
    public ArrayList<Island> checkBridgeCounts() {
        ArrayList<Island> mismatches = new ArrayList<>();
        for (Island island : islands) {
            if (island.getCurrentBridges() != island.getRequiredBridges()) {
                mismatches.add(island);
            }
        }
        return mismatches;
    }

    // Uses DFS to return connected components in the island graph
    public ArrayList<ArrayList<Island>> getConnectedComponents() {
        ArrayList<ArrayList<Island>> components = new ArrayList<>();
        HashSet<Island> visited = new HashSet<>();

        // build adjacency list
        HashMap<Island, ArrayList<Island>> adjList = new HashMap<>();
        for (Island island : islands) {
            adjList.put(island, new ArrayList<>());
        }
        for (Bridge b : bridges) {
            adjList.get(b.getIsland1()).add(b.getIsland2());
            adjList.get(b.getIsland2()).add(b.getIsland1());
        }

        // DFS on each unvisited island
        for (Island island : islands) {
            if (!visited.contains(island)) {
                ArrayList<Island> component = new ArrayList<>();
                Stack<Island> stack = new Stack<>();
                stack.push(island);
                while (!stack.isEmpty()) {
                    Island current = stack.pop();
                    if (!visited.contains(current)) {
                        visited.add(current);
                        component.add(current);
                        for (Island neighbor : adjList.get(current)) {
                            if (!visited.contains(neighbor)) {
                                stack.push(neighbor);
                            }
                        }
                    }
                }
                components.add(component);
            }
        }

        return components;
    }

    // Checks bridge counts and connectivity; prints issues if found
    public void validateSolution() {
        List<Island> mismatches = checkBridgeCounts();
        if (mismatches.isEmpty()) {
            System.out.println("All islands match their required number of bridges.");
        } else {
            System.out.println("Islands with mismatched bridge counts:");
            for (Island island : mismatches) {
                System.out.println("  " + island);
            }
        }

        ArrayList<ArrayList<Island>> components = getConnectedComponents();
        if (components.size() == 1) {
            System.out.println("The graph is connected. All islands are in one cluster.");
        } else {
            System.out.println("The graph is not fully connected. Disjoint clusters are:");
            int count = 1;
            for (List<Island> comp : components) {
                System.out.print("Cluster " + count + ": ");
                for (Island island : comp) {
                    System.out.print(island.getId() + " ");
                }
                System.out.println();
                count++;
            }
        }
    }
}
