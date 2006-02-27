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

import at.generic.dao.EventtypeDAO;
import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventtype;

/**
 * @author szabolcs
 * @version $Id: EventtypeDAOHibernate.java,v 1.1 2006/02/27 14:57:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface Hibernate implementation
 * 
 */
public class EventtypeDAOHibernate extends HibernateDaoSupport implements EventtypeDAO { 
	private Log log = LogFactory.getLog(EventtypeDAOHibernate.class);  
 
	/**
	 * @return List with all eventtypes
	 */
	public List getEventtypes() {
		return getHibernateTemplate().find("from Eventtype");
	}
	
	/**
	 * @return List with eventtypes using pagination
	 */
	public List getEventtypesByPage(final int pageNumber, final int pageSize) {
		HibernateTemplate ht = getHibernateTemplate();
		List eventtypes = ht.executeFind(new HibernateCallback() {
		
			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.createQuery("from eventtype order by eventtypeid");
				q.setMaxResults(pageSize);
				q.setFirstResult(pageSize * pageNumber - pageSize);
				List eventtypes = q.list();
				return eventtypes;
			}
		});
		
		log.debug("Size: " + eventtypes.size());
		return eventtypes;
	}
	
	/**
	 * @param id eventtypes id
	 * @return Eventtype eventtype
	 */
	public Eventtype getEventtype(Integer id) {
		return(Eventtype) getHibernateTemplate().get(Eventtype.class, id);
	}
	
	/**
	 * Retrieves eventtype its name
	 * 
	 * @param eventname
	 */
	public Eventtype getEventtypeByName(String eventname) {
		List eventTypeList = getHibernateTemplate().find("from Eventtype where eventname = '" + eventname + "'");
		if (eventTypeList.size() > 0) {
			Iterator i = eventTypeList.iterator();
			while (i.hasNext()) {
				Eventtype et = (Eventtype) i.next();
				return et;
			}
		}
		
		return null;
	}
	
	/**
	 * @param eventtype Eventtype to save
	 */
	public void saveOrUpdateEventtype(Eventtype eventtype) {
		if (this.getEventtypeByName(eventtype.getEventname()) == null)
			getHibernateTemplate().save(eventtype);
		else if (eventtype.getEventtypeid() != null)
			this.updateEventtype(eventtype);
	}
	
	/**
	 * @param eventtype Eventtype to update
	 */
	public void updateEventtype(Eventtype eventtype) {
		if (eventtype.getEventtypeid() != null)
			getHibernateTemplate().update(eventtype);
	}
	
	/**
	 * @param id eventtype id to remove
	 */
	public void removeEventtype(Integer id) {
		Eventtype eventtype = (Eventtype)getHibernateTemplate().get(Eventtype.class, id); 
		getHibernateTemplate().delete(eventtype); 
	}
}