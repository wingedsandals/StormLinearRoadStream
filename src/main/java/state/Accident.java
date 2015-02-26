package state;

import java.io.Serializable;

/**
 * Created by jdu on 2/12/15.
 */
public class Accident implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1409337187611088655L;

    public int xway;
    public int dir;
    public int seg;
    public int pos;
    public int started_tod;
    public int ended_tod;

    public Accident(int xway, int dir, int seg, int pos, int started_tod, int ended_tod) {
        this.xway = xway;
        this.dir = dir;
        this.seg = seg;
        this.pos = pos;
        this.started_tod = started_tod;
        this.ended_tod = ended_tod;
    }
    
    public String toString()
    {
        return "" + this.xway + " " +
        this.dir + " " +
        this.seg + " " +
        this.pos + " " +
        this.started_tod + " " +
        this.ended_tod;
    }

}

