package at.generic.dao;

import java.util.List;

import at.generic.eventmodel.OrderReceivedEvent;
import at.generic.model.Correlationset;

/**
 * @author szabolcs
 * @version $Id: OrderReceivedEventDAO.java,v 1.1 2005/12/19 23:16:54 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface for OrderReceivedEvent
 * 
 */
public interface OrderReceivedEventDAO extends DAO 
{ 
	/**
	 * @return List with OrderReceivedEvents Sets
	 */
	public List getAll(); 
	
	/**
	 * @param id Order Receivedevent id
	 * @return Correlatedset
	 */
	public OrderReceivedEvent getById(Integer id);
	
	/**
	 * @param correlatedSet Correlatedevent to save
	 */
	public void save(OrderReceivedEvent orderReceivedEvent); 
	
	/**
	 * @param correlatedEvent Correlatedevent to update
	 */
	public void update(OrderReceivedEvent orderReceivedEvent); 
	
	/**
	 * @param id Correlated sets id to remove
	 */
	public void remove(Integer id);
}