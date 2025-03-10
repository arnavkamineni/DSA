package Exercises;

import hardExercise.Node;

public class Ex {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void swap(Node A) {
		Node temp = A.next;
		A.next = A.next.next;
		temp.next = temp.next.next;
		A.next.next = temp;
	}

}
