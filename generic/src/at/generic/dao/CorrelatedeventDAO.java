package at.generic.dao;

import java.util.List;

import at.generic.model.Correlatedevent;

/**
 * @author szabolcs
 * @version $Id: CorrelatedeventDAO.java,v 1.1 2005/12/09 16:24:33 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
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