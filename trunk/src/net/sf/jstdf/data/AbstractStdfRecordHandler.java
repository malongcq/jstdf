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
import net.sf.jstdf.record.STDFRecord;
import net.sf.jstdf.record.SiteDescriptionRecord;
import net.sf.jstdf.record.SoftwareBinRecord;
import net.sf.jstdf.record.TestSynopsisRecord;
import net.sf.jstdf.record.UnknownSTDFRecord;
import net.sf.jstdf.record.WaferConfigurationRecord;
import net.sf.jstdf.record.WaferInformationRecord;
import net.sf.jstdf.record.WaferResultsRecord;

public abstract class AbstractStdfRecordHandler implements StdfRecordHandler 
{
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
