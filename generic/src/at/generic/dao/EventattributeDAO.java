package at.generic.dao;

import java.util.List;

import at.generic.eventmodel.Eventattribute;

/**
 * @author szabolcs
 * @version $Id: EventattributeDAO.java,v 1.1 2006/02/27 14:57:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface for Eventattribute
 * 
 */
public interface EventattributeDAO extends DAO 
{ 
	/**
	 * @return List with Eventattributes
	 */
	public List getEventattributes();
	
	/**
	 * @return List with Eventattributes using pagination
	 */
	public List getEventattributesByPage(int pageNumber, int pageSize); 
	
	/**
	 * Retrieves Eventattribute by its id
	 * 
	 * @param id 
	 * @return Eventattribute
	 */
	public Eventattribute getEventattribute(Long id);
	
	/**
	 * Retrieves Eventattributes by the event id
	 * 
	 * @param id 
	 * @return Eventattribute
	 */
	public List getEventattributesForEvent(Long id);
	
	/**
	 * Saves an eventattribute
	 * 
	 * @param eventAttribute to save
	 */
	public void saveOrUpdateEventattribute(Eventattribute eventAttribute); 
	
	/**
	 * Updates an eventattribute
	 * 
	 * @param eventAttribute to update
	 */
	public void updateEventattribute(Eventattribute eventAttribute); 
	
	/**
	 * Removes an eventattribute
	 * 
	 * @param id
	 */
	public void removeEventattribute(Long id);
}