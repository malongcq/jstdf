package net.sf.jstdf.record;

import java.nio.ByteBuffer;

public interface STDFRecord
{
	public STDFRecordType getRecordType();
	
	public int getRecordNo();
	public int getRecordLength();
	public int getRecordTypeCode();
	public int getRecordSubTypeCode();
	
	public void setHeadInfo(int seq, int len, int typ, int sub);
	public void readContent(ByteBuffer bb);
}
