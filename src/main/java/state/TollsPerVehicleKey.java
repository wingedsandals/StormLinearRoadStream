package state;

public class TollsPerVehicleKey {
	public Integer vid;
	public Integer xway;
	public Integer tollday;
	
	public TollsPerVehicleKey(Integer vid, Integer xway, Integer tollday) {
		this.vid = vid;
		this.xway = xway;
		this.tollday = tollday;
	}
	
	public String toString() {
		return String.valueOf(this.vid) + " " +
				String.valueOf(this.xway) + " " +
				String.valueOf(this.tollday);
	}
}
