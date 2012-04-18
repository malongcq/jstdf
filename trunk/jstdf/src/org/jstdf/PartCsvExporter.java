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
import org.jstdf.record.SoftwareBinRecord;


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

	@Override
	public void endReadRecord()
	{
		//export test parameters
		try
		{
			FileWriter w = new FileWriter(new File(out_dir, filename_test_params));
			w.write("Test_No,Name,Unit,Low_Spec,High_Spec,Low_Limit,High_Limit\n");
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
	}
	
	protected String filename_part_result = "Part_Result.csv";
	protected String filename_bin_spec = "Bin_Spec.csv";
	protected String filename_test_params = "Test_Params.csv";
	
	public PartCsvExporter(File parent_dir) throws IOException
	{
		out_dir = parent_dir;
		out_dir.mkdirs();
	}
	
	protected File out_dir, out_prr, out_ptr, out_mpr, out_ftr;
	//protected Map<String, File> out_fs = new HashMap<String, File>();
	protected FileWriter out_fw;
	
	protected void setFileWriter(String filename, String header) throws IOException
	{
		File out_f = new File(out_dir, filename);
		if(out_f.exists())
		{
			out_fw = new FileWriter(out_f, true);
		}
		else
		{
			out_fw = new FileWriter(out_f, true);
			out_fw.write(header);
		}
	}
	
	protected void exportParameter(PartResultsRecord prr, ParametricTestRecord ptr) throws IOException
	{
		setFileWriter("PTR.csv", "Seq,X,Y,H_Bin,S_Bin,Param,Test_No,Value,Pass\n");
		
		out_fw.write(String.format("%d,%d,%d,%d,%d,%s,%d,%g,%d\n", prr.getRecordNo(), 
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				ptr.TEST_TXT, ptr.TEST_NUM, ptr.RESULT, ptr.isPass() ? 1 : 0));
		out_fw.close();
	}
	
	protected void exportParameter(PartResultsRecord prr, MultipleResultParametricRecord mpr) throws IOException
	{
		setFileWriter("MPR.csv", "Seq,X,Y,H_Bin,S_Bin,Param,Test_No,N,Values\n");
		
		out_fw.write(String.format("%d,%d,%d,%d,%d,%s,%d,%d,%s\n", prr.getRecordNo(), 
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				mpr.TEST_TXT, mpr.TEST_NUM, 
				mpr.RSLT_CNT, Arrays.toString(mpr.RTN_RSLT)));
		out_fw.close();
	}
	
	protected void exportParameter(PartResultsRecord prr, FunctionalTestRecord ftr) throws IOException
	{
		setFileWriter("FTR.csv", "Seq,X,Y,H_Bin,S_Bin,Param,Test_No,XFail_Ad,YFail_Ad,Fail_Pin\n");
		
		out_fw.write(String.format("%d,%d,%d,%d,%d,%s,%d,%d,%d,%d\n", prr.getRecordNo(), 
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				ftr.TEST_TXT, ftr.TEST_NUM, ftr.XFAIL_AD, ftr.YFAIL_AD, ftr.NUM_FAIL));
		out_fw.close();
	}
	
	protected void exportPartResult(PartResultsRecord prr) throws IOException
	{
		if(out_prr==null)
		{
			out_prr = new File(out_dir, filename_part_result);
			out_fw = new FileWriter(out_prr, true);
			out_fw.write("Seq,Head,Site,X,Y,H_Bin,S_Bin,ID,Txt\n");
		}
		else
		{
			out_fw = new FileWriter(out_prr, true);
		}
		
		out_fw.write(String.format("%d,%d,%d,%d,%d,%d,%d,%s,%s\n", prr.getRecordNo(), 
				prr.HEAD_NUM, prr.SITE_NUM,
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				prr.PART_ID, prr.PART_TXT));
		out_fw.close();
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
