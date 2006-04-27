package at.generic.jms;

import at.generic.event.BaseEvent;
import at.generic.event.IEventDefinitionResolver;

public interface IEventTransformer {
	
	public static final String XML_ATTR_TYPEURI = "typeUri";
	public static final String CORRELATIONDATA_SPLITTER ="|@|";
	public static final String SCHEME_EVENTTYPE ="eventtype";

	public abstract BaseEvent transform(Object message) throws Exception;
	public abstract void setEventDefinitionResolver(IEventDefinitionResolver eventDefinitionResolver);

}