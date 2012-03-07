package org.jstdf.record;

/**
 * Represents an STDF record type.
 * 
 * @author malong
 *
 */
public enum STDFRecordType 
{
	//0 Information about the STDF file
	/**
	 * type=0, sub=10 File Attributes Record (FAR)
	 */
	FAR, 
	/**
	 * type=0, sub=20 Audit Trail Record (ATR)
	 */
	ATR,
	
	//1 Data collected on a per lot basis
	/**
	 * type=1, sub=10 Master Information Record (MIR)
	 */
	MIR, 
	/**
	 * type=1, sub=20 Master Results Record (MRR)
	 */
	MRR,
	/**
	 * type=1, sub=30 Part Count Record (PCR)
	 */
	PCR,
	/**
	 * type=1, sub=40 Hardware Bin Record (HBR)
	 */
	HBR, 
	/**
	 * type=1, sub=50 Software Bin Record (SBR)
	 */
	SBR,
	/**
	 * type=1, sub=60 Pin Map Record (PMR)
	 */
	PMR, 
	/**
	 * type=1, sub=62 Pin Group Record (PGR)
	 */
	PGR,
	/**
	 * type=1, sub=63 Pin List Record (PLR)
	 */
	PLR, 
	/**
	 * type=1, sub=70 Retest Data Record (RDR)
	 */
	RDR, 
	/**
	 * type=1, sub=80 Site Description Record (SDR)
	 */
	SDR,
	
	//2 Data collected per wafer
	/**
	 * type=2, sub=10 Wafer Information Record (WIR)
	 */
	WIR, 
	/**
	 * type=2, sub=20 Wafer Results Record (WRR)
	 */
	WRR,
	/**
	 * type=2, sub=30 Wafer Configuration Record (WCR)
	 */
	WCR,
	
	//5 Data collected on a per part basis
	/**
	 * type=5, sub=10 Part Information Record (PIR)
	 */
	PIR, 
	/**
	 * type=5, sub=20 Part Results Record (PRR)
	 */
	PRR,
	
	//10 Data collected per test in the test program
	/**
	 * type=10, sub=30 Test Synopsis Record (TSR)
	 */
	TSR, 
	
	//15 Data collected per test execution
	/**
	 * type=15, sub=10 Parametric Test Record (PTR)
	 */
	PTR, 
	/**
	 * type=15, sub=15 Multiple-Result Parametric Record (MPR)
	 */
	MPR,
	/**
	 * type=15, sub=20 Functional Test Record (FTR)
	 */
	FTR,
	
	//20 Data collected per program segment
	/**
	 * type=20, sub=10 Begin Program Section Record (BPS)
	 */
	BPS, 
	/**
	 * type=20, sub=20 End Program Section Record (EPS)
	 */
	EPS,
	
	//50 Generic Data
	/**
	 * type=50, sub=10 Generic Data Record (GDR)
	 */
	GDR, 
	/**
	 * type=50, sub=30 Datalog Text Record (DTR)
	 */
	DTR,
	
	/**
	 * unknown stdf record type
	 */
	Unknown
}
