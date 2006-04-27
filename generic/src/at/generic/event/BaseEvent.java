package at.generic.event;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class BaseEvent {
	
	
	private URI eventype;
	
	private Map<String,String> Attributes = new HashMap<String,String>();
	private Map<String,String> CorrelationValues = new HashMap<String,String>();
	
	private String xmlEventString;
	
	
	public BaseEvent(URI typeUri)
	{
		this.eventype = typeUri;
		
		
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
