package at.generic.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.generic.dao.hibernate.GenericServiceDAOTarget;
import at.generic.etl.SourceEventEtl;
import at.generic.eventmodel.BaseEvent;
import at.generic.service.EventSourceManager;

/**
 * @author szabolcs
 * @version $Id: EventSourceManagerImpl.java,v 1.4 2005/12/21 22:07:42 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.4 $
 * 
 * Implementation of the Events Data Manager
 * 
 */
public class EventSourceManagerImpl implements EventSourceManager {
	private static Log log = LogFactory.getLog(EventSourceManagerImpl.class);
	private GenericServiceDAOTarget genericDAOTarget;
	private SourceEventEtl sourceEventEtl;
	
	private int pageSize;
	
	/**
	 * Initialize basic information for etl module
	 */
	public void init() {
		sourceEventEtl.getBasicInfos();
	}
	
	/**
	 * Returns a subset of base events including the Raw XML Data
	 * according to the selected page 
	 * 
	 * @return List with all the base events
	 */
	public List getBaseEventsByPage(int pageNr) {
		return genericDAOTarget.getObjectsByPage(new BaseEvent(), pageNr, pageSize);
	}


	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return Returns the genericDAOTarget.
	 */
	public GenericServiceDAOTarget getGenericDAOTarget() {
		return genericDAOTarget;
	}

	/**
	 * @param genericDAOTarget The genericDAOTarget to set.
	 */
	public void setGenericDAOTarget(GenericServiceDAOTarget genericDAOTarget) {
		this.genericDAOTarget = genericDAOTarget;
	}

	/**
	 * @return Returns the sourceEventEtl.
	 */
	public SourceEventEtl getSourceEventEtl() {
		return sourceEventEtl;
	}

	/**
	 * @param sourceEventEtl The sourceEventEtl to set.
	 */
	public void setSourceEventEtl(SourceEventEtl sourceEventEtl) {
		this.sourceEventEtl = sourceEventEtl;
	}
	
}