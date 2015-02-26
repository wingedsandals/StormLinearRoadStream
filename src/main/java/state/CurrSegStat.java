package state;

import java.io.Serializable;

/**
 * Created by jdu on 2/12/15.
 */
public class CurrSegStat implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1409337187611088655L;

    public int xway;
    public int tod;
    public int seg;
    public int dir;
    public int num_cars;
    public int total_speed;
    public int toll;

    public CurrSegStat(int xway, int tod, int seg, int dir, int num_cars,
    				int total_speed, int toll) {
        this.xway = xway;
        this.tod = tod;
        this.seg = seg;
        this.dir = dir;
        this.num_cars = num_cars;
        this.total_speed = total_speed;
        this.toll = toll;
    }
    

    public String toString()
    {
        return "" + this.xway + " " +
        this.tod + " " +
        this.seg + " " +
        this.dir + " " +
        this.num_cars + " " +
        this.total_speed + " " +
        this.toll;
    }

}

