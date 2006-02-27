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

import at.generic.dao.EventattributeDAO;
import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventattribute;

/**
 * @author szabolcs
 * @version $Id: EventattributeDAOHibernate.java,v 1.1 2006/02/27 14:57:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface for Eventattribute
 * 
 */
public class EventattributeDAOHibernate extends HibernateDaoSupport implements EventattributeDAO { 
	private Log log = LogFactory.getLog(EventattributeDAOHibernate.class); 
	
	/**
	 * @return List with Eventattributes
	 */
	public List getEventattributes() {
		return getHibernateTemplate().find("from Eventattribute");
	}
	
	/**
	 * @return List with Eventattributes using pagination
	 */
	public List getEventattributesByPage(final int pageNumber, final int pageSize) {
		HibernateTemplate ht = getHibernateTemplate();
		List eventAttributes = ht.executeFind(new HibernateCallback() {
		
			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.createQuery("from Eventattribute order by attributeid");
				q.setMaxResults(pageSize);
				q.setFirstResult(pageSize * pageNumber - pageSize);
				List eventAttributes = q.list();
				return eventAttributes;
			}
		});
		
		return eventAttributes;
	}
	
	/**
	 * Retrieves Eventattributes by the event id
	 * 
	 * @param id 
	 * @return Eventattribute
	 */
	public List getEventattributesForEvent(Long id) {
		return getHibernateTemplate().find("from Eventattribute where eventid = " + id);
	}
	
	/**
	 * Retrieves Eventattribute by its id
	 * 
	 * @param id 
	 * @return Eventattribute
	 */
	public Eventattribute getEventattribute(Long id) {
		return (Eventattribute) getHibernateTemplate().get(Eventattribute.class, id);
	}
	
	/**
	 * Saves an eventattribute
	 * 
	 * @param eventAttribute to save
	 */
	public void saveOrUpdateEventattribute(Eventattribute eventAttrib) {
		List time = getHibernateTemplate().find(
				"from Eventattribute where attributename = '" + eventAttrib.getAttributename() + "' and " +
				"value = '" + eventAttrib.getValue() + "' and " +  
				"xmluri = '" + eventAttrib.getXmluri() + "' and " +
				"eventid = " + eventAttrib.getEventid()
			);
		if (time.size() == 0) {
			getHibernateTemplate().save(eventAttrib);
		} 
	}
	
	/**
	 * Updates an eventattribute
	 * 
	 * @param eventAttribute to update
	 */
	public void updateEventattribute(Eventattribute eventAttribute) {
		getHibernateTemplate().update(eventAttribute);
	}
	
	/**
	 * Removes an eventattribute
	 * 
	 * @param id
	 */
	public void removeEventattribute(Long id) {
		Eventattribute eventAttribute = (Eventattribute)getHibernateTemplate().get(Eventattribute.class, id); 
		getHibernateTemplate().delete(eventAttribute); 
	}
}