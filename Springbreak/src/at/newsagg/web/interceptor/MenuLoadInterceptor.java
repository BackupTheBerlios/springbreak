/*
 * Created on 29.08.2005
 * king
 * 
 */
package at.newsagg.web.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import at.newsagg.dao.FeedSubscriberDAO;
import at.newsagg.web.FeedSubscriberSession;
import at.newsagg.web.UserSession;

/**
 * @author king
 * @version
 * created on 29.08.2005 15:53:03
 *
 */
public class MenuLoadInterceptor extends HandlerInterceptorAdapter {
    private static Log log = LogFactory.getLog(MenuLoadInterceptor.class);

    private FeedSubscriberDAO feedSubscriberDAO;
    
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	   
		log.info("intercept");
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		FeedSubscriberSession fss = (FeedSubscriberSession)WebUtils.getSessionAttribute(request, "feedSubscriberSession");
		if (fss == null) 
	{    
				fss = new FeedSubscriberSession ();
				fss.setFeedSubscribers(feedSubscriberDAO.getFeedSubscriberByUser(userSession.getUserData().getUsername()));
			   
				log.info("Forced Menureload in Session!");
				request.getSession().setAttribute("feedSubscriberSession", fss);
				
		
				
		}
		return true;
		
	}
    /**
     * @return Returns the feedSubscriberDAO.
     */
    public FeedSubscriberDAO getFeedSubscriberDAO() {
        return feedSubscriberDAO;
    }
    /**
     * @param feedSubscriberDAO The feedSubscriberDAO to set.
     */
    public void setFeedSubscriberDAO(FeedSubscriberDAO feedSubscriberDAO) {
        this.feedSubscriberDAO = feedSubscriberDAO;
    }
}
