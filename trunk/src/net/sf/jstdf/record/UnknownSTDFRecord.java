package net.sf.jstdf.record;

import java.nio.ByteBuffer;

/**
 * Represents unknown STDF record type 
 * 
 * @author malong
 *
 */
public class UnknownSTDFRecord extends AbstractSTDFRecord
{
	public byte[] Raw_Bytes;
	
	public static final UnknownSTDFRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		UnknownSTDFRecord rec = new UnknownSTDFRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}

	@Override
	public void readContent(ByteBuffer bb)
	{
		Raw_Bytes = bb.array();
	}

	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.Unknown;
	}
}
