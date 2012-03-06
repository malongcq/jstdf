package net.sf.jstdf.data;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.GZIPInputStream;

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
import net.sf.jstdf.util.StdfRecordUtils;

/**
 * Class for reading stdf file or gzipped stdf file
 * 
 * @author malong
 *
 */
public class StdfReader 
{
	/**
	 * stdf record header length of bytes
	 */
	public final static int STDF_HEAD_LEN = 4;
	
	/**
	 * Create a new stdf reader, the default stdf record handler is SimpleSTDFSummary
	 */
	public StdfReader()
	{
		this.recordHandler = new SimpleSTDFSummary();
	}
	
	/**
	 * Create a new stdf reader, given the stdf record handler to process record.
	 * 
	 * @param recordHandler
	 */
	public StdfReader(StdfRecordHandler recordHandler)
	{
		this.recordHandler = recordHandler;
	}
	
	private boolean verbose = true;
	public boolean isVerbose()
	{
		return verbose;
	}
	public void setVerbose(boolean verbose)
	{
		this.verbose = verbose;
	}
	
	private StdfRecordHandler recordHandler;
	public StdfRecordHandler getRecordHandler() {
		return recordHandler;
	}

	public void setRecordHandler(StdfRecordHandler recordHandler) {
		this.recordHandler = recordHandler;
	}

	public int loadFromSTDF(String filename) throws IOException 
	{
		return loadFromSTDF(new File(filename));
	}
	
	private int loadFromSTDF(File f) throws IOException
	{
		InputStream is = null;
		
		try
		{
			is = new GZIPInputStream(new FileInputStream(f));
		} catch (IOException e)
		{
			if(e!=null && e.getMessage()!=null && e.getMessage().equals("Not in GZIP format"))
			{
				is = new FileInputStream(f);
			}
			else throw e;
		}
		
		return loadFromSTDF(is);
	}
	
	
	public int loadFromSTDF(InputStream ins) throws IOException
	{
		BufferedInputStream bis = new BufferedInputStream(ins);
		
		byte[] head = new byte[STDF_HEAD_LEN], rec_data;
		int read_len = bis.read(head);
		
		assert(read_len==STDF_HEAD_LEN);
			
		ByteOrder order = head[0]==2 ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
		
		ByteBuffer head_buf, rec_buf;
		int REC_LEN, REC_TYP, REC_SUB;
		int rec_cnt = 0, read_cnt = 0;
		
		while(read_len==STDF_HEAD_LEN)
		{
			head_buf = ByteBuffer.wrap(head).order(order);
			REC_LEN = StdfRecordUtils.readU2Int(head_buf);
			REC_TYP = StdfRecordUtils.readU1Int(head_buf);
			REC_SUB = StdfRecordUtils.readU1Int(head_buf);
//			System.out.printf("#%d:typ=%d,sub=%d,len=%d\n",rec_cnt,REC_TYP,REC_SUB,REC_LEN);
			
			rec_data = new byte[REC_LEN];
			read_len = bis.read(rec_data, 0, REC_LEN);
			
			if(REC_LEN>read_len)
			{
				System.out.printf("#%d:typ=%d,sub=%d,len=%d; ",rec_cnt,REC_TYP,REC_SUB,REC_LEN);
				System.out.println(read_len + " bytes read, Interrupted");
				break;
			}
			
			rec_buf = ByteBuffer.wrap(rec_data).order(order);
			
//			readSTDFRecord(rec_cnt, REC_LEN, REC_TYP, REC_SUB, rec_buf);
			read_cnt += readSTDFRecord(rec_cnt, REC_LEN, REC_TYP, REC_SUB, rec_buf) ? 1 : 0;

			if( (++rec_cnt)%10000==0 && verbose) 
			{
				System.out.printf("%d/%d processed...\n", read_cnt, rec_cnt);
			}
			
			read_len = bis.read(head, 0, STDF_HEAD_LEN);
		}
		
		if(verbose) 
		{
			System.out.println("Total "+rec_cnt+" processed, "+read_cnt+" read.");
		}
		
		bis.close();
		
		return rec_cnt;
	}
	
	protected boolean readSTDFRecord(int seq, int REC_LEN, int REC_TYP, int REC_SUB, ByteBuffer bb)
	{
		switch(REC_TYP)
		{
		case 0: //Information about the STDF file
			switch(REC_SUB)
			{
			case 10: //10 File Attributes Record (FAR)
				return recordHandler.readRecord(FileAttributesRecord.getInstance(seq, REC_LEN, REC_TYP, REC_SUB, bb));
			
			case 20: //20 Audit Trail Record (ATR)
				return recordHandler.readRecord(AuditTrailRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB, bb));
			
			}
			break;
		case 1: //Data collected on a per lot basis
			switch(REC_SUB)
			{
			case 10: //10 Master Information Record (MIR)
				return recordHandler.readRecord(MasterInformationRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
			
			case 20: //20 Master Results Record (MRR)
				return recordHandler.readRecord(MasterResultsRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
			
			case 30: //30 Part Count Record (PCR)
				return recordHandler.readRecord(PartCountRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 40: //40 Hardware Bin Record (HBR)
				return recordHandler.readRecord(HardwareBinRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 50: //50 Software Bin Record (SBR)
				return recordHandler.readRecord(SoftwareBinRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 60: //60 Pin Map Record (PMR)
				return recordHandler.readRecord(PinMapRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 62: //62 Pin Group Record (PGR)
				return recordHandler.readRecord(PinGroupRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 63: //63 Pin List Record (PLR)
				return recordHandler.readRecord(PinListRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 70: //70 Retest Data Record (RDR)
				return recordHandler.readRecord(RetestDataRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 80: //80 Site Description Record (SDR)
				return recordHandler.readRecord(SiteDescriptionRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			}
			break;
		case 2: //Data collected per wafer
			switch(REC_SUB)
			{
			case 10: //10 Wafer Information Record (WIR)
				return recordHandler.readRecord(WaferInformationRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 20: //20 Wafer Results Record (WRR)
				return recordHandler.readRecord(WaferResultsRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 30: //30 Wafer Configuration Record (WCR)
				return recordHandler.readRecord(WaferConfigurationRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			}
			break;
		case 5: //Data collected on a per part basis
			switch(REC_SUB)
			{
			case 10: //10 Part Information Record (PIR)
				return recordHandler.readRecord(PartInformationRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 20: //20 Part Results Record (PRR)
				return recordHandler.readRecord(PartResultsRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			}
			break;
		case 10: //Data collected per test in the test program
			switch(REC_SUB)
			{
			case 30: //30 Test Synopsis Record (TSR)
				return recordHandler.readRecord(TestSynopsisRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			}
			break;
		case 15: //Data collected per test execution
			switch(REC_SUB)
			{
			case 10: //10 Parametric Test Record (PTR)
				return recordHandler.readRecord(ParametricTestRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 15: //15 Multiple-Result Parametric Record (MPR)
				return recordHandler.readRecord(MultipleResultParametricRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 20: //20 Functional Test Record (FTR)
				return recordHandler.readRecord(FunctionalTestRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			}
			break;
		case 20: //Data collected per program segment
			switch(REC_SUB)
			{
			case 10: //10 Begin Program Section Record (BPS)
				return recordHandler.readRecord(BeginProgramSectionRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 20: //20 End Program Section Record (EPS)
				return recordHandler.readRecord(EndProgramSectionRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			}
			break;
		case 50: //Generic Data
			switch(REC_SUB)
			{
			case 10: //10 Generic Data Record (GDR)
				return recordHandler.readRecord(GenericDataRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			case 30: //30 Datalog Text Record (DTR)
				return recordHandler.readRecord(DatalogTextRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));
				
			}
			break;
		case 180:
			//type = STDFRecordType.Reserved_Image;
			break;
		case 181:
			//type = STDFRecordType.Reserved_IG900;
			break;
		}
		
		return recordHandler.readRecord(UnknownSTDFRecord.getInstance(seq,REC_LEN,REC_TYP,REC_SUB,bb));		
	}	
}
