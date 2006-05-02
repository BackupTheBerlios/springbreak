package at.generic.indexing;

import at.generic.event.BaseEvent;

public class EventPersistence implements IEventPersistence {

	private IEvent2DBPersistence event2DBPersistence = new Event2DBPersistence();
	private IIndexingPersistence luceneIndexingPersistence = new LuceneIndexingPersistence();
	
	public BaseEvent storeAndSimpleIndex(BaseEvent event) {
		// TODO Auto-generated method stub
		event = event2DBPersistence.store(event);
		event = luceneIndexingPersistence.addDocument(event);
		
		return event;
	}
	
	

}
