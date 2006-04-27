package at.generic.jms;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import at.generic.event.BaseEvent;
import at.generic.event.IEventDefinitionResolver;
import at.generic.event.correlation.CorrelationData;
import at.generic.event.correlation.CorrelationTuple;

public class XML2EventTransformer implements IEventTransformer{

	

	private IEventDefinitionResolver eventDefinitionResolver;
	
	
	/* (non-Javadoc)
	 * @see at.generic.jms.IEventTransformer#transform(java.lang.Object)
	 */
	public BaseEvent transform(Object message) throws Exception {
		if (!(message instanceof String)) {
			throw new Exception("notString");
		}
		
		Document document = DocumentHelper.parseText((String) message);

		if ((document == null) || (!document.hasContent())) {
			throw new Exception("emptyDoc");
		}

		
			Element root = document.getRootElement();
			if (root.attributeValue(XML_ATTR_TYPEURI) == null) {
				throw new Exception("no TypeURI");
			}

			BaseEvent baseEvent = TransformFromXml(root, root.attributeValue(XML_ATTR_TYPEURI));
			
			//set the message - which should be the event as String - in baseEvent
			baseEvent.setXmlEventString((String)message);
			
			return baseEvent;
		

	}
	
	
	private BaseEvent TransformFromXml(Element node, String typeUri) throws Exception
	{
		
		URI uri = new URI (typeUri);
		
		boolean isEvent = (uri.getScheme().equals(SCHEME_EVENTTYPE)) ? true : false;

		BaseEvent retVal = null;
		if (isEvent)
		{
			retVal = new BaseEvent (uri);
			
		}
		else
		{
			throw new Exception ("SCHEME_EVENTTYPE wrong");
		}

		// extract attributes from node
		TransformNodeAttributesFromXml(node, retVal);

		//extract all CorrelationAttributes from node element (we know the correlation Attributes through theh typeUri)
		TransformCorrelationDataFromXml(node,uri,retVal);

		return retVal;
	}
	
	private void TransformNodeAttributesFromXml(Element node, BaseEvent retVal)
	{
		Iterator i = node.attributeIterator();
		Attribute a;
		while (i.hasNext())
		{
			a = (Attribute)i.next();
			retVal.addAttribute(a.getName(),a.getText());
		}	
	}
	
	private void TransformCorrelationDataFromXml(Element node, URI typeUri, BaseEvent retVal) throws Exception
	{
		
		List<CorrelationTuple> correlationInfo = eventDefinitionResolver.getCorrelationInfos(typeUri);
		
		
		for (CorrelationTuple ct : correlationInfo)
		{
			
			StringBuilder correlationValue = new StringBuilder();
			correlationValue.append(ct.getCorrelationName());
			
			List<CorrelationData> correlationdatas = ct.getCorrelationDatas();
			for (CorrelationData cd : correlationdatas)
			{
				Node corrData = node.selectSingleNode(cd.getXPathSelector());
				if (corrData == null)
				{
					throw new Exception ("CORRDATA NOT SET IN EVENT");
					
				}
				correlationValue.append(CORRELATIONDATA_SPLITTER);
				correlationValue.append(corrData.getText());
			}
			//TODO
			//may be we should not simplify to a STRING here, rather use Objectstructures
			retVal.addCorrelationValue(ct.getCorrelationName(),correlationValue.toString());
		}
		
		
		
		
	}


	public void setEventDefinitionResolver(
			IEventDefinitionResolver eventDefinitionResolver) {
		this.eventDefinitionResolver = eventDefinitionResolver;
	}
	

}
