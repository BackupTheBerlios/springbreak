package at.generic.jms;

import org.springframework.jms.core.JmsTemplate;

import at.generic.event.BaseEvent;

public class SimpleEventAdapter implements IEventAdapter {
	
	private JmsTemplate jmsTemplate;
	private IEventTransformer eventTransformer;
	
	/* (non-Javadoc)
	 * @see at.generic.jms.IEventAdapter#receiveAndConvertEvent()
	 */
	public BaseEvent receiveAndConvertEvent()
	{
		//receiveAndConverts blocks until message arrives
		Object message = jmsTemplate.receiveAndConvert(jmsTemplate.getDefaultDestinationName());
		
		BaseEvent baseEvent = null;
		try {
			 
			
			baseEvent = eventTransformer.transform(message);
			  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return baseEvent;
		
		
		
	}

	/* (non-Javadoc)
	 * @see at.generic.jms.IEventAdapter#getJmsTemplate()
	 */
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	/* (non-Javadoc)
	 * @see at.generic.jms.IEventAdapter#setJmsTemplate(org.springframework.jms.core.JmsTemplate)
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setEventTransformer(IEventTransformer eventTransformer) {
		this.eventTransformer = eventTransformer;
	}
	

}
