package at.generic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;



public class IndexViewController implements Controller { 
	private static Log log = LogFactory.getLog(IndexViewController.class); 
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		
	    request.getSession().invalidate();
	    log.debug("## test");
		return new ModelAndView("index"); 
	}
}