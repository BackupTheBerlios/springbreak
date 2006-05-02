package at.generic.indexing;

import at.generic.event.BaseEvent;

public class Event2DBPersistence implements IEvent2DBPersistence {
	
	/* (non-Javadoc)
	 * @see at.generic.indexing.IEvent2DBPersistence#store(at.generic.event.BaseEvent)
	 */
	public BaseEvent store (BaseEvent event)
	{
		//TODO: Implement;
		System.out.println("Event stored in DB!");
		return event;
	}

}
