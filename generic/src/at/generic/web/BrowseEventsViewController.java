package at.generic.web;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import at.generic.eventmodel.BaseEvent;
import at.generic.service.EventSourceManager;
import at.generic.web.commandObj.BrowserCommand;


/**
 * @author szabolcs
 * @version $Id: BrowseEventsViewController.java,v 1.4 2005/12/22 19:39:43 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.4 $
 * 
 * Event Browser View Controller
 * 
 */
public class BrowseEventsViewController implements Controller { 
	private static Log log = LogFactory.getLog(BrowseEventsViewController.class); 
	
	private EventSourceManager eventSourceManager;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		// check if the Browser has been initialized
		eventSourceManager.init();
		
	    Vector browserList = new Vector();
	    BrowserCommand browserCmd = new BrowserCommand();
	    browserCmd.setEventDetail("nothing to display");
	    
	    // start with page 1 if the user clicked "Browse Events"
	    List baseEventModels = null;
	    
	    if (request.getParameter("browserPage") == null) {
	    	baseEventModels = eventSourceManager.getBaseEventsByPage(1);
	    	browserCmd.setCurrentPage(1);
	    } else {
	    	baseEventModels = eventSourceManager.getBaseEventsByPage(Integer.parseInt(request.getParameter("browserPage")));
	    	browserCmd.setCurrentPage(Integer.parseInt(request.getParameter("browserPage")));
	    }
	    
	    Iterator i = baseEventModels.iterator();
	    while (i.hasNext()) {
			BaseEvent baseEvent = (BaseEvent)i.next();
			
			// pretty printing
			baseEvent.setEventXml(this.convertDocToPretty(baseEvent.getEventXml()));
            
            browserList.add(baseEvent);
            
			
			if (request.getParameter("selectedEventId") != null 
					&& request.getParameter("selectedEventId").trim().equals(baseEvent.getId().toString())) {
				browserCmd.setEventDetail(baseEvent.getEventXml());
				//log.debug("### eventXML" + baseEvent.getEventXml().substring(0,40));
			}
			//log.debug("### selectedEventId : " + request.getParameter("selectedEventId"));
			//log.debug("### baseEvent id : " + baseEvent.getId());
	    }
	    
	    browserCmd.setListEventsCommand(browserList);
	    browserCmd.setMaxPageSize(eventSourceManager.getSourceEventEtl().getNumberOfProcessedEvents()/eventSourceManager.getPageSize()+1);
	    
	   
		return new ModelAndView("eventbrowser", "browserCmd", browserCmd); 
	}
	
	/** 
	 * Creates a pretty printing out of a string 
	 * 
	 * @param xml mixed up chars
	 * @return a pretty looking String ;-)
	 */
	private String convertDocToPretty(String xml) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new InputSource( new StringReader(xml)) );
			StringWriter stringWriter = new StringWriter();
			OutputFormat format = new OutputFormat( doc );
	        format.setIndenting( false );
	        format.setLineWidth( 65 );
	        format.setIndent( 2 );
	        XMLSerializer serializer = new XMLSerializer( stringWriter, format );
	        serializer.serialize(doc);
	        return stringWriter.toString();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        return xml;
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