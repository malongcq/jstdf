package org.jstdf.record;

import java.nio.ByteBuffer;
import java.util.Date;

import org.jstdf.util.StdfRecordUtils;


/**
 * Master Information Record (MIR)
 * 
 * @author malong
 *
 */
public class MasterInformationRecord extends AbstractSTDFRecord
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 265909535730887767L;
	/**
	 * 1
	 */
	public static final byte Type = 1;
	/**
	 * 10
	 */
	public static final byte SubType = 10;
	
	/**
	 * U*4 Date and time of job setup
	 */
	public Date SETUP_T; //U*4 Date and time of job setup
	/**
	 * U*4 Date and time first part tested
	 */
	public Date START_T; //U*4 Date and time first part tested
	/**
	 * U*1 Tester station number
	 */
	public int STAT_NUM; //U*1 Tester station number
	/**
	 * Test mode code (e.g. prod, dev) space
	 */
	public char MODE_COD; //C*1 Test mode code (e.g. prod, dev) space
	/**
	 * C*1 Lot retest code space
	 */
	public char RTST_COD; //C*1 Lot retest code space
	/**
	 * C*1 Data protection code space
	 */
	public char PROT_COD; //C*1 Data protection code space
	/**
	 * U*2 Burn-in time (in minutes) 65,535 if data missing
	 */
	public int BURN_TIM; //U*2 Burn-in time (in minutes) 65,535
	/**
	 * C*1 Command mode code space
	 */
	public char CMOD_COD; //C*1 Command mode code space
	/**
	 * C*n Lot ID (customer specified)
	 */
	public String LOT_ID; //C*n Lot ID (customer specified)
	/**
	 * C*n Part Type (or product ID)
	 */
	public String PART_TYP; //C*n Part Type (or product ID)
	/**
	 * C*n Name of node that generated data
	 */
	public String NODE_NAM; //C*n Name of node that generated data
	/**
	 * C*n Tester type
	 */
	public String TSTR_TYP; //C*n Tester type
	/**
	 * C*n Job name (test program name)
	 */
	public String JOB_NAM; //C*n Job name (test program name)
	/**
	 * C*n Job (test program) revision number length byte = 0 if data missing
	 */
	public String JOB_REV; //C*n Job (test program) revision number length byte = 0
	/**
	 * C*n Sublot ID length byte = 0 if data missing
	 */
	public String SBLOT_ID; //C*n Sublot ID length byte = 0
	/**
	 * C*n Operator name or ID (at setup time) length byte = 0 if data missing
	 */
	public String OPER_NAM; //C*n Operator name or ID (at setup time) length byte = 0
	/**
	 * C*n Tester executive software type length byte = 0 if data missing
	 */
	public String EXEC_TYP; //C*n Tester executive software type length byte = 0
	/**
	 * C*n Tester exec software version number length byte = 0 if data missing
	 */
	public String EXEC_VER; //C*n Tester exec software version number length byte = 0
	/**
	 * C*n Test phase or step code length byte = 0 if data missing
	 */
	public String TEST_COD; //C*n Test phase or step code length byte = 0
	/**
	 * C*n Test temperature length byte = 0 if data missing
	 */
	public String TST_TEMP; //C*n Test temperature length byte = 0
	/**
	 * C*n Generic user text length byte = 0 if data missing
	 */
	public String USER_TXT; //C*n Generic user text length byte = 0
	/**
	 * C*n Name of auxiliary data file length byte = 0 if data missing
	 */
	public String AUX_FILE; //C*n Name of auxiliary data file length byte = 0
	/**
	 * C*n Package type length byte = 0 if data missing
	 */
	public String PKG_TYP; //C*n Package type length byte = 0
	/**
	 * C*n Product family ID length byte = 0 if data missing
	 */
	public String FAMLY_ID; //C*n Product family ID length byte = 0
	/**
	 * C*n Date code length byte = 0 if data missing
	 */
	public String DATE_COD; //C*n Date code length byte = 0
	/**
	 * C*n Test facility ID length byte = 0 if data missing
	 */
	public String FACIL_ID; //C*n Test facility ID length byte = 0
	/**
	 * C*n Test floor ID length byte = 0 if data missing
	 */
	public String FLOOR_ID; //C*n Test floor ID length byte = 0
	/**
	 * C*n Fabrication process ID length byte = 0 if data missing
	 */
	public String PROC_ID; //C*n Fabrication process ID length byte = 0
	/**
	 * C*n Operation frequency or step length byte = 0 if data missing
	 */
	public String OPER_FRQ; //C*n Operation frequency or step length byte = 0
	/**
	 * C*n Test specification name length byte = 0 if data missing
	 */
	public String SPEC_NAM; //C*n Test specification name length byte = 0
	/**
	 * C*n Test specification version number length byte = 0 if data missing
	 */
	public String SPEC_VER; //C*n Test specification version number length byte = 0
	/**
	 * C*n Test flow ID length byte = 0 if data missing
	 */
	public String FLOW_ID; //C*n Test flow ID length byte = 0
	/**
	 * C*n Test setup ID length byte = 0 if data missing
	 */
	public String SETUP_ID; //C*n Test setup ID length byte = 0
	/**
	 * C*n Device design revision length byte = 0 if data missing
	 */
	public String DSGN_REV; //C*n Device design revision length byte = 0
	/**
	 * C*n Engineering lot ID length byte = 0 if data missing
	 */
	public String ENG_ID; //C*n Engineering lot ID length byte = 0
	/**
	 * C*n ROM code ID length byte = 0 if data missing
	 */
	public String ROM_COD; //C*n ROM code ID length byte = 0
	/**
	 * C*n Tester serial number length byte = 0 if data missing
	 */
	public String SERL_NUM; //C*n Tester serial number length byte = 0
	/**
	 * C*n Supervisor name or ID length byte = 0 if data missing
	 */
	public String SUPR_NAM; //C*n Supervisor name or ID length byte = 0
	
	public static final MasterInformationRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		MasterInformationRecord rec = new MasterInformationRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public void readContent(ByteBuffer bb)
	{
		SETUP_T = StdfRecordUtils.readDate(bb);
		START_T = StdfRecordUtils.readDate(bb);
		
		STAT_NUM = StdfRecordUtils.readU1Int(bb);
		MODE_COD = StdfRecordUtils.readC1Char(bb);
		RTST_COD = StdfRecordUtils.readC1Char(bb);
		PROT_COD = StdfRecordUtils.readC1Char(bb);
		BURN_TIM = StdfRecordUtils.readU2Int(bb);
		CMOD_COD = StdfRecordUtils.readC1Char(bb);
		
		LOT_ID = StdfRecordUtils.readCnString(bb);
		PART_TYP = StdfRecordUtils.readCnString(bb);
		NODE_NAM = StdfRecordUtils.readCnString(bb);
		TSTR_TYP = StdfRecordUtils.readCnString(bb);
		JOB_NAM = StdfRecordUtils.readCnString(bb);
		JOB_REV = StdfRecordUtils.readCnString(bb);
		SBLOT_ID = StdfRecordUtils.readCnString(bb);
		OPER_NAM = StdfRecordUtils.readCnString(bb);
		EXEC_TYP = StdfRecordUtils.readCnString(bb);
		EXEC_VER = StdfRecordUtils.readCnString(bb);
		TEST_COD = StdfRecordUtils.readCnString(bb);
		TST_TEMP = StdfRecordUtils.readCnString(bb);
		USER_TXT = StdfRecordUtils.readCnString(bb);
		AUX_FILE = StdfRecordUtils.readCnString(bb);
		PKG_TYP = StdfRecordUtils.readCnString(bb);
		FAMLY_ID = StdfRecordUtils.readCnString(bb);
		DATE_COD = StdfRecordUtils.readCnString(bb);
		FACIL_ID = StdfRecordUtils.readCnString(bb);
		FLOOR_ID = StdfRecordUtils.readCnString(bb);
		PROC_ID = StdfRecordUtils.readCnString(bb);
		OPER_FRQ = StdfRecordUtils.readCnString(bb);
		SPEC_NAM = StdfRecordUtils.readCnString(bb);
		SPEC_VER = StdfRecordUtils.readCnString(bb);
		FLOW_ID = StdfRecordUtils.readCnString(bb);
		SETUP_ID = StdfRecordUtils.readCnString(bb);
		DSGN_REV = StdfRecordUtils.readCnString(bb);
		ENG_ID = StdfRecordUtils.readCnString(bb);
		ROM_COD = StdfRecordUtils.readCnString(bb);
		SERL_NUM = StdfRecordUtils.readCnString(bb);
		SUPR_NAM = StdfRecordUtils.readCnString(bb);
	}

	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.MIR;
	}

	@Override
	public String toString()
	{
		return getRecordNo()+": MasterInformationRecord [SETUP_T=" + SETUP_T + ", START_T="
				+ START_T + ", STAT_NUM=" + STAT_NUM + ", MODE_COD=" + MODE_COD
				+ ", RTST_COD=" + RTST_COD + ", PROT_COD=" + PROT_COD
				+ ", BURN_TIM=" + BURN_TIM + ", CMOD_COD=" + CMOD_COD
				+ ", LOT_ID=" + LOT_ID + ", PART_TYP=" + PART_TYP
				+ ", NODE_NAM=" + NODE_NAM + ", TSTR_TYP=" + TSTR_TYP
				+ ", JOB_NAM=" + JOB_NAM + ", JOB_REV=" + JOB_REV
				+ ", SBLOT_ID=" + SBLOT_ID + ", OPER_NAM=" + OPER_NAM
				+ ", EXEC_TYP=" + EXEC_TYP + ", EXEC_VER=" + EXEC_VER
				+ ", TEST_COD=" + TEST_COD + ", TST_TEMP=" + TST_TEMP
				+ ", USER_TXT=" + USER_TXT + ", AUX_FILE=" + AUX_FILE
				+ ", PKG_TYP=" + PKG_TYP + ", FAMLY_ID=" + FAMLY_ID
				+ ", DATE_COD=" + DATE_COD + ", FACIL_ID=" + FACIL_ID
				+ ", FLOOR_ID=" + FLOOR_ID + ", PROC_ID=" + PROC_ID
				+ ", OPER_FRQ=" + OPER_FRQ + ", SPEC_NAM=" + SPEC_NAM
				+ ", SPEC_VER=" + SPEC_VER + ", FLOW_ID=" + FLOW_ID
				+ ", SETUP_ID=" + SETUP_ID + ", DSGN_REV=" + DSGN_REV
				+ ", ENG_ID=" + ENG_ID + ", ROM_COD=" + ROM_COD + ", SERL_NUM="
				+ SERL_NUM + ", SUPR_NAM=" + SUPR_NAM + "]";
	}
}
