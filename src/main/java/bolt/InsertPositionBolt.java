package bolt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import spout.AccidentSpout;
import state.Accident;
import state.AccidentDB;
import state.AccidentKey;
import state.AccidentStateUpdater;
import state.CurrPosition;
import state.CurrPositionDB;
import state.CurrPositionKey;
import state.CurrSegStat;
import state.CurrSegStatDB;
import state.CurrSegStatKey;
import state.Position;
import state.PositionDB;
import state.PositionKey;
import state.SegmentHistory;
import state.SegmentHistoryDB;
import state.SegmentHistoryKey;
import state.StoppedCar;
import state.StoppedCarDB;
import state.StoppedCarKey;
import state.Timestamp;
import state.TimestampDB;
import state.TollsPerVehicle;
import state.TollsPerVehicleDB;
import state.TollsPerVehicleKey;
import state.TollsPerVehicleStateUpdater;
import state.Vehicle;
import state.VehicleDB;
import state.VehicleKey;
import state.memcached.MemcachedState;
import storm.trident.Stream;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.Function;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;
import utils.LinearRoadConstants;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class InsertPositionBolt extends BaseFunction implements Function {
	
	TimestampDB timestampState;
	CurrPositionDB currPositionState;
	CurrSegStatDB currSegStatState;
	TollsPerVehicleDB tollsPerVehicleState;
	StoppedCarDB stoppedCarState;
	Stream accidentS;
	AccidentDB accidentState;
	SegmentHistoryDB segmentHistoryState;
	VehicleDB vehicleState;

	public InsertPositionBolt(TridentTopology topology, TimestampDB timestampState, 
			CurrPositionDB currPositionState, CurrSegStatDB currSegStatState, 
			TollsPerVehicleDB tollsPerVehicleState, StoppedCarDB stoppedCarState) {
		this.timestampState = timestampState;
		this.currPositionState = currPositionState;
		this.currSegStatState = currSegStatState;
		this.tollsPerVehicleState = tollsPerVehicleState;
		this.stoppedCarState = stoppedCarState;
	}
	
	public void prepare(Map conf, TridentOperationContext context, OutputCollector collector) {
		// TODO Auto-generated method stub
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
//        long flag = tuple.getLong(0);
        int time = tuple.getInteger(1);
        int vid = tuple.getInteger(2);
        int spd = tuple.getInteger(4);
        int xway = tuple.getInteger(5);
        int lane = tuple.getInteger(6);
        int dir = tuple.getInteger(7);
        int seg = tuple.getInteger(8);
        int pos = tuple.getInteger(9);
        long starttime = tuple.getInteger(10);;
		
    	long auditTime = System.nanoTime();
        int tod = time / 60;

        int addSpd = spd;
    	int addCount = 1, curCount = 1;
    	int evalAccident = 0, todChange = 0, nextToll = 0;
    	int prevSeg = -1, prevDir = -1, prevPos = -1, prevSpeed = -1, prevCarTS = -1, prevCarTOD = -1, prevCount = -1, prevToll = -1;
    	ArrayList<String> outputTuples = new ArrayList<String>();
    	
    	//if not a valid segment
    	if(seg < 0 || seg >= 100 || dir < 0 || dir > 1 || xway > LinearRoadConstants.NUMBER_OF_XWAYS)
    	{
    		String out = "-1," + time + "," + LinearRoadConstants.EMIT_TIME_STR + "," + vid + "," + seg + "," + dir; 
    		outputTuples.add(out);
    		return;
//    		return LinearRoadConstants.getOutputVoltTable(starttime, outputTuples);
    	}
    	
        List<Timestamp> timestamps = 
        		timestampState.getXwayBulk(new ArrayList<Integer>(Arrays.asList(xway)));
        Integer timestampCnt = timestamps.size();
        assert(timestampCnt > 0);      
    	long prevTS = timestamps.get(0).ts;
    	long prevTOD = timestamps.get(0).tod;
    	
    	if(prevTOD != tod)
    		todChange = 1;
    	
        CurrPositionKey currPositionKey = new CurrPositionKey(xway, vid);
        List<CurrPosition> prevPositions =
        		currPositionState.getXwayBulk(new ArrayList<CurrPositionKey>(Arrays.asList(currPositionKey)));
        
    	//if the vehicle is NOT just entering the highway
    	if(lane != 0 && prevPositions.size() > 0) {
    		prevSeg = prevPositions.get(0).seg;
    		prevDir = prevPositions.get(0).dir;
    		prevPos = prevPositions.get(0).pos;
    		prevSpeed = prevPositions.get(0).speed;
    		prevCarTS = prevPositions.get(0).ts;
    		prevCarTOD = (int) prevPositions.get(0).tod;
    		prevCount = prevPositions.get(0).count_at_pos;
    		prevToll = prevPositions.get(0).toll_to_charge;
    	}
    		
    	///////////if we're entering a new segment/////////////////////
    	if(seg != prevSeg) {
    		/////////////////////notifications for tolls and accidents//////////////////////////
    		CurrSegStatKey currSegStatKey = new CurrSegStatKey(xway, tod-1, seg, dir);
    		List<CurrSegStat> lastMinTolls = currSegStatState.getCurrSegStatBulk(
    				new ArrayList<CurrSegStatKey>(Arrays.asList(currSegStatKey)));
			List<CurrPosition> lastMinAccidents = dir == 0 ?
    				currPositionState.getLastMinAccidents(new ArrayList<Integer>(Arrays.asList(xway)),
    						new ArrayList<Integer>(Arrays.asList(dir)), 
    						new ArrayList<Integer>(Arrays.asList(seg)), 
    						new ArrayList<Integer>(Arrays.asList(seg + 4)),
    						new ArrayList<Integer>(Arrays.asList(tod)),
    						new ArrayList<Integer>(Arrays.asList(tod)))
    					:
        			currPositionState.getLastMinAccidents(new ArrayList<Integer>(Arrays.asList(xway)),
        					new ArrayList<Integer>(Arrays.asList(dir)), 
        					new ArrayList<Integer>(Arrays.asList(seg - 4)), 
        					new ArrayList<Integer>(Arrays.asList(seg)),
        					new ArrayList<Integer>(Arrays.asList(tod)),
        					new ArrayList<Integer>(Arrays.asList(tod)));

    		if(lane != LinearRoadConstants.EXIT_LANE) {
	    		if(lastMinTolls.size() != 0) {
	    			int lav = (int)lastMinTolls.get(0).num_cars;
	    			nextToll = (int)lastMinTolls.get(0).toll;
	    			String s = "0," + time + "," + LinearRoadConstants.EMIT_TIME_STR + "," + lav + "," + nextToll;
	    			outputTuples.add(s);
	    		}
	    		
	    		for(int i = 0; i < lastMinAccidents.size(); i++) {
	    			String s = "1," + time + "," + LinearRoadConstants.EMIT_TIME_STR + "," + lastMinAccidents.get(i).seg;
	    			outputTuples.add(s);
	    		}
    		}
    		///////////////////end notifications/////////////////////////
    		
    		///////////////////add to account balance////////////////////
    		if(prevToll > 0) {
    			List<TollsPerVehicleKey> tollsPerVehicleKeys = new ArrayList<TollsPerVehicleKey>(Arrays.asList(new TollsPerVehicleKey(vid, xway, 0)));
    			List<TollsPerVehicle> prevVIDTolls = tollsPerVehicleState.getTollsPerVehicleBulk(tollsPerVehicleKeys);
	    		//if there is already an account row for this vid on this xway
	    		tollsPerVehicleState.updateTollsPerVehicleBulk(tollsPerVehicleKeys, new ArrayList<Integer>(Arrays.asList(prevToll)));
    		}
    		///////////////////end add to account balance/////////////////
    	}
    	else {
    		////////////////////look for stopped cars/////////////////////
    		if(prevCount >= 3) {
    			if(prevPos == pos) {
    				curCount = curCount + prevCount;
    				stoppedCarState.setStoppedCarBulk(
    						new ArrayList<StoppedCar>(Arrays.asList(
    								new StoppedCar(xway, pos, vid, seg, dir, tod))));
    				evalAccident = 1;
    			}
    			else {
    				stoppedCarState.removeStoppedCarBulk(
    						new ArrayList<StoppedCarKey>(Arrays.asList(
    								new StoppedCarKey(xway, pos, vid))));
    				accidentState.updateAccidentBulk(
    						new ArrayList<AccidentKey>(Arrays.asList(
    								new AccidentKey(xway, prevDir, prevSeg, prevPos))),
    						tod);
    			}
    		}
    		///////////////////end look for stopped cars////////////////////
    		
    		if(prevTOD == tod) {
    			addSpd = (spd + prevSpeed)/2 - prevSpeed;
    			addCount = 0;
    		}
    	}
    	
    	voltQueueSQL(upsertPosition, part_id, xway, vid, seg, dir, lane, pos, spd, time, tod, curCount, nextToll);
    	voltQueueSQL(insertSegStatsStream, part_id, xway, tod, time, seg, dir, addCount, addSpd, evalAccident, todChange);
    	voltQueueSQL(UpdateCurrentTimestamp, tod, time, xway, part_id);
    	voltExecuteSQL();
    	checkOutTrigger(checkOutStmt, outStreamName, part_id);

        auditTime = auditQuery(part_id, tod, 3, auditTime);
        
        //hack to return a string back through a VoltTable
        return LinearRoadConstants.getOutputVoltTable(starttime, outputTuples);
	}


}
