package at.generic.jms;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import at.generic.indexing.SimpleIndexingWorker;

public class EventProcessor {
	
	IEventAdapter _eventAdapter;
	private BlockingQueue<Object> _eventQueue;
	
	private ExecutorService _executorServiceIndexing;
	private ExecutorService _executorServiceAdapter;
	

	
	private boolean _stopped = false;
	
	

	public void onInit()
	{	
		_eventQueue = new LinkedBlockingQueue<Object> ();
		
		_eventAdapter.setEventQueue(this._eventQueue);
	}
	
	
	public void onStart()
	{
		onInit();
		
		
		_executorServiceAdapter.execute(_eventAdapter);
		
		while (_stopped == false)
		{	
			
			if (this._eventQueue.size() > 0) //TODO: is this condition correct?!
//				TODO: factory for IndexingWorker
			_executorServiceIndexing.execute(new SimpleIndexingWorker(_eventQueue));
		}
		
		_executorServiceIndexing.shutdown();
		
		_executorServiceAdapter.shutdown();
		try {
			if (_executorServiceAdapter.awaitTermination(1,TimeUnit.SECONDS)  == false)
				_executorServiceAdapter.shutdownNow();
		} catch (InterruptedException e) {
		}
		
		onStop();
	}
	
	public void onStop()
	{
		//Empty Hook
	}
	

	public boolean isStopped() {
		return _stopped;
	}


	public void setStopped(boolean _stopped) {
		this._stopped = _stopped;
	}

	public void setEventAdapter(IEventAdapter adapter) {
		_eventAdapter = adapter;
	}

	protected void setEventQueue(BlockingQueue<Object> queue) {
		_eventQueue = queue;
	}


	public void setExecutorServiceAdapter(ExecutorService serviceAdapter) {
		_executorServiceAdapter = serviceAdapter;
	}


	public void setExecutorServiceIndexing(ExecutorService serviceIndexing) {
		_executorServiceIndexing = serviceIndexing;
	}
	
	

	
	
	
	
	
	
	
	
	

}
