package at.generic.event;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

public class BaseEvent {
	
	
	private URI eventype;
	
	private Map<String,String> Attributes = new HashMap<String,String>();
	private Map<String,String> CorrelationValues = new HashMap<String,String>();
	
	private String xmlEventString;
	private Document xmlEventStringDocument = null;
	
	
	public BaseEvent(URI typeUri)
	{
		this.eventype = typeUri;
		
		
	}
	
	/**
	 * returns the StringValue of a TextNode selected through the XPathExpression.
	 * returns null if XPathExpression does not select a node.
	 * returns empty string if selected node has no text.
	 * 
	 * @param XPathExpression XPath expression, that should select single node
	 * @return
	 */
	public String selectNodeText(String XPathExpression)
	{
		if (xmlEventStringDocument == null)
		{
			try {
				xmlEventStringDocument = DocumentHelper.parseText(xmlEventString);
			} catch (DocumentException e) {
				//error should not happen around here! Because xmlEventString already was tested when creating BaseEvent in Transformer
			}
		}
		Node result =  xmlEventStringDocument.selectSingleNode(XPathExpression);
		if ((result != null))
			return result.getText();
		else
			return null;
	}
	/**
	 * Returns  a List of Node instances or String instances depending on the XPath expression.
	 * @param XPathExpression
	 * @return
	 */
	
	public List selectXPathValues(String XPathExpression)
	{
		if (xmlEventStringDocument == null)
		{
			try {
				xmlEventStringDocument = DocumentHelper.parseText(xmlEventString);
			} catch (DocumentException e) {
				//error should not happen around here! Because xmlEventString already was tested when creating BaseEvent in Transformer
			}
		}
		return xmlEventStringDocument.selectNodes(XPathExpression);
		
		
	}
	
	
	
	
	public void addAttribute(String key, String value)
	{
		Attributes.put(key,value);
	}
	
	public String getAttribute (String key)
	{
		return Attributes.get(key);
	}
	
	public void addCorrelationValue(String key, String value)
	{
		this.CorrelationValues.put(key,value);
	}
	
	public String getCorrelationValue(String key)
	{
	 return this.CorrelationValues.get(key);	
	}

	public String getXmlEventString() {
		return xmlEventString;
	}

	public void setXmlEventString(String xmlEventString) {
		this.xmlEventString = xmlEventString;
	}

	public URI getEventype() {
		return eventype;
	}


	
	
	

}
