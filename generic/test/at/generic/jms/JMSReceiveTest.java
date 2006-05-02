package at.generic.jms;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import junit.framework.TestCase;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import at.generic.event.BaseEvent;
import at.generic.event.IEventDefinitionResolver;


public class JMSReceiveTest extends TestCase {

	
	private FileSystemXmlApplicationContext ctx;

	

	@Override
	protected void setUp() throws Exception {
		String[] paths = { "C:/java/workspace/generic/config/web/applicationInput.xml"};
		
		ctx = new FileSystemXmlApplicationContext(paths);
		
		
		
	}
	
	public void testEventAdapter()
	{
		//Needs a running + filled JMS!
		IEventAdapter eventAdapter = (IEventAdapter)ctx.getBean("EventAdapter");
		
		BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
		
		eventAdapter.setEventQueue(queue);
		eventAdapter.receiveAndQueueEvent();
		assertEquals(1,queue.size());
		try {
			assertNotNull((Object)queue.take());
		} catch (InterruptedException e) {
		}
		
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

}



/*

public class JMSReceiveTest {
	
	
	public static void main(String[] args) {
		
		ActiveMQConnectionFactory connFactory;
		connFactory = new ActiveMQConnectionFactory();
		connFactory.setBrokerURL("tcp://localhost:61616");
		
		JmsTemplate jmsTemplate = new JmsTemplate(connFactory);
		jmsTemplate.setMessageConverter(new SimpleMessageConverter());
		String m = (String)jmsTemplate.receiveAndConvert("MyDestination");
		System.out.println(m);
		 
		
		
		
	}
	
	

}*/
