package at.generic.service;

import java.util.List;

import at.generic.dao.GenericServiceDAO;
import at.generic.eventmodel.Eventattribute;
import at.generic.eventmodel.Eventtype;
import at.generic.eventmodel.Rwtime;
import at.generic.eventmodel.Txtime;

/**
 * @author szabolcs
 * @version $Id: EventHandling.java,v 1.1 2006/01/31 20:15:15 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Facade for event operations
 * 
 */
public interface EventHandling {

	/**
	 * Stores eventtype 
	 * 
	 * @param eventType
	 */
	public abstract void storeEventtype(Eventtype eventType);
	
	/**
	 * Retrieves eventtype Id by its name
	 * 
	 * @param eventname
	 */
	public Integer getEventtypeIdByName(String eventname);

	/**
	 * @return Returns the genericServiceTarget.
	 */
	public abstract GenericServiceDAO getGenericServiceTarget();

	/**
	 * @param genericServiceTarget The genericServiceTarget to set.
	 */
	public abstract void setGenericServiceTarget(GenericServiceDAO genericServiceTarget);

	/**
	 * stores rwtime unique like
	 * 
	 * @param rwTime
	 */
	public void storeRwTime(Rwtime rwTime);
	
	/**
	 * stores rwtime unique like
	 * 
	 */
	public void storeTxTime(Txtime txTime);
	
	/**
	 * Retrieves Id
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return Id
	 */
	public Integer getRwTimeIdByDates(int day, int month, int year);
	
	/**
	 * Retrieves rwtime Id
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return Id
	 */
	public Integer getTxTimeIdByDates(int day, int month, int year);
	
	/**
	 * stores event attributes unique like
	 * 
	 * @param eventAttrib
	 */
	public void storeEventAttribute(Eventattribute eventAttrib);
	
	/**
	 * Counts the number of itedentified items in the database
	 * 
	 * @return number if identified items
	 */
	public int getNumberOfIdentifiedEvents();
	
	/**
	 * Counts the number of itedentified items in the database
	 * 
	 * @return number if identified items
	 */
	public List getIdentifiedEventTypes();

}