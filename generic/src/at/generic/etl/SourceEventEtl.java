package at.generic.etl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.dao.OrderReceivedEventDAO;
import at.generic.eventmodel.OrderReceivedEvent;
import at.generic.model.Correlatedevent;
import at.generic.xmlhandlers.EventXmlHandler;

/**
 * @author szabolcs
 * @version $Id: SourceEventEtl.java,v 1.1 2005/12/19 23:17:08 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Main File for the coordination of loading the events from the source and transforming
 * them into a warehouse like representation for further use.
 * 
 */
public class SourceEventEtl {
	private static Log log = LogFactory.getLog(SourceEventEtl.class);
	private CorrelatedeventDAO correlatedEventDAO;
	private OrderReceivedEventDAO orderReceivedEventDAO;
	private Date lastUpdate;
	private ArrayList identifiedEvents;
	private int numberOfIdentifiedEvents;
	private int numberOfProcessedEvents;
	private boolean initDone;
	
	public void getBasicInfos () {
		// go through database relations to count the initial numberOfIdentifiedEvents, 
		// numberOfPorcessedEvents and identifiedEvents
		if (initDone == false) {
			this.numberOfIdentifiedEvents = 0;
			this.numberOfProcessedEvents = 0;
			
			// get all Events from Source to determine the maximum size
			List correlatedEventList = correlatedEventDAO.getCorrelatedevents();
			numberOfProcessedEvents = correlatedEventList.size();
			
			// OrderReceivedEvent
			List orderReceivedEvents = orderReceivedEventDAO.getAll();
			numberOfIdentifiedEvents = numberOfIdentifiedEvents + orderReceivedEvents.size();
			
			initDone = true;
		} 
		
		
	}
	
	public void transformSourceEvents () {
		this.lastUpdate = new Date(System.currentTimeMillis());
		this.numberOfIdentifiedEvents = 0;
		this.numberOfProcessedEvents = 0;
		
		List correlatedEventList = correlatedEventDAO.getCorrelatedevents();
		
		EventXmlHandler eventXmlHandler = new EventXmlHandler();
		
		Iterator i = correlatedEventList.iterator();
		while (i.hasNext()) {
			Correlatedevent correlatedEvent = (Correlatedevent) i.next();
			this.numberOfProcessedEvents++;
			
			// determine the xml type e.g.: OrderReceived,...
			StringBuffer stringBuffer = new StringBuffer(correlatedEvent.getEventXml());
			ByteArrayInputStream xmlStream = new ByteArrayInputStream(stringBuffer.toString().getBytes());
			
			try {
				SAXReader reader = new SAXReader();
		        Document document = reader.read(xmlStream);
		        
				// Create EventModel and Parse xml according to its type
		        Object parsedEvent = eventXmlHandler.handleEvent(correlatedEvent, document.getRootElement().getName());
		        
		        // store object
		        if (parsedEvent != null)
		        	this.storeParsedEvent(parsedEvent);
		        
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void storeParsedEvent(Object parsedEvent) {
		if (parsedEvent.getClass().getName().equals("at.generic.eventmodel.OrderReceivedEvent")) {
			orderReceivedEventDAO.save((OrderReceivedEvent)parsedEvent);
			this.numberOfIdentifiedEvents++;
		}
	}
	

	/**
	 * @return Returns the correlatedEventDAO.
	 */
	public CorrelatedeventDAO getCorrelatedEventDAO() {
		return correlatedEventDAO;
	}

	/**
	 * @param correlatedEventDAO The correlatedEventDAO to set.
	 */
	public void setCorrelatedEventDAO(CorrelatedeventDAO correlatedEventDAO) {
		this.correlatedEventDAO = correlatedEventDAO;
	}

	/**
	 * @return Returns the orderReceivedEventDAO.
	 */
	public OrderReceivedEventDAO getOrderReceivedEventDAO() {
		return orderReceivedEventDAO;
	}

	/**
	 * @param orderReceivedEventDAO The orderReceivedEventDAO to set.
	 */
	public void setOrderReceivedEventDAO(OrderReceivedEventDAO orderReceivedEventDAO) {
		this.orderReceivedEventDAO = orderReceivedEventDAO;
	}

	/**
	 * @return Returns the identifiedEvents.
	 */
	public ArrayList getIdentifiedEvents() {
		return identifiedEvents;
	}

	/**
	 * @param identifiedEvents The identifiedEvents to set.
	 */
	public void setIdentifiedEvents(ArrayList identifiedEvents) {
		this.identifiedEvents = identifiedEvents;
	}

	/**
	 * @param numberOfProcessedEvents The numberOfProcessedEvents to set.
	 */
	public void setNumberOfProcessedEvents(int numberOfProcessedEvents) {
		this.numberOfProcessedEvents = numberOfProcessedEvents;
	}

	/**
	 * @return Returns the lastUpdate.
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate The lastUpdate to set.
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @return Returns the numberOfIdentifiedEvents.
	 */
	public int getNumberOfIdentifiedEvents() {
		return numberOfIdentifiedEvents;
	}

	/**
	 * @param numberOfIdentifiedEvents The numberOfIdentifiedEvents to set.
	 */
	public void setNumberOfIdentifiedEvents(int numberOfIdentifiedEvents) {
		this.numberOfIdentifiedEvents = numberOfIdentifiedEvents;
	}

	/**
	 * @return Returns the numberOfPorcessedEvents.
	 */
	public int getNumberOfProcessedEvents() {
		return numberOfProcessedEvents;
	}

	/**
	 * @param numberOfPorcessedEvents The numberOfPorcessedEvents to set.
	 */
	public void setNumberOfPorcessedEvents(int numberOfProcessedEvents) {
		this.numberOfProcessedEvents = numberOfProcessedEvents;
	}

	
	
	
}