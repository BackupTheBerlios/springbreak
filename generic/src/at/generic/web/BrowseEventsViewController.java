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
import at.generic.web.commandObj.BrowserCommand;
import at.generic.web.commandObj.ListEventsCommand;


/**
 * @author szabolcs
 * @version $Id: BrowseEventsViewController.java,v 1.2 2005/12/19 23:18:07 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * Event Browser View Controller
 * 
 */
public class BrowseEventsViewController implements Controller { 
	private static Log log = LogFactory.getLog(BrowseEventsViewController.class); 
	
	private EventSourceManager eventSourceManager;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
	    //request.getSession().invalidate();
		
		// check if the Browser has been initialized
		/*if (eventSourceManager.getPopulatedNumberOfEvents() == false) {
			eventSourceManager.getAllCorrelatedEventModels();
			log.debug("### identified events: " + eventSourceManager.getNumberOfEvents());
		}
		
	    Vector browserList = new Vector();
	    BrowserCommand browserCmd = new BrowserCommand();
	    
	    // start with page 1 if the user clicked "Browse Events"
	    Vector correlatedEventModels = null;
	    
	    if (request.getParameter("browserPage") == null) {
	    	correlatedEventModels = eventSourceManager.getCorrelatedEventsByPage(1);
	    	browserCmd.setCurrentPage(1);
	    } else {
	    	correlatedEventModels = eventSourceManager.getCorrelatedEventsByPage(Integer.parseInt(request.getParameter("browserPage")));
	    	browserCmd.setCurrentPage(Integer.parseInt(request.getParameter("browserPage")));
	    }

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
			} else if (eventObj.getClass().getName().equals("at.generic.eventmodel.BaseCorrelatedEvent")) {
				BaseCorrelatedEvent baseCorrelatedEvent = (BaseCorrelatedEvent)eventObj;

				ListEventsCommand eventCmd = new ListEventsCommand();
				eventCmd.setEventId(baseCorrelatedEvent.getCorrelatedEvent().getId().toString());
				eventCmd.setEventType("unidentified Event");
				eventCmd.setDateTime("unknown");
				
				browserList.add(eventCmd);
			}
		}
		
		browserCmd.setListEventsCommand(browserList);
	    browserCmd.setMaxPageSize(eventSourceManager.getNumberOfEvents()/eventSourceManager.getPageSize()+1);
	    
		log.debug("### getMaxPageSize() : " + browserCmd.getMaxPageSize());
		return new ModelAndView("eventbrowser", "browserCmd", browserCmd); */
		return new ModelAndView("eventbrowser");
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