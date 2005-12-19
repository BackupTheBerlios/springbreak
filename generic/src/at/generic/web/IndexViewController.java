package at.generic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.generic.etl.SourceEventEtl;


/**
 * @author szabolcs
 * @version $Id: IndexViewController.java,v 1.5 2005/12/19 23:18:07 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.5 $
 * 
 * Main View Controller
 * 
 */
public class IndexViewController implements Controller { 
	private static Log log = LogFactory.getLog(IndexViewController.class); 
	
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
	    request.getSession().invalidate();

	    
		return new ModelAndView("index"); 
	}


		
	
}