package at.generic.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import at.generic.dao.FilterDAO;
import at.generic.eventmodel.Filter;

/**
 * @author szabolcs
 * @version $Id: FilterDAOHibernate.java,v 1.1 2006/03/16 23:35:50 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface Hibernate implementation
 * 
 */
public class FilterDAOHibernate extends HibernateDaoSupport implements FilterDAO { 
	private Log log = LogFactory.getLog(EventDAOHibernate.class); 
	
	/**
	 * @return List with Filters
	 */
	public List getFilters() {
		return getHibernateTemplate().find("from Filter");
	}
	
	/**
	 * @param id 
	 * @return Filter event
	 */
	public Filter getFilter(Integer id) {
		return(Filter) getHibernateTemplate().get(Filter.class, id);
	}
	
	
	/**
	 * Save or update filter
	 * 
	 * @param filter Filter to save
	 */
	public void saveOrUpdateFilter(Filter filter) {
		if (filter.getFid() == null) 
			getHibernateTemplate().save(filter);
		else if (this.getFilter(filter.getFid()) == null)
			getHibernateTemplate().save(filter);
		else 
			this.updateFilter(filter);
	}
	
	/**
	 * Update Filter
	 * 
	 * @param filter Filter to update
	 */
	public void updateFilter(Filter filter) {
		getHibernateTemplate().update(filter);
	}
	
	/**
	 * Remove Filter
	 * 
	 * @param id filter id to remove
	 */
	public void removeFilter(Integer id) {
		Filter filter = (Filter)getHibernateTemplate().get(Filter.class, id); 
		getHibernateTemplate().delete(filter); 
	}
	
	/**
	 * Returns a List of Filters for a Profile ordered by ranktype and name
	 * 
	 * @param pid Id of Profile
	 * @return List with Filter
	 */
	public List getAllFiltersByProfile(Integer pid) {
		return getHibernateTemplate().find("from Filter where pid = " + pid + " order by ranktype, name");
	}
}