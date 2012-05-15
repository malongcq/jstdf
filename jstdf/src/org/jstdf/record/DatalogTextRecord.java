package org.jstdf.record;

import java.nio.ByteBuffer;

import org.jstdf.util.StdfRecordUtils;


/**
 * Datalog Text Record (DTR)
 * 
 * @author malong
 *
 */
public class DatalogTextRecord extends AbstractSTDFRecord
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -104685357081907194L;
	/**
	 * 50
	 */
	public static final byte Type = 50;
	/**
	 * 30
	 */
	public static final byte SubType = 30;
	
	/**
	 * C*n ASCII text string
	 */
	public String TEXT_DAT; 
	
	@Override
	public void readContent(ByteBuffer bb)
	{
		TEXT_DAT = StdfRecordUtils.readCnString(bb);
	}

	public static final DatalogTextRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		DatalogTextRecord rec = new DatalogTextRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.DTR;
	}

	@Override
	public String toString()
	{
		return "DatalogTextRecord [TEXT_DAT=" + TEXT_DAT + "]";
	}
}
