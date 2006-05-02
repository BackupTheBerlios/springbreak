package at.generic.indexing;

import java.util.concurrent.BlockingQueue;

import at.generic.event.BaseEvent;
import at.generic.jms.IEventTransformer;
import at.generic.jms.XML2EventTransformer;

public class IndexingWorker implements Runnable {
	
	IEventTransformer _eventTransformer = new XML2EventTransformer();
	IEventPersistence _eventPersistence = new EventPersistence();
	ICorrelationService _correlationService;
	
	private BlockingQueue<Object> _eventQueue;
	
	
	public IndexingWorker (BlockingQueue<Object> eventQueue)
	{
		_eventQueue = eventQueue;
		
	}
	
	/**
	 * Taking one element from eventQueue and execute all steps through the indexing process.
	 * 
	 * @param event the event object.
	 */
	public void processEvent()
	{
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
	
	public void run()
	{
		this.processEvent();
		
	}

	protected IEventTransformer getEventTransformer() {
		return _eventTransformer;
	}
	
	

}
