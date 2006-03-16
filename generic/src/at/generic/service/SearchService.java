package at.generic.service;

import java.util.HashMap;

import at.generic.search.resultmodel.CorrResultModel;

/**
 * @author szabolcs
 * @version $Id: SearchService.java,v 1.5 2006/03/16 11:11:29 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.5 $
 * 
 * Search service
 * 
 */
public interface SearchService {
	
	/**
	 * Searches Index of correlating sets for a given query
	 * 
	 * @param searchString
	 * @param page
	 * @param exactMatch
	 * @return CorrResultModel
	 */
	public CorrResultModel searchForCorrEvents(String searchString, int page, boolean exactMatch);
	
	/**
	 * Searches Index of correlated events and expands the search queries by given criteria
	 * 
	 * @param searchString
	 * @param page
	 * @param foundEventtypes HashMap
	 * @return CorrResultModel
	 */
	public CorrResultModel searchForCorrEvents(String searchString, int page, boolean exactMatch, HashMap foundEventtypes);
	
	/**
	 * Search for Correlated context of an event
	 * 
	 * @param eventid
	 * @param page
	 * @param foundEventtypes
	 * @return CorrResultModel
	 */
	public CorrResultModel searchCorrContext(Long eventid, int page, HashMap foundEventtypes);
	
	/**
	 * Searches Index of events and expands the search queries by given criteria
	 * 
	 * @param searchString
	 * @param page
	 * @param foundEventtypes HashMap
	 * @param lowerBound
     * @param upperBound
	 * @return CorrResultModel
	 */
	public CorrResultModel searchForEvents(String searchString, int page, HashMap foundEventtypes, String lowerBound, String upperBound);
	
	/**
	 * Search index for matching events
	 * 
	 * @param searchString
	 * @param page
	 * @return
	 */
	public CorrResultModel searchForEvents(String searchString, int page, String lowerBound, String upperBound);
	
	
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