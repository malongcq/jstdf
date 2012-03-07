package org.jstdf.record;

import java.nio.ByteBuffer;

import org.jstdf.util.StdfRecordUtils;


/**
 * Begin Program Section Record (BPS)
 * 
 * @author malong
 *
 */
public class BeginProgramSectionRecord extends AbstractSTDFRecord
{
	/**
	 * 20
	 */
	public static final byte Type = 20;
	/**
	 * 10
	 */
	public static final byte SubType = 10;
	
	/**
	 * C*n Program section (or sequencer) name length byte = 0
	 */
	public String SEQ_NAME; 
	
	@Override
	public void readContent(ByteBuffer bb)
	{
		SEQ_NAME = StdfRecordUtils.readCnString(bb);
	}
	
	public static final BeginProgramSectionRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		BeginProgramSectionRecord rec = new BeginProgramSectionRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.BPS;
	}
}
