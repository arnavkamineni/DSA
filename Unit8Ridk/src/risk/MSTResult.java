package risk;

import java.util.*;

public class MSTResult {
    private final List<Edge> edges;
    private final int totalWeight;
    public MSTResult(List<Edge> edges, int totalWeight) {
        this.edges = edges;
        this.totalWeight = totalWeight;
    }
    public List<Edge> getEdges() { return edges; }
    public int getTotalWeight() { return totalWeight; }
}
