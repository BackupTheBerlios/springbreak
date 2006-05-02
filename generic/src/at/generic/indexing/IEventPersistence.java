package at.generic.indexing;

import at.generic.event.BaseEvent;

public interface IEventPersistence {
	
	public BaseEvent storeAndSimpleIndex(BaseEvent event);

}
