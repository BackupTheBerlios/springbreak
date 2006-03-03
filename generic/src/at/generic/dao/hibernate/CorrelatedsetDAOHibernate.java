package at.generic.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import at.generic.dao.CorrelatedsetDAO;
import at.generic.model.Correlatedevent;
import at.generic.model.Correlationset;

/**
 * @author szabolcs
 * @version $Id: CorrelatedsetDAOHibernate.java,v 1.4 2006/03/03 15:24:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.4 $
 * 
 * DAO interface Hibernate implementation
 * 
 */
public class CorrelatedsetDAOHibernate extends HibernateDaoSupport implements CorrelatedsetDAO { 
	private Log log = LogFactory.getLog(CorrelatedsetDAOHibernate.class); 
	
	/**
	 * Return a List of Correlatedsets ordered by its id
	 * 
	 * @return List with Correlated Sets
	 */
	public List getCorrelatedset() {
		return getHibernateTemplate().find("from Correlationset order by correlationsetguid, eventid"); 
	}
	
	/**
	 * Gets an Correlatedset object by it's id
	 * 
	 * @param id Correlated sets id
	 * @return Correlated set
	 */
	public Correlationset getCorrelatedset(Integer id) {
		Correlationset correlationSet = (Correlationset) getHibernateTemplate().load(Correlationset.class, id); 
		if (correlationSet == null) { 
			throw new ObjectRetrievalFailureException(Correlationset.class, id); 
		} return correlationSet;
	}
	
	/**
	 * Saves a Correlatedset
	 * 
	 * @param correlatedSet Correlatedset to save
	 */
	public void saveCorrelatedset(Correlationset correlatedSet) {
		getHibernateTemplate().save(correlatedSet);
	}
	
	/**
	 * Updates a Correlatedset
	 * 
	 * @param correlatedSet Correlatedset to update
	 */
	public void updateCorrelatedset(Correlationset correlatedSet) {
		getHibernateTemplate().update(correlatedSet);
	}
	
	/**
	 * Removes a Correlatedset according to it's id
	 * 
	 * @param id Correlated sets id to remove
	 */
	public void removeCorrelatedset(Integer id) {
		Object correlatedSet = getHibernateTemplate().load(Correlationset.class, id); 
		getHibernateTemplate().delete(correlatedSet); 
	}
	
	/**
	 * Returns a list with correlatedsets according to the given guid
	 * 
	 * @param guid
	 * @return List with correlatedsets or null if nothing found
	 */
	public List getCorrelatedsetByGuid (String guid) {
		return getHibernateTemplate().find("from Correlationset where correlationsetguid = '" + guid.trim() + "' order by correlationsetguid");
	}
}