package at.generic.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.generic.search.SearchForAttribs;
import at.generic.search.resultmodel.CorrResultModel;
import at.generic.service.SearchService;
import at.generic.util.EventDate;




/**
 * @author szabolcs
 * @version $Id: SearchController.java,v 1.8 2006/03/16 11:11:29 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.8 $
 * 
 * Controller for the Event Search
 * 
 */
public class SearchController implements Controller { 
	private static Log log = LogFactory.getLog(SearchController.class); 
	
	private SearchForAttribs eventSearch;
	private SearchService searchService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		
		// if no searchstring has been entered
		if ((request.getParameter("searchstring") == null || request.getParameter("searchstring").trim().equals(""))  
				&& request.getParameter("newSearch") != null) 
			return new ModelAndView("index"); 
		
		// if a new search has been initiated or a browser page has been requested then create search parameters
		if (request.getParameter("newSearch") != null 
				|| request.getParameter("browserPage") != null 
				|| request.getParameter("showContextForId") != null) {
			String searchStr = request.getParameter("searchstring");
			int page = 1;
			boolean showRefineQuery = false;
			boolean dateRangeActive = false;
			/*String lowerBoundDay = new String();
			String lowerBoundMonth = new String();
			String lowerBoundYear = new String();
			String upperBoundDay = new String();
			String upperBoundMonth = new String();
			String upperBoundYear = new String();*/
			String lowerRange = new String();
			String upperRange = new String();
			
			int lowerBoundDay = 0;
			int lowerBoundMonth = 0;
			int lowerBoundYear = 0;
			int upperBoundDay = 0;
			int upperBoundMonth = 0; 
			int upperBoundYear = 0;
			
			
			// retrieve display infos only if NO new search has been activated
			CorrResultModel corrResultModelTmp = (CorrResultModel)session.getAttribute("resultModel");
			if (corrResultModelTmp != null && request.getParameter("newSearch") == null ) {
				showRefineQuery = corrResultModelTmp.isShowRefineQuery();
			}
			
			// if user is browsing e.g. selecting a new page you have to retrieve the current page status from the last result model
			HashMap foundEventtypes = new HashMap();
			if (request.getParameter("browserPage") != null ) {
				log.debug("### start Rank");
				page = Integer.parseInt(request.getParameter("browserPage"));
				searchStr = corrResultModelTmp.getSearchString();
				
				// check if refinement filter has been applied and update it
				if (corrResultModelTmp != null) {
					foundEventtypes = corrResultModelTmp.getFoundEventtypes();
					
					if (corrResultModelTmp.isDateRangeActive() == true) {
						lowerBoundDay = corrResultModelTmp.getLowerBoundDay();
						lowerBoundMonth = corrResultModelTmp.getLowerBoundMonth();
						lowerBoundYear = corrResultModelTmp.getLowerBoundYear();
						//lowerRange = lowerBoundYear + lowerBoundMonth + lowerBoundDay;
						lowerRange = EventDate.getBoundFormatForLucene(lowerBoundDay,lowerBoundMonth,lowerBoundYear);
						upperBoundDay = corrResultModelTmp.getUpperBoundDay();
						upperBoundMonth = corrResultModelTmp.getUpperBoundMonth();
						upperBoundYear = corrResultModelTmp.getUpperBoundYear();
						//upperRange = upperBoundYear + upperBoundMonth + upperBoundDay;
						upperRange =  EventDate.getBoundFormatForLucene(upperBoundDay,upperBoundMonth,upperBoundYear);
						dateRangeActive = true;
					}
					
				}
				
				if (request.getParameter("changeEventFilter") != null 
						&& !request.getParameter("changeEventFilter").equals("")
						&& request.getParameter("changeEventFilterTo") != null 
						&& !request.getParameter("changeEventFilterTo").equals("")) {
					foundEventtypes.put(request.getParameter("changeEventFilter"), new Boolean(request.getParameter("changeEventFilterTo")));
				}
				
				// check if dateRange filter has been applied and update it
				if (request.getParameter("dateRangeChanged") != null 
						&& request.getParameter("dateRangeChanged").equals("true")) {
					lowerBoundDay = Integer.parseInt(request.getParameter("lowerBoundDay"));
					lowerBoundMonth = Integer.parseInt(request.getParameter("lowerBoundMonth"));
					lowerBoundYear = Integer.parseInt(request.getParameter("lowerBoundYear"));
					//lowerRange = lowerBoundYear + lowerBoundMonth + lowerBoundDay;
					lowerRange = EventDate.getBoundFormatForLucene(lowerBoundDay,lowerBoundMonth,lowerBoundYear);
					upperBoundDay =  Integer.parseInt(request.getParameter("upperBoundDay"));
					upperBoundMonth = Integer.parseInt(request.getParameter("upperBoundMonth"));
					upperBoundYear = Integer.parseInt(request.getParameter("upperBoundYear"));
					//upperRange = upperBoundYear + upperBoundMonth + upperBoundDay;
					upperRange =  EventDate.getBoundFormatForLucene(upperBoundDay,upperBoundMonth,upperBoundYear);
					dateRangeActive = true;
				}
			}
			
			// check search rank type!
			CorrResultModel corrResultModel = new CorrResultModel();
			if (request.getParameter("showContextForId") != null && !request.getParameter("showContextForId").trim().equals("")) {
				// event CONTEXT is chosen
				log.debug("### context search");
				corrResultModel = searchService.searchCorrContext(new Long(request.getParameter("showContextForId")), page, foundEventtypes);
				corrResultModel.setShowRefineQueryOption(false);
				log.debug("### context search stop");
			} else if (request.getParameter("ranksearchtype") == null || !request.getParameter("ranksearchtype").equals("rankonesearch")) {
				// RANK 2 search
				log.debug("### Rank 2 Search");
				if (request.getParameter("exactSearch") != null && request.getParameter("exactSearch").equals("true")) {
					// exact match
					corrResultModel = searchService.searchForCorrEvents(searchStr, page, true);
					log.debug("### exactMatch == true");
				} else {
					// normal rank 2 search
					// check if a refinement has been created
					if (foundEventtypes.size() > 0) {
						corrResultModel = searchService.searchForCorrEvents(searchStr, page, false, foundEventtypes);
						corrResultModel.setFoundEventtypes(foundEventtypes);
					} else {
						corrResultModel = searchService.searchForCorrEvents(searchStr, page, false);
					}
					log.debug("### exactMatch == false");
				}
			} else {
				// RANK 1 search
				log.debug("### Rank 1 Search");
				// check if a refinement has been created
				if (foundEventtypes.size() > 0) {
					corrResultModel = searchService.searchForEvents(searchStr, page, foundEventtypes, lowerRange, upperRange);
					corrResultModel.setFoundEventtypes(foundEventtypes);
				} else {
					corrResultModel = searchService.searchForEvents(searchStr, page, lowerRange, upperRange);
				}
					
				log.debug("### Rank 1 Search end");
			}
			
			log.debug("### setting result model");
			corrResultModel.setCurrentPage(page);
			corrResultModel.setMaxPageSize(corrResultModel.getNumberOfFoundCorrEvents()/searchService.getMaxSearchResults()+1);
			corrResultModel.setMaxSearchResults(searchService.getMaxSearchResults());
			corrResultModel.setShowRefineQuery(showRefineQuery);
			
			corrResultModel.setDateRangeActive(dateRangeActive);
			corrResultModel.setLowerBoundDay(lowerBoundDay);
			corrResultModel.setLowerBoundMonth(lowerBoundMonth);
			corrResultModel.setLowerBoundYear(lowerBoundYear);
			corrResultModel.setUpperBoundDay(upperBoundDay);
			corrResultModel.setUpperBoundMonth(upperBoundMonth);
			corrResultModel.setUpperBoundYear(upperBoundYear);
			
			// check if exact match
			if (request.getParameter("exactSearch") != null && request.getParameter("exactSearch").equals("true"))
				corrResultModel.setExactSearch("true");
			else
				corrResultModel.setExactSearch("false");
			
			log.debug("### create session for result model");
			session.setAttribute("resultModel", corrResultModel);
			// check search rank type!
			if (request.getParameter("ranksearchtype") == null || !request.getParameter("ranksearchtype").equals("rankonesearch")) {
				log.debug("### return search");
				return new ModelAndView("search", "searchResult", corrResultModel);
			} else {
				log.debug("### return searchRankOne");
				return new ModelAndView("searchRankOne", "searchResult", corrResultModel);
			}
		} else {
			// if search button has NOT been clicked then reuse the data
			//SearchResultCommand searchResultCmd = (SearchResultCommand)session.getAttribute("searchResultCmd");
			CorrResultModel corrResultModel = (CorrResultModel)session.getAttribute("resultModel");
			
			// showevent details
			if (request.getParameter("showEventId") != null && !request.getParameter("showEventId").equals("")) {
				log.debug("### if 1");
				corrResultModel.setShowEventId(request.getParameter("showEventId"));
				corrResultModel.setShowEventViewType(request.getParameter("showEventViewType"));
			} else if (request.getParameter("closeShowEventId") != null && !request.getParameter("closeShowEventId").equals("")) {
				log.debug("### if 2");
				corrResultModel.setShowEventId(null);
			}
			
			// open correlation 
			if (request.getParameter("showCorrEventId") != null && !request.getParameter("showCorrEventId").equals("")) {
				HashMap corrEventIdMap = corrResultModel.getShowCorrEventId();
				String corrEventId = (String)corrEventIdMap.get(request.getParameter("showCorrEventId"));
				// if the given Corr id is null then add the id to list --> means the subtree has to be opned
				if (corrEventId == null) {
					corrEventIdMap.put(request.getParameter("showCorrEventId"),request.getParameter("showCorrEventId"));
				} else {
					// if the Corr id has a value delete the id from the list --> means the subtree is closed
					corrEventIdMap.remove(corrEventId);
				}
				
				corrResultModel.setShowCorrEventId(corrEventIdMap);
			}
			
			// open-close query refinement
			if (request.getParameter("showRefineQuery") != null && request.getParameter("showRefineQuery").equals("false")) {
				log.debug("### showrefinement=false");
				corrResultModel.setShowRefineQuery(false);
			} else if (request.getParameter("showRefineQuery") != null && request.getParameter("showRefineQuery").equals("true")) {
				log.debug("### showrefinement=true");
				corrResultModel.setShowRefineQuery(true);
			}
			
			session.setAttribute("resultModel", corrResultModel);
			if (request.getParameter("showContextForId") != null && !request.getParameter("showContextForId").trim().equals("")) { 
				return new ModelAndView("search", "searchContext", corrResultModel);
			} else if (request.getParameter("ranksearchtype") == null || !request.getParameter("ranksearchtype").equals("rankonesearch")) 
				return new ModelAndView("search", "searchResult", corrResultModel);
			else
				return new ModelAndView("searchRankOne", "searchResult", corrResultModel);
		}
		
	}

	/**
	 * @return Returns the eventSearch.
	 */
	public SearchForAttribs getEventSearch() {
		return eventSearch;
	}

	/**
	 * @param eventSearch The eventSearch to set.
	 */
	public void setEventSearch(SearchForAttribs eventSearch) {
		this.eventSearch = eventSearch;
	}

	/**
	 * @return Returns the searchService.
	 */
	public SearchService getSearchService() {
		return searchService;
	}

	/**
	 * @param searchService The searchService to set.
	 */
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	
	
}	
