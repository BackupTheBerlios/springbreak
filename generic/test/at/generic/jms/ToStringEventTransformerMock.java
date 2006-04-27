package at.generic.jms;

import at.generic.event.BaseEvent;
import at.generic.event.IEventDefinitionResolver;

public class ToStringEventTransformerMock implements IEventTransformer {

	public BaseEvent transform(Object message) throws Exception {
		
		BaseEvent b = new BaseEvent(null);
		b.setXmlEventString(message.toString());
		return b;
	}
	
	public void setEventDefinitionResolver(
			IEventDefinitionResolver eventDefinitionResolver) {
		
	}
	

}
