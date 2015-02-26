package state;

public class StoppedCarKey {
	public int xway;
	public int pos;
	public int vid;
	
	public StoppedCarKey(int xway, int pos, int vid) {
		this.xway = xway;
		this.pos = pos;
		this.vid = vid;
	}
	
	public String toString() {
		return String.valueOf(this.xway) + " " +
				String.valueOf(this.pos) + " " +
				String.valueOf(this.vid);
	}
}
