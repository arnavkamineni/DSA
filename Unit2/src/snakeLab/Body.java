package snakeLab;

public class Body {
	int x, y;
	Body next;
	
	public Body(int x, int y, Body next) {
		super();
		this.x = x;
		this.y = y;
		this.next = next;
	}

	public Body(int row, int col) {
		// TODO Auto-generated constructor stub
		this.y = row;
		this.x = col;

	}

	public Body getNext() {
		return next;
	}

	public void setNext(Body next) {
		this.next = next;
	}
	
	

}
