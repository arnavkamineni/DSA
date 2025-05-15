// RiskDriver.java
package risk;

import java.io.IOException;
import java.util.*;

public class RiskDriver {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        Risk risk = new Risk();

        String edgesFile = "terrain.csv";
        risk.loadEdgeLengths(edgesFile);

        String defFile = "soldiers.csv";
        risk.loadVertexWeights(defFile);
//        System.out.println(risk.territories);
        System.out.print("From territory: ");
        String from = in.nextLine().trim();
        System.out.print("To territory: ");
        String to   = in.nextLine().trim();
        System.out.print("Soldiers available: ");
        int soldiers = Integer.parseInt(in.nextLine().trim());

        PathResult pr = risk.djikstra(from, to, soldiers);
        if (pr.isSuccess()) {
            System.out.println("Path found: " + pr.getPath());
            System.out.println("Total casualties: " + pr.getCost());
            
        } else {
            System.out.println("Cannot reach target with any remaining soldiers.");
            System.out.println("Furthest path: " + pr.getPath());
            System.out.println("Casualties so far: " + pr.getCost());
            System.out.println("Territories conquered before running out: "
                + pr.getPath().size() + " (" + (pr.getPath().size()-1) + " moves)");
        }

        System.out.println("\nComputing MST over static edges...");
        MSTResult mst = risk.primMST();
        System.out.println("MST total weight: " + mst.getTotalWeight());
        for (Edge e : mst.getEdges()) {
            System.out.println(e);
        }
    }
}
