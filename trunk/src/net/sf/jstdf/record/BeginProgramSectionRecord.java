package net.sf.jstdf.record;

import java.nio.ByteBuffer;

import net.sf.jstdf.util.StdfRecordUtils;


public class BeginProgramSectionRecord extends AbstractSTDFRecord
{
	public static final byte Type = 20;
	public static final byte SubType = 10;
	
	public String SEQ_NAME; //C*n Program section (or sequencer) name length byte = 0
	
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
