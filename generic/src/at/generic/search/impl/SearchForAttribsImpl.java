package at.generic.search.impl;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.generic.dao.GenericServiceDAO;
import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventattribute;
import at.generic.search.SearchForAttribs;
import at.generic.search.resultmodel.SearchResult;
import at.generic.service.EventHandling;

/**
 * @author szabolcs
 * @version $Id: SearchForAttribsImpl.java,v 1.1 2006/02/12 16:31:14 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Searchinterface for Events.
 * 
 */
public class SearchForAttribsImpl implements SearchForAttribs {
	private static Log log = LogFactory.getLog(SearchForAttribsImpl.class);
	
	private GenericServiceDAO genericServiceTarget;
	private EventHandling eventHandling;
	private int fetchLimit;
	
	
	/**
	 * Searches all Attributes of events for the given word(s) 
	 * and returns a List with found Events.
	 * 
	 * @param searchStr
	 * @return List with Events and EventAttributes
	 */
	public List getEventsForFoundAttribs(String searchStr) {
		log.debug("### searchStr: " + searchStr);
		// TODO: analyze the search string
		this.createSearchAttributeList(searchStr);
		
		// extract quoted substrings
		
		// TODO: determine search type AND, OR, Wildcards
		
		// TODO: choose search type
		
		// execute search
		List resultList = this.getEventsWithSimpleSearch(searchStr);
		
		// return results
		return resultList;
	}
	
	/**
	 * Searches event attributes for a simple word. Nothing special 
	 * and only for testing purpose not for deployment
	 * 
	 * @param word the simple search String
	 * @return List with Events and EventAttributes 
	 */
	private List getEventsWithSimpleSearch(String word) {
		// TODO: limit einbauen 300
		List eventAttribList = genericServiceTarget.getObjectsByQuery(
				"from Eventattribute where value = '" + word + "'");
		
		List resultList = new Vector();
		
		if (eventAttribList.size() > 0) {
			Iterator i = eventAttribList.iterator();
			while (i.hasNext()) {
				Eventattribute ea = (Eventattribute) i.next();
				Event event = eventHandling.getEventById(ea.getEventid());
				
				log.debug("### ea.getAttributeid(): " + ea.getAttributeid());
				log.debug("### ea.getAttributename(): " + ea.getAttributename());
				log.debug("### event.getEventid(): " + event.getEventid());
				
				SearchResult searchResult = new SearchResult();
				searchResult.setEvent(event);
				searchResult.setEventAttribute(ea);
				searchResult.setEventTypeName(eventHandling.getEventtypeNameById(event.getEventtypeid().intValue()));
				searchResult.setAttributeList(eventHandling.getAllEventAttributesByEvent(event.getEventid()));
				resultList.add(searchResult);
			}
		}
		
		return resultList;
	}
	
	private Vector createSearchAttributeList(String searchStr) {
		Vector searchAttribs = new Vector();

		searchStr.trim();
		
		log.debug("### searchStr.length(): " + searchStr.length());
		
		boolean openQuote = false;
		int i = 0;
		int j = 0;
		
		for (j = 0; j < searchStr.length(); j++) {
			log.debug("### i=" + i + "; j=" + j);
			if (((searchStr.substring(j,j+1).equals(" ") || (j == (searchStr.length() - 1))) && openQuote == false) ) {
				log.debug("### substring (i=" + i + ";j+1=" + j+1 + "): #" + searchStr.substring(i, j + 1).trim() + "#");
				searchAttribs.add(searchStr.substring(i, j + 1).trim());
				i = j + 1;
			} else if (searchStr.substring(j,j+1).equals("\"") && openQuote == false) {
				openQuote = true;
			} else if (searchStr.substring(j,j+1).equals("\"") && openQuote == true) {
				openQuote = false;
				log.debug("### substring (i=" + i + ";j=" + j + "): #" + searchStr.substring(i+1, j).trim() + "#");
				searchAttribs.add(searchStr.substring(i+1, j).trim());
				i = j + 1;
				j++;
			}
			
		}
		
		
		/*String concStr = new String();
		boolean openQuote = false;
		int i = 0;
		int j = 0;
		
		for (j = 0; j < searchStr.length(); j++) {
			//log.debug("### searchStr.substring(j,j+1): " + searchStr.substring(j,j+1));
			if (searchStr.substring(j,j+1).equals("\"")) {
				//log.debug("### entered i=" + i + "; j = " + j);
				if (openQuote == false) {
					openQuote = true;
					concStr = concStr + searchStr.substring(i, j);
					i = j + 1;
				} else {
					openQuote = false;
					//log.debug("### substring: #" + searchStr.substring(i, j).trim() + "#");
					searchAttribs.add(searchStr.substring(i, j).trim());
					i = j + 1;
				}
			}
		}
		//log.debug("### concStr: " + concStr);
		
		StringTokenizer st = new StringTokenizer(concStr);
		while (st.hasMoreTokens()) {
			//log.debug("### search token: " + st.nextToken());
			searchAttribs.add(st.nextToken());
		}
		
		Iterator iter = searchAttribs.iterator();
		while (iter.hasNext()) {
			String ea = (String) iter.next();
			log.debug("### Search Attrib List: " + ea);
		}*/
	     
	     return searchAttribs;
	}
	
	private void createSearchAttributeListaux(Vector searchAttribs, String searchStr, int i, int j) {
		/*i = searchStr.indexOf("\"", j);
		log.debug("### i: " + i);
		j = searchStr.indexOf("\"", i + 1);
		log.debug("### j: " + j);
		
		if (i != -1 && j != -1) {
			log.debug("### searchStr.substring(i, j-1): " + searchStr.substring(i+1, j));
			searchAttribs.add(searchStr.substring(i+1, j));
			createSearchAttributeListaux(searchAttribs, searchStr, 0, j);
		}
		i = 0;
		for (j = 0; j < searchStr.length(); j++) {
			log.debug("### searchStr.substring(j,j+1): " + searchStr.substring(j,j+1));
			if (searchStr.substring(j,j+1).equals(" ")) {
				log.debug("### substring: #" + searchStr.substring(i, j) + "#");
				i = j + 1;
			}
		}*/
	}
	
	/**
	 * @return Returns the eventHandling.
	 */
	public EventHandling getEventHandling() {
		return eventHandling;
	}

	/**
	 * @param eventHandling The eventHandling to set.
	 */
	public void setEventHandling(EventHandling eventHandling) {
		this.eventHandling = eventHandling;
	}

	/**
	 * @return Returns the genericServiceTarget.
	 */
	public GenericServiceDAO getGenericServiceTarget() {
		return genericServiceTarget;
	}

	/**
	 * @param genericServiceTarget The genericServiceTarget to set.
	 */
	public void setGenericServiceTarget(GenericServiceDAO genericServiceTarget) {
		this.genericServiceTarget = genericServiceTarget;
	}

	/**
	 * @return Returns the fetchLimit.
	 */
	public int getFetchLimit() {
		return fetchLimit;
	}

	/**
	 * @param fetchLimit The fetchLimit to set.
	 */
	public void setFetchLimit(int fetchLimit) {
		this.fetchLimit = fetchLimit;
	}
	
	
	
}