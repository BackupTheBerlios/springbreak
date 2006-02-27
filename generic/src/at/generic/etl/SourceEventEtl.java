package at.generic.etl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.dao.EventDAO;
import at.generic.dao.GenericServiceDAO;
import at.generic.service.AdminPersistenceService;
import at.generic.service.CorrelatingEventsPersistenceService;
import at.generic.service.EventHandling;
import at.generic.service.EventPersistenceService;

/**
 * @author szabolcs
 * @version $Id: SourceEventEtl.java,v 1.8 2006/02/27 14:57:57 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.8 $
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
	
	/**
	 * @return Returns the eventPersistenceService.
	 */
	public EventPersistenceService getEventPersistenceService();

	/**
	 * @param eventPersistenceService The eventPersistenceService to set.
	 */
	public void setEventPersistenceService(EventPersistenceService eventPersistenceService);
	
	/**
	 * @return Returns the corrEventsPersistenceService.
	 */
	public CorrelatingEventsPersistenceService getCorrEventsPersistenceService();

	/**
	 * @param corrEventsPersistenceService The corrEventsPersistenceService to set.
	 */
	public void setCorrEventsPersistenceService(
			CorrelatingEventsPersistenceService corrEventsPersistenceService);
	
	/**
	 * @return Returns the adminPersistenceService.
	 */
	public AdminPersistenceService getAdminPersistenceService();

	/**
	 * @param adminPersistenceService The adminPersistenceService to set.
	 */
	public void setAdminPersistenceService(
			AdminPersistenceService adminPersistenceService);
}