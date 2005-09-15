package at.newsagg.web; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.newsagg.model.User;



public class IndexViewController implements Controller { 
	private static Log log = LogFactory.getLog(IndexViewController.class); 
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		
	    HttpSession session = request.getSession();
	    session = null;
		return new ModelAndView("index", "login", new User()); 
	}
}