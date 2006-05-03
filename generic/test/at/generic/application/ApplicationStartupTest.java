package at.generic.application;

import junit.framework.TestCase;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import at.generic.jms.EventProcessor;

public class ApplicationStartupTest extends TestCase {

	private FileSystemXmlApplicationContext ctx;
	
	@Override
	protected void setUp() throws Exception {
		String[] paths = { "C:/java/workspace/generic/config/web/applicationEventProcessingContext.xml" };
		
		ctx = new FileSystemXmlApplicationContext(paths);
	}
	
	
	public void testStartUp()
	{
		EventProcessor ep = (EventProcessor)ctx.getBean("EventProcessor");
		ep.onStart();
		
	}
}
