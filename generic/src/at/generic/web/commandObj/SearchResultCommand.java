//searchResult.setEventTypeName(eventHandling.getEventtypeNameById(event.getEventtypeid()));
package at.generic.web.commandObj;

import java.util.List;

/**
* @author szabolcs
* @version $Id: SearchResultCommand.java,v 1.1 2006/02/14 10:10:08 szabolcs Exp $
* $Author: szabolcs $  
* $Revision: 1.1 $
* 
* Capsulates search results for presentation
* 
*/
public class SearchResultCommand {
	private List resultList;
	private int numberOfResults;
	private String searchString;
	
	/**
	 * @return Returns the numberOfResults.
	 */
	public int getNumberOfResults() {
		return numberOfResults;
	}
	/**
	 * @param numberOfResults The numberOfResults to set.
	 */
	public void setNumberOfResults(int numberOfResults) {
		this.numberOfResults = numberOfResults;
	}
	/**
	 * @return Returns the resultList.
	 */
	public List getResultList() {
		return resultList;
	}
	/**
	 * @param resultList The resultList to set.
	 */
	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
	/**
	 * @return Returns the searchString.
	 */
	public String getSearchString() {
		return searchString;
	}
	/**
	 * @param searchString The searchString to set.
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	
	
	
	
	
}