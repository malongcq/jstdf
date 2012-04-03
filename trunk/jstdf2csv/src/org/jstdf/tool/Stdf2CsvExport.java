package org.jstdf.tool;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.jstdf.PartCsvExporter;
import org.jstdf.SimpleSTDFSummary;
import org.jstdf.StdfReader;
import org.jstdf.WaferTestResult;

public class Stdf2CsvExport 
{
	static Options createOptions()
	{
		Options opts = new Options();
		
		Option help = new Option("help", false, "print help message");
		Option quiet = new Option("quiet", false, "be quiet when reading file" );
		Option summary = new Option("summary", false, "print STDF data file summary");
		Option wafer = new Option("wafer", false, "<under test>");
		Option param = new Option("param", true, "search parameters for export, " +
				"wildcard ? and * is supported, " +
				"omit this option to export all parameters. " +
				"DON'T USE SINGLE * OR ?, SHELL DOES INTERCEPT IT." );
		param.setArgName("parameter name");
		Option out = new Option("out", true, "output directory for .csv files, " +
				"default is [stdf_csv]" );
		out.setArgName("output dir");
		
		opts.addOption(help);
		opts.addOption(summary);
		opts.addOption(wafer);
		opts.addOption(quiet);
		opts.addOption(param);
		opts.addOption(out);
		
		return opts;
	}
	
	static void printHelp(Options opts)
	{
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("stdf2csv [Options]... [STDF File (*.stdf or *.stdf.gz)]", "", opts, "");
	}
	
	/**
	 * http://www.rgagnon.com/javadetails/java-0515.html
	 * @param wildcard
	 * @return
	 */
	public static final String wildcardToRegex(String wildcard)
	{
		StringBuffer s = new StringBuffer(wildcard.length());
		s.append('^');
        for (int i = 0, is = wildcard.length(); i < is; i++) 
        {
            char c = wildcard.charAt(i);
            switch(c) 
            {
                case '*':
                    s.append(".*");
                    break;
                case '?':
                    s.append(".");
                    break;
                    // escape special regexp-characters
                case '(': case ')': case '[': case ']': case '$':
                case '^': case '.': case '{': case '}': case '|':
                case '\\':
                    s.append("\\");
                    s.append(c);
                    break;
                default:
                    s.append(c);
                    break;
            }
        }
        s.append('$');
        return(s.toString());
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		CommandLineParser parser = new GnuParser();
		Options opts = createOptions();
		try
		{
			CommandLine cmd = parser.parse(opts, args);
			
			if(cmd.hasOption("help"))
			{
				printHelp(opts);
				return;
			}
			
			StdfReader reader = new StdfReader();
			reader.setVerbose(!cmd.hasOption("quiet"));
			
			String in_file = cmd.getArgs()[0];
			System.out.println("Reading file..." + in_file);
			
			Date d1, d2;
			d1 = new Date();
			
			if(cmd.hasOption("summary"))
			{
				SimpleSTDFSummary stdf = new SimpleSTDFSummary();
				reader.setRecordHandler(stdf);
				reader.loadFromSTDF(in_file);
				stdf.printSummary();
			}
			else if(cmd.hasOption("wafer"))
			{
				WaferTestResult stdf = new WaferTestResult();
				reader.setRecordHandler(stdf);
				reader.loadFromSTDF(in_file);
			}
			else
			{
				File out_dir = new File(cmd.getOptionValue("out", "stdf_out"));
				PartCsvExporter stdf = new PartCsvExporter(out_dir);
				
				if(cmd.hasOption("param"))
				{
					String param = cmd.getOptionValue("param", "");
					System.out.println("Searching parameters..." + param);
					param = wildcardToRegex(param);
					stdf.setTestParameterPattern(param);
				}
				
				reader.setRecordHandler(stdf);
				reader.loadFromSTDF(in_file);
			}
			d2 = new Date();
			System.out.println((d2.getTime()-d1.getTime())/1000+"s"); 
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			System.out.println("cmd=" + Arrays.asList(args));
			printHelp(opts);
		}
		finally
		{
			
		}
	}
}
