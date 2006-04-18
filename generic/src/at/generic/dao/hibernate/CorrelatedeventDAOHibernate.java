package at.generic.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.model.Correlatedevent;

/**
 * @author szabolcs
 * @version $Id: CorrelatedeventDAOHibernate.java,v 1.6 2006/04/18 22:39:02 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.6 $
 * 
 * DAO interface Hibernate implementation
 * 
 */
public class CorrelatedeventDAOHibernate extends HibernateDaoSupport implements CorrelatedeventDAO { 
	private Log log = LogFactory.getLog(CorrelatedeventDAOHibernate.class); 
	
	/**
	 * Returns the size of the relation
	 * see http://www.hibernate.org/hib_docs/reference/en/html/queryhql.html 11.13. Tips & Tricks
	 * 
	 * @return count
	 */
	public int getCount() {
		//return ( (Integer) getHibernateTemplate().iterate("select count(*) from Event").next() ).intValue();
		List eventCount = getHibernateTemplate().find("select count(*) from Correlatedevent");
		Iterator it = eventCount.iterator(); 
		
		while(it.hasNext()) {
			return new Integer(it.next().toString()).intValue();
		}
		
		return -1;
	}
	
	
	
	/**
	 * Return a List of Correlatedevents ordered by its id
	 * 
	 * @return List with Correlated Events
	 */
	public List getCorrelatedevents() {
		log.debug("### getCorrelatedevents()");
		List corrEvents = getHibernateTemplate().find("from Correlatedevent order by id");
		log.debug("### corrEvents.size(): " + corrEvents.size());
		return  corrEvents;
	}
	
	/**
	 * Gets an Correlatedevent object by it's id
	 * 
	 * @param id Correlated events id
	 * @return Correlated event
	 */
	public Correlatedevent getCorrelatedevent(Integer id) {
	    //getHibernateTemplate().setAllowCreate(false);
		Correlatedevent correlatedEvent = (Correlatedevent) getHibernateTemplate().load(Correlatedevent.class, id); 
		if (correlatedEvent == null) { 
			throw new ObjectRetrievalFailureException(Correlatedevent.class, id); 
		} return correlatedEvent;
	}
	
	/**
	 * Returns a List of Correlatedevent objects using paging
	 * 
	 * @return List with Correlated Events using pagination
	 */
	public List getCorrelatedeventsByPage(final int pageNumber, final int pageSize) {
		HibernateTemplate ht = getHibernateTemplate();
		List correlatedEvents = ht.executeFind(new HibernateCallback() {
		
			public Object doInHibernate(Session session) throws HibernateException {
				//Query q = session.createQuery("from Correlatedevent order by id");
				Query q = session.createQuery("from Correlatedevent order by id");
				q.setMaxResults(pageSize);
				q.setFirstResult(pageSize * pageNumber - pageSize);
				List correlatedEvents = q.list();
				return correlatedEvents;
			}
		});
		
		log.debug("Size: " + correlatedEvents.size());
		return correlatedEvents;
	}
	
	/**
	 * Saves a Correlatedevent
	 * 
	 * @param correlatedEvent Correlatedevent to save
	 */
	public void saveCorrelatedevent(Correlatedevent correlatedEvent) {
		getHibernateTemplate().save(correlatedEvent);
	}
	
	/**
	 * Updates a Correlatedevent
	 * 
	 * @param correlatedEvent Correlatedevent to update
	 */
	public void updateCorrelatedevent(Correlatedevent correlatedEvent) {
		getHibernateTemplate().update(correlatedEvent);
	}
	
	/**
	 * Removes a Correlatedevent according to it's id
	 * 
	 * @param id Correlated events id to remove
	 */
	public void removeCorrelatedevent(Integer id) {
		Object correlatedEvent = getHibernateTemplate().load(Correlatedevent.class, id); 
		getHibernateTemplate().delete(correlatedEvent); 
	}
}