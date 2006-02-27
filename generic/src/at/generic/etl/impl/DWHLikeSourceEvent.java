package at.generic.etl.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import at.generic.dao.GenericServiceDAO;
import at.generic.etl.SourceEventEtl;
import at.generic.eventmodel.BaseEvent;
import at.generic.eventmodel.OrderConfirmedEvent;
import at.generic.eventmodel.OrderReceivedEvent;
import at.generic.model.Correlatedevent;
import at.generic.xmlhandlers.EventXmlHandler;

/**
 * @author szabolcs
 * @version $Id: DWHLikeSourceEvent.java,v 1.2 2006/02/27 14:57:57 szabolcs Exp $
 * @deprecated
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * Main File for the coordination of loading the events from the source and transforming
 * them into a warehouse like representation for further use.
 * 
 */
public class DWHLikeSourceEvent {
	private static Log log = LogFactory.getLog(DWHLikeSourceEvent.class);
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
				//baseEvent.setEventXml(this.convertDocToPretty(document));
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
	 * Converts a given Document into a pretty printed String for
	 * 
	 * @param document
	 * @return
	 */
	private String convertDocToPretty(Document document) {
		try {
		
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("ISO-8859-15");
			StringWriter writer = new StringWriter();
			XMLWriter out = new XMLWriter(writer, format);
			out.write(document);
			return writer.toString();
		}
        catch(Exception e) {
        	log.debug(e.toString());
        }
        
        return null;
  
	}
	
	public void testnewway() {
		File file = new File("E:/jDev/generic/eventDefinitions/OrderReceivedEventType.xml");
		
		try {
			Document doc = this.parseXmlFile(file);
			this.transformEvent(doc);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Iterates through the XML File and creates the models 
	 * 
	 */
	public void transformEvent(Document doc) {
		Element root = doc.getRootElement();
		log.debug("### root element name: " + doc.getRootElement().getName());
		// extract event header attributes and store them
		// guid, originalGuid, priority,... 
		log.debug("### guid: " + doc.selectSingleNode("/" + root.getName()).valueOf("@guid"));
		log.debug("### originalGuid: " + doc.selectSingleNode("/" + root.getName()).valueOf("@originalGuid"));
		log.debug("### priority: " + doc.selectSingleNode("/" + root.getName()).valueOf("@priority"));
		log.debug("### severity: " + doc.selectSingleNode("/" + root.getName()).valueOf("@severity"));
		log.debug("### localTimeCreated: " + doc.selectSingleNode("/" + root.getName()).valueOf("@localTimeCreated"));
		log.debug("### localTimeCreatedRW: " + doc.selectSingleNode("/" + root.getName()).valueOf("@localTimeCreatedRW"));
		log.debug("### utcTimeCreated: " + doc.selectSingleNode("/" + root.getName()).valueOf("@utcTimeCreated"));
		log.debug("### utcTimeCreatedRW: " + doc.selectSingleNode("/" + root.getName()).valueOf("@utcTimeCreatedRW"));
		log.debug("### majorVersion: " + doc.selectSingleNode("/" + root.getName()).valueOf("@majorVersion"));
		log.debug("### minorVersion: " + doc.selectSingleNode("/" + root.getName()).valueOf("@minorVersion"));
		
		// iterate through the top level elements
		// map them on event attribute facts and EventObjectAttributes
		for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            log.debug("### element name: " + element.getName());
            log.debug("### element value: " + element.getText());
            log.debug("### data type for event attribute: " + this.getDataTypeForEventAttribute(element.getName(), doc));
            
			/*for ( Iterator i1 = element.elementIterator(); i1.hasNext(); ) {
				 Element el = (Element) i1.next();
		         log.debug("### element name in next leve: " + el.getName());
			}*/
        }
		
	}
	
	/**
	 * Parsers a XML file from a given location and returns a document
	 * 
	 * @param file
	 * @return
	 * @throws DocumentException
	 */
    public Document parseXmlFile(File file) throws DocumentException {
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
    public String getDataTypeForEventAttribute(String eventAttribute, Document document) {
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