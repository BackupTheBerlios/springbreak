package at.generic.service;

import java.util.List;
import java.util.Vector;

/**
 * @author szabolcs
 * @version $Id: EventSourceManager.java,v 1.2 2005/12/14 22:15:14 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
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
	
}