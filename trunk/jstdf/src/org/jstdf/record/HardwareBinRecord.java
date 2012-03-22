package org.jstdf.record;

import java.nio.ByteBuffer;

import org.jstdf.util.StdfRecordUtils;


/**
 * Hardware Bin Record (HBR)
 * 
 * @author malong
 *
 */
public class HardwareBinRecord extends AbstractSTDFRecord
{
	/**
	 * 1
	 */
	public static final byte Type = 1;
	/**
	 * 40
	 */
	public static final byte SubType = 40;
	
	/**
	 * U*1 Test head number 
	 */
	public int HEAD_NUM; //U*1 Test head number See note
	
	/**
	 * U*1 Test site number
	 */
	public int SITE_NUM; //U*1 Test site number
	
	/**
	 * U*2 Hardware bin number
	 */
	public int HBIN_NUM; //U*2 Hardware bin number
	
	/**
	 * U*4 Number of parts in bin
	 */
	public long HBIN_CNT; //U*4 Number of parts in bin
	
	/**
	 * C*1 Pass/fail indication space
	 */
	public char HBIN_PF; //C*1 Pass/fail indication space
	
	/**
	 * C*n Name of hardware bin length byte = 0 if data missing
	 */
	public String HBIN_NAM; //C*n Name of hardware bin length byte = 0
	
	public static final HardwareBinRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		HardwareBinRecord rec = new HardwareBinRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public void readContent(ByteBuffer bb)
	{
		HEAD_NUM = StdfRecordUtils.readU1Int(bb);
		SITE_NUM = StdfRecordUtils.readU1Int(bb);
		
		HBIN_NUM = StdfRecordUtils.readU2Int(bb);
		HBIN_CNT = StdfRecordUtils.readU4Int(bb);
		HBIN_PF = StdfRecordUtils.readC1Char(bb);
		HBIN_NAM = StdfRecordUtils.readCnString(bb);
	}

	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.HBR;
	}

	@Override
	public String toString()
	{
		return "HardwareBinRecord [HEAD_NUM=" + HEAD_NUM + ", SITE_NUM="
				+ SITE_NUM + ", HBIN_NUM=" + HBIN_NUM + ", HBIN_CNT="
				+ HBIN_CNT + ", HBIN_PF=" + HBIN_PF + ", HBIN_NAM=" + HBIN_NAM
				+ "]";
	}
	
	public boolean isPass()
	{
		return HBIN_PF=='P';
	}
}
