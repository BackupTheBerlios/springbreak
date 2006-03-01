package at.generic.search.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.generic.eventmodel.Event;
import at.generic.model.Correlationset;
import at.generic.search.resultmodel.CorrResultModel;
import at.generic.search.resultmodel.EventAgg;
import at.generic.search.resultmodel.FoundCorrSet;
import at.generic.service.CorrelatingEventsPersistenceService;
import at.generic.service.EventPersistenceService;
import at.generic.service.IndexingService;
import at.generic.service.SearchService;

/**
 * @author szabolcs
 * @version $Id: SearchServiceImpl.java,v 1.2 2006/03/01 11:44:52 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * Search service
 * 
 * 
 */
public class SearchServiceImpl implements SearchService {
	private static Log log = LogFactory.getLog(SearchServiceImpl.class);
	
	private IndexingService indexingServiceCorrEvents;
	private IndexingService indexingServiceEvents;
	private EventPersistenceService eventPersistenceService;
	private CorrelatingEventsPersistenceService corrPersistenceService;
	
	private int maxSearchResults; 
	
	/**
	 * Searches Index of correlating sets for a given query
	 * 
	 * @param searchString
	 * @return CorrResultModel
	 */
	public CorrResultModel searchForCorrEvents(String searchString) {
		int numberOfResults = 0;
		long start = new Date().getTime();
		
		Vector wids = indexingServiceCorrEvents.search(searchString, maxSearchResults);
		
		// go through the guid list provided by the search
		List foundCorrSetList = new Vector();
		Iterator widIt = wids.iterator();
		while (widIt.hasNext()) {
			String guid = (String) widIt.next();
			numberOfResults++;
			
			// get Correlatedsets with guid
			List corrEventList = corrPersistenceService.getCorrelatedsetByGuid(guid);
			
			List eventAggList = new Vector();
			List widList = new Vector();
			String correlationSetDef = new String();
			// go through the correlated sets and retrieve the events 
			Iterator eventIt = corrEventList.iterator();
			while (eventIt.hasNext()) {
				Correlationset corrSet = (Correlationset)eventIt.next();
				
				correlationSetDef = corrSet.getCorrelationSetDef();
				
				// get the event and its attributes
				Event event = eventPersistenceService.getEvent(corrSet.getEventid());
				widList.add(event.getEventid());
				List eventAttribs = eventPersistenceService.getEventattributesForEvent(event.getEventid());
				
				EventAgg eventAgg = new EventAgg();
				eventAgg.setEvent(event);
				eventAgg.setEventAttributes(eventAttribs);
				eventAgg.setEventTypeName(corrSet.getEventType());
				
				eventAggList.add(eventAgg);
			}
			
			// search inside events to generate a score
			
			Vector eventIds = indexingServiceEvents.search(searchString,maxSearchResults, widList);
			Iterator eventIdsAfterSearchIt = eventIds.iterator();
			while (eventIdsAfterSearchIt.hasNext()) {
				String eventIdsStr = (String)eventIdsAfterSearchIt.next();
				log.debug("### eventIdsStr " + eventIdsStr);
			}
			
			FoundCorrSet foundCorrSet = new FoundCorrSet();
			foundCorrSet.setGuid(guid);
			foundCorrSet.setEventAgg(eventAggList);
			foundCorrSet.setCorrelationSetDef(correlationSetDef);
			
			foundCorrSetList.add(foundCorrSet);
		}
		
		long end = new Date().getTime();
		long duration = end - start;
		
		
		CorrResultModel corrModel = new CorrResultModel();
		corrModel.setFoundCorrSet(foundCorrSetList);
		corrModel.setQueryTime(Long.toString(duration));
		corrModel.setNumberOfResults(numberOfResults);
		corrModel.setSearchString(searchString);
		
		return corrModel;
	}
	
	// =========== Getters and Setters ========================

	/**
	 * @return Returns the corrPersistenceService.
	 */
	public CorrelatingEventsPersistenceService getCorrPersistenceService() {
		return corrPersistenceService;
	}

	/**
	 * @param corrPersistenceService The corrPersistenceService to set.
	 */
	public void setCorrPersistenceService(
			CorrelatingEventsPersistenceService corrPersistenceService) {
		this.corrPersistenceService = corrPersistenceService;
	}

	/**
	 * @return Returns the eventPersistenceService.
	 */
	public EventPersistenceService getEventPersistenceService() {
		return eventPersistenceService;
	}

	/**
	 * @param eventPersistenceService The eventPersistenceService to set.
	 */
	public void setEventPersistenceService(
			EventPersistenceService eventPersistenceService) {
		this.eventPersistenceService = eventPersistenceService;
	}

	/**
	 * @return Returns the indexingServiceCorrEvents.
	 */
	public IndexingService getIndexingServiceCorrEvents() {
		return indexingServiceCorrEvents;
	}

	/**
	 * @param indexingServiceCorrEvents The indexingServiceCorrEvents to set.
	 */
	public void setIndexingServiceCorrEvents(
			IndexingService indexingServiceCorrEvents) {
		this.indexingServiceCorrEvents = indexingServiceCorrEvents;
	}

	/**
	 * @return Returns the maxSearchResults.
	 */
	public int getMaxSearchResults() {
		return maxSearchResults;
	}

	/**
	 * @param maxSearchResults The maxSearchResults to set.
	 */
	public void setMaxSearchResults(int maxSearchResults) {
		this.maxSearchResults = maxSearchResults;
	}

	/**
	 * @return Returns the indexingServiceEvents.
	 */
	public IndexingService getIndexingServiceEvents() {
		return indexingServiceEvents;
	}

	/**
	 * @param indexingServiceEvents The indexingServiceEvents to set.
	 */
	public void setIndexingServiceEvents(IndexingService indexingServiceEvents) {
		this.indexingServiceEvents = indexingServiceEvents;
	}
	
	
	
}