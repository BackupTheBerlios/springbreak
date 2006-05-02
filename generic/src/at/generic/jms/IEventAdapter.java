package at.generic.jms;

import java.util.concurrent.BlockingQueue;

import org.springframework.jms.core.JmsTemplate;

public interface IEventAdapter extends Runnable {

	public abstract void receiveAndQueueEvent();

	public abstract JmsTemplate getJmsTemplate();

	public abstract void setJmsTemplate(JmsTemplate jmsTemplate);
	
	public void setEventQueue(BlockingQueue<Object> eventQueue);
	

}