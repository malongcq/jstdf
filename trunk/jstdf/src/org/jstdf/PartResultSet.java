package org.jstdf;

import java.util.ArrayDeque;
import java.util.Deque;

import org.jstdf.record.FunctionalTestRecord;
import org.jstdf.record.MultipleResultParametricRecord;
import org.jstdf.record.ParametricTestRecord;
import org.jstdf.record.PartInformationRecord;
import org.jstdf.record.PartResultsRecord;
import org.jstdf.record.STDFRecord;


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
	public Deque<STDFRecord> getPartResults()
	{
		return rset;
	}
	public void setPartResults(Deque<STDFRecord> rset)
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
