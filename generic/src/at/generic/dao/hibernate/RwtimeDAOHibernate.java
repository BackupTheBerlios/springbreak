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

import at.generic.dao.RwtimeDAO;
import at.generic.eventmodel.Eventtype;
import at.generic.eventmodel.Rwtime;

/**
 * @author szabolcs
 * @version $Id: RwtimeDAOHibernate.java,v 1.1 2006/02/27 14:57:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface for Rwtime
 * 
 */
public class RwtimeDAOHibernate extends HibernateDaoSupport implements RwtimeDAO { 
	private Log log = LogFactory.getLog(RwtimeDAOHibernate.class); 
	/**
	 * @return List with Rwtimes
	 */
	public List getRwtimes() {
		return getHibernateTemplate().find("from Rwtime");
	}
	
	/**
	 * @return List with Rwtimes using pagination
	 */
	public List getRwtimesByPage(final int pageNumber, final int pageSize) {
		HibernateTemplate ht = getHibernateTemplate();
		List rwtimes = ht.executeFind(new HibernateCallback() {
		
			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.createQuery("from Rwtime order by rwtimeid");
				q.setMaxResults(pageSize);
				q.setFirstResult(pageSize * pageNumber - pageSize);
				List rwtimes = q.list();
				return rwtimes;
			}
		});
		
		return rwtimes;
	}
	
	/**
	 * @param id rwtime id
	 * @return Rwtime rwtime
	 */
	public Rwtime getRwtime(Integer id) {
		return(Rwtime) getHibernateTemplate().get(Rwtime.class, id);
	}
	
	/**
	 * Retrieves Id
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return Rwtime
	 */
	public Rwtime getRwTimeByDates(int day, int month, int year) {
		List time = getHibernateTemplate().find(
				"from Rwtime where rwday = " + day + " and " +
				"rwmonth = " + month + " and " +
				"rwyear = " + year 
				);
		
		if (time.size() > 0) {
			Iterator i = time.iterator();
			while (i.hasNext()) {
				return (Rwtime) i.next();
			}
		}
		
		return null;
	}
	
	/**
	 * @param rwtime Rwtime to save
	 */
	public void saveOrUpdateRwtime(Rwtime rwTime) {
		if (this.getRwTimeByDates(rwTime.getRwday().intValue(), rwTime.getRwmonth().intValue(), rwTime.getRwyear().intValue()) == null)
			getHibernateTemplate().save(rwTime);
		else if (rwTime.getRwtimeid() != null)
			this.updateRwtime(rwTime);
	}
	
	/**
	 * @param Rwtime Rwtime to update
	 */
	public void updateRwtime(Rwtime rwTime) {
		if (rwTime.getRwtimeid() != null)
			getHibernateTemplate().update(rwTime);
	}
	
	/**
	 * @param id Rwtime id to remove
	 */
	public void removeRwtime(Integer id) {
		Rwtime rwtime = (Rwtime)getHibernateTemplate().get(Rwtime.class, id); 
		getHibernateTemplate().delete(rwtime); 
	}
}