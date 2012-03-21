package org.jstdf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
	protected String filename_bin_result = "Bin_Result.csv";
	protected String filename_bin_spec = "Bin_Spec.csv";
	
	public PartCsvExporter(File parent_dir) throws IOException
	{
		out_dir = parent_dir;
		out_dir.mkdirs();
	}
	
	protected File out_dir;
	protected Map<String, File> out_fs = new HashMap<String, File>();
	protected FileWriter out_fw;
	
	protected void exportBin(String typ, int num, String name, char pf) throws IOException
	{
		File out_f = out_fs.get(filename_bin_spec);
		if(out_f==null)
		{
			out_f = new File(out_dir, filename_bin_spec);
			out_fs.put(filename_bin_spec, out_f);
			
			out_fw = new FileWriter(out_f, true);
			out_fw.write("Type,Num,Name,Pass_Fail\n");
		}
		else
		{
			out_fw = new FileWriter(out_f, true);
		}
		
		out_fw.write(String.format("%s,%d,%s,%s\n", typ, num, name, pf));
		out_fw.close();
	}
	
	@Override
	public boolean readRecord(HardwareBinRecord hbr) 
	{
		try
		{
			exportBin("Hard",hbr.HBIN_NUM, hbr.HBIN_NAM, hbr.HBIN_PF);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public boolean readRecord(SoftwareBinRecord sbr) 
	{
		try
		{
			exportBin("Soft",sbr.SBIN_NUM, sbr.SBIN_NAM, sbr.SBIN_PF);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	protected void exportParameter(PartResultsRecord prr, ParametricTestRecord ptr) throws IOException
	{
		String filename = String.format("PTR_%s_%d.csv", ptr.TEST_TXT.replaceAll("\\W+", "_"), ptr.TEST_NUM);
		File out_f = out_fs.get(filename);
		if(out_f==null)
		{
			out_f = new File(out_dir, filename);
			out_fs.put(filename, out_f);
			
			out_fw = new FileWriter(out_f, true);
			out_fw.write("Seq,X,Y,H_Bin,S_Bin,Param,Test_No,Value\n");
		}
		else
		{
			out_fw = new FileWriter(out_f, true);
		}
		
		out_fw.write(String.format("%d,%d,%d,%d,%d,%s,%d,%g\n", prr.getRecordNo(), 
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				ptr.TEST_TXT, ptr.TEST_NUM, ptr.RESULT));
		out_fw.close();
	}
	
	protected void exportParameter(PartResultsRecord prr, MultipleResultParametricRecord mpr) throws IOException
	{
		String filename = String.format("MPR_%s_%d.csv", mpr.TEST_TXT.replaceAll("\\W+", "_"), mpr.TEST_NUM);
		File out_f = out_fs.get(filename);
		if(out_f==null)
		{
			out_f = new File(out_dir, filename);
			out_fs.put(filename, out_f);
			
			out_fw = new FileWriter(out_f, true);
			out_fw.write("Seq,X,Y,H_Bin,S_Bin,Param,Test_No,Value\n");
		}
		else
		{
			out_fw = new FileWriter(out_f, true);
		}
		
		out_fw.write(String.format("%d,%d,%d,%d,%d,%s,%d,%s\n", prr.getRecordNo(), 
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				mpr.TEST_TXT, mpr.TEST_NUM, 
				mpr.RTN_RSLT.length==1 ? Double.toString(mpr.RTN_RSLT[0]) : Arrays.toString(mpr.RTN_RSLT)));
		out_fw.close();
	}
	
	protected void exportParameter(PartResultsRecord prr, FunctionalTestRecord ftr) throws IOException
	{
		String filename = String.format("FTR_%s_%d.csv", ftr.TEST_TXT.replaceAll("\\W+", "_"), ftr.TEST_NUM);
		File out_f = out_fs.get(filename);
		if(out_f==null)
		{
			out_f = new File(out_dir, filename);
			out_fs.put(filename, out_f);
			
			out_fw = new FileWriter(out_f, true);
			out_fw.write("Seq,X,Y,H_Bin,S_Bin,Param,Test_No,XFail_Ad,YFail_Ad,Fail_Pin\n");
		}
		else
		{
			out_fw = new FileWriter(out_f, true);
		}
		
		out_fw.write(String.format("%d,%d,%d,%d,%d,%s,%d,%d,%d,%d\n", prr.getRecordNo(), 
				prr.X_COORD, prr.Y_COORD, prr.HARD_BIN, prr.SOFT_BIN,
				ftr.TEST_TXT, ftr.TEST_NUM, ftr.XFAIL_AD, ftr.YFAIL_AD, ftr.NUM_FAIL));
		out_fw.close();
	}
	
	protected void exportPartResult(PartResultsRecord prr) throws IOException
	{
		File out_f = out_fs.get(filename_bin_result);
		if(out_f==null)
		{
			out_f = new File(out_dir, filename_bin_result);
			out_fs.put(filename_bin_result, out_f);
			
			out_fw = new FileWriter(out_f, true);
			out_fw.write("Seq,Head,Site,X,Y,H_Bin,S_Bin,ID,Txt\n");
		}
		else
		{
			out_fw = new FileWriter(out_f, true);
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
			
			for(STDFRecord rec : part_result.getPartResult())
			{
				if(rec.getRecordType()==STDFRecordType.PTR)
				{
					ParametricTestRecord ptr = (ParametricTestRecord)rec;
					exportParameter(prr, ptr);
				}
				else if(rec.getRecordType()==STDFRecordType.MPR)
				{
					MultipleResultParametricRecord mpr = (MultipleResultParametricRecord)rec;
					exportParameter(prr, mpr);
				}
				else if(rec.getRecordType()==STDFRecordType.FTR)
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
