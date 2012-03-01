package net.sf.jstdf.record;

import java.nio.ByteBuffer;

import net.sf.jstdf.util.StdfRecordUtils;


public class PartCountRecord extends AbstractSTDFRecord
{
	public static final byte Type = 1;
	public static final byte SubType = 30;
	
	public int HEAD_NUM; //U*1 Test head number See note
	public int SITE_NUM; //U*1 Test site number
	public long PART_CNT; //U*4 Number of parts tested
	public long RTST_CNT; //U*4 Number of parts retested 4,294,967,295
	public long ABRT_CNT; //U*4 Number of aborts during testing 4,294,967,295
	public long GOOD_CNT; //U*4 Number of good (passed) parts tested 4,294,967,295
	public long FUNC_CNT; //U*4 Number of functional parts tested 4,294,967,295
	
	public static final PartCountRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		PartCountRecord rec = new PartCountRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public void readContent(ByteBuffer bb)
	{
		HEAD_NUM = StdfRecordUtils.readU1Int(bb);
		SITE_NUM = StdfRecordUtils.readU1Int(bb);
		
		PART_CNT = StdfRecordUtils.readU4Int(bb);
		RTST_CNT = StdfRecordUtils.readU4Int(bb);
		ABRT_CNT = StdfRecordUtils.readU4Int(bb);
		GOOD_CNT = StdfRecordUtils.readU4Int(bb);
		FUNC_CNT = StdfRecordUtils.readU4Int(bb);
	}

	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.PCR;
	}
}
