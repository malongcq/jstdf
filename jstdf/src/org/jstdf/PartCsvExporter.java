package org.jstdf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.jstdf.record.FunctionalTestRecord;
import org.jstdf.record.HardwareBinRecord;
import org.jstdf.record.MultipleResultParametricRecord;
import org.jstdf.record.ParametricTestRecord;
import org.jstdf.record.PartResultsRecord;
import org.jstdf.record.STDFRecord;
import org.jstdf.record.STDFRecordType;
import org.jstdf.record.SiteDescriptionRecord;
import org.jstdf.record.SoftwareBinRecord;
import org.jstdf.record.TestSynopsisRecord;


public class PartCsvExporter extends AbstractPartTestResult
{
	private int pageLimit = 100000;
	public int getPageLimit()
	{
		return pageLimit;
	}
	public void setPageLimit(int pageLimit)
	{
		this.pageLimit = pageLimit;
	}

	private boolean exportPTR, exportFTR, exportMPR;
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

	protected String filename_bin_spec = "Bin_Spec.csv";
	protected String filename_test_params = "Test_Params.csv";
	protected String filename_site_desc = "Site_Desc.csv";
	protected String filename_tsr = "Test_Synopsis.csv";
	
	@Override
	public void endReadRecord()
	{
		//export TSR
		try
		{
			FileWriter w = new FileWriter(new File(out_dir, filename_tsr));
			w.write("Head,Site,Type,Test_Num," +
					"Exec_Cnt,Fail_Cnt,Alarm_Cnt," +
					"Test_Name,Seq_Name,Test_Label,Test_Opts," +
					"Time,Min,Max,Sum,Sum_Sqr\n");
			
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
		
		//export site description
		try
		{
			FileWriter w = new FileWriter(new File(out_dir, filename_site_desc));
			w.write("Head,Site_Grp,Site_Cnt,Site_Nums," +
					"Handler_Type, Handler_ID, Probe_Card_Type, Probe_Card_ID," +
					"Load_Type, Load_ID, DIB_Type, DIB_ID," +
					"Cable_Type, Cable_ID, Contactor_Type, Contactor_ID," +
					"Laser_Type, Laser_ID, Extra_Type, Extra_ID\n");
			
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
		
		//export test parameters
		try
		{
			FileWriter w = new FileWriter(new File(out_dir, filename_test_params));
			w.write("Test_Num,Name,Unit,Low_Spec,High_Spec,Low_Limit,High_Limit\n");
			if(testItems!=null)
			{
				for(ParametricTestItem t : testItems)
				{
					w.write(String.format("\"%d\",\"%s\",\"%s\",\"%g\",\"%g\",\"%g\",\"%g\"\n", 
						t.getTestNum(),t.getTestName(),t.getTestUnit(),
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
		
		//export bins 
		try
		{
			FileWriter w = new FileWriter(new File(out_dir, filename_bin_spec));
			w.write("Type,Num,Name,Pass_Fail\n");
			for(HardwareBinRecord hbr : hbrs)
			{
				w.write(String.format("H,%d,%s,%s\n", hbr.HBIN_NUM, hbr.HBIN_NAM, hbr.HBIN_PF));
			}
			for(SoftwareBinRecord sbr : sbrs)
			{
				w.write(String.format("S,%d,%s,%s\n", sbr.SBIN_NUM, sbr.SBIN_NAM, sbr.SBIN_PF));
			}
			w.flush();
			w.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//finish parameters results
		for(FileWriter fw : fws)
		{
			try
			{
				if(fw!=null) fw.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public PartCsvExporter(File parent_dir) throws IOException
	{
		out_dir = parent_dir;
		out_dir.mkdirs();
		
		fws = new FileWriter[] {null, null, null, null};
		fws_cnt = new int[] {0,0,0,0};
		//fws_typ = new String[] {"PRR"};
	}
	
	protected File out_dir;
	protected FileWriter[] fws;
	protected int[] fws_cnt;
	//protected String[] fws_typ;
	public final static int FW_IDX_PRR = 0;
	public final static int FW_IDX_PTR = 1;
	public final static int FW_IDX_MPR = 2;
	public final static int FW_IDX_FTR = 3;
	
	protected void writeFile(int idx, String line, String header, String prefix) throws IOException
	{
		if(fws_cnt[idx]%pageLimit==0)
		{
			if(fws[idx]!=null)
			{
				fws[idx].flush();
				fws[idx].close();
			}
			
			int lbl = fws_cnt[idx]/pageLimit;
			String filename = lbl==0 ? String.format("%s.csv", prefix) : 
				String.format("%s_%d.csv",prefix,lbl);
			fws[idx] = new FileWriter(new File(out_dir, filename));
			fws[idx].write(header);
		}
		
		fws[idx].write(line);
		fws_cnt[idx]++;
	}
	
	protected void exportParameter(PartResultsRecord prr, ParametricTestRecord ptr) throws IOException
	{
		String header = "Seq,Head,Site,X,Y,H_Bin,S_Bin,Param,Test_No,Value,Pass\n";
		String line = String.format("%d,%d,%d,%d,%d,%d,%d,%s,%d,%g,%d\n", 
				ptr.getRecordNo(), ptr.HEAD_NUM, ptr.SITE_NUM, 
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				ptr.TEST_TXT, ptr.TEST_NUM, ptr.RESULT, ptr.isPass() ? 1 : 0);
		
		writeFile(FW_IDX_PTR, line, header, "PTR");
		
//		setFileWriter("PTR.csv", "Seq,X,Y,H_Bin,S_Bin,Param,Test_No,Value,Pass\n");
//		
//		out_fw.write(String.format("%d,%d,%d,%d,%d,%s,%d,%g,%d\n", prr.getRecordNo(), 
//				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
//				ptr.TEST_TXT, ptr.TEST_NUM, ptr.RESULT, ptr.isPass() ? 1 : 0));
//		out_fw.close();
	}
	
	protected void exportParameter(PartResultsRecord prr, MultipleResultParametricRecord mpr) throws IOException
	{
		String header = "Seq,Head,Site,X,Y,H_Bin,S_Bin,Param,Test_No,N,Values\n";
		String line = String.format("%d,%d,%d,%d,%d,%d,%d,%s,%d,%d,%s\n", 
				mpr.getRecordNo(), mpr.HEAD_NUM, mpr.SITE_NUM,
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				mpr.TEST_TXT, mpr.TEST_NUM, 
				mpr.RSLT_CNT, Arrays.toString(mpr.RTN_RSLT));
		
		writeFile(FW_IDX_MPR, line, header, "MPR");
		
//		setFileWriter("MPR.csv", "Seq,X,Y,H_Bin,S_Bin,Param,Test_No,N,Values\n");
//		
//		out_fw.write(String.format("%d,%d,%d,%d,%d,%s,%d,%d,%s\n", prr.getRecordNo(), 
//				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
//				mpr.TEST_TXT, mpr.TEST_NUM, 
//				mpr.RSLT_CNT, Arrays.toString(mpr.RTN_RSLT)));
//		out_fw.close();
	}
	
	protected void exportParameter(PartResultsRecord prr, FunctionalTestRecord ftr) throws IOException
	{
		String header = "Seq,Head,Site,X,Y,H_Bin,S_Bin,Param,Test_No,XFail_Ad,YFail_Ad,Fail_Pin\n";
		String line = String.format("%d,%d,%d,%d,%d,%d,%d,%s,%d,%d,%d,%d\n", 
				ftr.getRecordNo(), ftr.HEAD_NUM, ftr.SITE_NUM, 
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				ftr.TEST_TXT, ftr.TEST_NUM, ftr.XFAIL_AD, ftr.YFAIL_AD, ftr.NUM_FAIL);
		
		writeFile(FW_IDX_FTR, line, header, "FTR");
		
//		setFileWriter("FTR.csv", "Seq,X,Y,H_Bin,S_Bin,Param,Test_No,XFail_Ad,YFail_Ad,Fail_Pin\n");
//		
//		out_fw.write(String.format("%d,%d,%d,%d,%d,%s,%d,%d,%d,%d\n", prr.getRecordNo(), 
//				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
//				ftr.TEST_TXT, ftr.TEST_NUM, ftr.XFAIL_AD, ftr.YFAIL_AD, ftr.NUM_FAIL));
//		out_fw.close();
	}
	
	protected void exportPartResult(PartResultsRecord prr) throws IOException
	{
		String header = "Seq,Head,Site,X,Y,H_Bin,S_Bin,ID,Txt\n";
		String line = String.format("%d,%d,%d,%d,%d,%d,%d,%s,%s\n", prr.getRecordNo(), 
				prr.HEAD_NUM, prr.SITE_NUM,
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				prr.PART_ID, prr.PART_TXT);
		
		writeFile(FW_IDX_PRR, line, header, "Part_Result");
		
//		if(out_prr==null)
//		{
//			out_prr = new File(out_dir, filename_part_result);
//			out_fw = new FileWriter(out_prr, true);
//			out_fw.write("Seq,Head,Site,X,Y,H_Bin,S_Bin,ID,Txt\n");
//		}
//		else
//		{
//			out_fw = new FileWriter(out_prr, true);
//		}
//		
//		out_fw.write(String.format("%d,%d,%d,%d,%d,%d,%d,%s,%s\n", prr.getRecordNo(), 
//				prr.HEAD_NUM, prr.SITE_NUM,
//				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
//				prr.PART_ID, prr.PART_TXT));
//		out_fw.close();
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
