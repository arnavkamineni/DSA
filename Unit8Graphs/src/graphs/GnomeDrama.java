package graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GnomeDrama {
    public static void main(String[] args) {
        Cottage cottage = new Cottage();
        GNode Doc = new GNode("Doc", 0);
        GNode Grumpy = new GNode("Grumpy", 1);
        GNode Sleepy = new GNode("Sleepy", 2);
        GNode Sneezy = new GNode("Sneezy", 3);
        HashSet<GNode> buddies = new HashSet<>(Set.of(Doc, Grumpy, Sleepy, Sneezy));
        cottage.addGNode(Doc, null, false);
        cottage.addGNode(Grumpy, new HashSet<>(Set.of(Doc)), false);
        
        Cottage letters = new Cottage();
        ArrayList<GNode> arr = new ArrayList<GNode>(); 
        arr.add(new GNode("A", 0));
        arr.add(new GNode("B", 1));
        arr.add(new GNode("C", 2));
        arr.add(new GNode("D", 3));
        arr.add(new GNode("E", 4));
        arr.add(new GNode("F", 5));
        HashSet<GNode> ANodes = new HashSet<>(Set.of(arr.get(1), arr.get(2)));
        HashSet<GNode> CNodes = new HashSet<>(Set.of(arr.get(1), arr.get(2), arr.get(4), arr.get(5)));
        HashSet<GNode> ENodes = new HashSet<>(Set.of(arr.get(3)));

        for (GNode g: arr) {
        	letters.addGNode(g, null, true);
        }
        
        letters.addGNode(arr.get(0), ANodes, true);
        letters.addGNode(arr.get(2), CNodes, true);
        letters.addGNode(arr.get(4), ENodes, true);
//        System.out.println(letters.DFT(arr.get(0)));
//        letters.DFT(arr.get(5));
        letters.BFS(arr.get(1));

    }
}
