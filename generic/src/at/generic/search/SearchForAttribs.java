package at.generic.search;

import java.util.List;

import at.generic.dao.GenericServiceDAO;
import at.generic.service.EventHandling;

/**
 * @author szabolcs
 * @version $Id: SearchForAttribs.java,v 1.1 2006/02/14 10:09:38 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Searchinterface for Events.
 * 
 */
public interface SearchForAttribs  {
	
	/**
	 * Searches all Attributes of events for the given word(s) 
	 * and returns a List with found Events.
	 * 
	 * @param searchStr
	 * @return List with Events
	 */
	public List getEventsForFoundAttribs(String searchStr);
	
	/**
	 * @param eventHandling The eventHandling to set.
	 */
	public void setEventHandling(EventHandling eventHandling);

	/**
	 * @return Returns the genericServiceTarget.
	 */
	public GenericServiceDAO getGenericServiceTarget();

	/**
	 * @param genericServiceTarget The genericServiceTarget to set.
	 */
	public void setGenericServiceTarget(GenericServiceDAO genericServiceTarget);
	
	/**
	 * @return Returns the fetchLimit.
	 */
	public int getFetchLimit(); 

	/**
	 * @param fetchLimit The fetchLimit to set.
	 */
	public void setFetchLimit(int fetchLimit);
}