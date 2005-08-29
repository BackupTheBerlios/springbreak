package at.newsagg.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import at.newsagg.web.UserSession;


/**
 * @author Szabolcs Rozsnyai
 * $Id: LoginInterceptor.java,v 1.1 2005/08/29 15:56:31 vecego Exp $
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static Log log = LogFactory.getLog(LoginInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		if (userSession == null) {
			log.info("interceptor has been called");
			ModelAndView modelAndView = new ModelAndView("welcome"); 
			throw new ModelAndViewDefiningException(modelAndView);
		}
		else {
			return true;
		}
		
	}

}
