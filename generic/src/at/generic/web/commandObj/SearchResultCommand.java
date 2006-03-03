//searchResult.setEventTypeName(eventHandling.getEventtypeNameById(event.getEventtypeid()));
package at.generic.web.commandObj;

import java.util.List;

import at.generic.search.resultmodel.CorrResultModel;

/**
* @author szabolcs
* @version $Id: SearchResultCommand.java,v 1.2 2006/03/03 15:25:32 szabolcs Exp $
* $Author: szabolcs $  
* $Revision: 1.2 $
* 
* Capsulates search results for presentation
* 
*/
public class SearchResultCommand {
	private CorrResultModel corrResultModel;
	private String showEventId;
	private List openedCorrs;
	
	/**
	 * @return Returns the corrResultModel.
	 */
	public CorrResultModel getCorrResultModel() {
		return corrResultModel;
	}
	/**
	 * @param corrResultModel The corrResultModel to set.
	 */
	public void setCorrResultModel(CorrResultModel corrResultModel) {
		this.corrResultModel = corrResultModel;
	}
	/**
	 * @return Returns the openedCorrs.
	 */
	public List getOpenedCorrs() {
		return openedCorrs;
	}
	/**
	 * @param openedCorrs The openedCorrs to set.
	 */
	public void setOpenedCorrs(List openedCorrs) {
		this.openedCorrs = openedCorrs;
	}
	/**
	 * @return Returns the showEventId.
	 */
	public String getShowEventId() {
		return showEventId;
	}
	/**
	 * @param showEventId The showEventId to set.
	 */
	public void setShowEventId(String showEventId) {
		this.showEventId = showEventId;
	}
	
	
}