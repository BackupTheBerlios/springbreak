package at.generic.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.generic.search.SearchForAttribs;
import at.generic.web.commandObj.SearchResultCommand;




/**
 * @author szabolcs
 * @version $Id: SearchController.java,v 1.1 2006/02/14 10:10:08 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Controller for the Event Search
 * 
 */
public class SearchController implements Controller { 
	private static Log log = LogFactory.getLog(SearchController.class); 
	
	private SearchForAttribs eventSearch;
	
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

	
}	