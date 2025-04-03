package graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Cottage {
	 HashMap<GNode, HashSet<GNode>> hiho;

	    public Cottage() {
	        this.hiho = new HashMap<>();
	    }

	    public boolean addGNode(GNode gnome, HashSet<GNode> buddies, boolean isUndirected) {
	        /*
	         * dumb way
	         * for (GNode buddy : buddies) {
	         * if(!hiho.containsKey(buddy))
	         * return false;
	         * }
	         */
	        if (buddies == null) 
	            buddies = new HashSet<GNode>();
	        if (gnome == null)
	            return false;
	        buddies.remove(gnome);
	        if (!hiho.keySet().containsAll(buddies))
	            return false;

	        hiho.put(gnome, buddies);

	        if (isUndirected) {
	            for (GNode buddy : buddies)
	                hiho.get(buddy).add(gnome);
	            return true;
	        }
	        return true;
	    }

	    public boolean addEdge(GNode gnome, HashSet<GNode> buddies, boolean isUndirected) {
	        if (gnome == null || buddies == null)
	            return false;
	        if (hiho.containsKey(gnome)) {
	            for (GNode buddy : buddies) {
	                if (hiho.containsKey(buddy)) {
	                    hiho.get(gnome).add(buddy);
	                    if (isUndirected)
	                        hiho.get(buddy).add(gnome);
	                }
	            }
	            return true;
	        }
	        return false;
	    }
	 
	    public boolean removeGNode(GNode gnome) {
	        for(HashSet<GNode> buddies: hiho.values())
	            buddies.remove(gnome);
	        hiho.keySet().remove(gnome);
	        return true;
	    }

	    public String toString() {
	        String output = "";
	        for (GNode gnome : hiho.keySet())
	            output += gnome + "-->" + hiho.get(gnome) + "\n";
	        return output;
	    }
	    
	    public HashSet<GNode> neighbors(GNode gnome){
	    	return gnome!=null&&hiho.containsKey(gnome)?hiho.get(gnome):null;
	    }
	    
	    /**
	     * Returns true if successful
	     * @param gnome1
	     * @param gnome2
	     * @param isMutual
	     * @return boolean
	     */
	    public boolean breakup(GNode gnome1, GNode gnome2, boolean isMutual) {
	    	if(gnome1==null||gnome2==null||!hiho.containsKey(gnome1)||!hiho.containsKey(gnome2))
	    		return false;
	    	if(isMutual) 
	    		return hiho.get(gnome1).remove(gnome2) || hiho.get(gnome2).remove(gnome1);
	    	return hiho.get(gnome1).remove(gnome2);
	    }
	    
	    public HashSet<GNode> DFT(GNode gnome){
	    	if(gnome==null||!hiho.containsKey(gnome))
	    		return null;
	    	HashSet<GNode> visited = new HashSet<GNode>();
	    	Stack<GNode> toVisit = new Stack<GNode>();
	    	toVisit.push(gnome);
	    	while(!toVisit.isEmpty()) {
	    		GNode current = toVisit.pop();
	    		
	    		if((visited.add(current))) {
	    			System.out.println(current);
	    		}
	    		for(GNode g:hiho.get(current)) 
	    			if(!visited.contains(g))
	    				toVisit.push(g);
	    	}
	    	return visited;
	    }
	    
	    public HashSet<GNode> BFS(GNode gnome) {
	    	if(gnome==null||!hiho.containsKey(gnome))
	    		return null;
	    	HashSet<GNode> visited = new HashSet<GNode>();
	    	Queue<GNode> toVisit = new LinkedList<GNode>();
	    	toVisit.offer(gnome);
	    	while(!toVisit.isEmpty()) {
	    		GNode current = toVisit.poll();
	    		
	    		if((visited.add(current))) {
	    			System.out.println(current);
	    		}
	    		for(GNode g:hiho.get(current)) 
	    			if(!visited.contains(g))
	    				toVisit.offer(g);
	    	}
	    	return visited;
	    }
	    
}
