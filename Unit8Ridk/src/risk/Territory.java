package risk;

import java.util.*;

public class Territory implements Comparable<Territory> {
    public final String name;
    public int defenders;          
    public Map<Territory,Integer> connections = new HashMap<>();
    // fields for Dijkstra
    public int cost;               // best casualty cost
    public int pathLength;         // best hop count
    public Territory prev;         // previous in best path

    public Territory(String name) {
        this.name = name;
        this.defenders = 0;
    }

    public void addConnection(Territory t, int weight) {
        connections.put(t, weight);
    }

    @Override
    public String toString() {
        return name + "(" + defenders + ")";
    }

    @Override
    public int compareTo(Territory o) {
        // for PQ: first by cost, then by hops
        if (this.cost != o.cost) return Integer.compare(this.cost, o.cost);
        return Integer.compare(this.pathLength, o.pathLength);
    }
}
