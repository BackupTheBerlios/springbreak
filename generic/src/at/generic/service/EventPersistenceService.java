package at.generic.service;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.dao.EventDAO;
import at.generic.dao.EventattributeDAO;
import at.generic.dao.EventtypeDAO;
import at.generic.dao.RwtimeDAO;
import at.generic.dao.TxtimeDAO;
import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventattribute;
import at.generic.eventmodel.Eventtype;
import at.generic.model.Correlatedevent;

/**
 * @author szabolcs
 * @version $Id: EventPersistenceService.java,v 1.1 2006/02/27 14:59:03 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Facade for event persitence operations
 */
public interface EventPersistenceService {
	
	// ========== main event stuff  ===========
	
	/**
	 * Returns a list with all events
	 * 
	 * @return List with Events
	 */
	public List getEvents(); 
	
	/**
	 * Returns a list of events using pagination
	 * 
	 * @return List with Events using pagination
	 */
	public List getEventsByPage(int pageNumber, int pageSize); 
	
	/**
	 * Returns an event with the given id 
	 * 
	 * @param id events id
	 * @return Event event
	 */
	public Event getEvent(Long id);
	
	/**
	 * Saves or Updates the given event model
	 * You don't have to set rwtimeid, txtimeid or the eventtype!
	 * 
	 * @param event Event to save
	 */
	public void saveOrUpdateEvent(Event event); 
	
	/**
	 * Transforms a given correlatedevent into an event model and 
	 * saves or update it afterwards and returns the event model
	 * 
	 * @param CorrelatedEvent to be saved/updated
	 */
	public Event saveOrUpdateEvent(Correlatedevent CorrelatedEvent);
	
	/**
	 * Actually the same like saveOrUpdateEvent() but more performant. 
	 * 
	 * @param Event Event to update
	 */
	public void updateEvent(Event event); 
	
	/**
	 * Removes an event with the given id
	 * 
	 * @param id events id to remove
	 */
	public void removeEvent(Long id);
	
	/**
	 * Counts the number of itedentified items in the database
	 * 
	 * @return number if identified items
	 */
	public int getNumberOfIdentifiedEvents();
	
	/**
	 * Counts the number of itedentified items in the database
	 * 
	 * @return number if identified items
	 */
	public List getIdentifiedEventTypes();
	
    // ========== correlated event stuff  ===========	
		
	/**
	 * Retrieves the correlated events
	 * 
	 * @return List with correlated events
	 */
	public List getCorrelatedevents();
	
	// ========== eventattribute stuff  =========== 
	
	/**
	 * @return List with Eventattributes
	 */
	public List getEventattributes();
	
	/**
	 * @return List with Eventattributes
	 */
	public List getEventattributesForEvent(Long id);
	
	/**
	 * @return List with Eventattributes using pagination
	 */
	public List getEventattributesByPage(int pageNumber, int pageSize); 
	
	/**
	 * Retrieves Eventattribute by its id
	 * 
	 * @param id 
	 * @return Eventattribute
	 */
	public Eventattribute getEventattribute(Long id);
	
	/**
	 * Saves an eventattribute
	 * 
	 * @param eventAttribute to save
	 */
	public void saveOrUpdateEventattribute(Eventattribute eventAttribute); 
	
	/**
	 * Updates an eventattribute
	 * 
	 * @param eventAttribute to update
	 */
	public void updateEventattribute(Eventattribute eventAttribute); 
	
	/**
	 * Removes an eventattribute
	 * 
	 * @param id
	 */
	public void removeEventattribute(Long id);
	
	// ========== Attribute getters and setters  ===========
	
	/**
	 * @return Returns the eventattributeDAO.
	 */
	public EventattributeDAO getEventattributeDAO();

	/**
	 * @param eventattributeDAO The eventattributeDAO to set.
	 */
	public void setEventattributeDAO(EventattributeDAO eventattributeDAO);
	/**
	 * @return Returns the eventDAO.
	 */
	public EventDAO getEventDAO();

	/**
	 * @param eventDAO The eventDAO to set.
	 */
	public void setEventDAO(EventDAO eventDAO);

	/**
	 * @return Returns the eventtypeDAO.
	 */
	public EventtypeDAO getEventtypeDAO();

	/**
	 * @param eventtypeDAO The eventtypeDAO to set.
	 */
	public void setEventtypeDAO(EventtypeDAO eventtypeDAO);

	/**
	 * @return Returns the rwtimeDAO.
	 */
	public RwtimeDAO getRwtimeDAO();

	/**
	 * @param rwtimeDAO The rwtimeDAO to set.
	 */
	public void setRwtimeDAO(RwtimeDAO rwtimeDAO);

	/**
	 * @return Returns the txtimeDAO.
	 */
	public TxtimeDAO getTxtimeDAO();

	/**
	 * @param txtimeDAO The txtimeDAO to set.
	 */
	public void setTxtimeDAO(TxtimeDAO txtimeDAO);
	
	/**
	 * @return Returns the correlatedEventDAO.
	 */
	public CorrelatedeventDAO getCorrelatedEventDAO();

	/**
	 * @param correlatedEventDAO The correlatedEventDAO to set.
	 */
	public void setCorrelatedEventDAO(CorrelatedeventDAO correlatedEventDAO);
}