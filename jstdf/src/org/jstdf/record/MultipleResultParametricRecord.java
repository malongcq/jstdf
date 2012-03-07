package org.jstdf.record;

import java.nio.ByteBuffer;
import java.util.BitSet;

import org.jstdf.util.StdfRecordUtils;


/**
 * Multiple-Result Parametric Record (MPR)
 * 
 * @author malong
 *
 */
public class MultipleResultParametricRecord extends AbstractSTDFRecord
{
	public static final byte Type = 15;
	public static final byte SubType = 15;
	
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
	 * U*2 Count (j) of PMR indexes 
	 */
	public int RTN_ICNT; //U*2 Count (j) of PMR indexes See note
	/**
	 * U*2 Count (k) of returned results 
	 */
	public int RSLT_CNT; //U*2 Count (k) of returned results See note
	/**
	 * jxN*1 Array of returned states RTN_ICNT = 0 if data missing
	 */
	public BitSet[] RTN_STAT; //jxN*1 Array of returned states RTN_ICNT = 0
	/**
	 * kxR*4 Array of returned results RSLT_CNT = 0 if data missing
	 */
	public double[] RTN_RSLT; //kxR*4 Array of returned results RSLT_CNT = 0
	
	/**
	 * C*n Descriptive text or label length byte = 0 if data missing
	 */
	public String TEST_TXT; //C*n Descriptive text or label length byte = 0
	/**
	 * C*n Name of alarm length byte = 0 if data missing
	 */
	public String ALARM_ID; //C*n Name of alarm length byte = 0
	
	/**
	 * B*1 Optional data flag See note
	 */
	public BitSet OPT_FLAG; //B*1 Optional data flag See note
	/**
	 * I*1 Test result scaling exponent OPT_FLAG bit 0 = 1 if data invalid
	 */
	public int RES_SCAL; //I*1 Test result scaling exponent OPT_FLAG bit 0 = 1
	/**
	 * I*1 Test low limit scaling exponent OPT_FLAG bit 4 or 6 = 1 if data invalid
	 */
	public int LLM_SCAL; //I*1 Test low limit scaling exponent OPT_FLAG bit 4 or 6 = 1
	/**
	 * I*1 Test high limit scaling exponent OPT_FLAG bit 5 or 7 = 1 if data invalid
	 */
	public int HLM_SCAL; //I*1 Test high limit scaling exponent OPT_FLAG bit 5 or 7 = 1
	
	/**
	 * R*4 Test low limit value OPT_FLAG bit 4 or 6 = 1 if data invalid
	 */
	public double LO_LIMIT; //R*4 Test low limit value OPT_FLAG bit 4 or 6 = 1
	/**
	 * R*4 Test high limit value OPT_FLAG bit 5 or 7 = 1 if data invalid
	 */
	public double HI_LIMIT; //R*4 Test high limit value OPT_FLAG bit 5 or 7 = 1
	/**
	 * R*4 Starting input value (condition) OPT_FLAG bit 1 = 1 if data invalid
	 */
	public double START_IN; //R*4 Starting input value (condition) OPT_FLAG bit 1 = 1
	/**
	 * R*4 Increment of input condition OPT_FLAG bit 1 = 1 if data invalid
	 */
	public double INCR_IN; //R*4 Increment of input condition OPT_FLAG bit 1 = 1
	/**
	 * jxU*2 Array of PMR indexes RTN_ICNT = 0 if data missing
	 */
	public int[] RTN_INDX; //jxU*2 Array of PMR indexes RTN_ICNT = 0
	/**
	 * C*n Units of returned results length byte = 0 if data missing
	 */
	public String UNITS; //C*n Units of returned results length byte = 0
	/**
	 * C*n Input condition units length byte = 0 if data missing
	 */
	public String UNITS_IN; //C*n Input condition units length byte = 0
	/**
	 * C*n ANSI C result format string length byte = 0 if data missing
	 */
	public String C_RESFMT; //C*n ANSI C result format string length byte = 0
	/**
	 * C*n ANSI C low limit format string length byte = 0 if data missing
	 */
	public String C_LLMFMT; //C*n ANSI C low limit format string length byte = 0
	/**
	 * C*n ANSI C high limit format string length byte = 0 if data missing
	 */
	public String C_HLMFMT; //C*n ANSI C high limit format string length byte = 0
	/**
	 * R*4 Low specification limit value OPT_FLAG bit 2 = 1 if data invalid
	 */
	public double LO_SPEC; //R*4 Low specification limit value OPT_FLAG bit 2 = 1
	/**
	 * R*4 High specification limit value OPT_FLAG bit 3 = 1 if data invalid
	 */
	public double HI_SPEC; //R*4 High specification limit value OPT_FLAG bit 3 = 1
	
	public static final MultipleResultParametricRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		MultipleResultParametricRecord rec = new MultipleResultParametricRecord();
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
		RTN_ICNT = StdfRecordUtils.readU2Int(bb);
		RSLT_CNT = StdfRecordUtils.readU2Int(bb);
		RTN_STAT = StdfRecordUtils.readKNBits(bb, RTN_ICNT);
		RTN_RSLT = StdfRecordUtils.readKR4Double(bb, RSLT_CNT);
		
		TEST_TXT = StdfRecordUtils.readCnString(bb);
		ALARM_ID = StdfRecordUtils.readCnString(bb);
		
		OPT_FLAG = StdfRecordUtils.readBits(bb, 1);
		RES_SCAL = StdfRecordUtils.readI1Int(bb);
		LLM_SCAL = StdfRecordUtils.readI1Int(bb);
		HLM_SCAL = StdfRecordUtils.readI1Int(bb);
		
		LO_LIMIT = StdfRecordUtils.readR4Double(bb);
		HI_LIMIT = StdfRecordUtils.readR4Double(bb);
		START_IN = StdfRecordUtils.readR4Double(bb);
		INCR_IN = StdfRecordUtils.readR4Double(bb);
		RTN_INDX = StdfRecordUtils.readKU2Int(bb, RTN_ICNT);
		
		UNITS = StdfRecordUtils.readCnString(bb);
		UNITS_IN = StdfRecordUtils.readCnString(bb);
		C_RESFMT = StdfRecordUtils.readCnString(bb, null);
		C_LLMFMT = StdfRecordUtils.readCnString(bb, null);
		C_HLMFMT = StdfRecordUtils.readCnString(bb, null);
		
		LO_SPEC = StdfRecordUtils.readR4Double(bb);
		HI_SPEC = StdfRecordUtils.readR4Double(bb);
	}

	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.MPR;
	}
	
	public boolean isValidResult()
	{
		return !(TEST_FLG.get(0)||TEST_FLG.get(2)||TEST_FLG.get(3)||TEST_FLG.get(4)||TEST_FLG.get(5)); 
	}
	
	public boolean isPass()
	{
		return !TEST_FLG.get(7);
	}
}
