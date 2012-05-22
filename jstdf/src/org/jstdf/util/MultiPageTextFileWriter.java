package org.jstdf.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MultiPageTextFileWriter
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
	
	public void reset(int writer_num)
	{
		fws = new FileWriter[writer_num];
		fws_cnt = new int[writer_num];
		for(int i=0; i<writer_num; i++) fws_cnt[i] = 0;
	}
	
	public MultiPageTextFileWriter(File parent, int writer_num)
	{
		out_dir = parent;
		if(out_dir!=null)
		{
			out_dir.mkdirs();
		}
		
		reset(writer_num);
	}
	
	public void dispose()
	{
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
	
	public void write(int idx, String line, String header, String prefix, String suffix) throws IOException
	{
		write(idx,line,header,prefix,suffix,true);
	}
	
	public void write(int idx, String line, String header, String prefix, String suffix,
			boolean multiPage) throws IOException
	{
		if(fws_cnt[idx]==0 || (fws_cnt[idx]%pageLimit==0 && multiPage) )
		{
			if(fws[idx]!=null)
			{
				fws[idx].flush();
				fws[idx].close();
			}
			
			int lbl = fws_cnt[idx]/pageLimit;
			String filename = lbl==0 ? String.format("%s.%s", prefix, suffix) : 
				String.format("%s_%d.%s",prefix, lbl, suffix);
			fws[idx] = new FileWriter(new File(out_dir, filename));
			
			if(header!=null)
			{
				fws[idx].write(header);
				if(!header.endsWith("\n")) fws[idx].write('\n');
			}
		}
		
		if(line!=null)
		{
			fws[idx].write(line);
			if(!line.endsWith("\n")) fws[idx].write('\n');
		}
		fws_cnt[idx]++;
	}
}
