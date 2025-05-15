package risk;

public class Edge {
    public final String u, v;
    public final int weight;
    public Edge(String u, String v, int weight) {
        this.u = u; this.v = v; this.weight = weight;
    }
    @Override
    public String toString() {
        return u + "—" + v + " (" + weight + ")";
    }
}
