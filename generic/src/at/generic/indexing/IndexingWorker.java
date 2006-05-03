package at.generic.indexing;

import java.util.concurrent.BlockingQueue;

import at.generic.event.BaseEvent;
import at.generic.jms.IEventTransformer;
import at.generic.jms.XML2EventTransformer;

public abstract class IndexingWorker implements Runnable {
	
	IEventTransformer _eventTransformer = new XML2EventTransformer();
	IEventPersistence _eventPersistence = new EventPersistence();
	ICorrelationService _correlationService;
	
	BlockingQueue<Object> _eventQueue;
	
	
	public IndexingWorker (BlockingQueue<Object> eventQueue)
	{
		_eventQueue = eventQueue;
		
	}
	
	
	public abstract void processEvent();
	
	
	public void run()
	{
		this.processEvent();
		
	}

	protected IEventTransformer getEventTransformer() {
		return _eventTransformer;
	}


	protected IEventPersistence get_eventPersistence() {
		return _eventPersistence;
	}

	
	

}
