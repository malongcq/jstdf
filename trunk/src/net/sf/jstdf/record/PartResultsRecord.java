package net.sf.jstdf.record;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;

import net.sf.jstdf.util.StdfRecordUtils;


public class PartResultsRecord extends AbstractSTDFRecord
{
	public static final byte Type = 5;
	public static final byte SubType = 20;
	
	public int HEAD_NUM; //U*1 Test head number
	public int SITE_NUM; //U*1 Test site number
	public BitSet PART_FLG; //B*1 Part information flag
	public int NUM_TEST; //U*2 Number of tests executed
	public int HARD_BIN; //U*2 Hardware bin number
	public int SOFT_BIN; //U*2 Software bin number 65535
	public int X_COORD; //I*2 (Wafer) X coordinate -32768
	public int Y_COORD; //I*2 (Wafer) Y coordinate -32768
	public long TEST_T; //U*4 Elapsed test time in milliseconds 0
	public String PART_ID; //C*n Part identification length byte = 0
	public String PART_TXT; //C*n Part description text length byte = 0
	public byte[] PART_FIX; //B*n Part repair information length byte = 0
	
	public static final PartResultsRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		PartResultsRecord rec = new PartResultsRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public void readContent(ByteBuffer bb)
	{
		HEAD_NUM = StdfRecordUtils.readU1Int(bb);
		SITE_NUM = StdfRecordUtils.readU1Int(bb);
		PART_FLG = StdfRecordUtils.readBits(bb, 1);
		NUM_TEST = StdfRecordUtils.readU2Int(bb);
		HARD_BIN = StdfRecordUtils.readU2Int(bb);
		SOFT_BIN = StdfRecordUtils.readU2Int(bb, 65535);
		X_COORD = StdfRecordUtils.readI2Int(bb, -32768);
		Y_COORD = StdfRecordUtils.readI2Int(bb, -32768);
		TEST_T = StdfRecordUtils.readU4Int(bb);
		PART_ID = StdfRecordUtils.readCnString(bb);
		PART_TXT = StdfRecordUtils.readCnString(bb);
		PART_FIX = StdfRecordUtils.readBytes(bb);
	}

	public boolean isPartID()
	{
		return PART_FLG.get(0);
	}
	
	public boolean isXYCoord()
	{
		return PART_FLG.get(1);
	}
	
	public boolean isAbnormal()
	{
		return PART_FLG.get(2);
	}
	
	public boolean isFailed()
	{
		return PART_FLG.get(3);
	}
	
	public boolean isNoPassFail()
	{
		return PART_FLG.get(4);
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.PRR;
	}

	@Override
	public String toString()
	{
		return "PartResultsRecord [HEAD_NUM=" + HEAD_NUM + ", SITE_NUM="
				+ SITE_NUM + ", PART_FLG=" + PART_FLG + ", NUM_TEST="
				+ NUM_TEST + ", HARD_BIN=" + HARD_BIN + ", SOFT_BIN="
				+ SOFT_BIN + ", X_COORD=" + X_COORD + ", Y_COORD=" + Y_COORD
				+ ", TEST_T=" + TEST_T + ", PART_ID=" + PART_ID + ", PART_TXT="
				+ PART_TXT + ", PART_FIX=" + Arrays.toString(PART_FIX) + "]";
	}
}