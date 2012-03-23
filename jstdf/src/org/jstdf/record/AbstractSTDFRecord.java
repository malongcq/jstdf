package org.jstdf.record;

import java.io.Serializable;

/**
 * An abstract class implements STDFRecord
 * 
 * @author malong
 *
 */
public abstract class AbstractSTDFRecord implements STDFRecord, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3324904248547773062L;

	protected int REC_NO;
	
	protected int REC_LEN;
	protected int REC_TYP;
	protected int REC_SUB;
	
	@Override
	public void setHeadInfo(int seq, int len, int typ, int sub) 
	{
		REC_NO = seq;
		REC_LEN = len;
		REC_TYP = typ;
		REC_SUB = sub;
	}
	
	@Override
	public int getRecordNo()
	{
		return REC_NO;
	}
	
	@Override
	public int getRecordLength()
	{
		return REC_LEN;
	}
	
	@Override
	public int getRecordTypeCode()
	{
		return REC_TYP;
	}
	
	@Override
	public int getRecordSubTypeCode()
	{
		return REC_SUB;
	}
}
