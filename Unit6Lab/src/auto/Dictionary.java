package auto;

import java.util.HashMap;

public class Dictionary {
	TNode root;
	
	public Dictionary() {
		String value = "abcdefghijklmnopqrstuvwxyz";
		value += value.toUpperCase();
		root = new TNode(value);
	}
	
	public void addKey(String s) {
		String start = s.substring(0, 1);
		if (root.next.get(start) != null)
			addKey(s.substring(1), root.next.get(start), start, root.next);
		else {
			root.next.put(start, new TNode(""));
			addKey(s.substring(1), root.next.get(start), start, root.next);
		}
	}
	
	private void addKey(String s, TNode node, String prevKey, HashMap<String, TNode> next) {
		if (s.length() == 0) {
			TNode temp = new TNode("");
			next.put(prevKey, temp);
			temp.isKey = true;
		}
		String start = s.substring(0, 1);

		if (node.value.equals("") && s.length() != 0) {
			boolean isKey = node.isKey;
			TNode val = new TNode(start);
			val.isKey = isKey;
			next.put(prevKey, val);
		}
	}
	
	
}
