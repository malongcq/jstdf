package org.jstdf.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvExporter
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
	
	protected File out_dir;
	protected FileWriter[] fws;
	protected int[] fws_cnt;
	
	public CsvExporter(File parent, int writer_num)
	{
		out_dir = parent;
		if(out_dir!=null)
		{
			out_dir.mkdirs();
		}
		
		fws = new FileWriter[writer_num];
		fws_cnt = new int[writer_num];
		for(int i=0; i<writer_num; i++) fws_cnt[i] = 0;
	}
	
	public void export(int idx, String line, String header, String prefix) throws IOException
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
}
