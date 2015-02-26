package state;

import java.util.ArrayList;
import java.util.List;

import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseStateUpdater;
import storm.trident.state.State;
import storm.trident.tuple.TridentTuple;

public class CurrPositionStateUpdater extends BaseStateUpdater{

	@Override
	public void updateState(State currPositionState, List tuples, TridentCollector collector) {
		List<CurrPosition> currPositions = new ArrayList<CurrPosition>();
		
		for (Object t : tuples) {
			TridentTuple tuple = (TridentTuple)t;

	        int xway = tuple.getInteger(0);
	        int vid = tuple.getInteger(1);
	        int seg = tuple.getInteger(2);
	        int dir = tuple.getInteger(3);
	        int lane = tuple.getInteger(4);
	        int pos = tuple.getInteger(5);
	        int speed = tuple.getInteger(6);
	        int ts = tuple.getInteger(7);
	        int tod = tuple.getInteger(8);
	        int count_at_pos = tuple.getInteger(9);;
	        int toll_to_charge = tuple.getInteger(10);
			
			CurrPosition cp = new CurrPosition(xway, vid, seg, dir, lane, pos, speed,
										ts, tod, count_at_pos, toll_to_charge);
			currPositions.add(cp);
		}
		
		CurrPositionDB currPositionDB = (CurrPositionDB) currPositionState;
		currPositionDB.setXwayBulk(currPositions);
	}


}
