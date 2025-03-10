package auto;

import java.util.HashMap;

public class TNode {
	String value;
	boolean isKey;
	HashMap<String, TNode> next;
	
	public TNode(String value) {
		this.value = value;
		next = new HashMap<String, TNode>();
	}
	
	public String toString() {
		
	}
	
	public String returnAllKids() {
		return
	}
}
