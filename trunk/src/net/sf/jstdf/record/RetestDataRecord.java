package net.sf.jstdf.record;

import java.nio.ByteBuffer;

import net.sf.jstdf.util.StdfRecordUtils;


public class RetestDataRecord extends AbstractSTDFRecord
{
	public static final byte Type = 1;
	public static final byte SubType = 70;
	
	public int NUM_BINS; //U*2 Number (k) of bins being retested
	public int[] RTST_BIN; //kxU*2 Array of retest bin numbers NUM_BINS = 0
	
	public static final RetestDataRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		RetestDataRecord rec = new RetestDataRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}

	@Override
	public void readContent(ByteBuffer bb)
	{
		NUM_BINS = StdfRecordUtils.readU2Int(bb);
		RTST_BIN = StdfRecordUtils.readKU2Int(bb, NUM_BINS);
	}

	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.RDR;
	}
}
