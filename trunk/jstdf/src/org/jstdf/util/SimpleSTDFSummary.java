package org.jstdf.util;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.TreeMap;

import org.jstdf.AbstractStdfRecordHandler;
import org.jstdf.record.FunctionalTestRecord;
import org.jstdf.record.MultipleResultParametricRecord;
import org.jstdf.record.ParametricTestRecord;
import org.jstdf.record.STDFRecord;
import org.jstdf.record.STDFRecordType;


public class SimpleSTDFSummary extends AbstractStdfRecordHandler
{
	static final EnumSet<STDFRecordType> STDF_TYPS = EnumSet.of(
			STDFRecordType.FAR, STDFRecordType.ATR, STDFRecordType.RDR,
			STDFRecordType.MIR, STDFRecordType.SDR, STDFRecordType.MRR, 
			STDFRecordType.WCR, STDFRecordType.WIR, STDFRecordType.WRR,
			STDFRecordType.HBR, STDFRecordType.SBR,
			STDFRecordType.PGR, STDFRecordType.PLR, STDFRecordType.PMR,
			STDFRecordType.PCR, STDFRecordType.TSR);
	
	EnumMap<STDFRecordType, Integer> cnt_summary = new EnumMap<STDFRecordType, Integer>(STDFRecordType.class); 
	
	TreeMap<String, Integer> ptrs = new TreeMap<String, Integer>();
	TreeMap<String, Integer> mprs = new TreeMap<String, Integer>();
	TreeMap<String, Integer> ftrs = new TreeMap<String, Integer>();
	
	Collection<STDFRecord> summary_rec = new ArrayDeque<STDFRecord>();
	
	@Override
	protected boolean readSTDFRecord(STDFRecord rec) 
	{
		STDFRecordType typ = rec.getRecordType();
		Integer typ_cnt = cnt_summary.get(typ);
		if(typ_cnt==null)
		{
			typ_cnt = 0;
		}
		cnt_summary.put(typ, ++typ_cnt);
		
		if(rec.getRecordType()==STDFRecordType.PTR)
		{
			ParametricTestRecord ptr = (ParametricTestRecord)rec;
			String key = String.format("%d_%s", ptr.TEST_NUM, ptr.TEST_TXT);
			
			Integer p_cnt = ptrs.get(key);
			if(p_cnt==null) p_cnt = 0;
			ptrs.put(key, ++p_cnt);
		}
		else if(rec.getRecordType()==STDFRecordType.MPR)
		{
			MultipleResultParametricRecord mpr = (MultipleResultParametricRecord)rec;
			String key = String.format("%d_%s", mpr.TEST_NUM, mpr.TEST_TXT);
			
			Integer p_cnt = mprs.get(key);
			if(p_cnt==null) p_cnt = 0;
			mprs.put(key, ++p_cnt);
		}
		else if(rec.getRecordType()==STDFRecordType.FTR)
		{
			FunctionalTestRecord ftr = (FunctionalTestRecord)rec;
			String key = String.format("%d_%s", ftr.TEST_NUM, ftr.TEST_TXT);
			
			Integer p_cnt = ftrs.get(key);
			if(p_cnt==null) p_cnt = 0;
			ftrs.put(key, ++p_cnt);
		}
		
		if(STDF_TYPS.contains(rec.getRecordType()))
		{
			summary_rec.add(rec);
		}
		
		return true;
	}

	public void printSummary()
	{
		printSummary(System.out);
	}
	
	public void printSummary(OutputStream os)
	{
		PrintWriter out = null;
		try
		{
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(os)));
			
			out.println("----------------------PTR------------------------");
			for(Map.Entry<String, Integer> e : ptrs.entrySet()) 
				out.println(e.getKey()+" = "+e.getValue()); 
			
			out.println("----------------------MPR------------------------");
			for(Map.Entry<String, Integer> e : mprs.entrySet()) 
				out.println(e.getKey()+" = "+e.getValue()); 
			
			out.println("----------------------FTR------------------------");
			for(Map.Entry<String, Integer> e : ftrs.entrySet()) 
				out.println(e.getKey()+" = "+e.getValue()); 
			
			out.println("----------------------Summary------------------------");
			for(STDFRecord rec : summary_rec)
				out.println(rec);
			out.println(cnt_summary);
		} 
		finally
		{
			if(out!=null) out.close();
		}
	}
}
