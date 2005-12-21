package at.generic.service;

import java.util.List;

import at.generic.etl.SourceEventEtl;
import at.generic.eventmodel.BaseEvent;

/**
 * @author szabolcs
 * @version $Id: EventSourceManager.java,v 1.4 2005/12/21 22:07:42 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.4 $
 * 
 * Interface for the Events Data Manager
 * 
 */
public interface EventSourceManager {
	/**
	 * Initialize basic information for etl module
	 */
	public void init();
	
	/**
	 * Returns a subset of base events including the Raw XML Data
	 * according to the selected page 
	 * 
	 * @return List with all the base events
	 */
	public List getBaseEventsByPage(int pageNr);
	
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
	 * @return Returns the sourceEventEtl.
	 */
	public SourceEventEtl getSourceEventEtl();

	/**
	 * @param sourceEventEtl The sourceEventEtl to set.
	 */
	public void setSourceEventEtl(SourceEventEtl sourceEventEtl);
	
}