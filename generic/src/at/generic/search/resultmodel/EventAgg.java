package at.generic.search.resultmodel;

import java.util.List;

import at.generic.eventmodel.Event;


/**
 * @author szabolcs
 * @version $Id: EventAgg.java,v 1.1 2006/02/27 14:58:25 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision:
 * 
 * Found Event and Eventattributes
 * 
 */
public class EventAgg {
	private Event event;
	private List eventAttributes;
	
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
	
	
	
}	