package at.generic.service;

import java.util.List;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.dao.CorrelatedsetDAO;
import at.generic.dao.EventDAO;
import at.generic.dao.EventattributeDAO;
import at.generic.dao.EventtypeDAO;
import at.generic.dao.RwtimeDAO;
import at.generic.dao.TxtimeDAO;
import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventattribute;
import at.generic.model.Correlatedevent;
import at.generic.model.Correlationset;

/**
 * @author szabolcs
 * @version $Id: CorrelatingEventsPersistenceService.java,v 1.1 2006/02/27 14:59:03 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Facade for correlating events persitence operations
 */
public interface CorrelatingEventsPersistenceService {

	// ========== correlated events stuff  ===========
	/**
	 * @return List with Correlated Events
	 */
	public List getCorrelatedevents(); 
	
	/**
	 * @return List with Correlated Events using pagination
	 */
	public List getCorrelatedeventsByPage(int pageNumber, int pageSize); 
	
	/**
	 * @param id Correlated events id
	 * @return Correlated event
	 */
	public Correlatedevent getCorrelatedevent(Integer id);
	
	/**
	 * @param correlatedEvent Correlatedevent to save
	 */
	public void saveCorrelatedevent(Correlatedevent correlatedEvent); 
	
	/**
	 * @param correlatedEvent Correlatedevent to update
	 */
	public void updateCorrelatedevent(Correlatedevent correlatedEvent); 
	
	/**
	 * @param id Correlated events id to remove
	 */
	public void removeCorrelatedevent(Integer id);
	
	//	 ========== correlated set stuff  ===========
	
	/**
	 * @return List with Correlated Sets
	 */
	public List getCorrelatedset(); 
	
	/**
	 * @param id Correlated sets id
	 * @return Correlatedset
	 */
	public Correlationset getCorrelatedset(Integer id);
	
	/**
	 * @param correlatedSet Correlatedevent to save
	 */
	public void saveCorrelatedset(Correlationset correlatedSet); 
	
	/**
	 * @param correlatedEvent Correlatedevent to update
	 */
	public void updateCorrelatedset(Correlationset correlatedSet); 
	
	/**
	 * @param id Correlated sets id to remove
	 */
	public void removeCorrelatedset(Integer id);
	
	/**
	 * Returns a list with correlatedsets according to the given guid
	 * 
	 * @param guid
	 * @return List with correlatedsets or null if nothing found
	 */
	public List getCorrelatedsetByGuid (String guid);
	
	//	 ========== Getters and Setters  ===========
	
	/**
	 * @return Returns the correlatedEventDAO.
	 */
	public CorrelatedeventDAO getCorrelatedEventDAO();
	/**
	 * @param correlatedEventDAO The correlatedEventDAO to set.
	 */
	public void setCorrelatedEventDAO(CorrelatedeventDAO correlatedEventDAO);

	/**
	 * @return Returns the correlatedSetDAO.
	 */
	public CorrelatedsetDAO getCorrelatedSetDAO();

	/**
	 * @param correlatedSetDAO The correlatedSetDAO to set.
	 */
	public void setCorrelatedSetDAO(CorrelatedsetDAO correlatedSetDAO);
}