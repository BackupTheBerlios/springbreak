package at.generic.etl.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import at.generic.dao.GenericServiceDAO;
import at.generic.etl.SourceEventEtl;
import at.generic.eventmodel.Dbinfo;
import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventattribute;
import at.generic.eventmodel.Eventtype;
import at.generic.eventmodel.Rwtime;
import at.generic.eventmodel.Txtime;
import at.generic.model.Correlatedevent;
import at.generic.service.EventHandling;
import at.generic.util.EventDate;

/**
 * @author szabolcs
 * @version $Id: GranSourceEvent.java,v 1.4 2006/02/02 19:41:33 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.4 $
 * 
 * Main File for the coordination of loading the events from the source and transforming
 * them into a warehouse like representation for further use.
 * 
 */
public class GranSourceEvent implements SourceEventEtl, Runnable {
	private static Log log = LogFactory.getLog(GranSourceEvent.class);
	
	private GenericServiceDAO genericServiceTarget;
	private GenericServiceDAO genericServiceSource;
	private EventHandling eventHandling;
	private java.util.Date etlThreadStartedAt;
	private Map identifiedEvents;
	private Map identifiedEventObjects;
	private int numberOfIdentifiedEvents;
	private int numberOfProcessedEvents;
	private boolean initDone;
	private boolean etlRunning;
	
	public void run() {
		log.debug("### etl running... ");
		
		if (etlRunning == false) {
			etlRunning = true;
			//new Transformthread().transformSourceEvents();
			Transformthread transformThread = new Transformthread();
			new Thread(transformThread).start();
		} 
	}
	
	public void stop() {
		log.debug("### etl stopped... ");
		etlRunning = false;
	}
	
	/**
	 * Should be executed init to get some infos about the amount of data that has been 
	 * transformed and how much is left... 
	 */
	public void getBasicInfos () {
		// go through database relations to count the initial numberOfIdentifiedEvents, 
		// numberOfPorcessedEvents and identifiedEvents
		if (initDone == false) {
			this.numberOfIdentifiedEvents = 0;
			this.numberOfProcessedEvents = 0;
			
			// get all Events from Source to determine the maximum size of events in the source db
			List correlatedEventList = genericServiceSource.getAllObjects(new Correlatedevent());
			
			this.numberOfIdentifiedEvents = eventHandling.getNumberOfIdentifiedEvents();
			this.numberOfProcessedEvents = correlatedEventList.size();
			
			initDone = true;
		} 
	}
	
	public void transformSourceEvents () {
		this.run();
	}
	
	public class Transformthread extends Thread {
		
		public void run() {
			log.debug("### Transformthread.start()");
			transformSourceEvents();
		}
		/**
		 * Main transformation service - coordinates all the work
		 */
		public void transformSourceEvents () {
			etlThreadStartedAt = new java.util.Date(System.currentTimeMillis());
			numberOfIdentifiedEvents = 0;
			numberOfProcessedEvents = 0;
			
			// create log data
			Dbinfo dbInfo = new Dbinfo();
			dbInfo.setUpdatestart(new java.util.Date(System.currentTimeMillis()).toGMTString());
			
			
			List correlatedEventList = genericServiceSource.getAllObjects(new Correlatedevent());
			
			Iterator i = correlatedEventList.iterator();
			while (i.hasNext()) {
				Correlatedevent correlatedEvent = (Correlatedevent) i.next();
				numberOfProcessedEvents++;
				
				// determine the xml type e.g.: OrderReceived,...
				StringBuffer stringBuffer = new StringBuffer(correlatedEvent.getEventXml());
				ByteArrayInputStream xmlStream = new ByteArrayInputStream(stringBuffer.toString().getBytes());
				
				try {
					SAXReader reader = new SAXReader();
			        Document event = reader.read(xmlStream);
			        
			        // retrieve Event Type xml location
			        String eventTypeLocation = 
			        	(String)identifiedEvents.get(event.getRootElement().getName());
			        
			        // parse Event Type xml if it is known
			        if (eventTypeLocation != null) {
				        Document eventType = parseXmlFile(eventTypeLocation);
				        transformEvent(event, eventType, correlatedEvent);
			        }
			        
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
			
			// determine number of identified items
			numberOfIdentifiedEvents = eventHandling.getNumberOfIdentifiedEvents();
			
			dbInfo.setUpdatestop(new java.util.Date(System.currentTimeMillis()).toGMTString());
			dbInfo.setProcesseditems(new Short((short)numberOfIdentifiedEvents));
			genericServiceTarget.saveWithoutCheck(dbInfo);
			etlRunning = false;
		}
	}
	
	
	
	/**
	 * Iterates through the XML File and creates the corrseponding models 
	 * 
	 * @param event from the database
	 * @param eventType definiton of the occured event
	 * @param correlatedEvent Correlated Event
	 */
	private void transformEvent(Document event, Document eventType, Correlatedevent correlatedEvent) {
		Element root = event.getRootElement();
		log.debug("### ------------------------------------------------------- ");
		log.debug("### root element name: " + event.getRootElement().getName());
		// extract event header attributes and store them
		// guid, originalGuid, priority,... 
		/*
		log.debug("### guid: " + event.selectSingleNode("/" + root.getName()).valueOf("@guid"));
		log.debug("### originalGuid: " + event.selectSingleNode("/" + root.getName()).valueOf("@originalGuid"));
		log.debug("### priority: " + event.selectSingleNode("/" + root.getName()).valueOf("@priority"));
		log.debug("### severity: " + event.selectSingleNode("/" + root.getName()).valueOf("@severity"));
		log.debug("### localTimeCreated: " + event.selectSingleNode("/" + root.getName()).valueOf("@localTimeCreated"));
		log.debug("### localTimeCreatedRW: " + event.selectSingleNode("/" + root.getName()).valueOf("@localTimeCreatedRW"));
		log.debug("### utcTimeCreated: " + event.selectSingleNode("/" + root.getName()).valueOf("@utcTimeCreated"));
		log.debug("### utcTimeCreatedRW: " + event.selectSingleNode("/" + root.getName()).valueOf("@utcTimeCreatedRW"));
		log.debug("### majorVersion: " + event.selectSingleNode("/" + root.getName()).valueOf("@majorVersion"));
		log.debug("### minorVersion: " + event.selectSingleNode("/" + root.getName()).valueOf("@minorVersion"));
		*/
		
		// store event data
		Event eventStore = new Event();
		eventStore.setEventid(new Long(correlatedEvent.getId().longValue()));
		eventStore.setXmlcontent(correlatedEvent.getEventXml());
		eventStore.setGuid(event.selectSingleNode("/" + root.getName()).valueOf("@originalGuid"));
		eventStore.setPriority(event.selectSingleNode("/" + root.getName()).valueOf("@priority"));
		eventStore.setLocaltimeid(new EventDate(event.selectSingleNode("/" + root.getName()).valueOf("@localTimeCreated")).getDateFormat());
		eventStore.setUtctimeid(new EventDate(event.selectSingleNode("/" + root.getName()).valueOf("@utcTimeCreated")).getDateFormat());
		eventStore.setLocalrwtimeid(new EventDate(event.selectSingleNode("/" + root.getName()).valueOf("@localTimeCreatedRW")).getDateFormat());
		eventStore.setUtcrwtimeid(new EventDate(event.selectSingleNode("/" + root.getName()).valueOf("@utcTimeCreatedRW")).getDateFormat());
		
		// determine eventtypes and store them
		Eventtype eventTypeObj = new Eventtype();
		eventTypeObj.setEventname(event.getRootElement().getName());
		eventHandling.storeEventtype(eventTypeObj);
		
		// retrieve id and save it in event object
		Integer eventTypeId = eventHandling.getEventtypeIdByName(event.getRootElement().getName());
		
		// if id has been found save eventtypeid
		if (eventTypeId.intValue() != -1)
			eventStore.setEventtypeid(eventTypeId);
		
		// store decomposed dates
		Rwtime rwTime = new Rwtime();
		EventDate rwTimeDate = new EventDate(event.selectSingleNode("/" + root.getName()).valueOf("@localTimeCreatedRW"));
		rwTime.setRwday(new Short((short)rwTimeDate.getDay()));
		rwTime.setRwmonth(new Short((short)rwTimeDate.getMonth()));
		rwTime.setRwyear(new Short((short)rwTimeDate.getYear()));
		eventHandling.storeRwTime(rwTime);
		
		Integer rwTimeId = eventHandling.getRwTimeIdByDates(rwTimeDate.getDay(), rwTimeDate.getMonth(), rwTimeDate.getYear());
		log.debug("### rwTimeId: " + rwTimeId);
		if (rwTimeId.intValue() != -1)
			eventStore.setRwtimeid(rwTimeId);
		
		Txtime txTime = new Txtime();
		EventDate txTimeDate = new EventDate(event.selectSingleNode("/" + root.getName()).valueOf("@utcTimeCreatedRW"));
		txTime.setTxday(new Short((short)txTimeDate.getDay()));
		txTime.setTxmonth(new Short((short)txTimeDate.getMonth()));
		txTime.setTxyear(new Short((short)txTimeDate.getYear()));
		eventHandling.storeTxTime(txTime);
		
		Integer txTimeId = eventHandling.getTxTimeIdByDates(txTimeDate.getDay(), txTimeDate.getMonth(), txTimeDate.getYear());
		log.debug("### txTimeId: " + txTimeId);
		if (txTimeId.intValue() != -1)
			eventStore.setTxtimeid(txTimeId);
		
		// save event
		genericServiceTarget.save(eventStore, eventStore.getEventid());
		
		// iterate through the top level elements
		// map them on event attribute facts and EventObjectAttributes
		for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            log.debug("### element name: " + element.getName());
            log.debug("### element value:**" + element.getText() + "**");
            
            // create event attribute            
            Eventattribute eventAttrib = new Eventattribute();
            if (!element.getText().equals("")) {
            	log.debug("### in: ");
	    		eventAttrib.setEventid(new Long(correlatedEvent.getId().longValue()));
	    		eventAttrib.setAttributename(element.getName());
	    		eventAttrib.setValue(element.getText());
	    		eventAttrib.setXmluri(element.getUniquePath());
	    		eventAttrib.setDatatype(this.getDataTypeForEventAttribute(element.getName(), eventType));
	    		
	    		// save event attribute
	    		eventHandling.storeEventAttribute(eventAttrib);
            }
    		
            if (element.getText() == null || element.getText() == "") {
            	for ( Iterator i1 = element.elementIterator(); i1.hasNext(); ) {
            		Element el = (Element) i1.next();
            		log.debug("### element name in 2nd level: " + el.getName());
            		
            		for ( Iterator i2 = el.elementIterator(); i2.hasNext(); ) {
                		Element el2 = (Element) i2.next();
                		
                		// store this value as EventAttribute
                		log.debug("### element name in 3rd level: " + el2.getName());
	                		// create event attribute
	                		eventAttrib = new Eventattribute();
	                		eventAttrib.setEventid(new Long(correlatedEvent.getId().longValue()));
	                		eventAttrib.setAttributename(el2.getName());
	                		eventAttrib.setValue(el2.getText());
	                		eventAttrib.setXmluri(el2.getUniquePath());
	                		
	                		// retrieve Event Type xml location
	                		String eventTypeObjectLocation = (String)this.identifiedEventObjects.get(el.getName());
	                		try {
		                		if (eventTypeObjectLocation != null) {
		                			Document eventTypeObject = this.parseXmlFile(eventTypeObjectLocation);
		                			eventAttrib.setDatatype(this.getDataTypeForEventAttributeObject(el2.getName(), eventTypeObject));
		            	        }
	                		} catch (DocumentException e) {
	                			e.printStackTrace();
	                		
	                		// save event attribute
	                		eventHandling.storeEventAttribute(eventAttrib);
                		 }
            		}
   				}
            }
            
    		// store this value as EventAttribute
            log.debug("### data type for event attribute: " + this.getDataTypeForEventAttribute(element.getName(), eventType));
        }
		
	}
	
	/**
	 * Parsers a XML file from a given location and returns a document
	 * 
	 * @param file
	 * @return
	 * @throws DocumentException
	 */
	private Document parseXmlFile(String location) throws DocumentException {
    	File file = new File(location);
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
    }
    
    /**
     * Returns the data type for an Event Attribute 
     * 
     * @param eventAttribute
     * @param document
     * @return
     */
	private String getDataTypeForEventAttribute(String eventAttribute, Document document) {
		log.debug("### getDataTypeForEventAttribute eventAttribute: " + eventAttribute);
		log.debug("### getDataTypeForEventAttribute document root name: " + document.getRootElement().getName());
    	Node node = document.selectSingleNode("/EventType/Attributes/Attribute[Name='" + eventAttribute.trim() + "']/DataType/RuntimeType");
    	return node.getText();
    }
	
	 /**
     * Returns the data type for an Event Attribute Object
     * 
     * @param eventAttribute
     * @param document
     * @return
     */
	private String getDataTypeForEventAttributeObject(String eventAttribute, Document document) {
		log.debug("### getDataTypeForEventAttribute eventAttribute: " + eventAttribute);
		log.debug("### getDataTypeForEventAttribute document root name: " + document.getRootElement().getName());
    	Node node = document.selectSingleNode("/MultipleDefinitions/EventObjectType/Attributes/Attribute[Name='" + eventAttribute.trim() + "']/DataType/RuntimeType");
    	return node.getText();
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
	public Map getIdentifiedEvents() {
		return identifiedEvents;
	}

	/**
	 * @param identifiedEvents The identifiedEvents to set.
	 */
	public void setIdentifiedEvents(Map identifiedEvents) {
		this.identifiedEvents = identifiedEvents;
	}

	/**
	 * @param numberOfProcessedEvents The numberOfProcessedEvents to set.
	 */
	public void setNumberOfProcessedEvents(int numberOfProcessedEvents) {
		this.numberOfProcessedEvents = numberOfProcessedEvents;
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

	/**
	 * @return Returns the identifiedEventObjects.
	 */
	public Map getIdentifiedEventObjects() {
		return identifiedEventObjects;
	}

	/**
	 * @param identifiedEventObjects The identifiedEventObjects to set.
	 */
	public void setIdentifiedEventObjects(Map identifiedEventObjects) {
		this.identifiedEventObjects = identifiedEventObjects;
	}

	/**
	 * @return Returns the etlRunning.
	 */
	public boolean isEtlRunning() {
		return etlRunning;
	}

	/**
	 * @param etlRunning The etlRunning to set.
	 */
	public void setEtlRunning(boolean etlRunning) {
		this.etlRunning = etlRunning;
	}

	/**
	 * @return Returns the etlThreadStartedAt.
	 */
	public java.util.Date getEtlThreadStartedAt() {
		return etlThreadStartedAt;
	}

	/**
	 * @param etlThreadStartedAt The etlThreadStartedAt to set.
	 */
	public void setEtlThreadStartedAt(java.util.Date etlThreadStartedAt) {
		this.etlThreadStartedAt = etlThreadStartedAt;
	}

	

}