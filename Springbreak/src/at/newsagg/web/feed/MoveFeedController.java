/*
 * Created on 26.08.2005
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
import org.springframework.web.servlet.view.RedirectView;
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
 * created on 26.08.2005 18:08:06
 *
 */
public class MoveFeedController extends MultiActionController {
	private static Log log = LogFactory.getLog(MultiActionController.class);
    private FeedSubscriberDAO feedSubscriberDAO;
    private CategoryDAO categoryDAO;
       
    
    
    public ModelAndView input (HttpServletRequest request,
            HttpServletResponse response) {
            
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		User user = userSession.getUserData();
		Collection cc = categoryDAO.getCategoriesByUser(user.getUsername());
		log.info("Resultset:"+cc.size());
        return new ModelAndView("openmovefeed", "categories", cc);
   }
    
    public ModelAndView store (HttpServletRequest request,
            HttpServletResponse response) {
            
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		User user = userSession.getUserData();
		
		//log.info(request.getParameter("category"));
		int category = new Integer(request.getParameter("hcategory")).intValue();
		int feed = new Integer(request.getParameter("feed")).intValue();
		int newcategory = new Integer(request.getParameter("newcat")).intValue();
		
		FeedSubscriber f = feedSubscriberDAO.getFeedSubscriberForUserOnChannelinCategory(user.getUsername(),feed,category);
		log.info("feedid modified: "+ f.getId()); 
		 /*
		  * 
		  * TODO: Feedid, Category, newCategory could be empty! Have to catch this in validator!
		  */
		Category c = new Category();
		c.setId(newcategory);
		f.setCategory(c);
		feedSubscriberDAO.updateFeedSubscriber(f);
		
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
