package state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import backtype.storm.tuple.Values;

import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.planner.Node;
import storm.trident.state.State;

public class StoppedCarDB extends TridentState implements State {
	
	protected StoppedCarDB(TridentTopology topology, Node node) {
		super(topology, node);
		// TODO Auto-generated constructor stub
	}

	Map<String, StoppedCar> stoppedCars = new HashMap<String, StoppedCar>();

	@Override
	public void beginCommit(Long txid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit(Long txid) {
		// TODO Auto-generated method stub
		
	}
	
	public void setStoppedCarBulk(List<StoppedCar> stoppedCars) {
		for (int i = 0; i < stoppedCars.size(); i++) {
			this.stoppedCars.put(
					new StoppedCarKey(stoppedCars.get(i).xway, stoppedCars.get(i).pos,
							stoppedCars.get(i).vid).toString(), stoppedCars.get(i));
		}
		
	}
	
	public List<StoppedCar> getStoppedCarBulk(List<StoppedCarKey> stoppedCarKeys) {
		List<StoppedCar> ret = new ArrayList<StoppedCar>();
		for (StoppedCarKey stoppedCarKey: stoppedCarKeys) {
			ret.add(stoppedCars.get(stoppedCarKey.toString()));
		}
		
		return ret;
	}
	
	public void removeStoppedCarBulk(List<StoppedCarKey> stoppedCarKeys) {
		for (StoppedCarKey stoppedCarKey: stoppedCarKeys) {
			stoppedCars.remove(stoppedCarKey.toString());
		}
	}
	
}
