package at.generic.web.commandObj;

import java.util.Vector;

/**
 * @author szabolcs
 * @version $Id: ListEventsCommand.java,v 1.2 2005/12/19 23:17:35 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * Data Object for List in Browser View
 * 
 */
public class ListEventsCommand { 
	private String eventId;
	private String eventType;
	private String dateTime;
	
	/**
	 * @return Returns the dateTime.
	 */
	public String getDateTime() {
		return dateTime;
	}
	/**
	 * @param dateTime The dateTime to set.
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	/**
	 * @return Returns the eventId.
	 */
	public String getEventId() {
		return eventId;
	}
	/**
	 * @param eventId The eventId to set.
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	/**
	 * @return Returns the eventType.
	 */
	public String getEventType() {
		return eventType;
	}
	/**
	 * @param eventType The eventType to set.
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
}