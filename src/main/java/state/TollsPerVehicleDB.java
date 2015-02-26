package state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.planner.Node;
import storm.trident.state.State;

public class TollsPerVehicleDB extends TridentState implements State {
	
	Map<String, TollsPerVehicle> tollsPerVehicles = new HashMap<String, TollsPerVehicle>();
	
	protected TollsPerVehicleDB(TridentTopology topology, Node node) {
		super(topology, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beginCommit(Long txid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit(Long txid) {
		// TODO Auto-generated method stub
		
	}
	
	public void setTollsPerVehiclesBulk(List<TollsPerVehicle> tollsPerVehicles) {
		for (TollsPerVehicle tollsPerVehicle : tollsPerVehicles) {
			int vid = tollsPerVehicle.vid;
			int xway = tollsPerVehicle.xway;
			int tollday = tollsPerVehicle.tollday;
			TollsPerVehicleKey tollsPerVehicleKey = new TollsPerVehicleKey(vid, xway, tollday);
			this.tollsPerVehicles.put(tollsPerVehicleKey.toString(), tollsPerVehicle);
		}
	}
	
//	public List<TollsPerVehicle> getTollsBulk(List<Long> vids) {
//		List<TollsPerVehicle> ret = new ArrayList<TollsPerVehicle>();
//		for (Long vid : vids) {
//			ret.add(tollsPerVehicles.get(vid));
//		}
//		return ret;
//	}
//	
	public List<TollsPerVehicle> getTollsPerVehicleBulk(List<TollsPerVehicleKey> tollsPerVehicleKeys) {
		List<TollsPerVehicle> ret = new ArrayList<TollsPerVehicle>();
		for (TollsPerVehicleKey tollsPerVehicleKey : tollsPerVehicleKeys) {
			ret.add(tollsPerVehicles.get(tollsPerVehicleKey.toString()));
		}
		return ret;
	}
	
	public void updateTollsPerVehicleBulk(List<TollsPerVehicleKey> tollsPerVehicleKeys, List<Integer> tolls) {
		List<TollsPerVehicle> updateList = new ArrayList<TollsPerVehicle>();
		for (int i = 0; i < tollsPerVehicleKeys.size(); i++) {
			for (int toll : tolls) {
				TollsPerVehicle tollsPerVehicle = new TollsPerVehicle(tollsPerVehicleKeys.get(i).vid,
																tollsPerVehicleKeys.get(i).xway,
																tollsPerVehicleKeys.get(i).tollday,
																toll);
				updateList.add(tollsPerVehicle);
			}
		}
		setTollsPerVehiclesBulk(updateList);
	}
	
//	public Long sumTolls(List<Long> vids) {
//		Long ret = (long) 0;
//		List<TollsPerVehicle> tolls = getTollsBulk(vids);
//		for (TollsPerVehicle toll : tolls) {
//			ret += toll.toll;
//		}
//		return ret;
//	}
	
	// TODO: get tolls by given xway, vid and tollday
	public Long getTolls(int xway, long vid, int tollday) {
		Long tolls = (long) 0;
		return tolls;
	}

}
