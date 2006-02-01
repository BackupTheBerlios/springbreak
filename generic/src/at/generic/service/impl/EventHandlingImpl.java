package at.generic.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.generic.dao.GenericServiceDAO;
import at.generic.eventmodel.Dbinfo;
import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventattribute;
import at.generic.eventmodel.Eventtype;
import at.generic.eventmodel.Rwtime;
import at.generic.eventmodel.Txtime;
import at.generic.service.EventHandling;

/**
 * @author szabolcs
 * @version $Id: EventHandlingImpl.java,v 1.2 2006/02/01 19:48:10 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * Interface Facade for event operations
 * 
 */

public class EventHandlingImpl implements EventHandling  {
	private static Log log = LogFactory.getLog(EventHandling.class);
	private GenericServiceDAO genericServiceTarget;
	
	/**
	 * stores eventtype unique like
	 * 
	 * @return Returns the genericServiceTarget.
	 */
	public void storeEventtype(Eventtype eventType) {
		List eventTypeList = genericServiceTarget.getObjectsByQuery("from Eventtype where eventname = '" + eventType.getEventname() + "'");
		if (eventTypeList.size() == 0) {
			genericServiceTarget.saveWithoutCheck(eventType);
		}
	}
	
	/**
	 * Retrieves eventtype Id by its name.
	 * Returns -1 if nothing has been found
	 * 
	 * @param eventname
	 */
	public Integer getEventtypeIdByName(String eventname) {
		List eventTypeList = genericServiceTarget.getObjectsByQuery("from Eventtype where eventname = '" + eventname + "'");
		if (eventTypeList.size() > 0) {
			Iterator i = eventTypeList.iterator();
			while (i.hasNext()) {
				Eventtype et = (Eventtype) i.next();
				return et.getEventtypeid();
			}
		}
		
		return new Integer(-1);
	}
	
	/**
	 * stores rwtime unique like
	 * 
	 */
	public void storeRwTime(Rwtime rwTime) {
		List time = genericServiceTarget.getObjectsByQuery(
				"from Rwtime where rwday = " + rwTime.getRwday() + " and " +
				"rwmonth = " + rwTime.getRwmonth() + " and " +
				"rwyear = " + rwTime.getRwyear() 
			);
		if (time.size() == 0) {
			genericServiceTarget.saveWithoutCheck(rwTime);
		}
	}
	
	/**
	 * Retrieves rwtime Id
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return Id
	 */
	public Integer getRwTimeIdByDates(int day, int month, int year) {
		List time = genericServiceTarget.getObjectsByQuery(
				"from Rwtime where rwday = " + day + " and " +
				"rwmonth = " + month + " and " +
				"rwyear = " + year 
				);
		if (time.size() > 0) {
			Iterator i = time.iterator();
			while (i.hasNext()) {
				Rwtime rw = (Rwtime) i.next();
				return rw.getRwtimeid();
			}
		}
		
		return new Integer(-1);
	}
	
	/**
	 * stores rwtime unique like
	 * 
	 */
	public void storeTxTime(Txtime txTime) {
		List time = genericServiceTarget.getObjectsByQuery(
				"from Txtime where txday = " + txTime.getTxday() + " and " +
				"txmonth = " + txTime.getTxmonth() + " and " +
				"txyear = " + txTime.getTxyear() 
			);
		if (time.size() == 0) {
			genericServiceTarget.saveWithoutCheck(txTime);
		}
	}
	
	/**
	 * Retrieves rwtime Id
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return Id
	 */
	public Integer getTxTimeIdByDates(int day, int month, int year) {
		List time = genericServiceTarget.getObjectsByQuery(
				"from Txtime where txday = " + day + " and " +
				"txmonth = " + month + " and " +
				"txyear = " + year 
				);
		if (time.size() > 0) {
			Iterator i = time.iterator();
			while (i.hasNext()) {
				Txtime tx = (Txtime) i.next();
				return tx.getTxtimeid();
			}
		}
		
		return new Integer(-1);
	}
	
	/**
	 * stores event attributes unique like
	 * 
	 * @param eventAttrib
	 */
	public void storeEventAttribute(Eventattribute eventAttrib) {
		List time = genericServiceTarget.getObjectsByQuery(
				"from Eventattribute where attributename = '" + eventAttrib.getAttributename() + "' and " +
				"value = '" + eventAttrib.getValue() + "' and " +  
				"xmluri = '" + eventAttrib.getXmluri() + "'"
			);
		if (time.size() == 0) {
			genericServiceTarget.saveWithoutCheck(eventAttrib);
		}
	}
	
	/**
	 * Counts the number of itedentified items in the database
	 * 
	 * @return number if identified items
	 */
	public int getNumberOfIdentifiedEvents() {
		List events = genericServiceTarget.getAllObjects(new Event());
		return events.size();
		
	}
	
	/**
	 * Counts the number of itedentified items in the database
	 * 
	 * @return number if identified items
	 */
	public List getIdentifiedEventTypes() {
		List eventTypes = genericServiceTarget.getAllObjects(new Eventtype());
		List identifiedTypes = new Vector();
		
		Iterator i = eventTypes.iterator();
		while (i.hasNext()) {
			Eventtype eventType = (Eventtype) i.next();
			identifiedTypes.add(eventType.getEventname());
		}
		
		return identifiedTypes; 
	}
	
	/**
	 * Returns the last etl process infos
	 * 
	 */
	public Dbinfo getLastEtlUpdate() {
		List dbInfo = genericServiceTarget.getAllObjects(new Dbinfo());
		log.debug("### " + dbInfo.size());
		
		if (dbInfo.size() == 0) {
			log.debug("### getLastEtlUpdate 1");
			return null;
		} else {
			log.debug("### getLastEtlUpdate 2");
			return (Dbinfo) dbInfo.get(dbInfo.size() - 1);
		}
	}

	public GenericServiceDAO getGenericServiceTarget() {
		return genericServiceTarget;
	}

	public void setGenericServiceTarget(GenericServiceDAO genericServiceTarget) {
		this.genericServiceTarget = genericServiceTarget;
	}
	
	
	
}	