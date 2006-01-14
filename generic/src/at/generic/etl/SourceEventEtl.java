package at.generic.etl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import at.generic.dao.GenericServiceDAO;

/**
 * @author szabolcs
 * @version $Id: SourceEventEtl.java,v 1.5 2006/01/14 19:42:54 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.5 $
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
	 * @return Returns the lastUpdate.
	 */
	public Date getLastUpdate();

	/**
	 * @param lastUpdate The lastUpdate to set.
	 */
	public void setLastUpdate(Date lastUpdate);

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
}