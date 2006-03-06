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




/**
 * @author szabolcs
 * @version $Id: SearchController.java,v 1.5 2006/03/06 23:21:13 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.5 $
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
		if ((request.getParameter("searchstring") == null || request.getParameter("searchstring").trim().equals(""))  && request.getParameter("newSearch") != null) 
			return new ModelAndView("index"); 
		// if a new search has been initiated then create search parameters
		if (request.getParameter("newSearch") != null || request.getParameter("browserPage") != null) {
			/*List resultList = eventSearch.getEventsForFoundAttribs(request.getParameter("searchstring"));
			log.debug("### resultList.size(): " + resultList.size());
			SearchResultCommand searchResultCmd = new SearchResultCommand();
			searchResultCmd.setNumberOfResults(resultList.size());
			searchResultCmd.setResultList(resultList);
			
			searchResultCmd.setSearchString(request.getParameter("searchstring"));
			session.setAttribute("searchResultCmd", searchResultCmd);
			
			//indexingService.search(request.getParameter("searchstring"),100);
			CorrResultModel corrResultModel = searchService.searchForCorrEvents(request.getParameter("searchstring"));
			log.debug("### corrResultModel.getNumberOfResults(): " + corrResultModel.getNumberOfResults());
			
			List corrSetList = corrResultModel.getFoundCorrSet();
			
			Iterator it = corrSetList.iterator();
			while (it.hasNext()) {
				FoundCorrSet foundCorrSet = (FoundCorrSet) it.next();
				log.debug("### foundCorrSet.getGuid(): " + foundCorrSet.getGuid());
			}
			*/
			int page = 1;
			if (request.getParameter("browserPage") != null ) 
				page = Integer.parseInt(request.getParameter("browserPage"));
			
			CorrResultModel corrResultModel = searchService.searchForCorrEvents(request.getParameter("searchstring"), page);
			corrResultModel.setCurrentPage(page);
			corrResultModel.setMaxPageSize(corrResultModel.getNumberOfFoundCorrEvents()/searchService.getMaxSearchResults()+1);
			corrResultModel.setMaxSearchResults(searchService.getMaxSearchResults());
			
			session.setAttribute("resultModel", corrResultModel);
			
			return new ModelAndView("search", "searchResult", corrResultModel); 
		} else {
			// if search button has NOT been clicked then reuse the data
			//SearchResultCommand searchResultCmd = (SearchResultCommand)session.getAttribute("searchResultCmd");
			CorrResultModel corrResultModel = (CorrResultModel)session.getAttribute("resultModel");
			if (request.getParameter("showEventId") != null && !request.getParameter("showEventId").equals("")) {
				log.debug("### if 1");
				corrResultModel.setShowEventId(request.getParameter("showEventId"));
				corrResultModel.setShowEventViewType(request.getParameter("showEventViewType"));
			} else if (request.getParameter("closeShowEventId") != null && !request.getParameter("closeShowEventId").equals("")) {
				log.debug("### if 2");
				corrResultModel.setShowEventId(null);
			}
			
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
			
			session.setAttribute("resultModel", corrResultModel);
			
			return new ModelAndView("search", "searchResult", corrResultModel); 
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
