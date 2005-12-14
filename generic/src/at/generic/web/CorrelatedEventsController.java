package at.generic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.generic.service.EventSourceManager;


/**
 * @author szabolcs
 * @version $Id: CorrelatedEventsController.java,v 1.1 2005/12/14 22:15:14 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Main View Controller
 * 
 */
public class CorrelatedEventsController implements Controller { 
	private static Log log = LogFactory.getLog(IndexViewController.class); 
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
	    request.getSession().invalidate();
	    
		return new ModelAndView("index"); 
	}

	
}