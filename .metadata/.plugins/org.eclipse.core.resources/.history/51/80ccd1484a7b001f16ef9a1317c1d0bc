package linkyListy;

public class Track {

	String title;
	String artist;
	int duration;
	Track next;
	
	public Track(String title, String artist, int duration, Track next) {
		super();
		this.title = title;
		this.artist = artist;
		this.duration = duration;
		this.next = next;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Track getNext() {
		return next;
	}

	public void setNext(Track next) {
		this.next = next;
	}
	
	public boolean equals(Object obj) {
		if(!(obj instanceof Track))
			return false;
		Track t = (Track)obj;
		return(title.equals(t.title)&&artist.equals(t.artist)&&duration==t.duration);
	}
}
