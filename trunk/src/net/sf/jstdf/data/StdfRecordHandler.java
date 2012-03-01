package net.sf.jstdf.data;

import net.sf.jstdf.record.AuditTrailRecord;
import net.sf.jstdf.record.BeginProgramSectionRecord;
import net.sf.jstdf.record.DatalogTextRecord;
import net.sf.jstdf.record.EndProgramSectionRecord;
import net.sf.jstdf.record.FileAttributesRecord;
import net.sf.jstdf.record.FunctionalTestRecord;
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
import net.sf.jstdf.record.SiteDescriptionRecord;
import net.sf.jstdf.record.SoftwareBinRecord;
import net.sf.jstdf.record.TestSynopsisRecord;
import net.sf.jstdf.record.UnknownSTDFRecord;
import net.sf.jstdf.record.WaferConfigurationRecord;
import net.sf.jstdf.record.WaferInformationRecord;
import net.sf.jstdf.record.WaferResultsRecord;

public interface StdfRecordHandler 
{
	public boolean readRecord(PartInformationRecord pir);
	public boolean readRecord(PartResultsRecord prr);
	public boolean readRecord(ParametricTestRecord ptr);
	public boolean readRecord(MultipleResultParametricRecord mpr);
	public boolean readRecord(FunctionalTestRecord ftr);
	
	public boolean readRecord(HardwareBinRecord hbr);
	public boolean readRecord(SoftwareBinRecord sbr);
	
	public boolean readRecord(WaferConfigurationRecord wcr);
	public boolean readRecord(WaferInformationRecord wir);
	public boolean readRecord(WaferResultsRecord wrr);
	
	public boolean readRecord(AuditTrailRecord atr);
	public boolean readRecord(FileAttributesRecord far);
	
	public boolean readRecord(BeginProgramSectionRecord bps);
	public boolean readRecord(EndProgramSectionRecord eps);
	
	public boolean readRecord(DatalogTextRecord dtr);
	public boolean readRecord(GenericDataRecord gdr);
	
	public boolean readRecord(MasterInformationRecord mir);
	public boolean readRecord(MasterResultsRecord mrr);
	public boolean readRecord(PartCountRecord pcr);
	
	public boolean readRecord(PinGroupRecord pgr);
	public boolean readRecord(PinListRecord plr);
	public boolean readRecord(PinMapRecord pmr);
	
	public boolean readRecord(RetestDataRecord rdr);
	public boolean readRecord(SiteDescriptionRecord sdr);
	public boolean readRecord(TestSynopsisRecord tsr);
	
	public boolean readRecord(UnknownSTDFRecord ur);
}
