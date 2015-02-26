package state;

public class CurrPositionKey {
	public Integer xway;
	public Integer vid;
	
	public CurrPositionKey(Integer xway, Integer vid) {
		this.xway = xway;
		this.vid = vid;
	}
	
	public String toString() {
		return String.valueOf(this.xway) + " " +
				String.valueOf(this.vid);
	}
}
