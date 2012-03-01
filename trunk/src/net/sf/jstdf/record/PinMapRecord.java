package net.sf.jstdf.record;

import java.nio.ByteBuffer;

import net.sf.jstdf.util.StdfRecordUtils;


public class PinMapRecord extends AbstractSTDFRecord
{
	public static final byte Type = 1;
	public static final byte SubType = 60;
	
	public int PMR_INDX; //U*2 Unique index associated with pin
	public int CHAN_TYP; //U*2 Channel type 0
	public String CHAN_NAM; //C*n Channel name length byte = 0
	public String PHY_NAM; //C*n Physical name of pin length byte = 0
	public String LOG_NAM; //C*n Logical name of pin length byte = 0
	public int HEAD_NUM; //U*1 Head number associated with channel 1
	public int SITE_NUM; //U*1 Site number associated with channel 1
	
	public static final PinMapRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		PinMapRecord rec = new PinMapRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}

	@Override
	public void readContent(ByteBuffer bb)
	{
		PMR_INDX = StdfRecordUtils.readU2Int(bb);
		CHAN_TYP = StdfRecordUtils.readU2Int(bb);
		CHAN_NAM = StdfRecordUtils.readCnString(bb);
		PHY_NAM = StdfRecordUtils.readCnString(bb);
		LOG_NAM = StdfRecordUtils.readCnString(bb);
		HEAD_NUM = StdfRecordUtils.readU1Int(bb, 1);
		SITE_NUM = StdfRecordUtils.readU1Int(bb, 1);
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.PMR;
	}
}
