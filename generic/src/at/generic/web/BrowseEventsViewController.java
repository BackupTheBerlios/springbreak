package at.generic.web;

import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.generic.eventmodel.OrderReceivedEvent;
import at.generic.service.EventSourceManager;
import at.generic.web.commandObj.ListEventsCommand;


/**
 * @author szabolcs
 * @version $Id: BrowseEventsViewController.java,v 1.1 2005/12/14 22:15:14 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Event Browser View Controller
 * 
 */
public class BrowseEventsViewController implements Controller { 
	private static Log log = LogFactory.getLog(BrowseEventsViewController.class); 
	
	private EventSourceManager eventSourceManager;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
	    request.getSession().invalidate();
	    
	    Vector browserList = new Vector();
	    
	    //Vector correlatedEventModels = eventSourceManager.getAllCorrelatedEventModels();
	    Vector correlatedEventModels = eventSourceManager.getCorrelatedEventsByPage(2);
	     correlatedEventModels = eventSourceManager.getCorrelatedEventsByPage(1);
	    Iterator i = correlatedEventModels.iterator();
		while (i.hasNext()) {
			Object eventObj = i.next();
			
			if (eventObj.getClass().getName().equals("at.generic.eventmodel.OrderReceivedEvent")) {
				OrderReceivedEvent orderReceivedEvent = (OrderReceivedEvent)eventObj;

				ListEventsCommand eventCmd = new ListEventsCommand();
				eventCmd.setEventId(orderReceivedEvent.getCorrelatedEvent().getId().toString());
				eventCmd.setEventType("OrderReceivedEvent");
				eventCmd.setDateTime(orderReceivedEvent.getDateTime());
				
				browserList.add(eventCmd);
			}
		}
	    
		return new ModelAndView("eventbrowser", "eventCmd", browserList); 
	}

	/**
	 * @return Returns the eventSourceManager.
	 */
	public EventSourceManager getEventSourceManager() {
		return eventSourceManager;
	}

	/**
	 * @param eventSourceManager The eventSourceManager to set.
	 */
	public void setEventSourceManager(EventSourceManager eventSourceManager) {
		this.eventSourceManager = eventSourceManager;
	}
	
	

	
}