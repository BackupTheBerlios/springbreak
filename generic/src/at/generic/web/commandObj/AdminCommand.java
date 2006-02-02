package at.generic.web.commandObj;

import java.util.Date;
import java.util.List;

/**
 * @author szabolcs
 * @version $Id: AdminCommand.java,v 1.4 2006/02/02 19:41:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.4 $
 * 
 * Data Object for the ETL Admin section
 * 
 */
public class AdminCommand { 
	private String updatestart;
	private String updatestop;
	private String etlThreadStartedAt;
	private List identifiedEvents;
	private int numberOfIdentifiedEvents;
	private int numberOfProcessedEvents;
	private int numberOfEventsInSource;
	private double etlBarLength;
	private boolean etlRunning;
	
	
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
	/**
	 * @return Returns the etlRunning.
	 */
	public boolean isEtlRunning() {
		return etlRunning;
	}
	/**
	 * @param etlRunning The etlRunning to set.
	 */
	public void setEtlRunning(boolean etlRunning) {
		this.etlRunning = etlRunning;
	}
	/**
	 * @return Returns the etlThreadStartedAt.
	 */
	public String getEtlThreadStartedAt() {
		return etlThreadStartedAt;
	}
	/**
	 * @param etlThreadStartedAt The etlThreadStartedAt to set.
	 */
	public void setEtlThreadStartedAt(String etlThreadStartedAt) {
		this.etlThreadStartedAt = etlThreadStartedAt;
	}
	
	
}