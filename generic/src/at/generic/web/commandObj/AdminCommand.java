package at.generic.web.commandObj;

import java.util.Date;
import java.util.List;

/**
 * @author szabolcs
 * @version $Id: AdminCommand.java,v 1.3 2006/02/01 19:48:51 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.3 $
 * 
 * Data Object for the ETL Admin section
 * 
 */
public class AdminCommand { 
	private String updatestart;
	private String updatestop;
	private List identifiedEvents;
	private int numberOfIdentifiedEvents;
	private int numberOfProcessedEvents;
	private int numberOfEventsInSource;
	private double etlBarLength;
	
	
	/**
	 * @return Returns the identifiedEvents.
	 */
	public List getIdentifiedEvents() {
		return identifiedEvents;
	}
	/**
	 * @param identifiedEvents The identifiedEvents to set.
	 */
	public void setIdentifiedEvents(List identifiedEvents) {
		this.identifiedEvents = identifiedEvents;
	}
	
	/**
	 * @return Returns the numberOfIdentifiedEvents.
	 */
	public int getNumberOfIdentifiedEvents() {
		return numberOfIdentifiedEvents;
	}
	/**
	 * @param numberOfIdentifiedEvents The numberOfIdentifiedEvents to set.
	 */
	public void setNumberOfIdentifiedEvents(int numberOfIdentifiedEvents) {
		this.numberOfIdentifiedEvents = numberOfIdentifiedEvents;
	}
	/**
	 * @return Returns the numberOfProcessedEvents.
	 */
	public int getNumberOfProcessedEvents() {
		return numberOfProcessedEvents;
	}
	/**
	 * @param numberOfProcessedEvents The numberOfProcessedEvents to set.
	 */
	public void setNumberOfProcessedEvents(int numberOfProcessedEvents) {
		this.numberOfProcessedEvents = numberOfProcessedEvents;
	}
	/**
	 * @return Returns the etlBarLength.
	 */
	public double getEtlBarLength() {
		return etlBarLength;
	}
	/**
	 * @param etlBarLength The etlBarLength to set.
	 */
	public void setEtlBarLength(double etlBarLength) {
		this.etlBarLength = etlBarLength;
	}
	/**
	 * @return Returns the updatestart.
	 */
	public String getUpdatestart() {
		return updatestart;
	}
	/**
	 * @param updatestart The updatestart to set.
	 */
	public void setUpdatestart(String updatestart) {
		this.updatestart = updatestart;
	}
	/**
	 * @return Returns the updatestop.
	 */
	public String getUpdatestop() {
		return updatestop;
	}
	/**
	 * @param updatestop The updatestop to set.
	 */
	public void setUpdatestop(String updatestop) {
		this.updatestop = updatestop;
	}
	
	
	
	
	
	
}