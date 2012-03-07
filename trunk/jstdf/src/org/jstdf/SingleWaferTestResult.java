package org.jstdf;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.jstdf.record.WaferConfigurationRecord;
import org.jstdf.record.WaferInformationRecord;
import org.jstdf.record.WaferResultsRecord;


public class SingleWaferTestResult extends PartTestResult 
{
	public SingleWaferTestResult()
	{
		this.setPartResultHandler(new PartResultHandler()
		{
			@Override
			public boolean readPartResult(PartResultSet prlt) 
			{
				int die_x = prlt.prr.X_COORD;
				int die_y = prlt.prr.Y_COORD;
				Point p = new Point(die_x, die_y);
				wmap.put(p, prlt);
				return true;
			}
		});
	}
	
	protected Map<Point, PartResultSet> wmap = new HashMap<Point, PartResultSet>();
	protected WaferConfigurationRecord wcr;
	protected WaferInformationRecord wir;
	protected WaferResultsRecord wrr;
	
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
}
