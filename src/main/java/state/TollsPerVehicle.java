package state;

import java.io.Serializable;

/**
 * Created by jdu on 2/12/15.
 */
public class TollsPerVehicle implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1409337187611088655L;

	int vid;
	int xway;
	int tollday;
	int toll;

    public TollsPerVehicle(int vid, int xway, int tollday, int toll) {
    	this.vid = vid;
        this.xway = xway;
    	this.tollday = tollday;
        this.toll = toll;
    }

    public String toString()
    {
        return "" + this.vid + " " + this.xway + " " + this.tollday + " " + this.toll;
    }

}

