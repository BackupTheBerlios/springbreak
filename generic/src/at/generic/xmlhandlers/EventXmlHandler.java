package at.generic.xmlhandlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import at.generic.eventmodel.OrderReceivedEvent;
import at.generic.eventmodel.ProductCollection;
import at.generic.model.Correlatedevent;

/**
 * @author szabolcs
 * @version $Id: EventXmlHandler.java,v 1.1 2005/12/14 22:15:14 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Parses the OrderReceived Event and returns the corresponding POJO with the
 * attached raw model
 */
public class EventXmlHandler {
	private static Log log = LogFactory.getLog(EventXmlHandler.class);
	
	public Object handleEvent(Correlatedevent correlatedEvent, String eventType) {
		if (eventType.equals("OrderReceived")) {
			return this.handleOrderReceivedEvent(correlatedEvent);
		}
		return null;
	}
	
	private OrderReceivedEvent handleOrderReceivedEvent(Correlatedevent correlatedEvent) {
		String xmlString = correlatedEvent.getEventXml();
		try {
			Digester digester = new Digester();
			digester.setValidating(false);
			
			StringBuffer stringBuffer = new StringBuffer(xmlString);
			ByteArrayInputStream xmlStream = new ByteArrayInputStream(stringBuffer.toString().getBytes("UTF-8"));
			
			digester.addObjectCreate("OrderReceived", OrderReceivedEvent.class );
			digester.addBeanPropertySetter("OrderReceived/OrderId", "orderId");
			digester.addBeanPropertySetter("OrderReceived/DateTime", "dateTime");
			digester.addBeanPropertySetter("OrderReceived/DeliveryDate", "deliveryDate");
			digester.addBeanPropertySetter("OrderReceived/Destination", "destination");
			
			digester.addObjectCreate("OrderReceived/ProductCollection", ProductCollection.class );
			digester.addBeanPropertySetter("OrderReceived/ProductCollection/Product/ProductId", "productId");
			digester.addBeanPropertySetter("OrderReceived/ProductCollection/Product/Amount", "amount");
			
			OrderReceivedEvent orderReceivedEvent = (OrderReceivedEvent)digester.parse( xmlStream );
			orderReceivedEvent.setCorrelatedEvent(correlatedEvent);
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
}	
	