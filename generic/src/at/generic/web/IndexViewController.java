package at.generic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.model.Correlatedevent;



public class IndexViewController implements Controller { 
	private static Log log = LogFactory.getLog(IndexViewController.class); 
	private CorrelatedeventDAO correlatedeventDAO;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
	    request.getSession().invalidate();
	    
	    
	    log.debug("## in handleRequest IndexViewController");
	    
	    Correlatedevent ce = correlatedeventDAO.getCorrelatedevent(new Integer(3490));
	    
	    log.debug("### Correlatedevent " + ce.getEventXml());
	    
	    
		return new ModelAndView("index"); 
	}
}