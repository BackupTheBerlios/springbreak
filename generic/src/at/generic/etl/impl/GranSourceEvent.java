package at.generic.etl.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

import at.generic.etl.SourceEventEtl;
import at.generic.eventmodel.Dbinfo;
import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventattribute;
import at.generic.eventmodel.Rwtime;
import at.generic.model.Correlatedevent;
import at.generic.model.Correlationset;
import at.generic.service.AdminPersistenceService;
import at.generic.service.CorrelatingEventsPersistenceService;
import at.generic.service.EventPersistenceService;
import at.generic.service.IndexingService;
import at.generic.util.EventDate;
import at.generic.util.XMLUtils;

/**
 * @author szabolcs
 * @version $Id: GranSourceEvent.java,v 1.10 2006/03/18 15:24:09 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.10 $
 * 
 * Main File for the coordination of loading the events from the source and transforming
 * them into a warehouse like representation for further use.
 * 
 */
public class GranSourceEvent implements SourceEventEtl, Runnable {
	private static Log log = LogFactory.getLog(GranSourceEvent.class);
	
	private java.util.Date etlThreadStartedAt;
	private Map identifiedEvents;
	private Map identifiedEventObjects;
	private int numberOfIdentifiedEvents;
	private int numberOfProcessedEvents;
	private boolean initDone;
	private boolean etlRunning;
	private CorrelatingEventsPersistenceService corrEventsPersistenceService;
	private EventPersistenceService eventPersistenceService;
	private AdminPersistenceService adminPersistenceService;
	private IndexingService indexingServiceEvents;
	private IndexingService indexingServiceCorrEvents;
	
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
			List correlatedEventList = eventPersistenceService.getCorrelatedevents();
			this.numberOfIdentifiedEvents = eventPersistenceService.getNumberOfIdentifiedEvents();
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
			//indexingServiceEvents.startBatchInsert();
			transformSourceEvents();
			//indexingServiceEvents.stopBatchInsert();
			
			//indexingServiceCorrEvents.startBatchInsert();
			extractCorrelatedEventsForIndex();
			//indexingServiceCorrEvents.stopBatchInsert();
		}
		
		public void extractCorrelatedEventsForIndex() {
			// retrieve correlationsets
			//List corrSetList = genericServiceSource.getAllObjects(new Correlationset());
			List corrSetList = corrEventsPersistenceService.getCorrelatedset();
			
			// iterate over correlationsets
			Iterator i = corrSetList.iterator();
			String guid = new String();
			String words = new String();
			String date = new String();
			String corrSetDef = new String();
			
			while (i.hasNext()) {
				Correlationset corrSet = (Correlationset) i.next();
				
				if (guid == null || guid.equals("")) 
					guid = corrSet.getCorrelationSetGuid();
				
				// if guid changed in loop it means that a new correlation is coming around
				// so save it to index
				if (!guid.equals(corrSet.getCorrelationSetGuid())) {
					log.debug("### guid: " + guid);
					log.debug("### words: " + words);
					log.debug("### corrSetDef: " + corrSetDef);
					log.debug("### date: " + date);
					indexingServiceCorrEvents.addDocument(guid,words,corrSetDef,date);
					guid = corrSet.getCorrelationSetGuid();
					date = new String();
					words = new String();
					corrSetDef = corrSet.getCorrelationSetDef();
					
					words = words + corrSetDef;
				} 
				
				words = words + corrSet.getEventType() + " ";
				
				// retrieve Event and Rwtime from Db
				Event event = eventPersistenceService.getEvent(corrSet.getEventid());
				Rwtime rwtime = eventPersistenceService.getRwtimeDAO().getRwtime(event.getRwtimeid());
				
				// extract rwtime and create a list of dates
				date = date +  " " + EventDate.getBoundFormatForLucene(rwtime.getRwday().intValue(), rwtime.getRwmonth().intValue(), rwtime.getRwyear().intValue());
				
				// retrieve event attributes 
				List attribsForEvent = eventPersistenceService.getEventattributesForEvent(corrSet.getEventid());
				// iterate over attributes
				log.debug("### corrSet.getEventid(): " + corrSet.getEventid());
				log.debug("### attribsForEvent.size(): " + attribsForEvent.size());
				Iterator iattrib = attribsForEvent.iterator();
				while (iattrib.hasNext()) {
					Eventattribute eventAttrib = (Eventattribute) iattrib.next();
					words = words + " " + eventAttrib.getValue();
					log.debug("### words = words + ' ' + eventAttrib.getValue()" + words);
				}
			}
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
			
			List correlatedEventList = corrEventsPersistenceService.getCorrelatedevents();
			
			Iterator i = correlatedEventList.iterator();
			while (i.hasNext()) {
				Correlatedevent correlatedEvent = (Correlatedevent) i.next();
				numberOfProcessedEvents++;
				
				// determine the xml type e.g.: OrderReceived,...
				try {
					Document corrEvent = new XMLUtils().convertXMLStringToDocument(correlatedEvent.getEventXml());
			        
			        // retrieve Event Type xml location
			        String eventTypeLocation = (String)identifiedEvents.get(corrEvent.getRootElement().getName());
			        
			        // parse Event Type xml if it is known
			        if (eventTypeLocation != null) {
				        Document eventType = new XMLUtils().parseXmlFile(eventTypeLocation);
				        transformEvent(corrEvent, eventType, correlatedEvent);
			        }
			        
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
			
			// determine number of identified items
			numberOfIdentifiedEvents = eventPersistenceService.getNumberOfIdentifiedEvents();
			
			dbInfo.setUpdatestop(new java.util.Date(System.currentTimeMillis()).toGMTString());
			dbInfo.setProcesseditems(new Short((short)numberOfIdentifiedEvents));
			adminPersistenceService.saveOrUpdateDbinfo(dbInfo);
			etlRunning = false;
		}
	}
	
	
	
	/**
	 * Iterates through the XML File and creates the corrseponding models 
	 * 
	 * @param eventType definiton of the occured event
	 * @param correlatedEvent Correlated Event
	 */
	private void transformEvent(Document corrEvent, Document eventType, Correlatedevent correlatedEvent) {
		eventPersistenceService.saveOrUpdateEvent(correlatedEvent);
		
		Element root = corrEvent.getRootElement();
		
		String indexText = root.getName() + " ";
		
		// iterate through the top level elements
		// map them on event attribute facts and EventObjectAttributes
		for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            //log.debug("### element name: " + element.getName());
            //log.debug("### element value:**" + element.getText() + "**");
            
            // create event attribute            
            Eventattribute eventAttrib = new Eventattribute();
            if (!element.getText().equals("")) {
            	//log.debug("### in: ");
	    		eventAttrib.setEventid(new Long(correlatedEvent.getId().longValue()));
	    		eventAttrib.setAttributename(element.getName());
	    		eventAttrib.setValue(element.getText());
	    		eventAttrib.setXmluri(element.getUniquePath());
	    		eventAttrib.setDatatype(this.getDataTypeForEventAttribute(element.getName(), eventType));
	    		
	    		// save event attribute
	    		eventPersistenceService.saveOrUpdateEventattribute(eventAttrib);
	    		
	    		// create index text list for event
	    		indexText = indexText + " " + eventAttrib.getValue();
            }
    		
            if (element.getText() == null || element.getText() == "") {
            	for ( Iterator i1 = element.elementIterator(); i1.hasNext(); ) {
            		Element el = (Element) i1.next();
            		//log.debug("### element name in 2nd level: " + el.getName());
            		
            		for ( Iterator i2 = el.elementIterator(); i2.hasNext(); ) {
                		Element el2 = (Element) i2.next();
                		
                		// store this value as EventAttribute
                		//log.debug("### element name in 3rd level: " + el2.getName());
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
		                			Document eventTypeObject = new XMLUtils().parseXmlFile(eventTypeObjectLocation);
		                			eventAttrib.setDatatype(this.getDataTypeForEventAttributeObject(el2.getName(), eventTypeObject));
		            	        }
	                		} catch (DocumentException e) {
	                			e.printStackTrace();
	                		}
	                		
	                		/*log.debug("### eventAttrib.getEventid: " + eventAttrib.getEventid());
	                		log.debug("### eventAttrib.getAttributename: " + eventAttrib.getAttributename());
	                		log.debug("### eventAttrib.getValue: " + eventAttrib.getValue());
	                		log.debug("### eventAttrib.getXmluri: " + eventAttrib.getXmluri());*/
	                		
	                		// save event attribute
	                		eventPersistenceService.saveOrUpdateEventattribute(eventAttrib);
	                		
	                		// create index text list for event
	        	    		indexText = indexText + " " + eventAttrib.getValue();
            		}
   				}
            }
            
    		// store this value as EventAttribute
            //log.debug("### data type for event attribute: " + this.getDataTypeForEventAttribute(element.getName(), eventType));
        }
		
		// add to index
		Event event = eventPersistenceService.getEvent(new Long(correlatedEvent.getId().longValue()));
		
		Rwtime rwtime = eventPersistenceService.getRwtimeDAO().getRwtime(event.getRwtimeid());
		
		String date  = EventDate.getBoundFormatForLucene(rwtime.getRwday().intValue(), rwtime.getRwmonth().intValue(), rwtime.getRwyear().intValue());

		/*date = rwtime.getRwyear().toString();
		if (rwtime.getRwmonth().intValue() < 10)
			date = date + "0" + rwtime.getRwmonth();
		else
			date = date + rwtime.getRwmonth();
		
		if (rwtime.getRwday().intValue() < 10)
			date = date + "0" + rwtime.getRwday();
		else
			date = date + rwtime.getRwday();*/
		
		/*
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		Date date = new Date();
		try {
			date = df.parse(rwtime.getRwyear().toString() + rwtime.getRwmonth().toString() + rwtime.getRwday().toString());
		} catch (ParseException e) {
			log.error("Something went wrong parsing the rwtime for indexing ", e);
		}*/
		indexingServiceEvents.addDocument(
				correlatedEvent.getId().toString(),
				indexText,
				eventPersistenceService.getEventtype(event.getEventtypeid()).getEventname(),
				date
			);
		
	}
	
    /**
     * Returns the data type for an Event Attribute 
     * 
     * @param eventAttribute
     * @param document
     * @return
     */
	private String getDataTypeForEventAttribute(String eventAttribute, Document document) {
		//log.debug("### getDataTypeForEventAttribute eventAttribute: " + eventAttribute);
		//log.debug("### getDataTypeForEventAttribute document root name: " + document.getRootElement().getName());
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
		//log.debug("### getDataTypeForEventAttribute eventAttribute: " + eventAttribute);
		//log.debug("### getDataTypeForEventAttribute document root name: " + document.getRootElement().getName());
    	Node node = document.selectSingleNode("/MultipleDefinitions/EventObjectType/Attributes/Attribute[Name='" + eventAttribute.trim() + "']/DataType/RuntimeType");
    	return node.getText();
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

	/**
	 * @return Returns the eventPersistenceService.
	 */
	public EventPersistenceService getEventPersistenceService() {
		return eventPersistenceService;
	}

	/**
	 * @param eventPersistenceService The eventPersistenceService to set.
	 */
	public void setEventPersistenceService(
			EventPersistenceService eventPersistenceService) {
		this.eventPersistenceService = eventPersistenceService;
	}

	/**
	 * @return Returns the corrEventsPersistenceService.
	 */
	public CorrelatingEventsPersistenceService getCorrEventsPersistenceService() {
		return corrEventsPersistenceService;
	}

	/**
	 * @param corrEventsPersistenceService The corrEventsPersistenceService to set.
	 */
	public void setCorrEventsPersistenceService(
			CorrelatingEventsPersistenceService corrEventsPersistenceService) {
		this.corrEventsPersistenceService = corrEventsPersistenceService;
	}

	/**
	 * @return Returns the adminPersistenceService.
	 */
	public AdminPersistenceService getAdminPersistenceService() {
		return adminPersistenceService;
	}

	/**
	 * @param adminPersistenceService The adminPersistenceService to set.
	 */
	public void setAdminPersistenceService(
			AdminPersistenceService adminPersistenceService) {
		this.adminPersistenceService = adminPersistenceService;
	}

	/**
	 * @return Returns the indexingServiceEvents.
	 */
	public IndexingService getIndexingServiceEvents() {
		return indexingServiceEvents;
	}

	/**
	 * @param indexingServiceEvents The indexingServiceEvents to set.
	 */
	public void setIndexingServiceEvents(IndexingService indexingServiceEvents) {
		this.indexingServiceEvents = indexingServiceEvents;
	}

	/**
	 * @return Returns the indexingServiceCorrEvents.
	 */
	public IndexingService getIndexingServiceCorrEvents() {
		return indexingServiceCorrEvents;
	}

	/**
	 * @param indexingServiceCorrEvents The indexingServiceCorrEvents to set.
	 */
	public void setIndexingServiceCorrEvents(
			IndexingService indexingServiceCorrEvents) {
		this.indexingServiceCorrEvents = indexingServiceCorrEvents;
	}

	
	
	
}