package at.generic.dao;

import java.util.List;

import at.generic.model.Correlatedevent;

/**
 * @author szabolcs
 * @version $Id: CorrelatedeventDAO.java,v 1.4 2006/04/27 15:56:15 vecego Exp $
 * $Author: vecego $  
 * $Revision: 1.4 $
 * 
 * DAO interface for Correlatedevent
 * 
 */
public interface CorrelatedeventDAO extends DAO 
{ 
	/**
	 * Returns the size of the relation
	 * see http://www.hibernate.org/hib_docs/reference/en/html/queryhql.html 11.13. Tips & Tricks
	 * 
	 * @return count
	 */
	public int getCount();
	
	/**
	 * @return List with Correlated Events
	 */
	public List getCorrelatedevents(); 
	
	/**
	 * 
	 * @return number of events in Correlated Events
	 */
	public int getCountEvents();
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