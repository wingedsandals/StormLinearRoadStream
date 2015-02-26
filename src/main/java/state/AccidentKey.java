package state;

public class AccidentKey {
	public Integer xway;
	public Integer seg;
	public Integer dir;
	public Integer pos;
	
	public AccidentKey(Integer xway, Integer seg, Integer dir, Integer pos) {
		this.xway = xway;
		this.seg = seg;
		this.dir = dir;
		this.pos = pos;
	}
	
	public String toString() {
		return String.valueOf(this.xway) + " " +
				String.valueOf(this.seg) +  " " +
				String.valueOf(this.dir) + " " +
				String.valueOf(this.pos);
	}
}
