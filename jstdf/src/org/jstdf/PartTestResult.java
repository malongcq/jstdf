package org.jstdf;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.jstdf.record.FunctionalTestRecord;
import org.jstdf.record.MultipleResultParametricRecord;
import org.jstdf.record.ParametricTestRecord;
import org.jstdf.record.PartInformationRecord;
import org.jstdf.record.PartResultsRecord;


public class PartTestResult extends GenricTestResult
{
	PartResultHandler partResultHandler;
	public PartResultHandler getPartResultHandler() {
		return partResultHandler;
	}
	public void setPartResultHandler(PartResultHandler partResultHandler) {
		this.partResultHandler = partResultHandler;
	}

	protected Pattern testParameterPattern;
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

	protected Map<String, PartResultSet> testParts = new HashMap<String, PartResultSet>();
	protected String format_head_site = "h=%d,s=%d";
	
	@Override
	public boolean readRecord(PartInformationRecord pir)
	{
		String key = String.format(format_head_site, pir.HEAD_NUM, pir.SITE_NUM);
		PartResultSet rset = new PartResultSet();
		rset.setPartInformationRecord(pir);
		testParts.put(key, rset);
		return true;
	}
	
	@Override
	public boolean readRecord(PartResultsRecord prr) 
	{
		String key = String.format(format_head_site, prr.HEAD_NUM, prr.SITE_NUM);
		PartResultSet rset = testParts.get(key);
		rset.setPartResultsRecord(prr);
		
		return partResultHandler==null ? true : partResultHandler.readPartResult(rset);
	}
	
	@Override
	public boolean readRecord(ParametricTestRecord ptr)
	{
		if(!acceptParameter(ptr.TEST_TXT)) return false;
		String key = String.format(format_head_site, ptr.HEAD_NUM, ptr.SITE_NUM);
		testParts.get(key).addPartResult(ptr);
		return true;
	}
	
	@Override
	public boolean readRecord(MultipleResultParametricRecord mpr)
	{
		if(!acceptParameter(mpr.TEST_TXT)) return false;
		String key = String.format(format_head_site, mpr.HEAD_NUM, mpr.SITE_NUM);
		testParts.get(key).addPartResult(mpr);
		return true;
	}
	
	@Override
	public boolean readRecord(FunctionalTestRecord ftr)
	{
		if(!acceptParameter(ftr.TEST_TXT)) return false;
		String key = String.format("h=%d,s=%d", ftr.HEAD_NUM, ftr.SITE_NUM);
		testParts.get(key).addPartResult(ftr);
		return true;
	}
}
