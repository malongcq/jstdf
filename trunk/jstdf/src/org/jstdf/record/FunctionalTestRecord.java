package org.jstdf.record;

import java.nio.ByteBuffer;
import java.util.BitSet;

import org.jstdf.util.StdfRecordUtils;


/**
 * Functional Test Record (FTR)
 * 
 * @author malong
 *
 */
public class FunctionalTestRecord extends AbstractSTDFRecord
{
	/**
	 * 15
	 */
	public static final byte Type = 15;
	/**
	 * 20
	 */
	public static final byte SubType = 20;
	
	/**
	 * U*4 Test number
	 */
	public long TEST_NUM; //U*4 Test number
	
	/**
	 * U*1 Test head number
	 */
	public int HEAD_NUM; // U*1 Test head number
	
	/**
	 * U*1 Test site number
	 */
	public int SITE_NUM; // U*1 Test site number
	
	/**
	 * B*1 Test flags (fail, alarm, etc.)
	 */
	public BitSet TEST_FLG; // B*1 Test flags (fail, alarm, etc.)
	
	/**
	 * B*1 Optional data flag See note
	 */
	public BitSet OPT_FLAG; // B*1 Optional data flag See note
	
	/**
	 * U*4 Cycle count of vector OPT_FLAG bit 0 = 1 if data invalid
	 */
	public long CYCL_CNT; // U*4 Cycle count of vector OPT_FLAG bit 0 = 1 
	
	/**
	 * U*4 Relative vector address OPT_FLAG bit 1 = 1 if data invalid
	 */
	public long REL_VADR; // U*4 Relative vector address OPT_FLAG bit 1 = 1 
	
	/**
	 * U*4 Repeat count of vector OPT_FLAG bit 2 = 1 if data invalid
	 */
	public long REPT_CNT; // U*4 Repeat count of vector OPT_FLAG bit 2 = 1
	
	/**
	 * U*4 Number of pins with 1 or more failures OPT_FLAG bit 3 = 1 if data invalid
	 */
	public long NUM_FAIL; // U*4 Number of pins with 1 or more failures OPT_FLAG bit 3 = 1
	
	/**
	 * I*4 X logical device failure address OPT_FLAG bit 4 = 1 if data invalid
	 */
	public int XFAIL_AD; // I*4 X logical device failure address OPT_FLAG bit 4 = 1
	
	/**
	 * I*4 Y logical device failure address OPT_FLAG bit 4 = 1 if data invalid
	 */
	public int YFAIL_AD; // I*4 Y logical device failure address OPT_FLAG bit 4 = 1
	
	/**
	 * I*2 Offset from vector of interest OPT_FLAG bit 5 = 1 if data invalid
	 */
	public int VECT_OFF; // I*2 Offset from vector of interest OPT_FLAG bit 5 = 1
	
	/**
	 * U*2 Count (j) of return data PMR indexes 
	 */
	public int RTN_ICNT; // U*2 Count (j) of return data PMR indexes 
	
	/**
	 * U*2 Count (k) of programmed state indexes 
	 */
	public int PGM_ICNT; // U*2 Count (k) of programmed state indexes 
	
	/**
	 * jxU*2 Array of return data PMR indexes RTN_ICNT = 0 if data missing
	 */
	public int[] RTN_INDX; // jxU*2 Array of return data PMR indexes RTN_ICNT = 0 
	
	/**
	 * jxN*1 Array of returned states RTN_ICNT = 0 if data missing
	 */
	public BitSet[] RTN_STAT; // jxN*1 Array of returned states RTN_ICNT = 0 
	
	/**
	 * kxU*2 Array of programmed state indexes PGM_ICNT = 0 if data missing
	 */
	public int[] PGM_INDX; // kxU*2 Array of programmed state indexes PGM_ICNT = 0 
	
	/**
	 * kxN*1 Array of programmed states PGM_ICNT = 0 if data missing
	 */
	public BitSet[] PGM_STAT; // kxN*1 Array of programmed states PGM_ICNT = 0 
	
	/**
	 * D*n Failing pin bitfield length bytes = 0 if data missing
	 */
	public BitSet FAIL_PIN; // D*n Failing pin bitfield length bytes = 0 
	
	/**
	 * C*n Vector module pattern name length byte = 0 if data missing
	 */
	public String VECT_NAM; // C*n Vector module pattern name length byte = 0 
	
	/**
	 * C*n Time set name length byte = 0 if data missing
	 */
	public String TIME_SET; // C*n Time set name length byte = 0 
	
	/**
	 * C*n Vector Op Code length byte = 0 if data missing
	 */
	public String OP_CODE; // C*n Vector Op Code length byte = 0 
	
	/**
	 * C*n Descriptive text or label length byte = 0 if data missing
	 */
	public String TEST_TXT; // C*n Descriptive text or label length byte = 0 
	
	/**
	 * C*n Name of alarm length byte = 0 if data missing
	 */
	public String ALARM_ID; // C*n Name of alarm length byte = 0 
	
	/**
	 * C*n Additional programmed information length byte = 0 if data missing
	 */
	public String PROG_TXT; // C*n Additional programmed information length byte = 0 
	
	/**
	 * C*n Additional result information length byte = 0 if data missing
	 */
	public String RSLT_TXT; // C*n Additional result information length byte = 0
	
	/**
	 * U*1 Pattern generator number 255 if data missing
	 */
	public int PATG_NUM; // U*1 Pattern generator number 255
	
	/**
	 * D*n Bit map of enabled comparators length byte = 0 if data missing
	 */
	public BitSet SPIN_MAP; // D*n Bit map of enabled comparators length byte = 0
	
	public static final FunctionalTestRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		FunctionalTestRecord rec = new FunctionalTestRecord();
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
		OPT_FLAG = StdfRecordUtils.readBits(bb, 1);
		
		CYCL_CNT = StdfRecordUtils.readU4Int(bb);
		REL_VADR = StdfRecordUtils.readU4Int(bb);
		REPT_CNT = StdfRecordUtils.readU4Int(bb);
		NUM_FAIL = StdfRecordUtils.readU4Int(bb);
		
		XFAIL_AD = StdfRecordUtils.readI4Int(bb);
		YFAIL_AD = StdfRecordUtils.readI4Int(bb);
		VECT_OFF = StdfRecordUtils.readI2Int(bb);
		
		RTN_ICNT = StdfRecordUtils.readU2Int(bb);
		PGM_ICNT = StdfRecordUtils.readU2Int(bb);
		RTN_INDX = StdfRecordUtils.readKU2Int(bb, RTN_ICNT);
		RTN_STAT = StdfRecordUtils.readKNBits(bb, RTN_ICNT);
		PGM_INDX = StdfRecordUtils.readKU2Int(bb, PGM_ICNT);
		PGM_STAT = StdfRecordUtils.readKNBits(bb, PGM_ICNT);
		
		FAIL_PIN = StdfRecordUtils.readDnBits(bb);
		
		VECT_NAM = StdfRecordUtils.readCnString(bb);
		TIME_SET = StdfRecordUtils.readCnString(bb);
		OP_CODE = StdfRecordUtils.readCnString(bb);
		TEST_TXT = StdfRecordUtils.readCnString(bb);
		ALARM_ID = StdfRecordUtils.readCnString(bb);
		PROG_TXT = StdfRecordUtils.readCnString(bb);
		RSLT_TXT = StdfRecordUtils.readCnString(bb);
		
		PATG_NUM = StdfRecordUtils.readU1Int(bb);
		SPIN_MAP = StdfRecordUtils.readDnBits(bb);
	}

	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.FTR;
	}
	
	public boolean isValidResult()
	{
		return !(TEST_FLG.get(0)||TEST_FLG.get(2)||TEST_FLG.get(3)||TEST_FLG.get(4)||TEST_FLG.get(5)); 
	}
}
