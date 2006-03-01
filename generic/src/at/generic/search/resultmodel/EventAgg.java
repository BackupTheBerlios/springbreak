package at.generic.search.resultmodel;

import java.util.List;

import at.generic.eventmodel.Event;


/**
 * @author szabolcs
 * @version $Id: EventAgg.java,v 1.2 2006/03/01 11:44:52 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision:
 * 
 * Found Event and Eventattributes
 * 
 */
public class EventAgg {
	private Event event;
	private List eventAttributes;
	private String eventTypeName;
	
	/**
	 * @return Returns the event.
	 */
	public Event getEvent() {
		return event;
	}
	/**
	 * @param event The event to set.
	 */
	public void setEvent(Event event) {
		this.event = event;
	}
	/**
	 * @return Returns the eventAttributes.
	 */
	public List getEventAttributes() {
		return eventAttributes;
	}
	/**
	 * @param eventAttributes The eventAttributes to set.
	 */
	public void setEventAttributes(List eventAttributes) {
		this.eventAttributes = eventAttributes;
	}
	
	/**
	 * @return Returns the eventTypeName.
	 */
	public String getEventTypeName() {
		return eventTypeName;
	}
	/**
	 * @param eventTypeName The eventTypeName to set.
	 */
	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}
	
	
	
}	