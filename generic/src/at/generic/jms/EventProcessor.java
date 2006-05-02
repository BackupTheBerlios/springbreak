package at.generic.jms;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import at.generic.indexing.IndexingWorker;

public class EventProcessor {
	
	IEventAdapter _eventAdapter;
	private BlockingQueue<Object> _eventQueue;
	

	private ExecutorService _executorServiceIndexing;
	private ExecutorService _executorServiceAdapter;
	
	private boolean _stopped = false;
	
	
	

	public EventProcessor ()
	{
		_eventAdapter = new SimpleEventAdapter();
		
		_eventQueue = new LinkedBlockingQueue<Object> ();
		
		_eventAdapter.setEventQueue(this._eventQueue);
		
		_executorServiceIndexing = Executors.newFixedThreadPool(10);
		_executorServiceAdapter = Executors.newSingleThreadExecutor();
	}
	
	public void onStart()
	{
		
		while (_stopped == false)
		{
			_executorServiceAdapter.execute(_eventAdapter);
			_executorServiceIndexing.execute(new IndexingWorker(_eventQueue));
		}
		
		_executorServiceIndexing.shutdown();
		
		_executorServiceAdapter.shutdown();
		try {
			if (_executorServiceAdapter.awaitTermination(1,TimeUnit.SECONDS)  == false)
				_executorServiceAdapter.shutdownNow();
		} catch (InterruptedException e) {
		}
	}
	

	public boolean isStopped() {
		return _stopped;
	}


	public void setStopped(boolean _stopped) {
		this._stopped = _stopped;
	}

	protected void setEventAdapter(IEventAdapter adapter) {
		_eventAdapter = adapter;
	}

	protected void setEventQueue(BlockingQueue<Object> queue) {
		_eventQueue = queue;
	}
	
	

	
	
	
	
	
	
	
	
	

}
