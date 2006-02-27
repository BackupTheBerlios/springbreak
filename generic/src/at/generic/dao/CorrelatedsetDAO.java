package at.generic.dao;

import java.util.List;

import at.generic.model.Correlationset;

/**
 * @author szabolcs
 * @version $Id: CorrelatedsetDAO.java,v 1.2 2006/02/27 14:57:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
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
}