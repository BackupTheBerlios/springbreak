/*
 * Created on 17.09.2005
 * king
 * 
 */
package at.newsagg.web.feed;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import at.newsagg.dao.CategoryDAO;
import at.newsagg.dao.FeedSubscriberDAO;
import at.newsagg.model.Category;
import at.newsagg.model.FeedSubscriber;
import at.newsagg.model.User;
import at.newsagg.web.UserSession;

/**
 * @author king
 * @version
 * created on 17.09.2005 16:25:23
 *
 */
public class DeleteFeedController extends MultiActionController {
    
    CategoryDAO categoryDAO;
    FeedSubscriberDAO feedSubscriberDAO;
    
    private static Log log = LogFactory.getLog(DeleteFeedController .class);
    
    public ModelAndView input (HttpServletRequest request,
            HttpServletResponse response) {
            
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		User user = userSession.getUserData();
		Collection cc = categoryDAO.getCategoriesByUser(user.getUsername());
		log.info("Resultset:"+cc.size());
        return new ModelAndView("opendeletefeed", "categories", cc);
   }
   
    public ModelAndView store (HttpServletRequest request,
            HttpServletResponse response) {
            
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		User user = userSession.getUserData();
		
		//log.info(request.getParameter("category"));
		int category = new Integer(request.getParameter("hcategory")).intValue();
		int feed = new Integer(request.getParameter("feed")).intValue();
		
		FeedSubscriber f = feedSubscriberDAO.getFeedSubscriberForUserOnChannelinCategory(user.getUsername(),feed,category);
		feedSubscriberDAO.removeFeedSubscriber(f.getId());
		log.info("feedid removed: "+ f.getId()); 
		 /*
		  * 
		  * TODO: Feedid, Category  could be empty! Have to catch this in validator!
		  */
		
		
		//Invalidate Menu to display Changes
		request.getSession().removeAttribute("feedSubscriberSession");
		
		return new ModelAndView("redirect:main.html");
      
   }
    

    /**
     * @return Returns the categoryDAO.
     */
    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }
    /**
     * @param categoryDAO The categoryDAO to set.
     */
    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
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
