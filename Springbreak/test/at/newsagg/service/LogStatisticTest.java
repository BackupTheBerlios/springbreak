/*
 * Created on 02.05.2005
 * king
 * 
 */
package at.newsagg.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import at.newsagg.model.User;
import at.newsagg.service.impl.LogStatisticsCronJobServiceImpl;

import junit.framework.TestCase;

/**
 * @author king
 * @version
 * created on 02.05.2005 10:44:53
 *
 */
public class LogStatisticTest extends TestCase{
    
    private ApplicationContext ctx; 
	private LogStatisticsCronJobServiceImpl mgr; 
    
    protected void setUp() throws Exception { 
		String[] paths = {"applicationContext.xml" ,"applicationContext-timers.xml"}; 
		ctx = new ClassPathXmlApplicationContext(paths); 
		mgr = (LogStatisticsCronJobServiceImpl) ctx.getBean("LogStatisticsCronJobService"); 
	} 
	
	protected void tearDown() throws Exception { 
		
		mgr = null; 
	} 
	
	public void testRun() throws Exception { 
	    
	    mgr.runLogSubscribers();
	    assertTrue (true);
	}

}
