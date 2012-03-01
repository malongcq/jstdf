package net.sf.jstdf.record;

import java.nio.ByteBuffer;
import java.util.Arrays;

import net.sf.jstdf.util.StdfRecordUtils;

/**
 * Site Description Record (SDR)
 * 
 * @author malong
 *
 */
public class SiteDescriptionRecord extends AbstractSTDFRecord
{
	public static final byte Type = 1;
	public static final byte SubType = 80;
	
	public int HEAD_NUM; //U*1 Test head number
	public int SITE_GRP; //U*1 Site group number
	public int SITE_CNT; //U*1 Number (k) of test sites in site group
	public int[] SITE_NUM; //kxU*1 Array of test site numbers
	public String HAND_TYP; //C*n Handler or prober type length byte = 0
	public String HAND_ID; //C*n Handler or prober ID length byte = 0
	public String CARD_TYP; //C*n Probe card type length byte = 0
	public String CARD_ID; //C*n Probe card ID length byte = 0
	public String LOAD_TYP; //C*n Load board type length byte = 0
	public String LOAD_ID; //C*n Load board ID length byte = 0
	public String DIB_TYP; //C*n DIB board type length byte = 0
	public String DIB_ID; //C*n DIB board ID length byte = 0
	public String CABL_TYP; //C*n Interface cable type length byte = 0
	public String CABL_ID; //C*n Interface cable ID length byte = 0
	public String CONT_TYP; //C*n Handler contactor type length byte = 0
	public String CONT_ID; //C*n Handler contactor ID length byte = 0
	public String LASR_TYP; //C*n Laser type length byte = 0
	public String LASR_ID; //C*n Laser ID length byte = 0
	public String EXTR_TYP; //C*n Extra equipment type field length byte = 0
	public String EXTR_ID; //C*n Extra equipment ID length byte = 0
	
	public static final SiteDescriptionRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		SiteDescriptionRecord rec = new SiteDescriptionRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}

	@Override
	public void readContent(ByteBuffer bb)
	{
		HEAD_NUM = StdfRecordUtils.readU1Int(bb);
		SITE_GRP = StdfRecordUtils.readU1Int(bb);
		SITE_CNT = StdfRecordUtils.readU1Int(bb);
		SITE_NUM = StdfRecordUtils.readKU1Int(bb, SITE_CNT);
		
		HAND_TYP = StdfRecordUtils.readCnString(bb);
		HAND_ID = StdfRecordUtils.readCnString(bb);
		CARD_TYP = StdfRecordUtils.readCnString(bb);
		CARD_ID = StdfRecordUtils.readCnString(bb);
		LOAD_TYP = StdfRecordUtils.readCnString(bb);
		LOAD_ID = StdfRecordUtils.readCnString(bb);
		DIB_TYP = StdfRecordUtils.readCnString(bb);
		DIB_ID = StdfRecordUtils.readCnString(bb);
		CABL_TYP = StdfRecordUtils.readCnString(bb);
		CABL_ID = StdfRecordUtils.readCnString(bb);
		CONT_TYP = StdfRecordUtils.readCnString(bb);
		CONT_ID = StdfRecordUtils.readCnString(bb);
		LASR_TYP = StdfRecordUtils.readCnString(bb);
		LASR_ID = StdfRecordUtils.readCnString(bb);
		EXTR_TYP = StdfRecordUtils.readCnString(bb);
		EXTR_ID = StdfRecordUtils.readCnString(bb);
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.SDR;
	}

	@Override
	public String toString()
	{
		return "SiteDescriptionRecord [HEAD_NUM=" + HEAD_NUM + ", SITE_GRP="
				+ SITE_GRP + ", SITE_CNT=" + SITE_CNT + ", SITE_NUM="
				+ Arrays.toString(SITE_NUM) + ", HAND_TYP=" + HAND_TYP
				+ ", HAND_ID=" + HAND_ID + ", CARD_TYP=" + CARD_TYP
				+ ", CARD_ID=" + CARD_ID + ", LOAD_TYP=" + LOAD_TYP
				+ ", LOAD_ID=" + LOAD_ID + ", DIB_TYP=" + DIB_TYP + ", DIB_ID="
				+ DIB_ID + ", CABL_TYP=" + CABL_TYP + ", CABL_ID=" + CABL_ID
				+ ", CONT_TYP=" + CONT_TYP + ", CONT_ID=" + CONT_ID
				+ ", LASR_TYP=" + LASR_TYP + ", LASR_ID=" + LASR_ID
				+ ", EXTR_TYP=" + EXTR_TYP + ", EXTR_ID=" + EXTR_ID + "]";
	}
}
