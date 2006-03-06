package at.generic.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventattribute;
	
/**
 * @author szabolcs
 * @version $Id: XMLUtils.java,v 1.3 2006/03/06 23:21:13 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.3 $
 * 
 * Misc utils for Event XMLs
 * 
 */
public class XMLUtils {
	private static Log log = LogFactory.getLog(XMLUtils.class);
	
	/** 
	 * Creates a pretty printing out of a string 
	 * 
	 * @param xml mixed up chars
	 * @return a pretty looking String ;-)
	 */
	public String convertDocToPretty(String xml) {
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
	 * Returns the Document object of a given XML String
	 *  
	 * @param rawXml xml string
	 * @return Document 
	 */
	public org.dom4j.Document convertXMLStringToDocument(String rawXml) {
		try {
			StringBuffer stringBuffer = new StringBuffer(rawXml);
			ByteArrayInputStream xmlStream = new ByteArrayInputStream(stringBuffer.toString().getBytes());
			
			SAXReader reader = new SAXReader();
	        return reader.read(xmlStream);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Parsers a XML file from a given location and returns a document
	 * 
	 * @param file
	 * @return
	 * @throws DocumentException
	 */
	public org.dom4j.Document parseXmlFile(String location) throws DocumentException {
    	File file = new File(location);
        SAXReader reader = new SAXReader();
        org.dom4j.Document document = reader.read(file);
        return document;
    }
	
	/**
	 * Creates an Eventattribute out of an Event unsing only the xml provided
	 * No datatype is set with this method
	 * 
	 * @param event
	 * @return List with Eventattributes
	 */
	public List extractAtrributesFromEvent(Event event) {
		 org.dom4j.Document corrEvent = new XMLUtils().convertXMLStringToDocument(event.getXmlcontent());
		 Element root = corrEvent.getRootElement();
		 
		 List eventAttribList = new Vector();
		 for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
	            Element element = (Element) i.next();
	            
	            Eventattribute eventAttrib = new Eventattribute();
	            if (!element.getText().trim().equals("")) {
		    		eventAttrib.setEventid(event.getEventid());
		    		eventAttrib.setAttributename(element.getName());
		    		eventAttrib.setValue(element.getText());
		    		eventAttrib.setXmluri(element.getUniquePath());
		    		
		    		// store event attribute
		    		eventAttribList.add(eventAttrib);
	            }
	            
	            
	            if (element.getText() == null || element.getText().trim().equals("")) {
	             	for ( Iterator i1 = element.elementIterator(); i1.hasNext(); ) {
	             		Element el = (Element) i1.next();
	             		
	             		for ( Iterator i2 = el.elementIterator(); i2.hasNext(); ) {
	                 		Element el2 = (Element) i2.next();
	                 		
	                 		// store this value as EventAttribute
	                		// create event attribute
	                		eventAttrib = new Eventattribute();
	                		eventAttrib.setEventid(event.getEventid());
	                		eventAttrib.setAttributename(el2.getName());
	                		eventAttrib.setValue(el2.getText());
	                		eventAttrib.setXmluri(el2.getUniquePath());
	                		
	                		// save event attribute
	                		eventAttribList.add(eventAttrib);
	                		
	             		}
	    			}
	             }
		 }
		 
       
	            
		 return eventAttribList;
	}
		 
		 
		 
		 
		 
		 
}