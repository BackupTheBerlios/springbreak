package at.generic.dao;

import java.util.List;

import at.generic.eventmodel.Event;
import at.generic.model.Correlatedevent;

/**
 * @author szabolcs
 * @version $Id: EventDAO.java,v 1.1 2006/02/27 14:57:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
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