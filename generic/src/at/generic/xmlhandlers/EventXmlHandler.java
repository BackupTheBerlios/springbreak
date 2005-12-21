package at.generic.xmlhandlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import at.generic.eventmodel.OrderConfirmedEvent;
import at.generic.eventmodel.OrderReceivedEvent;
import at.generic.eventmodel.ProductCollection;
import at.generic.model.Correlatedevent;

/**
 * @author szabolcs
 * @version $Id: EventXmlHandler.java,v 1.3 2005/12/21 22:07:59 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.3 $
 * 
 * Parses the OrderReceived Event and returns the corresponding POJO with the
 * attached raw model
 */
public class EventXmlHandler {
	private static Log log = LogFactory.getLog(EventXmlHandler.class);
	
	public Object handleEvent(Correlatedevent correlatedEvent, String eventType) {
		if (eventType.equals("OrderReceived")) {
			return this.handleOrderReceivedEvent(correlatedEvent);
		} else if (eventType.equals("OrderConfirmed")) {
			return this.handleOrderConfirmedEvent(correlatedEvent);
		} 
		
		return null;
	}
	
	/**
	 * OrderReceived Handler
	 * 
	 * @param correlatedEvent
	 * @return
	 */
	private OrderReceivedEvent handleOrderReceivedEvent(Correlatedevent correlatedEvent) {
		String xmlString = correlatedEvent.getEventXml();
		try {
			Digester digesterOrderReceived = new Digester();
			digesterOrderReceived.setValidating(false);
			
			StringBuffer stringBuffer = new StringBuffer(xmlString);
			ByteArrayInputStream xmlStream = new ByteArrayInputStream(stringBuffer.toString().getBytes("UTF-8"));
			
			// mapping xml to OrderReceived
			digesterOrderReceived.addObjectCreate("OrderReceived", OrderReceivedEvent.class );
			digesterOrderReceived.addSetProperties("/", "guid", "guid");
			digesterOrderReceived.addSetProperties("OrderReceived/", "originalGuid", "originalGuid");
			digesterOrderReceived.addSetProperties("OrderReceived", "priority", "priority");
			digesterOrderReceived.addSetProperties("OrderReceived", "severity", "severity");
			digesterOrderReceived.addSetProperties("OrderReceived", "localTimeCreated", "localTimeCreated");
			digesterOrderReceived.addSetProperties("OrderReceived", "localTimeCreatedRW", "localTimeCreatedRW");
			digesterOrderReceived.addSetProperties("OrderReceived", "utcTimeCreated", "utcTimeCreated");
			digesterOrderReceived.addSetProperties("OrderReceived", "utcTimeCreatedRW", "utcTimeCreatedRW");
			digesterOrderReceived.addSetProperties("OrderReceived", "majorVersion", "majorVersion");
			digesterOrderReceived.addSetProperties("OrderReceived", "minorVersion", "minorVersion");
			
			digesterOrderReceived.addBeanPropertySetter("OrderReceived/OrderId", "orderid");
			digesterOrderReceived.addBeanPropertySetter("OrderReceived/DateTime", "datetime");
			digesterOrderReceived.addBeanPropertySetter("OrderReceived/DeliveryDate", "deliverydate");
			digesterOrderReceived.addBeanPropertySetter("OrderReceived/Destination", "destination");
			
			// mapping xml to ProductCollection
			digesterOrderReceived.addObjectCreate("OrderReceived/ProductCollection/Product", ProductCollection.class );
			digesterOrderReceived.addBeanPropertySetter("OrderReceived/ProductCollection/Product/Amount", "amount");
			digesterOrderReceived.addCallMethod( "OrderReceived/ProductCollection/Product", "addProductCollectionKey", 1 );
			digesterOrderReceived.addCallParam( "OrderReceived/ProductCollection/Product/ProductId", 0 );
			digesterOrderReceived.addSetNext( "OrderReceived/ProductCollection/Product", "addProductCollections" );
			
			//digesterOrderReceived.addObjectCreate("OrderReceived/ProductCollection/Product", ProductCollectionPK.class );
			//digesterOrderReceived.addBeanPropertySetter("OrderReceived/ProductCollection/Product/ProductId", "productId");
			//digesterOrderReceived.addSetNext( "OrderReceived/ProductCollection/Product", "setComp_id" );
			
			OrderReceivedEvent orderReceivedEvent = (OrderReceivedEvent)digesterOrderReceived.parse( xmlStream );
			
			
			// ------ doing some stuff by hand ------
			orderReceivedEvent.setId(correlatedEvent.getId().longValue());
			
			// TODO: Digester benutzen um die Attribute zu setzten.
			// im Moment mach ich ich es ganz Pfui Pfui weil ich nicht checke wie ich solche 
			// Objektverschachtelungen mit Digester mappen kann.
			Set pcSet = orderReceivedEvent.getProductcollections();
			HashSet newSet = new HashSet();
			Iterator i = pcSet.iterator();
			while (i.hasNext()) {
				ProductCollection pc = (ProductCollection) i.next();
				pc.getComp_id().setId(new Long(correlatedEvent.getId().longValue()));
				newSet.add(pc);
			}
			orderReceivedEvent.setProductcollections(newSet);
			
			return orderReceivedEvent;
			
		} catch (UnsupportedEncodingException e1) {
			log.info("input xml is not utf-8 conform");
			e1.printStackTrace();
		} catch (SAXException e2) {
			e2.printStackTrace();
		} catch (IOException e3) {
			e3.printStackTrace();
		} 
		
		return null;
	}
	
	/**
	 * OrderConfirmed Handler
	 * 
	 * @param correlatedEvent
	 * @return
	 */
	private OrderConfirmedEvent handleOrderConfirmedEvent(Correlatedevent correlatedEvent) {
		String xmlString = correlatedEvent.getEventXml();
		try {
			Digester digester = new Digester();
			digester.setValidating(false);
			
			StringBuffer stringBuffer = new StringBuffer(xmlString);
			ByteArrayInputStream xmlStream = new ByteArrayInputStream(stringBuffer.toString().getBytes("UTF-8"));
			
			// mapping xml to OrderConfirmed
			digester.addObjectCreate("OrderConfirmed", OrderConfirmedEvent.class );
			digester.addSetProperties("/", "guid", "guid");
			digester.addSetProperties("OrderConfirmed/", "originalGuid", "originalGuid");
			digester.addSetProperties("OrderConfirmed", "priority", "priority");
			digester.addSetProperties("OrderConfirmed", "severity", "severity");
			digester.addSetProperties("OrderConfirmed", "localTimeCreated", "localTimeCreated");
			digester.addSetProperties("OrderConfirmed", "localTimeCreatedRW", "localTimeCreatedRW");
			digester.addSetProperties("OrderConfirmed", "utcTimeCreated", "utcTimeCreated");
			digester.addSetProperties("OrderConfirmed", "utcTimeCreatedRW", "utcTimeCreatedRW");
			digester.addSetProperties("OrderConfirmed", "majorVersion", "majorVersion");
			digester.addSetProperties("OrderConfirmed", "minorVersion", "minorVersion");
			
			digester.addBeanPropertySetter("OrderConfirmed/OrderId", "orderid");
			digester.addBeanPropertySetter("OrderConfirmed/DateTime", "datetime");
			
			OrderConfirmedEvent orderConfirmedEvent = (OrderConfirmedEvent)digester.parse( xmlStream );
			
			
			// ------ doing some stuff by hand ------
			orderConfirmedEvent.setId(correlatedEvent.getId().longValue());
			
			return orderConfirmedEvent;
			
		} catch (UnsupportedEncodingException e1) {
			log.info("input xml is not utf-8 conform");
			e1.printStackTrace();
		} catch (SAXException e2) {
			e2.printStackTrace();
		} catch (IOException e3) {
			e3.printStackTrace();
		} 
		
		return null;
	}
}	
	