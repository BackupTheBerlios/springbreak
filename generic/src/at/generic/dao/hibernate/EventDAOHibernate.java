package at.generic.dao.hibernate;

import java.util.Iterator;
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
 * @version $Id: EventDAOHibernate.java,v 1.3 2006/04/18 22:39:02 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.3 $
 * 
 * DAO interface Hibernate implementation
 * 
 */
public class EventDAOHibernate extends HibernateDaoSupport implements EventDAO { 
	private Log log = LogFactory.getLog(EventDAOHibernate.class); 
	
	/**
	 * Returns the size of the relation
	 * see http://www.hibernate.org/hib_docs/reference/en/html/queryhql.html 11.13. Tips & Tricks
	 * 
	 * @return count
	 */
	public int getCount() {
		//return ( (Integer) getHibernateTemplate().iterate("select count(*) from Event").next() ).intValue();
		List eventCount = getHibernateTemplate().find("select count(*) from Event");
		Iterator it = eventCount.iterator(); 
		
		while(it.hasNext()) {
			return new Integer(it.next().toString()).intValue();
		}
		
		return -1;
	}
	
	/**
	 * @return List with Events
	 */
	public List getEvents() {
		return getHibernateTemplate().find("from Event");
	}
	
	/**
	 * Retrieves Events by a list of event ids
	 * 
	 * @param ids
	 * @return Eventattribute
	 */
	public List getEvents(List ids) {
		Iterator it =ids.iterator();
		String idStr = new String();
		boolean first = true;
		while(it.hasNext()) {
			if (first == true) {
				idStr = "eventid = " + (Long) it.next();
				first = false;
			} else {
				idStr = idStr + " OR eventid = " + (Long) it.next();
			}
				
		}
		
		log.debug("### idStr: " + idStr);
		if (idStr != null && !idStr.equals(""))
			return getHibernateTemplate().find("from Event where " + idStr);
		else 
			return null;
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