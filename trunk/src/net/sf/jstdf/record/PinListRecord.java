package net.sf.jstdf.record;

import java.nio.ByteBuffer;

import net.sf.jstdf.util.StdfRecordUtils;

/**
 * Pin List Record (PLR)
 * 
 * @author malong
 *
 */
public class PinListRecord extends AbstractSTDFRecord
{
	public static final byte Type = 1;
	public static final byte SubType = 63;
	
	/**
	 * U*2 Count (k) of pins or pin groups
	 */
	public int GRP_CNT; //U*2 Count (k) of pins or pin groups
	/**
	 * kxU*2 Array of pin or pin group indexes
	 */
	public int[] GRP_INDX; //kxU*2 Array of pin or pin group indexes
	/**
	 * kxU*2 Operating mode of pin group, 0 if data missing
	 */
	public int[] GRP_MODE; //kxU*2 Operating mode of pin group 0
	/**
	 * kxU*1 Display radix of pin group, 0 if data missing
	 */
	public int[] GRP_RADX; //kxU*1 Display radix of pin group 0
	/**
	 * kxC*n Program state encoding characters length byte = 0 if data missing
	 */
	public String[] PGM_CHAR; //kxC*n Program state encoding characters length byte = 0
	/**
	 * kxC*n Return state encoding characters length byte = 0 if data missing
	 */
	public String[] RTN_CHAR; //kxC*n Return state encoding characters length byte = 0
	/**
	 * kxC*n Program state encoding characters length byte = 0 if data missing
	 */
	public String[] PGM_CHAL; //kxC*n Program state encoding characters length byte = 0
	/**
	 * kxC*n Return state encoding characters length byte = 0 if data missing
	 */
	public String[] RTN_CHAL; //kxC*n Return state encoding characters length byte = 0
	
	public static final PinListRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		PinListRecord rec = new PinListRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}

	@Override
	public void readContent(ByteBuffer bb)
	{
		GRP_CNT = StdfRecordUtils.readU2Int(bb);
		GRP_INDX = StdfRecordUtils.readKU2Int(bb, GRP_CNT);
		GRP_MODE = StdfRecordUtils.readKU2Int(bb, GRP_CNT);
		GRP_RADX = StdfRecordUtils.readKU1Int(bb, GRP_CNT);
		
		PGM_CHAR = StdfRecordUtils.readKCnString(bb, GRP_CNT);
		RTN_CHAR = StdfRecordUtils.readKCnString(bb, GRP_CNT);
		PGM_CHAL = StdfRecordUtils.readKCnString(bb, GRP_CNT);
		RTN_CHAL = StdfRecordUtils.readKCnString(bb, GRP_CNT);
	}

	public final static String getMode(int m)
	{
		String s = "not found";
		switch(m)
		{
		case 0: //= Unknown
			s = "unknown";
			break;
		case 10: //= Normal
			s = "Normal";
			break;
		case 20: //= SCIO (Same Cycle I/O)
			s = "SCIO (Same Cycle I/O)";
			break;
		case 21: //= SCIO Midband
			s = "SCIO Midband";
			break;
		case 22: //= SCIO Valid
			s = "SCIO Valid";
			break;
		case 23: //= SCIOWindow Sustain
			s = "SCIOWindow Sustain";
			break;
		case 30: //= Dual drive (two drive bits per cycle)
			s = "Dual drive (two drive bits per cycle)";
			break;
		case 31: //= Dual drive Midband
			s = "Dual drive Midband";
			break;
		case 32: //= Dual drive Valid
			s = "Dual drive Valid";
			break;
		case 33: //= Dual drive Window Sustain
			s = "Dual drive Window Sustain";
			break;
		}
		
		return s;
	}
	
	public final static String getDisplayRadix(int d)
	{
		String s = "not found";
		switch(d)
		{
		case 0: //= Use display program default
			s = "Default";
			break;
		case 2: //= Display in Binary
			s = "Binary";
			break;
		case 8: //= Display in Octal
			s = "Octal";
			break;
		case 10: //= Display in Decimal
			s = "Decimal";
			break;
		case 16: //= Display in Hexadecimal
			s = "Hexadecimal";
			break;
		case 20: //= Display as symbolic
			s = "Symbolic";
			break;
		}
		
		return s;
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.PLR;
	}
}
