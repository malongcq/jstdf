package net.sf.jstdf.record;

import java.nio.ByteBuffer;
import java.util.List;

import net.sf.jstdf.util.StdfRecordUtils;


public class GenericDataRecord extends AbstractSTDFRecord
{
	public static final byte Type = 50;
	public static final byte SubType = 10;
	
	public int FLD_CNT; //U*2 Count of data fields in record
	public List<GenericDataItem> GEN_DATA; //V*n Data type code and data for one field
	
	@Override
	public void readContent(ByteBuffer bb)
	{
		FLD_CNT = StdfRecordUtils.readU2Int(bb);
		GEN_DATA = StdfRecordUtils.readVnList(bb, FLD_CNT);
	}

	public static final GenericDataRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		GenericDataRecord rec = new GenericDataRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.GDR;
	}
}
