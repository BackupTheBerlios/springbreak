package at.generic.dao;

import java.util.List;

import at.generic.model.Correlationset;

/**
 * @author szabolcs
 * @version $Id: CorrelatedsetDAO.java,v 1.5 2006/04/18 22:39:02 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.5 $
 * 
 * DAO interface for Correlatedset
 * 
 */
public interface CorrelatedsetDAO extends DAO 
{ 
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
	
	/**
	 * Returns a list with correlatedsets according to the given eventid
	 * 
	 * @param guid
	 * @return List with correlatedsets or null if nothing found
	 */
	public List getCorrelatedsetByEvent (Long eventid);
	
	/**
	 * Creates a list of all occuring correlationsettypes
	 * @return List with unique correlation types
	 */
	public List getCorrelationsSetTypes ();
	
	/**
	 * Returns a List of Correlatedset objects using paging
	 * 
	 * @return List with Correlated Sets using pagination
	 */
	public List getCorrelatedeventsByPage(final int pageNumber, final int pageSize);
}