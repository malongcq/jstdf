package org.jstdf.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.jstdf.PartLocation;
import org.jstdf.PartResultSet;
import org.jstdf.record.HardwareBinRecord;
import org.jstdf.record.PartResultsRecord;
import org.jstdf.record.SoftwareBinRecord;
import org.jstdf.record.WaferConfigurationRecord;


/**
 * General utilities for wafer data generation.
 * 
 * @author malong
 *
 */
public class StdfWaferUtils 
{
	private StdfWaferUtils() {}
	
	/**
	 * Create Hard/Soft bin wafermap.
	 * 
	 * @param wafermap the wafer part test results. 
	 * @param softBin true to use soft bin, false to use hard bin.
	 * @param wcr WaferConfigurationRecord used to create wafermap, can be null.
	 * @return the bin wafermap in 2D array.
	 */
	public static final int[][] createWafermapBin2DArray(
			Map<PartLocation, PartResultSet> wafermap,
			boolean softBin,
			WaferConfigurationRecord wcr)
	{
		int x_f = wcr==null ? 1 : wcr.getDirectionFactorX();
		int y_f = wcr==null ? 1 : wcr.getDirectionFactorY();
		
		int max_x = Integer.MIN_VALUE, min_x = Integer.MAX_VALUE;
		int max_y = Integer.MIN_VALUE, min_y = Integer.MAX_VALUE;
		for(PartLocation p : wafermap.keySet())
		{
			int xx = p.x * x_f;
			int yy = p.y * y_f;
			
			max_x = xx > max_x ? xx : max_x;
			max_y = yy > max_y ? yy : max_y;
			min_x = xx < min_x ? xx : min_x;
			min_y = yy < min_y ? yy : min_y;
		}
		
		int[][] wmap = new int[max_y-min_y+1][max_x-min_x+1];
		for(int y=0;y<wmap.length;y++) for(int x=0;x<wmap[y].length;x++) wmap[y][x] = -1;
		
		for(PartResultSet part : wafermap.values())
		{
			PartResultsRecord prr = part.getPartResultsRecord();
			int xx = prr.X_COORD * x_f - min_x;
			int yy = prr.Y_COORD * y_f - min_y;
			wmap[yy][xx] = softBin ? prr.SOFT_BIN : prr.HARD_BIN;
		}
		
		return wmap;
	}
	
	/**
	 * Create Hard/Soft bin count summary.
	 * 
	 * @param parts the wafer part test results. 
	 * @param softBin true to use soft bin, false to use hard bin.
	 * @return a map stored bin-count pair of data.
	 */
	public final static Map<Integer, Integer> calculateBinResult(
			Collection<PartResultSet> parts, boolean softBin)
	{
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
		for(PartResultSet part : parts)
		{
			PartResultsRecord prr = part.getPartResultsRecord();
			int bin = softBin ? prr.SOFT_BIN : prr.HARD_BIN;
			Integer cnt = map.get(bin);
			if(cnt==null) cnt = 0;
			map.put(bin, ++cnt);
		}
		
		return map;
	}
	
	/**
	 * 
	 * @param hbrs
	 * @return
	 */
	public final static Map<Integer, HardwareBinRecord> createHardwareBinMap(Collection<HardwareBinRecord> hbrs)
	{
		Map<Integer, HardwareBinRecord> map = new HashMap<Integer, HardwareBinRecord>();
		
		for(HardwareBinRecord hbr : hbrs)
		{
			map.put(hbr.HBIN_NUM, hbr);
		}
		
		return map;
	}
	
	/**
	 * 
	 * @param sbrs
	 * @return
	 */
	public final static Map<Integer, SoftwareBinRecord> createSoftwareBinMap(Collection<SoftwareBinRecord> sbrs)
	{
		Map<Integer, SoftwareBinRecord> map = new HashMap<Integer, SoftwareBinRecord>();
		
		for(SoftwareBinRecord sbr : sbrs)
		{
			map.put(sbr.SBIN_NUM, sbr);
		}
		
		return map;
	}
	
	/**
	 * 
	 * @param wmap
	 * @param hbrs
	 * @return
	 */
	public final static int calculateHardwareBinPass(Collection<PartResultSet> parts, 
			Collection<HardwareBinRecord> hbrs)
	{
		Map<Integer, HardwareBinRecord> map = createHardwareBinMap(hbrs);
		int pass = 0;
		for(PartResultSet p : parts)
		{
			HardwareBinRecord hbr = map.get(p.getPartResultsRecord().HARD_BIN);
			pass += (hbr!=null && hbr.isPass()) ? 1 : 0;
		}
		return pass;
	}
	
	/**
	 * 
	 * @param wmap
	 * @param sbrs
	 * @return
	 */
	public final static int calculateSoftwareBinPass(Collection<PartResultSet> parts, 
			Collection<SoftwareBinRecord> sbrs)
	{
		Map<Integer, SoftwareBinRecord> map = createSoftwareBinMap(sbrs);
		int pass = 0;
		for(PartResultSet p : parts)
		{
			SoftwareBinRecord sbr = map.get(p.getPartResultsRecord().SOFT_BIN);
			pass += (sbr!=null && sbr.isPass()) ? 1 : 0;
		}
		return pass;
	}
}
