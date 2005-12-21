package at.generic.dao.hibernate;

import java.io.Serializable;
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

import at.generic.dao.GenericServiceDAO;
import at.generic.eventmodel.OrderReceivedEvent;
import at.generic.xmlhandlers.OrderReceivedXmlHandler;

/**
 * @author szabolcs
 * @version $Id: GenericServiceDAOTarget.java,v 1.1 2005/12/21 22:06:10 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Generic DAO Implementation 
 * 
 */
public class GenericServiceDAOTarget extends HibernateDaoSupport implements GenericServiceDAO {
	private static Log log = LogFactory.getLog(GenericServiceDAOTarget.class);
	
	public List getAllObjects(Object obj) {
		//log.debug("### retrieving all objects of type " + obj.getClass().getName());
		return getHibernateTemplate().find("from " + obj.getClass().getName());
	}
	
	public Object getObjectById(Object obj, Serializable id) {
		//log.debug("### retrieving object " + obj.getClass().getName() + " with id " + id.toString());
		Object newObj =	(Object) getHibernateTemplate().get(obj.getClass(), id);
		return newObj;
		
	}
	
	public void save(Object obj, Serializable id) {
		//log.debug("### saving object " + obj.getClass().getName() + " with id " + id.toString());
		if (this.getObjectById(obj, id) == null)
			getHibernateTemplate().save(obj);
		else 
			this.update(obj);
	}
	 
	 public void update(Object obj) {
		 //log.debug("### updating object " + obj.getClass().getName());
		 getHibernateTemplate().update(obj);
	 }
	 
	 public List getObjectsByQuery(String hqlQry) {
		 //log.debug("### query using hqlQuery " + hqlQry);
		 return getHibernateTemplate().find(hqlQry);
	 }
	 
	/**
	 * Returns a List of objects using paging
	 * 
	 * @return List with Correlated Events using pagination
	 */
	public List getObjectsByPage(final Object obj, final int pageNumber, final int pageSize) {
		HibernateTemplate ht = getHibernateTemplate();
		List objList = ht.executeFind(new HibernateCallback() {
		
			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.createQuery("from " + obj.getClass().getName() + " order by id");
				q.setMaxResults(pageSize);
				q.setFirstResult(pageSize * pageNumber - pageSize);
				List objList = q.list();
				return objList;
			}
		});
		
		log.debug("Size: " + objList.size());
		return objList;
	}
}
