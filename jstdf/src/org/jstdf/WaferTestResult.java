package org.jstdf;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.jstdf.record.WaferConfigurationRecord;
import org.jstdf.record.WaferInformationRecord;
import org.jstdf.record.WaferResultsRecord;

public class WaferTestResult extends AbstractPartTestResult 
{
	protected Map<Point, PartResultSet> wmap = new HashMap<Point, PartResultSet>();
	protected WaferConfigurationRecord wcr;
	protected WaferInformationRecord wir;
	protected WaferResultsRecord wrr;
	
	public Map<Integer, Integer> getBinSummary(boolean softBin)
	{
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
		for(PartResultSet p : wmap.values())
		{
			int bin = softBin ? p.getPartResultsRecord().SOFT_BIN : p.getPartResultsRecord().HARD_BIN;
			Integer cnt = map.get(bin);
			if(cnt==null) cnt = 0;
			map.put(bin, ++cnt);
		}
		
		return map;
	}
	
	public Map<Point, PartResultSet> getWafermap()
	{
		return wmap;
	}
	
	public WaferConfigurationRecord getWaferConfigurationRecord()
	{
		return wcr;
	}
	
	public WaferInformationRecord getWaferInformationRecord()
	{
		return wir;
	}
	
	public WaferResultsRecord getWaferResultsRecord()
	{
		return wrr;
	}
	
	@Override
	public boolean readRecord(WaferConfigurationRecord wcr)
	{
		this.wcr = wcr;
		return true;
	}
	
	@Override
	public boolean readRecord(WaferInformationRecord wir)
	{
		this.wir = wir;
		return true;
	}
	
	@Override
	public boolean readRecord(WaferResultsRecord wrr)
	{
		this.wrr = wrr;
		return true;
	}

	@Override
	public boolean readPartResultSet(PartResultSet part_result)
	{
		int die_x = part_result.prr.X_COORD;
		int die_y = part_result.prr.Y_COORD;
		Point p = new Point(die_x, die_y);
		// the new part result should always replace old in same test execution 
		wmap.put(p, part_result);
		return true;
	}
}
