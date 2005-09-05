package at.newsagg.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import at.newsagg.web.FeedSubscriberSession;
import at.newsagg.web.UserSession;

   
/** 
 * @author Szabolcs Rozsnyai
 * $Id: LoginInterceptor.java,v 1.2 2005/09/05 17:54:53 vecego Exp $
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static Log log = LogFactory.getLog(LoginInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		if (userSession == null) {
			log.info("interceptor has been called");
			FeedSubscriberSession fss = (FeedSubscriberSession)WebUtils.getSessionAttribute(request, "feedSubscriberSession");
			fss = null;
			ModelAndView modelAndView = new ModelAndView("redirect:welcome.html"); 
			throw new ModelAndViewDefiningException(modelAndView);
		}
		else {
			return true;
		}
		
	}

}
