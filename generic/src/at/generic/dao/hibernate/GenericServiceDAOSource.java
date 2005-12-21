package at.generic.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import at.generic.dao.GenericServiceDAO;
import at.generic.eventmodel.OrderReceivedEvent;

/**
 * @author szabolcs
 * @version $Id: GenericServiceDAOSource.java,v 1.1 2005/12/21 22:06:10 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Generic DAO Implementation 
 * 
 */
public class GenericServiceDAOSource extends HibernateDaoSupport implements GenericServiceDAO {
	private static Log log = LogFactory.getLog(GenericServiceDAOSource.class);
	
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
}
