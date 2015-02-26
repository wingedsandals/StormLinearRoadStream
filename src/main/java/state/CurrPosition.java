package state;

import java.io.Serializable;

/**
 * Created by jdu on 2/12/15.
 */
public class CurrPosition implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1409337187611088655L;

    public int xway;
    public int vid;
    public int seg;
    public int dir;
    public int lane;
    public int pos;
    public int speed;
    public int ts;
    public int tod;
    public int count_at_pos;
    public int toll_to_charge;

    public CurrPosition(int xway, int vid, int seg, int dir, int lane, int pos,
    				int speed, int ts, int tod, int count_at_pos, int toll_to_charge) {
        this.xway = xway;
        this.vid = vid;
        this.seg = seg;
        this.dir = dir;
        this.lane = lane;
        this.pos = pos;
        this.speed = speed;
        this.ts = ts;
        this.tod = tod;
        this.count_at_pos = count_at_pos;
        this.toll_to_charge = toll_to_charge;
    }
    
//    private void writeObject(ObjectOutputStream s) throws IOException {
//        s.writeLong(this.voteId);
//        s.writeInt(this.contestantNumber);
//        s.writeLong(this.phoneNumber);
//        s.writeInt(this.timestamp);
//        s.writeLong(this.txnStartTime);
//    }
//
//    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
//        this.voteId = s.readLong();
//        this.contestantNumber = s.readInt();
//        this.phoneNumber = s.readLong();
//        this.timestamp = s.readInt();
//        this.txnStartTime = s.readLong();
//    }
//
//    public void debug() {
//        System.out.println("call : " + this.voteId + "-" + this.phoneNumber + "-" + this.contestantNumber + "-" + this.timestamp);
//    }
//
//    public String getString()
//    {
//        return "" + this.voteId + " " + this.phoneNumber + " " + this.contestantNumber + " " + this.timestamp  + " " + txnStartTime + "\n";
//    }

    public String toString()
    {
        return "" + this.xway + " " +
        this.vid + " " +
        this.seg + " " +
        this.dir + " " +
        this.lane + " " +
        this.pos + " " +
        this.speed + " " +
        this.ts + " " +
        this.tod + " " +
        this.count_at_pos + " " +
        this.toll_to_charge;
    }

}

