package at.generic.jms;

import org.springframework.jms.core.JmsTemplate;

import at.generic.event.BaseEvent;

public interface IEventAdapter {

	public abstract BaseEvent receiveAndConvertEvent();

	public abstract JmsTemplate getJmsTemplate();

	public abstract void setJmsTemplate(JmsTemplate jmsTemplate);

}