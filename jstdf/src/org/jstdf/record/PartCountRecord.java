package org.jstdf.record;

import java.nio.ByteBuffer;

import org.jstdf.util.StdfRecordUtils;


/**
 * Part Count Record (PCR)
 * 
 * @author malong
 *
 */
public class PartCountRecord extends AbstractSTDFRecord
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1958524348834447276L;
	/**
	 * 1
	 */
	public static final byte Type = 1;
	/**
	 * 30
	 */
	public static final byte SubType = 30;
	
	/**
	 * U*1 Test head number See note
	 */
	public int HEAD_NUM; //U*1 Test head number See note
	/**
	 * U*1 Test site number
	 */
	public int SITE_NUM; //U*1 Test site number
	/**
	 * U*4 Number of parts tested
	 */
	public long PART_CNT; //U*4 Number of parts tested
	/**
	 * U*4 Number of parts retested, missing is 4,294,967,295
	 */
	public long RTST_CNT; //U*4 Number of parts retested 4,294,967,295
	/**
	 * U*4 Number of aborts during testing, missing is 4,294,967,295
	 */
	public long ABRT_CNT; //U*4 Number of aborts during testing 4,294,967,295
	/**
	 * U*4 Number of good (passed) parts tested, missing is 4,294,967,295
	 */
	public long GOOD_CNT; //U*4 Number of good (passed) parts tested 4,294,967,295
	/**
	 * U*4 Number of functional parts tested, missing is 4,294,967,295
	 */
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

	@Override
	public String toString()
	{
		return "PartCountRecord [HEAD_NUM=" + HEAD_NUM + ", SITE_NUM="
				+ SITE_NUM + ", PART_CNT=" + PART_CNT + ", RTST_CNT="
				+ RTST_CNT + ", ABRT_CNT=" + ABRT_CNT + ", GOOD_CNT="
				+ GOOD_CNT + ", FUNC_CNT=" + FUNC_CNT + "]";
	}
}
