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

public class AdminInterceptor extends HandlerInterceptorAdapter {
	private static Log log = LogFactory.getLog(AdminInterceptor.class);

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(
				request, "userSession");

		if (!userSession.getUserData().getIsAdmin()) {
			log.info("interceptor cancels request");
			ModelAndView modelAndView = new ModelAndView(
					"redirect:notallowed.jsp");
			throw new ModelAndViewDefiningException(modelAndView);
		} else {
			return true;
		}

	}

}
