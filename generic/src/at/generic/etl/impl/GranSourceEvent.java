package at.generic.etl.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
import at.generic.model.Correlatedevent;

/**
 * @author szabolcs
 * @version $Id: GranSourceEvent.java,v 1.1 2006/01/14 19:42:54 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Main File for the coordination of loading the events from the source and transforming
 * them into a warehouse like representation for further use.
 * 
 */
public class GranSourceEvent implements SourceEventEtl {
	private static Log log = LogFactory.getLog(DWHLikeSourceEvent.class);
	private GenericServiceDAO genericServiceTarget;
	private GenericServiceDAO genericServiceSource;
	private Date lastUpdate;
	private Map identifiedEvents;
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
			
			// get all Events from Source to determine the maximum size of events in the source db
			List correlatedEventList = genericServiceSource.getAllObjects(new Correlatedevent());
			numberOfProcessedEvents = correlatedEventList.size();
			
			// TODO: implement numberOfIdentifiedEvents - count transformed Events

			initDone = true;
		} 
		
		
	}
	
	/**
	 * Main transformation service - coordinates all the work
	 */
	public void transformSourceEvents () {
		this.lastUpdate = new Date(System.currentTimeMillis());
		this.numberOfIdentifiedEvents = 0;
		this.numberOfProcessedEvents = 0;
		
		List correlatedEventList = genericServiceSource.getAllObjects(new Correlatedevent());
		
		Iterator i = correlatedEventList.iterator();
		while (i.hasNext()) {
			Correlatedevent correlatedEvent = (Correlatedevent) i.next();
			this.numberOfProcessedEvents++;
			
			// determine the xml type e.g.: OrderReceived,...
			StringBuffer stringBuffer = new StringBuffer(correlatedEvent.getEventXml());
			ByteArrayInputStream xmlStream = new ByteArrayInputStream(stringBuffer.toString().getBytes());
			
			try {
				SAXReader reader = new SAXReader();
		        Document event = reader.read(xmlStream);
		        
		        // retrieve Event Type xml location
		        String eventTypeLocation = 
		        	(String)this.identifiedEvents.get(event.getRootElement().getName());
		        
		        // parse Event Type xml if it is known
		        if (eventTypeLocation != null) {
			        Document eventType = this.parseXmlFile(eventTypeLocation);
			        this.transformEvent(event, eventType);
		        }
		        
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Iterates through the XML File and creates the corrseponding models 
	 * 
	 * @param event from the database
	 * @param eventType definiton of the occured event
	 */
	private void transformEvent(Document event, Document eventType) {
		Element root = event.getRootElement();
		log.debug("### ------------------------------------------------------- ");
		log.debug("### root element name: " + event.getRootElement().getName());
		// extract event header attributes and store them
		// guid, originalGuid, priority,... 
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
		
		// iterate through the top level elements
		// map them on event attribute facts and EventObjectAttributes
		for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            log.debug("### element name: " + element.getName());
            log.debug("### element value: " + element.getText());
            
            if (element.getText() == null || element.getText() == "") {
            	for ( Iterator i1 = element.elementIterator(); i1.hasNext(); ) {
            		Element el = (Element) i1.next();
            		log.debug("### element name in 2nd level: " + el.getName());
            		
            		for ( Iterator i2 = el.elementIterator(); i2.hasNext(); ) {
                		Element el2 = (Element) i2.next();
                		
                		// store this value as EventAttribute
                		log.debug("### element name in 3rd level: " + el2.getName());
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