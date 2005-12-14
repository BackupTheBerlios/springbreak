package at.generic.service;

import java.util.List;

import at.generic.model.Correlatedevent;

/**
 * @author szabolcs
 * @version $Id: EventSourceManager.java,v 1.1 2005/12/14 09:58:12 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Interface for the Events Data Manager
 * 
 */
public interface EventSourceManager {
	/**
	 * Returns all CorrelatedEvents
	 * 
	 * @return List with all correlated events
	 */
	public List getAllCorrelatedEvents();
	
	/**
	 * Returns the DateTime for a given correlatedEvent
	 * 
	 * @param correlatedEvent
	 * @return String with DateTime 
	 */
	public String getDateTimeForEvent(Correlatedevent correlatedEvent);
	

}