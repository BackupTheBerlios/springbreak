package at.generic.web;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.generic.etl.SourceEventEtl;
import at.generic.eventmodel.Dbinfo;
import at.generic.service.AdminPersistenceService;
import at.generic.service.CorrelatingEventsPersistenceService;
import at.generic.service.EventPersistenceService;
import at.generic.service.IndexingService;
import at.generic.service.SearchService;
import at.generic.web.commandObj.AdminCommand;


/**
 * @author szabolcs
 * @version $Id: HelpController.java,v 1.1 2006/03/18 15:24:09 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * <p>Controller for the Help</p>
 * 
 */
public class HelpController implements Controller { 
	private static Log log = LogFactory.getLog(HelpController.class);
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		return new ModelAndView("help");
	}
}