package at.generic.event;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import at.generic.event.correlation.CorrelationTuple;

public class EventDefinitionResolver implements IEventDefinitionResolver {
	
	/* (non-Javadoc)
	 * @see at.generic.event.IEventDefinitionResolver#getCorrelationInfos(java.net.URI)
	 */
	public List<CorrelationTuple> getCorrelationInfos(URI typeUri)
	{
		//TODO implement
		
		return new ArrayList<CorrelationTuple>();
		
	}

}
