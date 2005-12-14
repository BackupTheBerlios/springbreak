package at.generic.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.model.Correlatedevent;
import at.generic.service.EventSourceManager;

/**
 * @author szabolcs
 * @version $Id: EventSourceManagerImpl.java,v 1.1 2005/12/14 09:58:12 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Implementation of the Events Data Manager
 * 
 */
public class EventSourceManagerImpl implements EventSourceManager {
	private static Log log = LogFactory.getLog(EventSourceManagerImpl.class);
	private CorrelatedeventDAO correlatedEventDAO;
	
	/**
	 * Returns all CorrelatedEvents
	 * 
	 * @return List with all correlated events
	 */
	public List getAllCorrelatedEvents() {
		return correlatedEventDAO.getCorrelatedevents();
	}
	
	/**
	 * Returns the DateTime for a given correlatedEvent
	 * 
	 * @param correlatedEvent
	 * @return String with DateTime 
	 */
	public String getDateTimeForEvent(Correlatedevent correlatedEvent) {
		return null;
	}

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
	
	
}