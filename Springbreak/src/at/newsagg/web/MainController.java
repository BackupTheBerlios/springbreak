package at.newsagg.web; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

import at.newsagg.dao.FeedSubscriberDAO;
import at.newsagg.dao.hibernate.FeedSubscriberDAOHibernate;
import at.newsagg.model.User;


public class MainController implements Controller { 
	private static Log log = LogFactory.getLog(MainController.class);
	private FeedSubscriberDAO feedSubscriberDAO;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		User user = userSession.getUserData();
		
		ModelAndView mv =  new ModelAndView("main", "user", user);
		 
		//add all feedSubscribes to output in View
		mv.addObject("feedSubscribes",feedSubscriberDAO.getFeedSubscriberByUser(user.getUsername()));
		//add 50 hottest items to output in View
		mv.addObject("items",feedSubscriberDAO.getHottestItemsForUser(user.getUsername(),50));
		
		return mv;
		
		
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