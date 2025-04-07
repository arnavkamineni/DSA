package hashi;

// ====================================================================
// Hashi Class
// This class contains the graph (list of islands) and the algorithms to:
// - Load and parse a potential solution from a file.
// - Build islands and the bridge connections between them.
// - Display the graph.
// - Check that each island meets its required number of bridges.
// - Check that the islands form one connected group.
public class Hashi {
    // List of all islands in the puzzle
    private List<Island> islands;
    // 2D grid of characters representing the puzzle solution
    private char[][] grid;
    // Map from grid coordinates (row,col as "row,col" string) to Island object
    private Map<String, Island> islandMap;

    // Unique id counter for islands
    private int islandIdCounter = 1;

    public Hashi() {
        islands = new ArrayList<>();
        islandMap = new HashMap<>();
    }

    /**
     * Loads the puzzle solution from a file and constructs the graph.
     * This method reads the file into a grid of characters, then
     * scans the grid to identify islands (digits) and then finds
     * the bridges connecting islands by scanning rightwards and downwards.
     * @param filename The name of the file containing the solution.
     */
    public void loadSolution(String filename) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            System.err.println("Error reading file " + filename + ": " + e.getMessage());
            return;
        }

        // Build the grid (2D char array) from file lines
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
                    grid[i][j] = ' '; // Fill missing columns with space
                }
            }
        }

        // First pass: Identify islands (any digit character) and store their positions.
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

        // Second pass: For each island, scan rightwards and downwards for bridges.
        // This way, we avoid duplicate detection.
        for (Island island : islands) {
            int r = island.getRow();
            int c = island.getCol();

            // Scan to the right for a horizontal bridge.
            scanDirection(island, r, c, 0, 1);

            // Scan downward for a vertical bridge.
            scanDirection(island, r, c, 1, 0);
        }
    }

    /**
     * Scans from an island in a given direction (dr, dc) to detect a valid bridge.
     * When a digit (another island) is encountered after a continuous line of valid bridge characters,
     * a connection is established between the two islands.
     *
     * @param island The starting island.
     * @param r Starting row.
     * @param c Starting column.
     * @param dr Direction row delta (0 for horizontal, 1 for vertical).
     * @param dc Direction column delta (1 for horizontal, 0 for vertical).
     */
    private void scanDirection(Island island, int r, int c, int dr, int dc) {
        int rows = grid.length;
        int cols = grid[0].length;
        int curR = r + dr;
        int curC = c + dc;
        StringBuilder bridgeBuilder = new StringBuilder();
        // Determine which characters are valid for bridges in this direction.
        // For horizontal, valid characters are '-' and '='.
        // For vertical, valid character is '|' (we assume double bridges are represented by consecutive '|' characters).
        char validChar1, validChar2;
        if (dr == 0 && dc == 1) {
            validChar1 = '-';
            validChar2 = '=';
        } else if (dr == 1 && dc == 0) {
            validChar1 = '|';
            validChar2 = '|'; // Use '|' for vertical; double '|' will denote 2 bridges.
        } else {
            return; // Unsupported direction (should never happen).
        }

        // Traverse in the given direction until we go out of bounds.
        while (curR >= 0 && curR < rows && curC >= 0 && curC < cols) {
            char ch = grid[curR][curC];
            if (ch == ' ') {
                // Skip spaces if we haven't started a bridge.
                if (bridgeBuilder.length() == 0) {
                    curR += dr;
                    curC += dc;
                    continue;
                } else {
                    // If we already have some bridge characters, a space interrupts the connection.
                    break;
                }
            }
            if (Character.isDigit(ch)) {
                // Found a candidate island at the end of a potential bridge.
                // Ensure we are not connecting an island to itself.
                // Find the island in our map.
                Island other = islandMap.get(curR + "," + curC);
                if (other != null && other != island && bridgeBuilder.length() > 0) {
                    int bridgeCount = 0;
                    // For horizontal, check if the bridge is made of '-' or '='
                    // For vertical, check the count of '|' characters.
                    // According to the rules, at most two bridges can exist between a pair.
                    char bridgeChar = bridgeBuilder.charAt(0);
                    if ((bridgeChar == validChar1 || bridgeChar == validChar2) &&
                        isUniform(bridgeBuilder.toString(), bridgeChar)) {
                        // If the bridge has length 1 then it is a single bridge,
                        // if length is 2 or more, we cap it to 2 (as per rules).
                        bridgeCount = (bridgeBuilder.length() >= 2) ? 2 : 1;
                    }
                    // Add the connection in both directions
                    island.addConnection(other, bridgeCount);
                    other.addConnection(island, bridgeCount);
                }
                break;
            }
            // If the character is a valid bridge character
            if (ch == validChar1 || ch == validChar2) {
                // If bridgeBuilder already has a character, ensure the new character is the same.
                if (bridgeBuilder.length() > 0 && ch != bridgeBuilder.charAt(0)) {
                    // Mixed bridge characters; break out as this is invalid.
                    break;
                }
                bridgeBuilder.append(ch);
            } else {
                // Encountered an unexpected character (like '#' or others); stop scanning.
                break;
            }
            curR += dr;
            curC += dc;
        }
    }

    /**
     * Helper method to check if all characters in a string are the same.
     * @param s The string to check.
     * @param ch The character to compare with.
     * @return true if all characters in s are equal to ch, false otherwise.
     */
    private boolean isUniform(String s, char ch) {
        for (char c : s.toCharArray()) {
            if (c != ch) {
                return false;
            }
        }
        return true;
    }

    /**
     * Displays the graph by printing the list of islands (vertices) and the bridges (edges).
     */
    public void displayGraph() {
        System.out.println("Vertices (Islands):");
        for (Island island : islands) {
            System.out.println(island);
        }
        System.out.println("\nEdges (Bridges):");
        // To avoid printing duplicate edges, we print only if the current island id is less than its neighbor's id.
        for (Island island : islands) {
            for (Map.Entry<Island, Integer> entry : island.getConnections().entrySet()) {
                Island neighbor = entry.getKey();
                if (island.getId() < neighbor.getId()) {
                    System.out.println("Edge: " + island + " --(" + entry.getValue() + ")--> " + neighbor);
                }
            }
        }
    }

    /**
     * Checks that each island has the correct number of bridges connected to it.
     * If an island does not match the required count, an error is printed.
     */
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

    /**
     * Checks that the graph is fully connected.
     * Uses Depth-First Search (DFS) to identify connected components.
     * If disjoint clusters exist, they are reported.
     */
    public void checkConnectivity() {
        System.out.println("\nConnectivity Check:");
        if (islands.isEmpty()) return;

        // Use DFS from the first island
        Set<Island> visited = new HashSet<>();
        dfs(islands.get(0), visited);

        if (visited.size() != islands.size()) {
            System.out.println("The graph is not fully connected. Disjoint clusters found:");
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
            // Print each cluster
            for (Set<Island> cluster : clusters) {
                System.out.println("Cluster: " + cluster);
            }
        } else {
            System.out.println("The graph is fully connected.");
        }
    }

    /**
     * Depth-first search (DFS) helper method to traverse connected islands.
     * @param current The current island.
     * @param visited Set of islands visited so far.
     */
    private void dfs(Island current, Set<Island> visited) {
        if (visited.contains(current)) return;
        visited.add(current);
        for (Island neighbor : current.getConnections().keySet()) {
            dfs(neighbor, visited);
        }
    }
}
