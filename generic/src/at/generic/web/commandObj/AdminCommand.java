package at.generic.web.commandObj;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author szabolcs
 * @version $Id: AdminCommand.java,v 1.1 2005/12/19 23:17:35 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Data Object for the ETL Admin section
 * 
 */
public class AdminCommand { 
	private Date lastUpdate;
	private ArrayList identifiedEvents;
	private int numberOfIdentifiedEvents;
	private int numberOfProcessedEvents;
	
	
	/**
	 * @return Returns the identifiedEvents.
	 */
	public ArrayList getIdentifiedEvents() {
		return identifiedEvents;
	}
	/**
	 * @param identifiedEvents The identifiedEvents to set.
	 */
	public void setIdentifiedEvents(ArrayList identifiedEvents) {
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