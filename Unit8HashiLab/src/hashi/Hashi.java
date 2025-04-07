package hashi;

public class Hashi {
    private List<Island> islands;
    
    public Hashi() {
        islands = new ArrayList<>();
    }
    
    // Load a file and construct the graph.
    public void loadSolution(String filename) {
        // Implement file reading and parsing logic.
        // This part should:
        // - Create Island objects with their id and required number.
        // - Identify bridge segments and add connections between islands.
    }
    
    // Display vertices and edges.
    public void displayGraph() {
        System.out.println("Vertices:");
        for (Island island : islands) {
            System.out.println(island);
        }
        System.out.println("\nEdges:");
        for (Island island : islands) {
            for (Map.Entry<Island, Integer> entry : island.getConnections().entrySet()) {
                // Print each edge only once.
                if (island.getId() < entry.getKey().getId()) {
                    System.out.println("Edge: " + island + " --(" + entry.getValue() + ")--> " + entry.getKey());
                }
            }
        }
    }
    
    // Check that each island's degree equals its number.
    public void checkDegrees() {
        for (Island island : islands) {
            int total = island.totalBridges();
            if (total != island.getRequiredBridges()) {
                System.out.println("Degree mismatch for " + island + ". Required: " 
                                   + island.getRequiredBridges() + ", found: " + total);
            }
        }
    }
    
    // Check connectivity using DFS.
    public void checkConnectivity() {
        if (islands.isEmpty()) return;
        
        Set<Island> visited = new HashSet<>();
        dfs(islands.get(0), visited);
        
        if (visited.size() != islands.size()) {
            System.out.println("The graph is not connected. Disjoint clusters are:");
            // Identify disjoint clusters:
            List<Set<Island>> clusters = new ArrayList<>();
            Set<Island> remaining = new HashSet<>(islands);
            while (!remaining.isEmpty()) {
                Island start = remaining.iterator().next();
                Set<Island> cluster = new HashSet<>();
                dfs(start, cluster);
                clusters.add(cluster);
                remaining.removeAll(cluster);
            }
            for (Set<Island> cluster : clusters) {
                System.out.println("Cluster: " + cluster);
            }
        } else {
            System.out.println("The graph is fully connected.");
        }
    }
    
    private void dfs(Island current, Set<Island> visited) {
        if (visited.contains(current))
            return;
        visited.add(current);
        for (Island neighbor : current.getConnections().keySet()) {
            dfs(neighbor, visited);
        }
    }
    
    public void addIsland(Island island) {
        islands.add(island);
    }
    
    // Other helper methods (e.g., to get an island by its id) can be added as needed.
}
