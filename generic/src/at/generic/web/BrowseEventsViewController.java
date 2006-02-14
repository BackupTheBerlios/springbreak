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

import at.generic.eventmodel.Event;
import at.generic.service.EventHandling;
import at.generic.service.EventSourceManager;
import at.generic.util.XMLUtils;
import at.generic.web.commandObj.BrowserCommand;
import at.generic.web.commandObj.BrowserEventList;


/**
 * @author szabolcs
 * @version $Id: BrowseEventsViewController.java,v 1.6 2006/02/14 10:10:08 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.6 $
 * 
 * Event Browser View Controller
 * 
 */
public class BrowseEventsViewController implements Controller { 
	private static Log log = LogFactory.getLog(BrowseEventsViewController.class); 
	
	private EventSourceManager eventSourceManager;
	private EventHandling eventHandling;
	
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
	    	Event baseEvent = (Event)i.next();
	    	
	    	// create List for View with main data
	    	
	    	BrowserEventList browserListEvent = new BrowserEventList();
	    	browserListEvent.setEventid(baseEvent.getEventid());
	    	browserListEvent.setLocaltimeid(baseEvent.getLocaltimeid());
	    	browserListEvent.setEventtype(eventHandling.getEventtypeNameById(baseEvent.getEventtypeid().intValue()));
            
            browserList.add(browserListEvent);

			if (request.getParameter("selectedEventId") != null 
					&& request.getParameter("selectedEventId").trim().equals(baseEvent.getEventid().toString())) {
				
				// pretty printing
				browserCmd.setEventDetail(new XMLUtils().convertDocToPretty(baseEvent.getXmlcontent()));
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

	/**
	 * @return Returns the eventHandling.
	 */
	public EventHandling getEventHandling() {
		return eventHandling;
	}

	/**
	 * @param eventHandling The eventHandling to set.
	 */
	public void setEventHandling(EventHandling eventHandling) {
		this.eventHandling = eventHandling;
	}
	
	

	
}