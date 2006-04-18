package at.generic.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.dao.EventDAO;
import at.generic.dao.EventattributeDAO;
import at.generic.dao.EventtypeDAO;
import at.generic.dao.RwtimeDAO;
import at.generic.dao.TxtimeDAO;
import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventattribute;
import at.generic.eventmodel.Eventtype;
import at.generic.eventmodel.Rwtime;
import at.generic.eventmodel.Txtime;
import at.generic.model.Correlatedevent;
import at.generic.service.EventPersistenceService;
import at.generic.util.EventDate;
import at.generic.util.XMLUtils;

/**
 * @author szabolcs
 * @version $Id: EventPersistenceServiceImpl.java,v 1.4 2006/04/18 22:39:02 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.4 $
 * 
 * Facade for event persitence operations
 */
public class EventPersistenceServiceImpl implements EventPersistenceService {
	private static Log log = LogFactory.getLog(EventPersistenceServiceImpl.class);
	
	private EventDAO eventDAO;
	private EventattributeDAO eventattributeDAO;
	private EventtypeDAO eventtypeDAO;
	private CorrelatedeventDAO correlatedEventDAO;
	private RwtimeDAO rwtimeDAO;
	private TxtimeDAO txtimeDAO; 
	
	/**
	 * Returns a list with all events
	 * 
	 * @return List with Events
	 */
	public List getEvents() {
		return eventDAO.getEvents();
	}
	
	/**
	 * Retrieves Events by a list of event ids
	 * 
	 * @param ids
	 * @return Eventattribute
	 */
	public List getEvents(List ids) {
		return eventDAO.getEvents(ids);
	}
	
	
	/**
	 * Returns a list of events using pagination
	 * 
	 * @return List with Events using pagination
	 */
	public List getEventsByPage(int pageNumber, int pageSize) {
		return eventDAO.getEventsByPage(pageNumber, pageSize);
	}
	
	/**
	 * Returns an event with the given id 
	 * 
	 * @param id events id
	 * @return Event event
	 */
	public Event getEvent(Long id) {
		return eventDAO.getEvent(id);
	}
	
	/**
	 * Saves or Updates the given event
	 * You don't have to set rwtimeid, txtimeid or the eventtype!
	 * 
	 * @param event Event to save
	 */
	public void saveOrUpdateEvent(Event event) {
		if (event.getXmlcontent() != null || !event.getXmlcontent().equals("")) {
			Document eventXmlDoc = new XMLUtils().convertXMLStringToDocument(event.getXmlcontent());
			
			// save eventtype 
			Eventtype eventTypeObj = new Eventtype();
			eventTypeObj.setEventname(eventXmlDoc.getRootElement().getName());
			this.eventtypeDAO.saveOrUpdateEventtype(eventTypeObj);
			
			// store eventtypeid
			Eventtype createdEventType = this.eventtypeDAO.getEventtypeByName(eventTypeObj.getEventname());
			if (createdEventType != null)
				event.setEventtypeid(createdEventType.getEventtypeid());
			
			// save rwtime
			Rwtime rwTime = new Rwtime();
			EventDate rwTimeDate = new EventDate(eventXmlDoc.selectSingleNode("/" + eventXmlDoc.getRootElement().getName()).valueOf("@localTimeCreatedRW"));
			rwTime.setRwday(new Short((short)rwTimeDate.getDay()));
			rwTime.setRwmonth(new Short((short)rwTimeDate.getMonth()));
			rwTime.setRwyear(new Short((short)rwTimeDate.getYear()));
			this.rwtimeDAO.saveOrUpdateRwtime(rwTime);
			
			// store rwtimeid
			Rwtime createdRwtime = this.rwtimeDAO.getRwTimeByDates(rwTime.getRwday().intValue(), rwTime.getRwmonth().intValue(), rwTime.getRwyear().intValue());
			if (createdRwtime != null)
				event.setRwtimeid(createdRwtime.getRwtimeid());
			
			// save txtime
			Txtime txTime = new Txtime();
			EventDate txTimeDate = new EventDate(eventXmlDoc.selectSingleNode("/" + eventXmlDoc.getRootElement().getName()).valueOf("@utcTimeCreatedRW"));
			txTime.setTxday(new Short((short)txTimeDate.getDay()));
			txTime.setTxmonth(new Short((short)txTimeDate.getMonth()));
			txTime.setTxyear(new Short((short)txTimeDate.getYear()));
			this.txtimeDAO.saveOrUpdateTxtime(txTime);
			
			// store txtime
			Txtime createdTxtime = this.txtimeDAO.getTxTimeByDates(txTime.getTxday().intValue(), txTime.getTxmonth().intValue(), txTime.getTxyear().intValue());
			if (createdTxtime != null)
				event.setTxtimeid(createdTxtime.getTxtimeid());
			
			eventDAO.saveOrUpdateEvent(event);
		}
	}
	
	/**
	 * Transforms a given correlatedevent into an event model and 
	 * saves or update it afterwards and returns the event model
	 * 
	 * @param CorrelatedEvent to be saved/updated
	 */
	public Event saveOrUpdateEvent(Correlatedevent correlatedEvent) {
		if (correlatedEvent != null || correlatedEvent.getEventXml() != null || !correlatedEvent.getEventXml().equals("")) {
			// extract event header attributes and store them
			Document corrEvent = new XMLUtils().convertXMLStringToDocument(correlatedEvent.getEventXml());
			Element root = corrEvent.getRootElement();
			
			log.debug("### ------------------------------------------------------- ");
			log.debug("### Id: " + correlatedEvent.getId());
			log.debug("### root element name: " + corrEvent.getRootElement().getName());
			
			Event eventStore = new Event();
			eventStore.setEventid(new Long(correlatedEvent.getId().longValue()));
			eventStore.setXmlcontent(correlatedEvent.getEventXml());
			eventStore.setGuid(corrEvent.selectSingleNode("/" + root.getName()).valueOf("@originalGuid"));
			eventStore.setPriority(corrEvent.selectSingleNode("/" + root.getName()).valueOf("@priority"));
			eventStore.setLocaltimeid(new EventDate(corrEvent.selectSingleNode("/" + root.getName()).valueOf("@localTimeCreated")).getDateFormat());
			eventStore.setUtctimeid(new EventDate(corrEvent.selectSingleNode("/" + root.getName()).valueOf("@utcTimeCreated")).getDateFormat());
			eventStore.setLocalrwtimeid(new EventDate(corrEvent.selectSingleNode("/" + root.getName()).valueOf("@localTimeCreatedRW")).getDateFormat());
			eventStore.setUtcrwtimeid(new EventDate(corrEvent.selectSingleNode("/" + root.getName()).valueOf("@utcTimeCreatedRW")).getDateFormat());
			
			this.saveOrUpdateEvent(eventStore);
			
			return eventStore;
		} 
		
		return null;
	}
	
	/**
	 * Actually the same like saveOrUpdateEvent() but more performant. 
	 * 
	 * @param Event Event to update
	 */
	public void updateEvent(Event event) {
		eventDAO.updateEvent(event);
	}
	
	/**
	 * Removes an event with the given id
	 * 
	 * @param id events id to remove
	 */
	public void removeEvent(Long id) {
		eventDAO.removeEvent(id);
	}
	
	/**
	 * Counts the number of itedentified items in the database
	 * 
	 * @return number if identified items
	 */
	public int getNumberOfIdentifiedEvents() {
		//List events = eventDAO.getEvents();
		return eventDAO.getCount();	
	}
	
	/**
	 * Counts the number of source Events
	 * 
	 * @return number if identified items
	 */
	public int getNumberOfSourceEvents() {
		//List events = eventDAO.getEvents();
		return this.correlatedEventDAO.getCount();	
	}
	
	/**
	 * Counts the number of itedentified items in the database
	 * 
	 * @return number if identified items
	 */
	public List getIdentifiedEventTypes() {
		List eventTypes = eventtypeDAO.getEventtypes();
		List identifiedTypes = new Vector();
		
		Iterator i = eventTypes.iterator();
		while (i.hasNext()) {
			Eventtype eventType = (Eventtype) i.next();
			identifiedTypes.add(eventType.getEventname());
		}
		
		return identifiedTypes; 
	}
	
	// ========== eventtye  ===========	
	
	/**
	 * @param id eventtypes id
	 * @return Eventtype eventtype
	 */
	public Eventtype getEventtype(Integer id) {
		return eventtypeDAO.getEventtype(id);
	}
	
	/**
	 * Retrieves eventtype its name
	 * 
	 * @param eventname
	 */
	public Eventtype getEventtypeByName(String eventname) {
		return eventtypeDAO.getEventtypeByName(eventname);
	}

// ========== eventattribute stuff  =========== 
	
	/**
	 * @return List with Eventattributes
	 */
	public List getEventattributes() {
		return eventattributeDAO.getEventattributes();
	}
	
	/**
	 * Retrieves Eventattributes by a given id
	 * 
	 * @param ids
	 * @return List with eventattributes
	 */
	public List getEventattributesForEvent(Long id) {
		return eventattributeDAO.getEventattributesForEvent(id);
	}
	
	/**
	 * Retrieves Eventattributes by a list of event ids
	 * 
	 * @param ids
	 * @return List with eventattributes
	 */
	public List getEventattributesForEvent(List id) {
		return eventattributeDAO.getEventattributesForEvent(id);
	}
	
	/**
	 * @return List with Eventattributes using pagination
	 */
	public List getEventattributesByPage(int pageNumber, int pageSize) {
		return eventattributeDAO.getEventattributesByPage(pageNumber, pageSize);
	}
	
	/**
	 * Retrieves Eventattribute by its id
	 * 
	 * @param id 
	 * @return Eventattribute
	 */
	public Eventattribute getEventattribute(Long id) {
		return eventattributeDAO.getEventattribute(id);
	}
	
	/**
	 * Saves an eventattribute
	 * 
	 * @param eventAttribute to save
	 */
	public void saveOrUpdateEventattribute(Eventattribute eventAttribute) {
		eventattributeDAO.saveOrUpdateEventattribute(eventAttribute);
	}
	
	/**
	 * Updates an eventattribute
	 * 
	 * @param eventAttribute to update
	 */
	public void updateEventattribute(Eventattribute eventAttribute) {
		eventattributeDAO.updateEventattribute(eventAttribute);
	}
	
	/**
	 * Removes an eventattribute
	 * 
	 * @param id
	 */
	public void removeEventattribute(Long id) {
		eventattributeDAO.removeEventattribute(id);
	}
	
	// ========== Correlated Events stuff ==================
	
	/**
	 * Retrieves the correlated events
	 * 
	 * @return List with correlated events
	 */
	public List getCorrelatedevents() {
		log.debug("### EventPersistenceService.getCorrelatedevents()");
		return correlatedEventDAO.getCorrelatedevents();
	}
	
	// ========== Attribute getters and setters  ===========

	/**
	 * @return Returns the eventattributeDAO.
	 */
	public EventattributeDAO getEventattributeDAO() {
		return eventattributeDAO;
	}

	/**
	 * @param eventattributeDAO The eventattributeDAO to set.
	 */
	public void setEventattributeDAO(EventattributeDAO eventattributeDAO) {
		this.eventattributeDAO = eventattributeDAO;
	}

	/**
	 * @return Returns the eventDAO.
	 */
	public EventDAO getEventDAO() {
		return eventDAO;
	}

	/**
	 * @param eventDAO The eventDAO to set.
	 */
	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	/**
	 * @return Returns the eventtypeDAO.
	 */
	public EventtypeDAO getEventtypeDAO() {
		return eventtypeDAO;
	}

	/**
	 * @param eventtypeDAO The eventtypeDAO to set.
	 */
	public void setEventtypeDAO(EventtypeDAO eventtypeDAO) {
		this.eventtypeDAO = eventtypeDAO;
	}

	/**
	 * @return Returns the rwtimeDAO.
	 */
	public RwtimeDAO getRwtimeDAO() {
		return rwtimeDAO;
	}

	/**
	 * @param rwtimeDAO The rwtimeDAO to set.
	 */
	public void setRwtimeDAO(RwtimeDAO rwtimeDAO) {
		this.rwtimeDAO = rwtimeDAO;
	}

	/**
	 * @return Returns the txtimeDAO.
	 */
	public TxtimeDAO getTxtimeDAO() {
		return txtimeDAO;
	}

	/**
	 * @param txtimeDAO The txtimeDAO to set.
	 */
	public void setTxtimeDAO(TxtimeDAO txtimeDAO) {
		this.txtimeDAO = txtimeDAO;
	}

	/**
	 * @return Returns the correlatedEventDAO.
	 */
	public CorrelatedeventDAO getCorrelatedEventDAO() {
		return correlatedEventDAO;
	}

	/**
	 * @param correlatedEventDAO The correlatedEventDAO to set.
	 */
	public void setCorrelatedEventDAO(CorrelatedeventDAO correlatedEventDAO) {
		this.correlatedEventDAO = correlatedEventDAO;
	}
	
	
	
}