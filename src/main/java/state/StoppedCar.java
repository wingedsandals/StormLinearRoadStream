package state;

import java.io.Serializable;

/**
 * Created by jdu on 2/12/15.
 */
public class StoppedCar implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1409337187611088655L;

	public int xway;
    public int pos;
    public int vid;
    public int seg;
    public int dir;
    public int latest_tod;

    public StoppedCar(int xway, int pos, int vid, int seg, int dir, int latest_tod) {
        this.xway = xway;
        this.pos = pos;
        this.vid = vid;
        this.seg = seg;
        this.dir = dir;
        this.latest_tod = latest_tod;
    }
    

    public String toString()
    {
        return "" + this.xway + " " +
        this.pos + " " +
        this.vid + " " +
        this.seg + " " +
        this.dir + " " +
        this.latest_tod;
    }

}

