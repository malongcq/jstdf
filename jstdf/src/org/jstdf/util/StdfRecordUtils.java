package org.jstdf.util;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jstdf.record.AuditTrailRecord;
import org.jstdf.record.BeginProgramSectionRecord;
import org.jstdf.record.DatalogTextRecord;
import org.jstdf.record.EndProgramSectionRecord;
import org.jstdf.record.FileAttributesRecord;
import org.jstdf.record.FunctionalTestRecord;
import org.jstdf.record.GenericDataRecord;
import org.jstdf.record.HardwareBinRecord;
import org.jstdf.record.MasterInformationRecord;
import org.jstdf.record.MasterResultsRecord;
import org.jstdf.record.MultipleResultParametricRecord;
import org.jstdf.record.ParametricTestRecord;
import org.jstdf.record.PartCountRecord;
import org.jstdf.record.PartInformationRecord;
import org.jstdf.record.PartResultsRecord;
import org.jstdf.record.PinGroupRecord;
import org.jstdf.record.PinListRecord;
import org.jstdf.record.PinMapRecord;
import org.jstdf.record.RetestDataRecord;
import org.jstdf.record.STDFRecord;
import org.jstdf.record.SiteDescriptionRecord;
import org.jstdf.record.SoftwareBinRecord;
import org.jstdf.record.TestSynopsisRecord;
import org.jstdf.record.UnknownSTDFRecord;
import org.jstdf.record.WaferConfigurationRecord;
import org.jstdf.record.WaferInformationRecord;
import org.jstdf.record.WaferResultsRecord;


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
	 * @return the list of generic data items contains code-data pair.
	 */
	public static final Map<Integer, Object> readVnList(ByteBuffer bb, int cnt)
	{
		Map<Integer, Object> map = new HashMap<Integer, Object>(cnt);
		
		for(int i=0; i<cnt; i++)
		{
			if( !bb.hasRemaining() ) break;
			int code = readU1Int(bb);
			Object item = null;
			
			switch(code)
			{
			case 0: //B*0 Special pad field, of length 0 (See note below)
				break;
			case 1: //U*1 One byte unsigned integer
				item = readU1Int(bb);
				break;
			case 2: //U*2 Two byte unsigned integer
				item = readU2Int(bb);
				break;
			case 3: //U*4 Four byte unsigned integer
				item = readU4Int(bb);
				break;
			case 4: //I*1 One byte signed integer
				item = readI1Int(bb);
				break;
			case 5: //I*2 Two byte signed integer
				item = readI2Int(bb);
				break;
			case 6: //I*4 Four byte signed integer
				item = readI4Int(bb);
				break;
			case 7: //R*4 Four byte floating point number
				item = readR4Double(bb);
				break;
			case 8: //R*8 Eight byte floating point number
				item = readR8Double(bb);
				break;
			case 10: //C*n Variable length ASCII character string (first byte is string length in bytes)
				item = readCnString(bb);
				break;
			case 11: //B*n Variable length binary data string (first byte is string length in bytes)
				item = readBytes(bb);
				break;
			case 12: //D*n Bit encoded data (first two bytes of string are length in bits)
				item = readDnBits(bb);
				break;
			case 13: //N*1 Unsigned nibble
				item = readKNBits(bb, 1);
				break;
			}
			
			map.put(code, item);
		}
		
		return map;
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
	 * Read four byte signed integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @return an integer.
	 */
	public static final int readI4Int(ByteBuffer bb)
	{
		return readI4Int(bb, 0);
	}
	
	/**
	 * Read four byte signed integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param default_value the default return value if fail to read.
	 * @return an integer.
	 */
	public static final int readI4Int(ByteBuffer bb, int default_value)
	{	
		return (bb.remaining()<4) ? default_value : bb.getInt();
	}
	
	/**
	 * Read four byte unsigned integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @return an integer.
	 */
	public static final long readU4Int(ByteBuffer bb)
	{
		return readU4Int(bb, 0);
	}
	
	/**
	 * Read four byte unsigned integer.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param default_value the default return value if fail to read.
	 * @return an integer.
	 */
	public static final long readU4Int(ByteBuffer bb, long default_value)
	{
		return (bb.remaining()<4) ? default_value : bb.getInt() & 0xFFFFFFFFL;
	}
	
	/**
	 * Read four byte floating point number
	 * 
	 * @param bb the buffer which data is read from.
	 * @return a double.
	 */
	public static final double readR4Double(ByteBuffer bb)
	{
		return readR4Double(bb, 0.0);
	}
	
	/**
	 * Read four byte floating point number
	 * 
	 * @param bb the buffer which data is read from.
	 * @param default_value the default return value if fail to read.
	 * @return a double.
	 */
	public static final double readR4Double(ByteBuffer bb, double default_value)
	{
		if(bb.remaining()<4) return default_value;
		float f = bb.getFloat();
		return f;
	}
	
	/**
	 * Read array of four byte floating point number.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param k the number of elements to read.
	 * @return an array of k doubles.
	 */
	public static final double[] readKR4Double(ByteBuffer bb, int k)
	{
		return readKR4Double(bb, k, 0.0);
	}
	
	/**
	 * Read array of four byte floating point number.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param k the number of elements to read.
	 * @param default_value the default value of array element if fail to read.
	 * @return an array of k doubles.
	 */
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
	 * Read eight byte floating point number.
	 * 
	 * @param bb the buffer which data is read from.
	 * @return a double.
	 */
	public static final double readR8Double(ByteBuffer bb)
	{
		return readR8Double(bb, 0.0);
	}
	
	/**
	 * Read eight byte floating point number.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param default_value the default return value if fail to read.
	 * @return a double.
	 */
	public static final double readR8Double(ByteBuffer bb, double default_value)
	{
		if(bb.remaining()<8) return default_value;
		double f = bb.getDouble();
		return f;
	}
	
	/**
	 * Read fixed length character string.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param len the number of bytes to read.
	 * @return a string.
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
	 * Read variable length character string, the length of string is stored in 1st byte.
	 * 
	 * @param bb the buffer which data is read from.
	 * @return a string.
	 */
	public static final String readCnString(ByteBuffer bb)
	{
		return readCnString(bb, "");
	}
	
	/**
	 * Read variable length character string, the length of string is stored in 1st byte.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param default_value the default return value if fail to read.
	 * @return a string.
	 */
	public static final String readCnString(ByteBuffer bb, String default_value)
	{
		if(!bb.hasRemaining()) return default_value;
		int len = bb.get() & 0xFF;	
		return readCString(bb, len);
	}
	
	/**
	 * Read array of variable length character string.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param k the number of elements to read.
	 * @return an array of k string
	 */
	public static final String[] readKCnString(ByteBuffer bb, int k)
	{
		return readKCNString(bb, k, "");
	}
	
	/**
	 * Read array of variable length character string.
	 * 
	 * @param bb the buffer which data is read from.
	 * @param k the number of elements to read.
	 * @param default_value the default value of array element if fail to read.
	 * @return an array of k string
	 */
	public static final String[] readKCNString(ByteBuffer bb, int k, String default_value)
	{
		String[] d = new String[k];
		for(int i=0; i<k; i++)
		{
			d[i] = readCnString(bb, default_value);
		}
		
		return d;
	}
	
	/**
	 * Create STDF record object by record header.
	 * 
	 * @param seq the sequence number when reading file.
	 * @param REC_LEN the record length in bytes.
	 * @param REC_TYP the record type.
	 * @param REC_SUB the record sub-type.
	 * @param bb the buffer which data is read from.
	 * @return the STDF record.
	 */
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
