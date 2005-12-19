package at.generic.service;

import java.util.List;
import java.util.Vector;

/**
 * @author szabolcs
 * @version $Id: EventSourceManager.java,v 1.3 2005/12/19 23:17:25 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.3 $
 * 
 * Interface for the Events Data Manager
 * 
 */
public interface EventSourceManager {
	/**
	 * Returns all CorrelatedEvents with Raw XML Data
	 * 
	 * @return List with all correlated events
	 */
	public List getAllCorrelatedEvents();
	
	/**
	 * Returns a Vector with the parsed xml events and the raw data attached
	 * @return
	 */
	public Vector getAllCorrelatedEventModels();
	
	/**
	 * Returns a subset of CorrelatedEvents with Raw XML Data
	 * according to the selected page 
	 * 
	 * @return List with all correlated events
	 */
	public Vector getCorrelatedEventsByPage(int pageNr);
	
	/**
	 * Returns the number of identified and parsed events
	 * 
	 * @return Returns the numberOfEvents.
	 */
	public int getNumberOfEvents();

	/**
	 * sets the number of identified and parsed events
	 * 
	 * @param numberOfEvents The numberOfEvents to set.
	 */
	public void setNumberOfEvents(int numberOfEvents);

	/**
	 * Gets the maximum page size - used in the browser view
	 * 
	 * @return Returns the pageSize.
	 */
	public int getPageSize();

	/**
	 * Sets the maximum page size - used in the browser view
	 * 
	 * @param pageSize The pageSize to set.
	 */
	public void setPageSize(int pageSize);

	/**
	 * Flag that indicates if the maximum number of retrieved objects has been setted
	 * 
	 * @return Returns the populatedNumberOfEvents.
	 */
	public boolean getPopulatedNumberOfEvents();

	/**
	 * Flag that indicates if the maximum number of retrieved objects has been setted
	 * 
	 * @param populatedNumberOfEvents The populatedNumberOfEvents to set.
	 */
	public void setPopulatedNumberOfEvents(boolean populatedNumberOfEvents);
	
}