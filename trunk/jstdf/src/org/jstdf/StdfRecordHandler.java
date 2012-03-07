package org.jstdf;

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
import org.jstdf.record.SiteDescriptionRecord;
import org.jstdf.record.SoftwareBinRecord;
import org.jstdf.record.TestSynopsisRecord;
import org.jstdf.record.UnknownSTDFRecord;
import org.jstdf.record.WaferConfigurationRecord;
import org.jstdf.record.WaferInformationRecord;
import org.jstdf.record.WaferResultsRecord;

/**
 * Defines how the handler read stdf record
 * @author malong
 *
 */
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
