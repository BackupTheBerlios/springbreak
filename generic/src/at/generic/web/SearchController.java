package at.generic.web;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.generic.search.SearchForAttribs;
import at.generic.search.resultmodel.CorrResultModel;
import at.generic.search.resultmodel.FoundCorrSet;
import at.generic.service.SearchService;
import at.generic.web.commandObj.SearchResultCommand;




/**
 * @author szabolcs
 * @version $Id: SearchController.java,v 1.2 2006/02/27 15:00:20 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
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
		// if a new search has been initiated then create search parameters
		if (request.getParameter("newSearch") != null) {
			List resultList = eventSearch.getEventsForFoundAttribs(request.getParameter("searchstring"));
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
			
			
			return new ModelAndView("search", "searchResult", searchResultCmd); 
		} else {
			// if search button has NOT been clicked then reuse the data
			SearchResultCommand searchResultCmd = (SearchResultCommand)session.getAttribute("searchResultCmd");
			
			return new ModelAndView("search", "searchResult", searchResultCmd); 
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
