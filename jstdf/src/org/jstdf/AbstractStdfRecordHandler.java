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
import org.jstdf.record.STDFRecord;
import org.jstdf.record.SiteDescriptionRecord;
import org.jstdf.record.SoftwareBinRecord;
import org.jstdf.record.TestSynopsisRecord;
import org.jstdf.record.UnknownSTDFRecord;
import org.jstdf.record.WaferConfigurationRecord;
import org.jstdf.record.WaferInformationRecord;
import org.jstdf.record.WaferResultsRecord;

/**
 * This class provides generic method for reading the stdf record
 * @author malong
 *
 */
public abstract class AbstractStdfRecordHandler implements StdfRecordHandler 
{
	@Override
	public void beginReadRecord()
	{
		//to override
	}
	
	@Override
	public void endReadRecord()
	{
		//to override
	}
	
	/**
	 * Generic method to read stdf record
	 * @param rec
	 * @return
	 */
	protected boolean readSTDFRecord(STDFRecord rec) 
	{
		return false;
	}
	
	@Override
	public boolean readRecord(PartInformationRecord pir) {
		return readSTDFRecord(pir);
	}

	@Override
	public boolean readRecord(PartResultsRecord prr) {
		return readSTDFRecord(prr);
	}

	@Override
	public boolean readRecord(ParametricTestRecord ptr) {
		return readSTDFRecord(ptr);
	}

	@Override
	public boolean readRecord(MultipleResultParametricRecord mpr) {
		return readSTDFRecord(mpr);
	}

	@Override
	public boolean readRecord(FunctionalTestRecord ftr) {
		return readSTDFRecord(ftr);
	}

	@Override
	public boolean readRecord(HardwareBinRecord hbr) {
		return readSTDFRecord(hbr);
	}

	@Override
	public boolean readRecord(SoftwareBinRecord sbr) {
		return readSTDFRecord(sbr);
	}

	@Override
	public boolean readRecord(WaferConfigurationRecord wcr) {
		return readSTDFRecord(wcr);
	}

	@Override
	public boolean readRecord(WaferInformationRecord wir) {
		return readSTDFRecord(wir);
	}

	@Override
	public boolean readRecord(WaferResultsRecord wrr) {
		return readSTDFRecord(wrr);
	}

	@Override
	public boolean readRecord(AuditTrailRecord atr) {
		return readSTDFRecord(atr);
	}

	@Override
	public boolean readRecord(FileAttributesRecord far) {
		return readSTDFRecord(far);
	}

	@Override
	public boolean readRecord(BeginProgramSectionRecord bps) {
		return readSTDFRecord(bps);
	}

	@Override
	public boolean readRecord(EndProgramSectionRecord eps) {
		return readSTDFRecord(eps);
	}

	@Override
	public boolean readRecord(DatalogTextRecord dtr) {
		return readSTDFRecord(dtr);
	}

	@Override
	public boolean readRecord(GenericDataRecord gdr) {
		return readSTDFRecord(gdr);
	}

	@Override
	public boolean readRecord(MasterInformationRecord mir) {
		return readSTDFRecord(mir);
	}

	@Override
	public boolean readRecord(MasterResultsRecord mrr) {
		return readSTDFRecord(mrr);
	}

	@Override
	public boolean readRecord(PartCountRecord pcr) {
		return readSTDFRecord(pcr);
	}

	@Override
	public boolean readRecord(PinGroupRecord pgr) {
		return readSTDFRecord(pgr);
	}

	@Override
	public boolean readRecord(PinListRecord plr) {
		return readSTDFRecord(plr);
	}

	@Override
	public boolean readRecord(PinMapRecord pmr) {
		return readSTDFRecord(pmr);
	}

	@Override
	public boolean readRecord(RetestDataRecord rdr) {
		return readSTDFRecord(rdr);
	}

	@Override
	public boolean readRecord(SiteDescriptionRecord sdr) {
		return readSTDFRecord(sdr);
	}

	@Override
	public boolean readRecord(TestSynopsisRecord tsr) {
		return readSTDFRecord(tsr);
	}

	@Override
	public boolean readRecord(UnknownSTDFRecord ur) {
		return readSTDFRecord(ur);
	}
}
