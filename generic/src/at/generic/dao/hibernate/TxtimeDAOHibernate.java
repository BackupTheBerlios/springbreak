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

import at.generic.dao.TxtimeDAO;
import at.generic.eventmodel.Rwtime;
import at.generic.eventmodel.Txtime;

/**
 * @author szabolcs
 * @version $Id: TxtimeDAOHibernate.java,v 1.1 2006/02/27 14:57:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface for Txtime
 * 
 */
public class TxtimeDAOHibernate extends HibernateDaoSupport implements TxtimeDAO { 
	private Log log = LogFactory.getLog(TxtimeDAOHibernate.class); 
	/**
	 * @return List with Txtimes
	 */
	public List getTxtimes() {
		return getHibernateTemplate().find("from Txtime");
	}
	
	/**
	 * @return List with Txtimes using pagination
	 */
	public List getTxtimesByPage(final int pageNumber, final int pageSize) {
		HibernateTemplate ht = getHibernateTemplate();
		List rwtimes = ht.executeFind(new HibernateCallback() {
		
			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.createQuery("from Txtime order by rwtimeid");
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
	 * @return Txtime txtime
	 */
	public Txtime getTxtime(Integer id) {
		return(Txtime) getHibernateTemplate().get(Txtime.class, id);
	}
	
	/**
	 * Retrieves Id
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return Rwtime
	 */
	public Txtime getTxTimeByDates(int day, int month, int year) {
		List time = getHibernateTemplate().find(
				"from Txtime where txday = " + day + " and " +
				"txmonth = " + month + " and " +
				"txyear = " + year 
				);
		
		if (time.size() > 0) {
			Iterator i = time.iterator();
			while (i.hasNext()) {
				return (Txtime) i.next();
			}
		}
		
		return null;
	}
	
	/**
	 * @param txtime Txtime to save
	 */
	public void saveOrUpdateTxtime(Txtime txtime) {
		if (this.getTxTimeByDates(txtime.getTxday().intValue(), txtime.getTxmonth().intValue(), txtime.getTxyear().intValue()) == null)
			getHibernateTemplate().save(txtime);
		else if (txtime.getTxtimeid() != null)
			this.updateTxtime(txtime);
	}
	
	/**
	 * @param Txtime Txtime to update
	 */
	public void updateTxtime(Txtime txtime) {
		if (txtime.getTxtimeid() != null)
			getHibernateTemplate().update(txtime);
	}
	
	/**
	 * @param id Txtime id to remove
	 */
	public void removeTxtime(Integer id) {
		Txtime txtime = (Txtime)getHibernateTemplate().get(Txtime.class, id); 
		getHibernateTemplate().delete(txtime); 
	}
}