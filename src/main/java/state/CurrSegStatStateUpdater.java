package state;

import java.util.ArrayList;
import java.util.List;

import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseStateUpdater;
import storm.trident.state.State;
import storm.trident.tuple.TridentTuple;

public class CurrSegStatStateUpdater extends BaseStateUpdater{

	@Override
	public void updateState(State currSegStatState, List tuples, TridentCollector collector) {
		List<CurrSegStat> currSegStats = new ArrayList<CurrSegStat>();
		
		for (Object t : tuples) {
			TridentTuple tuple = (TridentTuple)t;

	        int xway = tuple.getInteger(0);
	        int tod = tuple.getInteger(1);
	        int seg = tuple.getInteger(2);
	        int dir = tuple.getInteger(3);
	        int num_cars = tuple.getInteger(4);
	        int total_speed = tuple.getInteger(5);
	        int toll = tuple.getInteger(6);
			
			CurrSegStat currSegStat = new CurrSegStat(xway, tod, seg, dir, num_cars,
										total_speed, toll);
			currSegStats.add(currSegStat);
		}
		
		CurrSegStatDB currSegStatDB = (CurrSegStatDB) currSegStatState;
		currSegStatDB.setCurrSegStatBulk(currSegStats);
	}


}
