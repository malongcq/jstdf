package net.sf.jstdf.record;

import java.nio.ByteBuffer;

/**
 * End Program Section Record (EPS)
 * 
 * @author malong
 *
 */
public class EndProgramSectionRecord extends AbstractSTDFRecord
{
	public static final byte Type = 20;
	public static final byte SubType = 10;
	
	@Override
	public void readContent(ByteBuffer bb)
	{
	}

	public static final EndProgramSectionRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		EndProgramSectionRecord rec = new EndProgramSectionRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.EPS;
	}
}
