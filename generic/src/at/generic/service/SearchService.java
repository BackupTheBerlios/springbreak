package at.generic.service;

import at.generic.search.resultmodel.CorrResultModel;

/**
 * @author szabolcs
 * @version $Id: SearchService.java,v 1.2 2006/03/01 12:17:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * Search service
 * 
 */
public interface SearchService {
	
	/**
	 * Searches Index of correlating sets for a given query
	 * 
	 * @param searchString
	 * @return CorrResultModel
	 */
	public CorrResultModel searchForCorrEvents(String searchString);
	
	
	
	
	// ============== Getters and Setters =====================
	
	/**
	 * @return Returns the corrPersistenceService.
	 */
	public CorrelatingEventsPersistenceService getCorrPersistenceService();

	/**
	 * @param corrPersistenceService The corrPersistenceService to set.
	 */
	public void setCorrPersistenceService(CorrelatingEventsPersistenceService corrPersistenceService);
	/**
	 * @return Returns the eventPersistenceService.
	 */
	public EventPersistenceService getEventPersistenceService();

	/**
	 * @param eventPersistenceService The eventPersistenceService to set.
	 */
	public void setEventPersistenceService(EventPersistenceService eventPersistenceService);

	/**
	 * @return Returns the indexingServiceCorrEvents.
	 */
	public IndexingService getIndexingServiceCorrEvents();

	/**
	 * @param indexingServiceCorrEvents The indexingServiceCorrEvents to set.
	 */
	public void setIndexingServiceCorrEvents(IndexingService indexingServiceCorrEvents);
	
	/**
	 * @return Returns the maxSearchResults.
	 */
	public int getMaxSearchResults();

	/**
	 * @param maxSearchResults The maxSearchResults to set.
	 */
	public void setMaxSearchResults(int maxSearchResults);
	

	/**
	 * @return Returns the indexingServiceEvents.
	 */
	public IndexingService getIndexingServiceEvents();

	/**
	 * @param indexingServiceEvents The indexingServiceEvents to set.
	 */
	public void setIndexingServiceEvents(IndexingService indexingServiceEvents);
}