package at.generic.indexing;

import java.util.concurrent.BlockingQueue;

import at.generic.event.BaseEvent;
/**
 * SimpleIndexingWorker transforms input to Event, stores it in DB, indexes it to make it searchable.
 * @author rvecera
 *
 */
public class SimpleIndexingWorker extends IndexingWorker {

	/**
	 * Taking one element from eventQueue and execute all steps through the indexing process.
	 * 
	 * @param event the event object.
	 */
	
	public SimpleIndexingWorker (BlockingQueue<Object> eventQueue)
	{
		super(eventQueue);
		
	}
	
	
	
	@Override
	public void processEvent() {
		Object message = null;
		
		try {
			message = _eventQueue.take();
		} catch (InterruptedException e) {}
		
		
		BaseEvent event = null;
		//Steps through the indexing process
		try {
			event = _eventTransformer.transform(message);
		} catch (Exception e) {
			// TODO TRANSFORMER CHANGE NEEDED. Give better Exceptions
			e.printStackTrace();
		}
		if (event != null)
		{
			event = _eventPersistence.storeAndSimpleIndex(event);
			//_correlationService.storeAndComplexIndex(event);
		}

	}

}
