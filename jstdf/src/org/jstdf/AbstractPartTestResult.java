package org.jstdf;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.jstdf.record.FunctionalTestRecord;
import org.jstdf.record.HardwareBinRecord;
import org.jstdf.record.MultipleResultParametricRecord;
import org.jstdf.record.ParametricTestRecord;
import org.jstdf.record.PartInformationRecord;
import org.jstdf.record.PartResultsRecord;
import org.jstdf.record.SoftwareBinRecord;
import org.jstdf.record.TestSynopsisRecord;


public abstract class AbstractPartTestResult extends AbstractTestResult implements PartResultSetHandler
{
	protected Set<ParametricTestItem> testItems;
	protected Deque<HardwareBinRecord> hbrs;
	protected Deque<SoftwareBinRecord> sbrs;
	protected Deque<TestSynopsisRecord> tsrs;
	
	protected Pattern testParameterPattern;
	
//	private Map<String, PartResultSet> testParts = new HashMap<String, PartResultSet>();
//	private String format_head_site = "h=%d,s=%d";
	
	/*
	 * allocate as large as possible to handle all possible head/site
	 * head/site=U*1 then max=255
	 */
	private PartResultSet[][] testParts = new PartResultSet[256][256];

	public Set<ParametricTestItem> getParametricTestItems()
	{
		return testItems;
	}
	
	public Deque<HardwareBinRecord> getHardwareBinRecords()
	{
		return hbrs;
	}
	
	public Deque<SoftwareBinRecord> getSoftwareBinRecords()
	{
		return sbrs;
	}
	
	public Deque<TestSynopsisRecord> getTestSynopsisRecords()
	{
		return tsrs;
	}
	
	public String getTestParameterPattern()
	{
		return testParameterPattern==null ? "" : testParameterPattern.pattern();
	}
	public void setTestParameterPattern(String pattern)
	{
		testParameterPattern = (pattern==null||pattern.length()<1) ? 
				null : Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		//System.out.println(testParameterPattern.pattern());
	}
	
	protected boolean acceptParameter(String name)
	{
		return testParameterPattern==null ? 
			true : testParameterPattern.matcher(name).matches();
	}
	
	protected void removeCachedTestPart(int head, int site)
	{
//		String key = String.format(format_head_site, head, site);
//		testParts.remove(key);
		testParts[head][site] = null;
	}
	
	protected PartResultSet createCachedTestPart(int head, int site)
	{
		PartResultSet rset = new PartResultSet();
//		String key = String.format(format_head_site, head, site);
//		testParts.put(key, rset);
		testParts[head][site] = rset;
		
		return rset;
	}
	
	protected PartResultSet getCachedTestPart(int head, int site)
	{
//		String key = String.format(format_head_site, head, site);
//		return testParts.get(key);
		return testParts[head][site];
	}
	
	@Override
	public boolean readRecord(PartInformationRecord pir)
	{
//		String key = String.format(format_head_site, pir.HEAD_NUM, pir.SITE_NUM);
//		PartResultSet rset = new PartResultSet();
//		rset.setPartInformationRecord(pir);
//		testParts.put(key, rset);
		PartResultSet rset = createCachedTestPart(pir.HEAD_NUM, pir.SITE_NUM);
		rset.setPartInformationRecord(pir);
		return true;
	}
	
	@Override
	public boolean readRecord(PartResultsRecord prr) 
	{
//		String key = String.format(format_head_site, prr.HEAD_NUM, prr.SITE_NUM);
//		PartResultSet rset = testParts.get(key);
//		rset.setPartResultsRecord(prr);
		PartResultSet rset = getCachedTestPart(prr.HEAD_NUM, prr.SITE_NUM);
		if(rset==null) rset = new PartResultSet();
		rset.setPartResultsRecord(prr);
		
		removeCachedTestPart(prr.HEAD_NUM, prr.SITE_NUM);
		return readPartResultSet(rset);
	}
	
	@Override
	public boolean readRecord(ParametricTestRecord ptr)
	{
		if(!acceptParameter(ptr.TEST_TXT)) return false;
//		String key = String.format(format_head_site, ptr.HEAD_NUM, ptr.SITE_NUM);
//		testParts.get(key).addPartResult(ptr);
		PartResultSet rset = getCachedTestPart(ptr.HEAD_NUM, ptr.SITE_NUM);
		if(rset!=null)
		{
			rset.addPartResult(ptr);
			addParametricTestItem(ptr);
		}
		
		return true;
	}
	
	@Override
	public boolean readRecord(MultipleResultParametricRecord mpr)
	{
		if(!acceptParameter(mpr.TEST_TXT)) return false;
//		String key = String.format(format_head_site, mpr.HEAD_NUM, mpr.SITE_NUM);
//		testParts.get(key).addPartResult(mpr);
		PartResultSet rset = getCachedTestPart(mpr.HEAD_NUM, mpr.SITE_NUM);
		if(rset!=null)
		{
			rset.addPartResult(mpr);
			addParametricTestItem(mpr);
		}
		
		return true;
	}
	
	@Override
	public boolean readRecord(FunctionalTestRecord ftr)
	{
		if(!acceptParameter(ftr.TEST_TXT)) return false;
//		String key = String.format("h=%d,s=%d", ftr.HEAD_NUM, ftr.SITE_NUM);
//		testParts.get(key).addPartResult(ftr);
		PartResultSet rset = getCachedTestPart(ftr.HEAD_NUM, ftr.SITE_NUM);
		if(rset!=null)
		{
			rset.addPartResult(ftr);
//			addParametricTestItem(ftr);
		}
		
		return true;
	}
	
	@Override
	public boolean readRecord(HardwareBinRecord hbr) 
	{
		if(hbrs==null)
		{
			hbrs = new ArrayDeque<HardwareBinRecord>();
		}
		hbrs.add(hbr);
		
		return true;
	}
	
	@Override
	public boolean readRecord(SoftwareBinRecord sbr) 
	{
		if(sbrs==null)
		{
			sbrs = new ArrayDeque<SoftwareBinRecord>();
		}
		sbrs.add(sbr);
		return true;
	}
	
	@Override
	public boolean readRecord(TestSynopsisRecord tsr) 
	{
		if(tsrs==null)
		{
			tsrs = new ArrayDeque<TestSynopsisRecord>();
		}
		tsrs.add(tsr);
		return true;
	}
	
	protected void addParametricTestItem(ParametricTestRecord ptr)
	{
		if(testItems==null) 
		{
			testItems = new HashSet<ParametricTestItem>();
		}
		testItems.add(new ParametricTestItem(ptr));
	}
	
	protected void addParametricTestItem(MultipleResultParametricRecord mpr)
	{
		if(testItems==null) 
		{
			testItems = new HashSet<ParametricTestItem>();
		}
		testItems.add(new ParametricTestItem(mpr));
	}
}
