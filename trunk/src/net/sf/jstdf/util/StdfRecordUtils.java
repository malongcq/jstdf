package net.sf.jstdf.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.List;

import net.sf.jstdf.record.AuditTrailRecord;
import net.sf.jstdf.record.BeginProgramSectionRecord;
import net.sf.jstdf.record.DatalogTextRecord;
import net.sf.jstdf.record.EndProgramSectionRecord;
import net.sf.jstdf.record.FileAttributesRecord;
import net.sf.jstdf.record.FunctionalTestRecord;
import net.sf.jstdf.record.GenericDataItem;
import net.sf.jstdf.record.GenericDataRecord;
import net.sf.jstdf.record.HardwareBinRecord;
import net.sf.jstdf.record.MasterInformationRecord;
import net.sf.jstdf.record.MasterResultsRecord;
import net.sf.jstdf.record.MultipleResultParametricRecord;
import net.sf.jstdf.record.ParametricTestRecord;
import net.sf.jstdf.record.PartCountRecord;
import net.sf.jstdf.record.PartInformationRecord;
import net.sf.jstdf.record.PartResultsRecord;
import net.sf.jstdf.record.PinGroupRecord;
import net.sf.jstdf.record.PinListRecord;
import net.sf.jstdf.record.PinMapRecord;
import net.sf.jstdf.record.RetestDataRecord;
import net.sf.jstdf.record.STDFRecord;
import net.sf.jstdf.record.SiteDescriptionRecord;
import net.sf.jstdf.record.SoftwareBinRecord;
import net.sf.jstdf.record.TestSynopsisRecord;
import net.sf.jstdf.record.UnknownSTDFRecord;
import net.sf.jstdf.record.WaferConfigurationRecord;
import net.sf.jstdf.record.WaferInformationRecord;
import net.sf.jstdf.record.WaferResultsRecord;

/**
 * General utilities for STDF record generation.
 * 
 * @author malong
 *
 */
public class StdfRecordUtils
{
	private StdfRecordUtils() {}
	
	/**
	 * Read variable data type field.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param cnt the number of data items.
	 * @return the list of generic data items.
	 */
	public static final List<GenericDataItem> readVnList(ByteBuffer bb, int cnt)
	{
		List<GenericDataItem> lst = new ArrayList<GenericDataItem>(cnt);
		
		for(int i=0; i<cnt; i++)
		{
			if( !bb.hasRemaining() ) break;
			GenericDataItem item = new GenericDataItem();
			item.Code = readU1Int(bb);
			
			switch(item.Code)
			{
			case 0: //B*0 Special pad field, of length 0 (See note below)
				break;
			case 1: //U*1 One byte unsigned integer
				item.Data = readU1Int(bb);
				break;
			case 2: //U*2 Two byte unsigned integer
				item.Data = readU2Int(bb);
				break;
			case 3: //U*4 Four byte unsigned integer
				item.Data = readU4Int(bb);
				break;
			case 4: //I*1 One byte signed integer
				item.Data = readI1Int(bb);
				break;
			case 5: //I*2 Two byte signed integer
				item.Data = readI2Int(bb);
				break;
			case 6: //I*4 Four byte signed integer
				item.Data = readI4Int(bb);
				break;
			case 7: //R*4 Four byte floating point number
				item.Data = readR4Double(bb);
				break;
			case 8: //R*8 Eight byte floating point number
				item.Data = readR8Double(bb);
				break;
			case 10: //C*n Variable length ASCII character string (first byte is string length in bytes)
				item.Data = readCnString(bb);
				break;
			case 11: //B*n Variable length binary data string (first byte is string length in bytes)
				item.Data = readBytes(bb);
				break;
			case 12: //D*n Bit encoded data (first two bytes of string are length in bits)
				item.Data = readDnBits(bb);
				break;
			case 13: //N*1 Unsigned nibble
				item.Data = readKNBits(bb, 1);
				break;
			}
			
			lst.add(item);
		}
		
		return lst;
	}
	
	/**
	 * Read date from binary data.
	 * The date and time field used in this specification is defined as 
	 * a four byte (32 bit) unsigned integer field measuring the number of seconds 
	 * since midnight on January 1st, 1970.
	 * 
	 * @param bb the buffer which data is read from.
	 * @return the date.
	 */
	public static final Date readDate(ByteBuffer bb)
	{
		if( bb.remaining() < 4 ) return new Date(0);
		int t = bb.getInt(); 
		Date d = new Date((long)t*1000);
		return d;
	}
	
	/**
	 * Read fixed length byte data.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param len the number of bytes to read.
	 * @return the byte array of data.
	 */
	public static final byte[] readBytes(ByteBuffer bb, int len)
	{
		byte[] bytes = new byte[len];
		for(int i=0; i<len; i++)
		{
			bytes[i] = (bb.hasRemaining()) ? bb.get() : 0;
		}
		return bytes;
	}
	
	/**
	 * Read fixed length byte data, 
	 * the number of bytes to read is stored in 1st byte.
	 * 
	 * @param bb the buffer which data is read from.
	 * @return the byte array of data.
	 */
	public static final byte[] readBytes(ByteBuffer bb)
	{
		if( bb.remaining()<1 ) return new byte[0];
		int len = readU1Int(bb);
		return readBytes(bb, len);
	}
	
	/**
	 * Read fixed length bit-encoded data.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param byte_len the number of bytes to read.
	 * @return the bit-encoded data
	 */
	public static final BitSet readBits(ByteBuffer bb, int byte_len)
	{
		return Bytes2Bits(readBytes(bb, byte_len));
	}
	
	/**
	 * Read fixed length bit-encoded data, 
	 * the number of bits to read is stored in first 2 bytes.
	 * 
	 * @param bb the buffer which data is read from.
	 * @return the bit-encoded data
	 */
	public static final BitSet readDnBits(ByteBuffer bb)
	{
		if( bb.remaining()<2 ) return new BitSet(0);
		int len_bits = readU2Int(bb);
		int len_bytes = (len_bits%8==0) ? len_bits/8 : len_bits/8+1;
		return Bytes2Bits(readBytes(bb, len_bytes));
	}
	
	/**
	 * Convert byte array to bit-encoded data.
	 * 
	 * @param bytes the byte array to convert
	 * @return the bit-encoded data 
	 */
	public static final BitSet Bytes2Bits(byte[] bytes)
	{
		BitSet bits = new BitSet(bytes.length*8);
		for(int i=0; i<bytes.length; i++)
		{
			byte d = bytes[i];
			for(int j=0; j<8; j++)
			{
				if( (d & (1<<j)) > 0 )
				{
					bits.set(i*8 + j);
				}
			}
		}
		
		return bits;
	}
	
	/**
	 * Read array of Unsigned integer data stored in a nibble. 
	 * (Nibble = 4 bits of a byte).
	 * 
	 * @param bb the buffer which data is read from.
	 * @param k the number of nibble to read. 
	 * @return the array of bit-encoded data.
	 */
	public static final BitSet[] readKNBits(ByteBuffer bb, int k)
	{
		int len_bytes = (k%2==0) ? k/2 : k/2+1;
		BitSet[] kn = new BitSet[len_bytes*2];
		byte d;
		BitSet bits;
		for(int i=0; i<len_bytes; i++)
		{
			if( bb.hasRemaining() )
			{
				d = bb.get();
				bits = new BitSet(8);
				for(int j=0; j<8; j++)
				{
					if( (d & (1<<j)) > 0 )
					{
						bits.set(j);
					}
				}
				
				kn[i*2] = bits.get(0, 4);
				kn[i*2+1] = bits.get(5, 8);	
			}
			else
			{
				kn[i*2] = new BitSet(4);
				kn[i*2+1] = new BitSet(4);
			}
		}
		return kn;
	}
	
	/**
	 * Read one byte signed integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @return an integer.
	 */
	public static final int readI1Int(ByteBuffer bb)
	{
		return readI1Int(bb, 0);
	}
	
	/**
	 * Read one byte signed integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param default_value the default return value if fail to read.
	 * @return an integer.
	 */
	public static final int readI1Int(ByteBuffer bb, int default_value)
	{
		int d = bb.hasRemaining() ? bb.get() : default_value;
		return d;
	}
	
	/**
	 * Read one byte fixed length character string.
	 * 
	 * @param bb the buffer which data is read from.
	 * @return a character.
	 */
	public static final char readC1Char(ByteBuffer bb)
	{
		return (char)readU1Int(bb, ' ');
	}
			
	/**
	 * Read one byte unsigned integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @return an integer.
	 */
	public static final int readU1Int(ByteBuffer bb)
	{
		return readU1Int(bb, 0);
	}
	
	/**
	 * Read one byte unsigned integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param default_value the default return value if fail to read.
	 * @return an integer.
	 */
	public static final int readU1Int(ByteBuffer bb, int default_value)
	{
		int d = bb.hasRemaining() ? bb.get() & 0xFF : default_value;
		return d;
	}
	
	/**
	 * Read array of one byte unsigned integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param k the number of elements to read.
	 * @return an array of k integers. 
	 */
	public static final int[] readKU1Int(ByteBuffer bb, int k)
	{
		return readKU1Int(bb, k, 0);
	}
	
	/**
	 * Read array of one byte unsigned integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param k the number of elements to read.
	 * @param default_value the default value of array element if fail to read.
	 * @return an array of k integers. 
	 */
	public static final int[] readKU1Int(ByteBuffer bb, int k, int default_value)
	{
		int[] d = new int[k];
		for(int i=0; i<k; i++)
		{
			d[i] = readU1Int(bb, default_value);
		}
		
		return d;
	}
	
	/**
	 * Read two byte signed integer
	 * 
	 * @param bb the buffer which data is read from.
	 * @return an integer.
	 */
	public static final int readI2Int(ByteBuffer bb)
	{
		return readI2Int(bb, 0);
	}
	
	/**
	 * Read two byte signed integer
	 * 
	 * @param bb the buffer which data is read from.
	 * @param default_value the default return value if fail to read.
	 * @return an integer.
	 */
	public static final int readI2Int(ByteBuffer bb, int default_value)
	{
		return (bb.remaining()<2) ? default_value : bb.getShort();
	}
	
	/**
	 * Read two byte unsigned integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @return an integer.
	 */
	public static final int readU2Int(ByteBuffer bb)
	{
		return readU2Int(bb, 0);
	}
	
	/**
	 * Read two byte unsigned integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param default_value the default return value if fail to read.
	 * @return an integer.
	 */
	public static final int readU2Int(ByteBuffer bb, int default_value)
	{
		return (bb.remaining()<2) ? default_value : bb.getShort() & 0xFFFF;
	}
	
	/**
	 * Read array of two byte unsigned integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param k the number of elements to read.
	 * @return an array of k integers. 
	 */
	public static final int[] readKU2Int(ByteBuffer bb, int k)
	{
		return readKU2Int(bb, k, 0);
	}
	
	/**
	 * Read array of two byte unsigned integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param k the number of elements to read.
	 * @param default_value the default value of array element if fail to read.
	 * @return an array of k integers. 
	 */
	public static final int[] readKU2Int(ByteBuffer bb, int k, int default_value)
	{
		int[] d = new int[k];
		for(int i=0; i<k; i++)
		{
			d[i] = readU2Int(bb, default_value);
		}
		
		return d;
	}
	
	/**
	 * Four byte signed integer
	 * @param bb
	 * @return
	 */
	public static final int readI4Int(ByteBuffer bb)
	{
		return readI4Int(bb, 0);
	}
	public static final int readI4Int(ByteBuffer bb, int default_value)
	{	
		return (bb.remaining()<4) ? default_value : bb.getInt();
	}
	
	/**
	 * Four byte unsigned integer
	 * @param bb
	 * @return
	 */
	public static final long readU4Int(ByteBuffer bb)
	{
		return readU4Int(bb, 0);
	}
	public static final long readU4Int(ByteBuffer bb, long default_value)
	{
		return (bb.remaining()<4) ? default_value : bb.getInt() & 0xFFFFFFFFL;
	}
	
	/**
	 * Four byte floating point number
	 * @param bb
	 * @return
	 */
	public static final double readR4Double(ByteBuffer bb)
	{
		return readR4Double(bb, 0.0);
	}
	public static final double readR4Double(ByteBuffer bb, double default_value)
	{
		if(bb.remaining()<4) return default_value;
		float f = bb.getFloat();
		return f;
	}
	
	/**
	 * Array of Four byte floating point number
	 * @param bb
	 * @param k
	 * @return
	 */
	public static final double[] readKR4Double(ByteBuffer bb, int k)
	{
		return readKR4Double(bb, k, 0.0);
	}
	public static final double[] readKR4Double(ByteBuffer bb, int k, double default_value)
	{
		double[] d = new double[k];
		for(int i=0; i<k; i++)
		{
			d[i] = readR4Double(bb, default_value);
		}
		
		return d;
	}
	
	/**
	 * Eight byte floating point number
	 * @param bb
	 * @return
	 */
	public static final double readR8Double(ByteBuffer bb)
	{
		return readR8Double(bb, 0.0);
	}
	public static final double readR8Double(ByteBuffer bb, double default_value)
	{
		if(bb.remaining()<8) return default_value;
		double f = bb.getDouble();
		return f;
	}
	
	/**
	 * Fixed length character string
	 * @param bb
	 * @param len
	 * @return
	 */
	public static final String readCString(ByteBuffer bb, int len)
	{
		if(len<1) return "";
		byte[] d = new byte[len];
		for(int i=0; i<len; i++)
		{
			if(bb.hasRemaining()) d[i] = bb.get();
			else d[i] = 0x20;
		}
		return new String(d);
	}
	
	/**
	 * Variable length character string
	 * @param bb
	 * @return
	 */
	public static final String readCnString(ByteBuffer bb)
	{
		return readCnString(bb, "");
	}
	public static final String readCnString(ByteBuffer bb, String default_value)
	{
		if(!bb.hasRemaining()) return default_value;
		int len = bb.get() & 0xFF;	
		return readCString(bb, len);
	}
	
	/**
	 * Array of Variable length character string
	 * @param bb
	 * @param k
	 * @return
	 */
	public static final String[] readKCnString(ByteBuffer bb, int k)
	{
		return readKCNString(bb, k, "");
	}
	public static final String[] readKCNString(ByteBuffer bb, int k, String default_value)
	{
		String[] d = new String[k];
		for(int i=0; i<k; i++)
		{
			d[i] = readCnString(bb, default_value);
		}
		
		return d;
	}
	
	public static final STDFRecord createSTDFRecord(int seq, int REC_LEN, int REC_TYP, int REC_SUB, ByteBuffer bb)
	{
		STDFRecord rec = null;
		switch(REC_TYP)
		{
		case 0: //Information about the STDF file
			switch(REC_SUB)
			{
			case 10: //10 File Attributes Record (FAR)
				//type = STDFRecordType.FAR;
				rec = FileAttributesRecord.getInstance(seq, REC_LEN, REC_TYP, REC_SUB, bb);
				break;
			case 20: //20 Audit Trail Record (ATR)
				//type = STDFRecordType.ATR;
				rec = AuditTrailRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB, bb);
				break;
			}
			break;
		case 1: //Data collected on a per lot basis
			switch(REC_SUB)
			{
			case 10: //10 Master Information Record (MIR)
				//type = STDFRecordType.MIR;
				rec = MasterInformationRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			case 20: //20 Master Results Record (MRR)
				//type = STDFRecordType.MRR;
				rec = MasterResultsRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			case 30: //30 Part Count Record (PCR)
				//type = STDFRecordType.PCR;
				rec = PartCountRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			case 40: //40 Hardware Bin Record (HBR)
				//type = STDFRecordType.HBR;
				rec = HardwareBinRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			case 50: //50 Software Bin Record (SBR)
				//type = STDFRecordType.SBR;
				rec = SoftwareBinRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			case 60: //60 Pin Map Record (PMR)
				//type = STDFRecordType.PMR;
				rec = PinMapRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			case 62: //62 Pin Group Record (PGR)
				//type = STDFRecordType.PGR;
				rec = PinGroupRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			case 63: //63 Pin List Record (PLR)
				//type = STDFRecordType.PLR;
				rec = PinListRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			case 70: //70 Retest Data Record (RDR)
				//type = STDFRecordType.RDR; 
				rec = RetestDataRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			case 80: //80 Site Description Record (SDR)
				//type = STDFRecordType.SDR; 
				rec = SiteDescriptionRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			}
			break;
		case 2: //Data collected per wafer
			switch(REC_SUB)
			{
			case 10: //10 Wafer Information Record (WIR)
				rec = WaferInformationRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				//type = STDFRecordType.WIR;
				break;
			case 20: //20 Wafer Results Record (WRR)
				//type = STDFRecordType.WRR;
				rec = WaferResultsRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			case 30: //30 Wafer Configuration Record (WCR)
				//type = STDFRecordType.WCR;
				rec = WaferConfigurationRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			}
			break;
		case 5: //Data collected on a per part basis
			switch(REC_SUB)
			{
			case 10: //10 Part Information Record (PIR)
				//type = STDFRecordType.PIR;
				rec = PartInformationRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			case 20: //20 Part Results Record (PRR)
				//type = STDFRecordType.PRR;
				rec = PartResultsRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			}
			break;
		case 10: //Data collected per test in the test program
			switch(REC_SUB)
			{
			case 30: //30 Test Synopsis Record (TSR)
				//type = STDFRecordType.TSR;
				rec = TestSynopsisRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
				break;
			}
			break;
		case 15: //Data collected per test execution
			switch(REC_SUB)
			{
			case 10: //10 Parametric Test Record (PTR)
				//type = STDFRecordType.PTR;
				rec = (ParametricTestRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				break;
			case 15: //15 Multiple-Result Parametric Record (MPR)
				//type = STDFRecordType.MPR;
				rec = (MultipleResultParametricRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				break;
			case 20: //20 Functional Test Record (FTR)
				//type = STDFRecordType.FTR;
				rec = (FunctionalTestRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				break;
			}
			break;
		case 20: //Data collected per program segment
			switch(REC_SUB)
			{
			case 10: //10 Begin Program Section Record (BPS)
				//type = STDFRecordType.BPS;
				rec = (BeginProgramSectionRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				break;
			case 20: //20 End Program Section Record (EPS)
				//type = STDFRecordType.EPS;
				rec = (EndProgramSectionRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				break;
			}
			break;
		case 50: //Generic Data
			switch(REC_SUB)
			{
			case 10: //10 Generic Data Record (GDR)
				//type = STDFRecordType.GDR;
				rec = (GenericDataRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				break;
			case 30: //30 Datalog Text Record (DTR)
				//type = STDFRecordType.DTR;
				rec = (DatalogTextRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				break;
			}
			break;
		case 180:
			//type = STDFRecordType.Reserved_Image;
			break;
		case 181:
			//type = STDFRecordType.Reserved_IG900;
			break;
		}
		
		if(rec==null) rec = UnknownSTDFRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb);
		
		return rec;
	}
}
