package org.jstdf.record;

import java.nio.ByteBuffer;
import java.util.Map;

import org.jstdf.util.StdfRecordUtils;


/**
 * Generic Data Record (GDR)
 * 
 * @author malong
 *
 */
public class GenericDataRecord extends AbstractSTDFRecord
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6410255123211275462L;
	/**
	 * 50
	 */
	public static final byte Type = 50;
	/**
	 * 10
	 */
	public static final byte SubType = 10;
	
	/**
	 * U*2 Count of data fields in record
	 */
	public int FLD_CNT; //U*2 Count of data fields in record
	
	/**
	 * V*n Data type code and data for one field
	 */
	public Map<Integer, Object> GEN_DATA; //V*n Data type code and data for one field
	
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

	@Override
	public String toString()
	{
		return "GenericDataRecord [FLD_CNT=" + FLD_CNT + ", GEN_DATA="
				+ GEN_DATA + "]";
	}
}
