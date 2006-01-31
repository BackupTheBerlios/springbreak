package at.generic.web.commandObj;

import java.util.Date;
import java.util.List;

/**
 * @author szabolcs
 * @version $Id: AdminCommand.java,v 1.2 2006/01/31 20:15:15 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * Data Object for the ETL Admin section
 * 
 */
public class AdminCommand { 
	private Date lastUpdate;
	private List identifiedEvents;
	private int numberOfIdentifiedEvents;
	private int numberOfProcessedEvents;
	private int numberOfEventsInSource;
	
	
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
	 * @return Returns the lastUpdate.
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}
	/**
	 * @param lastUpdate The lastUpdate to set.
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
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
	
	
	
	
}