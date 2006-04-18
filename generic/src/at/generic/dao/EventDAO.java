package at.generic.dao;

import java.util.List;

import at.generic.eventmodel.Event;
import at.generic.model.Correlatedevent;

/**
 * @author szabolcs
 * @version $Id: EventDAO.java,v 1.3 2006/04/18 22:39:27 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.3 $
 * 
 * DAO interface for Event
 * 
 */
public interface EventDAO extends DAO 
{ 
	/**
	 * Returns the size of the relation
	 * see http://www.hibernate.org/hib_docs/reference/en/html/queryhql.html 11.13. Tips & Tricks
	 * 
	 * @return count
	 */
	public int getCount();
	
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