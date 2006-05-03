package at.generic.indexing;

import at.generic.event.BaseEvent;

public class EventPersistence implements IEventPersistence {

	private IEvent2DBPersistence event2DBPersistence = new Event2DBPersistence();
	private IIndexingPersistence indexingPersistence = new LuceneIndexingPersistence();
	
	

	public BaseEvent storeAndSimpleIndex(BaseEvent event) {
		// TODO Auto-generated method stub
		event = event2DBPersistence.store(event);
		event = indexingPersistence.addDocument(event);
		
		return event;
	}
	
	public void setEvent2DBPersistence(IEvent2DBPersistence event2DBPersistence) {
		this.event2DBPersistence = event2DBPersistence;
	}

	public void setIndexingPersistence(
			IIndexingPersistence IndexingPersistence) {
		this.indexingPersistence = IndexingPersistence;
	}

	
	
	

}
