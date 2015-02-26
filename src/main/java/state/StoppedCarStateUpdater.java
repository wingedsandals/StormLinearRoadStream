package state;

import java.util.ArrayList;
import java.util.List;

import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseStateUpdater;
import storm.trident.state.State;
import storm.trident.tuple.TridentTuple;

public class StoppedCarStateUpdater extends BaseStateUpdater{

	@Override
	public void updateState(State stoppedCarState, List tuples, TridentCollector collector) {
		List<StoppedCar> stoppedCars = new ArrayList<StoppedCar>();
		
		for (Object t : tuples) {
			TridentTuple tuple = (TridentTuple)t;

	        int xway = tuple.getInteger(0);
	        int pos = tuple.getInteger(1);
	        int vid = tuple.getInteger(2);
	        int seg = tuple.getInteger(3);
	        int dir = tuple.getInteger(4);
	        int latest_tod = tuple.getInteger(5);
			
			StoppedCar stoppedCar = new StoppedCar(xway, pos, vid, seg, dir, latest_tod);
			stoppedCars.add(stoppedCar);
		}
		
		StoppedCarDB stoppedCarDB = (StoppedCarDB) stoppedCarState;
		stoppedCarDB.setStoppedCarBulk(stoppedCars);
	}


}
