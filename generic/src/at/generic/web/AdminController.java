package at.generic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.generic.etl.SourceEventEtl;
import at.generic.eventmodel.Dbinfo;
import at.generic.service.AdminPersistenceService;
import at.generic.service.EventPersistenceService;
import at.generic.service.IndexingService;
import at.generic.web.commandObj.AdminCommand;


/**
 * @author szabolcs
 * @version $Id: AdminController.java,v 1.6 2006/02/27 15:00:19 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.6 $
 * 
 * Controller for the ETL Process
 * 
 */
public class AdminController implements Controller { 
	private static Log log = LogFactory.getLog(AdminController.class); 
	
	private SourceEventEtl sourceEventEtl;
	private EventPersistenceService eventPersistenceService;
	private AdminPersistenceService adminPersistenceService;
	private IndexingService indexingServiceEvents;
	private IndexingService indexingServiceCorrEvents;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		if (request.getParameter("startTransformation") != null && request.getParameter("startTransformation").equals("true")) {
			sourceEventEtl.transformSourceEvents();
		} else {
		    sourceEventEtl.getBasicInfos();
		}
		
		AdminCommand adminData = new AdminCommand();
		adminData.setNumberOfIdentifiedEvents(sourceEventEtl.getNumberOfIdentifiedEvents());
		//adminData.setIdentifiedEvents(sourceEventEtl.getIdentifiedEvents());
		adminData.setNumberOfProcessedEvents(sourceEventEtl.getNumberOfProcessedEvents());
		adminData.setIdentifiedEvents(eventPersistenceService.getIdentifiedEventTypes());
		
		// calc the elt bar length
		adminData.setEtlBarLength(((sourceEventEtl.getNumberOfIdentifiedEvents() / (sourceEventEtl.getNumberOfProcessedEvents() * 0.01))/100) * 300); 
		
		// get last update infos
		Dbinfo dbInfo = adminPersistenceService.getLastEtlUpdate();
		
		if (dbInfo != null) {
			adminData.setUpdatestart(dbInfo.getUpdatestart());
			adminData.setUpdatestop(dbInfo.getUpdatestop());
		}
		
		// check if ETL Process is running
		if (sourceEventEtl.isEtlRunning() == true) {
			adminData.setEtlRunning(true);
			
			// if so then add time when process has been started
			adminData.setEtlThreadStartedAt(sourceEventEtl.getEtlThreadStartedAt().toGMTString());
		}
		
		adminData.setIndexLocationForEvents(indexingServiceEvents.getIndexLocation());
		adminData.setNumberOfIndexedIemsForEvents(indexingServiceEvents.getNumberOfIndexedIems());
		adminData.setIndexCreatedForEvents(indexingServiceEvents.isIndexCreated());
		
		adminData.setIndexLocationForCorrEvents(indexingServiceCorrEvents.getIndexLocation());
		adminData.setNumberOfIndexedIemsForCorrEvents(indexingServiceCorrEvents.getNumberOfIndexedIems());
		adminData.setIndexCreatedForCorrEvents(indexingServiceCorrEvents.isIndexCreated());
		
		//sourceEventEtl.transformSourceEvents();
		return new ModelAndView("admin", "adminData", adminData);
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

	/**
	 * @return Returns the adminPersistenceService.
	 */
	public AdminPersistenceService getAdminPersistenceService() {
		return adminPersistenceService;
	}

	/**
	 * @param adminPersistenceService The adminPersistenceService to set.
	 */
	public void setAdminPersistenceService(
			AdminPersistenceService adminPersistenceService) {
		this.adminPersistenceService = adminPersistenceService;
	}

	/**
	 * @return Returns the eventPersistenceService.
	 */
	public EventPersistenceService getEventPersistenceService() {
		return eventPersistenceService;
	}

	/**
	 * @param eventPersistenceService The eventPersistenceService to set.
	 */
	public void setEventPersistenceService(
			EventPersistenceService eventPersistenceService) {
		this.eventPersistenceService = eventPersistenceService;
	}

	/**
	 * @return Returns the indexingServiceCorrEvents.
	 */
	public IndexingService getIndexingServiceCorrEvents() {
		return indexingServiceCorrEvents;
	}

	/**
	 * @param indexingServiceCorrEvents The indexingServiceCorrEvents to set.
	 */
	public void setIndexingServiceCorrEvents(
			IndexingService indexingServiceCorrEvents) {
		this.indexingServiceCorrEvents = indexingServiceCorrEvents;
	}

	/**
	 * @return Returns the indexingServiceEvents.
	 */
	public IndexingService getIndexingServiceEvents() {
		return indexingServiceEvents;
	}

	/**
	 * @param indexingServiceEvents The indexingServiceEvents to set.
	 */
	public void setIndexingServiceEvents(IndexingService indexingServiceEvents) {
		this.indexingServiceEvents = indexingServiceEvents;
	}

	
	
}