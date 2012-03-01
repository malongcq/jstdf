package net.sf.jstdf.data;

import java.util.ArrayDeque;
import java.util.Deque;

import net.sf.jstdf.record.FunctionalTestRecord;
import net.sf.jstdf.record.MultipleResultParametricRecord;
import net.sf.jstdf.record.ParametricTestRecord;
import net.sf.jstdf.record.PartInformationRecord;
import net.sf.jstdf.record.PartResultsRecord;
import net.sf.jstdf.record.STDFRecord;

public class PartResultSet 
{
	PartInformationRecord pir;
	public PartInformationRecord getPartInformationRecord()
	{
		return pir;
	}
	public void setPartInformationRecord(PartInformationRecord pir)
	{
		this.pir = pir;
	}
	
	PartResultsRecord prr;
	public PartResultsRecord getPartResultsRecord()
	{
		return prr;
	}
	public void setPartResultsRecord(PartResultsRecord prr)
	{
		this.prr = prr;
	}
	
	Deque<STDFRecord> rset = new ArrayDeque<STDFRecord>();
	public Deque<STDFRecord> getPartResult()
	{
		return rset;
	}
	public void setPartResult(Deque<STDFRecord> rset)
	{
		this.rset = rset;
	}
	public void addPartResult(ParametricTestRecord ptr)
	{
		rset.add(ptr);
	}
	public void addPartResult(MultipleResultParametricRecord mpr)
	{
		rset.add(mpr);
	}
	public void addPartResult(FunctionalTestRecord ftr)
	{
		rset.add(ftr);
	}
}
