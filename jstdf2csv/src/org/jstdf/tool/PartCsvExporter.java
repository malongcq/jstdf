package org.jstdf.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;

import org.jstdf.AbstractPartTestResult;
import org.jstdf.ParametricTestItem;
import org.jstdf.PartResultSet;
import org.jstdf.record.DatalogTextRecord;
import org.jstdf.record.FunctionalTestRecord;
import org.jstdf.record.HardwareBinRecord;
import org.jstdf.record.MultipleResultParametricRecord;
import org.jstdf.record.ParametricTestRecord;
import org.jstdf.record.PartResultsRecord;
import org.jstdf.record.PinMapRecord;
import org.jstdf.record.STDFRecord;
import org.jstdf.record.STDFRecordType;
import org.jstdf.record.SiteDescriptionRecord;
import org.jstdf.record.SoftwareBinRecord;
import org.jstdf.record.TestSynopsisRecord;


public class PartCsvExporter extends AbstractPartTestResult
{
	private boolean exportPTR, exportFTR, exportMPR, exportDTR;
	public boolean isExportPTR()
	{
		return exportPTR;
	}
	public void setExportPTR(boolean exportPTR)
	{
		this.exportPTR = exportPTR;
	}
	public boolean isExportFTR()
	{
		return exportFTR;
	}
	public void setExportFTR(boolean exportFTR)
	{
		this.exportFTR = exportFTR;
	}
	public boolean isExportMPR()
	{
		return exportMPR;
	}
	public void setExportMPR(boolean exportMPR)
	{
		this.exportMPR = exportMPR;
	}
	public boolean isExportDTR()
	{
		return exportDTR;
	}
	public void setExportDTR(boolean exportDTR)
	{
		this.exportDTR = exportDTR;
	}

	protected String filename_bin_spec = "Bin_Spec.csv";
	protected String filename_test_params = "Test_Params.csv";
	protected String filename_sdr = "Site_Desc.csv";
	protected String filename_tsr = "Test_Synopsis.csv";
	protected String filename_pmr = "Pin_Map.csv";
	
	@Override
	public void endReadRecord()
	{
		//export PMR
		_exportPMR();
		
		//export TSR
		_exportTSR();
		
		//export site description
		_exportSDR();
		
		//export test parameters
		_exportTestParams();
		
		//export bins 
		_exportBinSpec();
		
		//finish parameters results
		txtw.dispose();
	}
	
	private void _exportPMR()
	{
		try
		{
			FileWriter w = new FileWriter(new File(out_dir, filename_pmr));
			w.write("PMR_IDX,Channel_Type,Channel_Name,Physical_Name,Logic_Name,Head,Site\n");
			
			if(this.getPinMapRecords()!=null)
			for(PinMapRecord pmr : this.getPinMapRecords())
			{
				w.write(String.format("%d,%d,\"%s\",\"%s\",\"%s\",%d,%d\n", 
					pmr.PMR_INDX,pmr.CHAN_TYP,pmr.CHAN_NAM,pmr.PHY_NAM,pmr.LOG_NAM,pmr.HEAD_NUM,pmr.SITE_NUM));
			}
			
			w.flush();
			w.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void _exportTSR()
	{
		try
		{
			FileWriter w = new FileWriter(new File(out_dir, filename_tsr));
			w.write("Head,Site,Type,Test_Num," +
					"Exec_Cnt,Fail_Cnt,Alarm_Cnt," +
					"Test_Name,Seq_Name,Test_Label,Test_Opts," +
					"Time,Min,Max,Sum,Sum_Sqr\n");
			
			if(this.getTestSynopsisRecords()!=null)
			for(TestSynopsisRecord tsr : this.getTestSynopsisRecords())
			{
				w.write(String.format("%d,%d,%c,%d," +
						"%d,%d,%d," +
						"\"%s\",\"%s\",\"%s\",\"%s\"," +
						"%g,%g,%g,%g,%g\n", 
					tsr.HEAD_NUM, tsr.SITE_NUM, tsr.TEST_TYP, tsr.TEST_NUM,
					tsr.EXEC_CNT, tsr.FAIL_CNT, tsr.ALRM_CNT,
					tsr.TEST_NAM, tsr.SEQ_NAME, tsr.TEST_LBL, tsr.OPT_FLAG.toString(),
					tsr.TEST_TIM, tsr.TEST_MIN, tsr.TEST_MAX, tsr.TST_SUMS, tsr.TST_SQRS));
			}
			
			w.flush();
			w.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void _exportSDR()
	{
		try
		{
			FileWriter w = new FileWriter(new File(out_dir, filename_sdr));
			w.write("Head,Site_Grp,Site_Cnt,Site_Nums," +
					"Handler_Type, Handler_ID, Probe_Card_Type, Probe_Card_ID," +
					"Load_Type, Load_ID, DIB_Type, DIB_ID," +
					"Cable_Type, Cable_ID, Contactor_Type, Contactor_ID," +
					"Laser_Type, Laser_ID, Extra_Type, Extra_ID\n");
			
			if(this.getSiteDescriptionRecords()!=null)
			for(SiteDescriptionRecord sdr : this.getSiteDescriptionRecords())
			{
				w.write(String.format("%d,%d,%d,\"%s\"," +
						"\"%s\",\"%s\",\"%s\",\"%s\"," +
						"\"%s\",\"%s\",\"%s\",\"%s\"," +
						"\"%s\",\"%s\",\"%s\",\"%s\"," +
						"\"%s\",\"%s\",\"%s\",\"%s\"\n", 
					sdr.HEAD_NUM, sdr.SITE_GRP, sdr.SITE_CNT, Arrays.toString(sdr.SITE_NUM), 
					sdr.HAND_TYP, sdr.HAND_ID, sdr.CARD_TYP, sdr.CARD_ID,
					sdr.LOAD_TYP, sdr.LOAD_ID,sdr.DIB_TYP, sdr.DIB_ID,
					sdr.CABL_TYP, sdr.CABL_ID,sdr.CONT_TYP, sdr.CONT_ID,
					sdr.LASR_TYP, sdr.LASR_ID,sdr.EXTR_TYP, sdr.EXTR_ID));
			}
			
			w.flush();
			w.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void _exportTestParams()
	{
		try
		{
			FileWriter w = new FileWriter(new File(out_dir, filename_test_params));
			w.write("Type,Test_Num,Name,Unit,Low_Spec,High_Spec,Low_Limit,High_Limit\n");
			if(testItems!=null)
			{
				for(ParametricTestItem t : testItems)
				{
					w.write(String.format("%s,\"%d\",\"%s\",\"%s\",\"%g\",\"%g\",\"%g\",\"%g\"\n", 
						t.isMultipleResult()? "MPR":"PTR", t.getTestNum(),t.getTestName(),t.getTestUnit(),
						t.getLowSpec(),t.getHighSpec(),t.getLowLimit(),t.getHighLimit()));
				}
			}
			w.flush();
			w.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void _exportBinSpec()
	{
		try
		{
			FileWriter w = new FileWriter(new File(out_dir, filename_bin_spec));
			w.write("Type,Num,Name,Pass_Fail\n");
			
			if(hbrs!=null)
			{
				for(HardwareBinRecord hbr : hbrs)
				{
					w.write(String.format("H,%d,%s,%s\n", hbr.HBIN_NUM, hbr.HBIN_NAM, hbr.HBIN_PF));
				}
			}
			
			if(sbrs!=null)
			{
				for(SoftwareBinRecord sbr : sbrs)
				{
					w.write(String.format("S,%d,%s,%s\n", sbr.SBIN_NUM, sbr.SBIN_NAM, sbr.SBIN_PF));
				}
			}

			w.flush();
			w.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private MultiPageTextFileWriter txtw;
	protected File out_dir;
	public final static int FW_IDX_PRR = 0;
	public final static int FW_IDX_PTR = 1;
	public final static int FW_IDX_MPR = 2;
	public final static int FW_IDX_FTR = 3;
	public final static int FW_IDX_DTR = 4;
	
	public PartCsvExporter(File parent_dir, int page_limit) throws IOException
	{
		out_dir = parent_dir;
		
		txtw = new MultiPageTextFileWriter(out_dir, 5);
		txtw.setPageLimit(page_limit);
	}
	
	@Override
	public boolean readRecord(DatalogTextRecord dtr) 
	{
		if(exportDTR)
		{
			//String line = String.format("%d\n%s\n", dtr.getRecordNo(), dtr.TEXT_DAT);
			try
			{
				txtw.write(FW_IDX_DTR, dtr.getRecordNo()+"\n", null, "DTR", "txt", false);
				txtw.write(FW_IDX_DTR, dtr.TEXT_DAT, null, "DTR", "txt", false);
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}
	
	protected void exportParameter(PartResultsRecord prr, ParametricTestRecord ptr) throws IOException
	{
		String header = "Seq,Head,Site,X,Y,H_Bin,S_Bin,Param,Test_Num,Value,Pass\n";
		String line = String.format("%d,%d,%d,%d,%d,%d,%d,\"%s\",%d,%g,%d\n", 
				ptr.getRecordNo(), ptr.HEAD_NUM, ptr.SITE_NUM, 
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				ptr.TEST_TXT, ptr.TEST_NUM, ptr.RESULT, ptr.isPass() ? 1 : 0);
		
		txtw.write(FW_IDX_PTR, line, header, "PTR", "csv");
	}
	
	protected void exportParameter(PartResultsRecord prr, MultipleResultParametricRecord mpr) throws IOException
	{
		String header = "Seq,Head,Site,X,Y,H_Bin,S_Bin,Param,Test_Num,N,IDX,PMR_IDX,Value,Stat\n";
		for(int i=0; i<mpr.RSLT_CNT; i++)
		{
			double v = mpr.RTN_RSLT[i];
			Integer pmr_idx = mpr.RTN_ICNT==0 ? null : mpr.RTN_INDX[i];
			BitSet stat = mpr.RTN_ICNT==0 ? null : mpr.RTN_STAT[i];
			
			String line = String.format("%d,%d,%d,%d,%d,%d,%d,\"%s\",%d,%d,%d,%d,%g,%s\n", 
					mpr.getRecordNo(), mpr.HEAD_NUM, mpr.SITE_NUM,
					prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
					mpr.TEST_TXT, mpr.TEST_NUM, 
					mpr.RSLT_CNT, i, pmr_idx, v, stat==null?"":stat);
			
			txtw.write(FW_IDX_MPR, line, header, "MPR", "csv");
		}
	}
	
	protected void exportParameter(PartResultsRecord prr, FunctionalTestRecord ftr) throws IOException
	{
		String header = "Seq,Head,Site,X,Y,H_Bin,S_Bin,Param,Test_Num,XFail_Ad,YFail_Ad,Fail_Pin\n";
		String line = String.format("%d,%d,%d,%d,%d,%d,%d,\"%s\",%d,%d,%d,%d\n", 
				ftr.getRecordNo(), ftr.HEAD_NUM, ftr.SITE_NUM, 
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				ftr.TEST_TXT, ftr.TEST_NUM, ftr.XFAIL_AD, ftr.YFAIL_AD, ftr.NUM_FAIL);
		
		txtw.write(FW_IDX_FTR, line, header, "FTR", "csv");
	}
	
	protected void exportPartResult(PartResultsRecord prr) throws IOException
	{
		String header = "Seq,Head,Site,X,Y,H_Bin,S_Bin,ID,Txt\n";
		String line = String.format("%d,%d,%d,%d,%d,%d,%d,%s,%s\n", prr.getRecordNo(), 
				prr.HEAD_NUM, prr.SITE_NUM,
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				prr.PART_ID, prr.PART_TXT);
		
		txtw.write(FW_IDX_PRR, line, header, "Part_Result", "csv");
	}

	@Override
	public boolean readPartResultSet(PartResultSet part_result)
	{
		try 
		{
			PartResultsRecord prr = part_result.getPartResultsRecord();
			exportPartResult(prr);
			
			for(STDFRecord rec : part_result.getPartResults())
			{
				if(rec.getRecordType()==STDFRecordType.PTR && exportPTR)
				{
					ParametricTestRecord ptr = (ParametricTestRecord)rec;
					exportParameter(prr, ptr);
				}
				else if(rec.getRecordType()==STDFRecordType.MPR && exportMPR)
				{
					MultipleResultParametricRecord mpr = (MultipleResultParametricRecord)rec;
					exportParameter(prr, mpr);
				}
				else if(rec.getRecordType()==STDFRecordType.FTR && exportFTR)
				{
					FunctionalTestRecord ftr = (FunctionalTestRecord)rec;
					exportParameter(prr, ftr);
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return true;
	}
}
