package net.sf.jstdf.record;

import java.nio.ByteBuffer;

import net.sf.jstdf.util.StdfRecordUtils;

/**
 * Datalog Text Record (DTR)
 * 
 * @author malong
 *
 */
public class DatalogTextRecord extends AbstractSTDFRecord
{
	public static final byte Type = 50;
	public static final byte SubType = 30;
	
	public String TEXT_DAT; //C*n ASCII text string
	
	@Override
	public void readContent(ByteBuffer bb)
	{
		TEXT_DAT = StdfRecordUtils.readCnString(bb);
	}

	public static final DatalogTextRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		DatalogTextRecord rec = new DatalogTextRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.DTR;
	}
}
