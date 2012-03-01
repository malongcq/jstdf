package net.sf.jstdf.data;

import java.util.ArrayDeque;
import java.util.Deque;

import net.sf.jstdf.record.AuditTrailRecord;
import net.sf.jstdf.record.FileAttributesRecord;
import net.sf.jstdf.record.MasterInformationRecord;
import net.sf.jstdf.record.MasterResultsRecord;
import net.sf.jstdf.record.PartCountRecord;
import net.sf.jstdf.record.RetestDataRecord;
import net.sf.jstdf.record.SiteDescriptionRecord;

public class GenricTestResult extends AbstractStdfRecordHandler 
{
	protected FileAttributesRecord far;
	protected Deque<AuditTrailRecord> atrs;
	protected MasterInformationRecord mir;
	protected RetestDataRecord rdr;
	protected Deque<SiteDescriptionRecord> sdrs;
	protected Deque<PartCountRecord> pcrs;
	protected MasterResultsRecord mrr;
	
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
	public boolean readRecord(PartCountRecord pcr) 
	{
		if(this.pcrs==null)
		{
			this.pcrs = new ArrayDeque<PartCountRecord>();
		}
		this.pcrs.add(pcr);
		
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
