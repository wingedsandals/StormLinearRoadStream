package state;

public class CurrSegStatKey {
	public int xway;
	public int tod;
	public int seg;
	public int dir;
	
	public CurrSegStatKey(int xway, int tod, int seg, int dir) {
		this.xway = xway;
		this.tod = tod;
		this.seg = seg;
		this.dir = dir;
	}
	
	public String toString() {
		return String.valueOf(this.xway) + " " +
				String.valueOf(this.tod) + " " +
				String.valueOf(this.seg) + " " +
				String.valueOf(this.dir);
	}
}
