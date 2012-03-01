package net.sf.jstdf.record;

public enum STDFRecordType 
{
	/**
	 * 0 Information about the STDF file
	 * 10 File Attributes Record (FAR)
	 * 20 Audit Trail Record (ATR)
	 */
	FAR, ATR,
	
	/**
	 * 1 Data collected on a per lot basis
	 * 10 Master Information Record (MIR)
	 * 20 Master Results Record (MRR)
	 * 30 Part Count Record (PCR)
	 * 40 Hardware Bin Record (HBR)
	 * 50 Software Bin Record (SBR)
	 * 60 Pin Map Record (PMR)
	 * 62 Pin Group Record (PGR)
	 * 63 Pin List Record (PLR)
	 * 70 Retest Data Record (RDR)
	 * 80 Site Description Record (SDR)
	 */
	MIR, MRR, PCR, HBR, SBR, PMR, PGR, PLR, RDR, SDR,
	
	/**
	 * 2 Data collected per wafer
	 * 10 Wafer Information Record (WIR)
	 * 20 Wafer Results Record (WRR)
	 * 30 Wafer Configuration Record (WCR)
	 */
	WIR, WRR, WCR,
	
	/**
	 * 5 Data collected on a per part basis
	 * 10 Part Information Record (PIR)
	 * 20 Part Results Record (PRR)
	 */
	PIR, PRR,
	
	/**
	 * 10 Data collected per test in the test program
	 * 30 Test Synopsis Record (TSR)
	 */
	TSR, 
	
	/**
	 * 15 Data collected per test execution
	 * 10 Parametric Test Record (PTR)
	 * 15 Multiple-Result Parametric Record (MPR)
	 * 20 Functional Test Record (FTR)
	 */
	PTR, MPR, FTR,
	
	/**
	 * 20 Data collected per program segment
	 * 10 Begin Program Section Record (BPS)
	 * 20 End Program Section Record (EPS)
	 */
	BPS, EPS,
	
	/**
	 * 50 Generic Data
	 * 10 Generic Data Record (GDR)
	 * 30 Datalog Text Record (DTR)
	 */
	GDR, DTR,
	
	Unknown
}
