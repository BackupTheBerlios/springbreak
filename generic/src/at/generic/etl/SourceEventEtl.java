package at.generic.etl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import at.generic.dao.GenericServiceDAO;
import at.generic.eventmodel.BaseEvent;
import at.generic.eventmodel.OrderConfirmedEvent;
import at.generic.eventmodel.OrderReceivedEvent;
import at.generic.model.Correlatedevent;
import at.generic.xmlhandlers.EventXmlHandler;

/**
 * @author szabolcs
 * @version $Id: SourceEventEtl.java,v 1.3 2005/12/21 22:06:30 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.3 $
 * 
 * Main File for the coordination of loading the events from the source and transforming
 * them into a warehouse like representation for further use.
 * 
 */
public class SourceEventEtl {
	private static Log log = LogFactory.getLog(SourceEventEtl.class);
	private GenericServiceDAO genericServiceTarget;
	private GenericServiceDAO genericServiceSource;
	private Date lastUpdate;
	private ArrayList identifiedEvents;
	private int numberOfIdentifiedEvents;
	private int numberOfProcessedEvents;
	private boolean initDone;
	
	/**
	 * Should be executed init to get some infos about the amount of data that has been 
	 * transformed and how much is left... 
	 *
	 */
	public void getBasicInfos () {
		// go through database relations to count the initial numberOfIdentifiedEvents, 
		// numberOfPorcessedEvents and identifiedEvents
		if (initDone == false) {
			this.numberOfIdentifiedEvents = 0;
			this.numberOfProcessedEvents = 0;
			
			// get all Events from Source to determine the maximum size
			List correlatedEventList = genericServiceSource.getAllObjects(new Correlatedevent());
			numberOfProcessedEvents = correlatedEventList.size();
			
			// OrderReceivedEvent
			List orderReceivedEvents = genericServiceTarget.getAllObjects(new OrderReceivedEvent());
			numberOfIdentifiedEvents = numberOfIdentifiedEvents + orderReceivedEvents.size();
			
			// OrderConfirmedEvent
			List orderConfirmedEvents = genericServiceTarget.getAllObjects(new OrderConfirmedEvent());
			numberOfIdentifiedEvents = numberOfIdentifiedEvents + orderConfirmedEvents.size();
			
			initDone = true;
		} 
		
		
	}
	
	/**
	 * Main transformation service - coordinates all the work
	 *
	 */
	public void transformSourceEvents () {
		this.lastUpdate = new Date(System.currentTimeMillis());
		this.numberOfIdentifiedEvents = 0;
		this.numberOfProcessedEvents = 0;
		
		List correlatedEventList = genericServiceSource.getAllObjects(new Correlatedevent());
		
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
		        
		        // create baseevent - just a copy for performance
		        // don't want to create statements across multiple dbs especially over 
		        // legacy db in an awfull slow vmware running ;-)
		        BaseEvent baseEvent = new BaseEvent();
				baseEvent.setId(correlatedEvent.getId());
				baseEvent.setGuid(correlatedEvent.getGuid());
				baseEvent.setDbtimeCreated(correlatedEvent.getDbtimeCreated());
				baseEvent.setEventXml(correlatedEvent.getEventXml());
				baseEvent.setEventtype("unidentified");
				
				if (parsedEvent == null)
					baseEvent.setEventtype("unidentified");
				else
					baseEvent.setEventtype(parsedEvent.getClass().getName());
				genericServiceTarget.save(baseEvent, baseEvent.getId());
				
				log.debug("### baseEvent " + baseEvent.getId());
				
		        // store event object
		        if (parsedEvent != null)
		        	this.storeParsedEvent(parsedEvent);
		        
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Saves mapped model 
	 *  
	 * @param parsedEvent mapped model
	 */
	private void storeParsedEvent(Object parsedEvent) {
		log.debug("### identifiedEvents.indexOf: " + identifiedEvents.indexOf("OrderReceivedEvent"));
		if (parsedEvent.getClass().getName().equals("at.generic.eventmodel.OrderReceivedEvent")) {
			OrderReceivedEvent orderReceivedEvent = (OrderReceivedEvent) parsedEvent;
			genericServiceTarget.save(parsedEvent, new Long(orderReceivedEvent.getId()));
			this.numberOfIdentifiedEvents++;
		} else if (parsedEvent.getClass().getName().equals("at.generic.eventmodel.OrderConfirmedEvent")) {
			OrderConfirmedEvent orderConfirmedEvent = (OrderConfirmedEvent) parsedEvent;
			log.debug("### orderConfirmedEvent.getId() " + orderConfirmedEvent.getId());
			genericServiceTarget.save(parsedEvent, new Long(orderConfirmedEvent.getId()));
			this.numberOfIdentifiedEvents++;
		}
	}
	

	/**
	 * @return Returns the genericServiceSource.
	 */
	public GenericServiceDAO getGenericServiceSource() {
		return genericServiceSource;
	}

	/**
	 * @param genericServiceSource The genericServiceSource to set.
	 */
	public void setGenericServiceSource(GenericServiceDAO genericServiceSource) {
		this.genericServiceSource = genericServiceSource;
	}

	/**
	 * @return Returns the genericServiceTarget.
	 */
	public GenericServiceDAO getGenericServiceTarget() {
		return genericServiceTarget;
	}

	/**
	 * @param genericServiceTarget The genericServiceTarget to set.
	 */
	public void setGenericServiceTarget(GenericServiceDAO genericServiceTarget) {
		this.genericServiceTarget = genericServiceTarget;
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

	/**
	 * @return Returns the initDone.
	 */
	public boolean isInitDone() {
		return initDone;
	}

	/**
	 * @param initDone The initDone to set.
	 */
	public void setInitDone(boolean initDone) {
		this.initDone = initDone;
	}

	
	
	
}