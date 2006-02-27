package at.generic.dao;

import java.util.List;

import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventtype;

/**
 * @author szabolcs
 * @version $Id: EventtypeDAO.java,v 1.1 2006/02/27 14:57:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface for Eventtype
 * 
 */
public interface EventtypeDAO extends DAO 
{ 
	/**
	 * @return List with all eventtypes
	 */
	public List getEventtypes(); 
	
	/**
	 * @return List with eventtypes using pagination
	 */
	public List getEventtypesByPage(int pageNumber, int pageSize); 
	
	/**
	 * @param id eventtypes id
	 * @return Eventtype eventtype
	 */
	public Eventtype getEventtype(Integer id);
	
	/**
	 * Retrieves eventtype its name
	 * 
	 * @param eventname
	 */
	public Eventtype getEventtypeByName(String eventname);
	
	/**
	 * @param eventtype Eventtype to save
	 */
	public void saveOrUpdateEventtype(Eventtype eventtype); 
	
	/**
	 * @param eventtype Eventtype to update
	 */
	public void updateEventtype(Eventtype eventtype); 
	
	/**
	 * @param id eventtype id to remove
	 */
	public void removeEventtype(Integer id);
}