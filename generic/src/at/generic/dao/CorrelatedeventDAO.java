package at.generic.dao;

import java.util.List;

import at.generic.model.Correlatedevent;

/**
 * @author szabolcs
 * @version $Id: CorrelatedeventDAO.java,v 1.2 2005/12/14 22:15:14 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * DAO interface for Correlatedevent
 * 
 */
public interface CorrelatedeventDAO extends DAO 
{ 
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
}