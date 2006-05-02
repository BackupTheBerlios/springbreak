package at.generic.indexing;

import at.generic.event.BaseEvent;

public interface IIndexingPersistence {

	public abstract BaseEvent addDocument(BaseEvent event);

}