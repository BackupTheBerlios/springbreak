package at.generic.indexing;

import at.generic.event.BaseEvent;

public interface ICorrelationService {
	
	public void storeAndComplexIndex(BaseEvent event);

}
