package at.generic.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.dao.CorrelatedsetDAO;
import at.generic.model.Correlatedevent;
import at.generic.model.Correlationset;
import at.generic.service.CorrelatingEventsPersistenceService;

/**
 * @author szabolcs
 * @version $Id: CorrelatingEventsPersistenceServiceImpl.java,v 1.1 2006/02/27 14:59:03 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Facade for event persitence operations
 */
public class CorrelatingEventsPersistenceServiceImpl implements CorrelatingEventsPersistenceService {
	private static Log log = LogFactory.getLog(CorrelatingEventsPersistenceServiceImpl.class);
	
	private CorrelatedeventDAO correlatedEventDAO;
	private CorrelatedsetDAO correlatedSetDAO;
	
	
	//	 ========== correlated events stuff  ===========
	
	/**
	 * Return a List of Correlatedevents ordered by its id
	 * 
	 * @return List with Correlated Events
	 */
	public List getCorrelatedevents() {
		return correlatedEventDAO.getCorrelatedevents(); 
	}
	
	/**
	 * Gets an Correlatedevent object by it's id
	 * 
	 * @param id Correlated events id
	 * @return Correlated event
	 */
	public Correlatedevent getCorrelatedevent(Integer id) {
		return correlatedEventDAO.getCorrelatedevent(id);
	}
	
	/**
	 * Returns a List of Correlatedevent objects using paging
	 * 
	 * @return List with Correlated Events using pagination
	 */
	public List getCorrelatedeventsByPage(final int pageNumber, final int pageSize) {
		return correlatedEventDAO.getCorrelatedeventsByPage(pageNumber, pageSize);
	}
	
	/**
	 * Saves a Correlatedevent
	 * 
	 * @param correlatedEvent Correlatedevent to save
	 */
	public void saveCorrelatedevent(Correlatedevent correlatedEvent) {
		correlatedEventDAO.saveCorrelatedevent(correlatedEvent);
	}
	
	/**
	 * Updates a Correlatedevent
	 * 
	 * @param correlatedEvent Correlatedevent to update
	 */
	public void updateCorrelatedevent(Correlatedevent correlatedEvent) {
		correlatedEventDAO.updateCorrelatedevent(correlatedEvent);
	}
	
	/**
	 * Removes a Correlatedevent according to it's id
	 * 
	 * @param id Correlated events id to remove
	 */
	public void removeCorrelatedevent(Integer id) {
		correlatedEventDAO.removeCorrelatedevent(id);
	}
	
	//	 ========== correlated set stuff  ===========
	
	/**
	 * Return a List of Correlatedsets ordered by its id
	 * 
	 * @return List with Correlated Sets
	 */
	public List getCorrelatedset() {
		return correlatedSetDAO.getCorrelatedset();
	}
	
	/**
	 * Gets an Correlatedset object by it's id
	 * 
	 * @param id Correlated sets id
	 * @return Correlated set
	 */
	public Correlationset getCorrelatedset(Integer id) {
		return correlatedSetDAO.getCorrelatedset(id);
	}
	
	/**
	 * Saves a Correlatedset
	 * 
	 * @param correlatedSet Correlatedset to save
	 */
	public void saveCorrelatedset(Correlationset correlatedSet) {
		correlatedSetDAO.saveCorrelatedset(correlatedSet);
	}
	
	/**
	 * Updates a Correlatedset
	 * 
	 * @param correlatedSet Correlatedset to update
	 */
	public void updateCorrelatedset(Correlationset correlatedSet) {
		correlatedSetDAO.updateCorrelatedset(correlatedSet);
	}
	
	/**
	 * Removes a Correlatedset according to it's id
	 * 
	 * @param id Correlated sets id to remove
	 */
	public void removeCorrelatedset(Integer id) {
		correlatedSetDAO.removeCorrelatedset(id);
	}
	
	/**
	 * Returns a list with correlatedsets according to the given guid
	 * 
	 * @param guid
	 * @return List with correlatedsets or null if nothing found
	 */
	public List getCorrelatedsetByGuid (String guid) {
		return correlatedSetDAO.getCorrelatedsetByGuid(guid.trim());
	}

	//	 ========== Getters and Setters  ===========
	
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

	/**
	 * @return Returns the correlatedSetDAO.
	 */
	public CorrelatedsetDAO getCorrelatedSetDAO() {
		return correlatedSetDAO;
	}

	/**
	 * @param correlatedSetDAO The correlatedSetDAO to set.
	 */
	public void setCorrelatedSetDAO(CorrelatedsetDAO correlatedSetDAO) {
		this.correlatedSetDAO = correlatedSetDAO;
	}
}