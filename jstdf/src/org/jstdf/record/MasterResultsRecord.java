package org.jstdf.record;

import java.nio.ByteBuffer;
import java.util.Date;

import org.jstdf.util.StdfRecordUtils;


/**
 * Master Results Record (MRR)
 * 
 * @author malong
 *
 */
public class MasterResultsRecord extends AbstractSTDFRecord
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4749368768014362936L;
	/**
	 * 1
	 */
	public static final byte Type = 1;
	/**
	 * 20
	 */
	public static final byte SubType = 20;
	
	/**
	 * U*4 Date and time last part tested
	 */
	public Date FINISH_T; //U*4 Date and time last part tested
	/**
	 * C*1 Lot disposition code space
	 */
	public char DISP_COD; //C*1 Lot disposition code space
	/**
	 * C*n Lot description supplied by user length byte = 0 if data missing
	 */
	public String USR_DESC; //C*n Lot description supplied by user length byte = 0
	/**
	 * C*n Lot description supplied by exec length byte = 0 if data missing
	 */
	public String EXC_DESC; //C*n Lot description supplied by exec length byte = 0
	
	public static final MasterResultsRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		MasterResultsRecord rec = new MasterResultsRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public void readContent(ByteBuffer bb)
	{
		FINISH_T = StdfRecordUtils.readDate(bb);
		DISP_COD = StdfRecordUtils.readC1Char(bb);
		USR_DESC = StdfRecordUtils.readCnString(bb);
		EXC_DESC = StdfRecordUtils.readCnString(bb);
	}

	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.MRR;
	}

	@Override
	public String toString()
	{
		return getRecordNo()+": MasterResultsRecord [FINISH_T=" + FINISH_T + ", DISP_COD="
				+ DISP_COD + ", USR_DESC=" + USR_DESC + ", EXC_DESC="
				+ EXC_DESC + "]";
	}
}
