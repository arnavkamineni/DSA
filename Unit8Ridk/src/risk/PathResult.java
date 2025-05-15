package risk;

import java.util.*;

public class PathResult {
    private final List<Territory> path;
    private final int cost;
    private final boolean success;
    public PathResult(List<Territory> path, int cost, boolean success) {
        this.path = path; this.cost = cost; this.success = success;
    }
    public List<Territory> getPath() { return path; }
    public int getCost() { return cost; }
    public boolean isSuccess() { return success; }
}
