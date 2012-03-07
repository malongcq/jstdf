package org.jstdf.record;

import java.nio.ByteBuffer;

import org.jstdf.util.StdfRecordUtils;


/**
 * Part Information Record (PIR)
 * 
 * @author malong
 *
 */
public class PartInformationRecord extends AbstractSTDFRecord
{
	/**
	 * 5
	 */
	public static final byte Type = 5;
	/**
	 * 10
	 */
	public static final byte SubType = 10;
	
	/**
	 * U*1 Test head number
	 */
	public int HEAD_NUM; //U*1 Test head number
	/**
	 * U*1 Test site number
	 */
	public int SITE_NUM; //U*1 Test site number
	
	@Override
	public void readContent(ByteBuffer bb)
	{
		HEAD_NUM = StdfRecordUtils.readU1Int(bb);
		SITE_NUM = StdfRecordUtils.readU1Int(bb);
	}

	public static final PartInformationRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		PartInformationRecord rec = new PartInformationRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.PIR;
	}
}
