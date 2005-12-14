package at.generic.eventmodel;

import at.generic.model.Correlatedevent;

/**
 * @author szabolcs
 * @version $Id: BaseCorrelatedEvent.java,v 1.1 2005/12/14 22:15:14 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Raw Data that must be attached to all correlated events
 * 
 */
public class BaseCorrelatedEvent {
	private Correlatedevent correlatedEvent;

	/**
	 * @return Returns the correlatedEvent.
	 */
	public Correlatedevent getCorrelatedEvent() {
		return correlatedEvent;
	}

	/**
	 * @param correlatedEvent The correlatedEvent to set.
	 */
	public void setCorrelatedEvent(Correlatedevent correlatedEvent) {
		this.correlatedEvent = correlatedEvent;
	}

	
}