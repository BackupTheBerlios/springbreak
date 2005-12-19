package at.generic.service.impl;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.model.Correlatedevent;
import at.generic.service.EventSourceManager;
import at.generic.xmlhandlers.EventXmlHandler;

/**
 * @author szabolcs
 * @version $Id: EventSourceManagerImpl.java,v 1.3 2005/12/19 23:17:25 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.3 $
 * 
 * Implementation of the Events Data Manager
 * 
 */
public class EventSourceManagerImpl implements EventSourceManager {
	private static Log log = LogFactory.getLog(EventSourceManagerImpl.class);
	private CorrelatedeventDAO correlatedEventDAO;
	private int numberOfEvents;
	private boolean populatedNumberOfEvents; 
	private int pageSize;
	
	/**
	 * Returns all CorrelatedEvents
	 * 
	 * @return List with all correlated events
	 */
	public List getAllCorrelatedEvents() {
		return correlatedEventDAO.getCorrelatedevents();
	}
	
	/**
	 * Returns a Vector with all parsed xml events and the raw data attached.
	 * @return Vector with all events
	 */
	public Vector getAllCorrelatedEventModels() {
		List correlatedEventList = correlatedEventDAO.getCorrelatedevents();
		
		// retriev number of items
		/*Vector cem = this.createEventModel(correlatedEventList);
		this.setNumberOfEvents(cem.size());
		this.setPopulatedNumberOfEvents(true);
		
		return cem;*/
		return null;
	}
	
	/**
	 * Returns a subset of CorrelatedEvents with Raw XML Data
	 * according to the selected page 
	 * 
	 * @return List with all correlated events
	 */
	public Vector getCorrelatedEventsByPage(int pageNr) {
		List correlatedEventList = correlatedEventDAO.getCorrelatedeventsByPage(pageNr, this.pageSize);
		
		//return this.createEventModel(correlatedEventList);
		return null;
	}
	
	/**
	 * Determines which XML Type the given Event has and then it is parsed and 
	 * an Event Model is generated. The generated EventModels are packed into
	 * a Vector.
	 * 
	 * @return List with correlated events
	 */
	/*private Vector createEventModel(List correlatedEventList) {
		EventXmlHandler eventXmlHandler = new EventXmlHandler();
		Vector parsedEventModels = new Vector(); 
		
		Iterator i = correlatedEventList.iterator();
		while (i.hasNext()) {
			Correlatedevent correlatedEvent = (Correlatedevent) i.next();
			
			// determine the xml type e.g.: OrderReceived,...
			StringBuffer stringBuffer = new StringBuffer(correlatedEvent.getEventXml());
			ByteArrayInputStream xmlStream = new ByteArrayInputStream(stringBuffer.toString().getBytes());
			
			try {
				SAXReader reader = new SAXReader();
		        Document document = reader.read(xmlStream);
		        log.debug("### xml root element: " + document.getRootElement().getName());
		        log.debug("### correlatedEvent.getId(): " + correlatedEvent.getId());
		        
				// Create EventModel and Parse xml according to its type
		        Object parsedEvent = eventXmlHandler.handleEvent(correlatedEvent, document.getRootElement().getName());
		        parsedEventModels.add(parsedEvent);
		        
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		
		return parsedEventModels;
	}*/

	/**
	 * Getter for correlatedEventDAO
	 * @return CorrelatedeventDAO
	 */
	public CorrelatedeventDAO getCorrelatedEventDAO() {
		return correlatedEventDAO;
	}
	
	/**
	 * Setter for correlatedEventDAO
	 */
	public void setCorrelatedEventDAO(CorrelatedeventDAO correlatedEventDAO) {
		this.correlatedEventDAO = correlatedEventDAO;
	}

	/**
	 * @return Returns the numberOfEvents.
	 */
	public int getNumberOfEvents() {
		return numberOfEvents;
	}

	/**
	 * @param numberOfEvents The numberOfEvents to set.
	 */
	public void setNumberOfEvents(int numberOfEvents) {
		this.numberOfEvents = numberOfEvents;
	}

	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return Returns the populatedNumberOfEvents.
	 */
	public boolean getPopulatedNumberOfEvents() {
		return populatedNumberOfEvents;
	}

	/**
	 * @param populatedNumberOfEvents The populatedNumberOfEvents to set.
	 */
	public void setPopulatedNumberOfEvents(boolean populatedNumberOfEvents) {
		this.populatedNumberOfEvents = populatedNumberOfEvents;
	}

	
	
	
	
}