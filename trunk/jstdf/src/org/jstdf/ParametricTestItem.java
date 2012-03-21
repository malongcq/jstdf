package org.jstdf;

import org.jstdf.record.MultipleResultParametricRecord;
import org.jstdf.record.ParametricTestRecord;

public class ParametricTestItem
{
	private long testNum;
	private String testName;
	private Double lowLimit, highLimit, lowSpec, highSpec;
	
	public long getTestNum()
	{
		return testNum;
	}
	public void setTestNum(long testNum)
	{
		this.testNum = testNum;
	}
	public String getTestName()
	{
		return testName;
	}
	public void setTestName(String testName)
	{
		this.testName = testName;
	}
	public Double getLowLimit()
	{
		return lowLimit;
	}
	public void setLowLimit(Double lowLimit)
	{
		this.lowLimit = lowLimit;
	}
	public Double getHighLimit()
	{
		return highLimit;
	}
	public void setHighLimit(Double highLimit)
	{
		this.highLimit = highLimit;
	}
	public Double getLowSpec()
	{
		return lowSpec;
	}
	public void setLowSpec(Double lowSpec)
	{
		this.lowSpec = lowSpec;
	}
	public Double getHighSpec()
	{
		return highSpec;
	}
	public void setHighSpec(Double highSpec)
	{
		this.highSpec = highSpec;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((testName == null) ? 0 : testName.hashCode());
		result = prime * result + (int) (testNum ^ (testNum >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParametricTestItem other = (ParametricTestItem) obj;
		if (testName == null)
		{
			if (other.testName != null)
				return false;
		} else if (!testName.equals(other.testName))
			return false;
		if (testNum != other.testNum)
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "ParametricTestItem [testNum=" + testNum + ", testName="
				+ testName + ", lowLimit=" + lowLimit + ", highLimit="
				+ highLimit + ", lowSpec=" + lowSpec + ", highSpec=" + highSpec
				+ "]";
	}
	
	public ParametricTestItem(ParametricTestRecord ptr)
	{
		setTestNum(ptr.TEST_NUM);
		setTestName(ptr.TEST_TXT);
		setHighLimit(ptr.HI_LIMIT);
		setLowLimit(ptr.LO_LIMIT);
		setHighSpec(ptr.HI_SPEC);
		setLowSpec(ptr.LO_SPEC);
	}
	
	public ParametricTestItem(MultipleResultParametricRecord mpr)
	{
		setTestNum(mpr.TEST_NUM);
		setTestName(mpr.TEST_TXT);
		setHighLimit(mpr.HI_LIMIT);
		setLowLimit(mpr.LO_LIMIT);
		setHighSpec(mpr.HI_SPEC);
		setLowSpec(mpr.LO_SPEC);
	}
}
