package org.jstdf;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.jstdf.record.WaferConfigurationRecord;
import org.jstdf.record.WaferInformationRecord;
import org.jstdf.record.WaferResultsRecord;
import org.jstdf.util.StdfWaferUtils;

public class WaferTestResult extends AbstractPartTestResult 
{
	protected Map<Point, PartResultSet> wmap = new HashMap<Point, PartResultSet>();
	protected WaferConfigurationRecord wcr;
	protected WaferInformationRecord wir;
	protected WaferResultsRecord wrr;
	
	public int getSiteGroup()
	{
		if(wir!=null) return wir.SITE_GRP;
		else if (wrr!=null) return wrr.SITE_GRP;
		else return 255;
	}
	
	public String getWaferId()
	{
		if(wrr!=null && wrr.WAFER_ID!=null)
		{
			return wrr.WAFER_ID;
		}
		else if(wir!=null && wir.WAFER_ID!=null)
		{
			return wir.WAFER_ID;
		}
		return null;
	}
	
	public Map<Integer, Integer> getBinResults(boolean softBin)
	{
		return StdfWaferUtils.createWafermapBinCountSummary(wmap, softBin);
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
