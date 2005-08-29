package at.newsagg.web; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

import at.newsagg.dao.FeedSubscriberDAO;
import at.newsagg.dao.ItemDAO;
import at.newsagg.model.FeedSubscriber;
import at.newsagg.model.User;


public class MainController implements Controller { 
	private static Log log = LogFactory.getLog(MainController.class);
	private FeedSubscriberDAO feedSubscriberDAO;
	private ItemDAO itemDAO;
	
    /**
     * @return Returns the itemDAO.
     */
    public ItemDAO getItemDAO() {
        return itemDAO;
    }
    /**
     * @param itemDAO The itemDAO to set.
     */
    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }
	private short HOTTEST;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		User user = userSession.getUserData();
		
		short hottestHelper = this.HOTTEST;
		ModelAndView mv =  new ModelAndView("main", "user", user);

		
		//Moved to MenuLoadInterceptor:
		
//		//add all feedSubscribes to output in View
//		//mv.addObject("feedSubscribes",feedSubscriberDAO.getFeedSubscriberByUser(user.getUsername()));
////		if ((FeedSubscriber) WebUtils.getSessionAttribute(request, "feedSubscriberSession") == null)
////		{    
//			FeedSubscriberSession fss = new FeedSubscriberSession ();
//			fss.setFeedSubscribers(feedSubscriberDAO.getFeedSubscriberByUser(user.getUsername()));
//		    log.info("size: "+fss.getFeedSubscribers().size());
//			log.info("setsession");
////			ArrayList a = new ArrayList();
////			a.
//			request.getSession().setAttribute("feedSubscriberSession", fss);
//			log.info("setsession2");
//			
//		//	}
		
		short view = 0;
		if (request.getParameter("view") != null)
		{    
		 view = Integer.valueOf(request.getParameter("view")).shortValue();
		if (view > 0)
		{
		    hottestHelper = view;
		}
		}
		
		//hottest items to given channel
		if (request.getParameter("channel_id") != null)
		    mv.addObject("items",this.feedSubscriberDAO.getHottestItemsForUserByChannel(user.getUsername(),Integer.parseInt(request.getParameter("channel_id")),hottestHelper));
		//hottest items to given category
		else
		    if (request.getParameter("category_id") != null)
		        mv.addObject("items",feedSubscriberDAO.getHottestItemsForUserByCategory(user.getUsername(),Integer.parseInt(request.getParameter("category_id")),hottestHelper));
		//add HOTTEST items to output in View (in any subscribed feed)
		else
		{
		 if (view == 0)
		  mv.addObject("item",itemDAO.getItemsForUser(user.getUsername(),user.getLastLogin()));
		 else    
		    mv.addObject("items",itemDAO.getItemsForUser(user.getUsername(),(int)hottestHelper));
		}
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
    /**
     * @return Returns the hOTTEST.
     */
    public short getHOTTEST() {
        return HOTTEST;
    }
    /**
     * @param hottest The hOTTEST to set.
     */
    public void setHOTTEST(short hottest) {
        HOTTEST = hottest;
    }
}