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

public class CurrSegStatDB extends TridentState implements State {
	
	protected CurrSegStatDB(TridentTopology topology, Node node) {
		super(topology, node);
		// TODO Auto-generated constructor stub
	}

	Map<String, CurrSegStat> currSegStats = new HashMap<String, CurrSegStat>();

	@Override
	public void beginCommit(Long txid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit(Long txid) {
		// TODO Auto-generated method stub
		
	}
	
	public void setCurrSegStatBulk(List<CurrSegStat> currSegStats) {
		for (int i = 0; i < currSegStats.size(); i++) {
			this.currSegStats.put(
					new CurrSegStatKey(currSegStats.get(i).xway, currSegStats.get(i).tod,
							currSegStats.get(i).seg, currSegStats.get(i).dir).toString(), 
						currSegStats.get(i));
		}
		
	}
	
	public List<CurrSegStat> getCurrSegStatBulk(List<CurrSegStatKey> currSegStatKeys) {
		List<CurrSegStat> ret = new ArrayList<CurrSegStat>();
		for (CurrSegStatKey currSegStatKey: currSegStatKeys) {
			ret.add(currSegStats.get(currSegStatKey.toString()));
		}
		
		return ret;
	}
	
}
