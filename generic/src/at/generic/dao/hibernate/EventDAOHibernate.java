package at.generic.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import at.generic.dao.EventDAO;
import at.generic.eventmodel.Event;

/**
 * @author szabolcs
 * @version $Id: EventDAOHibernate.java,v 1.1 2006/02/27 14:57:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface Hibernate implementation
 * 
 */
public class EventDAOHibernate extends HibernateDaoSupport implements EventDAO { 
	private Log log = LogFactory.getLog(EventDAOHibernate.class); 
	
	/**
	 * @return List with Events
	 */
	public List getEvents() {
		return getHibernateTemplate().find("from Event");
	}
	
	/**
	 * @return List with Events using pagination
	 */
	public List getEventsByPage(final int pageNumber, final int pageSize) {
		HibernateTemplate ht = getHibernateTemplate();
		List events = ht.executeFind(new HibernateCallback() {
		
			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.createQuery("from event order by eventid");
				q.setMaxResults(pageSize);
				q.setFirstResult(pageSize * pageNumber - pageSize);
				List events = q.list();
				return events;
			}
		});
		
		log.debug("Size: " + events.size());
		return events;
	}
	
	/**
	 * @param id events id
	 * @return Event event
	 */
	public Event getEvent(Long id) {
		return(Event) getHibernateTemplate().get(Event.class, id);
	}
	
	/**
	 * @param event Event to save
	 */
	public void saveOrUpdateEvent(Event event) {
		if (this.getEvent(event.getEventid()) == null)
			getHibernateTemplate().save(event);
		else 
			this.updateEvent(event);
	}
	
	/**
	 * @param Event Event to update
	 */
	public void updateEvent(Event event) {
		getHibernateTemplate().update(event);
	}
	
	/**
	 * @param id events id to remove
	 */
	public void removeEvent(Long id) {
		Event event = (Event)getHibernateTemplate().get(Event.class, id); 
		getHibernateTemplate().delete(event); 
	}
}