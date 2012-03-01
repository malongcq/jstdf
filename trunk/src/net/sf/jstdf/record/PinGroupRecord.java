package net.sf.jstdf.record;

import java.nio.ByteBuffer;

import net.sf.jstdf.util.StdfRecordUtils;


public class PinGroupRecord extends AbstractSTDFRecord
{
	public static final byte Type = 1;
	public static final byte SubType = 62;
	
	public int GRP_INDX; //U*2 Unique index associated with pin group
	public String GRP_NAM; //C*n Name of pin group length byte = 0
	public int INDX_CNT; //U*2 Count (k) of PMR indexes
	public int[] PMR_INDX; //kxU*2 Array of indexes for pins in the group INDX_CNT = 0
	
	public static final PinGroupRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		PinGroupRecord rec = new PinGroupRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public void readContent(ByteBuffer bb)
	{
		GRP_INDX = StdfRecordUtils.readU2Int(bb);
		GRP_NAM = StdfRecordUtils.readCnString(bb);
		INDX_CNT = StdfRecordUtils.readU2Int(bb);
		PMR_INDX = StdfRecordUtils.readKU2Int(bb, INDX_CNT);
	}

	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.PGR;
	}
}