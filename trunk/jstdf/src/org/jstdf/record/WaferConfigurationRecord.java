package org.jstdf.record;

import java.nio.ByteBuffer;

import org.jstdf.util.StdfRecordUtils;


/**
 * Wafer Configuration Record (WCR)
 * 
 * @author malong
 *
 */
public class WaferConfigurationRecord extends AbstractSTDFRecord
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6378235238335122620L;
	/**
	 * 2
	 */
	public static final byte Type = 2;
	/**
	 * 30
	 */
	public static final byte SubType = 30;
	
	/**
	 * R*4 Diameter of wafer in WF_UNITS, 0 if data missing
	 */
	public double WAFR_SIZ; //R*4 Diameter of wafer in WF_UNITS 0
	/**
	 * R*4 Height of die in WF_UNITS, 0 if data missing
	 */
	public double DIE_HT; //R*4 Height of die in WF_UNITS 0
	/**
	 * R*4 Width of die in WF_UNITS, 0 if data missing
	 */
	public double DIE_WID; //R*4 Width of die in WF_UNITS 0
	/**
	 * U*1 Units for wafer and die dimensions, 0 if data missing
	 */
	public int WF_UNITS; //U*1 Units for wafer and die dimensions 0
	/**
	 * C*1 Orientation of wafer flat space
	 */
	public char WF_FLAT; //C*1 Orientation of wafer flat space
	/**
	 * I*2 X coordinate of center die on wafer, -32768 if data missing
	 */
	public int CENTER_X; //I*2 X coordinate of center die on wafer -32768
	/**
	 * I*2 Y coordinate of center die on wafer, -32768 if data missing
	 */
	public int CENTER_Y; //I*2 Y coordinate of center die on wafer -32768
	/**
	 * C*1 Positive X direction of wafer space
	 */
	public char POS_X; //C*1 Positive X direction of wafer space
	/**
	 * C*1 Positive Y direction of wafer space
	 */
	public char POS_Y; //C*1 Positive Y direction of wafer space
	
	public static final WaferConfigurationRecord getInstance(int seq, int len, int typ, int sub, ByteBuffer bb)
	{
		WaferConfigurationRecord rec = new WaferConfigurationRecord();
		rec.setHeadInfo(seq, len, typ, sub);
		rec.readContent(bb);
		return rec;
	}

	@Override
	public void readContent(ByteBuffer bb)
	{
		WAFR_SIZ = StdfRecordUtils.readR4Double(bb);
		DIE_HT = StdfRecordUtils.readR4Double(bb);
		DIE_WID = StdfRecordUtils.readR4Double(bb);
		WF_UNITS = StdfRecordUtils.readU1Int(bb);
		WF_FLAT = StdfRecordUtils.readC1Char(bb);
		CENTER_X = StdfRecordUtils.readI2Int(bb, -32768);
		CENTER_Y = StdfRecordUtils.readI2Int(bb, -32768);
		POS_X = StdfRecordUtils.readC1Char(bb);
		POS_Y = StdfRecordUtils.readC1Char(bb);
	}

	public int getDirectionFactorX()
	{
		int x_dir = 1;
		switch(POS_X)
		{
		case 'L':
			x_dir = -1;
			break;
		case 'R':
			x_dir = 1;
			break;
		default:
			x_dir = 1;
		}
		
		return x_dir;
	}
	
	public int getDirectionFactorY()
	{
		int y_dir = 1;
		switch(POS_Y)
		{
		case 'U':
			y_dir = -1;
			break;
		case 'D':
			y_dir = 1;
			break;
		default:
			y_dir = 1;
		}
		
		return y_dir;
	}
	
	public WaferFlatOrientation getWaferFlatOrientation()
	{
		WaferFlatOrientation ort = WaferFlatOrientation.DOWN;
		switch(WF_FLAT)
		{
		case 'U':
			ort = WaferFlatOrientation.UP;
			break;
		case 'D':
			ort = WaferFlatOrientation.DOWN;
			break;
		case 'L':
			ort = WaferFlatOrientation.LEFT;
			break;
		case 'R':
			ort = WaferFlatOrientation.RIGHT;
			break;
		}
		
		return ort;
	}
	
	@Override
	public STDFRecordType getRecordType()
	{
		return STDFRecordType.WCR;
	}

	@Override
	public String toString()
	{
		return "WaferConfigurationRecord [WAFR_SIZ=" + WAFR_SIZ + ", DIE_HT="
				+ DIE_HT + ", DIE_WID=" + DIE_WID + ", WF_UNITS=" + WF_UNITS
				+ ", WF_FLAT=" + WF_FLAT + ", CENTER_X=" + CENTER_X
				+ ", CENTER_Y=" + CENTER_Y + ", POS_X=" + POS_X + ", POS_Y="
				+ POS_Y + "]";
	}
}
