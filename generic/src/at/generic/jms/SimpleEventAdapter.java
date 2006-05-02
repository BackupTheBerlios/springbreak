package at.generic.jms;

import java.util.concurrent.BlockingQueue;

import org.springframework.jms.core.JmsTemplate;

public class SimpleEventAdapter implements IEventAdapter {

	private JmsTemplate _jmsTemplate;	
	private boolean _stopped = false;

	private BlockingQueue<Object> _eventQueue;
	

	
	
	/**
	 * Using Spring's JmsTemplate.receiveAndConvert() to receive an event from JMS.
	 */
	public void receiveAndQueueEvent() {
			Object message = _jmsTemplate.receiveAndConvert(_jmsTemplate
					.getDefaultDestinationName());
			_eventQueue.add(message);
	}
	
	public void run()
	{
		this.receiveAndQueueEvent();
	}

	
	
	//GETTER + SETTER
	
	public boolean isStopped() {
		return _stopped;
	}

	public void setStopped(boolean stopped) {
		this._stopped = stopped;
	}
	
	
	public void setEventQueue(BlockingQueue<Object> eventQueue) {
		this._eventQueue = eventQueue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.generic.jms.IEventAdapter#getJmsTemplate()
	 */
	public JmsTemplate getJmsTemplate() {
		return _jmsTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.generic.jms.IEventAdapter#setJmsTemplate(org.springframework.jms.core.JmsTemplate)
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this._jmsTemplate = jmsTemplate;
	}


}
