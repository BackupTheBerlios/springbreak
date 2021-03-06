package at.generic.search.resultmodel;

import java.util.List;


/**
 * @author szabolcs
 * @version $Id: FoundCorrSet.java,v 1.4 2006/03/08 16:48:35 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.4 $
 * 
 * Infos about the correlation set
 * 
 */
public class FoundCorrSet {
	private List eventAgg;
	private String guid;
	private String correlationSetDef;
	private List eventRank;
	
	/**
	 * @return Returns the eventAgg.
	 */
	public List getEventAgg() {
		return eventAgg;
	}
	/**
	 * @param eventAgg The eventAgg to set.
	 */
	public void setEventAgg(List eventAgg) {
		this.eventAgg = eventAgg;
	}
	/**
	 * @return Returns the guid.
	 */
	public String getGuid() {
		return guid;
	}
	/**
	 * @param guid The guid to set.
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}
	/**
	 * @return Returns the correlationSetDef.
	 */
	public String getCorrelationSetDef() {
		return correlationSetDef;
	}
	/**
	 * @param correlationSetDef The correlationSetDef to set.
	 */
	public void setCorrelationSetDef(String correlationSetDef) {
		this.correlationSetDef = correlationSetDef;
	}
	/**
	 * @return Returns the eventRank.
	 */
	public List getEventRank() {
		return eventRank;
	}
	/**
	 * @param eventRank The eventRank to set.
	 */
	public void setEventRank(List eventRank) {
		this.eventRank = eventRank;
	}

}	