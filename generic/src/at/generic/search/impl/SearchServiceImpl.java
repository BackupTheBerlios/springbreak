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
 * @version $Id: SearchServiceImpl.java,v 1.6 2006/03/08 16:48:35 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.6 $
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
		
		Vector wids = indexingServiceCorrEvents.search(searchString, maxSearchResults, page);


		int numberOfResults = 0;
		// go through the guid list provided by the search
		List foundCorrSetList = new Vector();
		Iterator widIt = wids.iterator();
		while (widIt.hasNext()) {
			String guid = (String) widIt.next();
			numberOfResults++;
			
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
			
			Vector eventRank = indexingServiceEvents.search(searchString,maxSearchResults, eventIdList);
			
			FoundCorrSet foundCorrSet = new FoundCorrSet();
			foundCorrSet.setGuid(guid);
			foundCorrSet.setEventAgg(eventAggList);
			foundCorrSet.setCorrelationSetDef(correlationSetDef);
			foundCorrSet.setEventRank(eventRank);
			
			if (exactMatch == false) {
				log.debug("### exactMatch == false");
				foundCorrSetList.add(foundCorrSet);
			} else if (eventRank.size() == 0){
				log.debug("### numberOfResults--;");
				numberOfResults--;
			} else {
				log.debug("### foundCorrSetList.add(foundCorrSet);");
				foundCorrSetList.add(foundCorrSet);
			}
		}
		
		long end = new Date().getTime();
		long duration = end - start;
		
		
		CorrResultModel corrModel = new CorrResultModel();
		corrModel.setFoundCorrSet(foundCorrSetList);
		corrModel.setQueryTime(Long.toString(duration));
		corrModel.setNumberOfResults(numberOfResults);
		corrModel.setSearchString(searchString);
		corrModel.setNumberOfFoundCorrEvents(indexingServiceCorrEvents.getNumberOfFoundCorrEvents());
		
		String termAry = new String();
		Iterator itTerms = indexingServiceCorrEvents.extractSearchTerms(searchString).iterator();
		while (itTerms.hasNext()) {
			Term term = (Term)itTerms.next();
			termAry = termAry + " " + term.text();
		}
		
		corrModel.setTermList(termAry.trim());
		
		return corrModel;
	}
	
	/**
	 * Search for Correlated context of an event
	 * 
	 * @param eventid
	 * @param page
	 * @return CorrResultModel
	 */
	public CorrResultModel searchCorrContext(Long eventid, int page) {
		int numberOfResults = 0;
		long start = new Date().getTime();
		
		List wids = this.corrPersistenceService.getCorrelatedsetByEvent(eventid);				//  ***** DB Access
		log.debug("### search 0.1 wids.size():" + wids.size());
		// go through the guid list provided by the search
		List foundCorrSetList = new Vector();
		Iterator widIt = wids.iterator();
		while (widIt.hasNext()) {
			log.debug("### search 1");
			String guid = (String) widIt.next();
			log.debug("### search 2 guid: " + guid);
			numberOfResults++;
			log.debug("### search 3 numberOfResults: " + numberOfResults);
			
			// get Correlatedsets with guid
			List corrEventList = corrPersistenceService.getCorrelatedsetByGuid(guid);	// ***** DB Access
			log.debug("### search 3 corrEventList.size():" + corrEventList.size());
			List widList = new Vector();
			String correlationSetDef = new String();
			// go through the correlated sets and retrieve the events 
			log.debug("### search 4");
			// create list with eventids
			List eventIdList = new Vector();
			HashMap eventTypeList = new HashMap();
			Iterator eventIt = corrEventList.iterator();
			log.debug("### search 5");
			while (eventIt.hasNext()) {
				log.debug("### search 6");
				Correlationset corrSet = (Correlationset)eventIt.next();
				correlationSetDef = corrSet.getCorrelationSetDef();
				eventTypeList.put(corrSet.getEventid(), corrSet.getEventType());
				eventIdList.add(corrSet.getEventid()); 
			}	
			
			// retrieve alle events for the current correlation
			List events = eventPersistenceService.getEvents(eventIdList);		//  ***** DB Access
			log.debug("### search 7 events.size():" + events.size());
			log.debug("---------------------- Event Search -----------------------------");
			
			List eventAggList = new Vector();
			Iterator retrievedEventsIt = events.iterator();
			while (retrievedEventsIt.hasNext()) {
				Event event = (Event)retrievedEventsIt.next();
				event.setXmlcontent(new XMLUtils().convertDocToPretty(event.getXmlcontent()));
				log.debug("### search 8");
				// retrieve Attributes
				//List eventAttribs = eventPersistenceService.getEventattributesForEvent(event.getEventid());		//  ***** DB Access
				
				EventAgg eventAgg = new EventAgg();
				eventAgg.setEvent(event);
				//eventAgg.setEventAttributes(eventAttribs);
				eventAgg.setEventAttributes(new XMLUtils().extractAtrributesFromEvent(event));
				eventAgg.setEventTypeName((String)eventTypeList.get(event.getEventid()));
				log.debug("### search 9");
				eventAggList.add(eventAgg);
			}
			log.debug("### search 10");
			FoundCorrSet foundCorrSet = new FoundCorrSet();
			foundCorrSet.setGuid(guid);
			foundCorrSet.setEventAgg(eventAggList);
			foundCorrSet.setCorrelationSetDef(correlationSetDef);
			foundCorrSet.setEventRank(null);
			foundCorrSetList.add(foundCorrSet);
			log.debug("### search 11");
		}
		
		long end = new Date().getTime();
		long duration = end - start;
		log.debug("### search 12");
		
		CorrResultModel corrModel = new CorrResultModel();
		corrModel.setFoundCorrSet(foundCorrSetList);
		corrModel.setQueryTime(Long.toString(duration));
		corrModel.setNumberOfResults(numberOfResults);
		corrModel.setSearchString(null);
		corrModel.setNumberOfFoundCorrEvents(numberOfResults);
		log.debug("### search 13");
		
		return corrModel;
	}
	
	
	
	/**
	 * Search index for matching events
	 * 
	 * @param searchString
	 * @param page
	 * @return
	 */
	public CorrResultModel searchForEvents(String searchString, int page) {
		int numberOfResults = 0;
		long start = new Date().getTime();
		
		Vector wids = indexingServiceEvents.search(searchString, maxSearchResults, page);
		
		List eventAggList = new Vector();
		Iterator widIt = wids.iterator();
		while (widIt.hasNext()) {
			String eventId = (String) widIt.next();
			numberOfResults++;
			
			log.debug("### eventId:" + eventId);
			
			Event event = eventPersistenceService.getEvent(new Long(eventId.trim()));						//  ***** DB Access
			event.setXmlcontent(new XMLUtils().convertDocToPretty(event.getXmlcontent()));
			
			EventAgg eventAgg = new EventAgg();
			eventAgg.setEvent(event);
			eventAgg.setEventAttributes(new XMLUtils().extractAtrributesFromEvent(event));
			eventAgg.setEventTypeName(eventPersistenceService.getEventtype(event.getEventtypeid()).getEventname());	//  ***** DB Access
			eventAggList.add(eventAgg);
		}
		long end = new Date().getTime();
		long duration = end - start;
		
		CorrResultModel corrModel = new CorrResultModel();
		corrModel.setFoundCorrSet(eventAggList);
		corrModel.setQueryTime(Long.toString(duration));
		corrModel.setNumberOfResults(numberOfResults);
		corrModel.setSearchString(searchString);
		corrModel.setNumberOfFoundCorrEvents(indexingServiceEvents.getNumberOfFoundCorrEvents());
		String termAry = new String();
		Iterator itTerms = indexingServiceEvents.extractSearchTerms(searchString).iterator();
		while (itTerms.hasNext()) {
			Term term = (Term)itTerms.next();
			termAry = termAry + " " + term.text();
		}
		
		corrModel.setTermList(termAry.trim());
		
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