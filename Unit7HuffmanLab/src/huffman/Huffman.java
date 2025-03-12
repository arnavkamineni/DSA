package huffman;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {
	HashMap<Character, String> encode;
	HashMap<String, Character> decode;
	PriorityQueue<CharNode> pq;
	
	public Huffman(String text) {
		initFreq(text);
	}
	
	private void initFreq(String text) {
		HashMap<Character, Integer> freqs = new HashMap<Character, Integer>();
		for (char c : text.toCharArray()) {
			freqs.putIfAbsent(c, 0);
			freqs.put(c, freqs.get(c)+1);
		}
		System.out.println(freqs);
		for (Character c: )
	}
}
