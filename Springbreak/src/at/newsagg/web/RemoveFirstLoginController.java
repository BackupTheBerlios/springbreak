package at.newsagg.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

import at.newsagg.web.feed.AddChannelController;

public class RemoveFirstLoginController implements Controller {
	private static Log log = LogFactory.getLog(RemoveFirstLoginController.class);
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(
                request, "userSession");
		userSession.getUserData().setLastLogin(userSession.getUserData().getCurrentLogin());
		log.info("Remove FirstLogin. ");
		return new ModelAndView ("redirect:main.html");
	}

}
