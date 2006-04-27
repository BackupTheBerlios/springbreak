package at.generic.event;

import java.net.URI;
import java.util.List;

import at.generic.event.correlation.CorrelationTuple;

public interface IEventDefinitionResolver {

	/* (non-Javadoc)
	 * @see at.generic.event.IEventDefinitionResolver#getCorrelationInfos(java.net.URI)
	 */
	public abstract List<CorrelationTuple> getCorrelationInfos(URI typeUri);

}