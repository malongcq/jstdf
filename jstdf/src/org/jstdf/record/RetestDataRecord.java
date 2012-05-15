package org.jstdf.record;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.jstdf.util.StdfRecordUtils;


/**
 * Retest Data Record (RDR)
 * 
 * @author malong
 *
 */
public class RetestDataRecord extends AbstractSTDFRecord
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6781761957965065925L;
	/**
	 * 1
	 */
	public static final byte Type = 1;
	/**
	 * 70
	 */
	public static final byte SubType = 70;
	
	/**
	 * U*2 Number (k) of bins being retested
	 */
	public int NUM_BINS; //U*2 Number (k) of bins being retested
	/**
	 * kxU*2 Array of retest bin numbers, NUM_BINS = 0 if data missing
	 */
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

	@Override
	public String toString()
	{
		return getRecordNo()+": RetestDataRecord [NUM_BINS=" + NUM_BINS + ", RTST_BIN="
				+ Arrays.toString(RTST_BIN) + "]";
	}
}
