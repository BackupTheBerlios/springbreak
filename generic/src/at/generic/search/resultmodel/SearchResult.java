package at.generic.search.resultmodel;

import java.util.List;

import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventattribute;


/**
 * @author szabolcs
 * @version $Id: SearchResult.java,v 1.1 2006/02/14 10:09:38 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Model for the SearchResults
 * 
 */
public class SearchResult {
	private Event event;
	private Eventattribute eventAttribute;
	private List attributeList;
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
	 * @return Returns the eventAttribute.
	 */
	public Eventattribute getEventAttribute() {
		return eventAttribute;
	}
	/**
	 * @param eventAttribute The eventAttribute to set.
	 */
	public void setEventAttribute(Eventattribute eventAttribute) {
		this.eventAttribute = eventAttribute;
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
	/**
	 * @return Returns the attributeList.
	 */
	public List getAttributeList() {
		return attributeList;
	}
	/**
	 * @param attributeList The attributeList to set.
	 */
	public void setAttributeList(List attributeList) {
		this.attributeList = attributeList;
	}
	
	
	
}