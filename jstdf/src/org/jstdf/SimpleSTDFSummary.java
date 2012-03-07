package org.jstdf;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.TreeMap;

import org.jstdf.record.FunctionalTestRecord;
import org.jstdf.record.MultipleResultParametricRecord;
import org.jstdf.record.ParametricTestRecord;
import org.jstdf.record.STDFRecord;
import org.jstdf.record.STDFRecordType;


public class SimpleSTDFSummary extends AbstractStdfRecordHandler
{
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
			Integer p_cnt = ptrs.get(ptr.TEST_TXT);
			if(p_cnt==null) p_cnt = 0;
			ptrs.put(ptr.TEST_TXT, ++p_cnt);
		}
		else if(rec.getRecordType()==STDFRecordType.MPR)
		{
			MultipleResultParametricRecord mpr = (MultipleResultParametricRecord)rec;
			Integer p_cnt = mprs.get(mpr.TEST_TXT);
			if(p_cnt==null) p_cnt = 0;
			mprs.put(mpr.TEST_TXT, ++p_cnt);
		}
		else if(rec.getRecordType()==STDFRecordType.FTR)
		{
			FunctionalTestRecord ftr = (FunctionalTestRecord)rec;
			Integer p_cnt = ftrs.get(ftr.TEST_TXT);
			if(p_cnt==null) p_cnt = 0;
			ftrs.put(ftr.TEST_TXT, ++p_cnt);
		}
		
		if(EnumSet.of(STDFRecordType.MIR, STDFRecordType.SDR, STDFRecordType.MRR, 
			STDFRecordType.WCR, STDFRecordType.WIR, STDFRecordType.WRR).contains(rec.getRecordType()))
		{
			summary_rec.add(rec);
		}
		
		return true;
	}

	public void printSummary()
	{
		System.out.println(cnt_summary);
		
		System.out.println("----------------------PTR------------------------");
		for(Map.Entry<String, Integer> e : ptrs.entrySet()) 
			System.out.println(e.getKey()+" = "+e.getValue()); 
		
		System.out.println("----------------------MPR------------------------");
		for(Map.Entry<String, Integer> e : mprs.entrySet()) 
			System.out.println(e.getKey()+" = "+e.getValue()); 
		
		System.out.println("----------------------FTR------------------------");
		for(Map.Entry<String, Integer> e : ftrs.entrySet()) 
			System.out.println(e.getKey()+" = "+e.getValue()); 
		
		System.out.println("----------------------Summary------------------------");
		for(STDFRecord rec : summary_rec)
			System.out.println(rec);
	}
}
