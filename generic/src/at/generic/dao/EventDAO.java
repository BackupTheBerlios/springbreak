package at.generic.dao;

import java.util.List;

import at.generic.eventmodel.Event;
import at.generic.model.Correlatedevent;

/**
 * @author szabolcs
 * @version $Id: EventDAO.java,v 1.2 2006/03/06 23:20:19 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * DAO interface for Event
 * 
 */
public interface EventDAO extends DAO 
{ 
	/**
	 * @return List with Events
	 */
	public List getEvents(); 
	
	/**
	 * Retrieves Events by a list of event ids
	 * 
	 * @param ids
	 * @return Eventattribute
	 */
	public List getEvents(List ids);
	
	/**
	 * @return List with Events using pagination
	 */
	public List getEventsByPage(int pageNumber, int pageSize); 
	
	/**
	 * @param id events id
	 * @return Event event
	 */
	public Event getEvent(Long id);
	
	/**
	 * @param event Event to save
	 */
	public void saveOrUpdateEvent(Event event); 
	
	/**
	 * @param Event Event to update
	 */
	public void updateEvent(Event event); 
	
	/**
	 * @param id events id to remove
	 */
	public void removeEvent(Long id);
}