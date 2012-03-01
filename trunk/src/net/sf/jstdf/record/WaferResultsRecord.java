package net.sf.jstdf.record;

import java.nio.ByteBuffer;
import java.util.Date;

import net.sf.jstdf.util.StdfRecordUtils;

/**
 * Wafer Results Record (WRR)
 * 
 * @author malong
 *
 */
public class WaferResultsRecord extends AbstractSTDFRecord
{
	public static final byte Type = 2;
	public static final byte SubType = 20;
	
	public int HEAD_NUM; //U*1 Test head number
	public int SITE_GRP; //U*1 Site group number 255
	public Date FINISH_T; //U*4 Date and time last part tested
	public long PART_CNT; //U*4 Number of parts tested
	public long RTST_CNT; //U*4 Number of parts retested 4,294,967,295
	public long ABRT_CNT; //U*4 Number of aborts during testing 4,294,967,295
	public long GOOD_CNT; //U*4 Number of good (passed) parts tested 4,294,967,295
	public long FUNC_CNT; //U*4 Number of functional parts tested 4,294,967,295
	public String WAFER_ID; //C*n Wafer ID length byte = 0
	public String FABWF_ID; //C*n Fab wafer ID length byte = 0
	public String FRAME_ID; //C*n Wafer frame ID length byte = 0
	public String MASK_ID; //C*n Wafer mask ID length byte = 0
	public String USR_DESC; //C*n Wafer description supplied by user length byte = 0
	public String EXC_DESC; //C*n Wafer description supplied by exec length byte = 0
	
	public static final WaferResultsRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		WaferResultsRecord rec = new WaferResultsRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}

	@Override
	public void readContent(ByteBuffer bb)
	{
		HEAD_NUM = StdfRecordUtils.readU1Int(bb);
		SITE_GRP = StdfRecordUtils.readU1Int(bb);
		FINISH_T = StdfRecordUtils.readDate(bb);
		PART_CNT = StdfRecordUtils.readU4Int(bb);
		RTST_CNT = StdfRecordUtils.readU4Int(bb);
		ABRT_CNT = StdfRecordUtils.readU4Int(bb);
		GOOD_CNT = StdfRecordUtils.readU4Int(bb);
		FUNC_CNT = StdfRecordUtils.readU4Int(bb);
		
		WAFER_ID = StdfRecordUtils.readCnString(bb);
		FABWF_ID = StdfRecordUtils.readCnString(bb);
		FRAME_ID = StdfRecordUtils.readCnString(bb);
		MASK_ID = StdfRecordUtils.readCnString(bb);
		USR_DESC = StdfRecordUtils.readCnString(bb);
		EXC_DESC = StdfRecordUtils.readCnString(bb);
	}

	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.WRR;
	}

	@Override
	public String toString()
	{
		return "WaferResultsRecord [HEAD_NUM=" + HEAD_NUM + ", SITE_GRP="
				+ SITE_GRP + ", FINISH_T=" + FINISH_T + ", PART_CNT="
				+ PART_CNT + ", RTST_CNT=" + RTST_CNT + ", ABRT_CNT="
				+ ABRT_CNT + ", GOOD_CNT=" + GOOD_CNT + ", FUNC_CNT="
				+ FUNC_CNT + ", WAFER_ID=" + WAFER_ID + ", FABWF_ID="
				+ FABWF_ID + ", FRAME_ID=" + FRAME_ID + ", MASK_ID=" + MASK_ID
				+ ", USR_DESC=" + USR_DESC + ", EXC_DESC=" + EXC_DESC + "]";
	}
}
