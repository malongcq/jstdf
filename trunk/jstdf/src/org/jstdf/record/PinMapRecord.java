package org.jstdf.record;

import java.nio.ByteBuffer;

import org.jstdf.util.StdfRecordUtils;


/**
 * Pin Map Record (PMR)
 * 
 * @author malong
 *
 */
public class PinMapRecord extends AbstractSTDFRecord
{
	/**
	 * 1
	 */
	public static final byte Type = 1;
	/**
	 * 60
	 */
	public static final byte SubType = 60;
	
	/**
	 * U*2 Unique index associated with pin
	 */
	public int PMR_INDX; //U*2 Unique index associated with pin
	/**
	 * U*2 Channel type, 0 if data missing
	 */
	public int CHAN_TYP; //U*2 Channel type 0
	/**
	 * C*n Channel name length byte = 0 if data missing
	 */
	public String CHAN_NAM; //C*n Channel name length byte = 0
	/**
	 * C*n Physical name of pin length byte = 0 if data missing
	 */
	public String PHY_NAM; //C*n Physical name of pin length byte = 0
	/**
	 * C*n Logical name of pin length byte = 0 if data missing
	 */
	public String LOG_NAM; //C*n Logical name of pin length byte = 0
	/**
	 * U*1 Head number associated with channel, 1 if data missing
	 */
	public int HEAD_NUM; //U*1 Head number associated with channel 1
	/**
	 * U*1 Site number associated with channel, 1 if data missing
	 */
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
