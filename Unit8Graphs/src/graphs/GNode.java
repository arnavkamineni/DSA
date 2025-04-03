package graphs;

public class GNode {
   
	String name;
    int ID;
    public GNode(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    
    public String toString() {
        return "GNode{" +name+"-"+ID+"}";
    }
}
