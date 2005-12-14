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

/**
 * @author szabolcs
 * @version $Id: OrderReceivedXmlHandler.java,v 1.1 2005/12/14 22:15:14 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Parses the OrderReceived Event and 
 */
public class OrderReceivedXmlHandler {
	private static Log log = LogFactory.getLog(OrderReceivedXmlHandler.class);
	
	private Digester digester;
	private OrderReceivedEvent orderReceivedEvent;
	
	public OrderReceivedXmlHandler(String xmlString) {
		try {
			StringBuffer stringBuffer = new StringBuffer(xmlString);
			ByteArrayInputStream xmlStream = new ByteArrayInputStream(stringBuffer.toString().getBytes("UTF-8"));
			
			digester = new Digester();
			digester.setValidating(false);
			
			digester.addObjectCreate("OrderReceived", OrderReceivedEvent.class );
			digester.addBeanPropertySetter("OrderReceived/OrderId", "orderId");
			digester.addBeanPropertySetter("OrderReceived/DateTime", "dateTime");
			digester.addBeanPropertySetter("OrderReceived/DeliveryDate", "deliveryDate");
			digester.addBeanPropertySetter("OrderReceived/Destination", "destination");
			
			digester.addObjectCreate("OrderReceived/ProductCollection", ProductCollection.class );
			digester.addBeanPropertySetter("OrderReceived/ProductCollection/Product/ProductId", "productId");
			digester.addBeanPropertySetter("OrderReceived/ProductCollection/Product/Amount", "amount");
			
			orderReceivedEvent = (OrderReceivedEvent)digester.parse( xmlStream );
		} catch (UnsupportedEncodingException e1) {
			log.info("input xml is not utf-8 conform");
			e1.printStackTrace();
		} catch (SAXException e2) {
			e2.printStackTrace();
		} catch (IOException e3) {
			e3.printStackTrace();
		} 
	}

	/**
	 * @return Returns the orderReceivedEvent.
	 */
	public OrderReceivedEvent getOrderReceivedEvent() {
		return orderReceivedEvent;
	}

	/**
	 * @param orderReceivedEvent The orderReceivedEvent to set.
	 */
	public void setOrderReceivedEvent(OrderReceivedEvent orderReceivedEvent) {
		this.orderReceivedEvent = orderReceivedEvent;
	}
	
	
}