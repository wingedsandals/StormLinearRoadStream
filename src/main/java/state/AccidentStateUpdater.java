package state;

import java.util.ArrayList;
import java.util.List;

import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseStateUpdater;
import storm.trident.state.State;
import storm.trident.tuple.TridentTuple;

public class AccidentStateUpdater extends BaseStateUpdater {

	@Override
	public void updateState(State accidentState, List tuples, TridentCollector collector) {
		List<Accident> accidents = new ArrayList<Accident>();
		for (Object t : tuples) {
			TridentTuple tt = (TridentTuple)t;
			int xway = tt.getInteger(0);
			int dir = tt.getInteger(1);
			int seg = tt.getInteger(2);
			int pos = tt.getInteger(3);
			int started_tod = tt.getInteger(4);
			int ended_tod = tt.getInteger(5);
			Accident accident = new Accident(xway, dir, seg, pos, started_tod, ended_tod);
			accidents.add(accident);
		}
		
		AccidentDB accidentDB = (AccidentDB) accidentState;
		accidentDB.setAccidentBulk(accidents);
	}

}
