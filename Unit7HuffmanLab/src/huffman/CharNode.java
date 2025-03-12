package huffman;

public class CharNode implements Comparable {
	int freq;
	CharNode left, right;
	
	public CharNode(int f, CharNode l, CharNode r) {
		freq = f;
		left = l;
		right = r;
	}
	
	
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
