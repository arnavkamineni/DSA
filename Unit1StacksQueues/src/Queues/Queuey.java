package Queues;

import java.util.Queue;
import java.util.Stack;
import java.util.Arrays;
import java.util.LinkedList;

public class Queuey {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		int[] input = {13, 4, 21, 5, 7};
//		System.out.println(Arrays.toString(stacky(input)));
		Queue<Integer> que = new LinkedList<Integer>();
		que.add(5); que.add(7); que.add(6); que.add(12);    
		System.out.println(que);
		System.out.println(que.remove());
		System.out.println(que);
		mirror(que);
		System.out.println(que);
	}

	private static void stutter(Queue<Integer> q) {
		//to be implemented
	}
	
	private static void mirror(Queue<Integer> q) {
		Stack<Integer> tempStack = new Stack<Integer>();
		for (int i:q) {
			tempStack.push(i);
		}
		while (!tempStack.isEmpty()) {
			q.add(tempStack.pop());
		}
	}

}
