package org.jstdf.record;

import java.nio.ByteBuffer;

/**
 * The interface defines the common property and methods of STDF record
 * 
 * @author malong
 *
 */
public interface STDFRecord
{
	/**
	 * 
	 * @return the STDF record type
	 */
	public STDFRecordType getRecordType();
	
	/**
	 * 
	 * @return the record sequence number when reading file
	 */
	public int getRecordNo();
	
	/**
	 * 
	 * @return the record length in bytes
	 */
	public int getRecordLength();
	
	/**
	 * 
	 * @return the record type code
	 */
	public int getRecordTypeCode();
	
	/**
	 * 
	 * @return the record sub-type code
	 */
	public int getRecordSubTypeCode();
	
	/**
	 * 
	 * @param seq the record number when reading file
	 * @param len the record length in bytes
	 * @param typ the record type code
	 * @param sub the record sub-type code
	 */
	public void setHeadInfo(int seq, int len, int typ, int sub);
	
	/**
	 * read data content from byte buffer of the record data
	 * @param bb 
	 */
	public void readContent(ByteBuffer bb);
}
