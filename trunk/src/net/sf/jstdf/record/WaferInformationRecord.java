package net.sf.jstdf.record;

import java.nio.ByteBuffer;
import java.util.Date;

import net.sf.jstdf.util.StdfRecordUtils;


public class WaferInformationRecord extends AbstractSTDFRecord
{
	public static final byte Type = 2;
	public static final byte SubType = 10;
	
	public int HEAD_NUM; //U*1 Test head number
	public int SITE_GRP; //U*1 Site group number 255
	public Date START_T; //U*4 Date and time first part tested
	public String WAFER_ID; //C*n Wafer ID length byte = 0
	
	public static final WaferInformationRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		WaferInformationRecord rec = new WaferInformationRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}

	@Override
	public void readContent(ByteBuffer bb)
	{
		HEAD_NUM = StdfRecordUtils.readU1Int(bb);
		SITE_GRP = StdfRecordUtils.readU1Int(bb);
		START_T = StdfRecordUtils.readDate(bb);
		WAFER_ID = StdfRecordUtils.readCnString(bb);
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.WIR;
	}

	@Override
	public String toString()
	{
		return "WaferInformationRecord [HEAD_NUM=" + HEAD_NUM + ", SITE_GRP="
				+ SITE_GRP + ", START_T=" + START_T + ", WAFER_ID=" + WAFER_ID
				+ "]";
	}
}
