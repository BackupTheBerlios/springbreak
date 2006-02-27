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

import at.generic.dao.DbinfoDAO;
import at.generic.eventmodel.Dbinfo;
import at.generic.eventmodel.Event;

/**
 * @author szabolcs
 * @version $Id: DbinfoDAOHibernate.java,v 1.1 2006/02/27 14:57:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface Hibernate implementation
 * 
 */
public class DbinfoDAOHibernate extends HibernateDaoSupport implements DbinfoDAO { 
	private Log log = LogFactory.getLog(DbinfoDAOHibernate.class);
	
	/**
	 * @return List with Dbinfos
	 */
	public List getDbinfos() {
		return getHibernateTemplate().find("from Dbinfo");
	}
	
	/**
	 * @return List with Dbinfos using pagination
	 */
	public List getDbinfosByPage(final int pageNumber, final int pageSize) {
		HibernateTemplate ht = getHibernateTemplate();
		List dbinfos = ht.executeFind(new HibernateCallback() {
		
			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.createQuery("from Dbinfo order by id");
				q.setMaxResults(pageSize);
				q.setFirstResult(pageSize * pageNumber - pageSize);
				List dbinfos = q.list();
				return dbinfos;
			}
		});
		
		return dbinfos;
	}
	
	/**
	 * @param id dbinfo id
	 * @return Dbinfo dbinfo
	 */
	public Dbinfo getDbinfo(Integer id) {
		return(Dbinfo) getHibernateTemplate().get(Dbinfo.class, id);
	}
	
	/**
	 * @param dbinfo dbinfo to save
	 */
	public void saveOrUpdateDbinfo(Dbinfo dbInfo) {
		getHibernateTemplate().save(dbInfo);
	}
	
	/**
	 * @param Dbinfo dbinfo to update
	 */
	public void updateDbinfo(Dbinfo dbInfo) {
		getHibernateTemplate().update(dbInfo);
	}
	
	/**
	 * @param id dbinfo id to remove
	 */
	public void removeDbinfo(Integer id) {
		Dbinfo dbInfo = (Dbinfo)getHibernateTemplate().get(Dbinfo.class, id); 
		getHibernateTemplate().delete(dbInfo); 
	}
}