package at.generic.etl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import at.generic.dao.GenericServiceDAO;
import at.generic.service.EventHandling;

/**
 * @author szabolcs
 * @version $Id: SourceEventEtl.java,v 1.7 2006/02/02 19:41:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.7 $
 * 
 * Main Interface for the coordination of loading the events from the source and transforming
 * them into a warehouse like representation for further use.
 * 
 */
public interface SourceEventEtl {
	/**
	 * Should be executed init to get some infos about the amount of data that has been 
	 * transformed and how much is left... 
	 */
	public void getBasicInfos ();
	
	/**
	 * Main transformation service - coordinates all the work
	 */
	public void transformSourceEvents ();
    
	/**
	 * @return Returns the genericServiceSource.
	 */
	public GenericServiceDAO getGenericServiceSource();

	/**
	 * @param genericServiceSource The genericServiceSource to set.
	 */
	public void setGenericServiceSource(GenericServiceDAO genericServiceSource);

	/**
	 * @return Returns the genericServiceTarget.
	 */
	public GenericServiceDAO getGenericServiceTarget();

	/**
	 * @param genericServiceTarget The genericServiceTarget to set.
	 */
	public void setGenericServiceTarget(GenericServiceDAO genericServiceTarget);

	/**
	 * @return Returns the identifiedEvents.
	 */
	public Map getIdentifiedEvents();

	/**
	 * @param identifiedEvents The identifiedEvents to set.
	 */
	public void setIdentifiedEvents(Map identifiedEvents);

	/**
	 * @param numberOfProcessedEvents The numberOfProcessedEvents to set.
	 */
	public void setNumberOfProcessedEvents(int numberOfProcessedEvents);

	/**
	 * @return Returns the numberOfIdentifiedEvents.
	 */
	public int getNumberOfIdentifiedEvents();

	/**
	 * @param numberOfIdentifiedEvents The numberOfIdentifiedEvents to set.
	 */
	public void setNumberOfIdentifiedEvents(int numberOfIdentifiedEvents);

	/**
	 * @return Returns the numberOfPorcessedEvents.
	 */
	public int getNumberOfProcessedEvents();

	/**
	 * @param numberOfPorcessedEvents The numberOfPorcessedEvents to set.
	 */
	public void setNumberOfPorcessedEvents(int numberOfProcessedEvents);

	/**
	 * @return Returns the initDone.
	 */
	public boolean isInitDone();

	/**
	 * @param initDone The initDone to set.
	 */
	public void setInitDone(boolean initDone);
	
	/**
	 * @return Returns the eventHandling.
	 */
	public EventHandling getEventHandling();

	/**
	 * @param eventHandling The eventHandling to set.
	 */
	public void setEventHandling(EventHandling eventHandling);
	
	/**
	 * @return Returns the identifiedEventObjects.
	 */
	public Map getIdentifiedEventObjects(); 

	/**
	 * @param identifiedEventObjects The identifiedEventObjects to set.
	 */
	public void setIdentifiedEventObjects(Map identifiedEventObjects); 
	
	/**
	 * @return Returns the etlRunning.
	 */
	public boolean isEtlRunning();

	/**
	 * @param etlRunning The etlRunning to set.
	 */
	public void setEtlRunning(boolean etlRunning);
	
	/**
	 * @return Returns the etlThreadStartedAt.
	 */
	public java.util.Date getEtlThreadStartedAt();

	/**
	 * @param etlThreadStartedAt The etlThreadStartedAt to set.
	 */
	public void setEtlThreadStartedAt(java.util.Date etlThreadStartedAt);
	
}