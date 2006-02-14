package at.generic.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
	
/**
 * @author szabolcs
 * @version $Id: XMLUtils.java,v 1.1 2006/02/14 10:10:00 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Misc utils for Event XMLs
 * 
 */
public class XMLUtils {
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
}