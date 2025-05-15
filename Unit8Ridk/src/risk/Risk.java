// Risk.java
package risk;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Risk {
    final Map<String, Territory> territories = new HashMap<>();
    final List<Edge> staticEdges = new ArrayList<>();

    public void loadEdgeLengths(String csvFile) throws IOException {
    	for (String line : Files.readAllLines(Paths.get(csvFile))) {
//            System.out.println(line);
    		line = line.trim();
            if (line.isEmpty()) continue;
            line = line.replace("\uFEFF", ""); // very annoying invisible character >:( - likely the case with many labs if the MST returns 132 for total length and there is an issue with Afghanistan
            String[] p = line.split(";");
            
            if (p.length < 3) continue;
            String u = p[0].trim(), v = p[1].trim();
            int w = Integer.parseInt(p[2].trim());
            staticEdges.add(new Edge(u, v, w));
            territories.putIfAbsent(u, new Territory(u));
            territories.putIfAbsent(v, new Territory(v));
//            System.out.println(Arrays.toString(p));
        }
    }

    public void loadVertexWeights(String csvFile) throws IOException {
        // read and parse each line
        List<String> lines = Files.readAllLines(Paths.get(csvFile));
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            line = line.replace("\uFEFF", "");
            String[] p = line.split(";");
            
            String name = p[0];
            int def;
            // input checker; used to find the issue with Afghanistan
            try {
                def = Integer.parseInt(p[1]);
            } catch (NumberFormatException ex) {
                System.err.println("Invalid defender count on line: " + line);
                continue;
            }
            
            // input checker; used to find the issue with Afghanistan
            Territory t = territories.get(name);
            if (t == null) {
                System.err.println("Unknown territory in defenders CSV: " + name);
            } else {
                t.defenders = def;
            }
        }
        
        for (Territory t : territories.values()) {
            t.connections.clear();
        }
        for (Edge e : staticEdges) {
            Territory a = territories.get(e.u);
            Territory b = territories.get(e.v);
            if (a != null && b != null) {
                a.addConnection(b, b.defenders);
                b.addConnection(a, a.defenders);
            }
        }
    }

    public PathResult djikstra(String startName, String endName, int soldiers) {
        Territory start = territories.get(startName),
                  end   = territories.get(endName);
        if (start == null || end == null) 
            return new PathResult(Collections.emptyList(), 0, false);

        // init all distances
        for (Territory t : territories.values()) {
            t.cost = Integer.MAX_VALUE;
            t.pathLength = Integer.MAX_VALUE;
            t.prev = null;
        }

        // don't include its own defenders in the cost
        start.cost = 0;
        start.pathLength = 0;

        PriorityQueue<Territory> pq = new PriorityQueue<>(territories.values());
        Set<Territory> done = new HashSet<>();

        // Main loop
        while (!pq.isEmpty()) {
            Territory u = pq.poll();
            if (!done.add(u)) continue;
            if (u == end) break;

            for (Map.Entry<Territory,Integer> e : u.connections.entrySet()) {
                Territory v = e.getKey();
                int weight = v.defenders;         // cost to conquer v
                int altCost = u.cost + weight;
                int altHops = u.pathLength + 1;

                if (altCost < v.cost 
                 || (altCost == v.cost && altHops < v.pathLength)) {
                    // relax
                    pq.remove(v);
                    v.cost = altCost;
                    v.pathLength = altHops;
                    v.prev = u;
                    pq.offer(v);
                }
            }
        }

        // reconstruct the full path from end to start
        List<Territory> full = new ArrayList<>();
        for (Territory cur = end; cur != null; cur = cur.prev) {
            full.add(cur);
        }
        Collections.reverse(full);

        // if we can afford the entire path, return success
        if (end.cost <= soldiers) {
            return new PathResult(full, end.cost, true);
        }

        // otherwise, build the furthest prefix we can pay for
        int cum = -1*start.defenders;
        for (int i = 0; i < full.size(); i++) {
            cum += full.get(i).defenders;
            if (cum > soldiers) {
                // we ran out at territory full.get(i)
                List<Territory> prefix = full.subList(0, i);
                int paid = cum - full.get(i).defenders;
                return new PathResult(prefix, paid, false);
            }
        }

        // fallback (should not happen)
        return new PathResult(Collections.singletonList(start), start.defenders, false);
    }


    // Prims MST over the original static edge lengths
    public MSTResult primMST() {
        Map<String,List<Edge>> adj = new HashMap<>();
        for (Edge e : staticEdges) {
            adj.computeIfAbsent(e.u, k->new ArrayList<>()).add(e);
            adj.computeIfAbsent(e.v, k->new ArrayList<>()).add(new Edge(e.v, e.u, e.weight));
        }

        Set<String> in = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(x->x.weight));
        List<Edge> tree = new ArrayList<>();
        int total = 0;

        String start = territories.keySet().iterator().next();
        in.add(start);
        pq.addAll(adj.getOrDefault(start, Collections.emptyList()));

        while (!pq.isEmpty() && in.size() < territories.size()) {
            Edge e = pq.poll();
            if (in.contains(e.v)) continue;
            in.add(e.v);
            tree.add(e);
            total += e.weight;
            for (Edge ne : adj.getOrDefault(e.v, Collections.emptyList())) {
                if (!in.contains(ne.v)) pq.offer(ne);
            }
        }
        return new MSTResult(tree, total);
    }
}
