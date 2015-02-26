package state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backtype.storm.tuple.Values;

import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.planner.Node;
import storm.trident.state.State;

public class AccidentDB extends TridentState implements State {
	
	protected AccidentDB(TridentTopology topology, Node node) {
		super(topology, node);
		// TODO Auto-generated constructor stub
	}

	Map<String, Accident> accidents = new HashMap<String, Accident>();

	@Override
	public void beginCommit(Long txid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit(Long txid) {
		// TODO Auto-generated method stub
		
	}
	
	public void setAccidentBulk(List<Accident> accidents) {
		for (int i = 0; i < accidents.size(); i++) {
			this.accidents.put(
					new AccidentKey(accidents.get(i).xway, accidents.get(i).seg, accidents.get(i).dir, accidents.get(i).pos).toString(), 
					accidents.get(i));
		}
		
	}
	
	public void updateAccidentBulk(List<AccidentKey> accidentKeys, Integer ended_tod) {
		List<Accident> currAccidents = getAccidentBulk(accidentKeys);
		for (Accident a : currAccidents) {
			a.ended_tod = ended_tod;
		}
	}
	
	public List<Accident> getAccidentBulk(List<AccidentKey> accidentKeys) {
		List<Accident> ret = new ArrayList<Accident>();
		for (AccidentKey accidentKey: accidentKeys) {
			ret.add(accidents.get(accidentKey.toString()));
		}
		
		return ret;
	}
	
	public Integer getAccidentCount() {
		return accidents.size();
	}
	
	// TODO: Remove accidents by xway
	public void clearAccidents(int xway) {
		
	}
	
	// TODO: "SELECT dir, seg FROM accidents WHERE xway = ?"
	public List<Values> getAccidents(int xway) {
		List<Values> ret = new ArrayList<Values>();
		
		return ret;
	}
	
}
