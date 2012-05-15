package org.jstdf.record;

import java.nio.ByteBuffer;

import org.jstdf.util.StdfRecordUtils;


/**
 * Software Bin Record (SBR)
 * 
 * @author malong
 *
 */
public class SoftwareBinRecord extends AbstractSTDFRecord
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1394354177457298802L;
	/**
	 * 1
	 */
	public static final byte Type = 1;
	/**
	 * 50
	 */
	public static final byte SubType = 50;
	
	/**
	 * U*1 Test head number 
	 */
	public int HEAD_NUM; //U*1 Test head number See note
	/**
	 * U*1 Test site number
	 */
	public int SITE_NUM; //U*1 Test site number
	/**
	 * U*2 Software bin number
	 */
	public int SBIN_NUM; //U*2 Software bin number
	/**
	 * U*4 Number of parts in bin
	 */
	public long SBIN_CNT; //U*4 Number of parts in bin
	/**
	 * C*1 Pass/fail indication space
	 */
	public char SBIN_PF; //C*1 Pass/fail indication space
	/**
	 * C*n Name of software bin length byte = 0 if data missing
	 */
	public String SBIN_NAM; //C*n Name of software bin length byte = 0
	
	public static final SoftwareBinRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		SoftwareBinRecord rec = new SoftwareBinRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}

	@Override
	public void readContent(ByteBuffer bb)
	{
		HEAD_NUM = StdfRecordUtils.readU1Int(bb);
		SITE_NUM = StdfRecordUtils.readU1Int(bb);
		
		SBIN_NUM = StdfRecordUtils.readU2Int(bb);
		SBIN_CNT = StdfRecordUtils.readU4Int(bb);
		SBIN_PF = StdfRecordUtils.readC1Char(bb);
		SBIN_NAM = StdfRecordUtils.readCnString(bb);
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.SBR;
	}

	@Override
	public String toString()
	{
		return getRecordNo()+": SoftwareBinRecord [HEAD_NUM=" + HEAD_NUM + ", SITE_NUM="
				+ SITE_NUM + ", SBIN_NUM=" + SBIN_NUM + ", SBIN_CNT="
				+ SBIN_CNT + ", SBIN_PF=" + SBIN_PF + ", SBIN_NAM=" + SBIN_NAM
				+ "]";
	}
	
	public boolean isPass()
	{
		return SBIN_PF=='P';
	}
}
