package at.generic.web.sessions;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.model.Correlatedevent;
import at.generic.service.EventSourceManager;
import at.generic.xmlhandlers.EventXmlHandler;

/**
 * @author szabolcs
 * @version $Id: BrowserSession.java,v 1.1 2005/12/19 23:17:50 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Holds Sessions Infos for the Browser View
 * 
 */

public class BrowserSession {
	private int numberOfEvents;
	private boolean PopulatedNumberOfEvents;

	/**
	 * @return Returns the numberOfEvents.
	 */
	public int getNumberOfEvents() {
		return numberOfEvents;
	}

	/**
	 * @param numberOfEvents The numberOfEvents to set.
	 */
	public void setNumberOfEvents(int numberOfEvents) {
		this.numberOfEvents = numberOfEvents;
	}

	/**
	 * @return Returns the populatedNumberOfEvents.
	 */
	public boolean isPopulatedNumberOfEvents() {
		return PopulatedNumberOfEvents;
	}

	/**
	 * @param populatedNumberOfEvents The populatedNumberOfEvents to set.
	 */
	public void setPopulatedNumberOfEvents(boolean populatedNumberOfEvents) {
		PopulatedNumberOfEvents = populatedNumberOfEvents;
	}
	
	
	
}