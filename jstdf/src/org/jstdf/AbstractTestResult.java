package org.jstdf;

import java.util.ArrayDeque;
import java.util.Deque;

import org.jstdf.record.AuditTrailRecord;
import org.jstdf.record.FileAttributesRecord;
import org.jstdf.record.MasterInformationRecord;
import org.jstdf.record.MasterResultsRecord;
import org.jstdf.record.RetestDataRecord;
import org.jstdf.record.SiteDescriptionRecord;

/**
 * The default implementations for reading stdf data file
 * @author malong
 *
 */
public abstract class AbstractTestResult extends AbstractStdfRecordHandler 
{
	protected FileAttributesRecord far;
	protected Deque<AuditTrailRecord> atrs;
	protected MasterInformationRecord mir;
	protected RetestDataRecord rdr;
	protected Deque<SiteDescriptionRecord> sdrs;
	protected MasterResultsRecord mrr;
	
	public FileAttributesRecord getFileAttributesRecord()
	{
		return far;
	}
	
	public MasterInformationRecord getMasterInformationRecord()
	{
		return mir;
	}
	
	public MasterResultsRecord getMasterResultsRecord()
	{
		return mrr;
	}
	
	public RetestDataRecord getRetestDataRecord()
	{
		return rdr;
	}
	
	public Deque<AuditTrailRecord> getAuditTrailRecords()
	{
		return atrs;
	}
	
	public Deque<SiteDescriptionRecord> getSiteDescriptionRecords()
	{
		return sdrs;
	}
	
	@Override
	public boolean readRecord(MasterInformationRecord mir) 
	{
		this.mir = mir;
		return true;
	}

	@Override
	public boolean readRecord(MasterResultsRecord mrr) 
	{
		this.mrr = mrr;
		return true;
	}
	
	@Override
	public boolean readRecord(AuditTrailRecord atr) 
	{
		if(this.atrs==null)
		{
			this.atrs = new ArrayDeque<AuditTrailRecord>();
		}
		this.atrs.add(atr);
		
		return true;
	}
	
	@Override
	public boolean readRecord(SiteDescriptionRecord sdr) 
	{
		if(this.sdrs==null)
		{
			this.sdrs = new ArrayDeque<SiteDescriptionRecord>();
		}
		this.sdrs.add(sdr);
		
		return true;
	}
}
