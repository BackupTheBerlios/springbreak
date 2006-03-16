package at.generic.search.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;

import at.generic.eventmodel.Event;
import at.generic.model.Correlationset;
import at.generic.search.resultmodel.CorrResultModel;
import at.generic.search.resultmodel.EventAgg;
import at.generic.search.resultmodel.FoundCorrSet;
import at.generic.service.CorrelatingEventsPersistenceService;
import at.generic.service.EventPersistenceService;
import at.generic.service.IndexingService;
import at.generic.service.SearchService;
import at.generic.util.XMLUtils;

/**
 * @author szabolcs
 * @version $Id: SearchServiceImpl.java,v 1.7 2006/03/16 11:11:29 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.7 $
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
	
	private int maxSearchResults; // number of items to be displayed
	
	/**
	 * Searches Index of correlating sets for a given query
	 * 
	 * @param searchString
	 * @param page
	 * @param exactMatch
	 * @return CorrResultModel
	 */
	public CorrResultModel searchForCorrEvents(String searchString, int page, boolean exactMatch) {
		
		long start = new Date().getTime();
		
		Vector wids = indexingServiceCorrEvents.search(searchString, maxSearchResults, page, new String(), new String());		// **** Search

		int numberOfResults = 0;
		// go through the guid list provided by the search
		List foundCorrSetList = new Vector();
		Iterator widIt = wids.iterator();
		while (widIt.hasNext()) {
			String guid = (String) widIt.next();
			
			// get Correlatedsets with guid
			List corrEventList = corrPersistenceService.getCorrelatedsetByGuid(guid);	// ***** DB Access
			
			List widList = new Vector();
			String correlationSetDef = new String();
			// go through the correlated sets and retrieve the events 
			
			// create list with eventids
			List eventIdList = new Vector();
			HashMap eventTypeList = new HashMap();
			Iterator eventIt = corrEventList.iterator();
			while (eventIt.hasNext()) {
				Correlationset corrSet = (Correlationset)eventIt.next();
				correlationSetDef = corrSet.getCorrelationSetDef();
				eventTypeList.put(corrSet.getEventid(), corrSet.getEventType());
				eventIdList.add(corrSet.getEventid()); 
			}	
			
			// retrieve alle events for the current correlation
			List events = eventPersistenceService.getEvents(eventIdList);		//  ***** DB Access
			
			log.debug("---------------------- Event Search -----------------------------");
			
			List eventAggList = new Vector();
			Iterator retrievedEventsIt = events.iterator();
			while (retrievedEventsIt.hasNext()) {
				Event event = (Event)retrievedEventsIt.next();
				event.setXmlcontent(new XMLUtils().convertDocToPretty(event.getXmlcontent()));
				
				// retrieve Attributes
				//List eventAttribs = eventPersistenceService.getEventattributesForEvent(event.getEventid());		//  ***** DB Access
				
				EventAgg eventAgg = new EventAgg();
				eventAgg.setEvent(event);
				//eventAgg.setEventAttributes(eventAttribs);
				eventAgg.setEventAttributes(new XMLUtils().extractAtrributesFromEvent(event));
				eventAgg.setEventTypeName((String)eventTypeList.get(event.getEventid()));
				
				eventAggList.add(eventAgg);
			}
			
			Vector eventRank = indexingServiceEvents.search(searchString,maxSearchResults, eventIdList);		// **** Search
			
			FoundCorrSet foundCorrSet = new FoundCorrSet();
			foundCorrSet.setGuid(guid);
			foundCorrSet.setEventAgg(eventAggList);
			foundCorrSet.setCorrelationSetDef(correlationSetDef);
			foundCorrSet.setEventRank(eventRank);
			
			if (exactMatch == false) {
				log.debug("### exactMatch == false");
				foundCorrSetList.add(foundCorrSet);
			} else if (exactMatch == true && eventRank.size() > 0){
				foundCorrSetList.add(foundCorrSet);
			} else {
				indexingServiceCorrEvents.setNumberOfFoundCorrEvents(indexingServiceCorrEvents.getNumberOfFoundCorrEvents() - 1);
			}
		}
		
		numberOfResults = foundCorrSetList.size();
		
		long end = new Date().getTime();
		long duration = end - start;
		
		
		CorrResultModel corrModel = new CorrResultModel();
		corrModel.setFoundCorrSet(foundCorrSetList);
		corrModel.setQueryTime(Long.toString(duration));
		corrModel.setNumberOfResults(numberOfResults);
		corrModel.setSearchString(searchString);
		corrModel.setNumberOfFoundCorrEvents(indexingServiceCorrEvents.getNumberOfFoundCorrEvents());
		corrModel.setFoundEventtypes(indexingServiceCorrEvents.getFoundEventtypes());
		
		return corrModel;
	}
	
	/**
	 * Search for Correlated context of an event
	 * 
	 * @param eventid
	 * @param page
	 * @return CorrResultModel
	 */
	public CorrResultModel searchCorrContext(Long eventid, int page, HashMap foundEventtypes) {
		int numberOfResults = 0;
		long start = new Date().getTime();
		
		List wids = this.corrPersistenceService.getCorrelatedsetByEvent(eventid);				//  ***** DB Access
		log.debug("### search 0.1 wids.size():" + wids.size());
		// go through the guid list provided by the search
		boolean createNew = false;
		if (foundEventtypes.size() > 0) 
			createNew = true;
		List foundCorrSetList = new Vector();
		Iterator widIt = wids.iterator();
		while (widIt.hasNext()) {
			String guid = (String) widIt.next();
			
			// get Correlatedsets with guid
			List corrEventList = corrPersistenceService.getCorrelatedsetByGuid(guid);	// ***** DB Access
			List widList = new Vector();
			String correlationSetDef = new String();
			
			// go through the correlated sets and retrieve the events 
			// create list with eventids
			List eventIdList = new Vector();
			HashMap eventTypeList = new HashMap();
			Iterator eventIt = corrEventList.iterator();
			log.debug("### foundEventtypes.size() " + foundEventtypes.size());
				
			// check if something is in foundEventtypes
			if (createNew == true) {
				// If there is something in it it means that the context search has already been executed and this is
				// just a filter process.
				// So check if set to false or true. If it is true skip this Correlation/Event construct
				while (eventIt.hasNext()) {
					Correlationset corrSet = (Correlationset)eventIt.next();
					Boolean setDefMinus = (Boolean)foundEventtypes.get(corrSet.getCorrelationSetDef());
					if (setDefMinus.booleanValue() == false) {
						correlationSetDef = corrSet.getCorrelationSetDef();
						eventTypeList.put(corrSet.getEventid(), corrSet.getEventType());
						eventIdList.add(corrSet.getEventid());
					}
				}
			} else {
				while (eventIt.hasNext()) {
					Correlationset corrSet = (Correlationset)eventIt.next();
					// If nothing is in the HashMap it means that the ContextSearch is executed for the first time
					// So just go on AND ADD the types to the Hashmap with the default false value
					correlationSetDef = corrSet.getCorrelationSetDef();
					eventTypeList.put(corrSet.getEventid(), corrSet.getEventType());
					eventIdList.add(corrSet.getEventid());
					foundEventtypes.put(correlationSetDef, new Boolean(false));
				}
			}
			
			// retrieve alle events for the current correlation
			List events = eventPersistenceService.getEvents(eventIdList);		//  ***** DB Access
			log.debug("---------------------- Event Search -----------------------------");
			
			List eventAggList = new Vector();
			Iterator retrievedEventsIt = events.iterator();
			while (retrievedEventsIt.hasNext()) {
				Event event = (Event)retrievedEventsIt.next();
				event.setXmlcontent(new XMLUtils().convertDocToPretty(event.getXmlcontent()));
				// retrieve Attributes
				//List eventAttribs = eventPersistenceService.getEventattributesForEvent(event.getEventid());		//  ***** DB Access
				
				EventAgg eventAgg = new EventAgg();
				eventAgg.setEvent(event);
				//eventAgg.setEventAttributes(eventAttribs);
				eventAgg.setEventAttributes(new XMLUtils().extractAtrributesFromEvent(event));
				eventAgg.setEventTypeName((String)eventTypeList.get(event.getEventid()));
				eventAggList.add(eventAgg);
			}
			
			FoundCorrSet foundCorrSet = new FoundCorrSet();
			foundCorrSet.setGuid(guid);
			foundCorrSet.setEventAgg(eventAggList);
			foundCorrSet.setCorrelationSetDef(correlationSetDef);
			foundCorrSet.setEventRank(null);
			foundCorrSetList.add(foundCorrSet);
		}
		
		numberOfResults = foundCorrSetList.size();
		
		long end = new Date().getTime();
		long duration = end - start;
		log.debug("### search 12");
		
		CorrResultModel corrModel = new CorrResultModel();
		corrModel.setFoundCorrSet(foundCorrSetList);
		corrModel.setQueryTime(Long.toString(duration));
		corrModel.setNumberOfResults(numberOfResults);
		corrModel.setSearchString(null);
		corrModel.setNumberOfFoundCorrEvents(numberOfResults);
		corrModel.setFoundEventtypes(foundEventtypes);
		log.debug("### search 13");
		
		return corrModel;
	}
	
	/**
	 * Searches Index of correlated events and expands the search queries by given criteria
	 * 
	 * @param searchString
	 * @param page
	 * @param foundEventtypes HashMap
	 * @return CorrResultModel
	 */
	public CorrResultModel searchForCorrEvents(String searchString, int page, boolean exactMatch, HashMap foundEventtypes) {
		String origSearchString = searchString;
		// iterate through foundEventtypes and append new criterias to query string
		Iterator it = foundEventtypes.keySet().iterator();
		while (it.hasNext()) {
			String key = (String)it.next();
			Boolean value = (Boolean)foundEventtypes.get(key);
			
			if (value.booleanValue() == true) {
				searchString = searchString + " -" + key;
			}
		}
		
		log.debug("### searchString:" + searchString);
		
		CorrResultModel corrResultModel = this.searchForCorrEvents(searchString, page, exactMatch);
		corrResultModel.setSearchString(origSearchString);
		
		
		String termAry = new String();
		Iterator itTerms = indexingServiceCorrEvents.extractSearchTerms(origSearchString).iterator();
		while (itTerms.hasNext()) {
			Term term = (Term)itTerms.next();
			termAry = termAry + " " + term.text();
		}
		
		corrResultModel.setTermList(termAry.trim());
		
		return corrResultModel;
	}
	
	
	
	/**
	 * Search index for matching events
	 * 
	 * @param searchString
	 * @param page
	 * @param lowerBound
     * @param upperBound
	 * @return
	 */
	public CorrResultModel searchForEvents(String searchString, int page, String lowerBound, String upperBound) {
		int numberOfResults = 0;
		long start = new Date().getTime();
		
		Vector wids = indexingServiceEvents.search(searchString, maxSearchResults, page, lowerBound, upperBound);		// ***** Search
		
		List eventAggList = new Vector();
		Iterator widIt = wids.iterator();
		while (widIt.hasNext()) {
			String eventId = (String) widIt.next();
			
			log.debug("### eventId:" + eventId);
			
			Event event = eventPersistenceService.getEvent(new Long(eventId.trim()));						//  ***** DB Access
			event.setXmlcontent(new XMLUtils().convertDocToPretty(event.getXmlcontent()));
			
			EventAgg eventAgg = new EventAgg();
			eventAgg.setEvent(event);
			eventAgg.setEventAttributes(new XMLUtils().extractAtrributesFromEvent(event));
			eventAgg.setEventTypeName(eventPersistenceService.getEventtype(event.getEventtypeid()).getEventname());	//  ***** DB Access
			eventAggList.add(eventAgg);
		}
		
		numberOfResults = eventAggList.size();
		
		long end = new Date().getTime();
		long duration = end - start;
		
		CorrResultModel corrModel = new CorrResultModel();
		corrModel.setFoundCorrSet(eventAggList);
		corrModel.setQueryTime(Long.toString(duration));
		corrModel.setNumberOfResults(numberOfResults);
		corrModel.setSearchString(searchString);
		corrModel.setNumberOfFoundCorrEvents(indexingServiceEvents.getNumberOfFoundCorrEvents());
		corrModel.setFoundEventtypes(indexingServiceEvents.getFoundEventtypes());
		
		String termAry = new String();
		Iterator itTerms = indexingServiceEvents.extractSearchTerms(searchString).iterator();
		while (itTerms.hasNext()) {
			Term term = (Term)itTerms.next();
			termAry = termAry + " " + term.text();
		}
		
		corrModel.setTermList(termAry.trim());
		
		return corrModel;
	}
	
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
	public CorrResultModel searchForEvents(String searchString, int page, HashMap foundEventtypes, String lowerBound, String upperBound) {
		String origSearchString = searchString;
		// iterate through foundEventtypes and append new criterias to query string
		Iterator it = foundEventtypes.keySet().iterator();
		while (it.hasNext()) {
			String key = (String)it.next();
			Boolean value = (Boolean)foundEventtypes.get(key);
			
			if (value.booleanValue() == true) {
				searchString = searchString + " -" + key;
			}
		}
		
		log.debug("### searchString:" + searchString);
		
		CorrResultModel corrResultModel = this.searchForEvents(searchString, page, lowerBound, upperBound);
		corrResultModel.setSearchString(origSearchString);
		
		String termAry = new String();
		Iterator itTerms = indexingServiceEvents.extractSearchTerms(origSearchString).iterator();
		while (itTerms.hasNext()) {
			Term term = (Term)itTerms.next();
			termAry = termAry + " " + term.text();
		}
		
		corrResultModel.setTermList(termAry.trim());
		
		return corrResultModel;
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