package at.generic.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import at.generic.dao.OrderReceivedEventDAO;
import at.generic.eventmodel.OrderReceivedEvent;

/**
 * @author szabolcs
 * @version $Id: OrderReceivedEventDAOHibernate.java,v 1.1 2005/12/19 23:16:54 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface Hibernate implementation
 * 
 */
public class OrderReceivedEventDAOHibernate extends HibernateDaoSupport implements OrderReceivedEventDAO  {
	private Log log = LogFactory.getLog(CorrelatedsetDAOHibernate.class); 
	
	/**
	 * Return all Elements ordered by its id
	 * 
	 * @return List with OrderReceivedEvent Sets
	 */
	public List getAll() {
		return getHibernateTemplate().find("from OrderReceivedEvent order by id"); 
	}
	
	/**
	 * Gets an OrderReceivedEvent object by it's id
	 * 
	 * @param id OrderReceivedEvent sets id
	 * @return OrderReceivedEvent set
	 */
	public OrderReceivedEvent getById(Integer id) {
		OrderReceivedEvent orderReceivedEvent = (OrderReceivedEvent) getHibernateTemplate().load(OrderReceivedEvent.class, id); 
		if (orderReceivedEvent == null) { 
			throw new ObjectRetrievalFailureException(OrderReceivedEvent.class, id); 
		} return orderReceivedEvent;
	}
	
	/**
	 * Saves a OrderReceivedEvent
	 * 
	 * @param orderReceivedEvent OrderReceivedEvent to save
	 */
	public void save(OrderReceivedEvent orderReceivedEvent) {
		if (this.getById(new Integer((int)orderReceivedEvent.getId())) == null)
			getHibernateTemplate().save(orderReceivedEvent);
		else 
			this.update(orderReceivedEvent);
	}
	
	/**
	 * Updates a OrderReceivedEvent
	 * 
	 * @param orderReceivedEvent OrderReceivedEvent to update
	 */
	public void update(OrderReceivedEvent orderReceivedEvent) {
		getHibernateTemplate().update(orderReceivedEvent);
	}
	
	/**
	 * Removes a OrderReceivedEvent according to it's id
	 * 
	 * @param id OrderReceivedEvent sets id to remove
	 */
	public void remove(Integer id) {
		Object orderReceivedEvent = getHibernateTemplate().load(OrderReceivedEvent.class, id); 
		getHibernateTemplate().delete(orderReceivedEvent); 
	}
}