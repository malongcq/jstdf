package org.jstdf.record;

import java.nio.ByteBuffer;
import java.util.BitSet;

import org.jstdf.util.StdfRecordUtils;


/**
 * Parametric Test Record (PTR)
 * 
 * @author malong
 *
 */
public class ParametricTestRecord extends AbstractSTDFRecord
{
	/**
	 * 15
	 */
	public static final byte Type = 15;
	/**
	 * 10
	 */
	public static final byte SubType = 10;
	
	/**
	 * U*4 Test number
	 */
	public long TEST_NUM; //U*4 Test number
	/**
	 * U*1 Test head number
	 */
	public int HEAD_NUM; //U*1 Test head number
	/**
	 * U*1 Test site number
	 */
	public int SITE_NUM; //U*1 Test site number
	/**
	 * B*1 Test flags (fail, alarm, etc.)
	 */
	public BitSet TEST_FLG; //B*1 Test flags (fail, alarm, etc.)
	/**
	 * B*1 Parametric test flags (drift, etc.)
	 */
	public BitSet PARM_FLG; //B*1 Parametric test flags (drift, etc.)
	/**
	 * R*4 Test result TEST_FLG bit 1 = 1
	 */
	public double RESULT; //R*4 Test result TEST_FLG bit 1 = 1
	/**
	 * C*n Test description text or label length byte = 0
	 */
	public String TEST_TXT; //C*n Test description text or label length byte = 0
	/**
	 * C*n Name of alarm length byte = 0
	 */
	public String ALARM_ID; //C*n Name of alarm length byte = 0
	
	/**
	 * B*1 Optional data flag 
	 */
	public BitSet OPT_FLAG; //B*1 Optional data flag See note
	
	/**
	 * I*1 Test results scaling exponent OPT_FLAG bit 0 = 1
	 */
	public int RES_SCAL; //I*1 Test results scaling exponent OPT_FLAG bit 0 = 1
	/**
	 * I*1 Low limit scaling exponent OPT_FLAG bit 4 or 6 = 1
	 */
	public int LLM_SCAL; //I*1 Low limit scaling exponent OPT_FLAG bit 4 or 6 = 1
	/**
	 * I*1 High limit scaling exponent OPT_FLAG bit 5 or 7 = 1
	 */
	public int HLM_SCAL; //I*1 High limit scaling exponent OPT_FLAG bit 5 or 7 = 1
	
	/**
	 * R*4 Low test limit value OPT_FLAG bit 4 or 6 = 1
	 */
	public double LO_LIMIT; //R*4 Low test limit value OPT_FLAG bit 4 or 6 = 1
	/**
	 * R*4 High test limit value OPT_FLAG bit 5 or 7 = 1
	 */
	public double HI_LIMIT; //R*4 High test limit value OPT_FLAG bit 5 or 7 = 1
	/**
	 * C*n Test units length byte = 0
	 */
	public String UNITS; //C*n Test units length byte = 0
	/**
	 * C*n ANSI C result format string length byte = 0
	 */
	public String C_RESFMT; //C*n ANSI C result format string length byte = 0
	/**
	 * C*n ANSI C low limit format string length byte = 0
	 */
	public String C_LLMFMT; //C*n ANSI C low limit format string length byte = 0
	/**
	 * C*n ANSI C high limit format string length byte = 0
	 */
	public String C_HLMFMT; //C*n ANSI C high limit format string length byte = 0
	/**
	 * R*4 Low specification limit value OPT_FLAG bit 2 = 1
	 */
	public double LO_SPEC; //R*4 Low specification limit value OPT_FLAG bit 2 = 1
	/**
	 * R*4 High specification limit value OPT_FLAG bit 3 = 1
	 */
	public double HI_SPEC; //R*4 High specification limit value OPT_FLAG bit 3 = 1
	
	public static final ParametricTestRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		ParametricTestRecord rec = new ParametricTestRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public void readContent(ByteBuffer bb)
	{
		TEST_NUM = StdfRecordUtils.readU4Int(bb);
		HEAD_NUM = StdfRecordUtils.readU1Int(bb);
		SITE_NUM = StdfRecordUtils.readU1Int(bb);
		TEST_FLG = StdfRecordUtils.readBits(bb, 1);
		PARM_FLG = StdfRecordUtils.readBits(bb, 1);
		RESULT = StdfRecordUtils.readR4Double(bb);
		TEST_TXT = StdfRecordUtils.readCnString(bb);
		ALARM_ID = StdfRecordUtils.readCnString(bb);
		
		OPT_FLAG = StdfRecordUtils.readBits(bb, 1);
		RES_SCAL = StdfRecordUtils.readI1Int(bb);
		LLM_SCAL = StdfRecordUtils.readI1Int(bb);
		HLM_SCAL = StdfRecordUtils.readI1Int(bb);
		LO_LIMIT = StdfRecordUtils.readR4Double(bb);
		HI_LIMIT = StdfRecordUtils.readR4Double(bb);
		UNITS = StdfRecordUtils.readCnString(bb);
		C_RESFMT = StdfRecordUtils.readCnString(bb, null);
		C_LLMFMT = StdfRecordUtils.readCnString(bb, null);
		C_HLMFMT = StdfRecordUtils.readCnString(bb, null);
		LO_SPEC = StdfRecordUtils.readR4Double(bb);
		HI_SPEC = StdfRecordUtils.readR4Double(bb);
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.PTR;
	}
	
	public boolean isValidResult()
	{
		return TEST_FLG.get(0, 6).cardinality()==0 && PARM_FLG.get(0, 3).cardinality()==0; 
	}
	
	public boolean isPass()
	{
		return !TEST_FLG.get(7);
	}
}
