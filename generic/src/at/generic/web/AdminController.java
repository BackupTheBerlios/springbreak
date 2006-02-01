package at.generic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.generic.etl.SourceEventEtl;
import at.generic.eventmodel.Dbinfo;
import at.generic.service.EventHandling;
import at.generic.web.commandObj.AdminCommand;


/**
 * @author szabolcs
 * @version $Id: AdminController.java,v 1.4 2006/02/01 19:48:51 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.4 $
 * 
 * Controller for the ETL Process
 * 
 */
public class AdminController implements Controller { 
	private static Log log = LogFactory.getLog(AdminController.class); 
	
	private SourceEventEtl sourceEventEtl;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		if (request.getParameter("startTransformation") != null && request.getParameter("startTransformation").equals("true")) {
			sourceEventEtl.transformSourceEvents();
		} else {
		    sourceEventEtl.getBasicInfos();
		}
		
		EventHandling eventHandling = sourceEventEtl.getEventHandling();
		
		AdminCommand adminData = new AdminCommand();
		adminData.setNumberOfIdentifiedEvents(sourceEventEtl.getNumberOfIdentifiedEvents());
		//adminData.setIdentifiedEvents(sourceEventEtl.getIdentifiedEvents());
		adminData.setNumberOfProcessedEvents(sourceEventEtl.getNumberOfProcessedEvents());
		adminData.setIdentifiedEvents(eventHandling.getIdentifiedEventTypes());
		
		// calc the elt bar length
		adminData.setEtlBarLength(((sourceEventEtl.getNumberOfIdentifiedEvents() / (sourceEventEtl.getNumberOfProcessedEvents() * 0.01))/100) * 300); 
		
		// get last update infos
		Dbinfo dbInfo = eventHandling.getLastEtlUpdate();
		
		if (dbInfo != null) {
			adminData.setUpdatestart(dbInfo.getUpdatestart());
			adminData.setUpdatestop(dbInfo.getUpdatestop());
		}
		
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


	
}