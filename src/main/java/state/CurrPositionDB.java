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

public class CurrPositionDB extends TridentState implements State {
	
	protected CurrPositionDB(TridentTopology topology, Node node) {
		super(topology, node);
		// TODO Auto-generated constructor stub
	}

	Map<String, CurrPosition> currPositions = new HashMap<String, CurrPosition>();

	@Override
	public void beginCommit(Long txid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit(Long txid) {
		// TODO Auto-generated method stub
		
	}
	
	public void setXwayBulk(List<CurrPosition> CurrPositions) {
		for (int i = 0; i < currPositions.size(); i++) {
			this.currPositions.put(
					new CurrPositionKey(currPositions.get(i).xway, currPositions.get(i).vid).toString(), 
					currPositions.get(i));
		}
		
	}
	
	public List<CurrPosition> getXwayBulk(List<CurrPositionKey> currPositionKeys) {
		List<CurrPosition> ret = new ArrayList<CurrPosition>();
		for (CurrPositionKey currPositionKey: currPositionKeys) {
			ret.add(currPositions.get(currPositionKey.toString()));
		}
		
		return ret;
	}
	
	
	// TODO: SELECT seg FROM cur_position WHERE part_id = ? AND xway = ? AND dir = ? AND seg >= ? AND seg <= ? AND started_tod <= ? AND ended_tod >= ?
	public List<CurrPosition> getLastMinAccidents(List<Integer> xways, List<Integer> dirs,
			List<Integer> started_segs, List<Integer> ended_segs, 
			List<Integer> started_tods, List<Integer> ended_tods) {
		List<CurrPosition> ret = new ArrayList<CurrPosition>();
		
		return ret;
	}
//	// Linear search - bad! We should come with better solutions. MemcachedState maybe?
//	public void removeXwayBulk(List<Integer> xways, List<Long> tod) {
//		Iterator<Map.Entry<String,Position>> iter = positions.entrySet().iterator();
//		while (iter.hasNext()) {
//		    Map.Entry<String,Position> entry = iter.next();
//		    if(xways.contains(entry.getKey()) && 
//		    		entry.getValue().tod < tod.get(xways.indexOf(entry.getValue().xway))){
//		        iter.remove();
//		    }
//		}		
//	}
	
}
