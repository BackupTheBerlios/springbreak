package at.generic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.generic.etl.SourceEventEtl;
import at.generic.web.commandObj.AdminCommand;


/**
 * @author szabolcs
 * @version $Id: AdminController.java,v 1.2 2006/01/14 19:43:05 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
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
		
		AdminCommand adminData = new AdminCommand();
		adminData.setNumberOfIdentifiedEvents(sourceEventEtl.getNumberOfIdentifiedEvents());
		adminData.setLastUpdate(sourceEventEtl.getLastUpdate());
		//adminData.setIdentifiedEvents(sourceEventEtl.getIdentifiedEvents());
		adminData.setNumberOfProcessedEvents(sourceEventEtl.getNumberOfProcessedEvents());
		
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