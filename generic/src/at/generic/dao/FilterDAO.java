package at.generic.dao;

import java.util.List;

import at.generic.eventmodel.Filter;

/**
 * @author szabolcs
 * @version $Id: FilterDAO.java,v 1.1 2006/03/16 23:35:50 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface for Filter
 * 
 */
public interface FilterDAO extends DAO 
{ 
	/**
	 * @return List with Filters
	 */
	public List getFilters(); 
	
	/**
	 * @param id 
	 * @return Filter event
	 */
	public Filter getFilter(Integer id);
	
	/**
	 * Save or update filter
	 * 
	 * @param filter Filter to save
	 */
	public void saveOrUpdateFilter(Filter filter); 
	
	/**
	 * Update Filter
	 * 
	 * @param filter Filter to update
	 */
	public void updateFilter(Filter filter); 
	
	/**
	 * Remove Filter
	 * 
	 * @param id filter id to remove
	 */
	public void removeFilter(Integer id);
	
	/**
	 * Returns a List of Filters for a Profile ordered by ranktype and name
	 * 
	 * @param pid Id of Profile
	 * @return List with Filter
	 */
	public List getAllFiltersByProfile(Integer pid);
}