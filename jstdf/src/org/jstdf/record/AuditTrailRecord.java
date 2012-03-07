package org.jstdf.record;

import java.nio.ByteBuffer;
import java.util.Date;

import org.jstdf.util.StdfRecordUtils;


/**
 * Audit Trail Record (ATR)
 * 
 * @author malong
 *
 */
public class AuditTrailRecord extends AbstractSTDFRecord
{
	/**
	 * 0
	 */
	public static final byte Type = 0;
	/**
	 * 20
	 */
	public static final byte SubType = 20;
	
	/**
	 * MOD_TIM U*4 Date and time of STDF file modification
	 */
	public Date MOD_TIM;
	
	/**
	 * CMD_LINE C*n Command line of program
	 */
	public String CMD_LINE;
	
	@Override
	public void readContent(ByteBuffer bb)
	{
		MOD_TIM = StdfRecordUtils.readDate(bb);
		CMD_LINE = StdfRecordUtils.readCnString(bb);
	}
	
	public static final AuditTrailRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		AuditTrailRecord rec = new AuditTrailRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.ATR;
	}
}
