package at.generic.etl;

import junit.framework.TestCase;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import at.generic.etl.impl.GranSourceEvent;

public class LuceneIndexPerformanceTest extends TestCase {



	private SourceEventEtl granSourceEvent;
	
	private FileSystemXmlApplicationContext ctx;

	public LuceneIndexPerformanceTest() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setUp() throws Exception {
		String[] paths = { "C:/java/workspace/generic/config/web/applicationContextTest.xml",
				"C:/java/workspace/generic/config/web/userDAOs.xml" };
		
		ctx = new FileSystemXmlApplicationContext(paths);
		

	}
	
	public void testIndexPerformance()
	{
		granSourceEvent = (SourceEventEtl)ctx.getBean("sourceEventEtl");
		granSourceEvent.transformSourceEvents();
		while (granSourceEvent.isEtlRunning())
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}		
		assertFalse(granSourceEvent.isEtlRunning());
		
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

}
