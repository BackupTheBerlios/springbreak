package at.generic.indexing;

import at.generic.event.BaseEvent;

public interface IEvent2DBPersistence {

	public abstract BaseEvent store(BaseEvent event);

}