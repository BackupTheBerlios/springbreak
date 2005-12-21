package at.generic.web.commandObj;

import java.util.Vector;

import at.generic.eventmodel.BaseEvent;

/**
 * @author szabolcs
 * @version $Id: BrowserCommand.java,v 1.2 2005/12/21 22:07:50 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * Data Object for List in Browser View. Cotains mainly logic data.
 * Attributes NextPage and PrevPage are calculated when currentPage is set.
 * 
 */
public class BrowserCommand { 
	// browser logic
	private int currentPage;
	private int nextPage;
	private int prevPage;
	private int maxPageSize;
	
	// data to display
	Vector listEventsCommand;			// BaseEvent objects stored in it
	private String eventDetail;			// either XML or HTML representation of the Event

	/**
	 * @return Returns the currentPage.
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage The currentPage to set.
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		this.nextPage = currentPage + 1;
		if (this.currentPage > 1) 
			this.prevPage = currentPage - 1;
	}

	/**
	 * @return Returns the maxPageSize.
	 */
	public int getMaxPageSize() {
		return maxPageSize;
	}

	/**
	 * @param maxPageSize The maxPageSize to set.
	 */
	public void setMaxPageSize(int maxPageSize) {
		this.maxPageSize = maxPageSize;
	}

	/**
	 * @return Returns the listEventsCommand.
	 */
	public Vector getListEventsCommand() {
		return listEventsCommand;
	}

	/**
	 * @param listEventsCommand The listEventsCommand to set.
	 */
	public void setListEventsCommand(Vector listEventsCommand) {
		this.listEventsCommand = listEventsCommand;
	}

	/**
	 * @return Returns the nextPage.
	 */
	public int getNextPage() {
		return nextPage;
	}

	/**
	 * @param nextPage The nextPage to set.
	 */
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	/**
	 * @return Returns the prevPage.
	 */
	public int getPrevPage() {
		return prevPage;
	}

	/**
	 * @param prevPage The prevPage to set.
	 */
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	/**
	 * @return Returns the eventDetail.
	 */
	public String getEventDetail() {
		return eventDetail;
	}

	/**
	 * @param eventDetail The eventDetail to set.
	 */
	public void setEventDetail(String eventDetail) {
		this.eventDetail = eventDetail;
	}
	
	
	
	
}